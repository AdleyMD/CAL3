package Classes;

import java.util.concurrent.Semaphore;
import javax.swing.JTextField;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:08
 */
public class Slide extends Activity {

    private BigPool bigPool;
    
    public Slide(String name, BigPool bigPool, JTextField queueText, JTextField insideText, JTextField supervisorText) {
        super(1, name, new Supervisor(supervisorText), new UserList(queueText), new UserList(insideText));
        getSupervisor().setActivity(this);
        this.bigPool = bigPool;
    }

    @Override
    public boolean canEnter(User user) {
        return !user.hasCompanion() && getCurCapacity() == 0;
    }

    @Override
    public void enter(User user) {
        try {
            getLock().lock();
            getQueue().enqueue(user);
            while (!canEnter(user))
                getActFull().await();
            getSupervisor().setUserToCheck(user);
            getExecutor().execute(getSupervisor());
            System.out.println(getName() + " en " + user.toString());
            getQueue().remove(user);
            System.out.println(getName() + " en " + user.toString());
        if (user.hasAppropiateAge())
            getInside().enqueue(user);
        
        } catch (InterruptedException e) {
        } finally {
            getLock().lock();
        }
    }

    @Override
    public void use(User user) {
        if (user.hasAppropiateAge())
            customSleep(2000, 3000);
    }

    @Override
    public void leave(User user) {
        if (user.hasAppropiateAge()) {
            getInside().remove(user);
        }
        
        
    }
}//end Slide
