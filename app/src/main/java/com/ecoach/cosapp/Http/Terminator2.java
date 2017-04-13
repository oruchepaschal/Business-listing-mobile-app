package com.ecoach.cosapp.Http;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
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
import com.ecoach.cosapp.Application.Application;
import com.ecoach.cosapp.DataBase.CompanyRepInvite;
import com.ecoach.cosapp.DataBase.GalleryStorage;
import com.ecoach.cosapp.DataBase.Recommendation;
import com.ecoach.cosapp.DataBase.RepInvites;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 4/10/17.
 */

public class Terminator2 extends IntentService {
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    public Terminator2(String name) {
        super(name);
    }
    public Terminator2(){
        super(null);// That was the lacking constructor
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {


            Log.d("TS  has started","terminatot ");
            // MultipartVolley("company");


            try{

               // Recommendation.truncate(Recommendation.class);

               // getRecomendations();

            }catch (Exception e){
                e.printStackTrace();
            }

            try {

                loadRepInviteVolley();

            }catch (Exception e){

                e.printStackTrace();
            }



            try {

                loadPenRepInviteVolley();

            }catch (Exception E){


            }





        }
    }

    private void loadRepInviteVolley(){


        final HashMap<String, String> params = new HashMap<String, String>();





        params.put("fetch_private_info", "1");
        params.put("scope", "user_rep_requests");




        System.out.println("Log Params"+params.toString());

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



                            formatJSONLOCAL(response);









                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                        //  Message.messageShort(MyApplication.getAppContext(),""+tokenValue+"\n"+response.toString()+"\n"+booleaner);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)   {



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
                headers.put("auth-key", Application.AppUserKey);
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
    private void formatJSONLOCAL(JSONObject response){

        CompanyRepInvite companies;
        List<CompanyRepInvite> companiesArrayList = new ArrayList<>();

        try {

            JSONObject  object= response.optJSONObject("ecoachlabs");
            JSONArray info = object.getJSONArray("info");

            for (int i = 0 ; i < info.length(); i++) {

                JSONObject obj = info.getJSONObject(i);


                companies = CompanyRepInvite.getCompanyByID(obj.getString("request_id"),Application.AppUserKey);

                if(companies == null){


                    Log.d("CompanyRepInvite","companies was null");

                    companies =   new CompanyRepInvite();
                }


                companies.setUser_id(Application.AppUserKey);

                String company_name = obj.getString("company_name");
                companies.setCompany_name(company_name);

                String company_id = obj.getString("company_id");
                companies.setCompany_id(company_id);

                String company_status = obj.getString("company_status");
                companies.setCompany_status(company_status);


                String company_path = obj.getString("path");
                companies.setPath(company_path);


                String storage = obj.getString("storage");
                companies.setStorage(storage);


                String department = obj.getString("department");
                companies.setDepartment(department);

                String company_category = obj.getString("company_category");
                companies.setCompany_category(company_category);

                String request_date = obj.getString("request_date");
                companies.setRequest_date(request_date);


                String request_id = obj.getString("request_id");
                companies.setRequest_id(request_id);


                String confirmation_code = obj.getString("confirmation_code");
                companies.setConfirmation_code(confirmation_code);

                String avatar = obj.getString("avatar");
                companies.setAvatar(avatar);






                companiesArrayList.add(companies);


            }

            ActiveAndroid.beginTransaction();
            try
            {

                for(CompanyRepInvite verifiedCompanies : companiesArrayList){



                    Long id =   verifiedCompanies.save();


                    Log.d("CompanyRepInvite ID", "id"+id);

                }





                ActiveAndroid.setTransactionSuccessful();
            }
            finally {
                ActiveAndroid.endTransaction();




                //SetRecycleView(view);
            }




        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //load rep invites

    private void loadPenRepInviteVolley(){


        final HashMap<String, String> params = new HashMap<String, String>();





        params.put("fetch_admin_info", "1");
        params.put("scope", "all_reps");




        System.out.println("Log Params"+params.toString());

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



                            formatPenRepJSONLOCAL(response);









                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                        //  Message.messageShort(MyApplication.getAppContext(),""+tokenValue+"\n"+response.toString()+"\n"+booleaner);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)   {



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
                headers.put("auth-key", Application.AppUserKey);
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
    private void formatPenRepJSONLOCAL(JSONObject response){

        RepInvites companies;
        List<RepInvites> companiesArrayList = new ArrayList<>();

        try {

            JSONObject  object= response.optJSONObject("ecoachlabs");
            JSONArray info = object.getJSONArray("info");

            for (int i = 0 ; i < info.length(); i++) {

                JSONObject obj = info.getJSONObject(i);


                companies = RepInvites.getRepInvite(obj.getString("email"),obj.getString("date"),Application.AppUserKey);

                if(companies == null){


                    Log.d("CompanyRepInvite","companies was null");

                    companies =   new RepInvites();
                }

/*
* "email": "yawboafo77@gmail.com",
        "department": "any",
        "company_name": "B finance",
        "company_id": "6e137e75181bc04445c1b633f2df0275",
        "date": "2017-04-11 09:48:49",
        "status": "pending"
*
* */
                companies.setUserKey(Application.AppUserKey);

                String company_name = obj.getString("company_name");
                companies.setCompany_name(company_name);

                String company_id = obj.getString("company_id");
                companies.setCompany_id(company_id);

                String company_status = obj.getString("status");
                companies.setStatus(company_status);


                String compdate = obj.getString("date");
                companies.setDate(compdate);


                String department = obj.getString("department");
                companies.setDepartment(department);


                String email = obj.getString("email");
                companies.setEmail(email);







                companiesArrayList.add(companies);


            }

            ActiveAndroid.beginTransaction();
            try
            {

                for(RepInvites verifiedCompanies : companiesArrayList){



                    Long id =   verifiedCompanies.save();


                    Log.d("CompanyRepInvite ID", "id"+id);

                }





                ActiveAndroid.setTransactionSuccessful();
            }
            finally {
                ActiveAndroid.endTransaction();




                //SetRecycleView(view);
            }




        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    //load recomendations

    private void getRecomendations(){


        Log.d("recomendation","loading from background");

        final HashMap<String, String> params = new HashMap<String, String>();



        params.put("fetch_public_info",""+ "1");
        params.put("scope","recommendation");
        params.put("rec_lat","0.0");
        params.put("rec_long","0.0");


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



                            Log.d("recomendation logs",response.toString());

                            getRecomendationsformatJSONLOCAL(response);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                        //  Message.messageShort(MyApplication.getAppContext(),""+tokenValue+"\n"+response.toString()+"\n"+booleaner);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)   {



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
    private void getRecomendationsformatJSONLOCAL(JSONObject response){

        List<Recommendation> companiesArrayList = new ArrayList<Recommendation>();
        List<GalleryStorage> showcaseList = new ArrayList<GalleryStorage>();
        Recommendation companies;
        GalleryStorage galleryStorage;
        try {

            JSONObject  object= response.optJSONObject("ecoachlabs");
            JSONArray info = object.getJSONArray("info");

            for (int i = 0 ; i < info.length(); i++) {

                JSONObject obj = info.getJSONObject(i);


                //companies = Recommendation.getCompaniesByID(obj.getString("companyCuid"),Application.getSelectedCategoryID());

                companies = Recommendation.getCompaniesByID(obj.getString("companyCuid"));

                if(companies == null){


                    Log.d("recomendation","companies was null");

                    companies =   new Recommendation();
                }



                companies =   new Recommendation();
                Log.d("companies","companies was not null");

                companies.setCategory_id(Application.getSelectedCategoryID());

                String company_id = obj.getString("companyCuid");
                companies.setCompanyCuid(company_id);

                String company_name = obj.getString("companyName");
                companies.setCompanyName(company_name);



                String companyCategory = obj.getString("companyCategory");
                companies.setCompanyCategory(companyCategory);



                String companyCategoryID = obj.getString("companyCategoryId");
                companies.setCompanyCategoryid(companyCategoryID);


                String company_path = obj.getString("Path");
                companies.setPath(company_path);


                String company_avator = obj.getString("avatarLocation");
                companies.setAvatarLocation(company_avator);


                String active = obj.getString("companyStatus");
                companies.setCompanyStatus(active);


                String company_rating = obj.getString("companyRating");
                companies.setCompanyRating(company_rating);

                String address = obj.getString("Address");
                companies.setAddress(address);

                String bio = obj.getString("Bio");
                companies.setBio(bio);


                String Phone1 = obj.getString("Phone1");
                companies.setPhone1(Phone1);


                String Phone2 = obj.getString("Phone2");
                companies.setPhone2(Phone2);

                String email = obj.getString("Email");
                companies.setEmail(email);


                String Website = obj.getString("Website");
                companies.setWebsite(Website);


                String companyLat = obj.getString("companyLat");
                companies.setCompanyLat(companyLat);


                String companyLong = obj.getString("companyLong");
                companies.setCompanyLong(companyLong);


                String coverLocation = obj.getString("coverLocation");
                companies.setCoverLocation(coverLocation);


                String companyStorageName = obj.getString("companyStorageName");
                companies.setCompanyStorageName(companyStorageName);


                companies.setForUser(false);






                companiesArrayList.add(companies);


            }

            ActiveAndroid.beginTransaction();
            try
            {

                for(Recommendation verifiedCompanies : companiesArrayList){



                    Long id =   verifiedCompanies.save();


                    Log.d("Company ID", "id"+id);

                }





                ActiveAndroid.setTransactionSuccessful();
            }
            finally {
                ActiveAndroid.endTransaction();




                //SetRecycleView(view);
            }





        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
