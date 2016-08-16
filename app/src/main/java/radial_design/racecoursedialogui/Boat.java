package radial_design.racecoursedialogui;

/**
 * Created by Jonathan on 16/08/2016.
 */
public class Boat {
    private String name;
    private int targetTime;
    private double[][] vmg = new double[4][3]; //[upwind,downwind,reach][5+,8+,12+,15+]

    public Boat (String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(int targetTime) {
        this.targetTime = targetTime;
    }

    public double[][] getVmg() {
        return vmg;
    }
    public void setVmg(double[][] vmg) {
        this.vmg = vmg;
    }
}

