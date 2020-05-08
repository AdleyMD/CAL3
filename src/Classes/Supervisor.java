package Classes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author CHAD (Copper Heroes Andrei & Darius)
 * @version 1.0
 * @created 08-may.-2020 12:16:09
 */
public class Supervisor extends Thread {

<<<<<<< HEAD
    private int id;
    private ExecutorService executor = Executors.newFixedThreadPool(8); 
=======
    private final int id;
>>>>>>> 3b7dc919bf7cff44260cbcb8a1539ab5a777bc1f

    public Supervisor(int id) {
        this.id = id;
    }

    @Override
    public void run() {

        while (true) {
            executor.execute(this);
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
