package com.example.healingfeeling.api;

public class Post {
    private String title;
    private String text;

    public String getText() {
        return text;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String s){
        title = s;
    }

    public void setText(String s){
        text = s;
    }
}
