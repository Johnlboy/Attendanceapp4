package com.example.attendanceapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;



/**
 *
 */
public class AddUsers extends Fragment {
    private View view;
    private TextView textView;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private Button button;
    private Context context;
    private TextView forgot_pass;
    private EditText Username,Fname,Sname;
    private Map <String, Object> data = new HashMap<>();
    private final static String TAG = "AddUsers";
    //Viewpager controls tab layout
    private ViewPager viewPager;
    //Firebase reference
    private FirebaseFirestore database;
    String StudentNo;
    String LName;
    String Firstname;


    public AddUsers() {

        // Required empty public constructor
    }
    public AddUsers(Context context, ViewPager viewPager) {
        this.context = context;
        this.viewPager = viewPager;
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_users, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        Username = view.findViewById(R.id.enterEmail);
        Fname = view.findViewById(R.id.Forename);
        Sname = view.findViewById(R.id.Surname);
        database= FirebaseFirestore.getInstance();
        button = view.findViewById(R.id.button_register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });
        return view;
    }

    private void addStudent() {
        //Get entered details
        getDetails();
        //Check if details are valid
        boolean valid = validation();
        if (valid) {
            String email = createEmail();
            addDetailsToMap(email);
            addStudentToDB(StudentNo);
        }

    }

    private void addStudentToDB(String studentNo) {
        database.collection("students").document(studentNo)
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        popToast(context, "Student details saved successfully");
                        Log.i(TAG, "Student added to databse");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("AddUsers", "Error adding page", e);

            }
        });
    }

    private String createEmail() {
        String email = StudentNo + "@ulster.ac.uk";
        Log.i(TAG, "New email created " + email);
        return email;
    }

    private void addDetailsToMap(String email) {
        data.put("BNo.", StudentNo);
        data.put("FName", Firstname);
        data.put("LName", LName);
        data.put("Email", email);
        data.put("dateStarted", new Timestamp(new Date()));
        Log.i(TAG, "Details add to map\n " + data.toString());
    }

    public void popToast(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
    }
    private boolean validation() {
        boolean valid = false;
        //Check if details are empty
        if (TextUtils.isEmpty(StudentNo))
            popToast(context, "Enter B number");
        else if (TextUtils.isEmpty(Firstname))
            popToast(context, "Enter first name");
        else if (TextUtils.isEmpty(LName))
            popToast(context, "Enter last name ");
        else
            valid = true;
        if(valid)
            Log.i(TAG,"Details Checked (OK)" );
        else
            Log.i(TAG, "Details Checked (Not OK");
        //Return
        return valid;
    }
    private void getDetails() {
        StudentNo = Username.getText().toString();
        Firstname = Fname.getText().toString();
        LName = Sname.getText().toString();
        //Log details
        Log.i(TAG, "Details got:\nStudent no.: " + StudentNo + "\nFname: " + Firstname + "\nLname " + LName);
    }


}
