package com.example.healingfeeling.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healingfeeling.CustomDialog;
import com.example.healingfeeling.R;
import com.example.healingfeeling.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class SongFragment extends Fragment {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    ArrayList<String> aa=new ArrayList<>();
    ArrayList<Post> arraypost=new ArrayList<>();
    RecyclerView recyclerView;
    String emotion;
    private FirebaseAuth mAuth;
    private RecyclerAdapter adapter;

    public static SongFragment newInstance() {
        SongFragment tab1 = new SongFragment();
        return tab1;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_song, container, false);

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

                    if (data.category.equals("노래")){
                        arraypost.add(data);
                    }

                }

                // 랭킹 정렬
                Collections.sort(arraypost,new PostComparator());


                adapter = new RecyclerAdapter(arraypost);
                recyclerView.setAdapter(adapter);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String the_uid = user.getUid();
                Log.d("nayoungid",the_uid);
                //String uid = mAuth.getCurrentUser().getUid();
                adapter.setOnItemClickListener((v, pos, title, category) -> {

                    Bundle bundle = new Bundle();
                    bundle.putString("titletext", title);
                    bundle.putString("category", category);
                    bundle.putString("user_uid",the_uid);
                    BottomSheetFragment bottomSheetFragment = new BottomSheetFragment(getContext());
                    //bottomSheetFragment.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
                   // bottomSheetFragment.getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));// PostFragment 선언
                    bottomSheetFragment.setArguments(bundle); //번들을 postFragment로 보낼 준비
                    bottomSheetFragment.show(((AppCompatActivity) getContext()).getSupportFragmentManager(), bottomSheetFragment.getTag());


                   /* CustomDialog dialog = new CustomDialog(getContext(),title,category,the_uid);
                    dialog.show();*/

                });
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

// 랭킹 정렬을 위한 comparator
class PostComparator implements Comparator<Post>{
    @Override
    public int compare(Post a,Post b){
        if(a.getRegister()>b.getRegister()) return -1;
        if(a.getRegister()<b.getRegister()) return 1;
        return 0;
    }
}
