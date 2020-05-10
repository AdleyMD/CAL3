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
    
    public Slide(String name, JTextField queueText, JTextField insideText) {
        super(1, name, new Supervisor(), new UserList(queueText), new UserList(insideText));
        semaphore = new Semaphore(maxUsers, true);
    }

    @Override
    public void enter(User user) {
        queue.enqueue(user);
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
        }
        executor.execute(supervisor);
        
        queue.dequeue();
        inside.enqueue(user);
    }

    @Override
    public void use(User user) {
        customSleep(2000, 3000);
    }

    @Override
    public void leave(User user) {
    }
    
    public Slide(String name, BigPool bigPool, JTextField queueText, JTextField insideText) {
        super(1, name, new Supervisor(), new UserList(queueText), new UserList(insideText));
        semaphore = new Semaphore(maxUsers, true);
    }

    @Override
    public boolean canEnter(User user) {
        return true;
    }
    
    @Override
    public void enter(User user) {
        queue.enqueue(user);
        try {
            semaphore.acquire();
        } catch(InterruptedException e){}
        executor.execute(user);
        
        if (user.hasAppropiateAge()) {
            queue.dequeue();
            bigPool.curCapacity = 5;
            inside.enqueue(user);
        }
    }

    @Override
    public void use(User user) {
        if (user.hasAppropiateAge()) {
            customSleep(2000, 3000);
        }
    }

    @Override
    public void leave(User user) {
        inside.remove(user);
    }

    

}//end Slide
