package com.example.healingfeeling.ui.home;


public class Data {

    private String photo;
    private String category;
    private String uid;
    private Boolean favorite;
    private String title;
    private String content;
    private String subtitle;
    private String registerCount;

    public Data(){}

    public String getPhoto(){
        return photo;
    }

    public void setPhoto(String photo){
        this.photo = photo;
    }

    public String getCategory(){
        return photo;
    }

    public void setCategory(String category){
        this.photo = photo;
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



    public boolean getFavorite() { return favorite; }

    public void setFavorite(boolean favorite) { this.favorite = favorite; }

    public String getRegisterCount() { return registerCount; }

    public void setRegisterCount(String registerCount) { this.registerCount = registerCount; }


}