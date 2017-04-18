package com.ecoach.cosapp.DataBase;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * Created by apple on 4/14/17.
 */


@Table(name = "RepsReview")
public class RepsReview extends TruncatableModel {


    @Column(name = "userKey")
    private String userKey;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "avatar")
    private String avatar;


    @Column(name = "storage")
    private String storage;

    @Column(name = "path")
    private String path;


    @Column(name = "email")
    private String email;


    @Column(name = "department")
    private String department;

    @Column(name = "link_date")
    private String link_date;



    @Column(name = "rep_rating")
    private String rep_rating;

    @Column(name = "total_reviews")
    private String total_reviews;

    @Column(name = "total_chats")
    private String total_chats;

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLink_date() {
        return link_date;
    }

    public void setLink_date(String link_date) {
        this.link_date = link_date;
    }

    public String getRep_rating() {
        return rep_rating;
    }

    public void setRep_rating(String rep_rating) {
        this.rep_rating = rep_rating;
    }

    public String getTotal_reviews() {
        return total_reviews;
    }

    public void setTotal_reviews(String total_reviews) {
        this.total_reviews = total_reviews;
    }

    public String getTotal_chats() {
        return total_chats;
    }

    public void setTotal_chats(String total_chats) {
        this.total_chats = total_chats;
    }

    public static RepsReview getRepInvite(String email,String userKey){

        return new Select()
                .from(RepsReview.class).where("email = ?",email).and("userKey = ? ",userKey)
                .executeSingle();
    }

}
