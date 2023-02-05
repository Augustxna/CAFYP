package com.example.cafyp.Admin;

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

import com.example.cafyp.Database.AdminUpdateHelpherClass;
import com.example.cafyp.Database.UserUpdateProfileHelpherClass;
import com.example.cafyp.R;
import com.example.cafyp.User.UserDashBoard;
import com.example.cafyp.User.user_profile;
import com.example.cafyp.session.SessionManager;
import com.example.cafyp.session.SessionManagerAdmin;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AdminProfile extends AppCompatActivity {

    TextView username, invicode, adminstate;
    TextInputLayout realname, icno, email, phoneno, password;
    String _admindate;
    String _invicode;

    Button updatebtn;

    ImageView homebtn, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        username = findViewById(R.id.admin_profile_edit_username);
        invicode = findViewById(R.id.admin_profile_edit_invitationcode);
        adminstate = findViewById(R.id.admin_profile_edit_state);

        realname = findViewById(R.id.admin_profile_edit_name);
        icno = findViewById(R.id.admin_profile_edit_nric);
        email = findViewById(R.id.admin_profile_edit_email);
        phoneno = findViewById(R.id.admin_profile_edit_phoneno);
        password = findViewById(R.id.admin_profile_edit_password);

        updatebtn = findViewById(R.id.admin_profile_update);
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(this, SessionManagerAdmin.SESSION_ADMINSESSION);
        HashMap<String, String> adminDetails = sessionManagerAdmin.getAdminDataDetailFromSession();

        String _adminusername = adminDetails.get(SessionManagerAdmin.KEY_ADMINUSERNAME);
        String _admininvitationcode = adminDetails.get(SessionManagerAdmin.KEY_ADMININVITATIONCODE);
        String _adminstate = adminDetails.get(SessionManagerAdmin.KEY_ADMINSTATE);
        _admindate = adminDetails.get(SessionManagerAdmin.KEY_ADMINCREATEDATE);
        String _adminname = adminDetails.get(SessionManagerAdmin.KEY_ADMINNAME);
        String _adminicno = adminDetails.get(SessionManagerAdmin.KEY_ADMINIC);
        String _adminemail = adminDetails.get(SessionManagerAdmin.KEY_ADMINEMAIL);
        String _adminphoneno = adminDetails.get(SessionManagerAdmin.KEY_ADMINPHONENO);
        String _adminpassword = adminDetails.get(SessionManagerAdmin.KEY_ADMINPASSWORD);

        username.setText(_adminusername);
        invicode.setText(_admininvitationcode);
        adminstate.setText(_adminstate);

        realname.getEditText().setText(_adminname);
        icno.getEditText().setText(_adminicno);
        email.getEditText().setText(_adminemail);
        phoneno.getEditText().setText(_adminphoneno);
        password.getEditText().setText(_adminpassword);

        _invicode = _admininvitationcode;

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAdminProfileData();
            }
        });

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminDashDoard.class);
                startActivity(intent);
                finish();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminDashDoard.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void updateAdminProfileData() {

        AlertDialog.Builder builder = new AlertDialog.Builder(AdminProfile.this);
        builder.setTitle("Are your sure?");
        builder.setMessage("Updated data can't be Undo");

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(!validatepassword()|!validatenric()|!validatename()|!validateemail()|!validatephoneNo()){
                    return;
                }
                String getadminusername = username.getText().toString().trim();
                String getinvitationcode = invicode.getText().toString().trim();
                String getadminstate = adminstate.getText().toString().trim();
                String getadmindate = _admindate;
                String getadminrealname = realname.getEditText().getText().toString().trim();
                String getadminicno = icno.getEditText().getText().toString().trim();
                String getadminemail = email.getEditText().getText().toString().trim();
                String getadminphone = phoneno.getEditText().getText().toString().trim();
                String getadminpassword = password.getEditText().getText().toString().trim();


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference(_invicode).child("Admins");
                DatabaseReference reference2nd = FirebaseDatabase.getInstance().getReference("Admins");


                AdminUpdateHelpherClass newadmindata = new AdminUpdateHelpherClass(getadminrealname, getadminusername, getadminicno, getadminemail, getadminphone, getadminpassword, getadmindate, getadminstate, getinvitationcode);
                AdminUpdateHelpherClass newadmindata2nd = new AdminUpdateHelpherClass(getadminrealname, getadminusername, getadminicno, getadminemail, getadminphone, getadminpassword, getadmindate, getadminstate, getinvitationcode);

                reference.child(getadminusername).setValue(newadmindata);
                reference2nd.child(getadminusername).setValue(newadmindata2nd);
                Toast.makeText(getApplicationContext(), "Data has been updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), AdminDashDoard.class);
                startActivity(intent);
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
    private boolean validatename() {
        String val = realname.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            realname.setError("Field can not be empty");
            return false;
        }
        else {
            realname.setError(null);
            realname.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateemail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        }else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email");
            return false;
        }
        else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatenric() {
        String val = icno.getEditText().getText().toString().trim();
        String checkNRIC = ".{12,12}";

        if (val.isEmpty()) {
            icno.setError("Field can not be empty");
            return false;
        }else if (!val.matches(checkNRIC)) {
            icno.setError("Ic Number must be 12 number");
            return false;
        }
        else {
            icno.setError(null);
            icno.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatephoneNo() {
        String val = phoneno.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            phoneno.setError("Field can not be empty");
            return false;
        }
        else {
            phoneno.setError(null);
            phoneno.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatepassword() {
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
        }else if (!val.matches(checkPassword)) {
            password.setError("Password should contain 8 characters, at least 1 digit, 1 lower case letter, 1 upper case letter, 1 speacial letter and no white spaces");
            return false;
        }
        else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), AdminDashDoard.class);
        startActivity(intent);
        finish();
    }

}