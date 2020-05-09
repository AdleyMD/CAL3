package Classes;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:06
 */
public class ChildrenPool extends Activity {

    private boolean needsCompanion;

    public ChildrenPool(int maxUsers, Supervisor supervisor, UserList queue, UserList inside) {
        super(maxUsers, "Children Pool", supervisor, queue, inside);
        curCapacity = 0;
        supervisor.setActName(name);
    }

    @Override
    public void enter(User user) {
        queue.enqueue(user);
        executor.execute(supervisor);
        try {
            lock.lock();
            while (curCapacity == maxUsers) {
                actFull.await();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ChildrenPool.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            lock.unlock();
        }

        if (needsCompanion) {
            curCapacity += 1;
        }
        curCapacity += 1;
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

    @Override
    public void use(User user) {
        customSleep(1000, 3000);
    }

}//end ChildrenPool
