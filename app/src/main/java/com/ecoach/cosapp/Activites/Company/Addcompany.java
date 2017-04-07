package com.ecoach.cosapp.Activites.Company;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
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
import com.ecoach.cosapp.Activites.MainActivity;
import com.ecoach.cosapp.Activites.SearchActivity;
import com.ecoach.cosapp.Activites.UserAccounts.CreateAccount;
import com.ecoach.cosapp.Application.Application;
import com.ecoach.cosapp.DataBase.AppInstanceSettings;
import com.ecoach.cosapp.DataBase.Categories;
import com.ecoach.cosapp.DataBase.User;
import com.ecoach.cosapp.Http.APIRequest;
import com.ecoach.cosapp.Http.UploadBase64;
import com.ecoach.cosapp.Http.VolleySingleton;
import com.ecoach.cosapp.R;
import com.ecoach.cosapp.Utilities.GPSTracker;
import com.ecoach.cosapp.Utilities.Utility;
import com.ecoach.cosapp.Utilities.ViewUtils;
import com.flask.colorpicker.ColorPickerView;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;
import com.vansuita.pickimage.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import info.hoang8f.widget.FButton;

public class Addcompany extends AppCompatActivity implements IPickResult {



    ImageView pinBusinesslocation,businesscoverLogo,chatBackground;
    CircleImageView companyLogo;
    LocationManager mLocationManager;
    GPSTracker gpsTracker;
    ViewFlipper viewFlipper;
    FButton nextButton,backButton,uploadCert;
    ColorPickerView color_picker_view;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    String email,category,use_my_email;
    Switch use_email;
    EditText companyName,company_email,company_phone1,company_phone2,company_URL,company_address,company_bio;
    Spinner Category;
    User user;
    TextView certLabel;
    String imageTypeUploaded;
    String company_lat = "0.0";
    String company_long = "0.0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcompany);

        if (getSupportActionBar() != null){

            getSupportActionBar().setTitle("Add Company");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        gpsTracker = new GPSTracker(Addcompany.this);

        initViews();
        setactionButtons();
        setSpinnerValues();
        setmLocationListener();


        try {
            user = User.getUserByKey(AppInstanceSettings.load(AppInstanceSettings.class,1).getUserkey());

        }catch (Exception e){

            e.printStackTrace();
        }
    }

    private void initViews() {



        viewFlipper=(ViewFlipper)findViewById(R.id.viewFlipper);
        color_picker_view=(ColorPickerView)findViewById(R.id.color_picker_view);
       // color_picker_view.setO;

        companyName = (EditText) findViewById(R.id.companyname);
        certLabel = (TextView)findViewById(R.id.certLabel);
        Category = (Spinner)findViewById(R.id.companyCategory);
        Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        company_email=(EditText)findViewById(R.id.companyemail);
        company_phone1=(EditText)findViewById(R.id.phone1);
        company_phone2=(EditText)findViewById(R.id.phone2);
        company_URL=(EditText)findViewById(R.id.website);
        company_address=(EditText)findViewById(R.id.address);
        company_bio=(EditText)findViewById(R.id.companyBio);
        use_email = (Switch)findViewById(R.id.useEmailSwitch);
        businesscoverLogo= (ImageView)findViewById(R.id.businesscoverLogo);
        companyLogo = (CircleImageView)findViewById(R.id.imageButton2);
        pinBusinesslocation = (ImageView)findViewById(R.id.imageButton3);
        uploadCert=(FButton)findViewById(R.id.button2);
        chatBackground=(ImageView)findViewById(R.id.imageView5) ;

        use_email.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if(isChecked){
                    company_email.setText(user.getEmail());
                }else{

                    company_email.setText("");
                }
            }
        });

        pinBusinesslocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!gpsTracker.canGetLocation()){


                    gpsTracker.showSettingsAlert();
                }else if(gpsTracker.getLocation() == null){


                    ViewUtils.multipleDialog(Addcompany.this,"Could not get your location","Try moving around for a few minutes");
                }else {

                    company_lat = String.valueOf(gpsTracker.getLatitude()) ;
                    company_long = String.valueOf(gpsTracker.getLocation());
                    pinBusinesslocation.setImageResource(R.drawable.mapshot);
                }


            }
        });

        companyLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTypeUploaded = "businesslogo";
                PickImageDialog.build(new PickSetup()).show(Addcompany.this);

            }
        });


        businesscoverLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTypeUploaded = "businesscover";
                PickImageDialog.build(new PickSetup()).show(Addcompany.this);
            }
        });

        uploadCert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTypeUploaded = "certificate";
                PickImageDialog.build(new PickSetup()).show(Addcompany.this);
            }
        });

        chatBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTypeUploaded = "chatBack";
                PickImageDialog.build(new PickSetup()).show(Addcompany.this);
            }
        });

        User user = User.load(User.class,1);
        Log.d("User key ",user.getUserkey());

    }


    void setSpinnerValues(){

        List<String> spinnerValues = new ArrayList<>();

        for(Categories  categories: Categories.getAllCategories()){

            spinnerValues.add( categories.getCategoryNames());
        }


        String[] wee = spinnerValues.toArray(new String[spinnerValues.size()]);
        final String[] str={"Report 1","Report 2","Report 3","Report 4","Report 5"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, wee);
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

// Spinner spinYear = (Spinner)findViewById(R.id.spin);
        Category.setAdapter(spinnerArrayAdapter);

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
        nextButton=(FButton)findViewById(R.id.nextButton);
        backButton=(FButton)findViewById(R.id.backButton);
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






    private void setmLocationListener(){

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,
                1000, mLocationListener);
    }

    private void validateFields(){

        //if all fields are successfuly

/**
 *
 * companyName = (EditText) findViewById(R.id.companyname);
 Category = (Spinner)findViewById(R.id.companyCategory);
 company_email=(EditText)findViewById(R.id.companyemail);
 company_phone1=(EditText)findViewById(R.id.phone1);
 company_phone2=(EditText)findViewById(R.id.phone2);
 company_URL=(EditText)findViewById(R.id.website);
 company_address=(EditText)findViewById(R.id.address);
 company_bio=(EditText)findViewById(R.id.companyBio);
 *
 * */





        try{


            if(use_email.isChecked()){
                email = user.getEmail();
                use_my_email = "1";
            }else{
                email = company_email.getText().toString();
                use_my_email = "0";
            }

   if(companyName.getText().toString().length() <=1){
       ViewUtils.singleDialog(Addcompany.this,"Provide a Company Name");
   }else if(email.isEmpty()){
       ViewUtils.singleDialog(Addcompany.this,"Provide a Company Email");
   }else if(company_phone1.getText().toString().length()<10){
       ViewUtils.singleDialog(Addcompany.this,"Provide a Company Phone");
   }else if(category.length() == 0){
       ViewUtils.singleDialog(Addcompany.this,"Select a Category");

   }else if(company_bio.getText().toString().length() == 0) {
       ViewUtils.singleDialog(Addcompany.this, "Add company Bio ");
   }else{

       createAccountVolley();

   }


        }catch (Exception e){

            e.printStackTrace();}




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
       final SweetAlertDialog pDialog;
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Setting you up ..");
        pDialog.setCancelable(false);
        pDialog.show();



        final HashMap<String, String> params = new HashMap<String, String>();



        params.put("is_add_company",""+ "1");
        params.put("company_name",companyName.getText().toString());
        params.put("company_category",Categories.getCategoryIDByName(category).getCategoryID().toString());
        params.put("phone1",company_phone1.getText().toString());
        params.put("phone2",company_phone2.getText().toString()+"");
        params.put("email",email);
        params.put("use_my_email",use_my_email);
        params.put("company_bio",company_bio.getText().toString());
        params.put("company_website",company_URL.getText().toString()+"");
        params.put("address",company_address.getText().toString()+"");
        params.put("company_lat",company_URL.getText().toString()+"");
        params.put("company_long",company_address.getText().toString()+"");

        volleySingleton= VolleySingleton.getsInstance();
        requestQueue=VolleySingleton.getRequestQueue();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                APIRequest.BASE_URL,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    //Log.d("Params",params+"");
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {



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

                                String companyID = object.getString("company_id");
                                Application.setLast_company_id(companyID);
                                new SweetAlertDialog(Addcompany.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Success")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {


                                                if(     Application.getCompanyCover().length() != 0 ||
                                                        Application.getCompanyCert().length() != 0 ||
                                                        Application.getCompanyChatBack().length() != 0 ||
                                                        Application.getCompanyLogo().length() != 0){


                                                    Intent intent = new Intent(Addcompany.this, UploadBase64.class);
                                                    startService(intent);



                                                }

                                                sweetAlertDialog.dismiss();


                                              //  finish();
                                            }
                                        })
                                        .setContentText("New Company Added")
                                        .show();




                            }





                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                        //  Message.messageShort(MyApplication.getAppContext(),""+tokenValue+"\n"+response.toString()+"\n"+booleaner);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)   {
                pDialog.dismiss();
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
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("auth-key", user.getUserkey());
                return headers;
            }
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

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here


            Toast.makeText(Addcompany.this,location.getLatitude()+ "" + location.getLongitude(),Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @Override
    public void onPickResult(PickResult pickResult) {



       switch(imageTypeUploaded){

           case "businesslogo" :

               companyLogo.setImageBitmap(pickResult.getBitmap());
               Application.setCompanyLogo(Utility.Base64String(pickResult.getBitmap()));
               break;
           case "businesscover" :
               businesscoverLogo.setImageBitmap(pickResult.getBitmap());
               Application.setCompanyCover(Utility.Base64String(pickResult.getBitmap()));
               break;
           case "certificate" :
               //businesscoverLogo.setImageBitmap(pickResult.getBitmap());
               certLabel.setText("1 File Uploaded");
               Application.setCompanyCert(Utility.Base64String(pickResult.getBitmap()));
               break;
           case "chatBack" :
               chatBackground.setImageBitmap(pickResult.getBitmap());
               Application.setCompanyChatBack(Utility.Base64String(pickResult.getBitmap()));
               break;
        }

    }
}
