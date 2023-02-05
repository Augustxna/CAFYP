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
import com.example.cafyp.R;
import com.example.cafyp.User.UserDashBoard;
import com.example.cafyp.adapter.Message2ndAdapter;
import com.example.cafyp.adapter.MessageAdapter;
import com.example.cafyp.session.SessionManagerAdmin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity2nd extends AppCompatActivity {

    TextView membername;
    FirebaseDatabase cdatabase;
    DatabaseReference reference;
    DatabaseReference myref;
    String invicode, adminname, chatmemberid, chatmembername;

    RecyclerView recyclerView;
    EditText msg_editText;
    ImageButton sendBtn;

    Message2ndAdapter message2ndAdapter;
    List<Chats2nd> mchat2nd;

    ImageView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_activity2nd);

        membername = findViewById(R.id.messageactivity2_textView3);
        sendBtn = findViewById(R.id.messageactivity2_btn_send);
        msg_editText = findViewById(R.id.messageactivity2_text_send);
        recyclerView = findViewById(R.id.messageactivity2_recycle_view);

        backbtn = findViewById(R.id.back_button);

        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(this, SessionManagerAdmin.SESSION_ADMINSESSION);
        HashMap<String,String> adminDetails = sessionManagerAdmin.getAdminDataDetailFromSession();
        String admininvitationcode = adminDetails.get(SessionManagerAdmin.KEY_ADMININVITATIONCODE);
        String adminusername = adminDetails.get(SessionManagerAdmin.KEY_ADMINUSERNAME);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        invicode = admininvitationcode;
        adminname = adminusername;

        String getchatmemberid = getIntent().getStringExtra("memberid");

        chatmemberid = getchatmemberid;

        cdatabase = FirebaseDatabase.getInstance();
        reference = cdatabase.getReference(invicode).child("Users").child(chatmemberid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String _chatmembername = snapshot.child("memberusername").getValue().toString();
                    chatmembername = _chatmembername;
                    membername.setText(_chatmembername);
                }else{
                    membername.setText("unknown");
                }

                readMessages(adminname,chatmembername);
            }



            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                membername.setText("unknown");
                Toast.makeText(MessageActivity2nd.this, "Failed to find the Member", Toast.LENGTH_SHORT).show();
            }
        });


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = msg_editText.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(adminname, chatmembername, msg);
                } else {
                    Toast.makeText(MessageActivity2nd.this, "Please enter something...", Toast.LENGTH_SHORT).show();
                }

                msg_editText.setText("");
            }
        });


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RelatedMemberChatList.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void sendMessage(String sender, String receiver, String message) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(invicode);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        ref.child("Chats").push().setValue(hashMap);
    }

    private void readMessages(String myname, String selectedmembername){

        mchat2nd = new ArrayList<>();
        myref = FirebaseDatabase.getInstance().getReference(invicode).child("Chats");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                mchat2nd.clear();
                for(DataSnapshot ss : snapshot.getChildren()){
                    Chats2nd chats2nd = ss.getValue(Chats2nd.class);
                    if(chats2nd.getReceiver().equals(myname) && chats2nd.getSender().equals(selectedmembername) || chats2nd.getReceiver().equals(selectedmembername) && chats2nd.getSender().equals(myname)){
                        mchat2nd.add(chats2nd);
                    }

                    message2ndAdapter = new Message2ndAdapter(MessageActivity2nd.this,mchat2nd,myname);
                    recyclerView.setAdapter(message2ndAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), RelatedMemberChatList.class);
        startActivity(intent);
        finish();
    }


}