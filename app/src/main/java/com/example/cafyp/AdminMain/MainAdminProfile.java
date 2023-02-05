package com.example.cafyp.AdminMain;

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

import com.example.cafyp.Admin.AdminDashDoard;
import com.example.cafyp.Admin.RelatedMemberView;
import com.example.cafyp.Database.AdminUpdateHelpherClass;
import com.example.cafyp.Database.UserUpdateProfileHelpherClass;
import com.example.cafyp.R;
import com.example.cafyp.User.UserDashBoard;
import com.example.cafyp.User.user_profile;
import com.example.cafyp.session.SessionManagerAdmin;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainAdminProfile extends AppCompatActivity {

    TextView username, invicode, adminstate;
    TextInputLayout realname, icno, email, phoneno, password;
    String _mainadmindate;
    String _invicode;

    Button updatebtn;
    ImageView homebtn, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin_profile);

        username = findViewById(R.id.mainadmin_profile_edit_username);
        invicode = findViewById(R.id.mainadmin_profile_edit_invitationcode);
        adminstate = findViewById(R.id.mainadmin_profile_edit_state);

        realname = findViewById(R.id.mainadmin_profile_edit_name);
        icno = findViewById(R.id.mainadmin_profile_edit_nric);
        email = findViewById(R.id.mainadmin_profile_edit_email);
        phoneno = findViewById(R.id.mainadmin_profile_edit_phoneno);
        password = findViewById(R.id.mainadmin_profile_edit_password);

        updatebtn = findViewById(R.id.mainadmin_profile_update);
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(this,SessionManagerAdmin.SESSION_ADMINSESSION);
        HashMap<String, String> adminDetails = sessionManagerAdmin.getAdminDataDetailFromSession();

        String _mainadminusername = adminDetails.get(SessionManagerAdmin.KEY_ADMINUSERNAME);
        String _mainadmininvitationcode = adminDetails.get(SessionManagerAdmin.KEY_ADMININVITATIONCODE);
        String _mainadminstate = adminDetails.get(SessionManagerAdmin.KEY_ADMINSTATE);
        _mainadmindate = adminDetails.get(SessionManagerAdmin.KEY_ADMINCREATEDATE);
        String _mainadminname = adminDetails.get(SessionManagerAdmin.KEY_ADMINNAME);
        String _mainadminicno = adminDetails.get(SessionManagerAdmin.KEY_ADMINIC);
        String _mainadminemail = adminDetails.get(SessionManagerAdmin.KEY_ADMINEMAIL);
        String _mainadminphoneno = adminDetails.get(SessionManagerAdmin.KEY_ADMINPHONENO);
        String _mainadminpassword = adminDetails.get(SessionManagerAdmin.KEY_ADMINPASSWORD);

        username.setText(_mainadminusername);
        invicode.setText(_mainadmininvitationcode);
        adminstate.setText(_mainadminstate);

        realname.getEditText().setText(_mainadminname);
        icno.getEditText().setText(_mainadminicno);
        email.getEditText().setText(_mainadminemail);
        phoneno.getEditText().setText(_mainadminphoneno);
        password.getEditText().setText(_mainadminpassword);

        _invicode = _mainadmininvitationcode;

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMainAdminProfileData();
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
                Intent intent = new Intent(getApplicationContext(), MainAdminDashBoard.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void updateMainAdminProfileData() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainAdminProfile.this);
        builder.setTitle("Are your sure?");
        builder.setMessage("Updated data can't be Undo");

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(!validatepassword()|!validatenric()|!validatename()|!validateemail()|!validatephoneNo()){
                    return;
                }
                String getmainadminusername = username.getText().toString().trim();
                String getmaininvitationcode = invicode.getText().toString().trim();
                String getmainadminstate = adminstate.getText().toString().trim();
                String getmainadmindate = _mainadmindate;
                String getmainadminrealname = realname.getEditText().getText().toString().trim();
                String getmainadminicno = icno.getEditText().getText().toString().trim();
                String getmainadminemail = email.getEditText().getText().toString().trim();
                String getmainadminphone = phoneno.getEditText().getText().toString().trim();
                String getmainadminpassword = password.getEditText().getText().toString().trim();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference(_invicode).child("Mainadmin");
                DatabaseReference reference2nd = FirebaseDatabase.getInstance().getReference("Mainadmin");

                AdminUpdateHelpherClass newadmindata = new AdminUpdateHelpherClass(getmainadminrealname, getmainadminusername, getmainadminicno, getmainadminemail, getmainadminphone, getmainadminpassword, getmainadmindate, getmainadminstate, getmaininvitationcode);
                AdminUpdateHelpherClass newadmindata2nd = new AdminUpdateHelpherClass(getmainadminrealname, getmainadminusername, getmainadminicno, getmainadminemail, getmainadminphone, getmainadminpassword, getmainadmindate, getmainadminstate, getmaininvitationcode);

                reference.child(getmainadminusername).setValue(newadmindata);
                reference2nd.child(getmainadminusername).setValue(newadmindata2nd);
                Toast.makeText(getApplicationContext(), "Data has been updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainAdminDashBoard.class);
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
        Intent intent = new Intent(getApplicationContext(), MainAdminDashBoard.class);
        startActivity(intent);
        finish();
    }

}