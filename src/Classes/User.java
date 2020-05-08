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

    public User(int id, int idCompanion, int age, int actsCounter){
        this.id = id;
        this.idCompanion = idCompanion;
        this.age = age;
        this.actsCounter = actsCounter;
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
