package com.example.cafyp.PaymentAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.cafyp.Admin.AdminDashDoard;
import com.example.cafyp.R;
import com.example.cafyp.User.UserDashBoard;

public class PaymentDashboard extends AppCompatActivity {

    Button createPayment, viewPayment;
    ImageView homebtn, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_dashboard);

        createPayment = findViewById(R.id.create_payment_button);
        viewPayment = findViewById(R.id.view_payment_button);
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);


        createPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreatePayment.class);
                startActivity(intent);
            }
        });


        viewPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewPayment.class);
                startActivity(intent);
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
                Intent intent = new Intent(getApplicationContext(), AdminDashDoard.class);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), AdminDashDoard.class);
        startActivity(intent);
        finish();
    }
}