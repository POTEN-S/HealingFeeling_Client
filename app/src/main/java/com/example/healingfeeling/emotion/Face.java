package com.example.healingfeeling.emotion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Face {



    @SerializedName("emotion")
    @Expose
    private Emotion emotion;

    public Emotion getEmotion() {
        return emotion;
    }

}
