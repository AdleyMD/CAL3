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
    private int idCompanion;

    public User(int id) {
        this.id = id;
        actsCounter = (int) (5 + (10 * Math.random()));
        age = (int) (5 + (10 * Math.random()));
    }
    
    public User(int id, int age){
        this.id = id;
        this.age = age;
        actsCounter = (int) (5 + (10 * Math.random()));
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
    
    public void setIdCompanion(int idCompanion) {
        this.idCompanion = idCompanion;
    }
    
    public String toString() {
        String comp = "";
        if (idCompanion != null)
            comp = idCompanion;
        return "ID" + id + "-" + comp;
    }
        
        
}//end User
