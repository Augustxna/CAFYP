package com.example.cafyp.PaymentMainAdmin;

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

import com.example.cafyp.Admin.AdminDashDoard;
import com.example.cafyp.Admin.RelatedMemberView;
import com.example.cafyp.AdminMain.MainAdminDashBoard;
import com.example.cafyp.PaymentAdmin.PaymentControl;
import com.example.cafyp.PaymentAdmin.PaymentUpdate;
import com.example.cafyp.PaymentAdmin.ViewPayment;
import com.example.cafyp.R;
import com.example.cafyp.session.SessionManagerAdmin;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainAdminPaymentControl extends AppCompatActivity {

    TextView paymentid, paymentname, paymentcost, paymentdescription, paymentcreatetime;
    String invicode, getpaymentname, getpaymentid;

    Button updatepaymentbtn;
    ImageView homebtn, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin_payment_control);

        paymentid = findViewById(R.id.paymentcontrol_id);
        paymentname = findViewById(R.id.paymentcontrol_name);
        paymentcost = findViewById(R.id.paymentcontrol_cost);
        paymentdescription = findViewById(R.id.paymentcontrol_description);
        paymentcreatetime = findViewById(R.id.paymentcontrol_date);

        updatepaymentbtn = findViewById(R.id.admin_update_payment_btn);
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(this, SessionManagerAdmin.SESSION_ADMINSESSION);
        HashMap<String,String> adminDetails = sessionManagerAdmin.getAdminDataDetailFromSession();
        String admininvitationcode = adminDetails.get(SessionManagerAdmin.KEY_ADMININVITATIONCODE);
        String adminusername = adminDetails.get(SessionManagerAdmin.KEY_ADMINUSERNAME);

        invicode = admininvitationcode;

        String _getpaymentid = getIntent().getStringExtra("paymentid");

        getpaymentid = _getpaymentid;

        DatabaseReference checkpayment = FirebaseDatabase.getInstance().getReference(invicode).child("Payments").child(getpaymentid);

        checkpayment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String _paymentid = snapshot.child("pid").getValue().toString();
                String _paymentname = snapshot.child("pname").getValue().toString();
                String _paymentcost = snapshot.child("pcost").getValue().toString();
                String _paymentdescription = snapshot.child("pdescription").getValue().toString();
                String _paymentdate = snapshot.child("pdate").getValue().toString();

                paymentid.setText(_paymentid);
                paymentname.setText(_paymentname);
                paymentcost.setText(_paymentcost);
                paymentdescription.setText(_paymentdescription);
                paymentcreatetime.setText(_paymentdate);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Intent intent = new Intent(MainAdminPaymentControl.this, ViewPayment.class);
                Toast.makeText(MainAdminPaymentControl.this,"Getting Payment Error", Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });


        updatepaymentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAdminPaymentControl.this, MainAdminPaymentUpdate.class);
                intent.putExtra("paymentid", getpaymentid);
                startActivity(intent);
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
                Intent intent = new Intent(getApplicationContext(), MainAdminViewPayment.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MainAdminViewPayment.class);
        startActivity(intent);
        finish();
    }

}