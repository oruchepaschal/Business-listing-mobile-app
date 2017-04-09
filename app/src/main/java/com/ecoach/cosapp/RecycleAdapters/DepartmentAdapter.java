package com.ecoach.cosapp.RecycleAdapters;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.ecoach.cosapp.DataBase.Departments;
import com.ecoach.cosapp.R;
import com.ecoach.cosapp.Utilities.ViewUtils;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by apple on 4/8/17.
 */

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapterViewHolder>  {
    private Context context;
    private LayoutInflater inflater;
    List<Departments> data= Collections.emptyList();
    View view=null;

    public DepartmentAdapter(Context context, List<Departments> data) {
        inflater= LayoutInflater.from(context);
        this.data=data;
        this.context=context;



    }

    @Override
    public DepartmentAdapterViewHolder onCreateViewHolder(ViewGroup
                                                                     parent, int viewType) {
        view = inflater.inflate(R.layout.departments_view_cell, parent, false);
        // } else if(Application.BillersRecycleViewLayoutType.equalsIgnoreCase("List")){
        // view= inflater.inflate(R.layout.list_item_layout_billers,parent,false);}

        DepartmentAdapterViewHolder holder = new DepartmentAdapterViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(DepartmentAdapterViewHolder
                                         holder, int position) {

        final Departments items=data.get(position);


        holder.txthiddenId.setText(items.getDepartmentid());
        holder.departmentName.setText(items.getDepartmentname().toString());



    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
class DepartmentAdapterViewHolder extends RecyclerView.ViewHolder {
    TextView txthiddenId,departmentName;
    Button morebutton;

    public DepartmentAdapterViewHolder(View itemView) {
        super(itemView);


        txthiddenId=(TextView)itemView.findViewById(R.id.hiddenID);
        departmentName = (TextView) itemView.findViewById(R.id.departmentName);
        morebutton=(Button)itemView.findViewById(R.id.morebutton);





    }
}

