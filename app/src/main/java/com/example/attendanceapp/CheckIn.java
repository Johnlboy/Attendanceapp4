package com.example.attendanceapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.OrderBy;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class CheckIn extends Fragment {
    private View view;
    private FirebaseFirestore Atten;
    private Button CheckIn;
    private TextView Title;
    private Map<String, Object> StudentData = new HashMap<>();
    private Context context;
    private ViewPager viewPager;
    private String UserEmail;
    private String DocID;

    public CheckIn() {
    }


    public CheckIn(Context applicationContext, ViewPager viewPager, String userEmail) {
        // Required empty public constructor
        context = applicationContext;
        this.viewPager = viewPager;
        UserEmail = userEmail;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_check_in, container, false);
        CheckIn = view.findViewById(R.id.button_Checkin);
        CheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signin();
            }
        });
        Title = view.findViewById(R.id.textView_CheckTitle);
        Atten = FirebaseFirestore.getInstance();
        return view;
    }

    public void Signin() {

        Query result = Atten.collection("Attendance")
                .orderBy("", Query.Direction.DESCENDING).limit(1);
        result.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                List<DocumentSnapshot> Docs = queryDocumentSnapshots.getDocuments();
                DocumentSnapshot doc = Docs.get(0);
                DocID = doc.getId();

            }
        });
        StudentData.put("UserEmail", UserEmail);
        StudentData.put("DateAdded", new Timestamp(new Date()));
        Atten.collection("Attendance").document(DocID).set(StudentData);

    }
}
