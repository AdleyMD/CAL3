package Classes;

import java.util.concurrent.CountDownLatch;
import javax.swing.JTextField;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:08
 */
public class Slide extends Activity {

    private BigPool bigPool;
    
    public Slide(String name, BigPool bigPool, JTextField queueText, JTextField insideText, JTextField supervisorText) {
        super(1, name, new Supervisor(supervisorText), new UserList(queueText), new UserList(insideText));
        getSupervisor().setActivity(this);
        this.bigPool = bigPool;
    }

    @Override
    public boolean canEnter(User user) {
        return getCurCapacity() == 0 && !bigPool.isFull();
    }

    @Override
    public void enter(User user) {
        CountDownLatch doneSignal = new CountDownLatch(1);
        
        try {
            getLock().lock();
            getQueue().enqueue(user);
            while (!canEnter(user))
                getActFull().await();
            getSupervisor().setCountdown(doneSignal);
            getSupervisor().setUserToCheck(user);
            getExecutor().execute(getSupervisor());
            doneSignal.await();
            getQueue().remove(user);
        if (user.hasAppropiateAge()) {
            getInside().enqueue(user);
            bigPool.addCurCapacity(1);
            addCurCapacity(1);
        }
        
        } catch (InterruptedException e) {
        } finally {
            getLock().unlock();
        }
    }

    @Override
    public void use(User user) {
        if (user.hasAppropiateAge())
            customSleep(2000, 3000); // PONER BIEN
    }

    @Override
    public void leave(User user) {
        try {
            getLock().lock();
            if (user.hasAppropiateAge()) {
                getInside().remove(user);
                bigPool.addCurCapacity(-1);
                addCurCapacity(-1);
            }
            getActFull().signal();
        } catch (Exception e) {
        } finally {
            getLock().unlock();
        }
        
    }
}
