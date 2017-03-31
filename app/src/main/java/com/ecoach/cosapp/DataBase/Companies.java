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


    @Column(name = "category_id")
    private String category_id;


    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

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

    public static Companies getCompaniesByID(String company_id,String category_id) {
        return new Select()
                .from(Companies.class)
                .where("company_id = ?",company_id).and("category_id = ?",category_id)
                .executeSingle();
    }
    public static List<Companies> getAllCompanies(String category_id) {
        return new Select()
                .from(Companies.class).where("category_id = ?",category_id)
                .execute();
    }
}
