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
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;

import com.example.cafyp.Admin.AdminDashDoard;
import com.example.cafyp.Admin.RelatedMemberControl;
import com.example.cafyp.R;
import com.example.cafyp.adapter.AllMemberAdapter;
import com.example.cafyp.adapter.RelatedMemberAdapter;
import com.example.cafyp.domain.AllMemberData;
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

public class ViewAllMembers extends AppCompatActivity {

    RecyclerView allmemberRecycleView;
    AllMemberAdapter mAllAdapter;
    List<AllMemberData> mAllList;

    EditText searchView;
    CharSequence search = "";

    FirebaseDatabase alldatabase;
    DatabaseReference allref;

    ImageView homebtn, backbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_members);

        allmemberRecycleView = findViewById(R.id.viewallmember_recycle);
        searchView = findViewById(R.id.search_bar);
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(this, SessionManagerAdmin.SESSION_ADMINSESSION);
        HashMap<String, String> adminDetails = sessionManagerAdmin.getAdminDataDetailFromSession();
        String admininvitationcode = adminDetails.get(SessionManagerAdmin.KEY_ADMININVITATIONCODE);
        String adminusername = adminDetails.get(SessionManagerAdmin.KEY_ADMINUSERNAME);

        allmemberRecycleView.setHasFixedSize(true);
        allmemberRecycleView.setLayoutManager(new LinearLayoutManager(this));

        alldatabase = FirebaseDatabase.getInstance();
        allref = alldatabase.getReference(admininvitationcode).child("Users");

        mAllList = new ArrayList<>();
        mAllAdapter = new AllMemberAdapter(this, mAllList, admininvitationcode);

        allmemberRecycleView.setAdapter(mAllAdapter);

        allref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AllMemberData allMemberData = dataSnapshot.getValue(AllMemberData.class);
                    mAllList.add(allMemberData);
                }

                mAllAdapter.notifyDataSetChanged();
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

                mAllAdapter.getfilter().filter(charSequence);
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