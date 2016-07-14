package radial_design.racecoursedialogui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by Jonathan on 14/07/2016.
 */
public class DistanceDialog extends Dialog {

    public DistanceDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        super.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(R.layout.distance_dialog);

        TextView titleV=(TextView) findViewById(R.id.distance_dialog_title);   //set dialog title
        titleV.setText("Choose Distance to Mark 1");
        titleV.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
    }
}
