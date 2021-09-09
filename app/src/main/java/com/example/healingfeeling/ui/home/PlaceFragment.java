package com.example.healingfeeling.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healingfeeling.R;
import com.example.healingfeeling.model.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlaceFragment extends Fragment {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    ArrayList<String> aa=new ArrayList<>();
    ArrayList<Post> arraypost=new ArrayList<>();
    RecyclerView recyclerView;
    String emotion;

    private RecyclerAdapter adapter;

    public static PlaceFragment newInstance() {
        PlaceFragment tab2 = new PlaceFragment();
        return tab2;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_place, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);


        GridLayoutManager GridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(GridLayoutManager);

        SharedPreferences sharedPreferences= this.getActivity().getSharedPreferences("test", Context.MODE_PRIVATE);    // test 이름의 기본모드 설정, 만약 test key값이 있다면 해당 값을 불러옴.
        emotion = sharedPreferences.getString("emotion","");

        Log.d("asdf",emotion);

        getData();


        return root;
    }



    private void getData() {
        // 임의의 데이터입니다.
        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동

        databaseReference = database.getReference().child(emotion); // DB 테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arraypost.clear(); // 기존 배열리스트가 존재하지않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                    Post data = snapshot.getValue(Post.class); // 만들어뒀던 Data 객체에 데이터를 담는다.

                    if (data.category.equals("장소")){
                        arraypost.add(data); }// 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비

                }

                // 랭킹 정렬
                Collections.sort(arraypost,new PostComparator());

                adapter = new RecyclerAdapter(arraypost);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("SongFragment", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });



    }


}


