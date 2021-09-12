package com.example.healingfeeling.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.healingfeeling.IslandActivity;
import com.example.healingfeeling.PostFragment;
import com.example.healingfeeling.R;
import com.example.healingfeeling.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {


    // adapter에 들어갈 list 입니다.
    private ArrayList<Post> listData = new ArrayList<>();
    Context context;

    public RecyclerAdapter(ArrayList<Post> data) {
        listData = data;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);


        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        context = holder.itemView.getContext();
        holder.onBind(listData.get(position));

        //0727 세림 : 아이템 클릭시 정보들이 PostFragment에 넘어가도록
        holder.itemView.setTag(position); //커스텀 리스트 뷰의 각각의 리스트를 의미
        holder.titletext.setText(listData.get(position).title);//

        //리스트 클릭 이벤트
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = holder.titletext.getText().toString(); //holder로 가져온 값을 변수에 넣기


                Log.d("title",title);

                Log.d("position",listData.get(position).title);
                
                
                Bundle bundle = new Bundle();
                bundle.putString("titletext", listData.get(position).title);
                bundle.putString("category", listData.get(position).category);
                bundle.putString("subtitletext", listData.get(position).subTitle);
                bundle.putString("imageurl", listData.get(position).getImageUrl());

                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                PostFragment postFragment = new PostFragment(); // PostFragment 선언
                postFragment.setArguments(bundle); //번들을 postFragment로 보낼 준비
                transaction.replace(R.id.nav_host_fragment, postFragment).commit();

            }

        });

    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    void addItem(Post data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);

     /*   Comparator<Data> noAsc = new Comparator<Data>() {
            @Override
            public int compare(Data item1, Data item2) {
                int ret ;
                if (item1.getRegisterCount() < item2.getRegisterCount())
                    ret = 1 ;
                else if (item1.getRegisterCount() == item2.getRegisterCount())
                    ret = 0 ;
                else
                    ret = -1 ;
                return ret ;
            }
        };
        Collections.sort(listData, noAsc) ;*/
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView titletext;
        private TextView subtitletext;
        private ImageView imageView;

        private boolean favorite;
        private TextView registerCount;

        private Button button;

        ItemViewHolder(View itemView) {
            super(itemView);

            titletext = itemView.findViewById(R.id.titletext);
            subtitletext = itemView.findViewById(R.id.subtitletext);
            imageView = itemView.findViewById(R.id.imageView);
            registerCount = itemView.findViewById(R.id.registercount);
            button = itemView.findViewById(R.id.favoritebutton);

            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


/*
                    Bundle bundle = new Bundle();
                    bundle.putString("titletext", String.valueOf(titletext));

                    FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                    PostFragment postFragment = new PostFragment();//PostFragment 선언
                    postFragment.setArguments(bundle);//번들을 postFragment로 보낼 준비
                    transaction.replace(R.id.nav_host_fragment, postFragment).commit();
*/

                    /*
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(context, PostFragment.class);
                        intent.putExtra("name","name");
                        context.startActivity(intent);
                    }

                    */
                }
            });

        }

        void onBind(Post data) {


            String url = data.getImageUrl();
            Glide.with(context)
                    .load(url)
                    .placeholder(R.drawable.loading)
                    .into(imageView);

            titletext.setText(data.getTitle());
            subtitletext.setText(data.getSubTitle());

            button.setSelected(favorite);
            button.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {


                    view.setSelected(!view.isSelected()); // 선택여부 반전시키기

                    if(view.isSelected()){
                        favorite = Boolean.TRUE;

                    }
                    else{

                        favorite = Boolean.FALSE;
                    }
                }
            });
        }
    }
}