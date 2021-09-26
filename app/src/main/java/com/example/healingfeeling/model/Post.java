package com.example.healingfeeling.model;

import java.util.ArrayList;
import java.util.List;

public class Post {
    public String category;
    public String title;
    public String subTitle;
    public String image;
    public String emotion;
    public ArrayList<String> favorite;
    public int register;
    public Double ratings;


    public Post(){
        // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
    }

    public Post(String category, String title, String subTitle, String image, String emotion, ArrayList<String> favorite, int register, Double ratings
    ) {
        this.category = category;
        this.title = title;
        this.subTitle=subTitle;
        this.image = image;
        this.emotion=emotion;
        this.favorite=favorite;
        this.register=register;
        this.ratings=ratings;

    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getImageUrl() {
        return image;
    }

    public Integer getRegister() { return register; }


}
