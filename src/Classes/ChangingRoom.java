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

    public ChangingRoom(String name, JTextField queueText, JTextField insideText, JTextField supervisorText) {
        super(0, name, new Supervisor(supervisorText), new UserList(queueText), new UserList(insideText));
        adultCapacity = 20;
        childrenCapacity = 10;
        getSupervisor().setActivity(this);
    }

    @Override
    public boolean canEnter(User user) {
        return (user.hasCompanion() && currentAdult < adultCapacity && currentChild < childrenCapacity
                || !user.hasCompanion() && user.getAge() <=17 && currentChild < childrenCapacity
                || !user.hasCompanion() && currentAdult < adultCapacity);
    }

    @Override
    public void enter(User user) {
        try {
            getLock().lock();
            getQueue().enqueue(user);
            getSupervisor().setUserToCheck(user);
            getExecutor().execute(getSupervisor());
            System.out.println("HE ENTRADO CABRONAZO");
            customSleep(3000);
            while (!(canEnter(user))) {
                try {
                    getActFull().await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ChangingRoom.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (!user.hasCompanion() && user.getAge() < 18) {
                childrenCapacity++;
            } else if (user.hasCompanion()) {
                adultCapacity++;
                childrenCapacity++;
            } else {
                adultCapacity++;
            }
            getQueue().remove(user);
            getInside().enqueue(user);
        } finally {
            getLock().unlock();
        }
    }
    
    @Override
    public void use(User user) {
        if (!supervisorSaidNo(user)) {
            customSleep(3000);
        }
        return;
    }

    @Override
    public void leave(User user) {
        try {
            getLock().lock();
            getInside().remove(user);
            if (user.hasCompanion()) {
                getActFull().signal();
                addCurCapacity(-2);
            } else {
                addCurCapacity(-1);
            }

            getActFull().signal();
        } catch (Exception e) {
        } finally {
            getLock().unlock();
        }
    }

}//end ChangingRoom
