package Classes;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:05
 */
public class ChangingRoom extends Activity {

    private int adultCapacity;
    private int currentAdult;
    private int childrenCapacity;
    private int currentChild;

    public ChangingRoom(Supervisor supervisor, UserList queue, UserList inside) {
        super(0, "Changing Room", supervisor, queue, inside);

        adultCapacity = 20;
        childrenCapacity = 10;
    }

    @Override
    public void enter(User user) {
        queue.enqueue(user);
        executor.execute(supervisor);
        try {
            lock.lock();

            while (currentAdult == adultCapacity || currentChild == childrenCapacity) {
                try {
                    actFull.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ChangingRoom.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // aqui estoy comprobando la edad, eso feo.
            /*while ((currentAdult == 20 && user.getAge() > 18)
                    || user.getAge() <= 10 && currentChild == 10 || currentAdult == 20
                    || (currentChild != 10 && user.getAge() < 18)) {

            }
             */
            //supervisor.sleep(1000);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void use() {
        customSleep(3000);
    }

    @Override
    public void leave(User user) {
        customSleep(3000);
        if (user.hasCompanion()) {
            actFull.signal();
        }
        actFull.signal();
    }

}//end ChangingRoom
