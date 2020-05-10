package Classes;

import java.util.concurrent.Executor;
import javax.swing.JTextField;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:08
 */
public class SunBeds extends Activity {
    
    private User first;

    public SunBeds(String name, JTextField queueText, JTextField insideText) {
        super(20, name, new Supervisor(), null, new UserList(insideText));
    }

    @Override
    public boolean canEnter(User user) {
        return (getCurCapacity() != 20);
    }

    @Override
    public void enter(User user) {
        getSupervisor().setUserToCheck(user);
        getExecutor().execute(getSupervisor());
        while (!canEnter(user)) {
        }
        if (supervisorSaidNo(user)) {
            return;
        }
        getInside().enqueue(user);
    }

    @Override
    public void use(User user) {

        if (supervisorSaidNo(user)) {
            return;
        }
        customSleep(2000, 4000);
    }

    @Override
    public void leave(User user) {
        addCurCapacity(-1);
    }

    // not quite yet.....
    public User firstUser() {
        return this.first;
    }

}//end SunBeds
