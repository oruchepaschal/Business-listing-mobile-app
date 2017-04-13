package com.ecoach.cosapp.DataBase;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by apple on 4/2/17.
 */


@Table(name="GalleryStorage")
public class GalleryStorage  extends Model{

    @Column(name = "companyCuid")
    private String companyCuid;


    @Column(name = "recomendationid")
    private String recomendationid;


    @Column(name = "showcaseId")
    private String showcaseId;


    @Column(name = "showcaseLocation")
    private String showcaseLocation;


    @Column(name = "showcaseType")
    private String showcaseType;


    public String getCompanyCuid() {
        return companyCuid;
    }

    public void setCompanyCuid(String companyCuid) {
        this.companyCuid = companyCuid;
    }

    public String getShowcaseId() {
        return showcaseId;
    }

    public void setShowcaseId(String showcaseId) {
        this.showcaseId = showcaseId;
    }

    public String getShowcaseLocation() {
        return showcaseLocation;
    }

    public void setShowcaseLocation(String showcaseLocation) {
        this.showcaseLocation = showcaseLocation;
    }

    public String getShowcaseType() {
        return showcaseType;
    }

    public void setShowcaseType(String showcaseType) {
        this.showcaseType = showcaseType;
    }


    public String getRecomendationid() {
        return recomendationid;
    }

    public void setRecomendationid(String recomendationid) {
        this.recomendationid = recomendationid;
    }

    public static GalleryStorage getReomendationStorageSingle(String recomendationid, String showcaseId) {
        return new Select()
                .from(GalleryStorage.class)
                .where("recomendationid = ?",recomendationid).and("showcaseId = ?",showcaseId)
                .executeSingle();
    }

    public static GalleryStorage getStorageSingle(String company_id, String showcaseId) {
        return new Select()
                .from(GalleryStorage.class)
                .where("companyCuid = ?",company_id).and("showcaseId = ?",showcaseId)
                .executeSingle();
    }


    public static List<GalleryStorage> getCompanyGalleryItemsByCompanyID(String company_id) {
        return new Select()
                .from(GalleryStorage.class)
                .where("companyCuid = ?",company_id)
                .execute();
    }
}
