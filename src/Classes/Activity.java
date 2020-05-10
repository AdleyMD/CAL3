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

    private String name;
    private int maxUsers;
    private int curCapacity;
    private Supervisor supervisor;
    private UserList queue;
    private UserList inside;
    private ExecutorService executor;
    private Lock lock;
    private Condition actFull;

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

    public abstract boolean canEnter(User user);

    public abstract void enter(User user);

    public abstract void use(User user);

    public abstract void leave(User user);

    public boolean supervisorSaidNo(User user) {
        return (user.hasAppropiateAge());
    }

    public String getName() {
        return name;
    }

    public synchronized boolean isFull() {
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

    public int getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    public int getCurCapacity() {
        return curCapacity;
    }

    public void setCurCapacity(int curCapacity) {
        this.curCapacity = curCapacity;
    }
    
    public void addCurCapacity(int add) {
        this.curCapacity += add;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public UserList getQueue() {
        return queue;
    }

    public void setQueue(UserList queue) {
        this.queue = queue;
    }

    public UserList getInside() {
        return inside;
    }

    public void setInside(UserList inside) {
        this.inside = inside;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    public Condition getActFull() {
        return actFull;
    }

    public void setActFull(Condition actFull) {
        this.actFull = actFull;
    }
    
    
    
    
    

}//end Activity
