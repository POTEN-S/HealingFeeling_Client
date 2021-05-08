package com.example.healingfeeling.ui.home;

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

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private ArrayList<Data> listData = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    public static BookFragment newInstance() {
        BookFragment tab2 = new BookFragment();
        return tab2;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        //Bundle bundle = getArguments();
        //String emotion = bundle.getString("emotion");


        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동

        databaseReference = database.getReference().child("happy"); // DB 테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                listData.clear(); // 기존 배열리스트가 존재하지않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                    Data data = snapshot.getValue(Data.class); // 만들어뒀던 Data 객체에 데이터를 담는다.
                    listData.add(data); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("BookFragment", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });





        View root = inflater.inflate(R.layout.fragment_book, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화

        GridLayoutManager GridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(GridLayoutManager);

        listData = new ArrayList<>();   // Data 객체를 담을 어레이 리스트 (어댑터쪽으로)
        adapter = new RecyclerAdapter(listData, getContext());
        recyclerView.setAdapter(adapter);
        //getData();


        return root;
    }

}
