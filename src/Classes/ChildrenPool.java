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

    public ChildrenPool(String name, JTextField queueText, JTextField insideText, JTextField supervisorText) {
        super(15, name, new Supervisor(supervisorText), new UserList(queueText), new UserList(insideText));
        getSupervisor().setActivity(this);
    }

    @Override
    public boolean canEnter(User user) {
        return ((getCurCapacity() < getMaxUsers() - 1 && user.hasCompanion())
                || (getCurCapacity() < getMaxUsers()));
    }

    @Override
    public void enter(User user) {
        try {
            getLock().lock();
            getSupervisor().setUserToCheck(user);
            getExecutor().execute(getSupervisor());
            getQueue().enqueue(user);
            // compruebo la edad aqui blabla...
            while (!canEnter(user)) {
                getActFull().await();
            }
            getQueue().dequeue();

            if (user.hasCompanion() && user.getAge() <= 5) {
                addCurCapacity(2);
            } else
                addCurCapacity(1);

        } catch (InterruptedException ex) {
            Logger.getLogger(ChildrenPool.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            getLock().unlock();
        }

    }

    @Override
    public void use(User user) {
        if (!supervisorSaidNo(user)) {
            customSleep(1000, 3000);
        }
        return;
    }

    @Override
    public void leave(User user) {
        try {
            getLock().lock();
            getInside().remove(user);
            if (user.hasCompanion() && user.getAge() <= 5) {
                addCurCapacity(-2);
                getActFull().signal();
            } else
                addCurCapacity(-1);

            getActFull().signal();
        } catch (Exception e) {
        } finally {
            getLock().unlock();
        }
    }

}//end ChildrenPool
