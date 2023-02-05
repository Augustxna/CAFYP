package com.example.cafyp.Common.LoginSignUp;

import androidx.annotation.NonNull;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafyp.Admin.AdminLogin;
import com.example.cafyp.Common.MainActivity;
import com.example.cafyp.session.SessionManager;
import com.example.cafyp.R;
import com.example.cafyp.User.UserDashBoard;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class Login extends AppCompatActivity {

    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber, password;
    RelativeLayout progressbar;
    String invicode;
    Button userloginbtn;
    Button forgotpasswordbtn;

    EditText phoneNoeditText;
    EditText passwordEditText;

    TextView loginpostcodetext;
    ArrayList<String> postcodeList;
    Dialog dialog;

    CheckBox rememberme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafyp_login);

        countryCodePicker = findViewById(R.id.login_country_code_picker);
        phoneNumber = findViewById(R.id.login_phone_number);
        password = findViewById(R.id.login_password);
        progressbar = findViewById(R.id.login_progressbar);
        userloginbtn = findViewById(R.id.user_login_button);
        forgotpasswordbtn = findViewById(R.id.forgotpassword_btn);
        rememberme = findViewById(R.id.rememberme_checkbox);
        phoneNoeditText = findViewById(R.id.member_loginphoneno_edittext);
        passwordEditText = findViewById(R.id.member_loginpassword_edittext);

        SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_REMEMBERME);
        if(sessionManager.checkRememberMe()){
            HashMap<String,String> remmeberMeDetails = sessionManager.getRememberMeDetailFromSession();
            phoneNoeditText.setText(remmeberMeDetails.get(SessionManager.KEY_REMEMBERMEPHONENUMBER));
            passwordEditText.setText(remmeberMeDetails.get(SessionManager.KEY_REMEMBERMEPASSWORD));

        }

        forgotpasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordByphoneno.class);
                startActivity(intent);
            }
        });

    }

    public void callSignupScreen(View view) {

        startActivity(new Intent(this, Signup.class));
    }

    public void BacktoMain(View view) {

        startActivity(new Intent(this, MainActivity.class));
    }


    public void loginToUserDashBoard(View view) {
        if (!validateFields()) {
            return;
        }

        progressbar.setVisibility(View.VISIBLE);

        //get data
        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();


        if (_phoneNumber.charAt(0) == '0') {
            _phoneNumber = _phoneNumber.substring(1);
        }

        String _completePhoneNumber = "+" + countryCodePicker.getSelectedCountryCode() + _phoneNumber;
        String fullPhoneNumber = "+" + countryCodePicker.getSelectedCountryCode() + _phoneNumber;


        if(rememberme.isChecked()){

            SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_REMEMBERME);
            sessionManager.createRememberSession(_phoneNumber,_password);
        }


        Query reference = FirebaseDatabase.getInstance().getReference("Members").orderByChild("phoneno").equalTo(fullPhoneNumber);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    String _code = dataSnapshot.child(fullPhoneNumber).child("invitationcode").getValue().toString();
                    invicode = _code;

                    FirebaseDatabase realdatabase = FirebaseDatabase.getInstance();
                    Query checkref = realdatabase.getReference(invicode).child("Users").orderByChild("phoneno").equalTo(_completePhoneNumber);

                    checkref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                phoneNumber.setError(null);
                                phoneNumber.setErrorEnabled(false);

                                String UserPassword = snapshot.child(_completePhoneNumber).child("password").getValue(String.class);
                                if (UserPassword.equals(_password)) {
                                    password.setError(null);
                                    password.setErrorEnabled(false);

                                    String _fullname = snapshot.child(_completePhoneNumber).child("fullName").getValue(String.class);
                                    String _email = snapshot.child(_completePhoneNumber).child("email").getValue(String.class);
                                    String _nric = snapshot.child(_completePhoneNumber).child("nric").getValue(String.class);
                                    String _date = snapshot.child(_completePhoneNumber).child("birth").getValue(String.class);
                                    String _address = snapshot.child(_completePhoneNumber).child("address").getValue(String.class);
                                    String _password = snapshot.child(_completePhoneNumber).child("password").getValue(String.class);
                                    String _resadminid = snapshot.child(_completePhoneNumber).child("resadminid").getValue(String.class);
                                    String _phoneno = snapshot.child(_completePhoneNumber).child("phoneno").getValue(String.class);
                                    String _invitationcode = snapshot.child(_completePhoneNumber).child("invitationcode").getValue(String.class);
                                    String _memberusername = snapshot.child(_completePhoneNumber).child("memberusername").getValue(String.class);

                                    SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_USERSESSION);
                                    sessionManager.createLoginSession(_fullname, _email, _nric, _date, _address, _password, _resadminid, _phoneno, _invitationcode, _memberusername);

                                    Intent intent = new Intent(getApplicationContext(), UserDashBoard.class);
                                    startActivity(intent);
                                    finish();

                                    Toast.makeText(Login.this, _fullname + "\n" + _phoneno + "\n" + _password + "\n" + "Login", Toast.LENGTH_SHORT).show();
                                } else {
                                    progressbar.setVisibility(View.GONE);
                                    Toast.makeText(Login.this, "wrong password!", Toast.LENGTH_SHORT).show();

                                }


                            } else {
                                progressbar.setVisibility(View.GONE);
                                Toast.makeText(Login.this, "wrong phone number!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressbar.setVisibility(View.GONE);
                            Toast.makeText(Login.this, "wrong id!", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "wrong id!", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(Login.this, "something error!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean validateFields() {

        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        if (_phoneNumber.isEmpty()) {
            phoneNumber.setError("PhoneNumber cannot be empty");
            phoneNumber.requestFocus();
            return false;
        } else if (_password.isEmpty()) {
            password.setError("Password cannot be empty");
            password.requestFocus();
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }

    public void ToAdminLoginPage(View view) {
        Intent intent = new Intent(Login.this, AdminLogin.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
