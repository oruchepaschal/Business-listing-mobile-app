package com.ecoach.cosapp.Activites;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

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
import com.ecoach.cosapp.DataBase.Categories;
import com.ecoach.cosapp.DataBase.Companies;
import com.ecoach.cosapp.Http.APIRequest;
import com.ecoach.cosapp.Http.VolleySingleton;
import com.ecoach.cosapp.R;
import com.ecoach.cosapp.RecycleAdapters.CompaniesViewAdapter;
import com.ecoach.cosapp.RecycleAdapters.MainCategoryAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.activeandroid.Cache.getContext;

public class CompaniesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies);
        getCategories();
    }

    private void getCategories(){


        final ProgressDialog pDialog  = new ProgressDialog(CompaniesActivity.this);
        pDialog.setMessage(". ...");
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //  pDialog.setIcon(R.drawable.ic_synce);
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();



        final HashMap<String, String> params = new HashMap<String, String>();



        params.put("fetch_public_info",""+ "1");
        params.put("scope","company_list");
        params.put("category_id", Application.getSelectedCategoryID());

        volleySingleton= VolleySingleton.getsInstance();
        requestQueue=VolleySingleton.getRequestQueue();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                APIRequest.BASE_URL,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    //Log.d("Params",params+"");
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.hide();
                        try {



                            Log.d("logs",response.toString());

                            formatJSON(response);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                        //  Message.messageShort(MyApplication.getAppContext(),""+tokenValue+"\n"+response.toString()+"\n"+booleaner);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)   {
                pDialog.dismiss();

                //  dialogs.SimpleWarningAlertDialog("Transmission Error", "Connection Failed").show();
                Log.d("volley.Response", error.toString());



                pDialog.dismiss();
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


    private void SetupRecycleview(){

        recyclerView = (RecyclerView)findViewById(R.id.companyRecycle);
        CompaniesViewAdapter companiesAdapter = new CompaniesViewAdapter(getContext(), Companies.getAllCompanies());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CompaniesActivity.this);
        recyclerView.setAdapter(companiesAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        //        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getContext(),Categories.getAllCategories());


    }
    private void formatJSON(JSONObject response){

        List<Companies> companiesArrayList = new ArrayList<Companies>();

        try {

            JSONObject  object= response.optJSONObject("ecoachlabs");
            JSONArray info = object.getJSONArray("info");

            for (int i = 0 ; i < info.length(); i++) {

                JSONObject obj = info.getJSONObject(i);
                String company_id = obj.getString("id");
                Companies company = Companies.getCompaniesByID(company_id);

                if(company == null)
                    company = new Companies();


                //String category_id = obj.getString("category_id");

                company.setCompany_id(company_id);

                String company_name = obj.getString("company_name");
                company.setCompanyName(company_name);



                String company_path = obj.getString("path");
                company.setPath(company_path);


                String company_storage = obj.getString("storage");
                company.setStorage(company_storage);


                String company_avator = obj.getString("avatar");
                company.setAvatar(company_avator);


                String company_rating = obj.getString("rating");
                company.setRating(company_rating);


                companiesArrayList.add(company);


            }
/*
*      "company_name": "Swift Cars Enterprise",
        "id": "f90ab0404da904474b21eb12bf7b5d6d",
        "path": "http://api.ecoachlabs.com/v1/cosapp/uploads/companies/",
        "storage": "a99f38414617b6652102fee1f817784f",
        "avatar": null,
        "rating": "0.0"
*
*
*
*
* **/

            ActiveAndroid.beginTransaction();
            try
            {

                for(Companies center : companiesArrayList){


                    Long id =   center.save();


                    Log.d("Company ID", "id"+id);

                }



                ActiveAndroid.setTransactionSuccessful();
            }
            finally {
                ActiveAndroid.endTransaction();


                SetupRecycleview();

                //SetRecycleView(view);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
