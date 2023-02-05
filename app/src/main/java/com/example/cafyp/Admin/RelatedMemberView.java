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

import com.example.cafyp.AdminMain.AdminControl;
import com.example.cafyp.PaymentAdmin.PaymentDashboard;
import com.example.cafyp.R;
import com.example.cafyp.User.UserDashBoard;
import com.example.cafyp.adapter.AdminAdapter;
import com.example.cafyp.adapter.RelatedMemberAdapter;
import com.example.cafyp.domain.AdminData;
import com.example.cafyp.domain.RelatedMemberData;
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

public class RelatedMemberView extends AppCompatActivity {

    RecyclerView relatedmemberRecycleView;
    RelatedMemberAdapter mRelatedAdapter;
    List<RelatedMemberData> mRelatedList;

    EditText searchView;
    CharSequence search = "";

    FirebaseDatabase relateddatabase;
    DatabaseReference relatedref;
    ImageView homebtn, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_related_member_view);

        relatedmemberRecycleView = findViewById(R.id.view_relatedmember_recycle);
        searchView = findViewById(R.id.searchrelatedmember_bar);
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(this, SessionManagerAdmin.SESSION_ADMINSESSION);
        HashMap<String, String> adminDetails = sessionManagerAdmin.getAdminDataDetailFromSession();
        String admininvitationcode = adminDetails.get(SessionManagerAdmin.KEY_ADMININVITATIONCODE);
        String adminusername = adminDetails.get(SessionManagerAdmin.KEY_ADMINUSERNAME);

        relatedmemberRecycleView.setHasFixedSize(true);
        relatedmemberRecycleView.setLayoutManager(new LinearLayoutManager(this));

        relateddatabase = FirebaseDatabase.getInstance();
        relatedref = relateddatabase.getReference(admininvitationcode).child("Users");

        mRelatedList = new ArrayList<>();
        mRelatedAdapter = new RelatedMemberAdapter(this, mRelatedList, admininvitationcode);

        relatedmemberRecycleView.setAdapter(mRelatedAdapter);

        relatedref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    RelatedMemberData relatedMemberData = dataSnapshot.getValue(RelatedMemberData.class);
                    if (relatedMemberData.getResadminid().equals(adminusername)) {
                        mRelatedList.add(relatedMemberData);
                    }
                }

                mRelatedAdapter.notifyDataSetChanged();
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

                mRelatedAdapter.getfilter().filter(charSequence);
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