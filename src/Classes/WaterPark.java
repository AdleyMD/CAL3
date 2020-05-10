package Classes;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WaterPark extends Thread {

    private final int maxUsers;
    private int curCapacity;
    private final UserList queue;
    private final UserList inside;
    private final Activity[] activities;
    private final Lock lock;
    private final Condition isFullCondition;
    private Main window;

    public WaterPark(Main window) {
        maxUsers = 100;
        curCapacity = 0;
        queue = new UserList(window.getEntrQueueTF());
        inside = new UserList(null);
        activities = new Activity[8];
        lock = new ReentrantLock();
        isFullCondition = lock.newCondition();
        this.window = window;
    }
    
    @Override
    public void run() {
        BigPool bigPool = new BigPool("Big Pool", window.getBpQueueTF(), window.getBpInsideTF(), window.getBpSupervisorTF());
        this.addActivity(new ChangingRoom("Changing Room", window.getCrQueueTF(), window.getCrInsideTF(), window.getCrSupervisorTF()), 0);
        this.addActivity(new ChildrenPool("Children Pool", window.getCpQueueTF(), window.getCpInsideTF(), window.getCpSupervisorTF()), 1);
        this.addActivity(new WavePool("Wave Pool", window.getWpQueueTF(), window.getWpInsideTF(), window.getWpSupervisorTF()), 2);
        this.addActivity(bigPool, 3);
        this.addActivity(new SunBeds("Sun Beds", window.getSbsInsideTF(), window.getSbsSupervisorTF()), 4);
        this.addActivity(new Slide("Slide A", bigPool, window.getSaQueueTF(), window.getSaInsideTF(), window.getSaSupervisorTF()), 5);
        this.addActivity(new Slide("Slide B", bigPool, window.getSbQueueTF(), window.getSbInsideTF(), window.getSbSupervisorTF()), 6);
        this.addActivity(new Slide("Slide C", bigPool, window.getScQueueTF(), window.getScInsideTF(), window.getScSupervisorTF()), 7);
        
        int usersToCreate = 500;
        User user;
        for (int i = 1; i <= usersToCreate; i++) {
            if (i == usersToCreate)
                user = new User(i, 11, 50, this);
            else
                user = new User(i, 1, 50, this);
            
            if (user.hasCompanion())
                i++;
            
            user.start();
            
            try {
                Thread.sleep((long) (400 + 300 * Math.random()));
            } catch (InterruptedException ex) {}
        }
    }
    
    public void addActivity(Activity act, int index) {
        activities[index] = act;
    }

    public Activity getRandomActivity() {
        int actIndex = (int) (1 + 6 * Math.random());
        return activities[actIndex];
    }
    
    public void enter(User user) {
        try {
            lock.lock();
            queue.enqueue(user);
            while (!canEnter(user)) {
                isFullCondition.await();
            }

            queue.dequeue();
            
            if (user.hasCompanion())
                curCapacity += 2;
            else
                curCapacity++;

            inside.enqueue(user);
            activities[0].enter(user); // Enters the changing room
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }
    
    public void use(User user) {
        for (int i = 0; i < user.getActsCounter(); i++) {
            Activity nextActivity = getRandomActivity();
            nextActivity.enter(user);
            nextActivity.use(user);
            nextActivity.leave(user);
        }
    }

    public void leave(User user) {
        try {
            lock.lock();
            activities[0].enter(user);
            inside.dequeue();
            
            if (user.hasCompanion()) {
                curCapacity -= 2;
                isFullCondition.signal();
            }
            else
                curCapacity--;
            
            isFullCondition.signal();
            
        } catch (Exception e) {
            
        } finally {
            lock.unlock();
        }
    }

    public boolean canEnter(User user) {
        return user.hasCompanion() && curCapacity < maxUsers - 1 || !user.hasCompanion() && curCapacity < maxUsers;
    }
}
