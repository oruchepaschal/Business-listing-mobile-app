package com.ecoach.cosapp.Application;

import android.content.Context;






import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.ecoach.cosapp.DataBase.Categories;
import com.ecoach.cosapp.DataBase.Companies;
import com.ecoach.cosapp.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Created by apple on 3/25/17.
 */

public class Application extends android.app.Application {


    public static String selectedCategoryID;

    private static Context context;


        @Override
        public void onCreate() {
            super.onCreate();

            context = getApplicationContext();




            try{



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
      //  configurationBuilder.addModelClass(Appointments.class);

        ActiveAndroid.initialize(configurationBuilder.create());
    }


    public static String getSelectedCategoryID() {
        return selectedCategoryID;
    }

    public static void setSelectedCategoryID(String selectedCategoryID) {
        Application.selectedCategoryID = selectedCategoryID;
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
