package Classes;

public class WaterPark {
    
    private final int maxUsers;
    private final int usersToEnter;
    private int currentUsers;
    private final Queue queue;
    private final ActivitiesZone zone;
    
    public WaterPark() {
        maxUsers = 100;
        usersToEnter = 5000;
        queue = new Queue(usersToEnter);
        zone = new ActivitiesZone();
    }
    
    public void startSimulation() {
        for (int i = 0; i < usersToEnter; i++) {
            User user = new User(i);
            if (user.hasCompanion())
                i++;
            user.start();
        }
    }
}
