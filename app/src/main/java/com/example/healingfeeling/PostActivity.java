package com.example.healingfeeling;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

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

        Spinner spinner = (Spinner)findViewById(R.id.spinner);


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



        //스피너 구현

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 parent.getItemAtPosition(position);


            } //이 오버라이드 메소드에서 position은 몇번째 값이 클릭됬는지 알 수 있습니다.
            //getItemAtPosition(position)를 통해서 해당 값을 받아올수있습니다.

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






    }

    //등록하기
    private void Write() {

    }

}
