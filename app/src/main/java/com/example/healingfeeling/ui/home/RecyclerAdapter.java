package com.example.healingfeeling.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.healingfeeling.R;
import com.example.healingfeeling.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {


    // adapter에 들어갈 list 입니다.
    private ArrayList<Post> listData = new ArrayList<>();
    private Context context;

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
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        context = holder.itemView.getContext();
        holder.onBind(listData.get(position));
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
        private TextView contenttext;
        private TextView subtitletext;
        private ImageView imageView;

        private boolean favorite;
        private TextView registerCount;

        private Button button;

        ItemViewHolder(View itemView) {
            super(itemView);

            titletext = itemView.findViewById(R.id.titletext);
            subtitletext = itemView.findViewById(R.id.subtitletext);
            contenttext = itemView.findViewById(R.id.contenttext);
            imageView = itemView.findViewById(R.id.imageView);
            registerCount = itemView.findViewById(R.id.registercount);
            button = itemView.findViewById(R.id.favoritebutton);

        }

        void onBind(Post data) {


            String url = data.getImageUrl();
            Glide.with(context)
                    .load(url)
                    .placeholder(R.drawable.feelings)
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