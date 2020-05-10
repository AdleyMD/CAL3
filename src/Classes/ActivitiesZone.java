package Classes;

/**
 * @author andro
 * @version 1.0
 * @created 08-may.-2020 12:16:01
 */
public class ActivitiesZone {
        
    private WaterPark park;
    private ChangingRoom cr;
    private BigPool bp;
    private Slide sA;
    private Slide sB;
    private Slide sC;
    private WavePool wp;
    private SunBeds sb;
    private ChildrenPool cp;

    public ActivitiesZone(){
        cr = new ChangingRoom();
        bp = new BigPool();
        sA = new Slide();
        sB = new Slide();
        sC = new Slide();
        wp = new WavePool();
        sb = new SunBeds();
        cp = new ChildrenPool();
    }
}//end ActivitiesZone