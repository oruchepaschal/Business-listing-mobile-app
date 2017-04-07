package com.ecoach.cosapp.Activites.Company;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ecoach.cosapp.Activites.GalleryImageExplorer;
import com.ecoach.cosapp.Application.Application;
import com.ecoach.cosapp.DataBase.GalleryStorage;
import com.ecoach.cosapp.DataBase.VerifiedCompanies;
import com.ecoach.cosapp.ListViewAdapter.DetailsListViewAdapter;
import com.ecoach.cosapp.Models.DetailsItem;
import com.ecoach.cosapp.R;
import com.ecoach.cosapp.RecycleAdapters.ImageGalleryAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyCompanyInformation extends Activity {
    ListView listView;
    List<DetailsItem> detailsItems = new ArrayList<>();
    RecyclerView recycleGallery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_company_information);


        setViews();
        setGalleryImages();
    }


    void setViews(){




        VerifiedCompanies company = Application.getSelectedCompanyObbject();




        detailsItems.add(new DetailsItem(MyCompanyInformation.this.getResources().getString(R.string.fa_addressbook),company.getAddress()));
        detailsItems.add(new DetailsItem(MyCompanyInformation.this.getResources().getString(R.string.fa_phone),company.getPhone1()));
        detailsItems.add(new DetailsItem(MyCompanyInformation.this.getResources().getString(R.string.fa_phone),company.getPhone2()));

        detailsItems.add(new DetailsItem(MyCompanyInformation.this.getResources().getString(R.string.fa_envelope),company.getEmail()));
        detailsItems.add(new DetailsItem(MyCompanyInformation.this.getResources().getString(R.string.fa_globe),company.getWebsite()));
        //  detailsItems.add(new DetailsItem(R.mipmap.ic_adressbook,"dsdsdsd"));


        listView =(ListView)findViewById(R.id.itemDetails);
        // get data from the table by the ListAdapter
        DetailsListViewAdapter customAdapter = new DetailsListViewAdapter(this, R.layout.itemsdetailslistcell, detailsItems);

        listView .setAdapter(customAdapter);

    }
    void setGalleryImages(){

        recycleGallery =
                (RecyclerView)findViewById(R.id.recycleGallery);

        ImageGalleryAdapter recommendationAdapter = new ImageGalleryAdapter(MyCompanyInformation.this, GalleryStorage.getCompanyGalleryItemsByCompanyID(Application.getSelectedCompanyObbject().getCompanyCuid()));
        recycleGallery.setAdapter(recommendationAdapter);
        recycleGallery.setLayoutManager(new LinearLayoutManager(MyCompanyInformation.this, LinearLayoutManager.HORIZONTAL, false));
        recycleGallery.addOnItemTouchListener(new RecyclerTouchListener(MyCompanyInformation.this, recycleGallery, new ClickListener() {
            @Override
            public void onClick(View view, int position) {


                TextView name=(TextView)view.findViewById(R.id.galleryItemID);
                String catname=name.getText().toString();



                Intent intent = new Intent(MyCompanyInformation.this, GalleryImageExplorer.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }


    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener){
            this.clickListener=clickListener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child= recyclerView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clickListener!=null){


                        clickListener.onLongClick(child,recyclerView.getChildPosition(child));
                    }

                }
            });
        }
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child= rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clickListener!=null && gestureDetector.onTouchEvent(e)){


                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public static interface ClickListener{

        public void onClick(View view,int position);
        public void onLongClick(View view, int position);

    }

}
