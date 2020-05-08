package Classes;

/**
 * @author andro
 * @version 1.0
 * @created 08-may.-2020 12:16:10
 */
public class User extends Thread {
    
    private WaterPark park;
    private int actCounter;
    private int age;
    private int id;
    private int idCompanion;

    public User(){
        actCounter = (int) (5 + (10 * Math.random()));
        age = (int) (5 + (10 * Math.random()));
    }
    
    @Override
    public void run() {
        
    }
    
    public void subActCounter(){
        actCounter--;
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

    public int getIdCompanion() {
        return idCompanion;
    }
        
        
}//end User
