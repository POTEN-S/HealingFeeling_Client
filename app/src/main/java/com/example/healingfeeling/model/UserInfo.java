package com.example.healingfeeling.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class UserInfo {

        public String pw;
        public String name;
        public String birth;
        public String gender;


        public UserInfo(){
            // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
        }

        public UserInfo( String pw, String name, String birth, String gender
                        ) {
            this.pw = pw;
            this.name = name;
            this.birth = birth;
            this.gender = gender;

        }

        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("pw", pw);
            result.put("name", name);
            result.put("birth", birth);
            result.put("gender", gender);


            return result;
        }



}
