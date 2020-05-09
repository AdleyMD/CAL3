package Classes;

import java.util.ArrayList;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:06
 */
public class ChildrenPool extends Activity {

    private boolean needsCompanion;
     

    public ChildrenPool(int maxUsers, Supervisor supervisor, UserList queue, UserList inside) {
        super(maxUsers,"Children Pool" ,supervisor, queue, inside);
        curCapacity = 0;
    }
    
    @Override
    public void enter(User user) {
        queue.enqueue(user);
        while (curCapacity != maxUsers){
            
        }
        
        
        if (curCapacity != maxUsers) {
            User visitor = queue.dequeue();
            int visitorAge = visitor.getAge();
            if (supervisor.checkAge(1, 5, visitorAge)) {
                needsCompanion = true;
            } else if (supervisor.checkAge(6, 10, visitorAge)) {
                needsCompanion = false;
            }
            customSleep(1000, 1500);
            if (needsCompanion) {
                curCapacity += 1;
            }
            curCapacity += 1;
        }
    }

    @Override
    public void leave(User user) {
        
    }

    @Override
    public void use() {

        customSleep(1000,3000);

    }

    
}//end ChildrenPool
