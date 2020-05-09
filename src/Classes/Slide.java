package Classes;

/**
 * @author andro
 * @version 1.0
 * @created 08-may.-2020 12:16:08
 */
public class Slide extends Activity {

    public Slide() {
        super(1, "Slide", new Supervisor(), new UserList(), null);
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