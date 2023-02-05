package com.example.cafyp.User.Heirs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafyp.Admin.AdminDashDoard;
import com.example.cafyp.Admin.NewRegisterCheckBoard;
import com.example.cafyp.Admin.RelatedMemberView;
import com.example.cafyp.R;
import com.example.cafyp.User.UserDashBoard;
import com.example.cafyp.session.SessionManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class HeirControlBoard extends AppCompatActivity {

    TextView heirname, heiricno, heirphoneno, heiremail, heirrelationship;
    String getheirname;

    ImageView homebtn, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heir_control_board);

        heirname = findViewById(R.id.userheirs_name);
        heiricno = findViewById(R.id.userheirs_icno);
        heirphoneno = findViewById(R.id.userheirs_phoneno);
        heiremail = findViewById(R.id.userheirs_email);
        heirrelationship = findViewById(R.id.userheirs_relationship);
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        SessionManager sessionManager = new SessionManager(getApplicationContext(),SessionManager.SESSION_USERSESSION);
        HashMap<String, String> usersDetails = sessionManager.getUserDataDetailFromSession();
        String invicode = usersDetails.get(SessionManager.KEY_CODE);
        String phonenumber = usersDetails.get(SessionManager.KEY_PHONENUMBER);

        String _getheirname = "heir not exist";

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _getheirname = extras.getString("heirname");
        }

        getheirname = _getheirname;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(invicode).child("Heirs").child(phonenumber).child(getheirname);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String _heirname = snapshot.child("name").getValue().toString();
                String _heiricno = snapshot.child("ic").getValue().toString();
                String _heiremail = snapshot.child("email").getValue().toString();
                String _heirphoneno = snapshot.child("phoneno").getValue().toString();
                String _heirrelationship = snapshot.child("relationship").getValue().toString();

                heirname.setText(_heirname);
                heiricno.setText(_heiricno);
                heiremail.setText(_heiremail);
                heirphoneno.setText(_heirphoneno);
                heirrelationship.setText(_heirrelationship);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                Toast.makeText(HeirControlBoard.this, "Database Error", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(getApplicationContext(), user_heirs.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), user_heirs.class);
        startActivity(intent);
        finish();
    }


}