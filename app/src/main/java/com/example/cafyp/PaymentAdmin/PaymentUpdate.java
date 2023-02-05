package com.example.cafyp.PaymentAdmin;

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
import com.example.cafyp.Admin.RelatedMemberView;
import com.example.cafyp.Database.PaymentUpdateHelpherClass;
import com.example.cafyp.R;
import com.example.cafyp.User.Heirs.HeirControlBoard;
import com.example.cafyp.session.SessionManagerAdmin;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class PaymentUpdate extends AppCompatActivity {

    TextView paymentupdateid, paymentupdatedate;
    TextInputLayout paymentupdatename, paymentupdatedes, paymentupdatecost;
    String getpaymentidfromcontrol;
    Button paymentupdatebtn;
    ImageView homebtn, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_update);

        paymentupdateid = findViewById(R.id.paymentid_updatepage);
        paymentupdatedate = findViewById(R.id.paymentcreatetime_updatepage);

        paymentupdatename = findViewById(R.id.paymentname_updatepage);
        paymentupdatedes = findViewById(R.id.paymentdescription_updatepage);
        paymentupdatecost = findViewById(R.id.paymentcost_updatepage);
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        paymentupdatebtn = findViewById(R.id.paymentupdate_updatepage_btn);

        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(this, SessionManagerAdmin.SESSION_ADMINSESSION);
        HashMap<String, String> adminDetails = sessionManagerAdmin.getAdminDataDetailFromSession();
        String admininvitationcode = adminDetails.get(SessionManagerAdmin.KEY_ADMININVITATIONCODE);
        String adminusername = adminDetails.get(SessionManagerAdmin.KEY_ADMINUSERNAME);

        getpaymentidfromcontrol = getIntent().getStringExtra("paymentid");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(admininvitationcode).child("Payments").child(getpaymentidfromcontrol);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                String pid = snapshot.child("pid").getValue().toString();
                String pname = snapshot.child("pname").getValue().toString();
                String pdes = snapshot.child("pdescription").getValue().toString();
                String pcost = snapshot.child("pcost").getValue().toString();
                String pcreatetime = snapshot.child("pdate").getValue().toString();

                paymentupdateid.setText(pid);
                paymentupdatedate.setText(pcreatetime);

                paymentupdatename.getEditText().setText(pname);
                paymentupdatedes.getEditText().setText(pdes);
                paymentupdatecost.getEditText().setText(pcost);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                Intent intent = new Intent(PaymentUpdate.this, ViewPayment.class);
                startActivity(intent);
                Toast.makeText(PaymentUpdate.this, "Getting Payment error", Toast.LENGTH_SHORT).show();
            }
        });

        paymentupdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(PaymentUpdate.this);
                builder.setTitle("Are your sure?");
                builder.setMessage("Updated data can't be Undo");

                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(!validatepaymentcost() | !validatepaymentdes() | !validatepaymentname()){
                            return;
                        }

                        String updatepaymentid = paymentupdateid.getText().toString().trim();
                        String updatepaymentdate = paymentupdatedate.getText().toString().trim();

                        String updatepaymentname = paymentupdatename.getEditText().getText().toString().trim();
                        String updatepaymentdes = paymentupdatedes.getEditText().getText().toString().trim();
                        String updatepaymentcost = paymentupdatecost.getEditText().getText().toString().trim();


                        FirebaseDatabase updatabase = FirebaseDatabase.getInstance();
                        DatabaseReference upref = updatabase.getReference(admininvitationcode).child("Payments");

                        PaymentUpdateHelpherClass newdata = new PaymentUpdateHelpherClass(updatepaymentid, updatepaymentname, updatepaymentdes, updatepaymentcost, updatepaymentdate);
                        upref.child(getpaymentidfromcontrol).setValue(newdata);

                        Toast.makeText(PaymentUpdate.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(getApplicationContext(),"Canceled", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();

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
                Intent intent = new Intent(getApplicationContext(), PaymentControl.class);
                intent.putExtra("paymentid", getpaymentidfromcontrol);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean validatepaymentname() {
        String val = paymentupdatename.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            paymentupdatename.setError("Field can not be empty");
            return false;
        }
        else {
            paymentupdatename.setError(null);
            paymentupdatename.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatepaymentcost() {
        String val = paymentupdatecost.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            paymentupdatecost.setError("Field can not be empty");
            return false;
        }
        else {
            paymentupdatecost.setError(null);
            paymentupdatecost.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatepaymentdes() {
        String val = paymentupdatedes.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            paymentupdatedes.setError("Field can not be empty");
            return false;
        }
        else {
            paymentupdatedes.setError(null);
            paymentupdatedes.setErrorEnabled(false);
            return true;
        }
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), PaymentControl.class);
        intent.putExtra("paymentid", getpaymentidfromcontrol);
        startActivity(intent);
        finish();
    }

}