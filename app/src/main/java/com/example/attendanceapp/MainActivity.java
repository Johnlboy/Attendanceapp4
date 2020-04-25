package com.example.attendanceapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get tab layout and view pager references
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.Main_pager);
        // create view pager adapter
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        LoginFragment Login = new LoginFragment(getApplicationContext(),viewPager);
        SignUpFragment SignUp = new SignUpFragment(getApplicationContext(),viewPager);
        viewPagerAdapter.addFragments(Login, "Login");
        viewPagerAdapter.addFragments(SignUp, "SignUp");
        viewPager.setAdapter(viewPagerAdapter);
        //set up tab layout w/ pager
        tabLayout.setupWithViewPager(viewPager);

    }

}