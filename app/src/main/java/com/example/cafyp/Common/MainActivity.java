package com.example.cafyp.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;

import com.example.cafyp.Common.LoginSignUp.Login;
import com.example.cafyp.Common.LoginSignUp.Signup;
import com.example.cafyp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void callLoginScreen(View view) {

        startActivity(new Intent(this, Login.class));
    }

    public void callSignupScreen(View view) {

        startActivity(new Intent(this, Signup.class));
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}