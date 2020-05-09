package Classes;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:12
 */
public class WavePool extends Activity {

    Condition waitingPair;

    public WavePool(int maxUsers, Supervisor supervisor, UserList queue, ArrayList<User> inside) {
        super(maxUsers, supervisor, queue, inside);
        curCapacity = 0;
    }

    @Override
    public void enter(User user) {

        queue.enqueue(user);
        try {
            lock.lock();
            while ((user.hasCompanion() && curCapacity == maxUsers - 1
                    || (!user.hasCompanion() && !queue.peek().hasCompanion())) && curCapacity == maxUsers - 1) {
                actFull.await();
            }
            if (user.hasCompanion()) {
                inside.add(user);
                curCapacity += 2;
            } else if (!queue.checkPos(2).hasCompanion()) {
                inside.add(user);
                inside.add(queue.checkPos(2));
                curCapacity += 2;
            }
            queue.dequeue();
            queue.dequeue();
            use();
        } catch (InterruptedException ie) {
        } finally {
            lock.unlock();
        }

        // en teoria para ejecutarlo en supervisor.java
        supervisor.checkAge(6, 10, user.getAge());
    }

    @Override
    public void leave(User user) {
        inside.remove(user);
    }

    @Override
    public void use() {
        customSleep(2000, 5000);
    }

}//end WavePool
