package com.example.cafyp.PaymentMember;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafyp.User.UserDashBoard;
import com.example.cafyp.session.SessionManager;
import com.example.cafyp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CheckOutPage extends AppCompatActivity implements PaymentResultListener {

    TextView paymentname, paymentcost;
    String getid;
    FirebaseDatabase cdatabase;
    DatabaseReference cref;
    Button checkoutbtn, paybyreceipt;
    String amount;
    String phonenumber;
    String invicode;

    ImageView homebtn, backbtn;
    Button razorbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_page);

        paymentname = findViewById(R.id.checkout_payment_name);
        paymentcost = findViewById(R.id.checkout_payment_cost);
        razorbtn = findViewById(R.id.check_out_by_razor);

        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);

        Checkout.preload(getApplicationContext());

        checkoutbtn = findViewById(R.id.check_out_btn);
        //paybyreceipt = findViewById(R.id.payby_receipt_btn);

        SessionManager sessionManager = new SessionManager(getApplicationContext(),SessionManager.SESSION_USERSESSION);
        HashMap<String, String> usersDetails = sessionManager.getUserDataDetailFromSession();
        invicode = usersDetails.get(SessionManager.KEY_CODE);
        phonenumber = usersDetails.get(SessionManager.KEY_PHONENUMBER);

        Intent intent = getIntent();
        getid = intent.getStringExtra("paymentid");


        cdatabase = FirebaseDatabase.getInstance();
        cref = cdatabase.getReference(invicode).child("Payments").child(getid);

        cref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String _paymentname = snapshot.child("pname").getValue().toString();
                String _paymentcost = snapshot.child("pcost").getValue().toString();

                Double amount1 = Double.valueOf(_paymentcost);
                amount = _paymentcost;
                paymentname.setText(_paymentname);
                paymentcost.setText("" + amount1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        checkoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CheckoutActivityJava.class);
                intent.putExtra("amount" ,amount);
                intent.putExtra("userphoneno", phonenumber);
                intent.putExtra("paymentid" , getid);
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
                Intent intent = new Intent(getApplicationContext(), MemberPaymentControl.class);
                startActivity(intent);
                finish();
            }
        });

       /* paybyreceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UploadReceipt.class);
                startActivity(intent);
            }
        });*/

        razorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });

    }

    public void startPayment() {

        Checkout checkout = new Checkout();

        final Activity activity = CheckOutPage.this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "EKhairat");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            //options.put("theme.color", "#3399cc");
            options.put("currency", "MYR");

            double total = Double.parseDouble(paymentcost.getText().toString());
            total = total * 100;
            options.put("amount", total);//pass amount in currency subunits
            JSONObject preFill = new JSONObject();
            preFill.put("prefill.email", "xna5125@gmail.com");
            preFill.put("prefill.contact","+601128692488");
            //retryObj.put("enabled", true);
            //retryObj.put("max_count", 4);
            options.put("prefill", preFill);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this,"PaymentSuccessful", Toast.LENGTH_SHORT).show();

        Map<String,String> mMap = new HashMap<>();
        mMap.put("paymentid", getid);
        mMap.put("paymentuserno", phonenumber);
        mMap.put("paymentamount", amount);

        FirebaseDatabase checkoutdatabase = FirebaseDatabase.getInstance();
        DatabaseReference checkoutref = checkoutdatabase.getReference(invicode).child("PaymentChecked");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(invicode).child("PaymentDone");


        checkoutref.child(phonenumber).child(getid).setValue(mMap);
        reference.child(phonenumber + getid).setValue(mMap);

        Intent intent = new Intent(this, MemberViewPayment.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this,"PaymentFailed", Toast.LENGTH_SHORT).show();

    }
}