package com.ecoach.cosapp.Activites.UserAccounts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.ecoach.cosapp.Application.Application;
import com.ecoach.cosapp.DataBase.AppInstanceSettings;
import com.ecoach.cosapp.DataBase.User;
import com.ecoach.cosapp.R;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import de.hdodenhof.circleimageview.CircleImageView;
import info.hoang8f.widget.FButton;

public class ProfileEditActivity extends AppCompatActivity implements IPickResult {
    EditText emailedt,phoneTxt,passwordTxt,confirmpassword,surname,firstname,locationDiscirption;
    FButton changePassword,backbutton;
    ViewFlipper viewFlipper;
    CircleImageView imageView;
    ImageView imageButton;
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

        changePassword=(FButton) findViewById(R.id.changePassword);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewFlipper.showNext();
            }
        });


        backbutton=(FButton) findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.showPrevious();
            }
        });

        imageButton =(ImageView)findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup()).show(ProfileEditActivity.this);
            }
        });

        imageView=(CircleImageView)findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup()).show(ProfileEditActivity.this);
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

    @Override
    public void onPickResult(PickResult pickResult) {
        imageView.setImageBitmap(pickResult.getBitmap());
    }
}
