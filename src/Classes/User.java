package Classes;

/**
 * @author andro
 * @version 1.0
 * @created 08-may.-2020 12:16:10
 */
public class User extends Thread {
    
    private WaterPark park;
    private int actsCounter;
    private int age;
    private int id;
    private User companion;

    public User(int id) {
        this.id = id;
        actsCounter = (int) (5 + (10 * Math.random()));
        age = (int) (1 + (49 * Math.random()));
        if (age <= 10)
            companion = new User(id+1, (int) (18 + (32 * Math.random())), this);
        else
            companion = null;
    }
    
    /**
     * This constructor is used for the parent companions.
     * A parent companion won't need an activities counter
     * because it will follow the child anywhere it wants.
     */
    public User(int id, int age, User companion){
        this.id = id;
        this.age = age;
        this.companion = companion;
    }
    
    @Override
    public void run() {
        
    }
    
    public void subActsCounter(){
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
    
    @Override
    public String toString() {
        String comp = "";
        if (companion != null)
            comp += companion.getUserId();
        
        return "ID" + id + "-" + comp;
    }   
}//end User
