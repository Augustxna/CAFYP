package com.example.cafyp.message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafyp.Admin.RelatedMemberView;
import com.example.cafyp.User.UserDashBoard;
import com.example.cafyp.session.SessionManager;
import com.example.cafyp.R;
import com.example.cafyp.adapter.MessageAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    TextView username;
    FirebaseDatabase cdatabase;
    DatabaseReference reference;
    String _postcode;
    String _username;
    String tf;

    RecyclerView recyclerView;
    EditText msg_editText;
    ImageButton sendBtn;

    MessageAdapter messageAdapter;
    List<Chats> mchat;

    String invicode;

    ImageView backbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        username = findViewById(R.id.textView3);
        backbtn = findViewById(R.id.back_button);

        sendBtn = findViewById(R.id.btn_send);
        msg_editText = findViewById(R.id.text_send);

        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

        SessionManager sessionManager = new SessionManager(this,SessionManager.SESSION_USERSESSION);
        HashMap<String, String> usersDetails = sessionManager.getUserDataDetailFromSession();
        String memberusername = usersDetails.get(SessionManager.KEY_MEMEBRUSERNAME);
        String code = usersDetails.get(SessionManager.KEY_CODE);
        String resadminid = usersDetails.get(SessionManager.KEY_RESADMINID);

        invicode = code;


        cdatabase = FirebaseDatabase.getInstance();
        Query cref = cdatabase.getReference(code).child("Admins").orderByChild("username").equalTo(resadminid);
        cref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    for(DataSnapshot ds : dataSnapshot.getChildren()){

                        tf = ds.child("username").getValue(String.class);
                        username.setText(tf);
                    }

                } else {

                   username.setText("unknown");

                }

                readMessages(memberusername,tf);
                /*HelperClass helperClass = dataSnapshot.getValue(HelperClass.class);

                if(helperClass.getPostcode().equals(_state)){

                    String name = dataSnapshot.child("username").getValue().toString();
                    username.setText(name);


                }*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = msg_editText.getText().toString();
                if(!msg.equals("")){
                    sendMessage(memberusername, tf, msg);
                }else{
                    Toast.makeText(MessageActivity.this, "Please enter something...", Toast.LENGTH_SHORT).show();
                }

                msg_editText.setText("");
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

    private void sendMessage(String sender, String receiver, String message){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(invicode);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        reference.child("Chats").push().setValue(hashMap);
    }


    private void readMessages(String myname, String userid){

        mchat = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference(invicode).child("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mchat.clear();
                for(DataSnapshot ss : snapshot.getChildren()){
                    Chats chats = ss.getValue(Chats.class);
                    if(chats.getReceiver().equals(myname) && chats.getSender().equals(userid) || chats.getReceiver().equals(userid) && chats.getSender().equals(myname)){
                        mchat.add(chats);
                    }

                    messageAdapter = new MessageAdapter(MessageActivity.this, mchat, myname);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), UserDashBoard.class);
        startActivity(intent);
        finish();
    }


   /*private void setSupportActionBar(Toolbar toolbar) {
    }*/
}