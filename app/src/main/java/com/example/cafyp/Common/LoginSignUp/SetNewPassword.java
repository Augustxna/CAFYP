package com.example.cafyp.Common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cafyp.Admin.RelatedMemberView;
import com.example.cafyp.R;
import com.example.cafyp.User.UserDashBoard;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetNewPassword extends AppCompatActivity {

    TextInputLayout getnewpassword, getnewconfirmpassword;
    Button setnewpasswordbtn;
    ImageView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        setnewpasswordbtn = findViewById(R.id.setnewpassword_btn);
        getnewpassword = findViewById(R.id.setnewpassword);
        getnewconfirmpassword = findViewById(R.id.setnewconfirmpassword);
        backbtn = findViewById(R.id.back_button);

        setnewpasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewPasswordFunction();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordByphoneno.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setNewPasswordFunction() {

        if (!validatePhoneno()) {
            return;
        }

        String newpassword = getnewpassword.getEditText().getText().toString().trim();
        String _phonenumber = getIntent().getStringExtra("phoneNo");
        String _invicode = getIntent().getStringExtra("invicode");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Members");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(_invicode).child("Users");

        reference.child(_phonenumber).child("password").setValue(newpassword);
        ref.child(_phonenumber).child("password").setValue(newpassword);

        startActivity(new Intent(getApplicationContext(), ForgetPasswordSuccessMessage.class));
        Toast.makeText(getApplicationContext(), "reset successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean validatePhoneno() {

        String newpassword = getnewpassword.getEditText().getText().toString().trim();
        String newconfirmpassword = getnewconfirmpassword.getEditText().getText().toString().trim();

        if (newpassword.equals(newconfirmpassword)) {
            getnewconfirmpassword.setError(null);
            getnewconfirmpassword.setErrorEnabled(false);
            return true;
        } else {
            getnewconfirmpassword.setError("Confirm assword is not same");
            return false;

        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), ForgotPasswordByphoneno.class);
        startActivity(intent);
        finish();
    }

}