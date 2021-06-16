package com.example.healingfeeling.ui.recommend;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healingfeeling.MainActivity;
import com.example.healingfeeling.R;
import com.example.healingfeeling.model.Post;
import com.example.healingfeeling.ui.home.PlaceFragment;
import com.example.healingfeeling.ui.home.PostRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecommendFragment extends Fragment {

    private PostRecyclerAdapter adapter;
    RecyclerView recyclerView;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    ArrayList<Post> arraypost=new ArrayList<>();


    public static RecommendFragment newInstance() {
        RecommendFragment recommendFragment = new RecommendFragment();

        return recommendFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_recommend, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);

        LinearLayoutManager GridLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(GridLayoutManager);
        //번들 받기. getArguments() 메소드로 받음.
        Bundle bundle = getArguments();
        if(bundle != null){
            String emotion = bundle.getString("emotion"); //Name 받기.
            Log.d("Recommend",emotion); //확인

        }

        getData();


        return root;
    }



    private void getData() {

        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동

        databaseReference = database.getReference().child("post").child("97r9eGbBNIRiCUHZoEfbQIqioQ52"); // DB 테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arraypost.clear(); // 기존 배열리스트가 존재하지않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                    Post data = snapshot.getValue(Post.class); // 만들어뒀던 Data 객체에 데이터를 담는다.
                    arraypost.add(data); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비

                }
                adapter = new PostRecyclerAdapter(arraypost);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("RecommendFragment", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });
    }


}
