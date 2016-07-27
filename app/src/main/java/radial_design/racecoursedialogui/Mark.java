package radial_design.racecoursedialogui;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Jonathan on 23/07/2016.
 */
public class Mark {
    private String name;
    private int direction=0; //direction from reference point. clockwise(usually minus, as a result);
    private double distance=0;
    private boolean distaneFactor=false;

    private ArrayList<Mark> referedMarks;

    private boolean isGatable=false;
    private String gateType="Bouy";  //TODO make it enum.
    private int gateDirection=(-90);
    private double gateDistance=0;

    public Mark (String name){
        this.name=name;
        referedMarks=new ArrayList<Mark>();
    }


    public void setName(String name) {
        if(name!=null)this.name = name;
        else Log.w("Mark Class insertion","null name set for Mark named:"+this.name);

    }
    public void setDirection(int direction) {
        this.direction = direction;
    }
    public void setDirection(String direction) {
        if(direction!=null)this.direction = Integer.parseInt(direction);
        else Log.w("Mark Class insertion","null direction set - Mark named:"+this.name);
    }
    public void setDistance(double distance) {
        this.distance = this.distance;
    }
    public void setDistance(String distance) {
        if(distance!=null)this.distance = Double.parseDouble(distance);
        else Log.w("Mark Class insertion","null distance set - Mark named:"+this.name);

    }
    public void setDistaneFactor(boolean distaneFactor) {
        this.distaneFactor = distaneFactor;
    }
    public void setDistaneFactor(String distaneFactor) {
        if(distaneFactor!=null)this.distaneFactor = distaneFactor.equals("true");
        else Log.w("Mark Class insertion","null distanceFactor set - Mark named:"+this.name);
    }
    public boolean addReferedMark(Mark referedMark){
        return referedMarks.add(referedMark);
    }
    public void setIsGatable(boolean isGatable) {
        this.isGatable = isGatable;
    }
    public void setIsGatable(String isGatable) {
        if(isGatable!=null)this.isGatable = isGatable.equals("true");
        else Log.w("Mark Class insertion","null isGatable set - Mark named:"+this.name);
    }
    public void setGateDirection(int gateDirection) {
        this.gateDirection = gateDirection;
    }
    public void setGateDirection(String gateDirection) {
        if(gateDirection!=null)this.gateDirection = Integer.parseInt(gateDirection);
        else Log.w("Mark Class insertion","null gateDirection set - Mark named:"+this.name);

    }
    public void setGateDistance(double gateDistance) {
        this.gateDistance = gateDistance;
    }
    public void setGateDistance(String gateDistance) {
        if(gateDistance!=null)this.gateDistance = Double.parseDouble(gateDistance);
        else Log.w("Mark Class insertion","null gateDistance set - Mark named:"+this.name);
    }
    public void setGateType(String gateType) {
        this.gateType = gateType;
    }
}
