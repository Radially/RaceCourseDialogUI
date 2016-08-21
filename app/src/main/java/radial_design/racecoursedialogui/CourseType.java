package radial_design.racecoursedialogui;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonathan on 13/07/2016.
 */
public class CourseType {
    private String name;
    private int imageID = R.drawable.racecourse_optimist;
    private List<String[]> options; //{name, view to contain options, option1, option2, ...}
    public double[] courseFactors = {1,1,0};


    public CourseType(String name, List<String[]> options) {
        this.name = name;
        this.options = options;
    }

    public CourseType(String name, String[] optionsArray) {
        List<String[]> options = new ArrayList<>();
        options.add(optionsArray);
        this.name = name;
        this.options = options;
    }

    public CourseType(String name) {
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

    public void setOptions(List<String[]> options) {
        this.options = options;
    }

    public void setCourseFactors(double[] courseFactors) {
        this.courseFactors = courseFactors;
    }

    public double[] getCourseFactors() {
        return courseFactors;
    }

    public void setCourseFactor(int index, double factor){
        if(index<courseFactors.length)courseFactors[index]=factor;
        else Log.w("CourseType class", "setCourseFactor: out of factors");

    }

}
