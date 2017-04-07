package com.ecoach.cosapp.DataBase;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by apple on 4/2/17.
 */
@Table(name="VerifiedCompanies")
public class VerifiedCompanies extends Model {


    @Column(name = "category_id")
    private String category_id;

    @Column(name = "Address")
    private String Address;

    @Column(name = "avatarLocation")
    private String avatarLocation;

    @Column(name = "Bio")
    private String Bio;


    @Column(name = "companyCuid")
    private String companyCuid;


    @Column(name = "companyStatus")
    private String companyStatus;

    @Column(name = "companyLat")
    private String companyLat;

    @Column(name = "companyLong")
    private String companyLong;


    @Column(name = "companyName")
    private String companyName;


    @Column(name = "companyRating")
    private String companyRating;

    @Column(name = "companyStorageName")
    private String companyStorageName;

    @Column(name = "coverLocation")
    private String coverLocation;

    @Column(name = "Email")
    private String Email;

    @Column(name = "Path")
    private String Path;

    @Column(name = "Phone1")
    private String Phone1;


    @Column(name = "Phone2")
    private String Phone2;

    @Column(name = "Website")
    private String Website;


    @Column(name = "forUser")
    private boolean forUser;

    @Column(name = "accountType")
    private String accountType;


    @Column(name = "companyCategory")
    private String companyCategory;


    public String getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(String companyStatus) {
        this.companyStatus = companyStatus;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getAvatarLocation() {
        return avatarLocation;
    }

    public void setAvatarLocation(String avatarLocation) {
        this.avatarLocation = avatarLocation;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public String getCompanyCuid() {
        return companyCuid;
    }

    public void setCompanyCuid(String companyCuid) {
        this.companyCuid = companyCuid;
    }

    public String getCompanyLat() {
        return companyLat;
    }

    public void setCompanyLat(String companyLat) {
        this.companyLat = companyLat;
    }

    public String getCompanyLong() {
        return companyLong;
    }

    public void setCompanyLong(String companyLong) {
        this.companyLong = companyLong;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyRating() {
        return companyRating;
    }

    public void setCompanyRating(String companyRating) {
        this.companyRating = companyRating;
    }

    public String getCompanyStorageName() {
        return companyStorageName;
    }

    public void setCompanyStorageName(String companyStorageName) {
        this.companyStorageName = companyStorageName;
    }

    public String getCoverLocation() {
        return coverLocation;
    }

    public void setCoverLocation(String coverLocation) {
        this.coverLocation = coverLocation;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getPhone1() {
        return Phone1;
    }

    public void setPhone1(String phone1) {
        Phone1 = phone1;
    }

    public String getPhone2() {
        return Phone2;
    }

    public void setPhone2(String phone2) {
        Phone2 = phone2;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public boolean isForUser() {
        return forUser;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setForUser(boolean forUser) {
        this.forUser = forUser;
    }


    public String getCompanyCategory() {
        return companyCategory;
    }

    public void setCompanyCategory(String companyCategory) {
        this.companyCategory = companyCategory;
    }

    public static VerifiedCompanies getCompanyByID(String company_id) {
        return new Select()
                .from(VerifiedCompanies.class)
                .where("companyCuid = ?",company_id)
                .executeSingle();
    }

    public static VerifiedCompanies getCompaniesByID(String company_id,String category_id) {

        return new Select()
                .from(VerifiedCompanies.class)
                .where("companyCuid = ?",company_id).and("category_id = ?",category_id)
                .executeSingle();
    }
    public static VerifiedCompanies getCompaniesByIDand4User(String company_id,boolean token) {

        return new Select()
                .from(VerifiedCompanies.class)
                .where("companyCuid = ?",company_id).and("forUser = ?",token)
                .executeSingle();
    }
    public static List<VerifiedCompanies> getAllCompanies(String category_id,String companyStatus) {
        return new Select()
                .from(VerifiedCompanies.class).where("category_id = ?",category_id).and("companyStatus = ?",companyStatus)
                .execute();
    }

    public static List<VerifiedCompanies> getAllCompaniesBy4User(boolean forUser) {
        return new Select()
                .from(VerifiedCompanies.class).where("forUser = ?",forUser)
                .execute();
    }
}
