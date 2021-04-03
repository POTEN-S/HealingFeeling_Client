package com.example.healingfeeling.ui.home;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.healingfeeling.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;


    private TabLayout tabLayout;
    private ArrayList<String> tabNames = new ArrayList<>();



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                loadTabName();
                setTabLayout();
                setViewPager();

            }

            @TargetApi(Build.VERSION_CODES.N)
            private void setTabLayout(){
                tabLayout = root.findViewById(R.id.tab);
                tabNames.stream().forEach(name ->tabLayout.addTab(tabLayout.newTab().setText(name)));
            }

            private void loadTabName(){
                tabNames.add("Song");
                tabNames.add("Book");
                tabNames.add("Place");
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

        });
        return root;
    }




}