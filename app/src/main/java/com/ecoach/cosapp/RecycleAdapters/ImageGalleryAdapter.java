package com.ecoach.cosapp.RecycleAdapters;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.ecoach.cosapp.Application.Application;
import com.ecoach.cosapp.DataBase.Categories;
import com.ecoach.cosapp.DataBase.GalleryStorage;
import com.ecoach.cosapp.R;
import com.ecoach.cosapp.Utilities.ViewUtils;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by apple on 4/3/17.
 */

public class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapterViewHolder>  {
    private Context context;
    private LayoutInflater inflater;
    List<GalleryStorage> data= Collections.emptyList();
    View view=null;

    public ImageGalleryAdapter(Context context, List<GalleryStorage> data) {
        inflater= LayoutInflater.from(context);
        this.data=data;
        this.context=context;



    }

    @Override
    public ImageGalleryAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.gallery_images_viewrow, parent, false);
        // } else if(Application.BillersRecycleViewLayoutType.equalsIgnoreCase("List")){
        // view= inflater.inflate(R.layout.list_item_layout_billers,parent,false);}

        ImageGalleryAdapterViewHolder holder = new ImageGalleryAdapterViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ImageGalleryAdapterViewHolder holder, int position) {

        final GalleryStorage items=data.get(position);


        holder.txthiddenId.setText(items.getShowcaseId());

        String avatorPath= Application.getSelectedCompanyObbject().getPath()
                +Application.getSelectedCompanyObbject().getCompanyStorageName()+"/"+items.getShowcaseLocation();

        Log.d("GalleryItems",avatorPath);
        Picasso.with(context)
                .load(avatorPath)
                .placeholder(R.drawable.ic_no_image)
                .fit()
                .centerCrop()
                .into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
class ImageGalleryAdapterViewHolder extends RecyclerView.ViewHolder {
    TextView txthiddenId,txtLabel;
    ImageView imageView;
    CircleImageView iconView;

    public ImageGalleryAdapterViewHolder(View itemView) {
        super(itemView);

        txthiddenId=(TextView)itemView.findViewById(R.id.galleryItemID);
        imageView=(ImageView)itemView.findViewById(R.id.galleryImage);






    }
}
