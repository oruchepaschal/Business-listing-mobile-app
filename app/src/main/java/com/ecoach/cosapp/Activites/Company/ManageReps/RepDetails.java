package com.ecoach.cosapp.Activites.Company.ManageReps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ecoach.cosapp.R;

public class RepDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep_details);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Rep Review");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }
}
