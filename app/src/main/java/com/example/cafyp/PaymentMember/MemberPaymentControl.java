package com.example.cafyp.PaymentMember;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafyp.R;
import com.example.cafyp.User.UserDashBoard;
import com.example.cafyp.session.SessionManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class MemberPaymentControl extends AppCompatActivity {

    TextView paymentname, paymentdescription, paymentcost, paymentdate, paymentstate;

    FirebaseDatabase database;
    DatabaseReference reference;

    Button paybtn;
    ImageView homebtn, backbtn;

    String _userpaymentid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_payment_control);

        SessionManager sessionManager = new SessionManager(getApplicationContext(),SessionManager.SESSION_USERSESSION);
        HashMap<String, String> usersDetails = sessionManager.getUserDataDetailFromSession();
        String invicode = usersDetails.get(SessionManager.KEY_CODE);
        String phonenumber = usersDetails.get(SessionManager.KEY_PHONENUMBER);

        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        paymentname = findViewById(R.id.user_payment_name);
        paymentdescription = findViewById(R.id.user_payment_description);
        paymentcost = findViewById(R.id.user_payment_cost);
        paymentdate = findViewById(R.id.user_payment_date);
        paymentstate = findViewById(R.id.user_payment_state);

        paybtn = findViewById(R.id.userpayment_pay_button);

        String userpayment = getIntent().getStringExtra("paymentid");

        _userpaymentid = userpayment;

        database = FirebaseDatabase.getInstance();
        reference = database.getReference(invicode).child("Payments").child(_userpaymentid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String _paymentname = snapshot.child("pname").getValue().toString();
                String _paymentdescription = snapshot.child("pdescription").getValue().toString();
                String _paymentcost = snapshot.child("pcost").getValue().toString();
                String _paymentdate = snapshot.child("pdate").getValue().toString();

                paymentname.setText(_paymentname);
                paymentdescription.setText(_paymentdescription);
                paymentcost.setText(_paymentcost);
                paymentdate.setText(_paymentdate);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        FirebaseDatabase cpsdatabase = FirebaseDatabase.getInstance();
        Query cpsref = cpsdatabase.getReference(invicode).child("PaymentChecked").child(phonenumber).orderByChild("paymentid").equalTo(_userpaymentid);
        cpsref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    paymentstate.setText("Done");
                }
                else{
                    paymentstate.setText("Incompleted Payment");
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


       paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (paymentstate.getText().equals("Done")) {

                    Toast.makeText(MemberPaymentControl.this, "PaymentCompleted", Toast.LENGTH_SHORT).show();
                } else {

                    getIDAndSendToPayPage();

                }
            }

           private void getIDAndSendToPayPage() {

                Intent intent = new Intent(getApplicationContext(),CheckOutPage.class);
                intent.putExtra("paymentid", _userpaymentid);
                startActivity(intent);
                finish();

           }
       });

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserDashBoard.class);
                startActivity(intent);
                finish();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MemberViewPayment.class);
                startActivity(intent);
                finish();
            }
        });


    }
}