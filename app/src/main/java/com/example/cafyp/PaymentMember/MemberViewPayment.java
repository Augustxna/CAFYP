package com.example.cafyp.PaymentMember;

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

import com.example.cafyp.AdminMain.AdminControl;
import com.example.cafyp.R;
import com.example.cafyp.User.UserDashBoard;
import com.example.cafyp.adapter.MemberPaymentAdapter;
import com.example.cafyp.adapter.PaymentAdapter;
import com.example.cafyp.domain.MemberPaymentData;
import com.example.cafyp.session.SessionManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MemberViewPayment extends AppCompatActivity {


    RecyclerView mPaymentRecycleView;
    MemberPaymentAdapter mmPaymentAdapter;
    List<MemberPaymentData> mmPaymentList;

    EditText searchView;
    CharSequence search = "";

    FirebaseDatabase mmdatabase;
    DatabaseReference mmref;

    ImageView homebtn, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_view_payment);

        mPaymentRecycleView = findViewById(R.id.memberviewpayment_recycle);
        searchView = findViewById(R.id.searchmemberpayment_bar);
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        SessionManager sessionManager = new SessionManager(getApplicationContext(),SessionManager.SESSION_USERSESSION);
        HashMap<String, String> usersDetails = sessionManager.getUserDataDetailFromSession();
        String invicode = usersDetails.get(SessionManager.KEY_CODE);
        String phonenumber = usersDetails.get(SessionManager.KEY_PHONENUMBER);

        mPaymentRecycleView.setHasFixedSize(true);
        mPaymentRecycleView.setLayoutManager(new LinearLayoutManager(this));

        mmdatabase = FirebaseDatabase.getInstance();
        mmref = mmdatabase.getReference(invicode).child("Payments");

        mmPaymentList = new ArrayList<>();
        mmPaymentAdapter = new MemberPaymentAdapter(this,mmPaymentList);

        mPaymentRecycleView.setAdapter(mmPaymentAdapter);

        mmref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    MemberPaymentData memberPaymentData = dataSnapshot.getValue(MemberPaymentData.class);
                    mmPaymentList.add(memberPaymentData);
                }

                mmPaymentAdapter.notifyDataSetChanged();
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

                mmPaymentAdapter.getfilter().filter(charSequence);
                search = charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
                Intent intent = new Intent(getApplicationContext(), UserDashBoard.class);
                startActivity(intent);
                finish();
            }
        });

    }
}