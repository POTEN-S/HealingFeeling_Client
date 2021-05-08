package com.example.healingfeeling.ui.home;


import java.lang.reflect.Array;
import java.util.List;

public class Data {

    private String image;
    private String category;
    private String uid;
    private String favorite;
    private String title;
    private String subtitle;
    private String registercount;

    public Data(){}

    public String getPhoto(){
        return image;
    }

    public void setPhoto(String image){
        this.image = image;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getUid(){
        return uid;
    }

    public void setUid(String uid){
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }



    public String getFavorite() { return favorite; }

    public void setFavorite(String favorite) { this.favorite = favorite; }

    public String getRegisterCount() { return registercount; }

    public void setRegisterCount(String registercount) { this.registercount = registercount; }


}