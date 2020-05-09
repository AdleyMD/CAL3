package Classes;

import java.util.ArrayList;
import javax.swing.JTextField;

/**
 * @author andro
 * @version 1.0
 * @created 08-may.-2020 12:16:07
 */
public class UserList {

	private ArrayList<User> queue;
	private JTextField textField;

	public UserList() {
            queue = new ArrayList<>();
	}

	public User dequeue() {
            return queue.remove(0);
	}

	public void enqueue(User user) {
            queue.add(user);
	}

        public void remove(User user) {
            queue.remove(user);
        }
        
	public boolean isEmpty() {
            return queue.isEmpty();
	}
        
	public User peek() {
            return queue.get(0);
	}
        
        public User checkPos(int n){
            return queue.get(n);
        }
        
        public void setText() {
            String text = "";
            String comp = "";
            for (User u : queue) {
                if (u.hasCompanion())   // Prints the companion too, if any
                    comp = u.getCompanion().toString() + ", ";
                text += u.toString() + ", " + comp;
            }
            textField.setText(text);
        }
}//end UserList