package radial_design.racecoursedialogui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public Context context;
    public Button dialogOpenner;
    public Button distanceDialogB;
    public CourseXmlParser xmlParserC;
    public BoatXmlParser boatXmlParser;
    private List<CourseTypeOptions> coursesInfo;
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
        coursesInfo = new ArrayList<CourseTypeOptions>();


        List<String> names=xmlParserC.parseCourseNames();
        boats = boatXmlParser.parseBoats();
        for (int i=0; i<names.size();i++){
            coursesInfo.add(new CourseTypeOptions(names.get(i), xmlParserC.parseCourseOptions(names.get(i))));
        }

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
                        raceCourseReference=xmlParserC.parseXml(selectedOptions);
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
                    public void finish(Object result) {
                        //something to do
                        Toast.makeText(context, result.getClass().toString(), Toast.LENGTH_SHORT).show();
                        switch (result.getClass().toString()){
                            case "class java.lang.Double":
                                raceCourseDistance = (double)result;
                                Toast.makeText(context, raceCourseDistance+", Straightforward ha?", Toast.LENGTH_SHORT).show();
                                break;
                            case "class [Ljava.lang.Object;":
                                break;
                        }
                    }
                });
            }
        });
    }
}
