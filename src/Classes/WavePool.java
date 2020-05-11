package Classes;

import java.util.concurrent.CyclicBarrier;
import javax.swing.JTextField;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:12
 */
public class WavePool extends Activity {

    private CyclicBarrier barrier = new CyclicBarrier(2);

    public WavePool(String name, JTextField queueText, JTextField insideText, JTextField supervisorText) {
        super(20, name, new Supervisor(supervisorText), new UserList(queueText), new UserList(insideText));
        getSupervisor().setActivity(this);
    }

    @Override
    public boolean canEnter(User user) {
        return (getCurCapacity() != getMaxUsers() - 1);
    }

    @Override
    public void enter(User user) {

        getLock().lock();

        getSupervisor().setUserToCheck(user);

        getExecutor().execute(getSupervisor());
        getQueue().enqueue(user);

        // pegarle un repaso a esto, comprobar porq entran dentro directamente y no en cola.
        // getinside en use??
        try {
            
            while (!canEnter(user)) {
                //System.out.println("No pude entrar.");
                getActFull().await();
            }
            if (user.getFlag()){
                getInside().enqueue(getQueue().dequeue());
                getInside().enqueue(getQueue().dequeue());
            }
            /*if (user.hasCompanion()) {
                //System.out.println("hey, tengo compañero!");
                getInside().enqueue(user);
                getInside().enqueue(user.getCompanion());
                addCurCapacity(2);
            }*/ else if (!user.hasCompanion()) {
                //System.out.println("estoy solito :v");
                //a esperar chaval lmao...
                System.out.println("estoy esperando lmao ");
                //getInside().enqueue(user);
                //addCurCapacity(1);
            }// else if (user.getFlag()){
                //getInside
            //}
            getQueue().remove(user);
        } catch (InterruptedException ex) {
            Logger.getLogger(ChangingRoom.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            getLock().unlock();
        }
    }

    @Override
    public void use(User user) {
        if (user.hasAppropiateAge()) {
            customSleep(2000, 5000);
        }
    }

    @Override
    public void leave(User user) {
        try {
            getLock().lock();
            getInside().remove(user);
            if (user.hasCompanion()) {
                getActFull().signal();
                addCurCapacity(-2);
            } else {
                addCurCapacity(-1);
            }
            getActFull().signal();
        } catch (Exception e) {

        } finally {
            getLock().unlock();
        }
    }
}//end WavePool
