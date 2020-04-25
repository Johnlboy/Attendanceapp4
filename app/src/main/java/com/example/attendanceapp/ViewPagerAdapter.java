package com.example.attendanceapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
//Constructor calling from FPA class
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> tabTitles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm); //Calling superclass constructor
    }

    public void addFragments(Fragment fragment, String title){
        fragments.add(fragment);
        tabTitles.add(title);
    }
    //Helps tabs be viewed on device
    public String getPageTitle(int position){
        return tabTitles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}