package radial_design.racecoursedialogui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public Context context;
    public Button dialogOpenner;
    public Button distanceDialogB;
    private List<CourseTypeOptions> coursesInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        context=this;
        coursesInfo = new ArrayList<CourseTypeOptions>();
        //since coursesInfo is sent from the server, but we still don't have this system:
        //------------------from here -information sent by the server------
        List<String[]> options = new ArrayList<>();
        String[] legs = {"Legs","spinner", "Shorted Outer", "reach is 1/2 beat", "reach is 2/3 beat" };
        options.add(legs);
        String[] gate3 = {"3 Gate","toggle"};
        options.add(gate3);
        String[] gate4 = {"4 Gate","toggle"};
        options.add(gate4);
        coursesInfo.add(new CourseTypeOptions("Trapezoid 60,120", options));
        coursesInfo.add(new CourseTypeOptions("Trapezoid 70,110", options));
        String[] laser = {"Course Option","spinner","Normal","A-Large Fleets","B-Huge Fleets","C-Small Fleets","D-Narrow Waters"};
        coursesInfo.add(new CourseTypeOptions("Laser", laser));
        String[] windLee = {"Finish line location","spinner","W-Windward","L-Leeward","WR-Windward Right","WG-Windward Left","LR-Leeward Right","LG-Windward Left"};
        coursesInfo.add(new CourseTypeOptions("Windward-Leeward",windLee));
        coursesInfo.add(new CourseTypeOptions("Optimist"));
        //------------------until here -information sent by the server------

        dialogOpenner =(Button)findViewById(R.id.open_dialog_button);
        dialogOpenner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseTypeDialog dialog = new CourseTypeDialog(context, coursesInfo);
                dialog.show();
                dialog.setDialogResult(new CourseTypeDialog.OnMyDialogResult() {
                    public void finish(Map<String, String> result) {
                        //something to do
                        //use the map of the selected race curse options
                    }
                });
            }
        });

        distanceDialogB =(Button)findViewById(R.id.distance_dialog_b);
        distanceDialogB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DistanceDialog dialog = new DistanceDialog(context);
                dialog.show();
                /*dialog.setDialogResult(new CourseTypeDialog.OnMyDialogResult() {
                    public void finish(Map<String, String> result) {
                        //something to do
                        //use the map of the selected race curse options
                    }
                });*/
            }
        });
    }
}
