package com.ecoach.cosapp.RecycleAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ecoach.cosapp.DataBase.Categories;
import com.ecoach.cosapp.DataBase.Companies;
import com.ecoach.cosapp.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by apple on 3/28/17.
 */

public class CompaniesViewAdapter extends RecyclerView.Adapter<CompaniesViewAdapterViewHolder>  {
    private Context context;
    private LayoutInflater inflater;
    List<Companies> data= Collections.emptyList();
    View view=null;

    public CompaniesViewAdapter(Context context, List<Companies> data) {
        inflater= LayoutInflater.from(context);
        this.data=data;
        this.context=context;



    }

    @Override
    public CompaniesViewAdapterViewHolder onCreateViewHolder(ViewGroup
                                                                      parent, int viewType) {
        view = inflater.inflate(R.layout.companies_viewcell, parent, false);
        // } else if(Application.BillersRecycleViewLayoutType.equalsIgnoreCase("List")){
        // view= inflater.inflate(R.layout.list_item_layout_billers,parent,false);}

        CompaniesViewAdapterViewHolder holder = new CompaniesViewAdapterViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(CompaniesViewAdapterViewHolder
                                         holder, int position) {

        final Companies items=data.get(position);


        holder.txthiddenId.setText(items.getCompany_id());
        holder.labelTxt.setText(items.getCompanyName().toString());


        Picasso.with(context)
                .load(items.getAvatar())
                .placeholder(R.drawable.ic_no_image)
                .into(holder.iconView);



        holder.ratingBar.setNumStars(5);
        holder.ratingBar.setRating((float)Double.parseDouble(items.getRating()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
class CompaniesViewAdapterViewHolder extends RecyclerView.ViewHolder {
    TextView txthiddenId,labelTxt;
    ImageView background;
    RatingBar ratingBar;
    Button chatButton;
    CircleImageView iconView;

    public CompaniesViewAdapterViewHolder(View itemView) {
        super(itemView);

      /**  txthiddenId=(TextView)itemView.findViewById(R.id.texthidden);
        background=(ImageView)itemView.findViewById(R.id.RecobackgroundImage);
        ratingBar=(RatingBar)itemView.findViewById(R.id.ratingBar) ;
        labelTxt=(TextView)itemView.findViewById(R.id.labelTxt);
        **/

        txthiddenId=(TextView)itemView.findViewById(R.id.companyid);
        iconView=(CircleImageView)itemView.findViewById(R.id.iconview);
        labelTxt = (TextView) itemView.findViewById(R.id.labelTxt);
        ratingBar=(RatingBar)itemView.findViewById(R.id.ratingBar2) ;
        chatButton=(Button)itemView.findViewById(R.id.chatbutton);





    }
}
