package Classes;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:03
 */
public abstract class Activity {

    public int maxUsers;
    public Supervisor supervisor;
    public Queue queue;
    public ArrayList<User> inside;

    public Activity(int maxUsers, Supervisor supervisor, Queue queue, ArrayList<User> inside) {
        this.maxUsers = maxUsers;
        this.supervisor = supervisor;
        this.queue = queue;
        this.inside = inside;
    }

    public abstract void enter();

    public abstract void leave();

    public abstract void use();

    /**
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
    
    /**
     *
     * @param time
     */
    public void customSleep(int min, int max) {
        try {
            Thread.sleep((long) (min + Math.random() * max));
        } catch (InterruptedException ex) {
            Logger.getLogger(ChangingRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}//end Activity
