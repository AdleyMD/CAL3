package Classes;

/**
 * @author andro
 * @version 1.0
 * @created 08-may.-2020 12:16:08
 */
public class Slide extends Activity {
    
    private User user;

    public Slide() {
        super(1, "Slide A", new Supervisor(), new UserList(), null);
        user = null;
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

}//end Slide