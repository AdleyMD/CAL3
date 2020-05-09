package Classes;

public class WaterPark {
    
    private final int maxUsers;
    private int currentUsers;
    private final UserList queue;
    private final ActivitiesZone zone;
    
    public WaterPark() {
        maxUsers = 100;
        queue = new UserList();
        zone = new ActivitiesZone();
    }
    
    public void startSimulation() {
        int usersToCreate = 5000;
        User user;
        for (int i = 1; i <= usersToCreate; i++) {
            if (i == usersToCreate)
                user = new User(i, 11, 50, this);
            else
                user = new User(i, 1, 50, this);
            if (user.hasCompanion())
                i++;
            user.start();
        }
    }
}
