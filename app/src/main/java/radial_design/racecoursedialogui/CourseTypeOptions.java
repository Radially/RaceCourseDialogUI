package radial_design.racecoursedialogui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jonathan on 13/07/2016.
 */
public class CourseTypeOptions {
    private String name;
    private int imageID = R.drawable.racecourse_optimist;
    private List<String[]> options; //{name, view to contain options, option1, option2, ...}
    private Map<String, Double> lengthFactorMap = new HashMap<String, Double>();  //the length of the first leg, divide by all leg length


    public CourseTypeOptions(String name, List<String[]> options) {
        lengthFactorMap.put("default", 0.5);
        this.name = name;
        this.options = options;
    }

    public CourseTypeOptions(String name, String[] optionsArray) {
        lengthFactorMap.put("default", 0.5);
        List<String[]> options = new ArrayList<>();
        options.add(optionsArray);
        this.name = name;
        this.options = options;
    }

    public CourseTypeOptions(String name) {
        lengthFactorMap.put("default", 0.5);
        this.name = name;
        this.options = null;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getImageID() {
        return imageID;
    }

    public List<String[]> getOptions() {
        return options;
    }

    /*public double getDistance(Map<String,String> selectedOptions, double avarageVMG, int targetTime){
        try{
             return lengthFactorMap.get(selectedOptions.get("Legs"))*avarageVMG/targetTime;
        }
        catch (Exception e){
            Log.w("CourseTypeOptions:" ,"no length factor");
            return 0.5;
        }
    }*/
}
