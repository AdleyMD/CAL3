package Classes;

import javax.swing.JTextField;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:12
 */
public class WavePool extends Activity {

    private Lock pairLock = new ReentrantLock();
    private Condition pairs = pairLock.newCondition();
    // mira cyclic barriers. 
    // http://chuwiki.chuidiang.org/index.php?title=Ejemplo_simple_con_CyclicBarrier

    public WavePool(String name, JTextField queueText, JTextField insideText) {
        super(20, name, new Supervisor(), new UserList(queueText), new UserList(insideText));
        curCapacity = 0;
        supervisor.setActivity(this);
    }

    @Override
    public boolean canEnter(User user) {
        return (curCapacity != maxUsers - 1);
    }

    @Override
    public void enqueue(User user) {
        if (canEnter(user)) {
            return;
        }
        lock.lock();
        executor.execute(supervisor);
        queue.enqueue(user);
        try {
            while (!canEnter(user)) {
                actFull.await();

            }

        } catch (InterruptedException ex) {
            Logger.getLogger(ChangingRoom.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            lock.unlock();
        }
        try {
            while (!coupleReady()) {
                pairs.await();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(WavePool.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void enter(User user) {
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

    public boolean coupleReady() {
        User user = queue.peek();
        return (user.hasCompanion() || (!queue.checkPos(1).hasCompanion() && !user.hasCompanion()));
    }

    public Condition getPairs() {
        return this.pairs;
    }

}//end WavePool
