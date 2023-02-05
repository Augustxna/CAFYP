package com.example.cafyp.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cafyp.Admin.RelatedMemberView;
import com.example.cafyp.R;
import com.example.cafyp.session.SessionManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ViewRelatedAdmin extends AppCompatActivity {

    TextView adminInvitationcode, adminfullname, adminusername, adminemail, adminphoneno;
    String _getinvicode, _getuserid;

    Button mainMenubtn;

    ImageView homebtn, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_related_admin);

        adminfullname = findViewById(R.id.relatedadmin_name);
        adminusername = findViewById(R.id.relatedadmin_username);
        adminemail = findViewById(R.id.relatedadmin_email);
        adminphoneno = findViewById(R.id.relatedadmin_phoneno);
        adminInvitationcode = findViewById(R.id.relatedadmin_invicode);
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        mainMenubtn = findViewById(R.id.mainmenu_btn);

        _getuserid = getIntent().getStringExtra("relatedadminid");

        SessionManager sessionManager = new SessionManager(this,SessionManager.SESSION_USERSESSION);
        HashMap<String, String> usersDetails = sessionManager.getUserDataDetailFromSession();
        _getinvicode = usersDetails.get(SessionManager.KEY_CODE);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(_getinvicode).child("Admins").child(_getuserid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String _adminfullname = snapshot.child("name").getValue().toString();
                String _adminusername = snapshot.child("username").getValue().toString();
                String _adminemail = snapshot.child("email").getValue().toString();
                String _adminphoneno = snapshot.child("phoneno").getValue().toString();
                String _adminInvitationcode = snapshot.child("invitationcode").getValue().toString();

                adminfullname.setText(_adminfullname);
                adminusername.setText(_adminusername);
                adminemail.setText(_adminemail);
                adminphoneno.setText(_adminphoneno);
                adminInvitationcode.setText(_adminInvitationcode);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        mainMenubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewRelatedAdmin.this, UserDashBoard.class);
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
                Intent intent = new Intent(getApplicationContext(), user_profile.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), user_profile.class);
        startActivity(intent);
        finish();
    }
}