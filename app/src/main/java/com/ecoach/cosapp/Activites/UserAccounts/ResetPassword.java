package com.ecoach.cosapp.Activites.UserAccounts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.ecoach.cosapp.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ResetPassword extends AppCompatActivity {

    EditText email, code, password, confirmPassword;
    Button nextButton;
    ViewFlipper viewFlipper;

    Button indicator1,indicator2;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        callFlipper();


    }

    private void callFlipper() {
        edViews();

        viewFlipper=(ViewFlipper)findViewById(R.id.viewFlipper);

        nextButton=(Button)findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nextButton.getText().equals("Next");{
                    validateFields();
                }
            }
        });




}

    private void edViews() {
        email = (EditText)findViewById(R.id.email);
        code = (EditText)findViewById(R.id.code);
        password = (EditText)findViewById(R.id.password);
        confirmPassword = (EditText)findViewById(R.id.confirmPassword);
    }

    private void validateFields() {
        if(email.getText().toString().length()<4){

            new SweetAlertDialog(this)
                    .setTitleText("Provide a Valid Email")
                    .show();
    } else if(password.getText().toString().length()<6){
            new SweetAlertDialog(this)
                    .setTitleText("Choose a password not less than Six characters")
                    .show();
        } else if(password != confirmPassword){
            new SweetAlertDialog(this)
                    .setTitleText("New Password don't match")
                    .show();
        }else{

            requestNewPassword();
        }


    }

    private void requestNewPassword() {

        //APi call for password OTP and new password change


    }

    }