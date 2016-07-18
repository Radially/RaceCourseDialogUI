package radial_design.racecoursedialogui;

/**
 * Created by Jonathan on 18/07/2016.
 */
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HorizontalNumberPicker extends RelativeLayout {
    private String number = "";
    private String rightLabel = "";
    private TextView numberTV;
    private TextView rightTextView;
    private int leftStyle ;
    private int rightStyle;

    public HorizontalNumberPicker(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.horizontal_number_picker, this);
    }

    public HorizontalNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
    }

    public HorizontalNumberPicker(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
        initViews(context, attrs);
    }

    private void initViews(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.HorizontalNumberPicker, 0, 0);

        try {
            // get the text and colors specified using the names in attrs.xml

        } finally {
            a.recycle();
        }

        LayoutInflater.from(context).inflate(R.layout.horizontal_number_picker, this);

        numberTV = (TextView) this.findViewById(R.id.np_number);
        numberTV.setText("NA1");

        /*//left text view
        leftTextView = (TextView) this.findViewById(R.id.leftTextView);
        leftTextView.setText(leftLabel);
        leftTextView.setTextAppearance(context, leftStyle);

        //right text view
        rightTextView = (TextView) this.findViewById(R.id.rightTextView);
        rightTextView.setText(rightLabel);
        rightTextView.setTextAppearance(context, rightStyle);*/
    }

    public String getNumber() {
        return number;
    }

    public void setNumberTV(String text) {
        this.number = text;
        if(numberTV!=null){
            numberTV.setText(text);
        }
    }

    public String getRightLabel() {
        return rightLabel;
    }

    public void setRightLabel(String rightLabel) {
        this.rightLabel = rightLabel;
        if(rightTextView!=null){
            rightTextView.setText(rightLabel);
        }
    }
}