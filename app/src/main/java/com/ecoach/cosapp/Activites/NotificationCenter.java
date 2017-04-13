package com.ecoach.cosapp.Activites;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
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
import com.ecoach.cosapp.Http.APIRequest;
import com.ecoach.cosapp.Http.VolleySingleton;
import com.ecoach.cosapp.R;
import com.ecoach.cosapp.RecycleAdapters.RepInviteAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import info.hoang8f.widget.FButton;

public class NotificationCenter extends AppCompatActivity {


    String requestid;
    SwipeRefreshLayout refreshLayout;
    RepInviteAdapter repInviteAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_center);



        if (getSupportActionBar() != null){

            getSupportActionBar().setTitle("Notification");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        SetupRecycleview();
        refreshLayout=(SwipeRefreshLayout)findViewById(R.id.refreshlayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                loadRepInviteVolley();

            refreshLayout.setRefreshing(false);

            }
        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void SetupRecycleview(){

        recyclerView = (RecyclerView)findViewById(R.id.notificationRecycle);

        repInviteAdapter = new RepInviteAdapter(NotificationCenter.this, CompanyRepInvite.getCompanyRepInvitations(Application.AppUserKey));

        linearLayoutManager = new LinearLayoutManager(NotificationCenter.this);
        recyclerView.setAdapter(repInviteAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(NotificationCenter.this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                TextView textView = (TextView)view.findViewById(R.id.rep_companyName);

                final CompanyRepInvite companyRepInvite = CompanyRepInvite.getRepReqByCompanyName(textView.getText().toString());
                requestid= companyRepInvite.getRequest_id();

                Log.d("requestid",requestid);


                FButton acceptInvite = (FButton)view.findViewById(R.id.acceptButton);
                acceptInvite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new SweetAlertDialog(NotificationCenter.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Accept Invite ?")
                                .setContentText("Are you Sure you want to Accept Invite ?")
                                .setCancelText("Cancel")
                                .setConfirmText("Yes Accept")
                                .showCancelButton(true)
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        //
                                         sweetAlertDialog.dismiss();
                                        AcceptRepInviteVolley(companyRepInvite.getCompany_id(),companyRepInvite.getConfirmation_code());

                                    }
                                })
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.cancel();
                                    }

                                })
                                .show();

                    }
                });

                FButton reject = (FButton)view.findViewById(R.id.rejectButton);
                reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new SweetAlertDialog(NotificationCenter.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Reject Invite ?")
                                .setContentText("Are you Sure you want to Reject Invite ?")
                                .setCancelText("Cancel")
                                .setConfirmText("Yes Reject")
                                .showCancelButton(true)
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        //

                                        sweetAlertDialog.dismiss();
                                        RejectRepInviteVolley(companyRepInvite.getCompany_id(),companyRepInvite.getConfirmation_code());

                                    }
                                })
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.cancel();
                                    }

                                })
                                .show();

                    }
                });


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



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


                repInviteAdapter = new RepInviteAdapter(NotificationCenter.this, CompanyRepInvite.getCompanyRepInvitations(Application.AppUserKey));
                linearLayoutManager = new LinearLayoutManager(NotificationCenter.this);
                recyclerView.setAdapter(repInviteAdapter);
                recyclerView.setLayoutManager(linearLayoutManager);
                refreshLayout.setRefreshing(false);

                //SetRecycleView(view);
            }




        } catch (Exception e) {
            e.printStackTrace();
        }


    }



    private void AcceptRepInviteVolley(String companyID,String code){
       final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Accepting Invite ..");
        pDialog.setCancelable(false);
        pDialog.show();

        final HashMap<String, String> params = new HashMap<String, String>();


        params.put("is_accept_rep_invite", "1");
        params.put("company_id", companyID);
        params.put("confirmation_code", code);



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
                               pDialog.dismiss();

                            JSONObject  object= response.optJSONObject("ecoachlabs");
                            String statuscode = object.getString("status");
                            String message = object.getString("msg");

                            if(statuscode.equals("201")){

                                Log.d("requestid",requestid);
                                new Delete().from(CompanyRepInvite.class).where("request_id = ?", requestid).execute();
                                repInviteAdapter = new RepInviteAdapter(NotificationCenter.this, CompanyRepInvite.getCompanyRepInvitations(Application.AppUserKey));
                                recyclerView.setAdapter(repInviteAdapter);
                                recyclerView.invalidate();

                                new SweetAlertDialog(NotificationCenter.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Great !")
                                        .setContentText(message)
                                        .show();





                            }else{

                                new SweetAlertDialog(NotificationCenter.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Something went wrong !")
                                        .setContentText(message)
                                        .show();

                            }





                          //  AcceptformatJSONLOCAL(response);









                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                        //  Message.messageShort(MyApplication.getAppContext(),""+tokenValue+"\n"+response.toString()+"\n"+booleaner);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)   {

                pDialog.dismiss();

                new SweetAlertDialog(NotificationCenter.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Something went wrong !")
                        .setContentText("Try Again")
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
    private void RejectRepInviteVolley(String companyID,String code){
        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Rejecting Invite ..");
        pDialog.setCancelable(false);
        pDialog.show();

        final HashMap<String, String> params = new HashMap<String, String>();


        params.put("is_reject_rep_invite", "1");
        params.put("company_id", companyID);
        //params.put("confirmation_code", code);



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
                            pDialog.dismiss();

                            JSONObject  object= response.optJSONObject("ecoachlabs");
                            String statuscode = object.getString("status");
                            String message = object.getString("msg");

                            if(statuscode.equals("201")){


                                Log.d("requestid",requestid);

                                new Delete().from(CompanyRepInvite.class).where("request_id = ?", requestid).execute();
                                repInviteAdapter = new RepInviteAdapter(NotificationCenter.this, CompanyRepInvite.getCompanyRepInvitations(Application.AppUserKey));
                                recyclerView.setAdapter(repInviteAdapter);
                                recyclerView.invalidate();

                                new SweetAlertDialog(NotificationCenter.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Great !")
                                        .setContentText(message)
                                        .show();





                            }else{

                                new SweetAlertDialog(NotificationCenter.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Something went wrong !")
                                        .setContentText(message)
                                        .show();

                            }





                            //  AcceptformatJSONLOCAL(response);









                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                        //  Message.messageShort(MyApplication.getAppContext(),""+tokenValue+"\n"+response.toString()+"\n"+booleaner);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)   {

                pDialog.dismiss();

                new SweetAlertDialog(NotificationCenter.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Something went wrong !")
                        .setContentText("Try Again")
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
    private void AcceptformatJSONLOCAL(JSONObject response){

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


                repInviteAdapter = new RepInviteAdapter(NotificationCenter.this, CompanyRepInvite.getCompanyRepInvitations(Application.AppUserKey));
                linearLayoutManager = new LinearLayoutManager(NotificationCenter.this);
                recyclerView.setAdapter(repInviteAdapter);
                recyclerView.setLayoutManager(linearLayoutManager);
                refreshLayout.setRefreshing(false);

                //SetRecycleView(view);
            }




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
