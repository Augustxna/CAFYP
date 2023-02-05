package com.example.cafyp.AdminMain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafyp.Admin.RelatedMemberView;
import com.example.cafyp.R;
import com.example.cafyp.User.UserDashBoard;
import com.example.cafyp.domain.Calculate;
import com.example.cafyp.session.SessionManagerAdmin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Reportread extends AppCompatActivity {

    TextView paymentid, paymentname, paymentcost, paymentdescription, paymentcreatetime;
    TextView paymentnumber, paymenttotal;
    String invicode, getpaymentname, getpaymentid, getpaymentcost;
    List<Calculate> calculateList;

    ImageView homebtn, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportread);

        paymentid = findViewById(R.id.paymentreport_id);
        paymentname = findViewById(R.id.paymentreport_name);
        paymentcost = findViewById(R.id.paymentreport_cost);
        paymentdescription = findViewById(R.id.paymentreport_description);
        paymentcreatetime = findViewById(R.id.paymentreport_date);

        paymentnumber = findViewById(R.id.paymentreport_paymentnumber);
        paymenttotal = findViewById(R.id.paymentreport_totalpayment);

        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(this, SessionManagerAdmin.SESSION_ADMINSESSION);
        HashMap<String, String> adminDetails = sessionManagerAdmin.getAdminDataDetailFromSession();
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

                getpaymentcost = _paymentcost;

                paymentid.setText(_paymentid);
                paymentname.setText(_paymentname);
                paymentcost.setText(_paymentcost);
                paymentdescription.setText(_paymentdescription);
                paymentcreatetime.setText(_paymentdate);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Intent intent = new Intent(Reportread.this, Allpaymentlist.class);
                Toast.makeText(Reportread.this, "Getting Payment Error", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(getApplicationContext(), Allpaymentlist.class);
                startActivity(intent);
                finish();
            }
        });

        Getthetotalnumber();

        /*get = findViewById(R.id.textView2);

        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(this);
        HashMap<String, String> adminDetails = sessionManagerAdmin.getAdminDataDetailFromSession();
        String admininvitationcode = adminDetails.get(SessionManagerAdmin.KEY_ADMININVITATIONCODE);
        String adminusername = adminDetails.get(SessionManagerAdmin.KEY_ADMINUSERNAME);

        String getpid = "Payment not set";

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            getpid = extras.getString("pid");
        }

        _getpid = getpid;

        calculateList = new ArrayList<>();


        mref = FirebaseDatabase.getInstance().getReference(admininvitationcode).child("PaymentDone");

       mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                int n = 0;
                int m = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Calculate calculate = dataSnapshot.getValue(Calculate.class);
                    if(calculate.getPaymentid().equals(_getpid)) {
                        calculateList.add(calculate);
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference reference = database.getReference(admininvitationcode).child("Payments").child(_getpid);
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                price = snapshot.child("pcost").getValue().toString();
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                    }
                    n = calculateList.size();
                    m = Integer.parseInt(price);
                    int total = n * m;
                    get.setText(String.valueOf(total));
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });*/

    }

    private void Getthetotalnumber() {

        calculateList = new ArrayList<>();

        DatabaseReference mref = FirebaseDatabase.getInstance().getReference(invicode).child("PaymentDone");
        mref.addValueEventListener(new ValueEventListener() {
            int n = 0;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Calculate calculate = dataSnapshot.getValue(Calculate.class);
                    if (calculate.getPaymentid().equals(getpaymentid)) {
                        calculateList.add(calculate);
                    }
                    n = calculateList.size();
                    paymentnumber.setText(String.valueOf(n));
                    Double total = Double.parseDouble(getpaymentcost) * n;
                    paymenttotal.setText(String.valueOf(total));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Double total = Double.parseDouble(getpaymentcost) * n;
        // paymenttotal.setText(String.valueOf(total));

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), Allpaymentlist.class);
        startActivity(intent);
        finish();
    }

}