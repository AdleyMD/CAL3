package Classes;

import javax.swing.JTextField;

public class WaterPark {
    
    private final int maxUsers;
    private int currentUsers;
    private final UserList queue;
    private final Activity[] activities;
    
    public WaterPark(JTextField entranceQueue) {
        maxUsers = 100;
        queue = new UserList(entranceQueue);
        activities = new Activity[8];
    }
    
    public void addActivity(Activity act, int index) {
        activities[index] = act;
    }
}
