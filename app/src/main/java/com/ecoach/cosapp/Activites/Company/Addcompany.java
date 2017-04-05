package com.ecoach.cosapp.Activites.Company;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ViewFlipper;

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
import com.ecoach.cosapp.Activites.MainActivity;
import com.ecoach.cosapp.Activites.SearchActivity;
import com.ecoach.cosapp.Activites.UserAccounts.CreateAccount;
import com.ecoach.cosapp.DataBase.User;
import com.ecoach.cosapp.Http.APIRequest;
import com.ecoach.cosapp.Http.VolleySingleton;
import com.ecoach.cosapp.R;

import org.json.JSONObject;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Addcompany extends AppCompatActivity {

    ViewFlipper viewFlipper;
    Button nextButton,backButton;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    EditText companyName,company_category,company_email,company_phone1,company_phone2,company_URL,physical,company_address;

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


        User user = User.load(User.class,1);
        Log.d("User key ",user.getUserkey());

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    private void setactionButtons() {

        // setIndicatorsInit();


        viewFlipper=(ViewFlipper)findViewById(R.id.viewFlipper);

        //indicator1.setBackgroundColor(CreateAccount.this.getResources().getColor(R.color.colorPrimary));
        nextButton=(Button)findViewById(R.id.nextButton);
        backButton=(Button)findViewById(R.id.backButton);
        backButton.setVisibility(View.INVISIBLE);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(nextButton.getText().toString().equals("Add Company")){

                    validateFields();


                }


                if(viewFlipper.getDisplayedChild() == 1){

                    nextButton.setText("Add Company");



                }
                if(viewFlipper.getDisplayedChild() == 2){


                }else {
                    backButton.setVisibility(View.VISIBLE);
                    viewFlipper.showNext();
                }

            }
        });



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(viewFlipper.getDisplayedChild() == 1){

                    backButton.setVisibility(View.INVISIBLE);
                }
                if(viewFlipper.getDisplayedChild() == 0){
                    //backButton.setVisibility(View.INVISIBLE);

                }else {
                    nextButton.setText("Next");
                    viewFlipper.showPrevious();
                }
            }
        });

    }


    SweetAlertDialog pDialog;



    private void validateFields(){

        //if all fields are successfuly

        createAccountVolley();

        //else

        //show error dialog
        /**
         *  new SweetAlertDialog(Addcompany.this, SweetAlertDialog.ERROR_TYPE)
         .setTitleText("Sorry,Try Again")
         .setContentText(message)
         .show();
         *
         * */

    }

    private void createAccountVolley(){

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Setting you up ..");
        pDialog.setCancelable(false);
        pDialog.show();



        final HashMap<String, String> params = new HashMap<String, String>();



       /** params.put("is_signup",""+ "1");
        params.put("fname",firstname.getText().toString());
        params.put("lname",surname.getText().toString());
        params.put("email",emailedt.getText().toString());
        params.put("phone",phoneTxt.getText().toString());
        params.put("pass",passwordTxt.getText().toString());
        params.put("location_lat","");
        params.put("location_long","");
        params.put("location_desc",locationDiscirption.getText().toString());***/



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

                            pDialog.hide();

                            Log.d("logs",response.toString());




                            JSONObject  object= response.optJSONObject("ecoachlabs");
                            String statuscode = object.getString("status");
                            String message = object.getString("msg");

                            if(!statuscode.equals("201")){

                                new SweetAlertDialog(Addcompany.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Sorry,Try Again")
                                        .setContentText(message)
                                        .show();

                            }else {


                              //  persistUserData(object);

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
                new SweetAlertDialog(Addcompany.this, SweetAlertDialog.ERROR_TYPE)
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
}
