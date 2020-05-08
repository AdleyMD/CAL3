package Classes;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:09
 */
public class Supervisor {

    private int id;

    public Supervisor(int id) {
        this.id = id;
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
        return (user.getAge() <= 10);
    }
    
}//end Supervisor
