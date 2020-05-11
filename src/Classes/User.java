package Classes;
/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:10
 */
public class User extends Thread {

    private WaterPark park;
    private final int actsToDo;
    private int actsCounter;
    private int age;
    private int id;
    private String location;
    private User companion;
    private boolean appropriateAge;
    private boolean flag;

    public User(int id, int minAge, int maxAge, WaterPark park) {
        this.id = id;
        this.park = park;
        appropriateAge = false;
        actsToDo = (int) (5 + (10 * Math.random()));
        actsCounter = 0;
        age = (int) (minAge + ((maxAge - minAge) * Math.random()));

        if (age <= 10) {
            companion = new User(id + 1, (int) (18 + (32 * Math.random())), this);
        } else {
            companion = null;
        }
    }

    /**
     * This constructor is used for the adult companions. An adult companion
     * won't need an activities counter because it will follow the child
     * anywhere it wants.
     */
    public User(int id, int age, User companion) {
        this.id = id;
        this.age = age;
        this.companion = companion;
        actsToDo = 0;
    }

    @Override
    public void run() {
        park.enter(this);
        park.use(this);
        park.leave(this);
    }

    public int getActsCounter() {
        return actsCounter;
    }
    
    public void addActsCounter() {
        actsCounter++;
    }
    
    public int getActsToDo() {
        return actsToDo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getUserId() {
        return id;
    }

    public User getCompanion() {
        return companion;
    }

    public boolean hasCompanion() {
        return age <= 10;
    }

    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    /**
     * sets a flag to check if the user can use the activity.
     * @param bool 
     */
    public synchronized void setAppropriateAge(boolean bool) {
        this.appropriateAge = bool;
    }
    
    public synchronized boolean hasAppropriateAge() {
        return this.appropriateAge;
    }

    public void setFlag(boolean flag){
        this.flag = flag;
    }
    
    public boolean getFlag(){
        return this.flag;
    }
    
    @Override
    public String toString() {
        String comp = "";
        if (companion != null) {
            comp += "-" + companion.getUserId();
        }

        return "ID" + id + "-" + age + comp;
    }
}//end User
