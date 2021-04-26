package com.example.healingfeeling;

import android.content.Intent;
import android.database.Cursor;
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
import androidx.loader.content.CursorLoader;


import com.example.healingfeeling.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;


public class SignupActivity extends AppCompatActivity {


    private static final String TAG = "SignUpActivity";
    private static final int PICK_FROM_ALBUM = 10 ;


    private ImageView profile;
    private Uri imageUri;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private FirebaseStorage mStorage;
    private String pathUri;
    private File tempFile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mStorage = FirebaseStorage.getInstance();


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

                            final Uri file = Uri.fromFile(new File(pathUri)); // path
                            StorageReference storageReference = mStorage.getReference()
                                    .child("usersprofileImages").child("uid/"+file.getLastPathSegment());
                            storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    final Task<Uri> imageUrl = task.getResult().getStorage().getDownloadUrl();
                                    while (!imageUrl.isComplete()) ;

                                    User userModel = new User();

                                    userModel.userName = name;
                                    userModel.uid = uid;
                                    userModel.profileImageUrl = imageUrl.getResult().toString();

                                    // database에 저장
                                    mDatabase.getReference().child("users").child(uid)
                                            .setValue(userModel);

                                }
                            });




                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startToast("회원가입에 성공하였습니다.");


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
        if (resultCode != RESULT_OK) { // 코드가 틀릴경우
            startToast("취소 되었습니다");
            if (tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        Log.e(TAG, tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    }
                }
            }
            return;
        }

        switch (requestCode) {
            case PICK_FROM_ALBUM: { // 코드 일치
                // Uri
                imageUri = data.getData();
                pathUri = getPath(data.getData());
                Log.d(TAG, "PICK_FROM_ALBUM photoUri : " + imageUri);
                profile.setImageURI(imageUri); // 이미지 띄움
                break;
            }
        }


    }

    public String getPath(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null, null, null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();
        return cursor.getString(index);



    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



}