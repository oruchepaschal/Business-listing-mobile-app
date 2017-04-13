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
import com.ecoach.cosapp.DataBase.CompanyRepInvite;
import com.ecoach.cosapp.R;
import com.ecoach.cosapp.Utilities.ViewUtils;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.text.WordUtils;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import info.hoang8f.widget.FButton;

/**
 * Created by apple on 4/10/17.
 */

public class RepInviteAdapter extends RecyclerView.Adapter<RepInviteAdapterViewHolder>  {
    private Context context;
    private LayoutInflater inflater;
    List<CompanyRepInvite> data= Collections.emptyList();
    View view=null;

    public RepInviteAdapter(Context context, List<CompanyRepInvite> data) {
        inflater= LayoutInflater.from(context);
        this.data=data;
        this.context=context;



    }

    @Override
    public RepInviteAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.rep_invite, parent, false);
        // } else if(Application.BillersRecycleViewLayoutType.equalsIgnoreCase("List")){
        // view= inflater.inflate(R.layout.list_item_layout_billers,parent,false);}

        RepInviteAdapterViewHolder holder = new RepInviteAdapterViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RepInviteAdapterViewHolder holder, int position) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy.MMM.dd hh:mm aaa");


        final CompanyRepInvite items=data.get(position);
       // String fechaStr = items.getRequest_date();
       // String fechaNueva = DateFormat
        //2017-04-10 10:17:50
        try {
          //   fechaNueva = format.parse(fechaNueva.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }




        holder.repInvite.setText(items.getRequest_id());
        holder.companyName.setText(items.getCompany_name().toString());
        holder.companyDepartment.setText(WordUtils.capitalizeFully(items.getDepartment().toString()) );
        holder.timeStamp.setText(items.getRequest_date());

        TextDrawable drawable = TextDrawable.builder()
                .buildRoundRect(items.getCompany_name().toString().substring(0, 1),context.getResources().getColor(R.color.colorPrimary), 10);

        Drawable d = new BitmapDrawable(ViewUtils.drawableToBitmap(drawable));




        String avatorPath= items.getPath()+items.getStorage()+"/"+items.getAvatar();
        Picasso.with(context)
                .load(avatorPath)
                .placeholder(d)
                .fit()
                .centerCrop()
                .into(holder.iconAvatar);



        Log.d("avatorPath",avatorPath);




    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
class RepInviteAdapterViewHolder extends RecyclerView.ViewHolder {
    TextView companyName,companyDepartment,timeStamp,repInvite;
   FButton accept,reject;
    CircleImageView iconAvatar;

    public RepInviteAdapterViewHolder(View itemView) {
        super(itemView);

        companyName=(TextView)itemView.findViewById(R.id.rep_companyName);
        companyDepartment=(TextView)itemView.findViewById(R.id.company_department);
        timeStamp=(TextView)itemView.findViewById(R.id.company_date);
        accept=(FButton) itemView.findViewById(R.id.acceptButton);
        reject=(FButton) itemView.findViewById(R.id.rejectButton);
        iconAvatar = (CircleImageView)itemView.findViewById(R.id.avator);
        repInvite=(TextView)itemView.findViewById(R.id.repInvite);






    }
}
