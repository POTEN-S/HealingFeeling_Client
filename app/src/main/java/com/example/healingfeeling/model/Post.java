package com.example.healingfeeling.model;

public class Post {
    public String category;
    public String title;
    public String review;
    public String image;
    public String emotion;


    public Post(){
        // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
    }

    public Post( String category, String title, String review, String image,String emotion
    ) {
        this.category = category;
        this.title = title;
        this.review = review;
        this.image = image;
        this.emotion=emotion;

    }
}
