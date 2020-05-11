package Classes;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JTextField;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:05
 */
public class ChangingRoom extends Activity {

    private int adultCapacity;
    private int currentAdult;
    private int childrenCapacity;
    private int currentChild;
    private Lock changingLock;
    private Condition companion;

    public ChangingRoom(String name, JTextField queueText, JTextField insideText, JTextField supervisorText) {
        super(0, name, new Supervisor(supervisorText), new UserList(queueText), new UserList(insideText));
        adultCapacity = 20;
        childrenCapacity = 10;
        getSupervisor().setActivity(this);
        changingLock = new ReentrantLock();
        companion = changingLock.newCondition();
    }

    @Override
    public boolean canEnter(User user) {
        return (user.hasCompanion() && currentAdult < adultCapacity && currentChild < childrenCapacity
                || !user.hasCompanion() && user.getAge() <=17 && currentChild < childrenCapacity
                || !user.hasCompanion() && currentAdult < adultCapacity);
    }

    @Override
    public void enter(User user) {
        try {
            getLock().lock();
            getQueue().enqueue(user);
            while (!canEnter(user)) {
                getActFull().await();
            }
            getSupervisor().setUserToCheck(user);
            getExecutor().execute(getSupervisor());
            customSleep(3000);
            if (!user.hasCompanion() && user.getAge() < 18) {
                childrenCapacity++;
            } else if (user.hasCompanion()) {
                adultCapacity++;
                childrenCapacity++;
            } else {
                adultCapacity++;
            }
            getQueue().remove(user);
            getInside().enqueue(user);
        } catch (Exception e) {
        } finally {
            getLock().unlock();
        }
    }
    
    @Override
    public void use(User user) {
        customSleep(3000);
    }

    @Override
    public void leave(User user) {
        try {
            changingLock.lock();
            getInside().remove(user);
            if (user.hasCompanion()) {
                getActFull().signal();
                getActFull().signal();
                addCurCapacity(-2);

            } else {
                getActFull().signal();
                addCurCapacity(-1);
            }

        } catch (Exception e) {
        } finally {
            changingLock.unlock();
        }
    }

}//end ChangingRoom
