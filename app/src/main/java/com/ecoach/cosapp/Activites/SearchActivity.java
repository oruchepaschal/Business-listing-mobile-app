package com.ecoach.cosapp.Activites;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.SearchView;

import com.ecoach.cosapp.Application.Application;
import com.ecoach.cosapp.R;

public class SearchActivity extends AppCompatActivity {


    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        // add back arrow to toolbar
        if (getSupportActionBar() != null){

            getSupportActionBar().setTitle("Search ");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        setviews();
    }

    private void setviews() {

        searchView=(SearchView)findViewById(R.id.search_bar);
        searchView.requestFocus();

    }





    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
