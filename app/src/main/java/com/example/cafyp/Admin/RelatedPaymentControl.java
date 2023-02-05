package com.example.cafyp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cafyp.R;
import com.example.cafyp.session.SessionManagerAdmin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class RelatedPaymentControl extends AppCompatActivity {

    TextView repaymentname, repaymentdescription, repaymentcost, repaymentdate, repaymentstate;

    FirebaseDatabase redatabase;
    DatabaseReference rereference;

    Button paybtn;

    String _reuserpaymentid;
    String _getrelatedmemberid;
    ImageView homebtn, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_related_payment_control);

        repaymentname = findViewById(R.id.relatedpayment_name);
        repaymentdescription = findViewById(R.id.relatedpayment_description);
        repaymentcost = findViewById(R.id.relatedpayment_cost);
        repaymentdate = findViewById(R.id.relatedpayment_date);
        repaymentstate = findViewById(R.id.relatedpayment_state);
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(this, SessionManagerAdmin.SESSION_ADMINSESSION);
        HashMap<String, String> adminDetails = sessionManagerAdmin.getAdminDataDetailFromSession();
        String admininvitationcode = adminDetails.get(SessionManagerAdmin.KEY_ADMININVITATIONCODE);
        String adminusername = adminDetails.get(SessionManagerAdmin.KEY_ADMINUSERNAME);


        String reuserpayment = getIntent().getStringExtra("paymentid");

        _reuserpaymentid = reuserpayment;

        _getrelatedmemberid = getIntent().getStringExtra("userid");

        redatabase = FirebaseDatabase.getInstance();
        rereference = redatabase.getReference(admininvitationcode).child("Payments").child(_reuserpaymentid);

        rereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String _repaymentname = snapshot.child("pname").getValue().toString();
                String _repaymentdescription = snapshot.child("pdescription").getValue().toString();
                String _repaymentcost = snapshot.child("pcost").getValue().toString();
                String _repaymentdate = snapshot.child("pdate").getValue().toString();

                repaymentname.setText(_repaymentname);
                repaymentdescription.setText(_repaymentdescription);
                repaymentcost.setText(_repaymentcost);
                repaymentdate.setText(_repaymentdate);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        FirebaseDatabase recpsdatabase = FirebaseDatabase.getInstance();
        Query recpsref = recpsdatabase.getReference(admininvitationcode).child("PaymentChecked").child(_getrelatedmemberid).orderByChild("paymentid").equalTo(_reuserpaymentid);
        recpsref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    repaymentstate.setText("Done");
                } else {
                    repaymentstate.setText("Incompleted Payment");
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

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
                Intent intent = new Intent(getApplicationContext(), ViewRelatedPayment.class);
                intent.putExtra("relatedmemberid", _getrelatedmemberid);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), ViewRelatedPayment.class);
        intent.putExtra("relatedmemberid", _getrelatedmemberid);
        startActivity(intent);
        finish();
    }

}