package com.example.healingfeeling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.healingfeeling.databinding.ActivityMypageBinding;
import com.example.healingfeeling.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

public class MypageActivity extends AppCompatActivity {
    private static final String TAG = "MypageActivity";

    ActivityMypageBinding binding;
    DatabaseReference myRef;
    TextView myname;
    ImageView imageView;
    FirebaseUser user;
    FirebaseAuth mAuth;
    FirebaseDatabase database;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);


        binding= DataBindingUtil.setContentView(this,R.layout.activity_mypage);

        //binding.progressHappy.setProgress(50);
        //binding.progressSad.setProgress(10);
        //binding.progressAngry.setProgress(80);


        findViewById(R.id.logoutButton).setOnClickListener(onClickListener);
        findViewById(R.id.userDeleteButton).setOnClickListener(onClickListener);

        //myname = (TextView) findViewById(R.id.myPageNickName);
        //imageView=findViewById(R.id.mypageActivity_imageview_profile);

        User userModel = new User();


        user = FirebaseAuth.getInstance().getCurrentUser();

        String uid = user!= null? user.getUid() : null;
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");
        DatabaseReference username = myRef.child(uid).child("userName");
        DatabaseReference profile_image = myRef.child(uid).child("profileImageUrl");


        profile_image.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String profile_url = snapshot.child("image").getValue(String.class);
               /* Glide.with(MypageActivity.this).load(profile_url).apply(new RequestOptions().circleCrop())
                        .into(imageView);*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        username.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name = snapshot.getValue(String.class);
                myname.setText(name);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());

            }
        });




        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                //데이터를 화면에 출력해 준다.
                Log.d(TAG, "Value is: " + map);





            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



    }



    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.logoutButton:
                    FirebaseAuth.getInstance().signOut();
                    myStartActivity(LoginActivity.class);
                    break;
                case  R.id.userDeleteButton:
                    AlertDialog.Builder builder = new AlertDialog.Builder(MypageActivity.this);
                    builder.setTitle("회원탈퇴").setMessage("정말 회원탈퇴를 하시겠습니까?");
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //yes 클릭
                            mAuth.getCurrentUser().delete();
                            deleteUserData(mAuth.getCurrentUser().getDisplayName());




                            myStartActivity(LoginActivity.class);

                        }
                    });

                    builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getBaseContext(),"취소되었습니다.",Toast.LENGTH_SHORT).show();
                        }
                    });


                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                    break;


            }
        }
    };



    public void deleteUserData(final String userName){
        //get userID
        String uid = mAuth.getInstance().getCurrentUser().getUid();
        myRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    for(DataSnapshot data: snapshot.getChildren()){
                        data.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }





    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }







}