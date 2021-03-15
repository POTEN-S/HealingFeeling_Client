package com.example.healingfeeling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.healingfeeling.databinding.ActivityMypageBinding;

public class MypageActivity extends AppCompatActivity {

    ActivityMypageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_mypage);

        binding.progressHappy.setProgress(50);
        binding.progressSad.setProgress(10);
        binding.progressAngry.setProgress(80);

    }
}