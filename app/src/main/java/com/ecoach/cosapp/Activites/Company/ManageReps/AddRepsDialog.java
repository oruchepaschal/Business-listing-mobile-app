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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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
import com.ecoach.cosapp.DataBase.Departments;
import com.ecoach.cosapp.DataBase.VerifiedCompanies;
import com.ecoach.cosapp.Http.APIRequest;
import com.ecoach.cosapp.Http.VolleySingleton;
import com.ecoach.cosapp.Models.RepInvite;
import com.ecoach.cosapp.R;

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
    Spinner departments,companies;
    EditText emailAdd;
    ArrayAdapter<String> spinnerArrayAdapter;



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
                        String companySelectedID = VerifiedCompanies.getCompanyByIDByName(companySelected).getCompanyCuid();
                        String departSelected = departments.getSelectedItem().toString();
                        String departSelectedID = Departments.getDepartmentsByIDByName(departSelected).getDepartmentid();


                        RepInvite repInvite = new RepInvite(companySelectedID,departSelectedID,email);
                        Application.activeRepInvite = repInvite;

                        getDialog().dismiss();
                        mListener.onAddRepInteraction(repInvite);

                    } catch (Exception e){

                        e.printStackTrace();
                    }




                }
            }
        });

        departments = (Spinner)view.findViewById(R.id.spinnerDepartments);
        spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getList("department")); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departments.setAdapter(spinnerArrayAdapter);

        companies = (Spinner)view.findViewById(R.id.spinnerCompanies);
        spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getList("company")); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        companies.setAdapter(spinnerArrayAdapter);
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


        switch (token){


            case "department":
                for(Departments departments : Departments.getAllDepartments()){

                    list.add(departments.getDepartmentname());
                }
                break;
            case "company":
                for(VerifiedCompanies companies : VerifiedCompanies.getAllCompaniesBy4User(true,"admin")){

                    list.add(companies.getCompanyName());
                }
                break;
        }


        String[] simpleList = list.toArray(new String[list.size()]);


        return simpleList;

    }



}
