package com.ecoach.cosapp.Activites.UserAccounts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.ecoach.cosapp.Application.Application;
import com.ecoach.cosapp.DataBase.AppInstanceSettings;
import com.ecoach.cosapp.DataBase.User;
import com.ecoach.cosapp.R;

public class ProfileEditActivity extends AppCompatActivity {
    EditText emailedt,phoneTxt,passwordTxt,confirmpassword,surname,firstname,locationDiscirption;
    Button changePassword,backbutton;
    ViewFlipper viewFlipper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);



        if (getSupportActionBar() != null){

            getSupportActionBar().setTitle("Your Profile");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        viewFlipper=(ViewFlipper)findViewById(R.id.viewFlipper);

        changePassword=(Button)findViewById(R.id.changePassword);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewFlipper.showNext();
            }
        });


        backbutton=(Button)findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.showPrevious();
            }
        });


        editTextViews();

    }

    private void editTextViews(){

        emailedt = (EditText)findViewById(R.id.emailedt);
        phoneTxt = (EditText)findViewById(R.id.phoneEdt);
        //passwordTxt =(EditText)findViewById(R.id.passwordEdt);
        //confirmpassword=(EditText)findViewById(R.id.passwordEdt2);
        surname=(EditText)findViewById(R.id.surnameEdt);
        firstname=(EditText)findViewById(R.id.firstEdt);



        //set Values


        User user = User.getUserByKey(AppInstanceSettings.load(AppInstanceSettings.class,1).getUserkey());

        emailedt.setText(user.getEmail());
        phoneTxt.setText(user.getPhone());
        surname.setText(user.getLname());
        firstname.setText(user.getFname());


    }
    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
}
