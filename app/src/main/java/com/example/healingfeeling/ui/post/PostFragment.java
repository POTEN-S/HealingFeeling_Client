package com.example.healingfeeling.ui.post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.healingfeeling.PostActivity;
import com.example.healingfeeling.R;

public class PostFragment extends Fragment {

    private PostViewModel postViewModel;

    EditText etTitle;
    EditText etContent;
    ImageButton btnGallery;
    ImageButton btnCamera;
    Button btnCheck;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        postViewModel =
                new ViewModelProvider(this).get(PostViewModel.class);
        View root = inflater.inflate(R.layout.fragment_post, container, false);
/*
        Spinner spinner = (Spinner) root.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(postViewModel,
                R.array.my_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
*/

        etTitle = (EditText) root.findViewById(R.id.titleinput);
        etContent = (EditText)root.findViewById(R.id.textinput);

        btnGallery = (ImageButton)root.findViewById(R.id.gallery_btn);
        btnCamera = (ImageButton)root.findViewById(R.id.camera_btn);


        btnCheck = (Button)root.findViewById(R.id.check_btn);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Write();
            }
        });



        /*스피너 구현

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parent.getItemAtPosition(position);
                if (position==0){

                }

                if (position==1){

                }
                if (position==2){

                }



            } //이 오버라이드 메소드에서 position은 몇번째 값이 클릭됬는지 알 수 있습니다.
            //getItemAtPosition(position)를 통해서 해당 값을 받아올수있습니다.

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });*/


        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        return root;


    }




    //등록하기
    private void Write() {

    }


}