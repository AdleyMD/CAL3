package Classes;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WaterPark extends Thread {

    private final int maxUsers;
    private int curCapacity;
    private final UserList queue;
    private final Activity[] activities;
    private final Lock lock;
    private final Condition isFullCondition;
    private User[] usersArray;
    private Main window;

    public WaterPark(Main window) {
        maxUsers = 100;
        curCapacity = 0;
        queue = new UserList(window.getEntrQueueTF());
        activities = new Activity[8];
        lock = new ReentrantLock();
        isFullCondition = lock.newCondition();
        this.window = window;
    }
    
    @Override
    public void run() {
        BigPool bigPool = new BigPool("BigPool", window.getBpQueueTF(), window.getBpInsideTF(), window.getBpSupervisorTF());
        this.addActivity(new ChangingRoom("ChangingRoom", window.getCrQueueTF(), window.getCrInsideTF(), window.getCrSupervisorTF()), 0);
        this.addActivity(new ChildrenPool("ChildrenPool", window.getCpQueueTF(), window.getCpInsideTF(), window.getCpSupervisorTF()), 1);
        this.addActivity(new WavePool("WavePool", window.getWpQueueTF(), window.getWpInsideTF(), window.getWpSupervisorTF()), 2);
        this.addActivity(bigPool, 3);
        this.addActivity(new SunBeds("SunBeds", window.getSbsInsideTF(), window.getSbsSupervisorTF()), 4);
        this.addActivity(new Slide("SlideA", bigPool, window.getSaQueueTF(), window.getSaInsideTF(), window.getSaSupervisorTF()), 5);
        this.addActivity(new Slide("SlideB", bigPool, window.getSbQueueTF(), window.getSbInsideTF(), window.getSbSupervisorTF()), 6);
        this.addActivity(new Slide("SlideC", bigPool, window.getScQueueTF(), window.getScInsideTF(), window.getScSupervisorTF()), 7);
        
        int usersToCreate = 1;
        usersArray = new User[usersToCreate];
        User user;
        for (int i = 1; i <= usersToCreate; i++) {
            if (i == usersToCreate)
                user = new User(i, 11, 50, this);
            else
                user = new User(i, 1, 50, this);
            
            if (user.hasCompanion())
                i++;
            
            usersArray[i-1] = user;
            user.start();
            
            try {
                Thread.sleep((int) (400 + 300 * Math.random()));
            } catch (InterruptedException ex) {}
        }
    }
    
    public void addActivity(Activity act, int index) {
        activities[index] = act;
    }

    public Activity getRandomActivity() {
        int actIndex = (int) (1 + (7 * Math.random()));
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
        } catch (Exception e) {
        } finally {
            lock.unlock();
        }
    }
    
    public void use(User user) {
        activities[0].enter(user); // Enters the changing room
        user.setLocation(activities[0].getName());
        activities[0].use(user);
        user.setLocation(activities[0].getName());
        activities[0].leave(user);
        
        for (int i = 0; i < user.getActsToDo(); i++) {
            Activity nextActivity = getRandomActivity();
            nextActivity.enter(user);
            user.setLocation(nextActivity.getName());
            nextActivity.use(user);
            user.setLocation(nextActivity.getName());
            nextActivity.leave(user);
            user.addActsCounter();
        }
        activities[0].enter(user); // Enters the changing room
        user.setLocation(activities[0].getName());
        activities[0].use(user);
        user.setLocation("OutOfPark");
        activities[0].leave(user);
    }

    public void leave(User user) {
        try {
            lock.lock();
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
    
    public String getUserInfo(String userId) {
        String info = "";
        int id = Integer.parseInt(userId);
        if (id <= usersArray.length) {
            User user = usersArray[id-1];
            info += user.getUserId() + " " + user.getLocation() + user.getActsCounter();
        } else
            info = "Null";
        return info;
    }
}
