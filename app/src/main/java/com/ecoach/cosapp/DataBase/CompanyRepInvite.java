package com.ecoach.cosapp.DataBase;



import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by apple on 4/10/17.
 */

@Table(name = "CompanyRepInvite")
public class CompanyRepInvite extends Model{



    @Column(name = "request_id")
    private String request_id;

    @Column(name = "company_name")
    private String company_name;


    @Column(name = "company_id")
    private String company_id;


    @Column(name = "company_status")
    private String company_status;


    @Column(name = "path")
    private String path;


    @Column(name = "storage")
    private String storage;


    @Column(name = "department")
    private String department;

    @Column(name = "company_category")
    private String company_category;

    @Column(name = "avatar")
    private String avatar;



    @Column(name = "request_date")
    private String request_date;


    @Column(name = "user_id")
    private String user_id;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    @Column(name = "confirmation_code")
    private String confirmation_code;

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

    public String getCompany_status() {
        return company_status;
    }

    public void setCompany_status(String company_status) {
        this.company_status = company_status;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCompany_category() {
        return company_category;
    }

    public void setCompany_category(String company_category) {
        this.company_category = company_category;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRequest_date() {
        return request_date;
    }

    public void setRequest_date(String request_date) {
        this.request_date = request_date;
    }

    public String getConfirmation_code() {
        return confirmation_code;
    }

    public void setConfirmation_code(String confirmation_code) {
        this.confirmation_code = confirmation_code;
    }


    public static CompanyRepInvite getCompanyByID(String request_id,String user_id) {
        return new Select()
                .from(CompanyRepInvite.class)
                .where("request_id = ?",request_id).and("user_id = ?",user_id)
                .executeSingle();
    }


    public static CompanyRepInvite getRepReqByCompanyName(String companyName) {
        return new Select()
                .from(CompanyRepInvite.class)
                .where("company_name = ?",companyName)
                .executeSingle();
    }

    public static List<CompanyRepInvite> getCompanyRepInvitations(String user_id) {
        return new Select()
                .from(CompanyRepInvite.class).
                where("user_id = ?",user_id)
                .execute();
    }



}
