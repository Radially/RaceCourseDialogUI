package radial_design.racecoursedialogui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Jonathan on 13/07/2016.
 */
public class CourseTypeSecondDialog extends Dialog {
    private Context context;
    private LinearLayout ownLayout;
    private List<String[]> options; //{name, view to contain options}, {option1, option2, ...}

    public CourseTypeSecondDialog(Context context, List<String[]> options) {
        super(context);
        this.context = context;
        this.options = options;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTitle("Choose Race Course Options");
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
        finishB.setTextColor(context.getResources().getColor(R.color.nonscense_done_text));
        finishB.setBackgroundResource(R.color.nonscense_done);
        finishB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
        ownLayout.addView(finishB);

    }

}
