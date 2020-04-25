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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class Module_fragment extends Fragment {
    private View view;
    private TextView textView;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private Button button;
    private Context context;
    private TextView forgot_pass;
    //Viewpager controls tab layout
    private ViewPager viewPager;

    public Module_fragment() {
        // Required empty public constructor
    }

    public Module_fragment(Context context, ViewPager viewPager) {
        this.context = context;
        this.viewPager = viewPager;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_module_fragment, container, false);
    }
}
//    private void GetAttendanceStudent(final String StudentNo) {
//
//        CollectionReference docRef = OAdatabase.collection("Attendance").document("Attentest").collection("COM123");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Log.d(TAG, document.getId() + " => " + document.getData());
//                        NumOfDocs++;
//                        if(document.getString("StudentNo").equals(StudentNo)) {
//                            Count++;
//                        }
//                    }
//                } else {
//                    Log.d(TAG, "Error getting documents: ", task.getException());
//                }
//            }
//        });
//        double Percent = (double)Count/NumOfDocs;
//        Percentage.setText("" + Percent);
//    }
//
//}


