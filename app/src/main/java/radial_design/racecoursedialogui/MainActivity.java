package radial_design.racecoursedialogui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public Context context;
    public Button dialogOpenner;
    private List<CourseTypeOptions> coursesInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        context=this;
        coursesInfo = new ArrayList<CourseTypeOptions>();
        List<String[]> options = new ArrayList<>();
        String[] a = {"Legs","spinner", "Shorted Outer", "reach is 1/2 beat", "reach is 2/3 beat" };
        options.add(a);
        String[] b = {"4 Gate","toggle"};
        options.add(b);
        coursesInfo.add(new CourseTypeOptions("Trapezoid 60,120",options));
        coursesInfo.add(new CourseTypeOptions("Trapezoid 70,110"));
        coursesInfo.add(new CourseTypeOptions("Laser"));
        coursesInfo.add(new CourseTypeOptions("Windward-Leeward"));
        coursesInfo.add(new CourseTypeOptions("Optimist"));


        dialogOpenner =(Button)findViewById(R.id.open_dialog_button);
        dialogOpenner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseTypeDialog dialog = new CourseTypeDialog(context, coursesInfo);
                dialog.show();
                dialog.setDialogResult(new CourseTypeDialog.OnMyDialogResult() {
                    public void finish(String result) {
                        //something to do
                    }
                });
            }
        });
    }
}
