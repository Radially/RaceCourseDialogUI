package radial_design.racecoursedialogui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jonathan on 13/07/2016.
 */
public class CourseTypeSecondDialog extends Dialog {
    private Context context;
    private LinearLayout ownLayout;
    private List<String[]> options; //{name, view to contain options}, {option1, option2, ...}
    private OnMyDialogResult mDialogResult;
    private String courseName;


    public CourseTypeSecondDialog(Context context, String courseName ,List<String[]> options) {
        super(context);
        this.context = context;
        this.options = options;
        this.courseName=courseName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //super.setTitle("Choose Course Options");
        super.setContentView(R.layout.course_type_second_dialog);
        ownLayout = (LinearLayout) findViewById(R.id.course_type_second_dialog);
        if (options != null) {
            for (int c = 0; c < options.size(); c++) {
                TextView textView = new TextView(context);
                textView.setText(options.get(c)[0]);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView.setTextSize(25);
                ownLayout.addView(textView);
                View setterBox = null;
                switch (options.get(c)[1]) {
                    case "spinner":
                        Spinner dropdown = new Spinner(context);
                        String[] items = Arrays.copyOfRange(options.get(c), 2, options.get(c).length);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_layout, items);
                        dropdown.setAdapter(adapter);
                        dropdown.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        setterBox=dropdown;
                        break;
                    case "toggle":
                        setterBox = new ToggleButton(context);
                        break;
                }
                if (setterBox != null) ownLayout.addView(setterBox);
            }
        }
        Button finishB = new Button(context);
        finishB.setText("Done");
        finishB.setTextSize(25);
        finishB.setTextColor(context.getResources().getColor(R.color.cmark_orange_lighter));
        finishB.setBackgroundResource(R.color.cmark_blue_light);
        finishB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> selectedOptions = new HashMap<String, String>();  //map of the selected settings
                selectedOptions.put("type",courseName);
                for (int i = 0; i < ownLayout.getChildCount()-1; i=i+2) {
                    TextView tv=(TextView)ownLayout.getChildAt(i);
                    switch (ownLayout.getChildAt(i+1).getClass().toString()) {
                        case "class android.widget.Spinner":
                            Toast.makeText(context, "we can work from home! oooh o-oh", Toast.LENGTH_LONG).show();
                            Spinner spinner=(Spinner)ownLayout.getChildAt(i+1);
                            selectedOptions.put(tv.getText().toString(),spinner.getSelectedItem().toString());
                            break;
                        case "class android.widget.ToggleButton":
                            ToggleButton toggleButton=(ToggleButton)ownLayout.getChildAt(i+1);
                            if(toggleButton.isChecked())selectedOptions.put(tv.getText().toString(), "true");
                            else selectedOptions.put(tv.getText().toString(), "false");
                            break;
                    }
                }
                Log.w("check", selectedOptions.values().toString());
                mDialogResult.finish(selectedOptions);
                dismiss();
            }
        });
        ownLayout.addView(finishB);
    }

    public void setDialogResult(OnMyDialogResult dialogResult){
        mDialogResult = dialogResult;
    }

    public interface OnMyDialogResult{
        void finish(Map<String, String> result);
    }

}
