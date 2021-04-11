package com.example.healingfeeling.ui.home;

import android.os.Bundle;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SongFragment extends Fragment {


    private RecyclerAdapter adapter;

    public static SongFragment newInstance() {
        SongFragment tab1 = new SongFragment();
        return tab1;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_song, container, false);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);


        GridLayoutManager GridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(GridLayoutManager);


        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
        getData();


        return root;
    }



    private void getData() {
        // 임의의 데이터입니다.
        List<String> listTitle = Arrays.asList("Celebrity", "밤하늘의 별을(2020)", "Dynamite", "잠이 오질 않네요",
                "Lovesick Girls", "에잇", "내 손을 잡아", "Blueming",
                "오래된 노래[스탠딩에그]", "Dolphin[오마이걸]");
        List<String> listSubtitle = Arrays.asList("아이유", "경서", "방탄소년단", "장범준",
                "블랙핑크", "아이유", "아이유", "아이유",
                "스탠딩에그", "오마이걸");
        List<String> listContent = Arrays.asList(
                "이 노래 좋아요.",
                "이 노래 좋아요..",
                "이 노래 좋아요.",
                "이 노래 좋아요.",
                "이 노래 좋아요.",
                "이 노래 좋아요.",
                "이 노래 좋아요.",
                "이 노래 좋아요.",
                "이 노래 좋아요.",
                "이 노래 좋아요."
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
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();
    }


}
