package com.example.cafyp.User;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafyp.AdminMain.MainAdminDashBoard;
import com.example.cafyp.Common.LoginSignUp.Login;
import com.example.cafyp.Common.MainActivity;
import com.example.cafyp.PaymentAdmin.PaymentDashboard;
import com.example.cafyp.session.SessionManager;
import com.example.cafyp.PaymentMember.MemberViewPayment;
import com.example.cafyp.R;
import com.example.cafyp.User.Heirs.user_heirs;
import com.example.cafyp.message.MessageActivity;

import java.util.HashMap;

public class UserDashBoard extends AppCompatActivity {

    TextView userName, userPhoneNumber;
    CardView cardProfile;
    CardView cardHeirs;
    CardView cardChat;
    CardView cardPayment;
    CardView cardlogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_board);

        userName = findViewById(R.id.get_user_name);
        userPhoneNumber = findViewById(R.id.get_user_phoneno);
        cardProfile = findViewById(R.id.user_profile_button);
        cardHeirs = findViewById(R.id.user_heirs_button);
        cardChat = findViewById(R.id.user_chat_button);
        cardPayment = findViewById(R.id.user_payment_button);
        cardlogout = findViewById(R.id.user_logout_button);


        SessionManager sessionManager = new SessionManager(getApplicationContext(),SessionManager.SESSION_USERSESSION);
        HashMap<String, String> usersDetails = sessionManager.getUserDataDetailFromSession();
        String fullName = usersDetails.get(SessionManager.KEY_FULLNAME);
        String phonenumber = usersDetails.get(SessionManager.KEY_PHONENUMBER);

        /*Intent intent = getIntent();
        String _name = intent.getStringExtra("username");
        String _phone = intent.getStringExtra("phoneNo");*/

        userName.setText(fullName);
        userPhoneNumber.setText(phonenumber);

        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), user_profile.class));
            }
        });

        cardHeirs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), user_heirs.class);
                startActivity(intent);
            }
        });

        cardChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
                startActivity(intent);
            }
        });

        cardPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MemberViewPayment.class);
                startActivity(intent);
            }
        });

        cardlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UserDashBoard.this);
                builder.setTitle("Are your sure to logout?");

                builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SessionManager sessionManager2 = new SessionManager(UserDashBoard.this, SessionManager.SESSION_USERSESSION);
                        sessionManager2.logoutUserFromSession();
                        SessionManager sessionManager3 = new SessionManager(UserDashBoard.this, SessionManager.SESSION_REMEMBERME);
                        sessionManager3.logoutUserFromRememberMe();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(UserDashBoard.this);
        builder.setTitle("Are your sure to logout?");

        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SessionManager sessionManager2 = new SessionManager(UserDashBoard.this, SessionManager.SESSION_USERSESSION);
                sessionManager2.logoutUserFromSession();
                SessionManager sessionManager3 = new SessionManager(UserDashBoard.this, SessionManager.SESSION_REMEMBERME);
                sessionManager3.logoutUserFromRememberMe();
                Intent intent = new Intent(getApplicationContext(), Login.class);
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