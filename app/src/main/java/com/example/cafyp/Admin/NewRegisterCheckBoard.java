package com.example.cafyp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafyp.AdminMain.MainAdminDashBoard;
import com.example.cafyp.Database.NewUserHelpherClass;
import com.example.cafyp.R;
import com.example.cafyp.User.Heirs.HeirControlBoard;
import com.example.cafyp.User.UserDashBoard;
import com.example.cafyp.session.SessionManagerAdmin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class NewRegisterCheckBoard extends AppCompatActivity {

    TextView name, nric, email, phoneno, birth, code, fulladdress;
    String completeaddress;
    String nuname, nuic, nuemail, nuphoneno, nubirth, nucode;
    String postcode, state, address;
    String password;
    String adminid;
    String memberusername;
    Button approvebtn, notapprovedbtn;

    String newuserphoneno;

    FirebaseDatabase database;
    DatabaseReference reference;
    ImageView homebtn, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_register_check_board);

        name = findViewById(R.id.newregisteruser_name);
        nric = findViewById(R.id.newregisteruser_icno);
        email = findViewById(R.id.newregisteruser_email);
        phoneno = findViewById(R.id.newregisteruser_phoneno);
        birth = findViewById(R.id.newregisteruser_date);
        code = findViewById(R.id.newregisteruser_code);
        fulladdress = findViewById(R.id.newregisteruser_address);

        approvebtn = findViewById(R.id.admin_approve_user_btn);
        notapprovedbtn = findViewById(R.id.admin_notapproved_user_btn);
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);


        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(this, SessionManagerAdmin.SESSION_ADMINSESSION);
        HashMap<String, String> adminDetails = sessionManagerAdmin.getAdminDataDetailFromSession();
        String admininvitationcode = adminDetails.get(SessionManagerAdmin.KEY_ADMININVITATIONCODE);
        String adminusername = adminDetails.get(SessionManagerAdmin.KEY_ADMINUSERNAME);

        String _userphoneno = "user not exist";

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _userphoneno = extras.getString("userphoneno");
        }

        phoneno.setText(_userphoneno);
        newuserphoneno = _userphoneno;

        database = FirebaseDatabase.getInstance();
        reference = database.getReference(admininvitationcode).child("UserChecked").child(newuserphoneno);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String _name = snapshot.child("fullName").getValue().toString();
                String _icno = snapshot.child("nric").getValue().toString();
                String _email = snapshot.child("email").getValue().toString();
                String _phoneno = snapshot.child("phoneNo").getValue().toString();
                String _birth = snapshot.child("date").getValue().toString();
                String _code = snapshot.child("invitationcode").getValue().toString();

                postcode = snapshot.child("postCode").getValue().toString();
                address = snapshot.child("address").getValue().toString();
                state = snapshot.child("state").getValue().toString();
                memberusername = snapshot.child("memberusername").getValue().toString();

                completeaddress = address + ", " + postcode + ", " + state;

                String newuserpassword = snapshot.child("password").getValue().toString();

                name.setText(_name);
                nric.setText(_icno);
                email.setText(_email);
                phoneno.setText(_phoneno);
                birth.setText(_birth);
                code.setText(_code);
                fulladdress.setText(completeaddress);

                nuname = _name;
                nuic = _icno;
                nuemail = _email;
                nuphoneno = _phoneno;
                nubirth = _birth;
                nucode = _code;
                password = newuserpassword;
                adminid = adminusername;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        approvebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewUserDataToFirebase();
            }

            private void saveNewUserDataToFirebase() {

                Query checkuserexist  = FirebaseDatabase.getInstance().getReference(admininvitationcode).child("Users").orderByChild("phoneno").equalTo(nuphoneno);
                checkuserexist.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Toast.makeText(NewRegisterCheckBoard.this,"UserAlreadyExist", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            FirebaseDatabase sdatabase = FirebaseDatabase.getInstance();
                            DatabaseReference sref = sdatabase.getReference().child(admininvitationcode).child("Users");

                            FirebaseDatabase ssdatabase = FirebaseDatabase.getInstance();
                            DatabaseReference ssref = ssdatabase.getReference("Members");

                            NewUserHelpherClass addNewUserByAdmin = new NewUserHelpherClass(nuname, nuic, nuemail, nuphoneno, nubirth, completeaddress, password, nucode, adminid, memberusername);
                            NewUserHelpherClass addNewUserToAllMember = new NewUserHelpherClass(nuname, nuic, nuemail, nuphoneno, nubirth, completeaddress, password, nucode, adminid, memberusername);

                            sref.child(nuphoneno).setValue(addNewUserByAdmin);
                            ssref.child(nuphoneno).setValue(addNewUserToAllMember);

                            FirebaseDatabase ddatabase = FirebaseDatabase.getInstance();
                            DatabaseReference dref = ddatabase.getReference().child(admininvitationcode).child("UserChecked").child(nuphoneno);

                            Task<Void> mTask = dref.removeValue();
                            mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Intent intent = new Intent(NewRegisterCheckBoard.this, AdminDashDoard.class);
                                    startActivity(intent);
                                    Toast.makeText(NewRegisterCheckBoard.this, "Approved Succesfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Toast.makeText(NewRegisterCheckBoard.this, "Error Approve", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

                /*Toast.makeText(NewRegisterCheckBoard.this, "Approved Succesfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), NewRegistrationBoard.class);
                startActivity(intent);*/

            }
        });

        notapprovedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewRegisterCheckBoard.this);
                builder.setTitle("Are your sure?");
                builder.setMessage("NotApproved data can't be Undo");

                builder.setPositiveButton("NotApproved", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseDatabase notdatabase = FirebaseDatabase.getInstance();
                        DatabaseReference notreference = notdatabase.getReference().child(admininvitationcode).child("UserChecked").child(nuphoneno);

                        Task<Void> nTask = notreference.removeValue();

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
                Intent intent = new Intent(getApplicationContext(), AdminDashDoard.class);
                startActivity(intent);
                finish();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewRegistrationBoard.class);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), NewRegistrationBoard.class);
        startActivity(intent);
        finish();
    }

}