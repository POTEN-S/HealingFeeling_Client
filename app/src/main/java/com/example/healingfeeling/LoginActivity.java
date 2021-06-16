package com.example.healingfeeling;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.healingfeeling.common.MySharedPreference;
import com.example.healingfeeling.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity  {
    private static final String TAG = "SignUpActivity";
    private FirebaseAuth mAuth;

    private ActivityLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        binding= ActivityLoginBinding.inflate(getLayoutInflater());
        mAuth = FirebaseAuth.getInstance();
        View view = binding.getRoot();
        setContentView(view);



        if (MySharedPreference.get_user_email(LoginActivity.this).length() != 0) {//로그인 고유데이터(현재는 이메일) 길이 0 아닐시
            Intent intent = new Intent(LoginActivity.this, FaceRecoActivity.class);
            startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);//액티비티 스택제거
            Toast.makeText(getApplicationContext(), "자동 로그인 되었습니다", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            setListener();

        }



    }

    void setListener(){
        binding.loginActivityButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginEvent();
            }
        });
        binding.loginActivityButtonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStartActivity(SignupActivity.class);
            }
        });
    }






    void loginEvent(){

        String id = ((EditText) findViewById(R.id.loginActivity_edittext_id)).getText().toString();
        String password = ((EditText) findViewById(R.id.loginActivity_edittext_password)).getText().toString();

        if (id.length() >0 && password.length() >0 ) {
            mAuth.signInWithEmailAndPassword(id, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                showToast("로그인에 성공하였습니다.");
                                myStartActivity(FaceRecoActivity.class);
                                MySharedPreference.set_user_email(LoginActivity.this,id);


                            } else {
                                if (task.getException() != null) {
                                    showToast(task.getException().toString());
                                }

                            }
                        }
                    });


        } else {
            showToast("이메일 또는 비밀번호를 입력해 주세요.");
        }


    }

    private void showToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT ).show();
    }


    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



}
