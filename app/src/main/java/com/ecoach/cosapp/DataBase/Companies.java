package com.ecoach.cosapp.DataBase;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;


@Table(name="Companies")
public class Companies extends Model {

    @Column(name = "companyName")
    private String companyName;

    @Column(name = "path")
    private String path;

    @Column(name = "storage")
    private String storage;


    @Column(name = "avatar")
    private String avatar;


    @Column(name = "rating")
    private String rating;

    @Column(name = "company_id")
    private String company_id;


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public static Companies getCompaniesByID(String category) {
        return new Select()
                .from(Companies.class)
                .where("company_id = ?",category)
                .executeSingle();
    }
    public static List<Companies> getAllCompanies() {
        return new Select()
                .from(Companies.class)
                .execute();
    }
}
