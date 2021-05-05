package com.example.healingfeeling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.healingfeeling.databinding.ActivityMypageBinding;
import com.example.healingfeeling.model.User;
import com.google.firebase.auth.FirebaseAuth;

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


        findViewById(R.id.logoutButton).setOnClickListener(onClickListener);

       // TextView textView_name = (TextView) findViewById(R.id.myPageNickName);

        Intent intent_01 = getIntent();

        String name = intent_01.getStringExtra("입력한 이름");

       // textView_name.setText(String.valueOf(name));




    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.logoutButton:
                    FirebaseAuth.getInstance().signOut();
                    myStartActivity(LoginActivity.class);
                    break;
            }
        }
    };


    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}