package radial_design.racecoursedialogui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public Context context;
    public Button dialogOpenner;
    public Button distanceDialogB;
    public CourseXmlParser xmlParserC;
    public BoatXmlParser boatXmlParser;
    private List<CourseType> coursesInfo;
    private List<Boat> boats;

    private Map<String, String> selectedOptions;
    private double raceCourseDistance;
    private Mark raceCourseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context=this;
        xmlParserC = new CourseXmlParser(this, "courses_file.xml");
        boatXmlParser = new BoatXmlParser(this, "boats_file.xml");
        coursesInfo = xmlParserC.parseCourseTypes();
        boats=boatXmlParser.parseBoats();

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
                        selectedOptions=result;
                        raceCourseReference=xmlParserC.parseMarks(selectedOptions);
                    }
                });
            }
        });

        distanceDialogB =(Button)findViewById(R.id.distance_dialog_b);
        distanceDialogB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DistanceDialog dialog = new DistanceDialog(context, boats);
                dialog.show();
                dialog.setDialogResult(new DistanceDialog.OnMyDialogResult() {
                    public void finish(double result) {
                        //something to do
                        raceCourseDistance = result;
                        }

                });
            }
        });
    }
}
