package com.example.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class StaffMenuActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_menu);
        //Get tab layout and view pager references
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.Staff_pager);
        // create view pager adapter
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        AddUsers AddU = new AddUsers(getApplicationContext(),viewPager);
        OpencheckFragment Opencheck = new OpencheckFragment(getApplicationContext(),viewPager);
        Module_fragment Mod = new Module_fragment(getApplicationContext(),viewPager);
        Course_fragment Course = new Course_fragment(getApplicationContext(),viewPager);
        viewPagerAdapter.addFragments(Opencheck, "Open check in");
        viewPagerAdapter.addFragments(AddU, "AddUsers");
        viewPagerAdapter.addFragments(Mod, "Module attendance");
        viewPagerAdapter.addFragments(Course, "Course attendance");
        viewPager.setAdapter(viewPagerAdapter);
        //set up tab layout w/ pager
        tabLayout.setupWithViewPager(viewPager);
    }
}
