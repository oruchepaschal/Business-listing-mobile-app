package com.ecoach.cosapp.RecycleAdapters;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.ecoach.cosapp.DataBase.VerifiedCompanies;
import com.ecoach.cosapp.R;
import com.ecoach.cosapp.Utilities.Utility;
import com.ecoach.cosapp.Utilities.ViewUtils;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.text.WordUtils;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by apple on 4/6/17.
 */

public class MyCompaniesAdapter extends RecyclerView.Adapter<MyCompaniesAdapterViewHolder>  {
    private Context context;
    private LayoutInflater inflater;
    List<VerifiedCompanies> data= Collections.emptyList();
    View view=null;

    public MyCompaniesAdapter(Context context, List<VerifiedCompanies> data) {
        inflater= LayoutInflater.from(context);
        this.data=data;
        this.context=context;



    }

    @Override
    public MyCompaniesAdapterViewHolder onCreateViewHolder(ViewGroup
                                                                     parent, int viewType) {
        view = inflater.inflate(R.layout.mycompanies_viewcell, parent, false);
        // } else if(Application.BillersRecycleViewLayoutType.equalsIgnoreCase("List")){
        // view= inflater.inflate(R.layout.list_item_layout_billers,parent,false);}

        MyCompaniesAdapterViewHolder holder = new MyCompaniesAdapterViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyCompaniesAdapterViewHolder
                                         holder, int position) {

        final VerifiedCompanies items=data.get(position);


        holder.txthiddenId.setText(items.getCompanyCuid());
        holder.companyName.setText(items.getCompanyName().toString());
        holder.companyCategory.setText(items.getCompanyCategory().toString());
        holder.acoountHolderType.setText(WordUtils.capitalizeFully(items.getAccountType().toString()));

        Log.d("Avator",items.getAvatarLocation());

//let finalPath = paths[indexPath.row] + "" + storages[indexPath.row] + "/" +  avators[indexPath.row]

        String avatorPath= items.getPath()+items.getCompanyStorageName()+"/"+items.getAvatarLocation();



        TextDrawable drawable = TextDrawable.builder()
                .buildRoundRect(Utility.getInitialsFromString(items.getCompanyName().toString()), context.getResources().getColor(R.color.colorPrimary), 10); // radius in px

        Drawable d = new BitmapDrawable(ViewUtils.drawableToBitmap(drawable));

        Picasso.with(context)
                .load(avatorPath)
                .placeholder(d)
                .into(holder.iconView);




    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
class MyCompaniesAdapterViewHolder extends RecyclerView.ViewHolder {
    TextView txthiddenId,companyName,companyCategory,acoountHolderType;

    CircleImageView iconView;

    public MyCompaniesAdapterViewHolder(View itemView) {
        super(itemView);

        /**  txthiddenId=(TextView)itemView.findViewById(R.id.texthidden);
         background=(ImageView)itemView.findViewById(R.id.RecobackgroundImage);
         ratingBar=(RatingBar)itemView.findViewById(R.id.ratingBar) ;
         labelTxt=(TextView)itemView.findViewById(R.id.labelTxt);
         **/

        txthiddenId=(TextView)itemView.findViewById(R.id.companyid);
        iconView=(CircleImageView)itemView.findViewById(R.id.mycompanyLogo);
        companyName = (TextView) itemView.findViewById(R.id.rep_companyName);
        companyCategory=(TextView)itemView.findViewById(R.id.companyCategory) ;
        acoountHolderType=(TextView)itemView.findViewById(R.id.acoountHolderType);





    }
}

