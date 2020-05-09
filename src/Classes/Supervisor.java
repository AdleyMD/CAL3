package Classes;

import java.util.logging.Level;
import java.util.logging.Logger;

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
    private ActivitiesZone zone;
    private String actName;

    public Supervisor(ActivitiesZone zone) {
        id = counter + 5001;
        counter++;
        this.zone = zone;
    }

    @Override
    public void run() {
        switch (actName) {
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
    
    public void setActName(String actName) {
        this.actName = actName;
    }
    
    public void bigPool() {
        BigPool bp = (BigPool) zone.getActivity(actName);
        customSleep(500);
        if (bp.isFull())
            bp.kickRandom();
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
    
    public void customSleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(ChangingRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void customSleep(int min, int max) {
        try {
            Thread.sleep((long) (min + (max - min) * Math.random()));
        } catch (InterruptedException ex) {
            Logger.getLogger(ChangingRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}//end Supervisor
