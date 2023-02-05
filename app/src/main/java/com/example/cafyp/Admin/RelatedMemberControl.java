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

import com.example.cafyp.PaymentAdmin.PaymentControl;
import com.example.cafyp.PaymentAdmin.PaymentDashboard;
import com.example.cafyp.PaymentAdmin.ViewPayment;
import com.example.cafyp.R;
import com.example.cafyp.User.Heirs.HeirControlBoard;
import com.example.cafyp.User.UserDashBoard;
import com.example.cafyp.session.SessionManagerAdmin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class RelatedMemberControl extends AppCompatActivity {

    TextView membername, memberpassword, memberic, memberphoneno, memberemail, memberbirth, memberaddress, membercode, memberrelatedadmin;
    String invicode, getmemberid;

    Button viewheirbtn, viewpaymentbtn, updatememberbtn;
    ImageView homebtn, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_related_member_control);

        membername = findViewById(R.id.relatedmembercontrol_name);
        memberpassword = findViewById(R.id.relatedmembercontrol_password);
        memberic = findViewById(R.id.relatedmembercontrol_icno);
        memberphoneno = findViewById(R.id.relatedmembercontrol_phoneno);
        memberemail = findViewById(R.id.relatedmembercontrol_email);
        memberbirth = findViewById(R.id.relatedmembercontrol_date);
        memberaddress = findViewById(R.id.relatedmembercontrol_address);
        membercode = findViewById(R.id.relatedmembercontrol_code);
        memberrelatedadmin = findViewById(R.id.relatedmembercontrol_resadminid);

        viewheirbtn = findViewById(R.id.admin_viewrelatedheirs_btn);
        viewpaymentbtn = findViewById(R.id.admin_viewrelatedpayment_btn);
        updatememberbtn = findViewById(R.id.admin_updaterelatedmember_btn);
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(this, SessionManagerAdmin.SESSION_ADMINSESSION);
        HashMap<String, String> adminDetails = sessionManagerAdmin.getAdminDataDetailFromSession();
        String admininvitationcode = adminDetails.get(SessionManagerAdmin.KEY_ADMININVITATIONCODE);
        String adminusername = adminDetails.get(SessionManagerAdmin.KEY_ADMINUSERNAME);

        invicode = admininvitationcode;

        String _getmemberid = getIntent().getStringExtra("memberid");

        getmemberid = _getmemberid;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(invicode).child("Users").child(getmemberid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                String _membername = snapshot.child("fullName").getValue().toString();
                String _memberpassword = snapshot.child("password").getValue().toString();
                String _memberic = snapshot.child("nric").getValue().toString();
                String _memberemail = snapshot.child("email").getValue().toString();
                String _memberphoneno = snapshot.child("phoneno").getValue().toString();
                String _memberbirth = snapshot.child("birth").getValue().toString();
                String _memberaddress = snapshot.child("address").getValue().toString();
                String _membercode = snapshot.child("invitationcode").getValue().toString();
                String _memberresadminid = snapshot.child("resadminid").getValue().toString();

                membername.setText(_membername);
                memberpassword.setText(_memberpassword);
                memberic.setText(_memberic);
                memberemail.setText(_memberemail);
                memberphoneno.setText(_memberphoneno);
                memberbirth.setText(_memberbirth);
                memberaddress.setText(_memberaddress);
                membercode.setText(_membercode);
                memberrelatedadmin.setText(_memberresadminid);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        viewheirbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RelatedMemberControl.this, ViewRelatedHeirs.class);
                intent.putExtra("relatedmemberid", getmemberid);
                startActivity(intent);
                finish();
            }
        });

        viewpaymentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RelatedMemberControl.this, ViewRelatedPayment.class);
                intent.putExtra("relatedmemberid", getmemberid);
                startActivity(intent);
                finish();
            }
        });

        updatememberbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RelatedMemberControl.this, UpdateUserData.class);
                intent.putExtra("relatedmemberid", getmemberid);
                startActivity(intent);
                finish();
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
                Intent intent = new Intent(getApplicationContext(), RelatedMemberView.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), RelatedMemberView.class);
        startActivity(intent);
        finish();
    }

}