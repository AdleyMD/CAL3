package Classes;
/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:10
 */
public class User extends Thread {

    private WaterPark park;
    private int actsCounter;
    private int age;
    private int id;
    private User companion;
    private boolean appropiateAge;
    private boolean pass;

    public User(int id, int minAge, int maxAge, WaterPark park) {
        this.id = id;
        this.park = park;
        actsCounter = (int) (5 + (10 * Math.random()));
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
    }

    @Override
    public void run() {
        
        
        
        if (appropiateAge){
            // go to activity
        } else{
            // get a new activity or terminate the other one.
        }

    }

    public void subActsCounter() {
        actsCounter--;
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
    /**
     * sets a flag to check if the user can use the activity.
     * @param bool 
     */
    public void setAppropiatedAge(boolean bool) {
        this.appropiateAge = bool;
    }
    
    public boolean getAppropiatedAge(){
        return this.appropiateAge;
    }

    public void setPass(boolean bool){
        this.pass = bool;
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
