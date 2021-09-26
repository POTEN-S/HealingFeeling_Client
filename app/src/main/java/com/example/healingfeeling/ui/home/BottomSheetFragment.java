package com.example.healingfeeling.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.healingfeeling.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    Context context;

    public BottomSheetFragment(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.bottom_sheet, container, false);

        Button btnOK = view.findViewById(R.id.btnOK);

        Bundle bundle = getArguments();  //번들 받기. getArguments() 메소드로 받음.


        btnOK.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(context, "평점 남기기", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        Button btnClose = view.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String titletext = "";
                String category = "";

                if(bundle != null){
                    titletext = bundle.getString("titletext"); //Name 받기.
                    category = bundle.getString("category");
                }
//                Toast.makeText(context, "사이트 연결", Toast.LENGTH_SHORT).show();
//                dismiss();


                if(category.equals("노래")) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vibe.naver.com/search?query=" + titletext));
                    startActivity(browserIntent);
                }
                else if(category.equals("도서")){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://search.kyobobook.co.kr/mobile/search?keyword="+titletext));
                    startActivity(browserIntent);
                }else{
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q="+titletext));
                    startActivity(browserIntent);
                }

            }
        });

        return view;
    }

}
