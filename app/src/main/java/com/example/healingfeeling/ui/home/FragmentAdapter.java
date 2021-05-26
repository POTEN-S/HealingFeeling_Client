package com.example.healingfeeling.ui.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.healingfeeling.ui.chatting.ChattingFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList=new ArrayList<>();

    private ArrayList<String> fragmentArrayListTitle=new ArrayList<>();

    public FragmentAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return SongFragment.newInstance();
            case 1:
                return BookFragment.newInstance();
            case 2:
                return PlaceFragment.newInstance();
            case 3:
                return ChattingFragment.newInstance();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 4;
    }


    public void addFrag(Fragment fragment,String title){
        fragmentArrayList.add(fragment);
        fragmentArrayListTitle.add(title);
    }



}