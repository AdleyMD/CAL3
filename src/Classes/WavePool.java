package Classes;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:12
 */
public class WavePool extends Activity {

    public WavePool() {
        super(20, "Wave Pool", new Supervisor(), new UserList(), new UserList());
        curCapacity = 0;
        supervisor.setActivity(this);
    }

    @Override
    public boolean canEnter(User user){
        return (!(user.hasCompanion() || (!user.hasCompanion() && !queue.checkPos(1).hasCompanion()))
                    && curCapacity == maxUsers - 1);
    }
    
    @Override
    public void enqueue(User user) {
        if (canEnter(user)){
            return;
        }
        // el ejecutar al supervisor en un lock...? no es redundante? al fin y al cabo estamos en thread pool...
        lock.lock();
        executor.execute(supervisor);
        queue.enqueue(user);
        try {
            while ((user.hasCompanion() || (!user.hasCompanion() && !queue.checkPos(1).hasCompanion()))
                    && curCapacity == maxUsers - 1) {
                actFull.await();
            }
        } catch (InterruptedException ie) {
        } finally {
            lock.unlock();
        }
    }

    
    @Override
    public void use(User user) {
        if (user.hasCompanion()) {
            inside.enqueue(user);
            curCapacity += 2;
        } else if (!queue.checkPos(2).hasCompanion()) {
            inside.enqueue(user);
            curCapacity += 2;
        }
        queue.dequeue();
        queue.dequeue();
        customSleep(2000, 5000);
    }

    @Override
    public void leave(User user) {
        inside.remove(user);
        actFull.signal();
    }

}//end WavePool
