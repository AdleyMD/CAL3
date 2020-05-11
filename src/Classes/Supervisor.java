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
                System.out.println("A " + (!hasCompanion && userAge >= 11 && userAge <= 14));
                if (!hasCompanion && userAge >= 11 && userAge <= 14) {
                    userToCheck.setAppropiateAge(true);
                } else {
                    userToCheck.setAppropiateAge(false);
                }
                break;
            case ("Slide B"):
                System.out.println("B " + (!hasCompanion && userAge >= 15 && userAge <= 17));
                if (!hasCompanion && userAge >= 15 && userAge <= 17) {
                    userToCheck.setAppropiateAge(true);
                } else {
                    userToCheck.setAppropiateAge(false);
                }
                break;
            case ("Slide C"):
                System.out.println("A " + (userAge > 18));
                if (userAge >= 18) {
                    userToCheck.setAppropiateAge(true);
                } else {
                    userToCheck.setAppropiateAge(false);
                }
                break;
        }
        countdown.countDown();
    }

    public void sunBeds() {

        if (userToCheck.getAge() < 14) {
            userToCheck.setAppropiateAge(false);
        } else {
            userToCheck.setAppropiateAge(true);
        }
        customSleep(500, 900);

    }

    public void wavePool() {

        userToCheck.setAppropiateAge(false);
        UserList queue = activity.getQueue();

        customSleep(1000);

        if (userToCheck.getAge() > 6) {
            userToCheck.setAppropiateAge(true);
        }
        User first;
        User second;

        //omg toca limpiar esto....
        //ensuring there exists position X;
        if (queue.hasNElements(1)) {
            first = queue.peek();
            if (first.hasCompanion()) {
                first.setFlag(true);
                first.getCompanion().setFlag(true);
            } else {
                first.setFlag(false);
            }

        } else if (queue.hasNElements(2)) {
            first = queue.peek();
            second = queue.checkPos(2);

            
            // comprobar de nuevo que haga lo que quiero...
            // no hace lo q quiero, puede entrar 1 solo, lmao
            if (!first.hasCompanion() && second.hasCompanion()) {
                second.setFlag(true);
                second.getCompanion().setFlag(true);
            } else if (!first.hasCompanion() && !second.hasCompanion()) {
                first.setFlag(true);
                second.setFlag(true);
                //consigue esa wea y dale off we go. ? el cyclic barrier.
            }
            System.out.println("comprobe flags");

        } else {

        }

    }

    public void changingRoom() {
        customSleep(1000); // checking age
        countdown.countDown();
    }

    public void childrenPool() {
        customSleep(1000, 1500); // time to check the age
        if (userToCheck.getAge() < 6) {
            userToCheck.setAppropiateAge(true);
            userToCheck.getCompanion().setAppropiateAge(true);
        } else if (userToCheck.getAge() < 11) {
            userToCheck.setAppropiateAge(true);
        } else if (userToCheck.getAge() > 10 && !userToCheck.hasAppropiateAge()) {
            userToCheck.setAppropiateAge(false);
        }
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
