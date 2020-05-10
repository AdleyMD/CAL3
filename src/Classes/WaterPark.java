package Classes;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JTextField;

public class WaterPark {

    private final int maxUsers;
    private int curCapacity;
    private final UserList queue;
    private final UserList inside;
    private final Activity[] activities;
    private final Lock lock;
    private final Condition isFullCondition;

    public WaterPark(JTextField entranceQueue) {
        maxUsers = 100;
        curCapacity = 0;
        queue = new UserList(entranceQueue);
        inside = new UserList(null);
        activities = new Activity[8];
        lock = new ReentrantLock();
        isFullCondition = lock.newCondition();
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
            inside.dequeue();
            
            if (user.hasCompanion())
                curCapacity -= 2;
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
