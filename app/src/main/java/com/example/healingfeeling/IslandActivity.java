package com.example.healingfeeling;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.healingfeeling.databinding.ActivityIslandBinding;

public class IslandActivity extends AppCompatActivity {

    ActivityIslandBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_island);

        binding.happyBtn.setOnClickListener(v ->startActivity(new Intent(this,MainActivity.class)));
        binding.sadBtn.setOnClickListener(v ->startActivity(new Intent(this,MainActivity.class)));
        binding.angryBtn.setOnClickListener(v ->startActivity(new Intent(this,MainActivity.class)));

        binding.myPage.setOnClickListener(v -> startActivity(new Intent(this,LoginActivity.class)));


    }
}
