package Classes;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:09
 */
public class Supervisor implements Runnable {

    // counter Counts how many supervisors have been created. Only used to set
    // the id for each new supervisor.
    private static int counter = 0;
    private final int id;
    private User userToCheck;
    private Activity activity;

    public Supervisor() {
        id = counter + 5001;
        counter++;
    }

    @Override
    public void run() {
        switch (activity.getName()) {
            case ("Changing Room"):
                changingRoom();
                break;
            case ("Wave Pool"):
                wavePool();
                break;
            case ("Children Pool"):
                childrenPool();
                break;
            case ("Sun Beds"):
                sunBeds();
                break;
            case ("Big Pool"):
                bigPool();
                break;
            case ("Slide A"):
            case ("Slide B"):
            case ("Slide C"):
                slide();
                break;
        }
    }

    public void setUserToCheck(User user) {
        userToCheck = user;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void bigPool() {

        while (activity.isFull()) {
            customSleep(500);
            if (activity.isFull()) {
                customSleep(500, 1000);
                ((BigPool) activity).kickRandom();
            }
        }
    }

    public void slide() {

    }

    public void sunBeds() {
        User user = ((SunBeds) activity).firstUser();
        user.setFlag(true);
        if (user.getAge() < 14) {
            user.setAppropiateAge(false);
        }
        customSleep(500, 900);
    }

    public void wavePool() {
        User user = activity.queue.peek();
        // comprobamos con if su edad.
        user.setAppropiateAge(false);
        if (user.getAge() > 6) {
            user.setAppropiateAge(true);
            customSleep(1000);
        }
        if (((WavePool) activity).coupleReady()) {
        }
    }

    public void changingRoom() {
        customSleep(1000); // checking age
    }

    public void childrenPool() {
        User user = activity.queue.peek();
        customSleep(1000, 1500); // time to check the age

        if (user.getAge() < 6) {
            user.setAppropiateAge(true);
            user.getCompanion().setAppropiateAge(true);
        } else if (user.getAge() < 11) {
            user.setAppropiateAge(true);
        } else if (user.getAge() > 10 && !user.hasAppropiateAge()) {
            user.setAppropiateAge(false);
        }
    }

    public void customSleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(ChangingRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void customSleep(int min, int max) {
        try {
            Thread.sleep((long) (min + (max - min) * Math.random()));
        } catch (InterruptedException ex) {
            Logger.getLogger(ChangingRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}//end Supervisor