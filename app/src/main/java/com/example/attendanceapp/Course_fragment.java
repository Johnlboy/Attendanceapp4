package com.example.attendanceapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class Course_fragment extends Fragment {
    private View view;
    private TextView textView;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private Button button;
    private Context context;
    private TextView forgot_pass;
    //Viewpager controls tab layout
    private ViewPager viewPager;

    public Course_fragment() {
        // Required empty public constructor
    }
    public Course_fragment(Context context, ViewPager viewPager) {
        this.context = context;
        this.viewPager = viewPager;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_course_fragment, container, false);

        return view;
    }
}
