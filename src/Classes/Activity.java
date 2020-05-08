package Classes;

import java.util.ArrayList;

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
    
}//end Activity
