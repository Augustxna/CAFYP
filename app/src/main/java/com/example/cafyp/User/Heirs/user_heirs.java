package com.example.cafyp.User.Heirs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cafyp.Admin.NewRegisterCheckBoard;
import com.example.cafyp.PaymentAdmin.PaymentDashboard;
import com.example.cafyp.User.UserDashBoard;
import com.example.cafyp.adapter.NewRegistrationAdapter;
import com.example.cafyp.session.SessionManager;
import com.example.cafyp.R;
import com.example.cafyp.adapter.HeirAdapter;
import com.example.cafyp.domain.HeirData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class user_heirs extends AppCompatActivity {

    RecyclerView heirRecycleView;
    HeirAdapter mHeirAdapter;
    Button mAddHeir;
    Button Heirbackward;
    List<HeirData> mHeirList;
    FirebaseDatabase mdatabase;
    DatabaseReference Href;
    ImageView homebtn, backbtn;

    EditText searchView;
    CharSequence search = "";

    String invicode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_hiers);

        heirRecycleView = findViewById(R.id.heir_recycle);
        searchView = findViewById(R.id.searchpayment_bar);
        mAddHeir = findViewById(R.id.AddHeirbtn);
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        SessionManager sessionManager = new SessionManager(this,SessionManager.SESSION_USERSESSION);
        HashMap<String, String> usersDetails = sessionManager.getUserDataDetailFromSession();
        invicode = usersDetails.get(SessionManager.KEY_CODE);
        String Userphonenumber = usersDetails.get(SessionManager.KEY_PHONENUMBER);

        heirRecycleView.setHasFixedSize(true);
        heirRecycleView.setLayoutManager(new LinearLayoutManager(this));

        String RelatedUser = Userphonenumber;

        mdatabase = FirebaseDatabase.getInstance();
        Href = mdatabase.getReference(invicode).child("Heirs").child(RelatedUser);

        mHeirList = new ArrayList<>();
        mHeirAdapter = new HeirAdapter(this, mHeirList, invicode, Userphonenumber);

        heirRecycleView.setAdapter(mHeirAdapter);

        Href.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HeirData heirData = dataSnapshot.getValue(HeirData.class);
                    mHeirList.add(heirData);
                }

                mHeirAdapter.notifyDataSetChanged();

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

                mHeirAdapter.getfilter().filter(charSequence);
                search = charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        mAddHeir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(user_heirs.this, AddHeirs.class);
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
                Intent intent = new Intent(getApplicationContext(), UserDashBoard.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), UserDashBoard.class);
        startActivity(intent);
        finish();
    }

}