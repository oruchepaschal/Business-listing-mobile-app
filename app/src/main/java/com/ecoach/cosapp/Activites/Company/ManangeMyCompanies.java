package com.ecoach.cosapp.Activites.Company;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.ecoach.cosapp.Activites.SearchActivity;
import com.ecoach.cosapp.Activites.UserAccounts.CreateAccount;
import com.ecoach.cosapp.Application.Application;
import com.ecoach.cosapp.DataBase.AppInstanceSettings;
import com.ecoach.cosapp.DataBase.Departments;
import com.ecoach.cosapp.DataBase.GalleryStorage;
import com.ecoach.cosapp.DataBase.VerifiedCompanies;
import com.ecoach.cosapp.Http.APIRequest;
import com.ecoach.cosapp.Http.VolleySingleton;
import com.ecoach.cosapp.R;
import com.ecoach.cosapp.RecycleAdapters.MyCompaniesAdapter;

import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ManangeMyCompanies extends AppCompatActivity implements  Addcompany.onBackerPressed{
    private SwipeRefreshLayout refreshLayout;

    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    RecyclerView myCompanies;



    RecyclerView.LayoutManager layoutManager,verticalManager;
    MyCompaniesAdapter myCompaniesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manange_companies);


        try {
            Log.d("companies", "userKey" + AppInstanceSettings.load(AppInstanceSettings.class, 1).getUserkey());
        }catch (Exception e){

            e.printStackTrace();
        }
            if (getSupportActionBar() != null) {

                getSupportActionBar().setTitle("My Companies");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }


            initviews();
            setFloatingButton();
            setRefreshView();


            if (VerifiedCompanies.getAllCompaniesBy4User(true, Application.AppUserKey).size() == 0) {

                getCategoriesLocal();
            } else {

                setRecycleView();

            }


       // if(Departments.getAllDepartments().size() == 0){


       // }
       // loadcompanyDepartment();
    }

    private void setRefreshView() {
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshSupport);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCategoriesLocal();
                //setRecycleView();
            }
        });
    }

    private void initviews() {
        myCompanies=(RecyclerView)findViewById(R.id.myCompanies);
        //new RecyclerTouchListener(CompaniesActivity.this, recyclerView, new ClickListener()
        myCompanies.addOnItemTouchListener(new RecyclerTouchListener(ManangeMyCompanies.this, myCompanies, new ClickListener() {
            @Override
            public void onClick(View view, int position) {


                TextView txthiddenId=(TextView)view.findViewById(R.id.companyid);
                try {
                    String id = txthiddenId.getText().toString();
                    Application.setSelectedCategoryID(id);


                    Log.d("company id", "selected company ID" + Application.getSelectedCategoryID());
                    VerifiedCompanies verifiedCompanies = VerifiedCompanies.getCompanyByID(Application.getSelectedCategoryID());
                    Application.setSelectedCompanyObbject(verifiedCompanies);
                    Log.d("company id", "selected company Name" + verifiedCompanies.getCompanyName());
                    Intent intent = new Intent(ManangeMyCompanies.this,MyCompanyDetails.class);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void setRecycleView(){

        List<VerifiedCompanies> verifiedCompaniesList =  new ArrayList<>();

        boolean valueIsLoggedIn= false;

        try{

           valueIsLoggedIn = AppInstanceSettings.load(AppInstanceSettings.class,1).isloggedIn();


            if(valueIsLoggedIn){


                verifiedCompaniesList = VerifiedCompanies.getAllCompaniesBy4User(true,Application.AppUserKey);
            }else{

                verifiedCompaniesList = Collections.emptyList();
            }



        }catch (Exception e){




        }


        try {

            myCompaniesAdapter = new MyCompaniesAdapter(ManangeMyCompanies.this, verifiedCompaniesList);

            layoutManager = new GridLayoutManager(ManangeMyCompanies.this, 2);
            myCompanies.setAdapter(myCompaniesAdapter);
            myCompanies.setLayoutManager(layoutManager);


            refreshLayout.setRefreshing(false);

        }catch (Exception e){

            e.printStackTrace();
        }
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

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    void setFloatingButton(){

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addcompany);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try{

                    if(AppInstanceSettings.load(AppInstanceSettings.class,1).isloggedIn() == false){

                        new SweetAlertDialog(ManangeMyCompanies.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Sorry,")
                                .setContentText("You need to Login")
                                .show();



                    }else{

                        Intent intent = new Intent(ManangeMyCompanies.this,Addcompany.class);
                        startActivity(intent);

                    }


                }catch (Exception e){

                    new SweetAlertDialog(ManangeMyCompanies.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Sorry,")
                            .setContentText("You need to Login")
                            .show();

                }




            }
        });

    }

    private void getCategoriesLocal(){



        Log.d("companies","loading from background");

        final HashMap<String, String> params = new HashMap<String, String>();



        params.put("fetch_admin_info",""+ "1");
        params.put("scope","my_companies");
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


                        try {

                            refreshLayout.setRefreshing(false);

                            Log.d("logs",response.toString());


                            try{

                                new Delete().from(VerifiedCompanies.class).where("forUser = ?",true).and("userID = ?",
                                        AppInstanceSettings.load(AppInstanceSettings.class,1).getUserkey()).execute();


                            }catch (Exception e){


                            }
                         formatJSONLOCAL(response);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                        //  Message.messageShort(MyApplication.getAppContext(),""+tokenValue+"\n"+response.toString()+"\n"+booleaner);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)   {

                refreshLayout.setRefreshing(false);

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
    private void formatJSONLOCAL(JSONObject response){

        List<VerifiedCompanies> companiesArrayList = new ArrayList<VerifiedCompanies>();
        List<GalleryStorage> showcaseList = new ArrayList<GalleryStorage>();
        VerifiedCompanies companies;
        GalleryStorage galleryStorage;
        try {

            JSONObject  object= response.optJSONObject("ecoachlabs");
            JSONArray info = object.getJSONArray("info");

            for (int i = 0 ; i < info.length(); i++) {

                JSONObject obj = info.getJSONObject(i);


                companies = VerifiedCompanies.getCompaniesByIDand4User(obj.getString("companyCuid"),true);

                if(companies == null){


                    Log.d("companies","companies was null");

                    companies =   new VerifiedCompanies();
                }
                Log.d("companies","companies was not null");


                String companyCategoryId = obj.getString("companyCategoryId");
                companies.setCategory_id(companyCategoryId);

                String company_id = obj.getString("companyCuid");
                companies.setCompanyCuid(company_id);

                String company_name = obj.getString("companyName");
                companies.setCompanyName(company_name);


                String company_path = obj.getString("Path");
                companies.setPath(company_path);


                String company_avator = obj.getString("avatarLocation");
                companies.setAvatarLocation(company_avator);


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


                String accountType = obj.getString("accountType");
                companies.setAccountType(accountType);


                String compCatName = obj.getString("companyCategory");
                companies.setCompanyCategory(compCatName);


                companies.setForUser(true);


                companies.setUserID(AppInstanceSettings.load(AppInstanceSettings.class,1).getUserkey());

                //TODO Create the department schema


                JSONArray showcase = obj.getJSONArray("showcase");

                for (int A = 0 ; A < showcase.length(); A++) {

                    JSONObject showcaseobj = showcase.getJSONObject(i);
                    String storageID = showcaseobj.getString("showcaseId");
                    galleryStorage = GalleryStorage.getStorageSingle(companies.getCompanyCuid(),storageID);
                    if(galleryStorage == null){

                        galleryStorage = new GalleryStorage();

                    }
                    galleryStorage.setCompanyCuid(companies.getCompanyCuid());

                    String showcaseLocation = showcaseobj.getString("showcaseLocation");
                    galleryStorage.setShowcaseLocation(showcaseLocation);



                    String showType = showcaseobj.getString("showcaseType");
                    galleryStorage.setShowcaseType(showType);




                    showcaseList.add(galleryStorage);


                }


                companiesArrayList.add(companies);


            }

            ActiveAndroid.beginTransaction();
            try
            {

                for(VerifiedCompanies verifiedCompanies : companiesArrayList){



                    Long id =   verifiedCompanies.save();


                    Log.d("Company ID", "id"+id);

                }



                for(GalleryStorage galleryStorage1 : showcaseList){



                    Long id =   galleryStorage1.save();


                    Log.d("galleryStorage1", "id"+id);

                }

                ActiveAndroid.setTransactionSuccessful();
            }
            finally {
                ActiveAndroid.endTransaction();




                //SetRecycleView(view);
            }


            setRecycleView();
           // companiesAdapter.notifyDataSetChanged();
            //recyclerView.setAdapter(companiesAdapter);
            //recyclerView.setLayoutManager(linearLayoutManager);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackerPressed() {

      List<VerifiedCompanies> verifiedCompaniesList = VerifiedCompanies.getAllCompaniesBy4User(true);

        myCompaniesAdapter = new MyCompaniesAdapter(ManangeMyCompanies.this, verifiedCompaniesList);
        myCompaniesAdapter.notifyDataSetChanged();

        //layoutManager = new GridLayoutManager(ManangeMyCompanies.this, 2);
        //myCompanies.setAdapter(myCompaniesAdapter);
      //  myCompanies.setLayoutManager(layoutManager);

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

    private void AlertDialog(){
       // List<VerifiedCompanies> verifiedCompaniesList =  new ArrayList<>();
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(ManangeMyCompanies.this);
        //builderSingle.setTitle("Filter");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ManangeMyCompanies.this, android.R.layout.select_dialog_item);

        arrayAdapter.add("All");
        arrayAdapter.add("Rep");
        arrayAdapter.add("Admin");



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


                    List<VerifiedCompanies>   verifiedCompaniesList = VerifiedCompanies.getAllCompaniesBy4User(true,Application.AppUserKey);
                    myCompaniesAdapter = new MyCompaniesAdapter(ManangeMyCompanies.this, verifiedCompaniesList);

                    // layoutManager = new GridLayoutManager(ManangeMyCompanies.this, 2);
                    myCompanies.setAdapter(myCompaniesAdapter);
                    myCompanies.setLayoutManager(layoutManager);






                }else {

                    List<VerifiedCompanies>   verifiedCompaniesList = VerifiedCompanies.getAllCompaniesBy4User(true,strName.toLowerCase(),Application.AppUserKey);
                    myCompaniesAdapter = new MyCompaniesAdapter(ManangeMyCompanies.this, verifiedCompaniesList);

                   // layoutManager = new GridLayoutManager(ManangeMyCompanies.this, 2);
                    myCompanies.setAdapter(myCompaniesAdapter);
                    myCompanies.setLayoutManager(layoutManager);

                }
            }
        });
        builderSingle.show();

    }


}
