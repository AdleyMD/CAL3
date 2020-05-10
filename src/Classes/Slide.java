package Classes;

import javax.swing.JTextField;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:08
 */
public class Slide extends Activity {

    public Slide(String name, JTextField queueText, JTextField insideText) {
        super(1, name, new Supervisor(), new UserList(queueText), new UserList(insideText));
    }

    @Override
    public boolean canEnter(User user) {
        return true;
    }
    
    @Override
    public void enter(User user) {
        

    }

    @Override
    public void use(User user) {

    }

    @Override
    public void leave(User user) {
    }

    

}//end Slide
