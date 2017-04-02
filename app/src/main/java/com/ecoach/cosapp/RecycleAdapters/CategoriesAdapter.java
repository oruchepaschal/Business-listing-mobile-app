
package com.ecoach.cosapp.RecycleAdapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.ecoach.cosapp.DataBase.Categories;
import com.ecoach.cosapp.R;
import com.ecoach.cosapp.Utilities.ViewUtils;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.ecoach.cosapp.Utilities.ViewUtils.drawableToBitmap;


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapterViewHolder>  {
    private Context context;
    private LayoutInflater inflater;
    List<Categories> data= Collections.emptyList();
    View view=null;

    public CategoriesAdapter(Context context, List<Categories> data) {
        inflater= LayoutInflater.from(context);
        this.data=data;
        this.context=context;



    }

    @Override
    public CategoriesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.categoryviewcell, parent, false);
        // } else if(Application.BillersRecycleViewLayoutType.equalsIgnoreCase("List")){
        // view= inflater.inflate(R.layout.list_item_layout_billers,parent,false);}

        CategoriesAdapterViewHolder holder = new CategoriesAdapterViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(CategoriesAdapterViewHolder holder, int position) {

        final Categories items=data.get(position);


        holder.txthiddenId.setText(items.getCategoryID().toString());
        holder.txtLabel.setText(items.getCategoryNames().toString());


            Picasso.with(context)
                    .load(items.getCategoryBackgroundImage())
                    .placeholder(R.drawable.ic_no_image)
                    .centerCrop()
                    .into(holder.background);


        TextDrawable drawable = TextDrawable.builder()
                .buildRoundRect(items.getCategoryNames().toString().substring(0, 1),context.getResources().getColor(R.color.colorPrimary), 10);

        Drawable d = new BitmapDrawable(ViewUtils.drawableToBitmap(drawable));


//holder.iconView.setImageResource(R.drawable.ic_no_image);
        Picasso.with(context)
             .load(items.getCategoryBackgroundImage())
               .placeholder(d)
               .centerCrop()
               .into(holder.iconView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
class CategoriesAdapterViewHolder extends RecyclerView.ViewHolder {
    TextView txthiddenId,txtLabel;
    ImageView background;
    CircleImageView iconView;

    public CategoriesAdapterViewHolder(View itemView) {
        super(itemView);

        txthiddenId=(TextView)itemView.findViewById(R.id.hiddenID);
        background=(ImageView)itemView.findViewById(R.id.catbackgroundImage);
        iconView = (CircleImageView)itemView.findViewById(R.id.iconImage);
        txtLabel=(TextView)itemView.findViewById(R.id.labelTxt);






    }
}
