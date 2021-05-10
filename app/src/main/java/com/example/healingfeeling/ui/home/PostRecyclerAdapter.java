package com.example.healingfeeling.ui.home;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.healingfeeling.R;
import com.example.healingfeeling.model.Post;

import java.util.ArrayList;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.ViewHolder> {
    private ArrayList<Post> mData = null;
    private Context context;

    public PostRecyclerAdapter(ArrayList<Post> data) {
        mData = data;
    }

    // onCreateViewHolder : 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_post, parent, false);
        PostRecyclerAdapter.ViewHolder vh = new PostRecyclerAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder : position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post item = mData.get(position);

        context = holder.itemView.getContext();
        String url = mData.get(position).getImageUrl();
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.feelings)
                .into(holder.imageView);

        holder.mainText.setText(item.getTitle());
        holder.subText.setText(item.getSubTitle());
    }

    // getItemCount : 전체 데이터의 개수를 리턴
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView mainText;
        TextView subText;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조
            imageView = itemView.findViewById(R.id.postImage);
            mainText = itemView.findViewById(R.id.postTitle);
            subText = itemView.findViewById(R.id.postSubTitle);
        }
    }
}


