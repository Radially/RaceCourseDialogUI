package radial_design.racecoursedialogui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jonathan on 13/07/2016.
 */
public class CourseTypeRV extends RecyclerView.Adapter<CourseTypeRV.AdapterViewHolder>{
    public List<CourseTypeOptions> infoList;

    public CourseTypeRV(List<CourseTypeOptions> infoList){
        this.infoList=infoList;
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder{
        public TextView courseName;
        public ImageView courseImage;
        public AdapterViewHolder(View itemView){
            super(itemView);
            courseName= (TextView)itemView.findViewById(R.id.course_type_name);
            courseImage= (ImageView)itemView.findViewById(R.id.course_type_image);
        }
    }
    public void changeList(List<CourseTypeOptions> infoList){
        this.infoList.clear();
        this.infoList.addAll(infoList);
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_type_card, viewGroup, false);
        AdapterViewHolder vh = new AdapterViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder avh, int i) {        //on each message template
        avh.courseName.setText(infoList.get(i).getName());
        avh.courseImage.setBackgroundResource(infoList.get(i).getImageID());
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

}
