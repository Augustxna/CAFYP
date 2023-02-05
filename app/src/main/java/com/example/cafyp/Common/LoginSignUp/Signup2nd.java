package com.example.cafyp.Common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.mbms.StreamingServiceInfo;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafyp.Admin.RelatedMemberView;
import com.example.cafyp.Common.MainActivity;
import com.example.cafyp.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class Signup2nd extends AppCompatActivity {

    TextInputLayout invitationcode;
    DatePicker datePicker;
    ImageView backbtn;

    String namefromsignup,emailfromsignup,nricfromsignup,addressfromsignup,postcodefromsignup,statefromsignup,passwordfromsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafyp_signup2nd);

        invitationcode = findViewById(R.id.signup_invitation_code);
        datePicker = findViewById(R.id.date_picker);
        backbtn = findViewById(R.id.back_button);

        String invitationcodefromsignup3 = getIntent().getStringExtra("invicodefromsignup3");
        invitationcode.getEditText().setText(invitationcodefromsignup3);

        namefromsignup = getIntent().getStringExtra("name");
        emailfromsignup = getIntent().getStringExtra("email");
        nricfromsignup = getIntent().getStringExtra("NRIC");
        addressfromsignup = getIntent().getStringExtra("address");
        postcodefromsignup = getIntent().getStringExtra("postcode");
        statefromsignup = getIntent().getStringExtra("state");
        passwordfromsignup = getIntent().getStringExtra("password");

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                intent.putExtra("namefromsignup2", namefromsignup);
                intent.putExtra("emailfromsignup2", emailfromsignup);
                intent.putExtra("NRICfromsignup2", nricfromsignup);
                intent.putExtra("addressfromsignup2", addressfromsignup);
                intent.putExtra("postcodefromsignup2", postcodefromsignup);
                intent.putExtra("statefromsignup2", statefromsignup);
                intent.putExtra("passwordfromsignup2", passwordfromsignup);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean validateCode() {
        String val = invitationcode.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            invitationcode.setError("Field can not be empty");
            return false;
        } else {
            invitationcode.setError(null);
            invitationcode.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateDate() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 18) {
            Toast.makeText(this, "you are valid to register", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    public void call3rdSignupScreen(View view) {


        if (!validateCode() || !validateDate()) {
            return;
        }

        String name2 = getIntent().getStringExtra("name");
        String email2 = getIntent().getStringExtra("email");
        String nric2 = getIntent().getStringExtra("NRIC");
        String address2 = getIntent().getStringExtra("address");
        String postcode2 = getIntent().getStringExtra("postcode");
        String state2 = getIntent().getStringExtra("state");
        String password2 = getIntent().getStringExtra("password");

        String invitationCode = invitationcode.getEditText().getText().toString().trim();

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        String date = day + "/" + month + "/" + year;


        Intent intent = new Intent(Signup2nd.this, Signup3nd.class);
        intent.putExtra("name2", name2);
        intent.putExtra("email2", email2);
        intent.putExtra("NRIC2", nric2);
        intent.putExtra("address2", address2);
        intent.putExtra("postcode2", postcode2);
        intent.putExtra("state2", state2);
        intent.putExtra("password2", password2);
        intent.putExtra("invitationcode2", invitationCode);
        intent.putExtra("date2", date);
        startActivity(intent);
        finish();

    }

    public void callLoginScreen(View view) {

        startActivity(new Intent(this, Login.class));
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), Signup.class);
        intent.putExtra("namefromsignup2", namefromsignup);
        intent.putExtra("emailfromsignup2", emailfromsignup);
        intent.putExtra("NRICfromsignup2", nricfromsignup);
        intent.putExtra("addressfromsignup2", addressfromsignup);
        intent.putExtra("postcodefromsignup2", postcodefromsignup);
        intent.putExtra("statefromsignup2", statefromsignup);
        intent.putExtra("passwordfromsignup2", passwordfromsignup);
        startActivity(intent);
        finish();
    }

}