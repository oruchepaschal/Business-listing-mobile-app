package com.ecoach.cosapp.Activites.Company;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TabHost;

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
import com.ecoach.cosapp.Activites.CompanyDetailsTabbedActivities.Details;
import com.ecoach.cosapp.Activites.CompanyDetailsTabbedActivities.Map;
import com.ecoach.cosapp.Activites.CompanyDetailsTabbedActivities.Profile;
import com.ecoach.cosapp.Application.Application;
import com.ecoach.cosapp.DataBase.AppInstanceSettings;
import com.ecoach.cosapp.DataBase.Categories;
import com.ecoach.cosapp.DataBase.Departments;
import com.ecoach.cosapp.Http.APIRequest;
import com.ecoach.cosapp.Http.UploadBase64;
import com.ecoach.cosapp.Http.VolleySingleton;
import com.ecoach.cosapp.R;
import com.ecoach.cosapp.RecycleAdapters.DepartmentAdapter;

import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MyCompanyDepartments extends Activity {

    LinearLayoutManager linearLayoutManager;
    DepartmentAdapter departmentAdapter;
    RecyclerView departmentsRecyleview;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_company_departments);

        try{

            setdepartmentsRecyleview();

          if(Departments.getDepartmentsByCompanyID(Application.getSelectedCompanyObbject().getCompanyCuid()).size() == 0){

              loadcompanyDepartment();
          }  else{


              departmentAdapter.notifyDataSetChanged();


          }

        }catch (Exception e)
        {e.printStackTrace();}


    }

    void setdepartmentsRecyleview(){

        departmentsRecyleview =(RecyclerView)findViewById(R.id.departments);
        departmentAdapter = new DepartmentAdapter(MyCompanyDepartments.this, Departments.getDepartmentsByCompanyID(Application.getSelectedCompanyObbject().getCompanyCuid()));

        linearLayoutManager = new LinearLayoutManager(MyCompanyDepartments.this);
        departmentsRecyleview.setAdapter(departmentAdapter);
        departmentsRecyleview.setLayoutManager(linearLayoutManager);
    }

    private void loadcompanyDepartment(){



        final HashMap<String, String> params = new HashMap<String, String>();



        params.put("fetch_public_info", "1");
        params.put("scope","wide_company_departments");
        params.put("company_id",Application.getSelectedCompanyObbject().getCompanyCuid());

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




                            JSONObject  object= response.optJSONObject("ecoachlabs");
                            String statuscode = object.getString("status");
                            String message = object.getString("msg");

                            Departments departments;
                            List<Departments> departmentsList = new ArrayList<>();

                            if(statuscode.equals("201")){

                                JSONArray info = object.getJSONArray("info");
                                //   JSONArray info = object.getJSONArray("info");

                                for (int i = 0 ; i < info.length(); i++) {

                                    JSONObject obj = info.getJSONObject(i);


                                    Log.d("selectedCUD",Application.getSelectedCompanyObbject().getCompanyCuid());

                                    departments = Departments.getDepartmentsByIDByName(obj.getString("department"),Application.getSelectedCompanyObbject().getCompanyCuid());

                                    if(departments == null){

                                        departments= new Departments();
                                    }


                                    //departments.setDepartmentid(obj.getString("department_id"));
                                    departments.setCompany_id(Application.getSelectedCompanyObbject().getCompanyCuid());
                                    departments.setDepartmentname(WordUtils.capitalizeFully(obj.getString("department")));

                                    departmentsList.add(departments);
                                }
                            }


                            ActiveAndroid.beginTransaction();
                            try
                            {

                                for(Departments departments1 : departmentsList){



                                    Long id =   departments1.save();


                                    Log.d("department ID", "id"+id);

                                }




                                ActiveAndroid.setTransactionSuccessful();
                            }
                            finally {
                                ActiveAndroid.endTransaction();




                                setdepartmentsRecyleview();
                            }




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
            public java.util.Map<String, String> getHeaders() throws AuthFailureError {
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



}
