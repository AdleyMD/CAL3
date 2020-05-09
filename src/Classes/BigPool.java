package Classes;

import java.util.ArrayList;

/**
 * @author andro
 * @version 1.0
 * @created 08-may.-2020 12:16:04
 */
public class BigPool extends Activity {
    
    private Slide slideA;
    private Slide slideB;
    private Slide slideC;
    
    public BigPool(){
        super(50, new Supervisor(), new Queue(), new ArrayList<>());
    }

    @Override
    public void enter() {
        
    }

    @Override
    public void leave() {
        
    }

    @Override
    public void use() {
        
    }


}//end BigPool