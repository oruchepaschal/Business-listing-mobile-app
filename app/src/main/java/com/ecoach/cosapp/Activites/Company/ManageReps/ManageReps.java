package com.ecoach.cosapp.Activites.Company.ManageReps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
import com.ecoach.cosapp.DataBase.AppInstanceSettings;
import com.ecoach.cosapp.DataBase.RepInvites;
import com.ecoach.cosapp.Http.APIRequest;
import com.ecoach.cosapp.Http.VolleySingleton;
import com.ecoach.cosapp.Models.RepInvite;
import com.ecoach.cosapp.R;
import com.ecoach.cosapp.RecycleAdapters.RepRequestAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import info.hoang8f.widget.FButton;

public class ManageReps extends AppCompatActivity implements AddRepsDialog.OnAddRepListener {
SwipeRefreshLayout swipelayout;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    LinearLayoutManager linearLayoutManager;
    RepRequestAdapter repRequestAdapter;

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_reps);


        if (getSupportActionBar() != null){

            getSupportActionBar().setTitle("Manage Reps");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        recyclerView =(RecyclerView)findViewById(R.id.recycleviewReps);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(ManageReps.this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                TextView hiddenId = (TextView)view.findViewById(R.id.hiddenID);

                String repEmail = hiddenId.getText().toString();

                final RepInvites repInvites = RepInvites.load(RepInvites.class,Long.parseLong(repEmail));
                Application.activeRepInvites = repInvites;

               // Toast.makeText(ManageReps.this,repEmail,Toast.LENGTH_LONG).show();

                final FButton button =(FButton)view.findViewById(R.id.actionButton);
                final String status = button.getText().toString();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {




                        new SweetAlertDialog(ManageReps.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText(status + "!")
                                .setContentText("Are you sure you want to perform this action .. ?")
                                .setConfirmText("Yes Continue")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();

                                        switch (status){

                                            case "Remove Rep":

                                                //   new Sw
                                                RepActionVolley(repInvites,"Remove Rep");

                                                break;

                                            case "Cancel Request":
                                                RepActionVolley(repInvites,"Cancel Request");
                                                break;
                                        }
                                    }
                                }).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                                .setCancelText("No Cancel")
                                .show();




                    }
                });
                final FButton morebutton =(FButton)view.findViewById(R.id.viewMoreButon);
                morebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        //final RepInvites repInvites = RepInvites.load(RepInvites.class,Long.parseLong(repEmail));

                        Intent intent = new Intent(ManageReps.this,RepDetails.class);
                        startActivity(intent);
                    }
                });





            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
       // recyclerView.addOnItemTouchListener(new RecyclerTouchListener(ManageReps.this, recyclerView, new ClickListener())
        swipelayout=(SwipeRefreshLayout)findViewById(R.id.swipelayout);
        swipelayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPenRepInviteVolley();

                swipelayout.setRefreshing(false);
            }
        });

        setFloatingButton();
        setdepartmentsRecyleview();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.company_filter, menu);



        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.filter_settings :

                AlertDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void AlertDialog(){
        // List<VerifiedCompanies> verifiedCompaniesList =  new ArrayList<>();
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(ManageReps.this);
        //builderSingle.setTitle("Filter");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ManageReps.this, android.R.layout.select_dialog_item);

        arrayAdapter.add("All");
        arrayAdapter.add("Pending");
        arrayAdapter.add("Confirmed");



        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);

                if (strName.equals("All")){


                    repRequestAdapter = new RepRequestAdapter(ManageReps.this, RepInvites.getAllRepInvites(Application.AppUserKey));

                    linearLayoutManager = new LinearLayoutManager(ManageReps.this);
                    recyclerView.setAdapter(repRequestAdapter);
                    recyclerView.setLayoutManager(linearLayoutManager);






                }else {

                    repRequestAdapter = new RepRequestAdapter(ManageReps.this, RepInvites.getAllRepInvites(Application.AppUserKey,strName.toLowerCase()));

                    linearLayoutManager = new LinearLayoutManager(ManageReps.this);
                    recyclerView.setAdapter(repRequestAdapter);
                    recyclerView.setLayoutManager(linearLayoutManager);

                }
            }
        });
        builderSingle.show();

    }
    void showDialog(DialogFragment dialogFragment, String tag) {


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(tag);
        if (prev != null) {

            ft.remove(prev);
        }
        ft.addToBackStack(null);

        dialogFragment.show(ft, tag);
    }

    void setdepartmentsRecyleview(){


        repRequestAdapter = new RepRequestAdapter(ManageReps.this, RepInvites.getAllRepInvites(Application.AppUserKey));

        linearLayoutManager = new LinearLayoutManager(ManageReps.this);
        recyclerView.setAdapter(repRequestAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    void setFloatingButton(){

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addreps);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddRepsDialog addRepsDialog=new AddRepsDialog().newInstance("","");
               showDialog(addRepsDialog,"checkout");





            }
        });

    }

    private void addRepVolley(RepInvite repInvite){
        final SweetAlertDialog pDialog;
        pDialog = new SweetAlertDialog(ManageReps.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Sending Rep Invite ..");
        pDialog.setCancelable(false);
        pDialog.show();



        final HashMap<String, String> params = new HashMap<String, String>();



        params.put("is_add_rep",""+ "1");
        params.put("company_id",repInvite.getCompany_id());
        params.put("rep_email",repInvite.getRep_email());
        params.put("department",repInvite.getDepartment_id());


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

                                new SweetAlertDialog(ManageReps.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Sorry,Try Again")
                                        .setContentText(message)
                                        .show();

                            }else {



                                new SweetAlertDialog(ManageReps.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Success")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                                Application.activeRepInvite = new RepInvite("","","");
                                                  sweetAlertDialog.dismiss();
                                                loadPenRepInviteVolley();
                                            }
                                        })
                                        .setContentText("Rep Invitation Succesfully Sent")
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
                new SweetAlertDialog(ManageReps.this, SweetAlertDialog.ERROR_TYPE)
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
    private void RepActionVolley(final RepInvites repInvite, String token){
        final SweetAlertDialog pDialog;
        pDialog = new SweetAlertDialog(ManageReps.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));

        if(token == "Remove Rep")
        pDialog.setTitleText(" Rep Invite ..");
        else if(token == "Cancel Request")
            pDialog.setTitleText(" Rep Invite ..");
        pDialog.setCancelable(false);
        pDialog.show();



        final HashMap<String, String> params = new HashMap<String, String>();

        if(token == "Remove Rep"){

            params.put("is_remove_rep",""+ "1");
            params.put("company_id",repInvite.getCompany_id());
            params.put("rep_email",repInvite.getEmail());
        }

        else if(token == "Cancel Request")
        {

            params.put("is_cancel_rep_invite",""+ "1");
            params.put("company_id",repInvite.getCompany_id());
            params.put("rep_email",repInvite.getEmail());
        }





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

                                new SweetAlertDialog(ManageReps.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Sorry,Try Again")
                                        .setContentText(message)
                                        .show();

                            }else {



                                new SweetAlertDialog(ManageReps.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Success")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                               RepInvites.delete(RepInvites.class,repInvite.getId());
                                                setdepartmentsRecyleview();
                                                sweetAlertDialog.dismiss();
                                                //  finish();
                                            }
                                        })
                                        //.setContentText("Rep Invitation Succesfully Sent")
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
                new SweetAlertDialog(ManageReps.this, SweetAlertDialog.ERROR_TYPE)
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

    private void loadPenRepInviteVolley(){

        RepInvites.truncate(RepInvites.class);
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

            setdepartmentsRecyleview();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onAddRepInteraction(RepInvite repInvite) {
        addRepVolley(repInvite);

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
