package com.example.beproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomTabAdapter extends FragmentPagerAdapter {
    private FragmentManager fragmentManager;

    List<Fragment> fragmentList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();

    public CustomTabAdapter(FragmentManager fm){
        super(fm);
        this.fragmentManager = fm;
    }

    public void addFragment(Fragment fragment , String strTitle){
        fragmentList.add(fragment);
        titleList.add(strTitle);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
