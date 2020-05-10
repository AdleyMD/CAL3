package Classes;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

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

    public ChangingRoom(String name, JTextField queueText, JTextField insideText) {
        super(0, name, new Supervisor(), new UserList(queueText), new UserList(insideText));

        adultCapacity = 20;
        childrenCapacity = 10;
        supervisor.setActivity(this);
    }

    @Override
    public boolean canEnter(User user) {
        return (adultCapacity < currentAdult && childrenCapacity < currentChild);
    }

    @Override
    public void enter(User user) {
        lock.lock();
        queue.enqueue(user);
        executor.execute(supervisor);
        try {
            while (!(canEnter(user))) {
                try {
                    actFull.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ChangingRoom.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            customSleep(3000);
            if (!user.hasCompanion() && user.getAge() < 18) {
                childrenCapacity++;
            } else if (user.hasCompanion()) {
                adultCapacity++;
                childrenCapacity++;
            } else {
                adultCapacity++;
            }
            queue.enqueue(user);

        } finally {
            lock.unlock();
        }
    }

    @Override
    public void use(User user) {
        customSleep(3000);
    }

    @Override
    public void leave(User user) {

        if (user.hasCompanion()) {
            actFull.signal();
        }
        actFull.signal();
    }

}//end ChangingRoom
