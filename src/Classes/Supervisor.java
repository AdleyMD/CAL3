package Classes;



/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:09
 */
public class Supervisor extends Thread {


    private int id;
    

    public Supervisor(int id) {
        this.id = id;
    }

    @Override
    public void run() {

        while (true) {
            
            
            // crear una condicion que lo despierte si necesita levantar a alguien?
        }
    }

    /**
     *
     * @param min
     * @param max
     * @param age
     * @param user
     * @return
     */
    public boolean checkAge(int min, int max, int age) {
        return (min <= age && age <= max);
    }

    /**
     *
     * @param user
     * @return
     */
    public boolean checkCompanion(User user) {
        return (user.getCompanion() != null);
    }

}//end Supervisor
