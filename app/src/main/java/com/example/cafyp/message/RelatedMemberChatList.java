package com.example.cafyp.message;

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
import com.example.cafyp.Admin.RelatedMemberView;
import com.example.cafyp.AdminMain.AdminControl;
import com.example.cafyp.R;
import com.example.cafyp.User.UserDashBoard;
import com.example.cafyp.adapter.ChatlistAdapter;
import com.example.cafyp.domain.ChatlistData;
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

public class RelatedMemberChatList extends AppCompatActivity {

    RecyclerView chatlistRecycleView;
    ChatlistAdapter mChatlistAdapter;
    List<ChatlistData> mChatlistList;

    EditText searchView;
    CharSequence search = "";

    ImageView homebtn, backbtn;

    FirebaseDatabase cldatabase;
    DatabaseReference clref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_related_member_chat_list);

        chatlistRecycleView = findViewById(R.id.chatlist_recycle);
        searchView = findViewById(R.id.searchchatlist_bar);
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(this, SessionManagerAdmin.SESSION_ADMINSESSION);
        HashMap<String, String> adminDetails = sessionManagerAdmin.getAdminDataDetailFromSession();
        String admininvitationcode = adminDetails.get(SessionManagerAdmin.KEY_ADMININVITATIONCODE);
        String adminusername = adminDetails.get(SessionManagerAdmin.KEY_ADMINUSERNAME);


        chatlistRecycleView.setHasFixedSize(true);
        chatlistRecycleView.setLayoutManager(new LinearLayoutManager(this));

        cldatabase = FirebaseDatabase.getInstance();
        clref = cldatabase.getReference(admininvitationcode).child("Users");


        mChatlistList = new ArrayList<>();
        mChatlistAdapter = new ChatlistAdapter(this, mChatlistList);

        chatlistRecycleView.setAdapter(mChatlistAdapter);

        clref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ChatlistData chatlistData = dataSnapshot.getValue(ChatlistData.class);
                    if (chatlistData.getResadminid().equals(adminusername)) {
                        mChatlistList.add(chatlistData);
                    }
                }
                mChatlistAdapter.notifyDataSetChanged();
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

                mChatlistAdapter.getfilter().filter(charSequence);
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