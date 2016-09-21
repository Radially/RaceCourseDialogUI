package radial_design.racecoursedialogui;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonathan on 23/07/2016.
 */
public class Mark {
    private String name;
    private int direction = 0; //direction from reference point. clockwise(usually minus, as a result);
    private double distance = 0;
    private boolean distanceFactor = false;

    public ArrayList<Mark> referedMarks;

    private boolean isGatable = false;
    private String gateType = "Buoy";  //TODO make it enum.
    private int gateDirection = (-90);
    private double gateDistance = 0;

    public Mark(String name) {
        this.name = name;
        referedMarks = new ArrayList<Mark>();
    }


    public void setName(String name) {
        if (name != null) this.name = name;
        else Log.w("Mark Class insertion", "null name set for Mark named:" + this.name);
    }

    public String getName() {
        return name;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setDirection(String direction) {
        if (direction != null) this.direction = Integer.parseInt(direction);
        else Log.w("Mark Class insertion", "null direction set - Mark named:" + this.name);
    }

    public int getDirection() {
        return direction;
    }

    public void setDistance(double distance) {
        this.distance = this.distance;
    }

    public void setDistance(String distance) {
        if (distance != null) this.distance = Double.parseDouble(distance);
        else Log.w("Mark Class insertion", "null distance set - Mark named:" + this.name);
    }

    public double getDistance() {
        return distance;
    }

    public void setDistanceFactor(boolean distanceFactor) {
        this.distanceFactor = distanceFactor;
    }

    public double getAbsDistance(double multiplication) {
        if (distanceFactor) return multiplication * getDistance();
        return getDistance();
    }

    public void setDistaneFactor(String distaneFactor) {
        if (distaneFactor != null)
            this.distanceFactor = distaneFactor.equals("true") || distaneFactor.equals("always");
        else Log.w("Mark Class insertion", "null distanceFactor set - Mark named:" + this.name);
    }

    public boolean addReferedMark(Mark referedMark) {
        return referedMarks.add(referedMark);
    }

    public void setIsGatable(boolean isGatable) {
        this.isGatable = isGatable;
    }

    public ArrayList<Mark> getReferedMarks() {
        return referedMarks;
    }

    public void setIsGatable(String isGatable) {
        if (isGatable != null)
            this.isGatable = isGatable.equals("true") || isGatable.equals("always");
        else Log.w("Mark Class insertion", "null isGatable set - Mark named:" + this.name);
    }

    public void setGateDirection(int gateDirection) {
        this.gateDirection = gateDirection;
    }

    public void setGateDirection(String gateDirection) {
        if (gateDirection != null) this.gateDirection = Integer.parseInt(gateDirection);
        else Log.w("Mark Class insertion", "null gateDirection set - Mark named:" + this.name);

    }

    public void setGateDistance(double gateDistance) {
        this.gateDistance = gateDistance;
    }

    public void setGateDistance(String gateDistance) {
        if (gateDistance != null) this.gateDistance = Double.parseDouble(gateDistance);
        else Log.w("Mark Class insertion", "null gateDistance set - Mark named:" + this.name);
    }

    public void setGateType(String gateType) {
        this.gateType = gateType;
    }

    private List<Buoy> parseBouys(AviLocation referencePoint, double multiplication) {
        List<Buoy> buoys = new ArrayList<>();
        AviLocation location =new AviLocation(referencePoint, getDirection(), getAbsDistance(multiplication));
        switch (this.gateType){
            case "Buoy":  //adds a single buoy
                buoys.add(new Buoy(this.getName(), location));
                break;
            case "Gate":
                break;
            case "FinishLine":
                break;
            case "StartLine":
                break;
            case "Satellite":
                break;
            default:
        }
        //parseChildren
        for(int i=0; i<this.getReferedMarks().size(); i++ ){
            Mark child = this.getReferedMarks().get(i);
            buoys.addAll(child.parseBouys(location, multiplication));
        }
        return buoys;
    }

}
