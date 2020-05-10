package Classes;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

/**
 * @author andro
 * @version 1.0
 * @created 08-may.-2020 12:16:04
 */
public class BigPool extends Activity {

    
    public BigPool(String name, JTextField queueText, JTextField insideText, JTextField supervisorText) {
        super(50, name, new Supervisor(supervisorText), new UserList(queueText), new UserList(insideText));
        getSupervisor().setActivity(this);
    }

    @Override
    public void enter(User user) {
        getSupervisor().setUserToCheck(user);
        getExecutor().execute(getSupervisor());
        try {
            getLock().lock();
            getQueue().enqueue(user);
            while (!canEnter(user)) {
                getActFull().await();
            }

            if (user.hasCompanion()) {
                addCurCapacity(2);
            } else {
                addCurCapacity(1);
            }

            getQueue().remove(user);
            getInside().enqueue(user);
        } catch (InterruptedException ex) {
            Logger.getLogger(BigPool.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            getLock().unlock();
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
            getLock().lock();
            getInside().remove(user);

            if (user.hasCompanion()) {
                addCurCapacity(-2);
                getActFull().signal();
            } else
                addCurCapacity(-1);
            
            getActFull().signal();

        } catch (Exception ex) {
            Logger.getLogger(BigPool.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            getLock().unlock();
        }
    }

    @Override
    public boolean canEnter(User user) {
        return user.hasCompanion() && getCurCapacity() <= getMaxUsers() - 1 || !user.hasCompanion() && getCurCapacity() < getMaxUsers();
    }

    public void kickRandom() {
        User kickedUser = getInside().extractRandom();
        kickedUser.interrupt();
    }
}//end BigPool
