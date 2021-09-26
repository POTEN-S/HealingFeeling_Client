package com.example.healingfeeling.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.healingfeeling.R;
import com.example.healingfeeling.model.Post;

import java.util.ArrayList;

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

        //리스트 클릭 이벤트  나영이가 잠시 테스트 해 봄.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(view, position,listData.get(position).title,listData.get(position).category);


            }

        });

    }
    public interface OnItemClickListener
    {
        void onItemClick(View v, int pos, String s, String title);
    }
    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null;

    // OnItemClickListener 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.mListener = listener;
    }
    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
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