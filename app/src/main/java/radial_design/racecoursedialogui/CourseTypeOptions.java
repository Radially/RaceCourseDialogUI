package radial_design.racecoursedialogui;

import java.util.List;
import java.util.Map;

/**
 * Created by Jonathan on 13/07/2016.
 */
public class CourseTypeOptions {
    private String name;
    private int imageID = R.drawable.racecourse_optimist;
    private List<String[]> options; //{name, view to contain options}, {option1, option2, ...}


    public CourseTypeOptions(String name, List<String[]> options){
        this.name=name;
        this.options=options;
    }
    public CourseTypeOptions(String name){
        this.name=name;
        this.options=null;
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
}
