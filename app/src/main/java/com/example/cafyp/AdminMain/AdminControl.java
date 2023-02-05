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

import com.example.cafyp.PaymentAdmin.PaymentDashboard;
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
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AdminControl extends AppCompatActivity {

    TextView adminname, adminic, adminemail, adminphone, adminusername, adminstate, admincreatedate, adminpassword, admininvitationcode;
    FirebaseDatabase mdatabase;
    DatabaseReference mref;
    Button updatebtn;
    String _username;

    String admininvitationcodefromsession;
    ImageView homebtn, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_control);

        adminusername = findViewById(R.id.admin_username);
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        adminname = findViewById(R.id.admin_name);
        adminic = findViewById(R.id.admin_ic);
        adminemail = findViewById(R.id.admin_email);
        adminpassword = findViewById(R.id.admin_password);
        adminphone = findViewById(R.id.admin_phoneno);
        adminstate = findViewById(R.id.admin_state);
        admincreatedate = findViewById(R.id.admin_createdate);
        admininvitationcode = findViewById(R.id.admin_invitationcode);

        updatebtn = findViewById(R.id.admincontrol_update_button);

        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(this, SessionManagerAdmin.SESSION_ADMINSESSION);
        HashMap<String, String> adminDetails = sessionManagerAdmin.getAdminDataDetailFromSession();
        admininvitationcodefromsession = adminDetails.get(SessionManagerAdmin.KEY_ADMININVITATIONCODE);
        String adminusernamefromsession = adminDetails.get(SessionManagerAdmin.KEY_ADMINUSERNAME);

        String getusername = getIntent().getStringExtra("adminid");

        _username = getusername;


        mdatabase = FirebaseDatabase.getInstance();
        mref = mdatabase.getReference(admininvitationcodefromsession).child("Admins").child(_username);

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                String _username = datasnapshot.child("username").getValue().toString();
                String _name = datasnapshot.child("name").getValue().toString();
                String _email = datasnapshot.child("email").getValue().toString();
                String _phoneno = datasnapshot.child("phoneno").getValue().toString();
                String _password = datasnapshot.child("password").getValue().toString();
                String _state = datasnapshot.child("state").getValue().toString();
                String _createdate = datasnapshot.child("date").getValue().toString();
                String _ic = datasnapshot.child("ic").getValue().toString();
                String _invitationcode = datasnapshot.child("invitationcode").getValue().toString();

                adminname.setText(_name);
                adminusername.setText(_username);
                adminic.setText(_ic);
                adminemail.setText(_email);
                adminpassword.setText(_password);
                adminphone.setText(_phoneno);
                adminstate.setText(_state);
                admincreatedate.setText(_createdate);
                admininvitationcode.setText(_invitationcode);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminControl.this, AdminControlUpdate.class);
                intent.putExtra("adminid", _username);
                startActivity(intent);
                finish();
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
                Intent intent = new Intent(getApplicationContext(), ViewAdminBoard.class);
                startActivity(intent);
                finish();
            }
        });

    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), ViewAdminBoard.class);
        startActivity(intent);
        finish();
    }


}