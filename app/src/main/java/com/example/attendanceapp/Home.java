package com.example.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {
    //fire base auth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //iniatializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //get user
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        //initialise views
        TextView textViewUserEmail = (TextView) findViewById(R.id.textView_hometitle);

        //displaying logged in user name
        textViewUserEmail.setText("Welcome " + currentUser.getEmail());

    }
    public void logOut(View view){
        //log out the user
        firebaseAuth.signOut();
        //closing activity
        finish();
        //starting login activty
        startActivity(new Intent(Home.this, LoginFragment.class));
    }

}
