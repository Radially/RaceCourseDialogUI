package radial_design.racecoursedialogui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Jonathan on 14/07/2016.
 */
public class DistanceDialog extends Dialog {


    private Context context;
    private LinearLayout ownLayout;
    private OnMyDialogResult mDialogResult;
    private TabHost tabHost;

    private Spinner classSpinner;
    private HorizontalNumberPicker windPicker;
    private HorizontalNumberPicker targetTimePicker;
    private HorizontalNumberPicker distancePicker;
    public Button finishB;

    private List<MainActivity.BoatType> boats;

    public DistanceDialog(Context context, MainActivity.BoatType[] boats) {
        super(context);
        this.context=context;
        this.boats= Arrays.asList(boats);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        super.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(R.layout.distance_dialog);
        ownLayout = (LinearLayout) findViewById(R.id.distance_dialog);

        TextView titleV=(TextView) findViewById(R.id.distance_dialog_title);   //set dialog title
        titleV.setText("Choose Distance to Mark 1");
        titleV.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        tabHost =(TabHost)findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec spec=tabHost.newTabSpec("Distance");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Distance");
        tabHost.addTab(spec);

        spec=tabHost.newTabSpec("Class & Wind");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Class & Wind");
        tabHost.addTab(spec);

        windPicker = (HorizontalNumberPicker)findViewById(R.id.wind_picker);
        windPicker.configNumbers(15, 2);
        targetTimePicker = (HorizontalNumberPicker)findViewById(R.id.target_time_picker);
        targetTimePicker.configNumbers(60,5);
        distancePicker = (HorizontalNumberPicker)findViewById(R.id.distance_length_picker);
        distancePicker.configNumbers(1.0,0.1);
        classSpinner = (Spinner) findViewById(R.id.distance_class_spinner);
        String[] items = new String[boats.size()];
        for(int i=0; i<boats.size();i++){
            items[i]=boats.get(i).name();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_layout, items);
        classSpinner.setAdapter(adapter);

        finishB = (Button)findViewById(R.id.distance_finish_b);
        finishB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (tabHost.getCurrentTab()){
                    case 0:
                        mDialogResult.finish(distancePicker.getNumber());
                        break;
                    case 1:
                        MainActivity.BoatType selectedB = boats.get(classSpinner.getSelectedItemPosition());
                        Toast.makeText(context, "Have you chosen "+selectedB.name()+ "?", Toast.LENGTH_SHORT).show();
                        mDialogResult.finish(new Object[]{selectedB, windPicker.getNumber(), targetTimePicker.getNumber()});
                        break;
                }
                dismiss();
            }
        });
    }

    public void setDialogResult(OnMyDialogResult dialogResult){
        mDialogResult = dialogResult;
    }

    public interface OnMyDialogResult{
        void finish(Object result);
    }
}
