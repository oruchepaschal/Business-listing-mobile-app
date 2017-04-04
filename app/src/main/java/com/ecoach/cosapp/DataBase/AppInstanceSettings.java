package com.ecoach.cosapp.DataBase;

import android.graphics.PorterDuff;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by apple on 4/3/17.
 */


@Table(name = "AppInstanceSettings")
public class AppInstanceSettings extends Model {

    @Column(name = "isloggedIn")
    private boolean isloggedIn;


    @Column(name = "userkey")
    private String userkey;


    public boolean isloggedIn() {
        return isloggedIn;
    }

    public void setIsloggedIn(boolean isloggedIn) {
        this.isloggedIn = isloggedIn;
    }


    public String getUserkey() {
        return userkey;
    }

    public void setUserkey(String userkey) {
        this.userkey = userkey;
    }
}
