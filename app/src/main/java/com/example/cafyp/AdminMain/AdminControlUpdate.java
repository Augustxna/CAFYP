package com.example.cafyp.AdminMain;

import androidx.annotation.NonNull;
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
import com.example.cafyp.Admin.UpdateUserData;
import com.example.cafyp.Database.AdminUpdateHelpherClass;
import com.example.cafyp.Database.UserUpdateProfileHelpherClass;
import com.example.cafyp.R;
import com.example.cafyp.User.UserDashBoard;
import com.example.cafyp.User.user_profile;
import com.example.cafyp.session.SessionManagerAdmin;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class AdminControlUpdate extends AppCompatActivity {

    TextInputLayout adminfullname, adminEmail, adminNric, adminPhoneno, adminPassword;
    String adminState;
    TextView adminInvitationcode, adminCreatedDate, adminusername;
    Button updateAdminBtn;

    String _getinvicode, _getuserid;
    ImageView homebtn, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_control_update);

        adminfullname = findViewById(R.id.main_edit_admin_name);
        adminEmail = findViewById(R.id.main_edit_admin_email);
        adminNric = findViewById(R.id.main_edit_admin_nric);
        adminPhoneno = findViewById(R.id.main_edit_admin_phoneno);
        adminPassword = findViewById(R.id.main_edit_admin_password);

        adminInvitationcode = findViewById(R.id.main_edit_admin_invitationcode);
        adminCreatedDate = findViewById(R.id.main_edit_admin_createddate);
        adminusername = findViewById(R.id.main_edit_admin_username);

        updateAdminBtn = findViewById(R.id.main_edit_admin_button);
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(this, SessionManagerAdmin.SESSION_ADMINSESSION);
        HashMap<String, String> adminDetails = sessionManagerAdmin.getAdminDataDetailFromSession();
        String admininvitationcode = adminDetails.get(SessionManagerAdmin.KEY_ADMININVITATIONCODE);
        String adminstate = adminDetails.get(SessionManagerAdmin.KEY_ADMINSTATE);

        _getinvicode = admininvitationcode;
        _getuserid = getIntent().getStringExtra("adminid");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(_getinvicode).child("Admins").child(_getuserid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                String _adminfullname = snapshot.child("name").getValue().toString();
                String _adminic = snapshot.child("ic").getValue().toString();
                String _adminemail = snapshot.child("email").getValue().toString();
                String _adminphoneno = snapshot.child("phoneno").getValue().toString();
                String _adminpassword = snapshot.child("password").getValue().toString();
                String _adminstate = snapshot.child("state").getValue().toString();

                String _adminusername = snapshot.child("username").getValue().toString();
                String _admindate = snapshot.child("date").getValue().toString();
                String _admininvitationcode = snapshot.child("invitationcode").getValue().toString();


                adminfullname.getEditText().setText(_adminfullname);
                adminEmail.getEditText().setText(_adminemail);
                adminNric.getEditText().setText(_adminic);
                adminPhoneno.getEditText().setText(_adminphoneno);
                adminPassword.getEditText().setText(_adminpassword);
                adminState = _adminstate;

                //uneditable data
                adminInvitationcode.setText(_admininvitationcode);
                adminCreatedDate.setText(_admindate);
                adminusername.setText(_adminusername);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        updateAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AdminControlUpdate.this);
                builder.setTitle("Are your sure?");
                builder.setMessage("Updated data can't be Undo");

                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(!validatepassword()|!validatenric()|!validatename()|!validateemail()|!validatephoneNo()){
                            return;
                        }

                        String getadminname = adminfullname.getEditText().getText().toString().trim();
                        String getadminemail = adminEmail.getEditText().getText().toString().trim();
                        String getadminic = adminNric.getEditText().getText().toString().trim();
                        String getadminphoneno = adminPhoneno.getEditText().getText().toString().trim();
                        String getadminpassword = adminPassword.getEditText().getText().toString().trim();
                        String getadminstate = adminState;

                        String getadmininvicode = adminInvitationcode.getText().toString().trim();
                        String getadmindate = adminCreatedDate.getText().toString().trim();
                        String getadminusername = adminusername.getText().toString().trim();

                        AdminUpdateHelpherClass newdata = new AdminUpdateHelpherClass(getadminname, getadminusername, getadminic, getadminemail, getadminphoneno, getadminpassword, getadmindate, getadminstate, getadmininvicode);
                        AdminUpdateHelpherClass newdata2nd = new AdminUpdateHelpherClass(getadminname, getadminusername, getadminic, getadminemail, getadminphoneno, getadminpassword, getadmindate, getadminstate, getadmininvicode);

                        FirebaseDatabase updatedatabse = FirebaseDatabase.getInstance();
                        DatabaseReference updatedref = updatedatabse.getReference(_getinvicode).child("Admins");
                        FirebaseDatabase updatedatabse2nd = FirebaseDatabase.getInstance();
                        DatabaseReference updatedref2nd = updatedatabse2nd.getReference("Admins");

                        updatedref.child(_getuserid).setValue(newdata);
                        updatedref2nd.child(_getuserid).setValue(newdata2nd);

                        Toast.makeText(AdminControlUpdate.this, "Admin Data has been updated", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(getApplicationContext(), AdminControl.class);
                intent.putExtra("adminid", _getuserid);
                startActivity(intent);
                finish();
            }
        });

    }

    private boolean validatename() {
        String val = adminfullname.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            adminfullname.setError("Field can not be empty");
            return false;
        }
        else {
            adminfullname.setError(null);
            adminfullname.setErrorEnabled(false);
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
        String val = adminNric.getEditText().getText().toString().trim();
        String checkNRIC = ".{12,12}";

        if (val.isEmpty()) {
            adminNric.setError("Field can not be empty");
            return false;
        }else if (!val.matches(checkNRIC)) {
            adminNric.setError("Ic Number must be 12 number");
            return false;
        }
        else {
            adminNric.setError(null);
            adminNric.setErrorEnabled(false);
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

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), AdminControl.class);
        intent.putExtra("adminid", _getuserid);
        startActivity(intent);
        finish();
    }

}