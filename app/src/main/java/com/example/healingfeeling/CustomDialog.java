package com.example.healingfeeling;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomDialog extends Dialog implements View.OnClickListener {

    private static final int LAYOUT = R.layout.custom_dialog;

    private Context context;

    private EditText rating;

    private Button cancel;
    private Button confirm;

    private String category;
    private String uid;
    private String title;
    DatabaseReference mDBReference = null;



    public CustomDialog(Context context,String title,String category,String uid){
        super(context);
        this.context = context;
        this.category = category;
        this.title= title;
        this.uid=uid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        rating = findViewById(R.id.edit_rating);

        cancel =  findViewById(R.id.btn_cancel);
        confirm =  findViewById(R.id.btn_confirm);

        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel:
                cancel();
                break;
            case R.id.btn_confirm:
                //String userId = mAuth.getCurrentUser().getUid();
                mDBReference = FirebaseDatabase.getInstance().getReference();
                mDBReference.child("score").child(category).child(uid).child(title).setValue(Double.parseDouble(rating.getText().toString()))
                        .addOnSuccessListener(aVoid -> {
                            // Write was successful!
                            // Toast.makeText(getContext(), "저장을 완료했습니다.", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Write failed
                                Toast.makeText(getContext(), "저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });

                dismiss();
                break;
        }
    }
}
