package radial_design.racecoursedialogui;

import android.support.annotation.Nullable;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonathan on 22/08/2016.
 */
public class LegsType {
    private String name;
    @NotNull private List<String[]> options= new ArrayList<String[]>();; //{name, view to contain options, option1, option2, ...}
    public double[] courseFactors = {1,1,0};

    public LegsType (String name){
        this.name=name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
