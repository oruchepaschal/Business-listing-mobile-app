package com.ecoach.cosapp.DataBase;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;


@Table(name="Categories")
public class Categories extends Model {

    @Column(name = "categoryNames")
    private String categoryNames;

    @Column(name = "categoryBackgroundImage")
    private String categoryBackgroundImage;

    @Column(name = "categoryIcons")
    private String categoryIcons;



    @Column(name = "path")
    private String path;


    @Column(name = "categoryID")
    private String categoryID;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(String categoryNames) {
        this.categoryNames = categoryNames;
    }

    public String getCategoryBackgroundImage() {
        return categoryBackgroundImage;
    }

    public void setCategoryBackgroundImage(String categoryBackgroundImage) {
        this.categoryBackgroundImage = categoryBackgroundImage;
    }

    public String getCategoryIcons() {
        return categoryIcons;
    }

    public void setCategoryIcons(String categoryIcons) {
        this.categoryIcons = categoryIcons;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public static Categories getCategoryByID(String category) {
        return new Select()
                .from(Categories.class)
                .where("categoryID = ?",category)
                .executeSingle();
    }
    public static List<Categories> getAllCategories() {
        return new Select()
                .from(Categories.class)
                .execute();
    }


    public static Categories getCategoryIDByName(String category_name) {
        return new Select()
                .from(Categories.class)
                .where("categoryNames = ?",category_name)
                .executeSingle();
    }

}
