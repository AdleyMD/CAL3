package Classes;

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
        try {
            getLock().lock();
            getQueue().enqueue(user);
            while (!canEnter(user))
                getActFull().await();
            getSupervisor().setUserToCheck(user);
            getExecutor().execute(getSupervisor());
            getQueue().remove(user);
            System.out.println(getName() + " en " + user.toString());
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
            System.out.println(user.getUserId() + " i sleep" + user.hasAppropiateAge());
            customSleep(2000, 3000);
    }

    @Override
    public void leave(User user) {
        try {
            getLock().lock();
            if (user.hasAppropiateAge()) {
                getInside().remove(user);
                bigPool.addCurCapacity(-1);
                addCurCapacity(-1);
                System.out.println(user.getUserId() + " i leave");
            }
            getActFull().signal();
        } catch (Exception e) {
        } finally {
            getLock().unlock();
        }
        
    }
}
