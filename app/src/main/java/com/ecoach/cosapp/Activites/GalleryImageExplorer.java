package com.ecoach.cosapp.Activites;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.ecoach.cosapp.Application.Application;
import com.ecoach.cosapp.DataBase.GalleryStorage;
import com.ecoach.cosapp.R;
import com.ecoach.cosapp.Utilities.PicassoImageLoader;
import com.veinhorn.scrollgalleryview.MediaInfo;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;
import com.veinhorn.scrollgalleryview.loader.DefaultImageLoader;
import com.veinhorn.scrollgalleryview.loader.DefaultVideoLoader;
import com.veinhorn.scrollgalleryview.loader.MediaLoader;

import java.util.ArrayList;
import java.util.List;

public class GalleryImageExplorer extends AppCompatActivity {
    private ScrollGalleryView scrollGalleryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_image_explorer);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(Application.getSelectedCompanyName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        setUpGalarySlider();
    }


    void setUpGalarySlider(){

          final ArrayList<String> images = new ArrayList<>();

        for(GalleryStorage galleryStorage : GalleryStorage.getCompanyGalleryItemsByCompanyID(Application.getSelectedCompanyObbject().getCompanyCuid())){

            String avatorPath= Application.getSelectedCompanyObbject().getPath()
                    +Application.getSelectedCompanyObbject().getCompanyStorageName()+"/"+galleryStorage.getShowcaseLocation();

            images.add(avatorPath);
        }

        List<MediaInfo> infos = new ArrayList<>(images.size());

        for (String url : images) infos.add(MediaInfo.mediaLoader(new PicassoImageLoader(url)));


        scrollGalleryView = (ScrollGalleryView) findViewById(R.id.scroll_gallery_view);
        scrollGalleryView
                .setThumbnailSize(100)
                .setZoom(true)
                .setFragmentManager(getSupportFragmentManager())
                .addMedia(infos);
    }

    private Bitmap toBitmap(int image) {
        return ((BitmapDrawable) getResources().getDrawable(image)).getBitmap();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
