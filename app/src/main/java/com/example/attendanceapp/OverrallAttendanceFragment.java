package com.example.attendanceapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class OverrallAttendanceFragment extends Fragment {
    private View view;
    private TextView Bno, Percentage;
    private FirebaseFirestore OAdatabase;
    String StudentNo;
    String percent;
    int Count = 0;
    int NumOfDocs = 0;


    public OverrallAttendanceFragment(Context applicationContext, ViewPager viewPager) {
        // Required empty public constructor

    }

    public OverrallAttendanceFragment(Context applicationContext, ViewPager viewPager, String BNo) {
        // Required empty public constructor
        StudentNo = BNo;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_overrall_attendance, container, false);
        OAdatabase = FirebaseFirestore.getInstance();
        Bno = view.findViewById(R.id.StudentID);
        Bno.setText(StudentNo);
        Percentage = view.findViewById(R.id.Percentage);
        GetAttendanceStudent();

        return view;

    }


    private void GetAttendanceStudent() {

        CollectionReference docRef = OAdatabase.collection("Attendance").document("Attentest").collection("COM123").document("25.04.20").collection("25.04.20");
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        NumOfDocs++;
                        String doc = document.getId();
                        if(doc.equals(StudentNo)) { //Null pointer error
                            Count++;
                        }
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        double Percent = (double)Count/NumOfDocs;
        Percentage.setText("" + Percent);
    }

}
