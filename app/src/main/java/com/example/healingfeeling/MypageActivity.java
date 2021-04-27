package com.example.healingfeeling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.healingfeeling.databinding.ActivityMypageBinding;
import com.example.healingfeeling.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MypageActivity extends AppCompatActivity {

    ActivityMypageBinding binding;
    DatabaseReference mDatabase;
    TextView myname;
    FirebaseUser user;
    FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_mypage);

        binding.progressHappy.setProgress(50);
        binding.progressSad.setProgress(10);
        binding.progressAngry.setProgress(80);


        findViewById(R.id.logoutButton).setOnClickListener(onClickListener);

        myname = (TextView) findViewById(R.id.myPageNickName);

        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user!= null? user.getUid() : null;
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference username = mDatabase.child(uid).child("userName");

        username.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                myname.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
            }
        }
    };


    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}