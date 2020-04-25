package com.example.attendanceapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
//displays in main activity
public class LoginFragment extends Fragment {
    private View view;
    private TextView textView;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore AddDB;
    private ProgressBar progressBar;
    private Button button;
    private Context context;
    private TextView forgot_pass;
    //Viewpager controls tab layout
    private ViewPager viewPager;
    private Map<String, Object> data = new HashMap<>();
    private final static String TAG = "LoginFragment";
    //private DatabaseReference aDatabase;
    private String email, Bno;


    public LoginFragment() {
        // Required empty public constructor
    }

    public LoginFragment(Context context, ViewPager viewPager) {
        this.context = context;
        this.viewPager = viewPager;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);

        //Get firebase
        firebaseAuth = FirebaseAuth.getInstance();
        AddDB = FirebaseFirestore.getInstance();
        //aDatabase = FirebaseDatabase.getInstance().getReference("AttendanceDB");
        //aDatabase.child("email");
        //Get button
        button = view.findViewById(R.id.button_Login);
        //get textView
        textView = view.findViewById(R.id.textView_RegisterNow);
        forgot_pass = view.findViewById(R.id.textView_ForgotPword);
        //set onClick listeners
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin(v);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRegister(v);
            }
        });
        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgottenPassword(v);
            }
        });
        return view;
    }
    public void goRegister(View v) {
        if (viewPager != null)
            viewPager.setCurrentItem(1, true);
    }

    public void userLogin(View v) {
        //Get email and password
        Bno = ((EditText) view.findViewById(R.id.email_login)).getText().toString();
        final String password = ((EditText) view.findViewById(R.id.pass_login)).getText().toString();
        email = createEmail(Bno);
        //Check if email and password are empty
        if (TextUtils.isEmpty(email)) {
            popToast(context, "Please enter email");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            popToast(context, "Please enter password");
            return;
        }
        //Sign in with email and password
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Check if successful
                        if(task.isSuccessful()) {
                            data.put("email", email);
                            data.put("password", password);

                            //Display Message
                            popToast(context, "Login Successful");
                            Intent intent;
                            if (isStudent(email)) {

                                AddDB.collection("Students").document(email).set(data);
                                intent = new Intent(context, StudentMenuActivity.class);
                                intent.putExtra("email", email);
                                intent.putExtra("StudentNo.", Bno);
                            }
                            else
                                intent = new Intent(context, StaffMenuActivity.class);
                            //Start new activity
                            startActivity(intent);
                            closeActvity();

                        } else {
                            //Get exception
                            FirebaseAuthException e = (FirebaseAuthException) task.getException();
                            //Display error message
                            popToast(context, "Failed Login"+e.getMessage());
                        }



                    }
                    private boolean isStudent(String email) {
                        boolean isStudent = false;
                        if (email != null && !email.equals("")) {
                            char firstLetter = email.charAt(0);
                            if (firstLetter == 'B' || firstLetter == 'b')
                                isStudent = true;
                        }
                        return isStudent;

                    }

                    private boolean isStaff(String email) {
                        boolean isStaff = false;
                        if (email != null && !email.equals("")) {
                            char firstLetter = email.charAt(0);
                            if (firstLetter == 'E' || firstLetter == 'e')
                                isStaff = true;
                        }
                        return isStaff;
                    }

                });



    }
    public void forgottenPassword(View v) {

    }
    public void popToast(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
    }

    public void closeActvity() {
        Activity activity = getActivity();
        if (activity != null )
            activity.finish();
    }

    //private void getDetails() {
        //StudentNo = emil_Login.getText().toString();
        //Firstname = Fname.getText().toString();
        //LName = Sname.getText().toString();
        //Log details
        //Log.i(TAG, "Details got:\nStudent no.: " + StudentNo + "\nFname: " + Firstname + "\nLname " + LName);
    //}

    private String createEmail(String StudentNo) {
        String email = StudentNo + "@ulster.ac.uk";
        Log.i(TAG, "New email created " + email);
        return email;
    }


}
