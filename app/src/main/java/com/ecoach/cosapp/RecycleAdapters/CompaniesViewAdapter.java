package com.ecoach.cosapp.RecycleAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.ecoach.cosapp.DataBase.Companies;
import com.ecoach.cosapp.R;
import com.ecoach.cosapp.Utilities.ViewUtils;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

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


        Log.d("Avator",items.getAvatar());

//let finalPath = paths[indexPath.row] + "" + storages[indexPath.row] + "/" +  avators[indexPath.row]

        String avatorPath= items.getPath()+items.getStorage()+"/"+items.getAvatar();

        TextDrawable drawable = TextDrawable.builder()
                .buildRoundRect(items.getCompanyName().toString().substring(0,1), context.getResources().getColor(R.color.colorPrimary), 10); // radius in px

        Drawable d = new BitmapDrawable(ViewUtils.drawableToBitmap(drawable));

        Picasso.with(context)
                .load(avatorPath)
                .placeholder(d)
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
    MaterialRatingBar ratingBar;
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
        ratingBar=(MaterialRatingBar)itemView.findViewById(R.id.ratingBar2) ;
        chatButton=(Button)itemView.findViewById(R.id.chatbutton);





    }
}
