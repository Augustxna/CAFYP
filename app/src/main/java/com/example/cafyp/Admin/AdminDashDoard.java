package com.example.cafyp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafyp.Common.LoginSignUp.Login;
import com.example.cafyp.Common.MainActivity;
import com.example.cafyp.PaymentAdmin.PaymentDashboard;
import com.example.cafyp.R;
import com.example.cafyp.User.Heirs.HeirControlBoard;
import com.example.cafyp.message.RelatedMemberChatList;
import com.example.cafyp.session.SessionManager;
import com.example.cafyp.session.SessionManagerAdmin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class AdminDashDoard extends AppCompatActivity {

    TextView adminname;

    CardView newregistrationbtn;
    CardView relatedmember;
    CardView adminchatbtn;
    CardView adminprofilebtn;
    CardView paymentCreatebtn;
    CardView adminlogoutbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash_doard);


        adminname = findViewById(R.id.get_admin_name);
        relatedmember = findViewById(R.id.admin_member_button);
        newregistrationbtn = findViewById(R.id.admin_new_member_registration_button);
        adminchatbtn = findViewById(R.id.admin_chat_button);
        adminprofilebtn = findViewById(R.id.admin_profile_button);
        paymentCreatebtn = findViewById(R.id.admin_payment_button);
        adminlogoutbtn = findViewById(R.id.admin_logout_button);

        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(this, SessionManagerAdmin.SESSION_ADMINSESSION);
        HashMap<String, String> adminDetails = sessionManagerAdmin.getAdminDataDetailFromSession();
        String invicode = adminDetails.get(SessionManagerAdmin.KEY_ADMININVITATIONCODE);
        String adminusername = adminDetails.get(SessionManagerAdmin.KEY_ADMINUSERNAME);

        adminname.setText(adminusername);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Query checknewmember = FirebaseDatabase.getInstance().getReference(invicode).child("UserChecked").orderByChild("invitationcode").equalTo(invicode);
        checknewmember.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(AdminDashDoard.this, "My Notification");
                    builder.setContentTitle("Notice...");
                    builder.setContentTitle("Got New Member Registration....");
                    builder.setSmallIcon(R.drawable.ic_launcher_background);
                    builder.setAutoCancel(true);

                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(AdminDashDoard.this);
                    managerCompat.notify(1,builder.build());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        newregistrationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashDoard.this, NewRegistrationBoard.class);
                startActivity(intent);
            }
        });

        relatedmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashDoard.this, RelatedMemberView.class);
                startActivity(intent);
            }
        });

        adminchatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashDoard.this, RelatedMemberChatList.class);
                startActivity(intent);
            }
        });

        adminprofilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashDoard.this, AdminProfile.class);
                startActivity(intent);
            }
        });

        paymentCreatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashDoard.this, PaymentDashboard.class);
                startActivity(intent);
            }
        });

        adminlogoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminDashDoard.this);
                builder.setTitle("Are your sure to logout?");

                builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SessionManagerAdmin sessionManagerAdmin2 = new SessionManagerAdmin(AdminDashDoard.this, SessionManagerAdmin.SESSION_ADMINSESSION);
                        sessionManagerAdmin2.logoutAdminFromSession();
                        SessionManagerAdmin sessionManagerAdmin3 = new SessionManagerAdmin(AdminDashDoard.this, SessionManagerAdmin.SESSION_ADMINREMEMBERME);
                        sessionManagerAdmin3.logoutAdminFromRememberMe();
                        Intent intent = new Intent(AdminDashDoard.this, AdminLogin.class);
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


    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminDashDoard.this);
        builder.setTitle("Are your sure to logout?");

        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SessionManagerAdmin sessionManagerAdmin2 = new SessionManagerAdmin(AdminDashDoard.this, SessionManagerAdmin.SESSION_ADMINSESSION);
                sessionManagerAdmin2.logoutAdminFromSession();
                SessionManagerAdmin sessionManagerAdmin3 = new SessionManagerAdmin(AdminDashDoard.this, SessionManagerAdmin.SESSION_ADMINREMEMBERME);
                sessionManagerAdmin3.logoutAdminFromRememberMe();
                Intent intent = new Intent(AdminDashDoard.this, AdminLogin.class);
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