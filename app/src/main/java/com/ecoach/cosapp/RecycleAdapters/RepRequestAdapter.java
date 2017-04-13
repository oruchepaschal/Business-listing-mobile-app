package com.ecoach.cosapp.RecycleAdapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ecoach.cosapp.DataBase.RepInvites;
import com.ecoach.cosapp.R;

import org.apache.commons.lang3.text.WordUtils;

import java.util.Collections;
import java.util.List;

import info.hoang8f.widget.FButton;

/**
 * Created by apple on 4/11/17.
 */

public class RepRequestAdapter extends RecyclerView.Adapter<RepRequestAdapterViewHolder>  {
    private Context context;
    private LayoutInflater inflater;
    List<RepInvites> data= Collections.emptyList();
    View view=null;
    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);


    public RepRequestAdapter(Context context, List<RepInvites> data) {
        inflater= LayoutInflater.from(context);
        this.data=data;
        this.context=context;



    }

    @Override
    public RepRequestAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.rep_pending_invites, parent, false);
        // } else if(Application.BillersRecycleViewLayoutType.equalsIgnoreCase("List")){
        // view= inflater.inflate(R.layout.list_item_layout_billers,parent,false);}

        RepRequestAdapterViewHolder holder = new RepRequestAdapterViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(RepRequestAdapterViewHolder holder, int position) {

        final RepInvites items=data.get(position);




        holder.id.setText(items.getId()+"");
        holder.email.setText(items.getEmail());
        holder.company.setText(items.getCompany_name());
        holder.department.setText(WordUtils.capitalize(items.getDepartment()));
        holder.requestDate.setText(items.getDate());
        holder.statusTxt.setText(WordUtils.capitalize(items.getStatus()));

        if(items.getStatus().equals("pending")) {
            holder.moreViewButton.setVisibility(View.GONE);
            holder.actionButton.setLayoutParams(lp);
            holder.actionButton.setText("Cancel Request");
            holder.statusTxt.setTextColor(ContextCompat.getColor(context, R.color.red_btn_bg_pressed_color));
        }else {
            holder.actionButton.setText("Remove Rep");
            holder.statusTxt.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));
           // holder.actionButton.setText(items.getStatus();
        }
        holder.id.setText(items.getId().toString());
//but_action.setLayoutParams(lp);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
class RepRequestAdapterViewHolder extends RecyclerView.ViewHolder {

    TextView email,department,company,id,requestDate,statusTxt;
    FButton actionButton,moreViewButton;
    /*
    *
    * CircleImageView userImage;
    TextView repName,repDepartment,requestDate,repRatingNumber;
    MaterialRatingBar repRatingBar;
    FButton reviewsButton,chatHistoryButton,actioButtonButton;
    *
    * **/

    public RepRequestAdapterViewHolder(View itemView) {
        super(itemView);

        statusTxt= (TextView)itemView.findViewById(R.id.statusTxt);
        email = (TextView)itemView.findViewById(R.id.rep_Email);
        company = (TextView)itemView.findViewById(R.id.rep_companyName);
        department = (TextView)itemView.findViewById(R.id.rep_company_dep);
        requestDate= (TextView)itemView.findViewById(R.id.rep_requestDate);
        id=(TextView)itemView.findViewById(R.id.hiddenID);
        actionButton=(FButton)itemView.findViewById(R.id.actionButton);

        moreViewButton=(FButton)itemView.findViewById(R.id.viewMoreButon);

      /**
       repName=(TextView)itemView.findViewById(R.id.repName);
        repDepartment=(TextView)itemView.findViewById(R.id.repDepartment);
        requestDate=(TextView)itemView.findViewById(R.id.requestDate);
        repRatingNumber=(TextView) itemView.findViewById(R.id.repRatingNumber);
        repRatingBar=(MaterialRatingBar) itemView.findViewById(R.id.repRatingBar);
        userImage = (CircleImageView)itemView.findViewById(R.id.userImage);

        reviewsButton= (FButton) itemView.findViewById(R.id.repReviewsButton);
        chatHistoryButton= (FButton) itemView.findViewById(R.id.repChatButton);
        actioButtonButton= (FButton) itemView.findViewById(R.id.repActionButton);
**/




    }
}
