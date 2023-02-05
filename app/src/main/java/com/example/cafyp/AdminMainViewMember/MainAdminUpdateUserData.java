package com.example.cafyp.AdminMainViewMember;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafyp.Admin.AdminDashDoard;
import com.example.cafyp.Admin.RelatedMemberControl;
import com.example.cafyp.Admin.UpdateUserData;
import com.example.cafyp.AdminMain.MainAdminDashBoard;
import com.example.cafyp.Database.UserUpdateProfileHelpherClass;
import com.example.cafyp.R;
import com.example.cafyp.session.SessionManagerAdmin;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class MainAdminUpdateUserData extends AppCompatActivity {

    TextInputLayout profilefullname, profileEmail, profileNric, profileAddress, profilePassword, profilebirth, profilerelatedadmin;
    TextView profileInvitationcode, profilephone;
    Button updateUserBtn;

    String _getinvicode, _getuserid;
    String memberusername;
    ImageView homebtn, backbtn;
    String checkadminusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin_update_user_data);

        //editable data
        profilefullname = findViewById(R.id.admin_edit_user_name);
        profileEmail = findViewById(R.id.admin_edit_user_email);
        profileNric = findViewById(R.id.admin_edit_user_nric);
        profileAddress = findViewById(R.id.admin_edit_user_address);
        profilePassword = findViewById(R.id.admin_edit_user_password);
        profilebirth = findViewById(R.id.admin_edit_user_date);
        profilerelatedadmin = findViewById(R.id.admin_edit_user_adminno);

        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        //uneditable data
        profileInvitationcode = findViewById(R.id.admin_edit_user_invitationcode);;
        profilephone = findViewById(R.id.admin_edit_user_phoneno);

        //button to update user's data
        updateUserBtn = findViewById(R.id.admin_edit_user_button);

        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(this, SessionManagerAdmin.SESSION_ADMINSESSION);
        HashMap<String, String> adminDetails = sessionManagerAdmin.getAdminDataDetailFromSession();
        String admininvitationcode = adminDetails.get(SessionManagerAdmin.KEY_ADMININVITATIONCODE);
        String adminstate = adminDetails.get(SessionManagerAdmin.KEY_ADMINSTATE);

        if (adminstate.equals("mainadmin")){
            profilerelatedadmin.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
        }else
        {
            profilerelatedadmin.getEditText().setInputType(InputType.TYPE_NULL);
        }

        //get the invitation code to link with the database
        _getinvicode = admininvitationcode;
        _getuserid = getIntent().getStringExtra("relatedmemberid");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(_getinvicode).child("Users").child(_getuserid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String _userfullname = snapshot.child("fullName").getValue().toString();
                String _usernric = snapshot.child("nric").getValue().toString();
                String _userbirth = snapshot.child("birth").getValue().toString();
                String _useremail = snapshot.child("email").getValue().toString();
                String _useraddress = snapshot.child("address").getValue().toString();
                String _userpassword = snapshot.child("password").getValue().toString();
                String _userphoneno = snapshot.child("phoneno").getValue().toString();
                String _userinvicode = snapshot.child("invitationcode").getValue().toString();
                String _userresadmin = snapshot.child("resadminid").getValue().toString();
                memberusername = snapshot.child("memberusername").getValue().toString();

                profilefullname.getEditText().setText(_userfullname);
                profileEmail.getEditText().setText(_useremail);
                profileNric.getEditText().setText(_usernric);
                profileAddress.getEditText().setText(_useraddress);
                profilePassword.getEditText().setText(_userpassword);
                profilebirth.getEditText().setText(_userbirth);
                profilerelatedadmin.getEditText().setText(_userresadmin);

                //uneditable data
                profileInvitationcode.setText(_userinvicode);
                profilephone.setText(_userphoneno);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        updateUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainAdminUpdateUserData.this);
                builder.setTitle("Are your sure to update this member?");
                builder.setMessage("(The record of chat will be deleted if changing the member name)");

                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(!validateemail()|!validatename()|!validatenric()|!validatepassword()|!validatebirth()|!validateaddress()|!validaterelatedadmin()){
                            return;
                        }

                        String val = profilerelatedadmin.getEditText().getText().toString().trim();
                        Query checkuserexist = FirebaseDatabase.getInstance().getReference(_getinvicode).child("Admins").orderByChild("username").equalTo(val);
                        checkuserexist.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(!snapshot.exists())
                                {
                                    profilerelatedadmin.setError("Admin does not exist");
                                }
                                else{
                                    String getfullname = profilefullname.getEditText().getText().toString().trim();
                                    String getuseremail = profileEmail.getEditText().getText().toString().trim();
                                    String getuseric = profileNric.getEditText().getText().toString().trim();
                                    String getuseraddress = profileAddress.getEditText().getText().toString().trim();
                                    String getuserpassword = profilePassword.getEditText().getText().toString().trim();
                                    String getuserbirth = profilebirth.getEditText().getText().toString().trim();
                                    String getuserrelatedadmin = profilerelatedadmin.getEditText().getText().toString().trim();

                                    String getuserinvicode = profileInvitationcode.getText().toString().trim();
                                    String getuserphoneno = profilephone.getText().toString().trim();

                                    String getmemberusername = getfullname + " / " + getuserphoneno;

                                    UserUpdateProfileHelpherClass newdata = new UserUpdateProfileHelpherClass(getfullname,getuseric,getuseremail,getuserphoneno,getuseraddress,getuserpassword,getuserbirth,getuserrelatedadmin,getuserinvicode,getmemberusername);
                                    UserUpdateProfileHelpherClass newdata2nd = new UserUpdateProfileHelpherClass(getfullname,getuseric,getuseremail,getuserphoneno,getuseraddress,getuserpassword,getuserbirth,getuserrelatedadmin,getuserinvicode,getmemberusername);

                                    FirebaseDatabase updatedatabse = FirebaseDatabase.getInstance();
                                    DatabaseReference updatedref = updatedatabse.getReference(_getinvicode).child("Users");
                                    FirebaseDatabase updatedatabse2nd = FirebaseDatabase.getInstance();
                                    DatabaseReference updatedref2nd = updatedatabse2nd.getReference("Members");

                                    updatedref.child(_getuserid).setValue(newdata);
                                    updatedref2nd.child(_getuserid).setValue(newdata2nd);


                                    Intent intent = new Intent(MainAdminUpdateUserData.this, MainAdminAllMemberControl.class);
                                    intent.putExtra("memberid", getuserphoneno);
                                    startActivity(intent);
                                    Toast.makeText(MainAdminUpdateUserData.this, "Data has been updated", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

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
                Intent intent = new Intent(getApplicationContext(), MainAdminAllMemberControl.class);
                intent.putExtra("memberid", _getuserid);
                startActivity(intent);
                finish();
            }
        });
    }


    private boolean validatename() {
        String val = profilefullname.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            profilefullname.setError("Field can not be empty");
            return false;
        }
        else {
            profilefullname.setError(null);
            profilefullname.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateemail() {
        String val = profileEmail.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            profileEmail.setError("Field can not be empty");
            return false;
        }else if (!val.matches(checkEmail)) {
            profileEmail.setError("Invalid Email");
            return false;
        }
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
                //(?=.*[a-zA-Z])" +          //any letter
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
        String val = profilebirth.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            profilebirth.setError("Field can not be empty");
            return false;
        }
        else {
            profilebirth.setError(null);
            profilebirth.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validaterelatedadmin() {

        String val = profilerelatedadmin.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            profilerelatedadmin.setError("Field can not be empty");
            return false;
        }
        else {
            profilerelatedadmin.setError(null);
            profilerelatedadmin.setErrorEnabled(false);
            return true;
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MainAdminAllMemberControl.class);
        intent.putExtra("memberid", _getuserid);
        startActivity(intent);
        finish();
    }
}