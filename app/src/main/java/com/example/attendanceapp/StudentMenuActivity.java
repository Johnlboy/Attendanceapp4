package com.example.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class StudentMenuActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu);
        //Get tab layout and view pager references
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.Student_pager);
        String email = this.getIntent().getExtras().getString("email");
        String Bno = this.getIntent().getExtras().getString("StudentNo.");

        // create view pager adapter
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        OverrallAttendanceFragment TotalAtten = new OverrallAttendanceFragment(getApplicationContext(),viewPager, Bno);
        CheckIn checkIn = new CheckIn(getApplicationContext(),viewPager, email);

        viewPagerAdapter.addFragments(checkIn, "Check in");
        viewPagerAdapter.addFragments(TotalAtten, "OverallAttendance");

        viewPager.setAdapter(viewPagerAdapter);
        //set up tab layout w/ pager
        tabLayout.setupWithViewPager(viewPager);

    }
}
