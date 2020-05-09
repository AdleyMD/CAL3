package Classes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:03
 */
public abstract class Activity {

    public String name;
    public int maxUsers;
    public int curCapacity;
    public Supervisor supervisor;
    public UserList queue;
    public UserList inside;
    public ExecutorService executor;
    public Lock lock;
    public Condition actFull;

    public Activity(int maxUsers, String name, Supervisor supervisor, UserList queue, UserList inside) {
        this.maxUsers = maxUsers;
        this.name = name;
        this.supervisor = supervisor;
        this.queue = queue;
        this.inside = inside;
        executor = Executors.newSingleThreadExecutor();
        curCapacity = 0;
        lock = new ReentrantLock();
        actFull = lock.newCondition();
    }

    public abstract void enter(User user);

    public abstract void use(User user);
    
    public abstract void leave(User user);

    public String getName() {
        return name;
    }
    
    public boolean isFull() {
        return curCapacity == maxUsers;
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

}//end Activity
