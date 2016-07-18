package radial_design.racecoursedialogui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * Created by Jonathan on 14/07/2016.
 */
public class DistanceDialog extends Dialog {

    public Button finishB;
    private Context context;
    private LinearLayout ownLayout;

    public DistanceDialog(Context context) {
        super(context);
        this.context=context;
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

        TabHost tabHost=(TabHost)findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec spec=tabHost.newTabSpec("Distance");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Distance");
        tabHost.addTab(spec);

        spec=tabHost.newTabSpec("Class & Wind");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Class & Wind");
        tabHost.addTab(spec);

        finishB = (Button)findViewById(R.id.distance_finish_b);
        finishB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
