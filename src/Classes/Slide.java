package Classes;

import java.util.concurrent.Semaphore;
import javax.swing.JTextField;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:08
 */
public class Slide extends Activity {

    private Semaphore semaphore;
    
    public Slide(String name, JTextField queueText, JTextField insideText) {
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
        queue.dequeue();
        inside.enqueue(user);
    }

    @Override
    public void use(User user) {

    }

    @Override
    public void leave(User user) {
    }

    

}//end Slide
