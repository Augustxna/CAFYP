package com.example.cafyp.User;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafyp.Admin.RelatedMemberView;
import com.example.cafyp.Database.UserUpdateProfileHelpherClass;
import com.example.cafyp.User.Heirs.HeirControlBoard;
import com.example.cafyp.session.SessionManager;
import com.example.cafyp.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class user_profile extends AppCompatActivity {

    TextInputLayout profileEmail, profileNric, profileAddress, profilePassword, profileDate;
    TextView profileInvitationcode, profilerelatedadmin, profilephone, profileUsername;
    DatabaseReference reference;

    Button viewAdmin;

    String fullName, email, nric, address, invicode, state, password, adminid, date, phonenumber;
    String memberusername;

    ImageView homebtn, backbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        reference = FirebaseDatabase.getInstance().getReference("Users");
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        profileUsername = findViewById(R.id.user_profile_edit_name);
        profileEmail = findViewById(R.id.user_profile_edit_email);
        profileNric = findViewById(R.id.user_profile_edit_nric);
        profileAddress = findViewById(R.id.user_profile_edit_address);
        profilePassword = findViewById(R.id.user_profile_edit_password);
        profileDate = findViewById(R.id.user_profile_edit_date);

        profileInvitationcode = findViewById(R.id.user_profile_edit_invitationcode);
        profilerelatedadmin = findViewById(R.id.user_profile_edit_adminno);
        profilephone = findViewById(R.id.user_profile_edit_phoneno);

        viewAdmin = findViewById(R.id.view_related_admin_detail);

        SessionManager sessionManager = new SessionManager(this,SessionManager.SESSION_USERSESSION);
        HashMap<String, String> usersDetails = sessionManager.getUserDataDetailFromSession();
        fullName = usersDetails.get(SessionManager.KEY_FULLNAME);
        email = usersDetails.get(SessionManager.KEY_EMAIL);
        nric = usersDetails.get(SessionManager.KEY_NRIC);
        address = usersDetails.get(SessionManager.KEY_ADDRESS);
        invicode = usersDetails.get(SessionManager.KEY_CODE);
        password = usersDetails.get(SessionManager.KEY_PASSWORD);
        adminid = usersDetails.get(SessionManager.KEY_RESADMINID);
        date = usersDetails.get(SessionManager.KEY_BIRTH);
        phonenumber = usersDetails.get(SessionManager.KEY_PHONENUMBER);
        memberusername = usersDetails.get(SessionManager.KEY_MEMEBRUSERNAME);


        profileUsername.setText(fullName);
        profileEmail.getEditText().setText(email);
        profileNric.getEditText().setText(nric);
        profileAddress.getEditText().setText(address);
        profilePassword.getEditText().setText(password);
        profileDate.getEditText().setText(date);

        profilephone.setText(phonenumber);
        profilerelatedadmin.setText(adminid);
        profileInvitationcode.setText(invicode);

        viewAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(user_profile.this, ViewRelatedAdmin.class);
                intent.putExtra("relatedadminid", adminid);
                startActivity(intent);
                finish();
            }
        });

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserDashBoard.class);
                startActivity(intent);
                finish();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserDashBoard.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void UpdateUserProfileData(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(user_profile.this);
        builder.setTitle("Are your sure?");
        builder.setMessage("Updated data can't be Undo");

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(!validateaddress()|!validatebirth()|!validateemail()|!validatenric()|!validatepassword()){
                    return;
                }

                String _profileUsername = profileUsername.getText().toString().trim();
                String _profileEmail = profileEmail.getEditText().getText().toString().trim();
                String _profileNric = profileNric.getEditText().getText().toString().trim();
                String _profileAddress = profileAddress.getEditText().getText().toString().trim();
                String _profilePassword = profilePassword.getEditText().getText().toString().trim();
                String _profileDate = profileDate.getEditText().getText().toString().trim();

                String _profilephone = profilephone.getText().toString().trim();
                String _profilerelatedadmin = profilerelatedadmin.getText().toString().trim();
                String _profileInvitationcode = profileInvitationcode.getText().toString().trim();
                String _memberusername = _profileUsername + " / " + _profilephone;

                FirebaseDatabase uudatabase = FirebaseDatabase.getInstance();
                DatabaseReference uuref = uudatabase.getReference(invicode).child("Users");

                DatabaseReference ouref = FirebaseDatabase.getInstance().getReference("Members");

                UserUpdateProfileHelpherClass newdata = new UserUpdateProfileHelpherClass(_profileUsername, _profileNric, _profileEmail, _profilephone, _profileAddress, _profilePassword, _profileDate, _profilerelatedadmin, _profileInvitationcode, _memberusername);
                UserUpdateProfileHelpherClass newdata2 = new UserUpdateProfileHelpherClass(_profileUsername, _profileNric, _profileEmail, _profilephone, _profileAddress, _profilePassword, _profileDate, _profilerelatedadmin, _profileInvitationcode, _memberusername);

                uuref.child(phonenumber).setValue(newdata);
                ouref.child(phonenumber).setValue(newdata2);

                Toast.makeText(getApplicationContext(), "Data has been updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), UserDashBoard.class);
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

    private boolean validateemail() {
        String val = profileEmail.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            profileEmail.setError("Field can not be empty");
            return false;
        }else if (!val.matches(checkEmail)) {
            profileEmail.setError("Invalid Email");
            return false;}
        else {
            profileEmail.setError(null);
            profileEmail.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatenric() {
        String val = profileNric.getEditText().getText().toString().trim();
        String checkNRIC = ".{12,12}";

        if (val.isEmpty()) {
            profileNric.setError("Field can not be empty");
            return false;
        }else if (!val.matches(checkNRIC)) {
            profileNric.setError("Ic Number must be 12 number");
            return false;
        }
        else {
            profileNric.setError(null);
            profileNric.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateaddress() {
        String val = profileAddress.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            profileAddress.setError("Field can not be empty");
            return false;
        }
        else {
            profileAddress.setError(null);
            profileAddress.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatepassword() {
        String val = profilePassword.getEditText().getText().toString().trim();
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
            profilePassword.setError("Field can not be empty");
            return false;
        }else if (!val.matches(checkPassword)) {
            profilePassword.setError("Password should contain 8 characters, at least 1 digit, 1 lower case letter, 1 upper case letter, 1 speacial letter and no white spaces");
            return false;
        }
        else {
            profilePassword.setError(null);
            profilePassword.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatebirth() {
        String val = profileDate.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            profileDate.setError("Field can not be empty");
            return false;
        }
        else {
            profileDate.setError(null);
            profileDate.setErrorEnabled(false);
            return true;
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), UserDashBoard.class);
        startActivity(intent);
        finish();
    }

}