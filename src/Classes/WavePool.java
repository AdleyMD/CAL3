package Classes;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:12
 */

public class WavePool extends Activity {

    Condition waitingPair;
    int capacity = 0;

    public WavePool(int maxUsers, Supervisor supervisor, Queue queue, ArrayList<User> inside) {
        super(maxUsers, supervisor, queue, inside);

       

    }

    @Override
    public void enter() {
        if (capacity != maxUsers) {
            User user = queue.peek();
            if (user.getCompanion()!= null){
                
            }
            supervisor.checkAge(6, 10, user.getAge());
        }
    }

    
    @Override
    public void leave() {
        
    }

    @Override
    public void use() {
        
    }

}//end WavePool
