package com.example.cafyp.AdminMainViewMember;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.cafyp.Admin.AdminDashDoard;
import com.example.cafyp.Admin.RelatedMemberControl;
import com.example.cafyp.Admin.RelatedMemberView;
import com.example.cafyp.AdminMain.MainAdminDashBoard;
import com.example.cafyp.R;
import com.example.cafyp.adapter.MainAdminRelatedHeirAdapter;
import com.example.cafyp.adapter.RelatedHeirsAdapter;
import com.example.cafyp.domain.RelatedHeirsData;
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

public class MainAdminViewRelatedHeirs extends AppCompatActivity {

    RecyclerView relatedheirsRecycleView;
    MainAdminRelatedHeirAdapter mRelatedHeirsAdapter;
    List<RelatedHeirsData> mRelatedHeirList;

    FirebaseDatabase reheirdatabase;
    DatabaseReference reheirref;

    String invicode;
    ImageView homebtn, backbtn;
    String _relatedmemberid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin_view_related_heirs);

        relatedheirsRecycleView = findViewById(R.id.viewrelatedheir_recycle);
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(this, SessionManagerAdmin.SESSION_ADMINSESSION);
        HashMap<String, String> adminDetails = sessionManagerAdmin.getAdminDataDetailFromSession();
        String admininvitationcode = adminDetails.get(SessionManagerAdmin.KEY_ADMININVITATIONCODE);

        invicode = admininvitationcode;

       _relatedmemberid = getIntent().getStringExtra("relatedmemberid");

        relatedheirsRecycleView.setHasFixedSize(true);
        relatedheirsRecycleView.setLayoutManager(new LinearLayoutManager(this));

        reheirdatabase = FirebaseDatabase.getInstance();
        reheirref = reheirdatabase.getReference(invicode).child("Heirs").child(_relatedmemberid);

        mRelatedHeirList = new ArrayList<>();
        mRelatedHeirsAdapter = new MainAdminRelatedHeirAdapter(this, mRelatedHeirList);

        relatedheirsRecycleView.setAdapter(mRelatedHeirsAdapter);

        reheirref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    RelatedHeirsData relatedHeirsData = dataSnapshot.getValue(RelatedHeirsData.class);
                    mRelatedHeirList.add(relatedHeirsData);
                }

                mRelatedHeirsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

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
                Intent intent = new Intent(getApplicationContext(), MainAdminAllMemberControl.class);
                intent.putExtra("memberid", _relatedmemberid);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MainAdminAllMemberControl.class);
        intent.putExtra("memberid", _relatedmemberid);
        startActivity(intent);
        finish();
    }

}