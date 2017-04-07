package com.ecoach.cosapp.Http;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;

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
import com.ecoach.cosapp.Activites.Company.Addcompany;
import com.ecoach.cosapp.Application.Application;
import com.ecoach.cosapp.DataBase.AppInstanceSettings;
import com.ecoach.cosapp.DataBase.Categories;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.ecoach.cosapp.Application.Application.requestQueue;
import static com.ecoach.cosapp.R.drawable.user;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UploadBase64 extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.ecoach.cosapp.Http.action.FOO";
    private static final String ACTION_BAZ = "com.ecoach.cosapp.Http.action.BAZ";

    // TODO: Rename parameters
    private static final String poi = "poi";
    private static final String EXTRA_PARAM2 = "com.ecoach.cosapp.Http.extra.PARAM2";

    public UploadBase64() {
        super("UploadBase64");
    }
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {


            Log.d("service has started",Application.getCompanyCert());
           // MultipartVolley("company");
            createAccountVolley();
            /*final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }*/
        }
    }


    private void createAccountVolley(){


        final HashMap<String, String> params = new HashMap<String, String>();



/*
* usage
base_profile_pic
base_showcase
base_chat_bg
base_cover
base_cert
*in
*
* */


        params.put("is_base64_upload", "1");
        params.put("poi", "company");
        params.put("usage", "multiple");
        params.put("base_profile_pic", Application.getCompanyLogo());
       // params.put("base_showcase", Application.getCompanyLogo());
        params.put("base_chat_bg", Application.getCompanyChatBack());
        params.put("base_cover", Application.getCompanyCover());
        params.put("base_cert", Application.getCompanyCert());
        params.put("company_id", Application.getLast_company_id());



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

    private void MultipartVolley(final String poi){

        Map<String,String> map=new TreeMap<>();
        //Map map=new TreeMap<>();
        map.put("is_base64_upload", "1");
        map.put("poi", poi);
        map.put("usage", "cover");
        map.put("file_upload","data:image/png;base64,"+ Application.getCompanyLogo());
        map.put("company_id", Application.getLast_company_id());


        for(Map.Entry<String,String> entry:map.entrySet()){

           Log.d("multipated","\nKey = "+entry.getKey()+"\n Value = "+entry.getValue());

        }

        String BASE_URL = APIRequest.BASE_URL;

        MultipartRequest multipartRequest = new MultipartRequest(BASE_URL, map, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response!=null){
                    Log.d("oxinbo", "response message from  =" + response);

                    try{






                    }catch (Exception e){


                        e.printStackTrace();
                    }



                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                //  pDialog.dismiss();
                // Handle your error types accordingly.For Timeout & No connection error, you can show 'retry' button.
                // For AuthFailure, you can re login with user credentials.
                // For ClientError, 400 & 401, Errors happening on client side when sending api request.
                // In this case you can check how client is forming the api and debug accordingly.
                // For ServerError 5xx, you can do retry or handle accordingly.
                // utils.RetryAlertDialog(OnBoardCustomerActivity.this, "error", "pls retry");

                //  utils.ErrorAlertDialog(OnBoardCustomerActivity.this, message, " Failed").show();
                if (error instanceof NetworkError) {
                    String msg="Network Error Occurred !";
                    //  RetryAlertDialog(msg);
                } else if (error instanceof ServerError) {
                    String msg="Server Error Occurred !";
                    // RetryAlertDialog(msg);
                } else if (error instanceof AuthFailureError) {
                    String msg="Application Error Occurred !";
                    // RetryAlertDialog(msg);

                } else if (error instanceof ParseError) {


                    String msg="A Parser Error Occurred !";
                    //   RetryAlertDialog(msg);
                } else if (error instanceof NoConnectionError) {


                    String msg="No Internet Connection !";
                    //   RetryAlertDialog(msg);

                } else if (error instanceof TimeoutError) {
                    String msg="Syncing Timed Out !";
                    //  RetryAlertDialog(msg);
                }
            }
        }){

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("auth-key", AppInstanceSettings.load(AppInstanceSettings.class,1).getUserkey());
                return headers;

            }
        };

        int socketTimeout = 999999999;//4 minutes - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(
                socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        multipartRequest.setRetryPolicy(policy);
        requestQueue.add(multipartRequest);



    }
}
