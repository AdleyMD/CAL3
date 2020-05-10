package Classes;

import javax.swing.JTextField;

/**
 * @author andro
 * @version 1.0
 * @created 08-may.-2020 12:16:08
 */
public class SunBeds extends Activity {
    
    public SunBeds(String name, JTextField insideText) {
        super(20, name, new Supervisor(), null, new UserList(insideText));
    }

    @Override
    public void enqueue(User user) {
    }

    @Override
    public void enter(User user) {
    }

    @Override
    public void leave(User user) {
    }

    @Override
    public boolean canEnter(User user) {
        return true;
    }

}//end SunBeds
