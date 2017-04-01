package com.ecoach.cosapp.Activites;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

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
import com.wang.avi.AVLoadingIndicatorView;

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
    CompaniesViewAdapter companiesAdapter;
    private AVLoadingIndicatorView avi;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_companies);

        recyclerView = (RecyclerView)findViewById(R.id.companyRecycle);



        try{

            if(Companies.getAllCompanies(Application.getSelectedCategoryID()).size() == 0){
Log.d("was i called ","yes i was called");

                getCategories();
            }else{

                SetupRecycleview(Companies.getAllCompanies(Application.getSelectedCategoryID()));
                getCategoriesLocal();
                Log.d("was i called ","no i was not");
            }


        }catch (Exception e){

            e.printStackTrace();


        }





        // add back arrow to toolbar
        if (getSupportActionBar() != null){

            getSupportActionBar().setTitle(Application.getSelectedCategoryName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


    }

    private void getCategories(){

        avi=(AVLoadingIndicatorView)findViewById(R.id.avi);
        avi.setVisibility(View.VISIBLE);
        avi.show();



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
                        avi.hide();

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
                avi.hide();


                //  dialogs.SimpleWarningAlertDialog("Transmission Error", "Connection Failed").show();
                Log.d("volley.Response", error.toString());



                avi.hide();

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
    private void getCategoriesLocal(){



        Log.d("companies","loading from background");

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
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    private void SetupRecycleview(  List<Companies> companiesArrayList){


        companiesAdapter = new CompaniesViewAdapter(CompaniesActivity.this, companiesArrayList);

        linearLayoutManager = new LinearLayoutManager(CompaniesActivity.this);
        recyclerView.setAdapter(companiesAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(CompaniesActivity.this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                TextView tv=(TextView)view.findViewById(R.id.labelTxt);
                String selectedCompanyName=tv.getText().toString();

                Application.setSelectedCompanyName(selectedCompanyName);


                TextView id=(TextView)view.findViewById(R.id.companyid);
                String selectedid=id.getText().toString();

                Application.setSelectedCompanyID(selectedid);


                Intent intent = new Intent(CompaniesActivity.this,CompanyDetails.class);
                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        //        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getContext(),Categories.getAllCategories());


    }
    private void formatJSON(JSONObject response){

        List<Companies> companiesArrayList = new ArrayList<Companies>();
        Companies companies;
        try {

            JSONObject  object= response.optJSONObject("ecoachlabs");
            JSONArray info = object.getJSONArray("info");

            for (int i = 0 ; i < info.length(); i++) {

                JSONObject obj = info.getJSONObject(i);


                 companies = Companies.getCompaniesByID(obj.getString("id"),Application.getSelectedCategoryID());

                if(companies == null){


                    Log.d("companies","companies was null");

                     companies =   new Companies();
                }
                Log.d("companies","companies was not null");

               companies.setCategory_id(Application.getSelectedCategoryID());

                String company_id = obj.getString("id");
                companies.setCompany_id(company_id);

                String company_name = obj.getString("company_name");
                companies.setCompanyName(company_name);


                String company_path = obj.getString("path");
                companies.setPath(company_path);


                String company_storage = obj.getString("storage");
                companies.setStorage(company_storage);



                String company_avator = obj.getString("avatar");
                companies.setAvatar(company_avator);


                String company_rating = obj.getString("rating");
                companies.setRating(company_rating);

                companiesArrayList.add(companies);


            }

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




                //SetRecycleView(view);
            }
            SetupRecycleview(companiesArrayList);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private void formatJSONLOCAL(JSONObject response){

        List<Companies> companiesArrayList = new ArrayList<Companies>();
        Companies companies;
        try {

            JSONObject  object= response.optJSONObject("ecoachlabs");
            JSONArray info = object.getJSONArray("info");

            for (int i = 0 ; i < info.length(); i++) {

                JSONObject obj = info.getJSONObject(i);


                companies = Companies.getCompaniesByID(obj.getString("id"),Application.getSelectedCategoryID());

                if(companies == null){


                    Log.d("companies","companies was null local load");

                    companies =   new Companies();
                }
                Log.d("companies","companies was not null local load");

                companies.setCategory_id(Application.getSelectedCategoryID());

                String company_id = obj.getString("id");
                companies.setCompany_id(company_id);

                String company_name = obj.getString("company_name");
                companies.setCompanyName(company_name);


                String company_path = obj.getString("path");
                companies.setPath(company_path);


                String company_storage = obj.getString("storage");
                companies.setStorage(company_storage);



                String company_avator = obj.getString("avatar");
                companies.setAvatar(company_avator);


                String company_rating = obj.getString("rating");
                companies.setRating(company_rating);

                companiesArrayList.add(companies);


            }

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




                //SetRecycleView(view);
            }
            //SetupRecycleview(companiesArrayList);
            companiesAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(companiesAdapter);
            recyclerView.setLayoutManager(linearLayoutManager);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private  ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener){
            this.clickListener=clickListener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child= recyclerView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clickListener!=null){


                        clickListener.onLongClick(child,recyclerView.getChildPosition(child));
                    }

                }
            });
        }
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child= rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clickListener!=null && gestureDetector.onTouchEvent(e)){


                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public static interface ClickListener{

        public void onClick(View view,int position);
        public void onLongClick(View view,int position);

    }
}
