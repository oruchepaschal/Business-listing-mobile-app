package com.ecoach.cosapp.Models;

/**
 * Created by apple on 3/30/17.
 */

public class DetailsItem {

    private String drawable;
    private String title;

    public DetailsItem(String drawable,String title){

        this.drawable = drawable;
        this.title = title;

    }

    public String getDrawable() {
        return drawable;
    }

    public void setDrawable(String drawable) {
        this.drawable = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
