package com.example.cafyp.PaymentMember;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cafyp.R;
import com.example.cafyp.User.Heirs.HeirControlBoard;
import com.example.cafyp.User.UserDashBoard;
import com.example.cafyp.session.SessionManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class CheckoutActivityJava extends AppCompatActivity {
    // 10.0.2.2 is the Android emulator's alias to localhost
    private static final String BACKEND_URL = "https://obscure-plains-94164.herokuapp.com/";
    private OkHttpClient httpClient = new OkHttpClient();
    private String paymentIntentClientSecret;
    private Stripe stripe;
    private TextView amountTextView;

    String paymentid = "";
    String userPhoneno = "";
    String amountString = "";

    ImageView homebtn, backbtn;


    String invicode;

    FirebaseDatabase checkoutdatabase;
    DatabaseReference checkoutref;

    FirebaseDatabase database;
    DatabaseReference reference;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_java);

        amountString = getIntent().getStringExtra("amount");
        paymentid = getIntent().getStringExtra("paymentid");
        userPhoneno = getIntent().getStringExtra("userphoneno");
        homebtn = findViewById(R.id.home_button);
        backbtn = findViewById(R.id.back_button);


        SessionManager sessionManager = new SessionManager(getApplicationContext(),SessionManager.SESSION_USERSESSION);
        HashMap<String, String> usersDetails = sessionManager.getUserDataDetailFromSession();
        invicode = usersDetails.get(SessionManager.KEY_CODE);
        String phonenumber = usersDetails.get(SessionManager.KEY_PHONENUMBER);

        amountTextView = findViewById(R.id.amountTextView);

        amountTextView.setText(amountString);


        // Configure the SDK with your Stripe publishable key so it can make requests to Stripe
        stripe = new Stripe(
                getApplicationContext(),
                Objects.requireNonNull("pk_test_51J0Kg9GUNz5EklpM7PSrIAnZ4i08lqjMRFDWu8LFmuk4QInNDauJX1Q4HyJjm9HOq6vvaF4Lg8sksP1sEBsyDrYo00ZoVEAMLb")
        );
        startCheckout();


        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutActivityJava.this);
                builder.setTitle("Are your sure?");
                builder.setMessage("This activity will cancel the Payment");

                builder.setPositiveButton("Home", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(getApplicationContext(), UserDashBoard.class);
                        startActivity(intent);
                        finish();

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

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutActivityJava.this);
                builder.setTitle("Are your sure?");
                builder.setMessage("This activity will cancel the Payment");

                builder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(getApplicationContext(), CheckOutPage.class);
                        startActivity(intent);
                        finish();

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
    }
    private void startCheckout() {
        // Create a PaymentIntent by calling the server's endpoint.
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        Double amount = Double.parseDouble(amountString) * 100;

        Map<String, Object> payMap = new HashMap<>();
        Map<String, Object> itemMap = new HashMap<>();
        List<Map<String, Object>> itemList = new ArrayList<>();
        payMap.put("currency", "usd"); //dont change the currency in testing phase otherwise it wont work
        itemMap.put("id","photo_subscription");
        itemMap.put("amount",amount);
        itemList.add(itemMap);
        payMap.put("items",itemList);
        String json = new Gson().toJson(payMap);

        RequestBody body = RequestBody.create(json, mediaType);
        Request request = new Request.Builder()
                .url(BACKEND_URL + "create-payment-intent")
                .post(body)
                .build();
        httpClient.newCall(request)
                .enqueue(new PayCallback(this));
        // Hook up the pay button to the card widget and stripe instance
        Button payButton = findViewById(R.id.payButton);
        payButton.setOnClickListener((View view) -> {
            CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
            PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
            if (params != null) {
                ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                        .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                stripe.confirmPayment(this, confirmParams);
            }
        });
    }
    private void displayAlert(@NonNull String title,
                              @Nullable String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);
        builder.setPositiveButton("Ok", null);
        builder.create().show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }
    private void onPaymentSuccess(@NonNull final Response response) throws IOException {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> responseMap = gson.fromJson(
                Objects.requireNonNull(response.body()).string(),
                type
        );
        paymentIntentClientSecret = responseMap.get("clientSecret");
    }
    private static final class PayCallback implements Callback {
        @NonNull private final WeakReference<CheckoutActivityJava> activityRef;
        PayCallback(@NonNull CheckoutActivityJava activity) {
            activityRef = new WeakReference<>(activity);
        }
        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            final CheckoutActivityJava activity = activityRef.get();
            if (activity == null) {
                return;
            }
            activity.runOnUiThread(() ->
                    Toast.makeText(
                            activity, "Error: " + e.toString(), Toast.LENGTH_LONG
                    ).show()
            );
        }
        @Override
        public void onResponse(@NonNull Call call, @NonNull final Response response)
                throws IOException {
            final CheckoutActivityJava activity = activityRef.get();
            if (activity == null) {
                return;
            }
            if (!response.isSuccessful()) {
                activity.runOnUiThread(() ->
                        Toast.makeText(
                                activity, "Error: " + response.toString(), Toast.LENGTH_LONG
                        ).show()
                );
            } else {
                activity.onPaymentSuccess(response);
            }
        }
    }

    private static final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull private final WeakReference<CheckoutActivityJava> activityRef;
        PaymentResultCallback(@NonNull CheckoutActivityJava activity) {
            activityRef = new WeakReference<>(activity);
        }


        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final CheckoutActivityJava activity = activityRef.get();
            if (activity == null) {
                return;
            }
            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                /*activity.displayAlert(
                        "Payment completed",
                        gson.toJson(paymentIntent)
                );*/
                Toast.makeText(activity,"Paid successfully!",Toast.LENGTH_SHORT).show();

                Map<String,String> mMap = new HashMap<>();
                mMap.put("paymentid", activity.paymentid);
                mMap.put("paymentuserno", activity.userPhoneno);
                mMap.put("paymentamount", activity.amountString);

                activity.checkoutdatabase = FirebaseDatabase.getInstance();
                activity.checkoutref = activity.checkoutdatabase.getReference(activity.invicode).child("PaymentChecked");

                activity.database = FirebaseDatabase.getInstance();
                activity.reference = activity.database.getReference(activity.invicode).child("PaymentDone");


                activity.checkoutref.child(activity.userPhoneno).child(activity.paymentid).setValue(mMap);
                activity.reference.child(activity.userPhoneno + activity.paymentid).setValue(mMap);

                Intent intent = new Intent(activity, MemberViewPayment.class);
                activity.startActivity(intent);
                activity.finish();
                // Payment completed successfully

            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed – allow retrying using a different payment method
                activity.displayAlert(
                        "Payment failed",
                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage()
                );
            }
        }

        @Override
        public void onError(@NonNull Exception e) {
            final CheckoutActivityJava activity = activityRef.get();
            if (activity == null) {
                return;
            }
            // Payment request failed – allow retrying using the same payment method
            activity.displayAlert("Error", e.toString());
        }
    }
}