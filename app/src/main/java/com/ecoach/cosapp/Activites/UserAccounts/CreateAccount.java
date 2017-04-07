package com.ecoach.cosapp.Activites.UserAccounts;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
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
import com.ecoach.cosapp.DataBase.AppInstanceSettings;
import com.ecoach.cosapp.DataBase.User;
import com.ecoach.cosapp.Http.APIRequest;
import com.ecoach.cosapp.Http.VolleySingleton;
import com.ecoach.cosapp.R;
import com.jakewharton.processphoenix.ProcessPhoenix;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import info.hoang8f.widget.FButton;

import static android.R.attr.button;

public class CreateAccount extends AppCompatActivity {


    EditText emailedt,phoneTxt,passwordTxt,confirmpassword,surname,firstname,locationDiscirption;
    ListView Summary;
    ViewFlipper viewFlipper;
    FButton nextButton,backButton;

    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    Button indicator1,indicator2,indicator3,indicator4,indicator5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        initViews();

    }

    private void initViews() {
        editTextViews();
       // setIndicatorsInit();


        viewFlipper=(ViewFlipper)findViewById(R.id.viewFlipper);

        //indicator1.setBackgroundColor(CreateAccount.this.getResources().getColor(R.color.colorPrimary));
        nextButton=(FButton) findViewById(R.id.nextButton);
        backButton=(FButton) findViewById(R.id.backButton);
        backButton.setVisibility(View.INVISIBLE);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nextButton.getText().equals("Get Started")){

                   // Toast.makeText(CreateAccount.this,"You need to Save",Toast.LENGTH_LONG).show();
                    validateFields();
                }


         if(viewFlipper.getDisplayedChild() == 2){

           nextButton.setText("Get Started");

             setListViewAdapter();

}


                if(viewFlipper.getDisplayedChild() == 3){


                }else{

                    backButton.setVisibility(View.VISIBLE);
                    viewFlipper.showNext();
                }

            }
        });



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewFlipper.getDisplayedChild() !=2){

                    nextButton.setText("Next");
                }

                if(viewFlipper.getDisplayedChild() == 1){

                    backButton.setVisibility(View.INVISIBLE);
                }


                if(viewFlipper.getDisplayedChild() != 0){

                    viewFlipper.showPrevious();

                }

            }
        });

    }
    private void editTextViews(){

        emailedt = (EditText)findViewById(R.id.emailedt);
        phoneTxt = (EditText)findViewById(R.id.phoneEdt);
        passwordTxt =(EditText)findViewById(R.id.passwordEdt);
        confirmpassword=(EditText)findViewById(R.id.passwordEdt2);
        surname=(EditText)findViewById(R.id.surnameEdt);
        firstname=(EditText)findViewById(R.id.firstEdt);
        locationDiscirption=(EditText)findViewById(R.id.locationNameEdt);
        Summary=(ListView) findViewById(R.id.summaryList);

     }

    private void setIndicatorsInit(){

        indicator1=(Button)findViewById(R.id.indicatorOne);
        indicator2=(Button)findViewById(R.id.indicatorTwo);
        indicator3=(Button)findViewById(R.id.indicatorThree);
        indicator4=(Button)findViewById(R.id.indicatorFour);
        indicator5=(Button)findViewById(R.id.indicatorfice);

    }
    private void validateFields(){

        if(emailedt.getText().toString().length()<4){

            new SweetAlertDialog(this)
                    .setTitleText("Provide a Valid Email")
                    .show();

        }else if(phoneTxt.getText().toString().length()<10){
            new SweetAlertDialog(this)
                    .setTitleText("Provide a Valid Phone Number")
                    .show();

        }else if(passwordTxt.getText().toString().length()<4){
            new SweetAlertDialog(this)
                    .setTitleText("Password Cannot be less than four")
                    .show();

        }else if( ! passwordTxt.getText().toString().equals(confirmpassword.getText().toString())){

            new SweetAlertDialog(this)
                    .setTitleText("Passwords do not match"+passwordTxt.getText().toString() + "\n  " +confirmpassword.getText().toString())
                    .show();

        }else if(firstname.getText().toString().length() <3){

            new SweetAlertDialog(this)
                    .setTitleText("We need your first name")
                    .show();

        }else if(surname.getText().toString().length() <3){

            new SweetAlertDialog(this)
                    .setTitleText("We need your surname name")
                    .show();

        }else{

            createAccountVolley();

        }

    }

    private void setListViewAdapter(){

        List<String> summaryList = new ArrayList<String>();
        summaryList.add(emailedt.getText().toString());
        summaryList.add(phoneTxt.getText().toString());
        summaryList.add(surname.getText().toString());
        summaryList.add(firstname.getText().toString());
        summaryList.add(locationDiscirption.getText().toString());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                summaryList );



        Summary.setAdapter(arrayAdapter);

    }
    SweetAlertDialog pDialog;
    private void createAccountVolley(){

         pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Setting you up ..");
        pDialog.setCancelable(false);
        pDialog.show();



        final HashMap<String, String> params = new HashMap<String, String>();



        params.put("is_signup",""+ "1");
        params.put("fname",firstname.getText().toString());
        params.put("lname",surname.getText().toString());
        params.put("email",emailedt.getText().toString());
        params.put("phone",phoneTxt.getText().toString());
        params.put("pass",passwordTxt.getText().toString());
        params.put("location_lat","");
        params.put("location_long","");
        params.put("location_desc",locationDiscirption.getText().toString());



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

                                new SweetAlertDialog(CreateAccount.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Sorry,Try Again")
                                        .setContentText(message)
                                        .show();

                            }else {


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
                new SweetAlertDialog(CreateAccount.this, SweetAlertDialog.ERROR_TYPE)
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

            ProcessPhoenix.triggerRebirth(CreateAccount.this);


        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
