package com.example.healingfeeling;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class PostActivity extends AppCompatActivity {

    EditText etTitle;
    EditText etContent;
    ImageButton btnGallery;
    ImageButton btnCamera;
    Button btnCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_post);


        etTitle = (EditText)findViewById(R.id.titleinput);
        etContent = (EditText)findViewById(R.id.textinput);

        btnGallery = (ImageButton)findViewById(R.id.gallery_btn);
        btnCamera = (ImageButton)findViewById(R.id.camera_btn);

        btnCheck = (Button)findViewById(R.id.check_btn);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Write();
            }
        });





    }

    //등록하기
    private void Write() {

    }

}
