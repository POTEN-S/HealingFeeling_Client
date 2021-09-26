package com.example.healingfeeling.ui.recommend;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healingfeeling.R;
import com.example.healingfeeling.api.MyApi;
import com.example.healingfeeling.common.MySharedPreference;
import com.example.healingfeeling.databinding.FragmentRecommendBinding;
import com.example.healingfeeling.model.Post;
import com.example.healingfeeling.ui.home.PostRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecommendFragment extends Fragment {

    private PostRecyclerAdapter adapter;
    RecyclerView recyclerView;

    private FirebaseDatabase database;

    private DatabaseReference databaseReference;


    ArrayList<Post> arraypost=new ArrayList<>();
    ArrayList<String> arraypost2=new ArrayList<>();
    FragmentRecommendBinding binding;

    String songTitle;
    private final String BASE_URL = "http://ec2-3-36-57-87.ap-northeast-2.compute.amazonaws.com:8000";

    private MyApi mMyAPI;
    private final  String TAG = getClass().getSimpleName();


    String happysongtitle ;
    String happysongratings;
    String happybooktitle;
    String happybookratings;
    String happywheretitle;
    String happywhereratings;

    //sad
    String sadsongtitle ;
    String sadsongratings;
    String sadbooktitle;
    String sadbookratings;
    String sadwheretitle;
    String sadwhereratings;

    //angry
    String angrysongtitle  ;
    String angrysongratings;
    String angrybooktitle;
    String angrybookratings;
    String angrywheretitle;
    String angrywhereratings;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_recommend, container, false);
        binding= FragmentRecommendBinding.bind(root);

        initMyAPI(BASE_URL);

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        Call<List<com.example.healingfeeling.api.PostItem>> getCall = mMyAPI.get_posts();
        getCall.enqueue(new Callback<List<com.example.healingfeeling.api.PostItem>>() {
            @Override
            public void onResponse(Call<List<com.example.healingfeeling.api.PostItem>> call, Response<List<com.example.healingfeeling.api.PostItem>> response) {
                if( response.isSuccessful()){
                    List<com.example.healingfeeling.api.PostItem> mList = response.body();
                    String result ="";
                    Boolean check = false;
                    for( com.example.healingfeeling.api.PostItem item : mList){
                         happysongtitle =item.gethappysongtitle() ;
                         happysongratings=item.getHappysongratings();
                         happybooktitle=item.gethappybooktitle();
                         happybookratings=item.getHappybookratings();
                         happywheretitle=item.getHappywheretitle();
                         happywhereratings=item.getHappywhereratings();

                        //sad
                         sadsongtitle =item.getsadsongtitle() ;
                         sadsongratings=item.getsadsongratings();
                         sadbooktitle=item.getsadbooktitle();
                         sadbookratings=item.getsadbookratings();
                         sadwheretitle=item.getsadwheretitle();
                         sadwhereratings=item.getsadwhereratings();

                        //angry
                         angrysongtitle =item.getangrysongtitle() ;
                         angrysongratings=item.getangrysongratings();
                         angrybooktitle=item.getangrybooktitle();
                         angrybookratings=item.getangrybookratings();
                         angrywheretitle=item.getangrywheretitle();
                         angrywhereratings=item.getangrywhereratings();

                    }

                    arraypost.clear();


                    String user_emotion = MySharedPreference.get_user_emotion(getContext());

                    database = FirebaseDatabase.getInstance();
                    databaseReference = database.getReference("postList");
                    Log.d("papapa",user_emotion);


                    if(user_emotion.equals("smile")) {
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                Post song = dataSnapshot.child(happysongtitle).getValue(Post.class);
                                Log.d("papapa", song.title);

                                if(song!=null) {
                                    Post postsong = new Post(song.category, song.title, song.subTitle, song.image, song.emotion, song.favorite, song.register, song.ratings);
                                    arraypost.add(postsong);
                                }
                                Post book = dataSnapshot.child(happybooktitle).getValue(Post.class);
                                if(book!=null) {
                                    Post postbook = new Post(book.category, book.title, book.subTitle, book.image, book.emotion, book.favorite, book.register, book.ratings);
                                    arraypost.add(postbook);
                                }

                                Post where = dataSnapshot.child(happywheretitle).getValue(Post.class);
                                if(where!=null) {
                                    Post postwhere = new Post(where.category, where.title, where.subTitle, where.image, where.emotion, where.favorite, where.register, where.ratings);
                                    arraypost.add(postwhere);
                                }

                                //Post group = dataSnapshot.child(songTitle).getValue();

                                adapter = new PostRecyclerAdapter(arraypost);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
                            }
                        });
                    }else if(user_emotion.equals("sad")){
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                Post song = dataSnapshot.child(sadsongtitle).getValue(Post.class);
                                if(song!=null) {
                                    Post postsong = new Post(song.category, song.title, song.subTitle, song.image, song.emotion, song.favorite, song.register, song.ratings);
                                    arraypost.add(postsong);
                                }
                                Post book = dataSnapshot.child(sadbooktitle).getValue(Post.class);
                                if(book!=null) {
                                    Post postbook = new Post(book.category, book.title, book.subTitle, book.image, book.emotion, book.favorite, book.register, book.ratings);
                                    arraypost.add(postbook);
                                }
                                Post where = dataSnapshot.child(sadwheretitle).getValue(Post.class);
                                if(where!=null) {
                                    Post postwhere = new Post(where.category, where.title, where.subTitle, where.image, where.emotion, where.favorite, where.register, where.ratings);
                                    arraypost.add(postwhere);
                                }

                                adapter = new PostRecyclerAdapter(arraypost);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
                            }
                        });

                    }else if(user_emotion.equals("angry")){

                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                Post song = dataSnapshot.child(angrysongtitle).getValue(Post.class);
                                Log.d("papapa", song.title);
                                if(song!=null) {
                                    Post postsong = new Post(song.category, song.title, song.subTitle, song.image, song.emotion, song.favorite, song.register, song.ratings);
                                    arraypost.add(postsong);
                                }
                                Post book = dataSnapshot.child(angrybooktitle).getValue(Post.class);
                                if(book!=null) {
                                    Post postbook = new Post(book.category, book.title, book.subTitle, book.image, book.emotion, book.favorite, book.register, book.ratings);
                                    arraypost.add(postbook);
                                }
                                Post where = dataSnapshot.child(angrywheretitle).getValue(Post.class);
                                if(where!=null) {
                                    Post postwhere = new Post(where.category, where.title, where.subTitle, where.image, where.emotion, where.favorite, where.register, where.ratings);
                                    arraypost.add(postwhere);
                                }
                                adapter = new PostRecyclerAdapter(arraypost);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
                            }
                        });

                    }


                }else {
                    Log.d("ssssss","ssssss" + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<com.example.healingfeeling.api.PostItem>> call, Throwable t) {
                Log.d(TAG,"Fail msg : " + t.getMessage());
            }
        });




        //번들 받기. getArguments() 메소드로 받음.
        Bundle bundle = getArguments();
        if(bundle != null){
            String emotion = bundle.getString("emotion"); //Name 받기.
            Log.d("Recommend",emotion); //확인

        }




        return root;
    }
    private void initMyAPI(String baseUrl){

        Log.d(TAG,"initMyAPI : " + baseUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mMyAPI = retrofit.create(MyApi.class);
    }

    /*void setListener(){
        if( v == mGetButton){
            Log.d(TAG,"GET");
            Call<List<com.example.healingfeeling.api.Post>> getCall = mMyAPI.get_posts();
            getCall.enqueue(new Callback<List<com.example.healingfeeling.api.Post>>() {
                @Override
                public void onResponse(Call<List<com.example.healingfeeling.api.Post>> call, Response<List<com.example.healingfeeling.api.Post>> response) {
                    if( response.isSuccessful()){
                        List<com.example.healingfeeling.api.Post> mList = response.body();
                        String result ="";
                        for( com.example.healingfeeling.api.Post item : mList){
                            result += "title : " + item.getTitle() + " text: " + item.getText() + "\n";
                        }
                        Log.d(TAG,"겟 성공");
                        mListTv.setText(result);
                    }else {
                        Log.d(TAG,"Status Code : " + response.code());
                    }
                }
                @Override
                public void onFailure(Call<List<com.example.healingfeeling.api.Post>> call, Throwable t) {
                    Log.d(TAG,"Fail msg : " + t.getMessage());
                }
            });
        }else if(v == mPostButton){
            Log.d(TAG,"POST");
            com.example.healingfeeling.api.Post item = new com.example.healingfeeling.api.Post();
            item.setTitle("Android title");
            item.setText("Android text");
            Call<com.example.healingfeeling.api.Post> postCall = mMyAPI.post_posts(item);
            postCall.enqueue(new Callback<com.example.healingfeeling.api.Post>() {
                @Override
                public void onResponse(Call<com.example.healingfeeling.api.Post> call, Response<com.example.healingfeeling.api.Post> response) {
                    if(response.isSuccessful()){
                        Log.d(TAG,"등록 완료");
                    }else {
                        Log.d(TAG,"Status Code : " + response.code());
                        Log.d(TAG,response.errorBody().toString());
                        Log.d(TAG,call.request().body().toString());
                    }
                }
                @Override
                public void onFailure(Call<com.example.healingfeeling.api.Post> call, Throwable t) {
                    Log.d(TAG,"Fail msg : " + t.getMessage());
                }
            });
        }else if( v == mPatchButton){
            Log.d(TAG,"PATCH");
            com.example.healingfeeling.api.Post item = new com.example.healingfeeling.api.Post();
            item.setTitle("android patch title");
            item.setText("android patch text");
            //pk 값은 임의로 하드코딩하였지만 동적으로 setting 해서 사용가능
            Call<com.example.healingfeeling.api.Post> patchCall = mMyAPI.patch_posts(1,item);
            patchCall.enqueue(new Callback<com.example.healingfeeling.api.Post>() {
                @Override
                public void onResponse(Call<com.example.healingfeeling.api.Post> call, Response<com.example.healingfeeling.api.Post> response) {
                    if(response.isSuccessful()){
                        Log.d(TAG,"patch 성공");
                    }else{
                        Log.d(TAG,"Status Code : " + response.code());
                    }
                }
                @Override
                public void onFailure(Call<com.example.healingfeeling.api.Post> call, Throwable t) {
                    Log.d(TAG,"Fail msg : " + t.getMessage());
                }
            });
        }else if( v == mDeleteButton){
            Log.d(TAG,"DELETE");
            // pk 값은 임의로 변경가능
            Call<com.example.healingfeeling.api.Post> deleteCall = mMyAPI.delete_posts(2);
            deleteCall.enqueue(new Callback<com.example.healingfeeling.api.Post>() {
                @Override
                public void onResponse(Call<com.example.healingfeeling.api.Post> call, Response<com.example.healingfeeling.api.Post> response) {
                    if(response.isSuccessful()){
                        Log.d(TAG,"삭제 완료");
                    }else {
                        Log.d(TAG,"Status Code : " + response.code());
                    }
                }
                @Override
                public void onFailure(Call<com.example.healingfeeling.api.Post> call, Throwable t) {
                    Log.d(TAG,"Fail msg : " + t.getMessage());
                }
            });
        }
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
    }*/


}