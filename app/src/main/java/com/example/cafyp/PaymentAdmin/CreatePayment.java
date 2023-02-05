package com.example.cafyp.PaymentAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cafyp.Admin.AdminDashDoard;
import com.example.cafyp.Admin.RelatedMemberView;
import com.example.cafyp.Database.PaymentHelpherClass;
import com.example.cafyp.R;
import com.example.cafyp.User.UserDashBoard;
import com.example.cafyp.session.SessionManagerAdmin;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class CreatePayment extends AppCompatActivity {

    TextInputLayout paymentName, paymentDescription, paymentnumber;
    Button paymentcreatebtn;
    FirebaseDatabase database;
    DatabaseReference reference;
    ImageView homebtn, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_payment);

        paymentName = findViewById(R.id.put_payment_name);
        paymentDescription = findViewById(R.id.put_payment_description);
        paymentnumber = findViewById(R.id.put_payment_number);
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(this,SessionManagerAdmin.SESSION_ADMINSESSION);
        HashMap<String,String> adminDetails = sessionManagerAdmin.getAdminDataDetailFromSession();
        String admininvitationcode = adminDetails.get(SessionManagerAdmin.KEY_ADMININVITATIONCODE);
        String adminusername = adminDetails.get(SessionManagerAdmin.KEY_ADMINUSERNAME);


        paymentcreatebtn = findViewById(R.id.create_payment_btn);

        paymentcreatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validatepaymentcost() | !validatepaymentdes() | !validatepaymentname()){
                    return;
                }

                String _paymentname = paymentName.getEditText().getText().toString();
                String _paymentDes = paymentDescription.getEditText().getText().toString();
                String _paymentnumber = paymentnumber.getEditText().getText().toString();

                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                String _paymentdate = date;
                String _paymentid = _paymentname + date;


                database = FirebaseDatabase.getInstance();
                reference = database.getReference(admininvitationcode).child("Payments");

                PaymentHelpherClass addNewPayment = new PaymentHelpherClass(_paymentid, _paymentnumber, _paymentname, _paymentDes,_paymentdate);

                reference.child(_paymentid).setValue(addNewPayment);
                Toast.makeText(CreatePayment.this, "Create successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), PaymentDashboard .class));
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
                Intent intent = new Intent(getApplicationContext(), PaymentDashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean validatepaymentname() {
        String val = paymentName.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            paymentName.setError("Field can not be empty");
            return false;
        }
        else {
            paymentName.setError(null);
            paymentName.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatepaymentcost() {
        String val = paymentnumber.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            paymentnumber.setError("Field can not be empty");
            return false;
        }
        else {
            paymentnumber.setError(null);
            paymentnumber.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatepaymentdes() {
        String val = paymentDescription.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            paymentDescription.setError("Field can not be empty");
            return false;
        }
        else {
            paymentDescription.setError(null);
            paymentDescription.setErrorEnabled(false);
            return true;
        }
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), PaymentDashboard.class);
        startActivity(intent);
        finish();
    }

}