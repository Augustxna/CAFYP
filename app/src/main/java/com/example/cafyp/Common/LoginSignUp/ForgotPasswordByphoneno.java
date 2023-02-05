package com.example.cafyp.Common.LoginSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.cafyp.Admin.RelatedMemberView;
import com.example.cafyp.R;
import com.example.cafyp.User.UserDashBoard;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import org.jetbrains.annotations.NotNull;

public class ForgotPasswordByphoneno extends AppCompatActivity {

    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber;
    Button nextbtn;
    String invicode;
    ImageView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_byphoneno);

        countryCodePicker = findViewById(R.id.forgotpassword_country_code_picker);
        phoneNumber = findViewById(R.id.forgotpassword_phone_number);
        nextbtn = findViewById(R.id.forgotpassword_nextbtn);
        backbtn = findViewById(R.id.back_button);


        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyPhonenumber();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void verifyPhonenumber() {

        if (!validateFields()) {
            return;
        }

        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();

        if (_phoneNumber.charAt(0) == '0') {
            _phoneNumber = _phoneNumber.substring(1);
        }

        String _completePhoneNumber = "+" + countryCodePicker.getSelectedCountryCode() + _phoneNumber;
        String fullPhoneNumber = "+" + countryCodePicker.getSelectedCountryCode() + _phoneNumber;

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
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                phoneNumber.setError(null);
                                phoneNumber.setErrorEnabled(false);

                                Intent intent = new Intent(getApplicationContext(), VerifyPhoneOTP.class);
                                intent.putExtra("phonenumber3", _completePhoneNumber);
                                intent.putExtra("whatToDo", "updateData");
                                intent.putExtra("invicode", invicode);
                                startActivity(intent);
                                finish();

                            } else {
                                phoneNumber.setError("No such user exist...");
                                phoneNumber.requestFocus();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });


                } else {
                    phoneNumber.setError("No such user exist...");
                    phoneNumber.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private boolean validateFields() {

        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();

        if (_phoneNumber.isEmpty()) {
            phoneNumber.setError("PhoneNumber cannot be empty");
            phoneNumber.requestFocus();
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }

}