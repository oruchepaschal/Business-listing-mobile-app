package com.ecoach.cosapp.Activites.Company;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TabHost;

import com.ecoach.cosapp.Activites.CompanyDetailsTabbedActivities.Details;
import com.ecoach.cosapp.Activites.CompanyDetailsTabbedActivities.Map;
import com.ecoach.cosapp.Activites.CompanyDetailsTabbedActivities.Profile;
import com.ecoach.cosapp.R;

public class MyCompanyDepartments extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_company_departments);
    }




}
