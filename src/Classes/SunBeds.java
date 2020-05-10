package Classes;

import javax.swing.JTextField;

/**
 * @author andro
 * @version 1.0
 * @created 08-may.-2020 12:16:08
 */
public class SunBeds extends Activity {

    public SunBeds(String name, JTextField queueText, JTextField insideText) {
        super(20, name, new Supervisor(), new UserList(queueText), new UserList(insideText));
        curCapacity = 0;
    }
    
    @Override
    public boolean canEnter(User user) {
        return (!(curCapacity == 20));
    }

    @Override
    public void enqueue(User user) {
        
    }

    @Override
    public void enter(User user) {
        if (canEnter(user)) {
            return;
        }
        
    }

    @Override
    public void leave(User user) {
        
        
    }

}//end SunBeds
