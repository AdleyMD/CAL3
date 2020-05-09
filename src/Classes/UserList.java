package Classes;

import java.util.ArrayList;
import javax.swing.JTextField;

/**
 * @author andro
 * @version 1.0
 * @created 08-may.-2020 12:16:07
 */
public class UserList {

	private ArrayList<User> list;
	private JTextField textField;

	public UserList() {
            list = new ArrayList<>();
	}

	public User dequeue() {
            return list.remove(0);
	}

	public void enqueue(User user) {
            list.add(user);
	}

        public void remove(User user) {
            list.remove(user);
        }
        
        public User extractRandom() {
            int randIndex = (int) (list.size() * Math.random()); // Puede dar error index out of bounds?
            User user = list.get(randIndex);
            list.remove(randIndex);
            return user;
        }
        
	public boolean isEmpty() {
            return list.isEmpty();
	}
        
	public User peek() {
            return list.get(0);
	}
        
        public User checkPos(int n){
            return list.get(n);
        }
        
        public void setText() {
            String text = "";
            String comp = "";
            for (User u : list) {
                if (u.hasCompanion())   // Prints the companion too, if any
                    comp = u.getCompanion().toString() + ", ";
                text += u.toString() + ", " + comp;
            }
            textField.setText(text);
        }
}//end UserList