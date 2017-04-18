package com.ecoach.cosapp.Activites.UserAccounts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ecoach.cosapp.DataBase.AppInstanceSettings;
import com.ecoach.cosapp.DataBase.User;
import com.ecoach.cosapp.Http.APIRequest;
import com.ecoach.cosapp.Http.VolleySingleton;
import com.ecoach.cosapp.R;
import com.jakewharton.processphoenix.ProcessPhoenix;

import org.json.JSONObject;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import info.hoang8f.widget.FButton;

public class LoginActivity extends AppCompatActivity {

    FButton createAccountButton;

    FButton loginButton;
    EditText usernameEdt,passwordEdt;
    TextView forgotpassword;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Login");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        initViews();
    }


    private void ValiedateLogin(){



        if(usernameEdt.getText().toString().isEmpty()){

            new SweetAlertDialog(this)
                    .setTitleText("Provide a Username")
                    .show();

        }else if(passwordEdt.getText().toString().isEmpty()){

            new SweetAlertDialog(this)
                    .setTitleText("Provide a Password")
                    .show();
        }else{

            loginVolley();

        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void intiEdt(){
        usernameEdt = (EditText)findViewById(R.id.usernameEdt);
        passwordEdt = (EditText)findViewById(R.id.passwordEdt);

    }

    private void initTetView(){
        forgotpassword = (TextView) findViewById(R.id.forgotpassword);
forgotpassword.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), ResetPassword.class));
    }
});
    }
    private void initViews() {


        intiEdt();
        createAccountButton=(FButton) findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(LoginActivity.this,CreateAccount.class); startActivity(intent);

            }
        });


        loginButton = (FButton) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("login","start here");
                ValiedateLogin();
            }
        });

        initTetView();
    }

    SweetAlertDialog pDialog;
    private void loginVolley(){

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Signing in ..");
        pDialog.setCancelable(false);
        pDialog.show();



        final HashMap<String, String> params = new HashMap<String, String>();



        params.put("is_login",""+ "1");
        params.put("user",usernameEdt.getText().toString());
        params.put("pass",passwordEdt.getText().toString());


        Log.d("loginD",params.toString());


        volleySingleton= VolleySingleton.getsInstance();
        requestQueue=VolleySingleton.getRequestQueue();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                APIRequest.BASE_URL,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    //Log.d("Params",params+"");
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Log.d("logs",response.toString());
                            pDialog.hide();

                            Log.d("logs",response.toString());




                            JSONObject  object= response.optJSONObject("ecoachlabs");
                            String statuscode = object.getString("status");
                            String message = object.getString("msg");

                            if(!statuscode.equals("201")){

                                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Sorry,Try Again")
                                        .setContentText(message)
                                        .show();

                            }else if(statuscode.equals("201")){

                                persistUserData(object);

                            }





                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                        //  Message.messageShort(MyApplication.getAppContext(),""+tokenValue+"\n"+response.toString()+"\n"+booleaner);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)   {
                pDialog.hide();
                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Something went wrong!")
                        .show();


                //  dialogs.SimpleWarningAlertDialog("Transmission Error", "Connection Failed").show();
                Log.d("volley.Response", error.toString());




                if (error instanceof TimeoutError) {
                    // dialogs.SimpleWarningAlertDialog("Network Slacking", "Time Out Error").show();
                    Log.d("volley", "NoConnectionError.....TimeoutError..");


                    //     dialogs.SimpleWarningAlertDialog("Network Slacking", "Time Out Error");



                } else if(error instanceof NoConnectionError){

                    // dialogs.SimpleWarningAlertDialog("No Internet Connections Detected", "No Internet Connection").show();

                }


                else if (error instanceof AuthFailureError) {
                    //  Log.d("volley", "AuthFailureError..");
                    // dialogs.SimpleWarningAlertDialog("Authentication Failure","AuthFailureError").show();


                } else if (error instanceof ServerError) {
                    // dialogs.SimpleWarningAlertDialog("Server Malfunction", "Server Error").show();

                } else if (error instanceof NetworkError) {
                    // dialogs.SimpleWarningAlertDialog("Network Error", "Network Error").show();

                } else if (error instanceof ParseError) {
                    // dialogs.SimpleWarningAlertDialog("Parse Error","Parse Error").show();
                }

            }
        }) {

        };
        int socketTimeout = 480000000;//8 minutes - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(
                socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        requestQueue.add(request);
        Log.d("oxinbo","Server Logs"+params.toString());
    }
    private void persistUserData(JSONObject jsonObject){

        try {

            Log.d("loginD",jsonObject.toString());
            String ukey = jsonObject.getString("ukey");
            String email = jsonObject.getString("email");

            String path = jsonObject.getString("path");
            String storage = jsonObject.getString("storage");


            String fname = jsonObject.getString("fname");
            String lname = jsonObject.getString("lname");

            String phone = jsonObject.getString("phone");
            String avatar = jsonObject.getString("avatar");


            User user = User.getUserByKey(ukey);
            if(user == null)
                user = new User();

            user.setUserkey(ukey);
            user.setEmail(email);
            user.setPath(path);
            user.setStorage(storage);
            user.setFname(fname);
            user.setLname(lname);
            user.setPhone(phone);
            user.setAvatar(avatar);

            Long id = user.save();

            Log.d("Saved User ",id.toString());


            AppInstanceSettings appInstanceSettings = AppInstanceSettings.load(AppInstanceSettings.class,1);
            if(appInstanceSettings == null)
                appInstanceSettings = new AppInstanceSettings();

            appInstanceSettings.setIsloggedIn(true);
            appInstanceSettings.setUserkey(ukey);
            appInstanceSettings.save();

            ProcessPhoenix.triggerRebirth(LoginActivity.this);


        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
