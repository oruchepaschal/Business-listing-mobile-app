package com.ecoach.cosapp.Activites.CompanyDetailsTabbedActivities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ecoach.cosapp.Application.Application;
import com.ecoach.cosapp.R;

public class Profile extends Activity {

    TextView aboutTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        aboutTxt=(TextView)findViewById(R.id.aboutTxt);
        aboutTxt.setText(Application.getSelectedCompanyObbject().getBio());
    }
}
