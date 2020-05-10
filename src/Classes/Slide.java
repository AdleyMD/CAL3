package Classes;

import java.util.concurrent.Semaphore;
import javax.swing.JTextField;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:08
 */
public class Slide extends Activity {

    private final Semaphore semaphore;
    private BigPool bigPool;
    
    public Slide(String name, BigPool bigPool, JTextField queueText, JTextField insideText) {
        super(1, name, new Supervisor(), new UserList(queueText), new UserList(insideText));
        getSupervisor().setActivity(this);
        semaphore = new Semaphore(getMaxUsers(), true);
        this.bigPool = bigPool;
    }

    @Override
    public boolean canEnter(User user) {
        return true;
    }

    @Override
    public void enter(User user) {
        getQueue().enqueue(user);
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {}
        getSupervisor().setUserToCheck(user);
        getExecutor().execute(getSupervisor());
        
        getQueue().remove(user);
        if (user.hasAppropiateAge())
            getInside().enqueue(user);
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
            semaphore.release();
        }
    }

    

}//end Slide
