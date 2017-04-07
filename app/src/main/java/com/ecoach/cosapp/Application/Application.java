package com.ecoach.cosapp.Application;

import android.content.Context;






import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.android.volley.RequestQueue;
import com.ecoach.cosapp.DataBase.AppInstanceSettings;
import com.ecoach.cosapp.DataBase.Categories;
import com.ecoach.cosapp.DataBase.Companies;
import com.ecoach.cosapp.DataBase.GalleryStorage;
import com.ecoach.cosapp.DataBase.User;
import com.ecoach.cosapp.DataBase.VerifiedCompanies;
import com.ecoach.cosapp.Http.VolleySingleton;
import com.ecoach.cosapp.Models.Company;
import com.ecoach.cosapp.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Created by apple on 3/25/17.
 */

public class Application extends android.app.Application {


    public static String selectedCategoryID;
    public static String selectedCategoryName;


    public static String selectedCompanyID;
    public static String selectedCompanyName;

    public static VerifiedCompanies selectedCompanyObbject;

    private static Context context;

    private static String companyCover = "";
    private static String companyLogo = "";
    private static String companyCert = "";
    private static String companyChatBack = "";
    private static String last_company_id = "";

    public static VolleySingleton volleySingleton;
    public static RequestQueue requestQueue;

        @Override
        public void onCreate() {
            super.onCreate();

            context = getApplicationContext();




            try{


                setFontsMaster();
                initializeDB();

                volleySingleton= VolleySingleton.getsInstance();
                requestQueue=VolleySingleton.getRequestQueue();


            }catch (Exception e){

                e.printStackTrace();
            }




        }


    public static Context getAppContext() {
        return context;
    }


    protected void initializeDB() {
        Configuration.Builder configurationBuilder = new Configuration.Builder(this);


        configurationBuilder.addModelClasses(Categories.class);
        configurationBuilder.addModelClass(Companies.class);
        configurationBuilder.addModelClass(GalleryStorage.class);
        configurationBuilder.addModelClass(VerifiedCompanies.class);
        configurationBuilder.addModelClass(User.class);
        configurationBuilder.addModelClass(AppInstanceSettings.class);

        ActiveAndroid.initialize(configurationBuilder.create());
    }


    public static VerifiedCompanies getSelectedCompanyObbject() {
        return selectedCompanyObbject;
    }

    public static void setSelectedCompanyObbject(VerifiedCompanies selectedCompanyObbject) {
        Application.selectedCompanyObbject = selectedCompanyObbject;
    }

    public static String getSelectedCompanyID() {
        return selectedCompanyID;
    }

    public static void setSelectedCompanyID(String selectedCompanyID) {
        Application.selectedCompanyID = selectedCompanyID;
    }

    public static String getSelectedCompanyName() {
        return selectedCompanyName;
    }

    public static void setSelectedCompanyName(String selectedCompanyName) {
        Application.selectedCompanyName = selectedCompanyName;
    }

    public static String getSelectedCategoryID() {
        return selectedCategoryID;
    }

    public static void setSelectedCategoryID(String selectedCategoryID) {
        Application.selectedCategoryID = selectedCategoryID;
    }

    public static String getSelectedCategoryName() {
        return selectedCategoryName;
    }

    public static void setSelectedCategoryName(String selectedCategoryName) {
        Application.selectedCategoryName = selectedCategoryName;
    }


    public static String getCompanyCover() {
        return companyCover;
    }

    public static void setCompanyCover(String companyCover) {
        Application.companyCover = companyCover;
    }

    public static String getCompanyLogo() {
        return companyLogo;
    }

    public static void setCompanyLogo(String companyLogo) {
        Application.companyLogo = companyLogo;
    }

    public static String getCompanyCert() {
        return companyCert;
    }

    public static void setCompanyCert(String companyCert) {
        Application.companyCert = companyCert;
    }

    public static String getCompanyChatBack() {
        return companyChatBack;
    }

    public static void setCompanyChatBack(String companyChatBack) {
        Application.companyChatBack = companyChatBack;
    }


    public static String getLast_company_id() {
        return last_company_id;
    }

    public static void setLast_company_id(String last_company_id) {
        Application.last_company_id = last_company_id;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        Application.context = context;
    }

    private void setFontsMaster(){

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Assistant-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

    }
}
