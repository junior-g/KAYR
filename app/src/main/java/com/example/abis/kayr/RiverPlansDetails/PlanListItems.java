package com.example.abis.kayr.RiverPlansDetails;

/**
 * Created by abis.
 */

public class PlanListItems {

    String Title, Discription;
    int imgSrc;
    String url;

    public PlanListItems(String Title, String Disc) {
        this.Title=Title;
        this.Discription=Disc;
    }


    public String getTitle() {
        return Title.toUpperCase();
    }

    public String getDiscription() {
        return Discription.toUpperCase();
    }

    public int getImgSrc() {
        return imgSrc;
    }
}
