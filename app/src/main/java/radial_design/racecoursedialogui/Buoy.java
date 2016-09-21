package radial_design.racecoursedialogui;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Jonathan on 27/08/2016.
 */
public class Buoy {
    private String name;
    private AviLocation aviLocation;
    public String color;
    public Date lastUpdate;
    public long id;
    private UUID _uuid;
    private UUID _raceCourseUUID;

    public Buoy(String name, AviLocation loc){
        this.name=name;
        this.aviLocation=loc;
        this.lastUpdate = new Date();
    }
}
