package Classes;

import java.util.ArrayList;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:06
 */
public class ChildrenPool extends Activity {

    private boolean needsCompanion;
    private int capacity;

    public ChildrenPool(int maxUsers, Supervisor supervisor, Queue queue, ArrayList<User> inside) {
        super(maxUsers, supervisor, queue, inside);
    }

    @Override
    public void enter() {

        if (capacity != maxUsers) {
            User visitor = queue.dequeue();
            int visitorAge = visitor.getAge();
            if (supervisor.checkAge(1, 5, visitorAge)) {
                needsCompanion = true;
            } else if (supervisor.checkAge(6, 10, visitorAge)) {
                needsCompanion = false;
            }
            customSleep(1000, 1500);
            if (needsCompanion) {
                capacity += 1;
            }
            capacity += 1;
        }
    }

    @Override
    public void leave() {
        
    }

    @Override
    public void use() {

        customSleep(1000,3000);

    }

}//end ChildrenPool
