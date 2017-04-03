package com.ecoach.cosapp.Application;

import android.content.Context;






import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.ecoach.cosapp.DataBase.Categories;
import com.ecoach.cosapp.DataBase.Companies;
import com.ecoach.cosapp.DataBase.GalleryStorage;
import com.ecoach.cosapp.DataBase.VerifiedCompanies;
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


        @Override
        public void onCreate() {
            super.onCreate();

            context = getApplicationContext();




            try{


                setFontsMaster();
                initializeDB();


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
