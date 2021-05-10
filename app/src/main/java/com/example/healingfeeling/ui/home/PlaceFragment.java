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


    private PostRecyclerAdapter adapter;
    RecyclerView recyclerView;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    ArrayList<String> aa=new ArrayList<>();
    ArrayList<Post> arraypost=new ArrayList<>();


    public static PlaceFragment newInstance() {
        PlaceFragment tab3 = new PlaceFragment();

        return tab3;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_place, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);

        LinearLayoutManager GridLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(GridLayoutManager);
          //번들 받기. getArguments() 메소드로 받음.
        Bundle bundle = getArguments();
        if(bundle != null){
            String emotion = bundle.getString("emotion"); //Name 받기.
            Log.d("place",emotion); //확인

        }

        getData();


        return root;
    }



    private void getData() {
        // 임의의 데이터입니다.
        /*List<String> listTitle = Arrays.asList("국화", "사막", "수국", "해파리", "코알라", "등대", "펭귄", "튤립",
                "국화", "사막", "수국", "해파리", "코알라", "등대", "펭귄", "튤립");
        List<String> listContent = Arrays.asList(
                "이 꽃은 국화입니다.",
                "여기는 사막입니다.",
                "이 꽃은 수국입니다.",
                "이 동물은 해파리입니다.",
                "이 동물은 코알라입니다.",
                "이것은 등대입니다.",
                "이 동물은 펭귄입니다.",
                "이 꽃은 튤립입니다.",
                "이 꽃은 국화입니다.",
                "여기는 사막입니다.",
                "이 꽃은 수국입니다.",
                "이 동물은 해파리입니다.",
                "이 동물은 코알라입니다.",
                "이것은 등대입니다.",
                "이 동물은 펭귄입니다.",
                "이 꽃은 튤립입니다."
        );
        List<Integer> listResId = Arrays.asList(
                R.drawable.ic_home_black_24dp,
                R.drawable.ic_home_black_24dp,
                R.drawable.ic_home_black_24dp,
                R.drawable.ic_home_black_24dp,
                R.drawable.ic_home_black_24dp,
                R.drawable.ic_home_black_24dp,
                R.drawable.ic_home_black_24dp,
                R.drawable.ic_home_black_24dp,
                R.drawable.ic_home_black_24dp,
                R.drawable.ic_home_black_24dp,
                R.drawable.ic_home_black_24dp,
                R.drawable.ic_home_black_24dp,
                R.drawable.ic_home_black_24dp,
                R.drawable.ic_home_black_24dp,
                R.drawable.ic_home_black_24dp,
                R.drawable.ic_home_black_24dp
        );

        List<Boolean> listFavorite = Arrays.asList(new Boolean[16]);
        Collections.fill(listFavorite, Boolean.FALSE);

        List<Integer> listRegister = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16);

        for (int i = 0; i < listTitle.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            Data data = new Data();
            data.setTitle(listTitle.get(i));
            data.setSubtitle(listTitle.get(i));
            data.setContent(listContent.get(i));
            data.setResId(listResId.get(i));
            data.setFavorite(listFavorite.get(i));
            data.setRegisterCount(listRegister.get(i));

            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(data);
        }*/
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
                Log.e("BookFragment", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        //Post data = new Post("asdf","aaaaaa","bbbbbbb","com.google.android.gms.tasks.zzu@9b67f61","asdf",aa,0);
        //Post data2 = new Post("asdf","aaaaaa","bbbbbbb","com.google.android.gms.tasks.zzu@9b67f61","asdf",aa,0);

        //adapter = new PostRecyclerAdapter(arraypost);

        // adapter의 값이 변경되었다는 것을 알려줍니다.
//        adapter.notifyDataSetChanged();
    }


}
