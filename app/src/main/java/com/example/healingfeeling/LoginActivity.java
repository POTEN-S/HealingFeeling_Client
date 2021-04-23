package com.example.healingfeeling;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity  {
    private static final String TAG = "SignUpActivity";
    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();



        findViewById(R.id.loginActivity_button_login).setOnClickListener(onClickListener);
        findViewById(R.id.loginActivity_button_signup).setOnClickListener(onClickListener);




    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.loginActivity_button_login:
                    loginEvent();
                    break;
                case R.id.loginActivity_button_signup:
                    myStartActivity(SignupActivity.class);
                    break;

            }
        }
    };




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
