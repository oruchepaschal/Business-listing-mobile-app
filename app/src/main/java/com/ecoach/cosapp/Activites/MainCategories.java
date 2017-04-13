package com.ecoach.cosapp.Activites;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.ecoach.cosapp.Activites.Company.CompaniesActivity;
import com.ecoach.cosapp.Application.Application;
import com.ecoach.cosapp.DataBase.Categories;
import com.ecoach.cosapp.R;
import com.ecoach.cosapp.RecycleAdapters.MainCategoryAdapter;

public class MainCategories extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    RecyclerView.LayoutManager layoutManager,verticalManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_categories);

        if (getSupportActionBar() != null){
          getSupportActionBar().setTitle("Categories");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }



        initViews();
    }

    private void initViews() {

        refreshLayout=(SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MainCategoryAdapter categoriesAdapter = new MainCategoryAdapter(MainCategories.this, Categories.getAllCategories());
                layoutManager = new GridLayoutManager(MainCategories.this, 3);



                recyclerView.setAdapter(categoriesAdapter);
                recyclerView.setLayoutManager(layoutManager);
                refreshLayout.setRefreshing(false);
            }
        });
        setUpRecycleView();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private  void setUpRecycleView(){

        recyclerView = (RecyclerView)findViewById(R.id.categoryRecycle);


        MainCategoryAdapter categoriesAdapter = new MainCategoryAdapter(MainCategories.this,Categories.getAllCategories());
        layoutManager = new GridLayoutManager(MainCategories.this, 3);



        recyclerView.setAdapter(categoriesAdapter);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setNestedScrollingEnabled(false);
        //new RecyclerTouchListener(getActivity(), walletRecycle, new ClickListener()
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(MainCategories.this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                TextView tv=(TextView)view.findViewById(R.id.catID);
                String id=tv.getText().toString();


                Log.d("category","catgory item "+tv.getText().toString());
                Application.setSelectedCategoryID(id);

                TextView name=(TextView)view.findViewById(R.id.labelview);
                String catname=name.getText().toString();

                Application.setSelectedCategoryName(catname);


                Intent intent = new Intent(MainCategories.this, CompaniesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);



            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));




    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private  ClickListener clickListener;

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
        public void onLongClick(View view,int position);

    }
}
