package Classes;

import java.util.ArrayList;
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
        super(50, "Big Pool", new Supervisor(), new UserList(), new UserList());
        supervisor.setActivity(name);
    }

    @Override
    public void enter(User user) {
        queue.enqueue(user);
        lock.lock();
        try {
            while (user.hasCompanion() && curCapacity >= maxUsers - 1 || curCapacity == maxUsers) {
                actFull.await();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(BigPool.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            lock.unlock();
        }
        user = queue.dequeue();
        
        if (user.hasCompanion())
            curCapacity += 2;
        else
            curCapacity++;
        
       inside.add(user);
    }
    
    @Override
    public void use() {
        customSleep(3000, 5000);
    }
    
    @Override
    public void leave(User user) {
        
    }
}//end BigPool