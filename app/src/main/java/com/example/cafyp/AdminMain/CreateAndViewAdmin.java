package com.example.cafyp.AdminMain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.cafyp.PaymentAdmin.PaymentDashboard;
import com.example.cafyp.R;
import com.example.cafyp.User.UserDashBoard;

public class CreateAndViewAdmin extends AppCompatActivity {

    Button createAdmin, viewAdmin;
    ImageView homebtn, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_and_view_admin);

        createAdmin = findViewById(R.id.create_admin_button);
        viewAdmin = findViewById(R.id.view_admin_button);
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        createAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateAdminForm.class);
                startActivity(intent);
                finish();
            }
        });

        viewAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewAdminBoard.class);
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
                Intent intent = new Intent(getApplicationContext(), MainAdminDashBoard.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MainAdminDashBoard.class);
        startActivity(intent);
        finish();
    }


}