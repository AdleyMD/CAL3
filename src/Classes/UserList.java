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

	public UserList(JTextField textField) {
            list = new ArrayList<>();
            this.textField = textField;
	}

	public User dequeue() {
            User u = list.remove(0);
            setText();
            return u;
	}

	public synchronized void enqueue(User user) {
            list.add(user);
            setText();
	}

        public synchronized void remove(User user) {
            list.remove(user);
            setText();
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
            User u;
            if (list.size() > n)
                u = list.get(n);
            else
                u = null;
            return u;
        }
        
        public boolean twoInQueue() {
            return (list.size() > 1);
        }
        
        public synchronized void setText() {
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
