package com.example.attendanceapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class OpencheckFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private View view;
    private Context context;
    private FirebaseFirestore Attendance;
    private Button openCheck, closeCheck;
    private EditText courseCode, courseTitle, date;
    public OpencheckFragment() {
        // Required empty public constructor
    }

    public OpencheckFragment (Context context, ViewPager viewPager) {
        this.context = context;
        this.viewPager = viewPager;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_opencheck, container, false);
        Attendance= FirebaseFirestore.getInstance();
        openCheck = view.findViewById(R.id.open_check);
        courseCode = view.findViewById(R.id.editText_CourseCode);
        courseTitle = view.findViewById(R.id.editText_CourseTitle);
        //date = view.findViewById(R.id.editText_date);
        Attendance = FirebaseFirestore.getInstance();
        openCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartAttendanceCapture(); // open sign in window
            }
        });
        return view;
    }


    private void StartAttendanceCapture() {
        //Get text from text boxes that user inputs

        String CourseCode = courseCode.getText().toString();
        String CourseTitle = courseTitle.getText().toString();
        String Date = new Date().toString();
        //Create new document name
        String docName = CourseCode + "_" + CourseTitle + "_" + Date;
        //Adding data to map
        Map<String, Object> Data = new HashMap<>();
        Data.put("DateAdded", new Timestamp(new Date()));
        Map<String, Object> data = new HashMap<>();
        data.put("Admin", Data);
        //Adding map to the database
        Attendance.collection("Attendance").document(CourseCode).collection(CourseTitle).document(Date).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                popToast(context, "Data added successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                popToast(context, "Data addition failed");
            }
        });


    }

    public void popToast(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
    }
}
