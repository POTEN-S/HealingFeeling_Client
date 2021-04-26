package com.example.healingfeeling;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.healingfeeling.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;


public class SignupActivity extends AppCompatActivity {


    private static final String TAG = "SignUpActivity";
    private static final int PICK_FROM_ALBUM = 10 ;
    private EditText name;

    private Button signup;
    private ImageView profile;
    private Uri imageUri;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signupActivity_button_signup).setOnClickListener(onClickListener);
        findViewById(R.id.signupActivity_imageview_profile).setOnClickListener(onClickListener);
        profile = (ImageView)findViewById(R.id.signupActivity_imageview_profile);
        findViewById(R.id.gotoLoginBtn).setOnClickListener(onClickListener);




    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.signupActivity_button_signup:
                    signUp();
                    break;
                case R.id.signupActivity_imageview_profile:
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    startActivityForResult(intent,PICK_FROM_ALBUM);
                    break;

                case R.id.gotoLoginBtn:
                    myStartActivity(LoginActivity.class);
                    break;

            }
        }
    };


    private void signUp() {
        String email = ((EditText)findViewById(R.id.signupActivity_edittext_email)).getText().toString();
        String password = ((EditText)findViewById(R.id.signupActivity_edittext_password)).getText().toString();
        String name = ((EditText)findViewById(R.id.signupActivity_edittext_name)).getText().toString();

        if(email.length()>0 && password.length() >0 && name.length() >0 ) {


            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        String uid = task.getResult().getUser().getUid();
                        FirebaseStorage.getInstance().getReference().child("userImages").child(uid).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                String imageUrl = task.getResult().getUploadSessionUri().toString();

                                User userModel = new User();
                                userModel.userName = name;
                                userModel.profileImageUrl = imageUrl;


                            }
                        });

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startToast("회원가입에 성공하였습니다.");
                            myStartActivity(FaceRecoActivity.class);


                            //UI
                        } else {
                            if(task.getException()!=null) {

                                startToast(task.getException().toString());
                            }

                            //UI
                        }

                    });

        }else {

            startToast("이메일 또는 비밀번호가 입력되지 않았거나 이름을 입력해주세요");
        }



    }

    private void startToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT ).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_ALBUM && resultCode == RESULT_OK) {
            profile.setImageURI(data.getData()); //가운데 뷰를 바꿈
            imageUri = data.getData(); //이미지 경로 원본

        }
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



}