package com.ecoach.cosapp.DataBase;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * Created by apple on 4/3/17.
 */

@Table(name = "User")
public class User extends Model {


    @Column(name = "userkey")
    private String userkey;


    @Column(name = "email")
    private String email;

    @Column(name = "path")
    private String path;


    @Column(name = "storage")
    private String storage;


    @Column(name = "fname")
    private String fname;


    @Column(name = "lname")
    private String lname;


    @Column(name = "phone")
    private String phone;


    @Column(name = "avatar")
    private String avatar;


    @Column(name = "loc_lat")
    private String loc_lat;


    @Column(name = "loc_long")
    private String loc_long;

    @Column(name = "loc_desc")
    private String loc_desc;


    public String getUserkey() {
        return userkey;
    }

    public void setUserkey(String userkey) {
        this.userkey = userkey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLoc_lat() {
        return loc_lat;
    }

    public void setLoc_lat(String loc_lat) {
        this.loc_lat = loc_lat;
    }

    public String getLoc_long() {
        return loc_long;
    }

    public void setLoc_long(String loc_long) {
        this.loc_long = loc_long;
    }

    public String getLoc_desc() {
        return loc_desc;
    }

    public void setLoc_desc(String loc_desc) {
        this.loc_desc = loc_desc;
    }



    public static User getUserByKey(String key){

        return new Select()
                .from(User.class)
                .where("userkey = ?",key)
                .executeSingle();

    }
}
