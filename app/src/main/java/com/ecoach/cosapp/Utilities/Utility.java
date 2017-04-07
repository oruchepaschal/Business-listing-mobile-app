package com.ecoach.cosapp.Utilities;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by apple on 4/5/17.
 */

public class Utility {

    public  static String Base64String(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);



        return "data:image/png;base64,"+encoded;
    }


    public static  String getInitialsFromString(String word){

        String initials = "";
        for (String s : word.split(" ")) {
            initials+=s.charAt(0);
        }


        return initials;
    }
}
