package com.ecoach.cosapp.RecycleAdapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.ecoach.cosapp.DataBase.Categories;
import com.ecoach.cosapp.DataBase.Recommendation;
import com.ecoach.cosapp.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by apple on 3/26/17.
 */

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapterViewHolder>  {
    private Context context;
    private LayoutInflater inflater;
    List<Recommendation> data= Collections.emptyList();
    View view=null;

    public RecommendationAdapter(Context context, List<Recommendation> data) {
        inflater= LayoutInflater.from(context);
        this.data=data;
        this.context=context;



    }

    @Override
    public RecommendationAdapterViewHolder onCreateViewHolder(ViewGroup
    parent, int viewType) {
        view = inflater.inflate(R.layout.recomendedviewcell, parent, false);
        // } else if(Application.BillersRecycleViewLayoutType.equalsIgnoreCase("List")){
        // view= inflater.inflate(R.layout.list_item_layout_billers,parent,false);}

        RecommendationAdapterViewHolder holder = new RecommendationAdapterViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecommendationAdapterViewHolder
    holder, int position) {

        final Recommendation items=data.get(position);


        holder.txthiddenId.setText(items.getCompanyCuid().toString());
        holder.labelTxt.setText(items.getCompanyName().toString());

        String avatorPath= items.getPath()+items.getCompanyStorageName()+"/"+items.getAvatarLocation();
        Picasso.with(context)
                .load(avatorPath)
                .placeholder(R.drawable.ic_no_image)
                .fit()
                .centerCrop()
                .into(holder.background);



        holder.ratingBar.setNumStars(5);
     holder.ratingBar.setRating((float) Float.parseFloat(items.getCompanyRating()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
    class RecommendationAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView txthiddenId,labelTxt;
        ImageView background;
        //RatingBar ratingBar;
        MaterialRatingBar ratingBar;
        CircleImageView iconView;

        public RecommendationAdapterViewHolder(View itemView) {
            super(itemView);

            txthiddenId=(TextView)itemView.findViewById(R.id.texthidden);
            background=(ImageView)itemView.findViewById(R.id.RecobackgroundImage);
            ratingBar=(MaterialRatingBar)itemView.findViewById(R.id.ratingBar) ;
            labelTxt=(TextView)itemView.findViewById(R.id.labelTxt);






        }
    }