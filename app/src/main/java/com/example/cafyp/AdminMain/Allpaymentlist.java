package com.example.cafyp.AdminMain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.contentcapture.DataRemovalRequest;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cafyp.Admin.RelatedMemberView;
import com.example.cafyp.PaymentAdmin.PaymentControl;
import com.example.cafyp.R;
import com.example.cafyp.User.UserDashBoard;
import com.example.cafyp.adapter.ListReportAdapter;
import com.example.cafyp.domain.ListReportData;
import com.example.cafyp.session.SessionManagerAdmin;
import com.google.firebase.auth.internal.RecaptchaActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Allpaymentlist extends AppCompatActivity {

    RecyclerView reportRecycleView;
    ListReportAdapter mlistReportAdapter;
    List<ListReportData> listReportDataList;

    EditText searchallpaymentView;
    CharSequence search = "";

    FirebaseDatabase database;
    DatabaseReference reference;

    ImageView homebtn, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allpaymentlist);

        reportRecycleView = findViewById(R.id.listreport_recycle);

        reportRecycleView.setHasFixedSize(true);
        reportRecycleView.setLayoutManager(new LinearLayoutManager(this));

        searchallpaymentView = findViewById(R.id.allpaymentsearch_bar);

        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(this, SessionManagerAdmin.SESSION_ADMINSESSION);
        HashMap<String, String> adminDetails = sessionManagerAdmin.getAdminDataDetailFromSession();
        String admininvitationcode = adminDetails.get(SessionManagerAdmin.KEY_ADMININVITATIONCODE);
        String adminusername = adminDetails.get(SessionManagerAdmin.KEY_ADMINUSERNAME);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference(admininvitationcode).child("Payments");


        listReportDataList = new ArrayList<>();
        mlistReportAdapter = new ListReportAdapter(this, listReportDataList);

        reportRecycleView.setAdapter(mlistReportAdapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ListReportData listReportData = dataSnapshot.getValue(ListReportData.class);
                    listReportDataList.add(listReportData);
                }

                mlistReportAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        searchallpaymentView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                mlistReportAdapter.getFilter().filter(charSequence);
                search = charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {

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