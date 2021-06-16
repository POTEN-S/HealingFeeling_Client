package com.example.healingfeeling.api;

public class Recommend {
    private String userId;
    private String emotion;
    private String category;

    public String getUserId() {
        return userId;
    }

    public String getEmotion(){
        return emotion;
    }

    public String getCategory(){
        return category;
    }

    public void setUserId(String s){
        userId = s;
    }

    public void setEmotion(String s){
        emotion = s;
    }

    public void setCategory(String s){
        category = s;
    }
}
