package com.ecoach.cosapp.Activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ecoach.cosapp.R;

public class MainRecentChats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recent_chats);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Recent Chats");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
