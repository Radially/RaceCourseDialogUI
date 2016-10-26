package radial_design.racecoursedialogui;

import android.content.SharedPreferences;
import android.location.Location;
import android.os.Handler;
import android.view.View;

import java.util.Date;
import java.util.List;

/**
 * Created by Jonathan on 26/10/2016.
 */
public class newGoogleMapsActivity extends GoogleMapsActivity {
    private RaceCourse raceCourse;  //imported from jonathan's new RaceCourse class //replaces private RaceCourse rc;
    public List<Buoy> buoys; //replaces public static Marks marks = new Marks();
    //private HashMap<String, BoatTypes> boatTypes;  //unnecessary, now is a part ot the xml boat parser
    private Buoy myBoat; //instead of AviObject class

    private static Users users;
    private SharedPreferences SP;
    private GoogleMaps mapLayer;
    private ConfigChange unc = new ConfigChange();
    private IGeo iGeo;
    private Handler handler = new Handler();
    private WindArrow wa;
    public static ICommManager commManager;
    private static String currentEventName;
    private Tracker mTracker;
    private boolean firstBoatLoad = true;





    private void AddRaceCourse() {
        /**
         * args:
         * distance to mark 1
         * wind direction
         * signal boat location
         * course options
         * context
         *
         * all GoogleMapsActivity variables
         */
        mapLayer.removeAllMarks(); //TODO: Test
        raceCourse = new RaceCourse(context,  signalBoatLoc, windDirection, distance2mark1 , startLineDistance, selectedCourseOptions );
        buoys.addAll(raceCourse.convertMarks2Bouys());

        /*   //i don't get why is this part exist...
        if (raceCourse==null){
            raceCourse = new RaceCourse();
        }
        if (raceCourse.getMarks()!=null){ //TODO : Check why being removed twice!
            for(Bouy buoy2remove: raceCourse.getBouyList()){
                mapLayer.removeMark(buoy2remove.getUUID());
            }
        }
        if ((commManager!=null)&&(commManager.getAllBuoys()!=null)) {
            for (Buoy buoy2remove : commManager.getAllBuoys()) {
                if (buoy2remove.getRaceCourseUUID() != null) {
                    mapLayer.removeMark(buoy2remove.getUUID());
                }
            }
        }

        raceCourse = new RaceCourse(context,  signalBoatLoc, windDirection, distance2mark1 , startLineDistance, selectedCourseOptions );
        removeAllRaceCourseMarks();//TODO : Check why being removed twice!
        for (Buoy m: buoys) {
            addMark(m);
        }*/
    }

     private void removeAllRaceCourseMarks() {  //becomes unnecessary
        buoys = commManager.getAllBuoys();
        for(Buoy m: buoys){
            if (m.getRaceCourseUUID()!=null){
                mapLayer.removeMark(m.getUUID());
            }
        }
    }

    private Runnable runnable = new Runnable() {
        public void run() {
            if ((users.getCurrentUser()!=null)&&(commManager.getAllBoats()!=null)) {
                if (myBoat == null) {
                    for(Buoy ao: commManager.getAllBoats()){
                        if (ao.getName() == users.getCurrentUser().DisplayName)
                        {
                            myBoat=ao;
                            break;
                        }
                    }
                    if(myBoat==null){
                        myBoat = new Buoy(users.getCurrentUser().DisplayName, iGeo.getLoc(), "Blue");//TODO Set color properly
                    }
                    if (isCurrentEventManager(users.getCurrentUser().Uid)) {
                        myBoat.setBuoyType(BuoyType.RaceManager);
                    } else myBoat.setBuoyType(BuoyType.WorkerBoat);
                } else {
                    myBoat.setLoc(iGeo.getLoc());
                    myBoat.lastUpdate = new Date();
                }
                commManager.writeBoatObject(myBoat);
            }
            if (((OwnLocation)iGeo).isGPSFix()){
                findViewById(R.id.gps_indicator).setVisibility(View.INVISIBLE);
            }
            else
            {
                findViewById(R.id.gps_indicator).setVisibility(View.VISIBLE);
            }
            redrawLayers();
            handler.postDelayed(runnable, (Integer.parseInt(SP.getString("refreshRate", "1")) * 1000));
        }
    };

    public void redrawLayers() {
        Location myLocation = iGeo.getLoc();
        buoys = commManager.getAllBoats();
        for (Buoy o: buoys) {
            //TODO: Handle in case of user is logged out or when database does not contain current user.
            if ((o != null)&&(o.getLoc()!=null)&&(users.getCurrentUser()!=null)&&(!o.getName().equals(users.getCurrentUser().DisplayName/*SP.getString("username","Manager1")*/))) {
                int id = getIconId(users.getCurrentUser().DisplayName/*SP.getString("username","Manager1")*/,o);
                mapLayer.addMark(o, getDirDistTXT(myLocation,o.getLoc()), id);
            }
            if ((o != null)&&(o.getLoc()!=null)&&(users.getCurrentUser()!=null)&&(o.getName().equals(users.getCurrentUser().DisplayName/*SP.getString("username","Manager1")*/))) {
                int id = getIconId(users.getCurrentUser().DisplayName/*SP.getString("username","Manager1")*/,o);
                mapLayer.addMark(o, null, id);
            }
        }
        List<Buoy> commBuoyList = commManager.getAllBuoys();
        for (Buoy o : commBuoyList){
            //TODO: Delete old buoys first
            if(o.getBuoyType() ==BuoyType.FlagBuoy) {
                switch(o.color){
                    case "Red":
                        mapLayer.addMark(o,getDirDistTXT(myLocation, o.getLoc()),R.mipmap.flag_buoy_red);
                        break;
                    case "Blue":
                        mapLayer.addMark(o,getDirDistTXT(myLocation, o.getLoc()),R.mipmap.flag_buoy_blue);
                        break;
                    case "Yellow":
                        mapLayer.addMark(o,getDirDistTXT(myLocation, o.getLoc()),R.mipmap.flag_buoy_yellow);
                        break;
                    case "Orange":
                    default:
                        mapLayer.addMark(o,getDirDistTXT(myLocation, o.getLoc()),R.mipmap.flag_buoy_orange);
                        break;
                }
            }
            else if(o.getBuoyType() ==BuoyType.TomatoBuoy) {
                switch(o.color) {
                    case "Red":
                        mapLayer.addMark(o, getDirDistTXT(myLocation, o.getLoc()), R.mipmap.tomato_buoy_red);
                        break;
                    case "Blue":
                        mapLayer.addMark(o, getDirDistTXT(myLocation, o.getLoc()), R.mipmap.tomato_buoy_blue);
                        break;
                    case "Yellow":
                        mapLayer.addMark(o, getDirDistTXT(myLocation, o.getLoc()), R.mipmap.tomato_buoy_yellow);
                        break;
                    case "Orange":
                    default:
                        mapLayer.addMark(o, getDirDistTXT(myLocation, o.getLoc()), R.mipmap.tomato_buoy_orange);
                        break;
                }
            }
            else if(o.getBuoyType() ==BuoyType.TriangleBuoy) {
                switch(o.color) {
                    case "Red":
                        mapLayer.addMark(o, getDirDistTXT(myLocation, o.getLoc()), R.mipmap.triangle_buoy_red);
                        break;
                    case "Blue":
                        mapLayer.addMark(o, getDirDistTXT(myLocation, o.getLoc()), R.mipmap.triangle_buoy_blue);
                        break;
                    case "Yellow":
                        mapLayer.addMark(o, getDirDistTXT(myLocation, o.getLoc()), R.mipmap.triangle_buoy_yellow);
                        break;
                    case "Orange":
                    default:
                        mapLayer.addMark(o, getDirDistTXT(myLocation, o.getLoc()), R.mipmap.triangle_buoy_orange);
                        break;
                }
            }
            else
                mapLayer.addMark(o,getDirDistTXT(myLocation, o.getLoc()),R.drawable.buoyblack);

        }
        if ((buoys.size()>0)&&(firstBoatLoad)&&(mapLayer.mapView!=null))
        {
            firstBoatLoad = false;
            mapLayer.ZoomToMarks();
        }
    }

    private int getIconId(String string, Buoy o) {
        int ret = R.drawable.boatred;
        if (o.getName().equals(string)){
            switch(o.getBuoyType()) {
                case WorkerBoat:
                    ret = R.drawable.boatgold;
                    if (AviLocation.Age(o.getAviLocation())>300)
                        ret = R.drawable.boatred;
                    break;
                case RaceManager: ret = R.drawable.managergold;
                    break;
                default: ret = R.drawable.boatred;
            }
        }
        else {
            switch (o.getBuoyType()) {
                case WorkerBoat:
                    ret = R.drawable.boatcyan;
                    if (AviLocation.Age(o.getAviLocation())>300)
                        ret = R.drawable.boatred;
                    break;
                case RaceManager:
                    ret = R.drawable.managerblue;
                    break;
                default:
                    ret = R.drawable.boatred;
            }
        }
        return ret;
    }

    private void addMark(long id, Location loc, Float dir, int dist){
        if (loc == null) return;
        Buoy o =new Buoy("Buoy"+id, GeoUtils.getLocationFromDirDist(loc,dir,dist), "Black", BuoyType.Buoy);// TODO: 11/02/2016 Add bouy types
        o.id = id;
        addMark(o);
    }

    private void addMark(Buoy m){
        commManager.writeBuoyObject(m);
    }

    public List<Buoy> getBuoys() {
        return buoys;
    }
}
