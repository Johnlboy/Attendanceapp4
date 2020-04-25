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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
//Displays in main Activity
public class SignUpFragment extends Fragment {
private View view;
private Context context;
private FirebaseAuth firebaseAuth;
private ProgressBar progressBar;
private ViewPager viewPager;
private EditText Username,Fname,Sname;
private Button button;
private Map<String, Object> data = new HashMap<>();
private final static String TAG = "SignUpFragment";
    private FirebaseFirestore database;
    String StudentNo;
    String LName;
    String Firstname;

    public SignUpFragment() {
        // Required empty public constructor
    }
    public SignUpFragment(Context context, ViewPager viewPager){
            this.context=context;
            this.viewPager=viewPager;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        firebaseAuth = FirebaseAuth.getInstance();


        button = view.findViewById(R.id.button_register);
        firebaseAuth = FirebaseAuth.getInstance();
        Username = view.findViewById(R.id.enterEmail);
        Fname = view.findViewById(R.id.Forename);
        Sname = view.findViewById(R.id.Surname);
        database= FirebaseFirestore.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(view);
                goLogin(view);
            }
        });

        return view;
    }
    public void popToast(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
    }

    public void goLogin(View v) {
        if(viewPager != null) {
            viewPager.setCurrentItem(0, true);
        }

    }

    public void registerUser(View v) {

        getDetails();
        boolean valid = validation();
        //Get email & password
        String password = ((EditText) v.findViewById(R.id.Password)).getText().toString();

        //Check if email and password are not empty

        if (TextUtils.isEmpty(password) && !valid) {
            popToast(context, "Please enter a valid password");
            return;
        }
        String email = createEmail();

        //Create a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //check if successful
                if(task.isSuccessful()) {
                    // Get rid of progress bar

                    popToast(context, "Registraition complete");

                }
                else{
                    //Get exception
                    FirebaseAuthException E = (FirebaseAuthException) task.getException();
                    //Display error
                    popToast(context,"Registraition failed " + E.getMessage());
                }
            }
        });

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




    private String createEmail() {
        String email = StudentNo + "@ulster.ac.uk";
        Log.i(TAG, "New email created " + email);
        return email;
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
    private void addDetailsToMap(String email) {
        data.put("BNo.", StudentNo);
        data.put("FName", Firstname);
        data.put("LName", LName);
        data.put("Email", email);
        data.put("dateStarted", new Timestamp(new Date()));
        Log.i(TAG, "Details add to map\n " + data.toString());
    }

    private void getDetails() {
        StudentNo = Username.getText().toString();
        Firstname = Fname.getText().toString();
        LName = Sname.getText().toString();
        //Log details
        Log.i(TAG, "Details got:\nStudent no.: " + StudentNo + "\nFname: " + Firstname + "\nLname " + LName);
    }

}
