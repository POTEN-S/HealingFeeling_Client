package com.example.healingfeeling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.healingfeeling.databinding.ActivityIslandBinding;

public class IslandActivity extends AppCompatActivity {

    ActivityIslandBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_island);

        binding.happyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IslandActivity.this,MainActivity.class);

                SharedPreferences sharedPreferences= getSharedPreferences("test", MODE_PRIVATE);    // test 이름의 기본모드 설정
                SharedPreferences.Editor editor= sharedPreferences.edit();
                SharedPreferences.Editor editor2= sharedPreferences.edit();//sharedPreferences를 제어할 editor를 선언

                editor.putString("emotion","행복"); // key,value 형식으로 저장
                editor2.putString("chat","happychat");
                editor.commit();
                editor2.commit();//최종 커밋. 커밋을 해야 저장이 된다.



                startActivity(intent);
            }
        });
        binding.sadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IslandActivity.this,MainActivity.class);

                SharedPreferences sharedPreferences= getSharedPreferences("test", MODE_PRIVATE);    // test 이름의 기본모드 설정
                SharedPreferences.Editor editor= sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언
                SharedPreferences.Editor editor2= sharedPreferences.edit();//sharedPreferences를 제어할 editor를 선언

                editor.putString("emotion","슬픔"); // key,value 형식으로 저장
                editor2.putString("chat","sadchat");

                editor.commit();
                editor2.commit();//최종 커밋. 커밋을 해야 저장이 된다.

                startActivity(intent);
            }
        });
        binding.angryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IslandActivity.this,MainActivity.class);

                SharedPreferences sharedPreferences= getSharedPreferences("test", MODE_PRIVATE);    // test 이름의 기본모드 설정
                SharedPreferences.Editor editor= sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언
                SharedPreferences.Editor editor2= sharedPreferences.edit();//sharedPreferences를 제어할 editor를 선언
                editor.putString("emotion","분노"); // key,value 형식으로 저장
                editor2.putString("chat","angrychat");
                editor.commit();    //최종 커밋. 커밋을 해야 저장이 된다.
                editor2.commit();

                startActivity(intent);
            }
        });

        binding.myPage.setOnClickListener(v -> {
            startActivity(new Intent(this, MypageActivity.class));
        });


    }
}
