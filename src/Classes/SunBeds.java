package Classes;

import java.util.concurrent.Executor;
import javax.swing.JTextField;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:08
 */
public class SunBeds extends Activity {

    private int curCapacity = getCurCapacity();
    private Supervisor supervisor = getSupervisor();
    private Executor executor = getExecutor();
    private UserList inside = getInside();

    private User first;

    public SunBeds(String name, JTextField queueText, JTextField insideText) {
        super(20, name, new Supervisor(), null, new UserList(insideText));
        curCapacity = 0;
    }

    @Override
    public boolean canEnter(User user) {
        return (curCapacity != 20);
    }

    @Override
    public void enter(User user) {
        supervisor.setUserToCheck(user);
        executor.execute(supervisor);
        while (!canEnter(user)) {
        }
        if (supervisorSaidNo(user)) {
            return;
        }
        inside.enqueue(user);
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
        curCapacity--;
    }

    // not quite yet.....
    public User firstUser() {
        return this.first;
    }

}//end SunBeds
