package com.example.cafyp.AdminMain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafyp.Database.AdminHelpherClass;
import com.example.cafyp.R;
import com.example.cafyp.User.UserDashBoard;
import com.example.cafyp.session.SessionManagerAdmin;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class CreateAdminForm extends AppCompatActivity {

    TextInputLayout adminName, adminUsername, adminIc, adminEmail, adminPhoneno, adminPassword, adminConfirmPassword;
    TextView admininvicode;
    String admininvicode2;
    Button createbtn;
    FirebaseDatabase madatabase;
    DatabaseReference maRef;

    ImageView homebtn, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_admin_form);

        adminName = findViewById(R.id.put_admin_name);
        adminUsername = findViewById(R.id.put_admin_username);
        adminIc = findViewById(R.id.put_admin_ic);
        adminEmail = findViewById(R.id.put_admin_email);
        adminPhoneno = findViewById(R.id.put_admin_phonenumber);
        adminPassword = findViewById(R.id.put_admin_password);
        adminConfirmPassword = findViewById(R.id.put_admin_confirmpassword);
        admininvicode = findViewById(R.id.put_admin_invitationcode);
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(this, SessionManagerAdmin.SESSION_ADMINSESSION);
        HashMap<String, String> adminDetails = sessionManagerAdmin.getAdminDataDetailFromSession();
        String admininvitationcode = adminDetails.get(SessionManagerAdmin.KEY_ADMININVITATIONCODE);

        admininvicode.setText(admininvitationcode);
        admininvicode2 = admininvitationcode;


        createbtn = findViewById(R.id.create_admin_btn);

        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validateemail()|!validatename()|!validateconfirmpassword()|!validatenric()|!validatepassword()|!validatephoneNo()|!validateusername()){
                    return;
                }

                if (!CheckPassword() == true) {
                    return;
                }


                String adminname = adminName.getEditText().getText().toString();
                String adminusername = adminUsername.getEditText().getText().toString();
                String adminic = adminIc.getEditText().getText().toString();
                String adminemail = adminEmail.getEditText().getText().toString();
                String adminphone = adminPhoneno.getEditText().getText().toString();
                String adminpassword = adminPassword.getEditText().getText().toString();
                String adminconfirmpassword = adminConfirmPassword.getEditText().getText().toString();

                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                String admindate = date;

                String adminstate = "ADMIN";

                Query checkadminexist = FirebaseDatabase.getInstance().getReference("Admins").orderByChild("username").equalTo(adminusername);
                checkadminexist.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            adminUsername.setError("this username has been used by others");
                        }
                        else
                        {
                            madatabase = FirebaseDatabase.getInstance();
                            maRef = madatabase.getReference("Admins");
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference(admininvicode2).child("Admins");

                            AdminHelpherClass addNewAdmin = new AdminHelpherClass(adminname, adminusername, adminic, adminemail, adminphone, adminpassword, admindate, adminstate, admininvicode2);
                            AdminHelpherClass addNewAdmin2 = new AdminHelpherClass(adminname, adminusername, adminic, adminemail, adminphone, adminpassword, admindate, adminstate, admininvicode2);

                            maRef.child(adminusername).setValue(addNewAdmin);
                            reference.child(adminusername).setValue(addNewAdmin2);
                            Toast.makeText(CreateAdminForm.this, "Create successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), CreateAndViewAdmin.class));
                            finish();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

            }

        });


        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainAdminDashBoard.class);
                startActivity(intent);
                finish();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateAndViewAdmin.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean CheckPassword() {

        String adminpassword = adminPassword.getEditText().getText().toString();
        String adminconfirmpassword = adminConfirmPassword.getEditText().getText().toString();

        if (!adminpassword.equals(adminconfirmpassword)) {
            adminConfirmPassword.setError("Confirm password is not same with the password");
            return false;
        } else {
            adminConfirmPassword.setError(null);
            adminConfirmPassword.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatename() {
        String val = adminName.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            adminName.setError("Field can not be empty");
            return false;
        }
        else {
            adminName.setError(null);
            adminName.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateusername() {
        String val = adminUsername.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            adminUsername.setError("Field can not be empty");
            return false;
        }
        else {
            adminUsername.setError(null);
            adminUsername.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateemail() {
        String val = adminEmail.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            adminEmail.setError("Field can not be empty");
            return false;
        }else if (!val.matches(checkEmail)) {
            adminEmail.setError("Invalid Email");
            return false;
        }
        else {
            adminEmail.setError(null);
            adminEmail.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatenric() {
        String val = adminIc.getEditText().getText().toString().trim();
        String checkNRIC = ".{12,12}";

        if (val.isEmpty()) {
            adminIc.setError("Field can not be empty");
            return false;
        }else if (!val.matches(checkNRIC)) {
            adminIc.setError("Ic Number must be 12 number");
            return false;
        }
        else {
            adminIc.setError(null);
            adminIc.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatephoneNo() {
        String val = adminPhoneno.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            adminPhoneno.setError("Field can not be empty");
            return false;
        }
        else {
            adminPhoneno.setError(null);
            adminPhoneno.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatepassword() {
        String val = adminPassword.getEditText().getText().toString().trim();
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
            adminPassword.setError("Field can not be empty");
            return false;
        }else if (!val.matches(checkPassword)) {
            adminPassword.setError("Password should contain 8 characters, at least 1 digit, 1 lower case letter, 1 upper case letter, 1 speacial letter and no white spaces");
            return false;
        }
        else {
            adminPassword.setError(null);
            adminPassword.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateconfirmpassword() {
        String val = adminConfirmPassword.getEditText().getText().toString().trim();
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
            adminConfirmPassword.setError("Field can not be empty");
            return false;
        }else if (!val.matches(checkPassword)) {
            adminConfirmPassword.setError("Password should contain 8 characters, at least 1 digit, 1 lower case letter, 1 upper case letter, 1 speacial letter and no white spaces");
            return false;
        }
        else {
            adminConfirmPassword.setError(null);
            adminConfirmPassword.setErrorEnabled(false);
            return true;
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), CreateAndViewAdmin.class);
        startActivity(intent);
        finish();
    }
}