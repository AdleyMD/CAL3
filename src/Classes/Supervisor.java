package Classes;



/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:09
 */
public class Supervisor extends Thread {
    
    // counter Counts how many supervisors have been created. Only used to set
    // the id for each new supervisor.
    private static int counter = 0; 
    private final int id;

    public Supervisor() {
        id = counter + 5001;
        counter++;
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
