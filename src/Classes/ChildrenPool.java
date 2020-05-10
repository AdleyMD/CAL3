package Classes;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:06
 */
public class ChildrenPool extends Activity {

    public ChildrenPool(String name, JTextField queueText, JTextField insideText) {
        super(15, name, new Supervisor(), new UserList(queueText), new UserList(insideText));
        curCapacity = 0;
        supervisor.setActivity(this);
    }

    @Override
    public boolean canEnter(User user) {
        return ((curCapacity < maxUsers - 1 && user.getAge() < 5) || (curCapacity < maxUsers) && user.hasCompanion());
    }

    @Override
    public void enter(User user) {
        if (canEnter(user)) {
            return;
        }

        lock.lock();
        executor.execute(supervisor);
        queue.enqueue(user);
        try {
            while (!canEnter(user)) {
                actFull.await();
            }
            if (supervisorSaidNo(user)) {
                return;
            }

            if (user.hasCompanion()) {
                curCapacity += 1;
            }
            curCapacity += 1;

        } catch (InterruptedException ex) {
            Logger.getLogger(ChildrenPool.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void use(User user) {
        if (supervisorSaidNo(user)) {
            return;
        }
        customSleep(1000, 3000);
    }

    @Override
    public void leave(User user) {
        if (user.hasCompanion()) {
            curCapacity--;
            actFull.signal();
        }
        curCapacity--;
        actFull.signal();
    }

}//end ChildrenPool
