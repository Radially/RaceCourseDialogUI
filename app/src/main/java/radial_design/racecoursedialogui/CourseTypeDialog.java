package radial_design.racecoursedialogui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.List;

/**
 * Created by Jonathan on 13/07/2016.
 */
public class CourseTypeDialog extends Dialog implements View.OnClickListener{


    private RecyclerView recyclerView;
    private CourseTypeRV adapter;
    private RecyclerView.LayoutManager layoutManager;

    private Context context;
    private OnMyDialogResult mDialogResult;
    private List<CourseTypeOptions> coursesList;

    public CourseTypeDialog(Context context, List<CourseTypeOptions> coursesList){
        super(context);
        this.context=context;
        this.coursesList=coursesList;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.course_type_dialog);

        recyclerView=(RecyclerView)findViewById(R.id.course_type_rv);
        recyclerView.setHasFixedSize(true);
        layoutManager= new GridLayoutManager(context,2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter= new CourseTypeRV(coursesList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v){

    }

    public void setDialogResult(OnMyDialogResult dialogResult){
        mDialogResult = dialogResult;
    }

    public interface OnMyDialogResult{
        void finish(String result);
    }
}
