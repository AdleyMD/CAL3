package Classes;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:05
 */
public class ChangingRoom {

    private int AdultCapacity;
    private int ChildrenCapacity;
    private Supervisor supervisor;
    private Queue queue;

    public ChangingRoom() {
        AdultCapacity = 20;
        ChildrenCapacity = 10;
    }

    public void enter() {
        User user = queue.peek();
        int age = user.getAge();
        if (supervisor.checkAge(10, 17, age) && ChildrenCapacity != 0) {
            ChildrenCapacity--;
            queue.dequeue();

            //user doing its thing
            customSleep(3000);
            
            //signal, despierta al supervisor.
            supervisor.sleep(1000);
            // ademas sincronizar al usuario para que se espere el tambien.

        } else if (supervisor.checkCompanion(user) && ChildrenCapacity != 0 && AdultCapacity != 0) {
            ChildrenCapacity--;
            AdultCapacity--;
            queue.dequeue();
            queue.dequeue();

            user.sleep(3000);
            //signal, despierta al supervisor.
            supervisor.sleep(2000);

        } else {
            // toca hacerle queue de nuevo al principio? que feo... retoco queue
        }
    }

    public void leave() {

        customSleep(3000);

    }

    /**
     * Function required to be in ChangingRoom since it's not part of activity.
     *
     * @param time
     */
    public void customSleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(ChangingRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}//end ChangingRoom
