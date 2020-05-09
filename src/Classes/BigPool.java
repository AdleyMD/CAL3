package Classes;

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
    
    public BigPool(ActivitiesZone zone){
        super(50, "Big Pool", new Supervisor(zone), new UserList(), new UserList());
        supervisor.setActName(name);
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
        
       inside.enqueue(user);
    }
    
    @Override
    public void use(User user) {
        try {
            Thread.sleep((long) (3000 + (2000 * Math.random())));
        } catch (InterruptedException e) {
            leave(user);
        }
    }
    
    @Override
    public void leave(User user) {
        lock.lock();
        try {
            if (user.hasCompanion())
                curCapacity -= 2;
            else
                curCapacity--;
            
            inside.remove(user);
            actFull.signal();
        } catch (Exception ex) {
            Logger.getLogger(BigPool.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            lock.unlock();
        }
    }
    
    public void kickRandom() {
        User kickedUser = inside.extractRandom();
        kickedUser.interrupt();
    }
}//end BigPool