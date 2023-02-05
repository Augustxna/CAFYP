package com.example.cafyp.Common.LoginSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.cafyp.Common.MainActivity;
import com.example.cafyp.Database.UserHelpherClass;
import com.example.cafyp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class VerifyPhoneOTP extends AppCompatActivity {

    PinView pin;
    String codebysystem;

    //TextView signupUser;

    String fullName, email, NRIC, address, postCode, state, password, date, invitationcode, phoneNo, memberusername;
    String invicode, whatToDo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_o_t_p);

        //signupUser = (Button) findViewById(R.id.signup_verify_button);
        //signupUser.setOnClickListener(this);

        pin = findViewById(R.id.pin_view);

        fullName = getIntent().getStringExtra("name3");
        email = getIntent().getStringExtra("email3");
        NRIC = getIntent().getStringExtra("nric3");
        address = getIntent().getStringExtra("address3");
        postCode = getIntent().getStringExtra("postcode3");
        state = getIntent().getStringExtra("state3");
        password = getIntent().getStringExtra("password3");
        invitationcode = getIntent().getStringExtra("invitationcode3");
        date = getIntent().getStringExtra("date3");
        phoneNo = getIntent().getStringExtra("phonenumber3");
        memberusername = getIntent().getStringExtra("memberusername");

        whatToDo = getIntent().getStringExtra("whatToDo");
        invicode = getIntent().getStringExtra("invicode");


        senVerificationCodeToUser(phoneNo);
    }

    private void senVerificationCodeToUser(String phoneNo) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codebysystem = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        pin.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {

                    Toast.makeText(VerifyPhoneOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codebysystem, code);
        signInWithPhoneAuthCredential(credential);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                                updateOldUserData();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(VerifyPhoneOTP.this, "Verification unsuccessfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void updateOldUserData() {

        Intent intent = new Intent(getApplicationContext(), SetNewPassword.class);
        intent.putExtra("phoneNo", phoneNo);
        intent.putExtra("invicode", invicode);
        startActivity(intent);
        finish();
    }

    private void storeNewUserData() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child(invitationcode).child("UserChecked");
        /*FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference reference = database2.getReference().child(invitationcode).child("Users");*/


        UserHelpherClass addNewUser = new UserHelpherClass(fullName, email, NRIC, address, postCode, state, password, invitationcode, date, phoneNo, memberusername);

        //UserHelpherClass CheckUSer = new UserHelpherClass(fullName,email,NRIC,address,postCode,state,password,invitationcode,date,phoneNo);

        myRef.child(phoneNo).setValue(addNewUser);
        //reference.child(phoneNo).setValue(CheckUSer);
        Toast.makeText(VerifyPhoneOTP.this, "Verify successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), Login.class);
        /*intent.putExtra("name3", fullName);
        intent.putExtra("email3", email);
        intent.putExtra("nric3", NRIC);
        intent.putExtra("address3", address);
        intent.putExtra("postcode3", postCode);
        intent.putExtra("state3", state);
        intent.putExtra("password3", password);
        intent.putExtra("invitationcode3", invitationcode);
        intent.putExtra("date3", date);
        intent.putExtra("phonenumber3", phoneNo);*/
        startActivity(intent);
        finish();
    }

    public void BacktoMain(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(VerifyPhoneOTP.this);
        builder.setTitle("Are your sure to quit OTP?");

        builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(getApplicationContext(),"Canceled", Toast.LENGTH_SHORT).show();

            }
        });
        builder.show();
    }


    public void callNextScreenFromOTP(View view) {

        String code = pin.getText().toString();
        if (!code.isEmpty()) {
            verifyCode(code);
        }
    }
}

   /* private void signupUser() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");

        myRef.setValue("Hello, World!");
        Toast.makeText(VerifyPhoneOTP.this, "Verify successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();

    }}*/
