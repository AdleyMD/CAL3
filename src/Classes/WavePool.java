package Classes;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JTextField;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:12
 */
public class WavePool extends Activity {

    private CountDownLatch waveSignal;
    private Lock waveLock;
    private Condition waiting;

    public WavePool(String name, JTextField queueText, JTextField insideText, JTextField supervisorText) {
        super(20, name, new Supervisor(supervisorText), new UserList(queueText), new UserList(insideText));
        getSupervisor().setActivity(this);
        waveLock = new ReentrantLock();
        waiting = waveLock.newCondition();
    }

    @Override
    public boolean canEnter(User user) {
        return (getCurCapacity() != getMaxUsers() - 1);
    }

    @Override
    public void enter(User user) {
        try {
            waveLock.lock();
            //getLock().lock();
            waveSignal = new CountDownLatch(1);
            getSupervisor().setCountdown(waveSignal);
            getSupervisor().setUserToCheck(user);
            getExecutor().execute(getSupervisor());

            getQueue().enqueue(user);
            //System.out.println("user " + user.getName() + " - age - " + user.getAge() + " tiene flag = " + user.getFlag());

            while (!user.getFlag()) {
                // se espera
                waiting.await();

            }

            if (user.getFlag()) {
                waiting.signal();
                getInside().enqueue(getQueue().dequeue());
                getInside().enqueue(getQueue().dequeue());
            }

            getQueue().remove(user);

        } catch (InterruptedException ex) {
            Logger.getLogger(WavePool.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            waveLock.unlock();
        }
    }

    @Override
    public void use(User user) {
        if (user.hasAppropriateAge())
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
            } else {
                addCurCapacity(-1);
            }
            getActFull().signal();
        } catch (Exception e) {

        } finally {
            getLock().unlock();
        }
    }
}//end WavePool
