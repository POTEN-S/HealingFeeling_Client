package com.example.healingfeeling.ui.home;


public class Data {

    private String title;
    private String content;
    private String subtitle;
    private int resId;
    private int favoriteCount;
    private int registerCount;

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

    public int getFavoriteCount() { return favoriteCount; }

    public void setFavoriteCount(int favoriteCount) { this.favoriteCount = favoriteCount; }

    public int getRegisterCount() { return registerCount; }

    public void setRegisterCount(int registerCount) { this.registerCount = registerCount; }


}