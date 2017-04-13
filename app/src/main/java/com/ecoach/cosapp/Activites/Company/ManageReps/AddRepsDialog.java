package com.ecoach.cosapp.Activites.Company.ManageReps;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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
import com.ecoach.cosapp.DataBase.Categories;
import com.ecoach.cosapp.DataBase.Departments;
import com.ecoach.cosapp.DataBase.VerifiedCompanies;
import com.ecoach.cosapp.Http.APIRequest;
import com.ecoach.cosapp.Http.VolleySingleton;
import com.ecoach.cosapp.Models.RepInvite;
import com.ecoach.cosapp.R;

import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import info.hoang8f.widget.FButton;


public class AddRepsDialog extends DialogFragment {

    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnAddRepListener mListener;

    public AddRepsDialog() {
        // Required empty public constructor
    }


    FButton closebutton,invitebutton;
    Spinner departmentsSpinner,companies;
    EditText emailAdd;
    ArrayAdapter<String> spinnerArrayAdapter;

    String companySelectedID;
    String departSelected;

    public static AddRepsDialog newInstance(String param1, String param2) {
        AddRepsDialog fragment = new AddRepsDialog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.fragment_add_reps, container, false);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        emailAdd =(EditText)view.findViewById(R.id.repemail);
        if(Application.activeRepInvite != null)
            emailAdd.setText(Application.activeRepInvite.getRep_email());

        closebutton=(FButton)view.findViewById(R.id.closebut);
        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        invitebutton=(FButton)view.findViewById(R.id.invitebutton);
        invitebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(emailAdd.getText().length() == 0){
                    emailAdd.setError("Cannot be empty");
                }else{


                    try{
                        String email = emailAdd.getText().toString();
                        String companySelected = companies.getSelectedItem().toString();
                         companySelectedID = VerifiedCompanies.getCompanyByIDByName(companySelected).getCompanyCuid();
                         departSelected = departmentsSpinner.getSelectedItem().toString();
                        //String departSelectedID = Departments.getDepartmentsByIDByName(departSelected).getDepartmentid();


                        RepInvite repInvite = new RepInvite(companySelectedID,departSelected,email);
                        Application.activeRepInvite = repInvite;

                        getDialog().dismiss();
                        mListener.onAddRepInteraction(repInvite);

                    } catch (Exception e){

                        e.printStackTrace();
                    }




                }
            }
        });

        departmentsSpinner = (Spinner)view.findViewById(R.id.spinnerDepartments);
        //spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getList("department")); //selected item will look like a spinner set from XML
        //spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //departments.setAdapter(spinnerArrayAdapter);

        companies = (Spinner)view.findViewById(R.id.spinnerCompanies);
        spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getList("company")); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        companies.setAdapter(spinnerArrayAdapter);
        companies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);

               companySelectedID =  VerifiedCompanies.getCompanyByIDByName(item.toString()).getCompanyCuid();
                Log.d("selectedCompanyID",companySelectedID + "list count " +getList("department").length);

                if(getList("department").length == 0){

                    loadcompanyDepartment();
                }

               spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getList("department")); //selected item will look like a spinner set from XML
               spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                departmentsSpinner.setAdapter(spinnerArrayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddRepListener) {
            mListener = (OnAddRepListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnAddRepListener {
        // TODO: Update argument type and name
        void onAddRepInteraction(RepInvite repInvite);
    }


    public String[] getList(String token){

        List<String> list = new ArrayList<>();

try{
        switch (token){


            case "department":



                for(Departments departments : Departments.getDepartmentsByCompanyID(companySelectedID)){

                    list.add(departments.getDepartmentname());
                }
                break;
            case "company":
                for(VerifiedCompanies companies : VerifiedCompanies.getAllCompaniesBy4User(true,"admin",Application.AppUserKey)){

                    list.add(companies.getCompanyName());
                }
                break;
        }

}catch (Exception e){e.printStackTrace();}
        String[] simpleList = list.toArray(new String[list.size()]);


        return simpleList;

    }

    private void loadcompanyDepartment(){



        final HashMap<String, String> params = new HashMap<String, String>();



        params.put("fetch_public_info", "1");
        params.put("scope","wide_company_departments");
        params.put("company_id",companySelectedID);

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


                                    Log.d("selectedCUD",companySelectedID);

                                    departments = Departments.getDepartmentsByIDByName(obj.getString("department"),companySelectedID);

                                    if(departments == null){

                                        departments= new Departments();
                                    }


                                    //departments.setDepartmentid(obj.getString("department_id"));
                                    departments.setCompany_id(companySelectedID);
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

                                spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getList("department")); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                departmentsSpinner.setAdapter(spinnerArrayAdapter);


                               // setdepartmentsRecyleview();
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
