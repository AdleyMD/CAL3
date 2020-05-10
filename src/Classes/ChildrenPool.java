package Classes;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:06
 */
public class ChildrenPool extends Activity {

    public ChildrenPool() {
        super(15, "Children Pool", new Supervisor(), new UserList(), new UserList());
        curCapacity = 0;
        supervisor.setActivity(this);
    }

    @Override
    public boolean canEnter(User user) {
        return ((curCapacity < maxUsers - 1 && user.getAge() < 5) || (curCapacity < maxUsers) && user.hasCompanion());
    }

    @Override
    public void enqueue(User user) {
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
        } catch (InterruptedException ex) {
            Logger.getLogger(ChildrenPool.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void enter(User user) {
        if (user.hasCompanion()) {
            curCapacity += 1;
        }
        curCapacity += 1;
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
