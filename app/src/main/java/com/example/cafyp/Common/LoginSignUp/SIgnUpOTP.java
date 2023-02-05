package com.example.cafyp.Common.LoginSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.cafyp.Admin.RelatedMemberView;
import com.example.cafyp.Common.MainActivity;
import com.example.cafyp.Database.UserHelpherClass;
import com.example.cafyp.R;
import com.example.cafyp.User.UserDashBoard;
import com.example.cafyp.session.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class SIgnUpOTP extends AppCompatActivity {

    PinView pin;
    String codebysystem;

    //TextView signupUser;

    String fullName, email, NRIC, address, postCode, state, password, date, invitationcode, phoneNo;
    String memberusername;
    String invicode, whatToDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_otp);

        pin = findViewById(R.id.signuppin_view);

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

        invicode = getIntent().getStringExtra("invicode");


        senVerificationCodeToSignupUser(phoneNo);
    }

    private void senVerificationCodeToSignupUser(String phoneNo) {
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
                        verifySignupOTPCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {

                    Toast.makeText(SIgnUpOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            };

    private void verifySignupOTPCode(String code) {
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

                                storeNewUserData();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(SIgnUpOTP.this, "Verification unsuccessfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


    private void storeNewUserData() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child(invitationcode).child("UserChecked");

        UserHelpherClass addNewUser = new UserHelpherClass(fullName, email, NRIC, address, postCode, state, password,
                invitationcode, date, phoneNo,memberusername);

        myRef.child(phoneNo).setValue(addNewUser);

        Toast.makeText(SIgnUpOTP.this, "Verify successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), Login.class);

        startActivity(intent);
        finish();
    }

    public void SignUpBacktoMain(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SIgnUpOTP.this);
        builder.setTitle("Are your sure to quit OTP?");

        builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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

    public void callNextScreenFromSignupOTP(View view) {

        String code = pin.getText().toString();
        if (!code.isEmpty()) {
            verifySignupOTPCode(code);
        }
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SIgnUpOTP.this);
        builder.setTitle("Are your sure to quit OTP?");

        builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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

}

