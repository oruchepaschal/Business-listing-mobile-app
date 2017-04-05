package com.ecoach.cosapp.Activites.Company;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ecoach.cosapp.Activites.SearchActivity;
import com.ecoach.cosapp.Activites.UserAccounts.CreateAccount;
import com.ecoach.cosapp.DataBase.AppInstanceSettings;
import com.ecoach.cosapp.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ManangeMyCompanies extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manange_companies);


        if (getSupportActionBar() != null){

            getSupportActionBar().setTitle("My Companies");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        setFloatingButton();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    void setFloatingButton(){

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addcompany);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try{

                    if(AppInstanceSettings.load(AppInstanceSettings.class,1).isloggedIn() == false){

                        new SweetAlertDialog(ManangeMyCompanies.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Sorry,")
                                .setContentText("You need to Login")
                                .show();



                    }else{

                        Intent intent = new Intent(ManangeMyCompanies.this,Addcompany.class);
                        startActivity(intent);

                    }


                }catch (Exception e){

                    new SweetAlertDialog(ManangeMyCompanies.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Sorry,")
                            .setContentText("You need to Login")
                            .show();

                }




            }
        });

    }
}
