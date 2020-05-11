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
            getExecutor().execute(getSupervisor());
            getSupervisor().setUserToCheck(user);
            getQueue().enqueue(user);
            // compruebo la edad aqui blabla...
            while (!canEnter(user)) {
                getActFull().await();
            }
            getQueue().dequeue();
            if (user.hasAppropiateAge()) {
                getInside().enqueue(user);
            //    System.out.println("user" + user.getId() + " appropiate Age");
                if (user.hasCompanion() && user.getAge() <= 5) {
                    getInside().enqueue(user.getCompanion());
                    addCurCapacity(2);
                //    System.out.println(" user has companion");
                } else {
                    addCurCapacity(1);
                //    System.out.println("user doesnt have a companion");
                }
            } else {
                // el usuario no ha tenido la edad apropiada
            //    System.out.println("user: " + user.getId() + " not an appropiate Age, age: " + user.getAge());
                return;
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(ChildrenPool.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            getLock().unlock();
        }

    }

    @Override
    public void use(User user) {

        if (user.hasAppropiateAge()) {
        //    System.out.println("user, age: "+ user.getAge() +" using the system");
            customSleep(10000, 30000);
        }
    }

    @Override
    public void leave(User user) {
        try {
            getLock().lock();
        //    System.out.println("user leaving");
            getInside().remove(user);
            if (user.hasCompanion() && user.getAge() <= 5) {
                addCurCapacity(-2);
                getActFull().signal();
            } else {
                addCurCapacity(-1);
            }

            getActFull().signal();
        } catch (Exception ex) {
            Logger.getLogger(ChildrenPool.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            getLock().unlock();
        }
    }

}//end ChildrenPool
