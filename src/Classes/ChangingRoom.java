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

            try {
                user.sleep(3000);
                supervisor.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ChangingRoom.class.getName()).log(Level.SEVERE, null, ex);
            }           

        } else if (supervisor.checkCompanion(user) && ChildrenCapacity != 0 && AdultCapacity != 0) {
            ChildrenCapacity--;
            AdultCapacity--;
            queue.dequeue();
            queue.dequeue();

            try {
                user.sleep(3000); 
                user.getCompanion().sleep(3000);  
                // se cambiarán a la vez?.... porq es secuencial
                // no quiero que lo bloquee, sino que vaya a otro lado
                supervisor.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ChangingRoom.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            // toca hacerle queue de nuevo al principio? que feo... retoco queue
        }
    }

    public void leave() {
        try {
            // necesito saber cuanta gente sale
            Thread.sleep(3000);
            // estoy haciendo Thread.sleep, no el hilo como tal, las cosas apuntan
            // a que voy a necesitar como parametro los hilos que quieren salir...
        } catch (InterruptedException ex) {
            Logger.getLogger(ChangingRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}//end ChangingRoom
