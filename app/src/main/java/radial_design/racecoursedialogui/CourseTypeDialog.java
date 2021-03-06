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
import java.util.Map;

/**
 * Created by Jonathan on 13/07/2016.
 */
public class CourseTypeDialog extends Dialog implements CourseTypeRV.OnRecyclerItemClickListener{


    private RecyclerView recyclerView;
    private CourseTypeRV adapter;
    private RecyclerView.LayoutManager layoutManager;

    private Context context;
    private OnMyDialogResult mDialogResult;
    private List<CourseType> coursesList;

    public CourseTypeDialog(Context context, List<CourseType> coursesList){
        super(context);
        this.context=context;
        this.coursesList=coursesList;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.setCancelable(true);

        super.setContentView(R.layout.course_type_dialog);

        recyclerView=(RecyclerView)findViewById(R.id.course_type_rv);
        recyclerView.setHasFixedSize(true);
        layoutManager= new GridLayoutManager(context,2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter= new CourseTypeRV(coursesList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRecyclerItemClick(CourseType courseType){
        CourseTypeSecondDialog dialog = new CourseTypeSecondDialog(context,courseType);
        dialog.show();
        dialog.setDialogResult(new CourseTypeSecondDialog.OnMyDialogResult() {
            public void finish(Map<String, String> result) {
                mDialogResult.finish(result);
            }
        });
        dismiss();
    }

    public void setDialogResult(OnMyDialogResult dialogResult){
        mDialogResult = dialogResult;
    }

    public interface OnMyDialogResult{
        void finish(Map<String, String> result);
    }

}
