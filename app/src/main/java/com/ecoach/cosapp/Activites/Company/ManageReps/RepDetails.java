package com.ecoach.cosapp.Activites.Company.ManageReps;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.amulyakhare.textdrawable.TextDrawable;
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
import com.ecoach.cosapp.Application.Application;
import com.ecoach.cosapp.DataBase.AppInstanceSettings;
import com.ecoach.cosapp.DataBase.RepsReview;
import com.ecoach.cosapp.Http.APIRequest;
import com.ecoach.cosapp.Http.VolleySingleton;
import com.ecoach.cosapp.R;
import com.ecoach.cosapp.Utilities.Utility;
import com.ecoach.cosapp.Utilities.ViewUtils;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import info.hoang8f.widget.FButton;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class RepDetails extends AppCompatActivity {
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    RepsReview repsReview = new RepsReview();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep_details);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Rep Review");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }



        try{

            if(RepsReview.getRepInvite(Application.activeRepInvites.getEmail(),Application.AppUserKey) == null){

                getrepDetails();

            }else{

                setRepInformation(RepsReview.getRepInvite(Application.activeRepInvites.getEmail(),Application.AppUserKey));

            }

        }catch (Exception e){

e.printStackTrace();

        }


    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    private void setRepInformation( RepsReview repsReview){
        CircleImageView circleImageView = (CircleImageView)findViewById(R.id.repImage);

        String avatorPath= repsReview.getPath()+repsReview.getStorage()+"/"+repsReview.getAvatar();

        TextDrawable drawable = TextDrawable.builder()
                .buildRoundRect(repsReview.getFullname().toString().substring(0,1),RepDetails.this. getResources().getColor(R.color.colorPrimary), 10); // radius in px

        Drawable d = new BitmapDrawable(ViewUtils.drawableToBitmap(drawable));

        Picasso.with(RepDetails.this)
                .load(avatorPath)
                .placeholder(d)
                .into(circleImageView);


        TextView rep_name = (TextView)findViewById(R.id.rep_name);
        rep_name.setText(repsReview.getFullname());

        TextView rep_department = (TextView)findViewById(R.id.rep_department);
        rep_department.setText(WordUtils.capitalize(repsReview.getDepartment()));


        TextView rep_email = (TextView)findViewById(R.id.rep_email);
        rep_email.setText(repsReview.getEmail());


        TextView rep_date_added = (TextView)findViewById(R.id.rep_date_added);
        rep_date_added.setText(repsReview.getLink_date());


        TextView repTotrating = (TextView)findViewById(R.id.repTotrating);
        repTotrating.setText(repsReview.getRep_rating());


        MaterialRatingBar materialRatingBar = (MaterialRatingBar)findViewById(R.id.repRatingBar);
        materialRatingBar.setRating(Float.parseFloat(repsReview.getRep_rating()));


        FButton totalReviewButton = (FButton)findViewById(R.id.totalReviewButton);
        totalReviewButton.setText(repsReview.getTotal_reviews() + " " + " total Reviews");



        FButton totalChatButton = (FButton)findViewById(R.id.totalChatButton);
        totalChatButton.setText(repsReview.getTotal_chats() + " " + " total Chats");

    }

    private void getrepDetails(){


        final SweetAlertDialog pDialog;
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Getting Rep Details");
        pDialog.setCancelable(false);
        pDialog.show();

        final HashMap<String, String> params = new HashMap<String, String>();



        params.put("fetch_admin_info",""+ "1");
        params.put("scope","rep_ratings");
        params.put("rep_email", Application.activeRepInvites.getEmail());
        params.put("company_id", Application.activeRepInvites.getCompany_id());
        //  params.put("category_id", Application.getSelectedCategoryID());

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


                            Log.d("RepsReview Params",params.toString()+"");
                            Log.d("RepsReview logs",response.toString());


                            try{



                                JSONObject  object= response.optJSONObject("ecoachlabs");
                                String statuscode = object.getString("status");
                                String message = object.getString("msg");


                                if(!statuscode.equals("201")) {

                                    new SweetAlertDialog(RepDetails.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Sorry")
                                            .setContentText(message)
                                            .show();


                                }else{

                                    formatPenRepsReviewJSON(response);

                                }
                            }catch (Exception e){

                               e.printStackTrace();
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
                new SweetAlertDialog(RepDetails.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Something went wrong!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                onBackPressed();
                            }
                        })
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
                headers.put("auth-key", AppInstanceSettings.load(AppInstanceSettings.class,1).getUserkey());
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
    private void formatPenRepsReviewJSON(JSONObject response){

       // RepsReview repsReview = new RepsReview();
      // List<RepInvites> companiesArrayList = new ArrayList<>();

        try {

            JSONObject  object= response.optJSONObject("ecoachlabs");
            JSONArray info = object.getJSONArray("info");

            for (int i = 0 ; i < info.length(); i++) {

                JSONObject obj = info.getJSONObject(i);


                 repsReview = RepsReview.getRepInvite(obj.getString("email"),Application.AppUserKey);
                if(repsReview == null){


                    Log.d("CompanyRepInvite","companies was null");

                    repsReview =   new RepsReview();
                }


                String fullname = obj.getString("firstname") + "  " +obj.getString("lastname");
                repsReview.setFullname(fullname);
                repsReview.setUserKey(Application.AppUserKey);
                repsReview.setAvatar(obj.getString("avatar"));
                repsReview.setStorage(obj.getString("storage"));
                repsReview.setPath(obj.getString("path"));
                repsReview.setEmail(obj.getString("email"));
                repsReview.setDepartment(obj.getString("department"));
                repsReview.setLink_date(obj.getString("link_date"));
                repsReview.setRep_rating(obj.getString("rep_rating"));
                repsReview.setTotal_reviews(obj.getString("total_reviews"));
                repsReview.setTotal_chats(obj.getString("total_chats"));

            }





            ActiveAndroid.beginTransaction();
            try
            {




             repsReview.save();





                ActiveAndroid.setTransactionSuccessful();
            }
            finally {
                ActiveAndroid.endTransaction();


                setRepInformation(repsReview);

                //SetRecycleView(view);
            }

           // setdepartmentsRecyleview();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
