package com.example.cafyp.Common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cafyp.Admin.RelatedMemberView;
import com.example.cafyp.Common.MainActivity;
import com.example.cafyp.R;
import com.example.cafyp.User.UserDashBoard;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Signup extends AppCompatActivity {


    TextInputLayout fullName, email, NRIC, currentAddress, postcode, state, password;
    //TextView postcodetext;
    ArrayList<String> postcodeList;
    Dialog dialog;

    ImageView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafyp_signup);

        fullName = findViewById(R.id.signup_full_name);
        email = findViewById(R.id.signup_email);
        NRIC = findViewById(R.id.signup_nric);
        currentAddress = findViewById(R.id.signup_current_address);
        postcode = findViewById(R.id.signup_postcode);
        state = findViewById(R.id.signup_state);
        password = findViewById(R.id.signup_password);
        backbtn = findViewById(R.id.back_button);

        String namefromsignup2 = getIntent().getStringExtra("namefromsignup2");
        fullName.getEditText().setText(namefromsignup2);
        String emailfromsignup2 = getIntent().getStringExtra("emailfromsignup2");
        email.getEditText().setText(emailfromsignup2);
        String nricfromsignup2 = getIntent().getStringExtra("NRICfromsignup2");
        NRIC.getEditText().setText(nricfromsignup2);
        String addressfromsignup2 = getIntent().getStringExtra("addressfromsignup2");
        currentAddress.getEditText().setText(addressfromsignup2);
        String postcodefromsignup2 = getIntent().getStringExtra("postcodefromsignup2");
        postcode.getEditText().setText(postcodefromsignup2);
        String statefromsignup2 = getIntent().getStringExtra("statefromsignup2");
        state.getEditText().setText(statefromsignup2);
        String passwordfromsignup2 = getIntent().getStringExtra("passwordfromsignup2");
        password.getEditText().setText(passwordfromsignup2);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean validateFullName() {
        String val = fullName.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            fullName.setError("Field can not be empty");
            return false;
        }
        else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateNRIC() {
        String val = NRIC.getEditText().getText().toString().trim();
        String checkNRIC = ".{12,12}";

        if (val.isEmpty()) {
            NRIC.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkNRIC)) {
            NRIC.setError("IC Number must be 12 numbers");
            return false;
        }
        else {
            NRIC.setError(null);
            NRIC.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateCurrentAddress() {
        String val = currentAddress.getEditText().getText().toString().trim();
        //String checkAddress = "";

        if (val.isEmpty()) {
            currentAddress.setError("Field can not be empty");
            return false;
        } //else if (!val.matches(checkAddress)){
        //currentAddress.setError("Invalid address");
        //return false;
        //}
        else {
            currentAddress.setError(null);
            currentAddress.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePostcode() {
        String val = postcode.getEditText().getText().toString().trim();
        //String checkPostcode = "";

        if (val.isEmpty()) {
            postcode.setError("Field can not be empty");
            return false;
        } //else if (!val.matches(checkPostcode)){
        //postcode.setError("Invalid postcode");
        //return false;
        //}
        else {
            postcode.setError(null);
            return true;
        }
    }

    private boolean validateState() {
        String val = state.getEditText().getText().toString().trim();
        //String checkState = "";

        if (val.isEmpty()) {
            state.setError("Field can not be empty");
            return false;
        } //else if (!val.matches(checkState)){
        //state.setError("Invalid state");
        // return false;
        //}
        else {
            state.setError(null);
            state.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                "(?=.*[0-9])" +           //at least 1 digit
                "(?=.*[a-z])" +           //at least 1 lower case letter
                "(?=.*[A-Z])" +           //at least 1 upper case letter
                //"(?=.*[a-zA-Z])" +          //any letter
                "(?=.*[@#$%^&+=])" +        //at least 1 special character
                "(?=\\S+$)" +               //no white spaces
                ".{8,}" +                   //at least 8 characters
                "$";

        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            password.setError("Password should contain 8 characters, at least 1 digit, 1 lower case letter, 1 upper case letter, 1 speacial letter and no white spaces");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }


    public void callNextSignupScreen(View view) {


        if (!validateFullName() | !validateEmail() | !validateNRIC() | !validateCurrentAddress() | !validatePostcode() | !validateState() | !validatePassword()) {
            return;
        }

        String nameS = fullName.getEditText().getText().toString().trim();
        String emailS = email.getEditText().getText().toString().trim();
        String nricS = NRIC.getEditText().getText().toString().trim();
        String addressS = currentAddress.getEditText().getText().toString().trim();
        String postcodeS = postcode.getEditText().getText().toString().trim();
        String stateS = state.getEditText().getText().toString().trim();
        String passwordS = password.getEditText().getText().toString().trim();


        Intent intent = new Intent(Signup.this, Signup2nd.class);
        intent.putExtra("name", nameS);
        intent.putExtra("email", emailS);
        intent.putExtra("NRIC", nricS);
        intent.putExtra("address", addressS);
        intent.putExtra("postcode", postcodeS);
        intent.putExtra("state", stateS);
        intent.putExtra("password", passwordS);
        startActivity(intent);
        finish();

    }

    public void callLoginScreen(View view) {
        startActivity(new Intent(this, Login.class));
        finish();
    }


    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

}