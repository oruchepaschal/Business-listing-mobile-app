package com.ecoach.cosapp.DataBase;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by apple on 4/12/17.
 */


@Table(name = "RepInvites")
public class RepInvites extends TruncatableModel {


    @Column(name = "email")
    private String email;

    @Column(name = "department")
    private String department;


    @Column(name = "userKey")
    private String userKey;

    @Column(name = "company_name")
    private String company_name;

    @Column(name = "company_id")
    private String company_id;


    @Column(name = "date")
    private String date;

    @Column(name = "status")
    private String status;

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

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public static List<RepInvites> getAllRepInvites() {
        return new Select()
                .from(RepInvites.class)
                .execute();
    }

    public static List<RepInvites> getAllRepInvites(String userID) {
        return new Select()
                .from(RepInvites.class).where("userKey = ?" ,userID)
                .execute();
    }

    public static List<RepInvites> getAllRepInvites(String userID,String status) {
        return new Select()
                .from(RepInvites.class).where("userKey = ?" ,userID).and("status = ?" ,status)
                .execute();
    }
    public static RepInvites getRepInvite(String email, String date, String userKey){

        return new Select()
                .from(RepInvites.class).where("date = ?",date).and("email = ?",email).and("userKey = ?",userKey)
                .executeSingle();
    }
}
