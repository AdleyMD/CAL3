package Classes;

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
        curCapacity = 0;
    }
    
    @Override
    public boolean canEnter(User user) {
        
        return (curCapacity != 20);
        
    }

    @Override
    public void enter(User user) {
        // una flag del propio supervisor
        // como obtenemos el usuario en el supervisor?
        while (!canEnter(user) && user.getFlag()){
            
        }
        if (canEnter(user)){
            
            return;
        }
    }

    @Override
    public void use(User user) {
        
    }

    @Override
    public void leave(User user) {
        
        curCapacity--;
        
    }
    
    
    // not quite yet.....
    public User firstUser(){
        return this.first;
    }

}//end SunBeds
