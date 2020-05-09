package Classes;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:09
 */
public class Supervisor implements Runnable {
    
    // counter Counts how many supervisors have been created. Only used to set
    // the id for each new supervisor.
    private static int counter = 0; 
    private final int id;
    private User userToCheck;
    private String activity;

    public Supervisor() {
        id = counter + 5001;
        counter++;
    }

    @Override
    public void run() {
        switch (activity) {
            case ("Changing Room"):
                changingRoom();
                break;
            case ("Wave Pool"):
                wavePool();
                break;
            case ("Children Pool"):
                childrenPool();
                break;
            case ("Sun Beds"):
                sunBeds();
                break;
            case ("Big Pool"):
                bigPool();
                break;
            case ("Slide"):
                slide();
                break;
        }
    }
    
    public void setUserToCheck(User user) {
        userToCheck = user;
    }
    
    public void setActivity(String activity) {
        this.activity = activity;
    }
    
    public void bigPool() {
        
    }
    
    public void slide() {
        
    }
    
    public void sunBeds() {
        
    }
    
    public void wavePool() {
        
    }
    
    public void changingRoom() {
        
    }
    
    public void childrenPool() {
        
    }
}//end Supervisor
