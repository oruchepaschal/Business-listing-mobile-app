package com.ecoach.cosapp.Activites.Company;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.ecoach.cosapp.Activites.MainActivity;
import com.ecoach.cosapp.Activites.SearchActivity;
import com.ecoach.cosapp.R;

public class Addcompany extends AppCompatActivity {

    ViewFlipper viewFlipper;
    Button nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcompany);

        if (getSupportActionBar() != null){

            getSupportActionBar().setTitle("Add Company");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        initViews();
        setactionButtons();

    }

    private void initViews() {

        viewFlipper=(ViewFlipper)findViewById(R.id.viewFlipper);

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    void setactionButtons(){


        nextButton = (Button)findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.showNext();

            }
        });

    }


}
