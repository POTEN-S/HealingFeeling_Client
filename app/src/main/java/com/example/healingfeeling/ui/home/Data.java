package com.example.healingfeeling.ui.home;


public class Data {

    private String photo;
    private String title;
    private String content;
    private String subtitle;
    private int resId;
    private boolean favorite;
    private int registerCount;

    public Data(){}

    public String getPhoto(){
        return photo;
    }

    public void setPhoto(String photo){
        this.photo = photo;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }


    public boolean getFavorite() { return favorite; }

    public void setFavorite(boolean favorite) { this.favorite = favorite; }

    public int getRegisterCount() { return registerCount; }

    public void setRegisterCount(int registerCount) { this.registerCount = registerCount; }


}