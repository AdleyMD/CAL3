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
    private Slide s;
    private WavePool wp;
    private SunBeds sb;
    private ChildrenPool cp;

    public ActivitiesZone(){

    }
        
    public Activity getActivity(String name) {
        Activity act = null;
        switch (name) {
            case ("Changing Room"):
                act = cr;
                break;
            case ("Wave Pool"):
                act = wp;
                break;
            case ("Children Pool"):
                act = cp;
                break;
            case ("Sun Beds"):
                act = sb;
                break;
            case ("Big Pool"):
                act = bp;
                break;
            case ("Slide"):
                act = s;
                break;
        }

        return act;
    }
}//end ActivitiesZone