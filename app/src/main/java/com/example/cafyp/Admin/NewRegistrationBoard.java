package com.example.cafyp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.cafyp.AdminMain.AdminControl;
import com.example.cafyp.AdminMain.MainAdminDashBoard;
import com.example.cafyp.R;
import com.example.cafyp.User.UserDashBoard;
import com.example.cafyp.adapter.NewRegistrationAdapter;
import com.example.cafyp.domain.NewRegistrationData;
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

public class NewRegistrationBoard extends AppCompatActivity {

    RecyclerView newregisterView;
    NewRegistrationAdapter mNewRegisterAdapter;
    List<NewRegistrationData> mNewRegisterList;

    FirebaseDatabase database;
    DatabaseReference reference;
    NewRegistrationAdapter.RecyclerNewRegisterClickListener listener;
    ImageView homebtn, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_registration_board);

        newregisterView = findViewById(R.id.newregistration_recycle);
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);


        newregisterView.setHasFixedSize(true);
        newregisterView.setLayoutManager(new LinearLayoutManager(this));

        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(this, SessionManagerAdmin.SESSION_ADMINSESSION);
        HashMap<String, String> adminDetails = sessionManagerAdmin.getAdminDataDetailFromSession();
        String admininvitationcode = adminDetails.get(SessionManagerAdmin.KEY_ADMININVITATIONCODE);
        String adminusername = adminDetails.get(SessionManagerAdmin.KEY_ADMINUSERNAME);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference(admininvitationcode).child("UserChecked");

        setOnClickListner();

        mNewRegisterList = new ArrayList<>();
        mNewRegisterAdapter = new NewRegistrationAdapter(this, mNewRegisterList, listener);

        newregisterView.setAdapter(mNewRegisterAdapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    NewRegistrationData newRegistrationData = dataSnapshot.getValue(NewRegistrationData.class);
                    mNewRegisterList.add(newRegistrationData);

                }
                mNewRegisterAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

    private void setOnClickListner() {

        listener = new NewRegistrationAdapter.RecyclerNewRegisterClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), NewRegisterCheckBoard.class);
                intent.putExtra("userphoneno", mNewRegisterList.get(position).getPhoneNo());
                startActivity(intent);
            }
        };
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), AdminDashDoard.class);
        startActivity(intent);
        finish();
    }

}