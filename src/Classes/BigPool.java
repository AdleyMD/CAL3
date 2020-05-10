package Classes;

import java.util.concurrent.Executor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

/**
 * @author andro
 * @version 1.0
 * @created 08-may.-2020 12:16:04
 */
public class BigPool extends Activity {

    private int curCapacity = getCurCapacity();
    private Supervisor supervisor = getSupervisor();
    private int maxUsers = getMaxUsers();
    private Lock lock = getLock();
    private Executor executor = getExecutor();
    private UserList queue = getQueue();
    private UserList inside = getInside();
    private Condition actFull = getActFull();

    public BigPool(String name, JTextField queueText, JTextField insideText) {
        super(50, name, new Supervisor(), new UserList(queueText), new UserList(insideText));
        supervisor.setActivity(this);
    }

    @Override
    public void enter(User user) {
        executor.execute(supervisor);
        try {
            lock.lock();
            queue.enqueue(user);
            while (!canEnter(user)) {
                actFull.await();
            }

            if (user.hasCompanion()) {
                curCapacity += 2;
            } else {
                curCapacity++;
            }

            user = queue.dequeue();
            inside.enqueue(user);
        } catch (InterruptedException ex) {
            Logger.getLogger(BigPool.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void use(User user) {
        try {
            Thread.sleep((long) (3000 + (2000 * Math.random())));
        } catch (InterruptedException e) {
            return;
        }
    }

    @Override
    public void leave(User user) {
        try {
            lock.lock();
            inside.remove(user);

            if (user.hasCompanion()) {
                curCapacity -= 2;
            } else {
                curCapacity--;
            }
            actFull.signal();

        } catch (Exception ex) {
            Logger.getLogger(BigPool.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean canEnter(User user) {
        return user.hasCompanion() && curCapacity <= maxUsers - 1 || user.hasCompanion() && curCapacity < maxUsers;
    }

    public void kickRandom() {
        User kickedUser = inside.extractRandom();
        kickedUser.interrupt();
    }
}//end BigPool
