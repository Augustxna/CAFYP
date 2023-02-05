package com.example.cafyp.User.Heirs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cafyp.Admin.RelatedMemberView;
import com.example.cafyp.User.UserDashBoard;
import com.example.cafyp.session.SessionManager;
import com.example.cafyp.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddHeirs extends AppCompatActivity {

    TextInputLayout Hname, Hic, Hemail, HphoneNumber, Hrelationship;
    Button mHeirButton;
    FirebaseDatabase database;
    DatabaseReference reference;
    ImageView homebtn, backbtn;

    String invicode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_heirs);

        Hname = findViewById(R.id.user_heirs_name);
        Hic = findViewById(R.id.user_heirs_ic);
        Hemail = findViewById(R.id.user_heirs_email);
        HphoneNumber = findViewById(R.id.user_heirs_phonenumber);
        Hrelationship = findViewById(R.id.user_heirs_relatioship);
        mHeirButton = findViewById(R.id.create_heir_btn);

        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        SessionManager sessionManager = new SessionManager(this,SessionManager.SESSION_USERSESSION);
        HashMap<String, String> usersDetails = sessionManager.getUserDataDetailFromSession();
        invicode = usersDetails.get(SessionManager.KEY_CODE);
        String Userphonenumber = usersDetails.get(SessionManager.KEY_PHONENUMBER);


        mHeirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validatename()|!validateic()|!validateemail()|!validatephoneNumber()|!validaterelationship()){
                    return;
                }

                String name = Hname.getEditText().getText().toString();
                String IC = Hic.getEditText().getText().toString();
                String email = Hemail.getEditText().getText().toString();
                String phoneNo = HphoneNumber.getEditText().getText().toString();
                String relationship = Hrelationship.getEditText().getText().toString();
                String relatedwith = Userphonenumber;

                Map<String, String> mMap = new HashMap<>();
                mMap.put("name", name);
                mMap.put("ic", IC);
                mMap.put("email", email);
                mMap.put("phoneno", phoneNo);
                mMap.put("relationship", relationship);
                mMap.put("relatedwith", relatedwith);

                database = FirebaseDatabase.getInstance();
                reference = database.getReference(invicode).child("Heirs");

                reference.child(relatedwith).child(name).setValue(mMap);
                Toast.makeText(AddHeirs.this, "added successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), user_heirs.class);
                startActivity(intent);


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

    private boolean validatename() {
        String val = Hname.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            Hname.setError("Field can not be empty");
            return false;
        }
        else {
            Hname.setError(null);
            Hname.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateic() {
        String val = Hic.getEditText().getText().toString().trim();
        String checkNRIC = ".{12,12}";

        if (val.isEmpty()) {
            Hic.setError("Field can not be empty");
            return false;
        }else if (!val.matches(checkNRIC)) {
           Hic.setError("Ic Number must be 12 number");
            return false;
        }
        else {
            Hic.setError(null);
            Hic.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateemail() {
        String val = Hemail.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            Hemail.setError("Field can not be empty");
            return false;
        }else if (!val.matches(checkEmail)) {
            Hemail.setError("Invalid Email");
            return false;}
        else {
            Hemail.setError(null);
            Hemail.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatephoneNumber() {
        String val = HphoneNumber.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            HphoneNumber.setError("Field can not be empty");
            return false;
        }
        else {
            HphoneNumber.setError(null);
            HphoneNumber.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validaterelationship() {
        String val = Hrelationship.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            Hrelationship.setError("Field can not be empty");
            return false;
        }
        else {
            Hrelationship.setError(null);
            Hrelationship.setErrorEnabled(false);
            return true;
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), user_heirs.class);
        startActivity(intent);
        finish();
    }

}