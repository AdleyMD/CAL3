package Classes;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author andro
 * @version 1.0
 * @created 08-may.-2020 12:16:04
 */
public class BigPool extends Activity {
    
    private Slide slideA;
    private Slide slideB;
    private Slide slideC;
    
    public BigPool(){
        super(50, new Supervisor(), new Queue(), new ArrayList<>());
    }

    @Override
    public void enter(User user) {
        queue.enqueue(user);
        try {
            semaphore.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(BigPool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void leave() {
        
    }

    @Override
    public void use() {
        
    }


}//end BigPool