package com.ecoach.cosapp.Application;

import android.content.Context;






import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.ecoach.cosapp.DataBase.Categories;


/**
 * Created by apple on 3/25/17.
 */

public class Application extends android.app.Application {


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
        //configurationBuilder.addModelClass(Users.class);
      //  configurationBuilder.addModelClass(Appointments.class);

        ActiveAndroid.initialize(configurationBuilder.create());
    }

}
