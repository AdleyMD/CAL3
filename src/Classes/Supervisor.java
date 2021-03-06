package Classes;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

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
    private CountDownLatch countdown;
    private JTextField textField;

    public Supervisor(JTextField textField) {
        id = counter + 5001;
        counter++;
        this.textField = textField;
        userToCheck = null;
    }

    @Override
    public void run() {
        setText();
        switch (activity.getName()) {
            case ("ChangingRoom"):
                changingRoom();
                break;
            case ("WavePool"):
                wavePool();
                break;
            case ("ChildrenPool"):
                childrenPool();
                break;
            case ("SunBeds"):
                sunBeds();
                break;
            case ("BigPool"):
                bigPool();
                break;
            case ("SlideA"):
            case ("SlideB"):
            case ("SlideC"):
                slide();
                break;
        }
    }

    public synchronized void setUserToCheck(User user) {
        userToCheck = user;
    }

    public User getUserToCheck() {
        return userToCheck;
    }

    public void setCountdown(CountDownLatch countdown) {
        this.countdown = countdown;
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
        int userAge = userToCheck.getAge();
        boolean hasCompanion = userToCheck.hasCompanion();
        customSleep(400, 500);
        switch (activity.getName()) {
            case ("Slide A"):
                if (!hasCompanion && userAge >= 11 && userAge <= 14) {
                    userToCheck.setAppropriateAge(true);
                } else {
                    userToCheck.setAppropriateAge(false);
                }
                break;
            case ("Slide B"):
                if (!hasCompanion && userAge >= 15 && userAge <= 17) {
                    userToCheck.setAppropriateAge(true);
                } else {
                    userToCheck.setAppropriateAge(false);
                }
                break;
            case ("Slide C"):
                if (userAge >= 18) {
                    userToCheck.setAppropriateAge(true);
                } else {
                    userToCheck.setAppropriateAge(false);
                }
                break;
        }
        countdown.countDown();
    }

    public void sunBeds() {
        if (userToCheck.getAge() < 14) {
            userToCheck.setAppropriateAge(false);
        } else {
            userToCheck.setAppropriateAge(true);
        }
        customSleep(500, 900);
    }

    public void wavePool() {
        userToCheck.setAppropriateAge(false);
        UserList queue = activity.getQueue();

        customSleep(1000);

        if (userToCheck.getAge() > 6) {
            userToCheck.setAppropriateAge(true);
        }
        User first;
        User second;

        //guaranteing that the queue has 2 elements so we can initialize them.
        if (queue.hasNElements(2)) {
            first = queue.peek();
            second = queue.checkPos(1);
            if (first.hasCompanion()) {
                first.setFlag(true);
                first.getCompanion().setFlag(true);
            }
            if (!first.hasCompanion() && second.hasCompanion()) {
                first.setFlag(false);
                second.setFlag(true);
                second.getCompanion().setFlag(true);
            } else if (!first.hasCompanion() && !second.hasCompanion()) {
                first.setFlag(true);
                second.setFlag(true);
            }

        } else if (queue.hasNElements(1)) {
            first = queue.peek();
            if (first.hasCompanion()) {
                first.setFlag(true);
                first.getCompanion().setFlag(true);
            } else {
                first.setFlag(false);
            }
        }
        countdown.countDown();
    }

    public void changingRoom() {
        customSleep(1000); // checking age

    }

    public void childrenPool() {
        User kid = userToCheck;

        if (kid.getAge() < 6) {
            kid.setAppropriateAge(true);
            kid.getCompanion().setAppropriateAge(true);
        } else if (kid.getAge() < 11) {
            kid.setAppropriateAge(true);
        } else if (kid.getAge() > 10 && !kid.hasAppropriateAge()) {
            kid.setAppropriateAge(false);
        }
        customSleep(1000, 1500);
        countdown.countDown();
    }

    public void customSleep(int time) {
        try {
            Thread.sleep(time);

        } catch (InterruptedException ex) {
            Logger.getLogger(ChangingRoom.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void customSleep(int min, int max) {
        try {
            Thread.sleep((long) (min + (max - min) * Math.random()));

        } catch (InterruptedException ex) {
            Logger.getLogger(ChangingRoom.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setText() {
        String text = "";
        text += "ID-" + id;
        textField.setText(text);
    }
}//end Supervisor
