package com.example.cafyp.AdminMain;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cafyp.Admin.AdminDashDoard;
import com.example.cafyp.Admin.AdminLogin;
import com.example.cafyp.Common.LoginSignUp.Login;
import com.example.cafyp.Common.MainActivity;
import com.example.cafyp.PaymentAdmin.PaymentDashboard;
import com.example.cafyp.PaymentMainAdmin.MainAdminPaymentDashboard;
import com.example.cafyp.R;
import com.example.cafyp.session.SessionManagerAdmin;

import java.util.HashMap;

public class MainAdminDashBoard extends AppCompatActivity {

    CardView cardAdmin;
    CardView cardpayment;
    CardView cardProfile;
    CardView cardMember;
    CardView cardlogout;
    CardView cardreport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin_dash_board);

        cardAdmin = findViewById(R.id.mainadmin_admin_button);
        cardpayment = findViewById(R.id.mainadmin_createpayment_button);
        cardProfile = findViewById(R.id.mainadmin_profile_button);
        cardMember = findViewById(R.id.mainadmin_viewmember_button);
        cardlogout = findViewById(R.id.mainadmin_logout_button);
        cardreport = findViewById(R.id.mainadmin_finalreport_button);

        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(this, SessionManagerAdmin.SESSION_ADMINSESSION);
        HashMap<String, String> adminDetails = sessionManagerAdmin.getAdminDataDetailFromSession();
        String admininvitationcode = adminDetails.get(SessionManagerAdmin.KEY_ADMININVITATIONCODE);
        String adminusername = adminDetails.get(SessionManagerAdmin.KEY_ADMINUSERNAME);

        cardAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAdminDashBoard.this, CreateAndViewAdmin.class);
                startActivity(intent);
            }
        });

        cardpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAdminDashBoard.this, MainAdminPaymentDashboard.class);
                startActivity(intent);
            }
        });

        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAdminDashBoard.this, MainAdminProfile.class);
                startActivity(intent);
            }
        });

        cardMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAdminDashBoard.this, ViewAllMembers.class);
                startActivity(intent);
            }
        });

        cardlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainAdminDashBoard.this);
                builder.setTitle("Are your sure to logout?");

                builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SessionManagerAdmin sessionManagerAdmin2 = new SessionManagerAdmin(MainAdminDashBoard.this, SessionManagerAdmin.SESSION_ADMINSESSION);
                        sessionManagerAdmin2.logoutAdminFromSession();
                        SessionManagerAdmin sessionManagerAdmin3 = new SessionManagerAdmin(MainAdminDashBoard.this, SessionManagerAdmin.SESSION_ADMINREMEMBERME);
                        sessionManagerAdmin3.logoutAdminFromRememberMe();
                        Intent intent = new Intent(MainAdminDashBoard.this, AdminLogin.class);
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

        cardreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAdminDashBoard.this, Allpaymentlist.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainAdminDashBoard.this);
        builder.setTitle("Are your sure to logout?");

        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SessionManagerAdmin sessionManagerAdmin2 = new SessionManagerAdmin(MainAdminDashBoard.this, SessionManagerAdmin.SESSION_ADMINSESSION);
                sessionManagerAdmin2.logoutAdminFromSession();
                SessionManagerAdmin sessionManagerAdmin3 = new SessionManagerAdmin(MainAdminDashBoard.this, SessionManagerAdmin.SESSION_ADMINREMEMBERME);
                sessionManagerAdmin3.logoutAdminFromRememberMe();
                Intent intent = new Intent(MainAdminDashBoard.this, AdminLogin.class);
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
}