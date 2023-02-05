package com.example.cafyp.Admin;

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

import com.example.cafyp.PaymentAdmin.PaymentControl;
import com.example.cafyp.R;
import com.example.cafyp.User.UserDashBoard;
import com.example.cafyp.adapter.PaymentAdapter;
import com.example.cafyp.adapter.RelatedHeirsAdapter;
import com.example.cafyp.adapter.RelatedPaymentAdapter;
import com.example.cafyp.domain.RelatedPaymentData;
import com.example.cafyp.session.SessionManagerAdmin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewRelatedPayment extends AppCompatActivity {

    RecyclerView relatedPayemntRecycleView;
    RelatedPaymentAdapter mRelatedPaymentAdapter;
    List<RelatedPaymentData> mRelatedPaymentList;

    EditText searchView;
    CharSequence search = "";

    FirebaseDatabase database;
    DatabaseReference reference;

    String getrelatedmemberid;
    ImageView homebtn, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_related_payment);

        relatedPayemntRecycleView = findViewById(R.id.relatedpayment_recycle);
        searchView = findViewById(R.id.searchrelatedpayment_bar);
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);


        relatedPayemntRecycleView.setHasFixedSize(true);
        relatedPayemntRecycleView.setLayoutManager(new LinearLayoutManager(this));


        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(this, SessionManagerAdmin.SESSION_ADMINSESSION);
        HashMap<String, String> adminDetails = sessionManagerAdmin.getAdminDataDetailFromSession();
        String admininvitationcode = adminDetails.get(SessionManagerAdmin.KEY_ADMININVITATIONCODE);
        String adminusername = adminDetails.get(SessionManagerAdmin.KEY_ADMINUSERNAME);

        getrelatedmemberid = getIntent().getStringExtra("relatedmemberid");

        database = FirebaseDatabase.getInstance();
        reference = database.getReference(admininvitationcode).child("Payments");


        mRelatedPaymentList = new ArrayList<>();
        mRelatedPaymentAdapter = new RelatedPaymentAdapter(this, mRelatedPaymentList,getrelatedmemberid);

        relatedPayemntRecycleView.setAdapter(mRelatedPaymentAdapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    RelatedPaymentData relatedPaymentData = dataSnapshot.getValue(RelatedPaymentData.class);
                    mRelatedPaymentList.add(relatedPaymentData);
                }

                mRelatedPaymentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                mRelatedPaymentAdapter.getfilter().filter(charSequence);
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
                Intent intent = new Intent(getApplicationContext(), RelatedMemberControl.class);
                intent.putExtra("memberid", getrelatedmemberid);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), RelatedMemberControl.class);
        intent.putExtra("memberid", getrelatedmemberid);
        startActivity(intent);
        finish();
    }


}