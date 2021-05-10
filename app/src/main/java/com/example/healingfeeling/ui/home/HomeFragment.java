package com.example.healingfeeling.ui.home;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.healingfeeling.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;


    private TabLayout tabLayout;
    private ArrayList<String> tabNames = new ArrayList<>();


    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

      /*  tabNames.add("Song");
        tabNames.add("Book");
        tabNames.add("Place");
                setTabLayout();
                setViewPager();*/

        ViewPager viewPager = root.findViewById(R.id.viewPager);
        tabLayout = root.findViewById(R.id.tab);


        Bundle bundle = getArguments();  //번들 받기. getArguments() 메소드로 받음.
        if(bundle != null){
            String emotion = bundle.getString("emotion"); //Name 받기.
//            Log.d("home",emotion); //확인

        }



        tabLayout.addTab(tabLayout.newTab().setText("노래"));
        tabLayout.addTab(tabLayout.newTab().setText("도서"));
        tabLayout.addTab(tabLayout.newTab().setText("장소"));

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        FragmentAdapter fragmentStateAdapter = new FragmentAdapter(getChildFragmentManager());
        viewPager.setAdapter(fragmentStateAdapter);





        return root;
    }

    private void setTabLayout(){
        tabLayout = root.findViewById(R.id.tab);
        tabNames.stream().forEach(name ->tabLayout.addTab(tabLayout.newTab().setText(name)));
    }


    private void setViewPager() {
        // fragment 안에서 또 다른 fragment를 관리할 경우 getFragmentManager()가 아니라
        // getChildFragmentManager를 사용해줄 것 ! => 화면 유지
        FragmentAdapter adapter = new FragmentAdapter(getChildFragmentManager());
        ViewPager viewPager = root.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}