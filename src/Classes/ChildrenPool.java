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
        System.out.println("current capacity : " + getCurCapacity());
        System.out.println("maxUsers : " + getMaxUsers());

        return ((getCurCapacity() < getMaxUsers() - 1 && user.hasCompanion())
                || (getCurCapacity() < getMaxUsers()));

    }

    @Override
    public void enter(User user) {

        getLock().lock();
        getExecutor().execute(getSupervisor());
        getSupervisor().setUserToCheck(user);
        getQueue().enqueue(user);

        try {
            // compruebo la edad aqui blabla...
            while (!canEnter(user)) {
                System.out.println("waiting lmao");
                getActFull().await();
            }
            getQueue().dequeue();
            System.out.println("supervisorsaidno: " + supervisorSaidNo(user));
            if (supervisorSaidNo(user)) {
                System.out.println("supervisor said no");
                return;
            }

            if (user.hasCompanion() && user.getAge() <= 5) {
                System.out.println("i have a companion lmao im smol");
                addCurCapacity(1);
            }
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
        if (supervisorSaidNo(user)) {
            return;
        }
        try {
            getLock().lock();
            getInside().remove(user);
            if (user.hasCompanion() && user.getAge() <= 5) {
                addCurCapacity(-2);
                getActFull().signal();
            } else {
                addCurCapacity(-1);
            }

            getActFull().signal();
        } catch (Exception e) {
        } finally {
            getLock().unlock();
        }
    }

}//end ChildrenPool
