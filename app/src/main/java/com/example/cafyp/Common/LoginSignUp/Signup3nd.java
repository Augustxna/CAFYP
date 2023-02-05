package com.example.cafyp.Common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.cafyp.Admin.RelatedMemberView;
import com.example.cafyp.Common.MainActivity;
import com.example.cafyp.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;


public class Signup3nd extends AppCompatActivity {

    ScrollView scrollView;
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;
    ImageView backbtn;

    String invicodefromsignup2,namefromsignup2,emailfromsignup2,nricfromsignup2,addressfromsignup2,postcodefromsignup2,statefromsignup2,passwordfromsignup2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafyp_signup3nd);

        //RegisterUser = (Button) findViewById(R.id.signup_login_button);
        //RegisterUser.setOnClickListener(this);


        scrollView = findViewById(R.id.singup_3rd_screen_scroolview);
        countryCodePicker = findViewById(R.id.country_code_picker);
        phoneNumber = findViewById(R.id.signup_phone_number);
        backbtn = findViewById(R.id.back_button);

         invicodefromsignup2 = getIntent().getStringExtra("invitationcode2");
         namefromsignup2 = getIntent().getStringExtra("name2");
         emailfromsignup2 = getIntent().getStringExtra("email2");
         nricfromsignup2 = getIntent().getStringExtra("NRIC2");
         addressfromsignup2 = getIntent().getStringExtra("address2");
         postcodefromsignup2 = getIntent().getStringExtra("postcode2");
         statefromsignup2 = getIntent().getStringExtra("state2");
         passwordfromsignup2 = getIntent().getStringExtra("password2");

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Signup2nd.class);
                intent.putExtra("invicodefromsignup3", invicodefromsignup2);
                intent.putExtra("name", namefromsignup2);
                intent.putExtra("email", emailfromsignup2);
                intent.putExtra("NRIC", nricfromsignup2);
                intent.putExtra("address", addressfromsignup2);
                intent.putExtra("postcode", postcodefromsignup2);
                intent.putExtra("state", statefromsignup2);
                intent.putExtra("password", passwordfromsignup2);
                startActivity(intent);
                finish();
            }
        });
        //fullName = getIntent().getStringExtra("name2");
        //email = getIntent().getStringExtra("email2");
        //NRIC = getIntent().getStringExtra("nric2");
        //address = getIntent().getStringExtra("address2");
        //postCode = getIntent().getStringExtra("postcode2");
        //state = getIntent().getStringExtra("state2");
        //password = getIntent().getStringExtra("password2");
        //gender = getIntent().getStringExtra("gender2");
        //age = getIntent().getStringExtra("date2");
        //String user_enter_phonenumber = phoneNumber.getEditText().getText().toString().trim();
        //phoneNo = "+" +countryCodePicker.getFullNumber()+user_enter_phonenumber;

    }

    public void callVerifyScreen(View view) {

        if (!validatePhoneNumber()) {
            return;
        }

        String name3 = getIntent().getStringExtra("name2");
        String email3 = getIntent().getStringExtra("email2");
        String nric3 = getIntent().getStringExtra("NRIC2");
        String address3 = getIntent().getStringExtra("address2");
        String postcode3 = getIntent().getStringExtra("postcode2");
        String state3 = getIntent().getStringExtra("state2");
        String password3 = getIntent().getStringExtra("password2");
        String invitationcode3 = getIntent().getStringExtra("invitationcode2");
        String date3 = getIntent().getStringExtra("date2");

        String user_enter_phonenumber = phoneNumber.getEditText().getText().toString().trim();
        String phoneNumber = "+" + countryCodePicker.getSelectedCountryCode() + user_enter_phonenumber;

        String memberusername = name3 + " / "  + phoneNumber;

        Intent intent = new Intent(getApplicationContext(), SIgnUpOTP.class);

        intent.putExtra("name3", name3);
        intent.putExtra("email3", email3);
        intent.putExtra("nric3", nric3);
        intent.putExtra("address3", address3);
        intent.putExtra("postcode3", postcode3);
        intent.putExtra("state3", state3);
        intent.putExtra("password3", password3);
        intent.putExtra("invitationcode3", invitationcode3);
        intent.putExtra("date3", date3);
        intent.putExtra("phonenumber3", phoneNumber);
        intent.putExtra("memberusername" , memberusername);

        startActivity(intent);

    }


    private boolean validatePhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();
        //String checkspaces = "Aw{1,20}z";
        if (val.isEmpty()) {
            phoneNumber.setError("Enter valid phone number");
            return false;
        } //else if (!val.matches(checkspaces)) {
        //phoneNumber.setError("No White spaces are allowed!");
        //return false;
        // }
        else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }

    public void callLoginScreen(View view) {
        startActivity(new Intent(this, Login.class));
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), Signup2nd.class);
        intent.putExtra("invicodefromsignup3", invicodefromsignup2);
        intent.putExtra("name", namefromsignup2);
        intent.putExtra("email", emailfromsignup2);
        intent.putExtra("NRIC", nricfromsignup2);
        intent.putExtra("address", addressfromsignup2);
        intent.putExtra("postcode", postcodefromsignup2);
        intent.putExtra("state", statefromsignup2);
        intent.putExtra("password", passwordfromsignup2);
        startActivity(intent);
        finish();
    }


}

  /*  @Override
        public void onClick(View view) {
            switch(view.getId()){
            case R.id.signup_login_button:
            RegisterUser();
            break;
            }
       }

    private void RegisterUser() {
       FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
       DatabaseReference reference = rootNode.getReference("Users");

        reference.setValue("Hello");
        }
      }*/