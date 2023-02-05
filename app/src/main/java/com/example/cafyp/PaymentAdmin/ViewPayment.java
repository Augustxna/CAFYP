package com.example.cafyp.PaymentAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cafyp.Admin.AdminDashDoard;
import com.example.cafyp.AdminMain.AdminControl;
import com.example.cafyp.R;
import com.example.cafyp.User.UserDashBoard;
import com.example.cafyp.adapter.PaymentAdapter;
import com.example.cafyp.domain.PaymentData;
import com.example.cafyp.session.SessionManagerAdmin;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewPayment extends AppCompatActivity {

    RecyclerView paymentRecycleView;
    PaymentAdapter mPaymentAdapter;
    List<PaymentData> mPaymentList;

    EditText searchView;
    CharSequence search = "";

    FirebaseDatabase mdatabase;
    DatabaseReference mref;

    ImageView homebtn, backbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_payment);

        paymentRecycleView = findViewById(R.id.viewpayment_recycle);
        searchView = findViewById(R.id.searchpayment_bar);
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        paymentRecycleView.setHasFixedSize(true);
        paymentRecycleView.setLayoutManager(new LinearLayoutManager(this));

        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(this, SessionManagerAdmin.SESSION_ADMINSESSION);
        HashMap<String,String> adminDetails = sessionManagerAdmin.getAdminDataDetailFromSession();
        String admininvitationcode = adminDetails.get(SessionManagerAdmin.KEY_ADMININVITATIONCODE);
        String adminusername = adminDetails.get(SessionManagerAdmin.KEY_ADMINUSERNAME);

        mdatabase = FirebaseDatabase.getInstance();
        mref = mdatabase.getReference(admininvitationcode).child("Payments");


        mPaymentList = new ArrayList<>();
        mPaymentAdapter = new PaymentAdapter(this, mPaymentList, admininvitationcode);

        paymentRecycleView.setAdapter(mPaymentAdapter);

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    PaymentData paymentData = dataSnapshot.getValue(PaymentData.class);
                    mPaymentList.add(paymentData);
                }

                mPaymentAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                mPaymentAdapter.getfilter().filter(charSequence);
                search = charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {

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

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), PaymentDashboard.class);
        startActivity(intent);
        finish();
    }

}