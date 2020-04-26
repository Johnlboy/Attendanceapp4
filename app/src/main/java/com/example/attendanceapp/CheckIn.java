package com.example.attendanceapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
    private Map<String, Object> data = new HashMap<>();
    private Context context;
    private ViewPager viewPager;
    private String UserEmail;
    private String DocID;
    private String Bno;
    private EditText Ctitle, CCode;
    public CheckIn() {
    }


    public CheckIn(Context applicationContext, ViewPager viewPager, String userEmail, String Bno) {
        // Required empty public constructor
        context = applicationContext;
        this.viewPager = viewPager;
        UserEmail = userEmail;
        this.Bno = Bno;
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
        CCode = view.findViewById(R.id.CourseCode);
        Ctitle = view.findViewById(R.id.CourseTitle);
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
        String CourseCode = CCode.getText().toString();
        String CourseTitle = Ctitle.getText().toString();
        StudentData.put("UserEmail", UserEmail);
        StudentData.put("DateAdded", new Timestamp(new Date()));
        data.put(Bno, StudentData);
        String Date = new Date().toString();
        Atten.collection("Attendance").document(CourseCode).collection(CourseTitle).document(Date).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                popToast(getContext(), "Checked in for class");
            }
        });

    }

    public void popToast(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
    }
}
