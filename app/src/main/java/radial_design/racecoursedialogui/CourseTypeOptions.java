package radial_design.racecoursedialogui;

/**
 * Created by Jonathan on 13/07/2016.
 */
public class CourseTypeOptions {
    private String name;
    private int imageID = R.drawable.racecourse_optimist;
    public CourseTypeOptions(String name){
        this.name=name;
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
}
