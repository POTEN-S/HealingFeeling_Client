package com.example.healingfeeling.ui.chatting;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.healingfeeling.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class ChattingFragment extends Fragment {

    EditText etName;
    CircleImageView ivProfile;
    Button entbtn;

    Uri imgUri; //선택한 프로필 이미지 경로 uri
    //선택한 프로필 이미지 경로 uri

    boolean isFirst = true; //앱을 처음 실행한 것인가
    boolean isChanged = false;//프로필을 변경한적이 있는가?


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_chatting, container, false);

        etName = v.findViewById(R.id.et_name);
        ivProfile = v.findViewById(R.id.iv_profile);
        entbtn = v.findViewById(R.id.enterbtn);
        entbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isChanged && !isFirst) {
                    //ChatActivity로 전환
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    startActivity(intent);


                } else {
                    //save 작업
                    saveData();
                    //ChatActivity로 전환


                }
            }
        });

        ivProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });


        loadData();
        if (G.nickName != null) {
            etName.setText(G.nickName);
            Picasso.get().load(G.profileUri).into(ivProfile);

            //처음이 아니다, 이미 접속한 적이 있다.
            isFirst = false;


            //폰에 저장되어있는 프로필 읽어오기
        }
        return v;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {
                    imgUri = data.getData();
                    Glide.with(getActivity()).load(imgUri).into(ivProfile);
                    //Glide는 이미지를 읽어와서 보여줄때 내 device의 외장메모리에 접근하는 퍼미션이 요구됨
                    //퍼미션이 없으면 이미지가 보이지 않음
                    Picasso.get().load(imgUri).into(ivProfile);

                    //변경된 이미지가 있다.
                    isChanged=true;
                }
                break;
        }
    }




    void saveData() {
        //EditText의 닉네임 가져오기
        G.nickName=etName.getText().toString();

        //이미지를 선택하지 않았을 수도 있으므로
        if(imgUri==null) return;;

        //Firebase storage에 이미지 저장하기 위해 파일명 만들기(날짜기반으로)
        SimpleDateFormat sdf=new SimpleDateFormat("yyyMMddhhmmss");
        String fileName = sdf.format(new Date())+".png";

        //Firebase storage에 저장하기
        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
        final StorageReference imgRef = firebaseStorage.getReference("profileImages/"+fileName);

        //파일업로드
        UploadTask uploadTask=imgRef.putFile(imgUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //이미지 업로드가 성공되었으므로
                //곧바로 firebasestorage의 이미지 파일다운로드 URL을 얻어오기
                imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //피라미터로 firebase의 저장소에 저장되어있는
                        //이미지에 대한 다운로드 주소(URL)을 문자열로 얻어오기
                        G.profileUri=uri.toString();
                        Toast.makeText(getActivity(),"프로필 저장 완료", Toast.LENGTH_SHORT).show();

                        //1. Firebase Database에 닉네임,프로필uri을 저장
                        //firebase DB관리자 객체 소환
                        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                        //'profiles'라는 이름의 자식 노드참조 객체 얻어오기
                        DatabaseReference profileRef = firebaseDatabase.getReference("profiles");

                        //닉네임을 key 식별자로 하고 프로필 이미지의 주소를 값으로 저장
                        profileRef.child(G.nickName).setValue(G.profileUri);


                        //2.내 phone에 nickname,profileUrl을 저장
                        SharedPreferences preferences = getActivity().getSharedPreferences("account", MODE_PRIVATE);
                        SharedPreferences.Editor editor=preferences.edit();

                        editor.putString("nickName",G.nickName);
                        editor.putString("profileUrl",G.profileUri);

                        editor.commit();
                        //저장이 완료되었으니 ChatActivity로 전환
                        Intent intent=new Intent(getActivity(), ChatActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    void loadData(){
        SharedPreferences preferences=getActivity().getSharedPreferences("account", MODE_PRIVATE);
        G.nickName=preferences.getString("nickName", null);
        G.profileUri=preferences.getString("ProfileUtl", null);
    }








}
