package Classes;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import javax.swing.JTextField;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:12
 */
public class WavePool extends Activity {

    private CyclicBarrier barrier = new CyclicBarrier(2);

    public WavePool(String name, JTextField queueText, JTextField insideText) {
        super(20, name, new Supervisor(), new UserList(queueText), new UserList(insideText));
        getSupervisor().setActivity(this);
    }

    @Override
    public boolean canEnter(User user) {
        return (getCurCapacity() != getMaxUsers() - 1);
    }

    @Override
    public void enter(User user) {
        getLock().lock();
        getSupervisor().setUserToCheck(user);
        getExecutor().execute(getSupervisor());
        getQueue().enqueue(user);
        try {
            while (!canEnter(user)) {
                getActFull().await();
            }
            if (supervisorSaidNo(user)) {
                return;
            }
            // waiting 2 users to enter/signal.
            barrier.await();

            if (user.hasCompanion()) {
                getInside().enqueue(user);
                addCurCapacity(2);
            } else if (!getQueue().checkPos(2).hasCompanion()) {
                getInside().enqueue(user);
                addCurCapacity(2);
            }
            getQueue().dequeue();
            getQueue().dequeue();

        } catch (InterruptedException ex) {
            Logger.getLogger(ChangingRoom.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BrokenBarrierException ex) {
            Logger.getLogger(WavePool.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            getLock().unlock();
        }
    }

    @Override
    public void use(User user) {
        if (supervisorSaidNo(user)) {
            return;
        }
        customSleep(2000, 5000);
    }

    @Override
    public void leave(User user) {
        try {
            getLock().lock();
            getInside().remove(user);
            if (user.hasCompanion()) {
                getActFull().signal();
                addCurCapacity(-2);
            } else
                addCurCapacity(-1);
            getActFull().signal();
        } catch (Exception e) {
            
        } finally {
            getLock().unlock();
        }
        
    }

    public boolean coupleReady() {
        User user = getQueue().peek();
        if (getQueue().checkPos(1) == null)
            return false;
        else
            return (user.hasCompanion() || (!getQueue().checkPos(1).hasCompanion() && !user.hasCompanion()));
    }

}//end WavePool
