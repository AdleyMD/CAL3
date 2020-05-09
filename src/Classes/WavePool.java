package Classes;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:12
 */
public class WavePool extends Activity {

    public WavePool(int maxUsers, Supervisor supervisor, UserList queue, UserList inside) {
        super(maxUsers, "Wave Pool", supervisor, queue, inside);
        curCapacity = 0;
        supervisor.setActivity(name);
    }

    @Override
    public void enter(User user) {
        queue.enqueue(user);
        executor.execute(supervisor);
        try {
            lock.lock();
            while ((user.hasCompanion() || (!user.hasCompanion() && !queue.checkPos(1).hasCompanion()))
                    && curCapacity == maxUsers - 1) {
                actFull.await();
            }
            if (user.hasCompanion()) {
                inside.enqueue(user);
                curCapacity += 2;
            } else if (!queue.checkPos(2).hasCompanion()) {
                inside.enqueue(user);
                curCapacity += 2;
            }
            queue.dequeue();
            queue.dequeue();
        } catch (InterruptedException ie) {
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void leave(User user) {
        inside.remove(user);
        actFull.signal();
    }

    @Override
    public void use() {
        customSleep(2000, 5000);
    }

}//end WavePool
