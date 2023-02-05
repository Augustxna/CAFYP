package com.example.cafyp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafyp.Common.LoginSignUp.Login;
import com.example.cafyp.Common.MainActivity;
import com.example.cafyp.AdminMain.MainAdminDashBoard;
import com.example.cafyp.PaymentAdmin.PaymentDashboard;
import com.example.cafyp.R;
import com.example.cafyp.session.SessionManager;
import com.example.cafyp.session.SessionManagerAdmin;
import com.google.android.material.textfield.TextInputLayout;
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

public class AdminLogin extends AppCompatActivity {

    TextInputLayout Adminusername, Adminpassword;
    RelativeLayout progressbar;
    TextView choosestate;
    ArrayList<String> arrayList;
    Dialog dialog;

    String invicode;
    EditText adminusernameedittext, adminpasswordedittext;

    CheckBox rememberme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        Adminusername = findViewById(R.id.admin_login_username);
        Adminpassword = findViewById(R.id.admin_login_password);
        progressbar = findViewById(R.id.login_progressbar);
        choosestate = findViewById(R.id.state_chooser);
        rememberme = findViewById(R.id.rememberme_checkbox);
        adminusernameedittext = findViewById(R.id.adminusername_edittext);
        adminpasswordedittext = findViewById(R.id.adminpassword_edittext);

        progressbar.setVisibility(View.GONE);

        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(AdminLogin.this, SessionManagerAdmin.SESSION_ADMINREMEMBERME);
        if(sessionManagerAdmin.checkAdminRemember()){
            HashMap<String,String> remmeberAdminDetails = sessionManagerAdmin.getAdminRememberDetailFromSession();
            adminusernameedittext.setText(remmeberAdminDetails.get(SessionManagerAdmin.KEY_ADMINREMEMBERMEUSERNAME));
            adminpasswordedittext.setText(remmeberAdminDetails.get(SessionManagerAdmin.KEY_ADMINREMEMBERMEPASSWORD));
            choosestate.setText(remmeberAdminDetails.get(SessionManagerAdmin.KEY_ADMINREMEMBERMESTATE));
        }



        arrayList = new ArrayList<>();
        arrayList.add("ADMIN");
        arrayList.add("MAINADMIN");

        choosestate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new Dialog(AdminLogin.this);

                dialog.setContentView(R.layout.dialog_searchable_spinner);
                dialog.getWindow().setLayout(650, 600);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                EditText editText = dialog.findViewById(R.id.adminstate_edit_text);
                ListView listView = dialog.findViewById(R.id.adminstate_list_view);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(AdminLogin.this, android.R.layout.simple_list_item_1, arrayList);

                listView.setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        adapter.getFilter().filter(s);

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        choosestate.setText(adapter.getItem(position));
                        dialog.dismiss();
                    }
                });

            }
        });


    }

    public void ToUserLoginPage(View view) {
        Intent intent = new Intent(AdminLogin.this, Login.class);
        startActivity(intent);
    }

    public void BacktoMain(View view) {
        Intent intent = new Intent(AdminLogin.this, Login.class);
        startActivity(intent);
    }

    public void loginToAdminDashBoard(View view) {

        progressbar.setVisibility(View.VISIBLE);

        String _adminname = Adminusername.getEditText().getText().toString();
        String _password = Adminpassword.getEditText().getText().toString();
        String _adminstate = choosestate.getText().toString();


        if(rememberme.isChecked()){

            SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(AdminLogin.this, SessionManagerAdmin.SESSION_ADMINREMEMBERME);
            sessionManagerAdmin.createAdminRememberSession(_adminname,_password,_adminstate);
        }

        if (_adminstate.equals("MAINADMIN")) {

            Query reference = FirebaseDatabase.getInstance().getReference("Mainadmin").orderByChild("username").equalTo(_adminname);

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                    if (datasnapshot.exists()) {

                        String _code = datasnapshot.child(_adminname).child("invitationcode").getValue().toString();
                        invicode = _code;

                        FirebaseDatabase realdatabase = FirebaseDatabase.getInstance();
                        Query checkadmin = realdatabase.getInstance().getReference(invicode).child("Mainadmin").orderByChild("username").equalTo(_adminname);
                        checkadmin.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    Adminusername.setError(null);
                                    Adminusername.setErrorEnabled(false);

                                    String adminpassword = snapshot.child(_adminname).child("password").getValue(String.class);
                                    if (adminpassword.equals(_password)) {
                                        Adminpassword.setError(null);
                                        Adminpassword.setErrorEnabled(false);

                                        String _adminusername = snapshot.child(_adminname).child("username").getValue(String.class);
                                        String _adminfullname = snapshot.child(_adminname).child("name").getValue(String.class);
                                        String _adminpassword = snapshot.child(_adminname).child("password").getValue(String.class);
                                        String _adminic = snapshot.child(_adminname).child("ic").getValue(String.class);
                                        String _adminemail = snapshot.child(_adminname).child("email").getValue(String.class);
                                        String _adminphoneno = snapshot.child(_adminname).child("phoneno").getValue(String.class);
                                        String _admincode = snapshot.child(_adminname).child("invitationcode").getValue(String.class);
                                        String _admincreatedate = snapshot.child(_adminname).child("date").getValue(String.class);
                                        String _adminstate = snapshot.child(_adminname).child("state").getValue(String.class);

                                        //Create a Session
                                        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(AdminLogin.this, SessionManagerAdmin.SESSION_ADMINSESSION);
                                        sessionManagerAdmin.createAdminLoginSession(_adminusername, _adminfullname, _adminpassword, _adminic, _adminemail, _adminphoneno, _admincode, _admincreatedate, _adminstate);


                                        Intent intent = new Intent(getApplicationContext(), MainAdminDashBoard.class);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(AdminLogin.this, _adminusername + "\n" + _admincode + "\n" + "Login", Toast.LENGTH_SHORT).show();

                                    } else {
                                        progressbar.setVisibility(View.GONE);
                                        Toast.makeText(AdminLogin.this, "wrong password!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    progressbar.setVisibility(View.GONE);
                                    Toast.makeText(AdminLogin.this, "wrong username", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                progressbar.setVisibility(View.GONE);
                                Toast.makeText(AdminLogin.this, "wrong id", Toast.LENGTH_SHORT).show();
                            }

                        });
                    } else {
                        progressbar.setVisibility(View.GONE);
                        Toast.makeText(AdminLogin.this, "wrong username", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(AdminLogin.this, "wrong id", Toast.LENGTH_SHORT).show();
                }
            });

        } else if (_adminstate.equals("ADMIN")) {
            Query reference = FirebaseDatabase.getInstance().getReference("Admins").orderByChild("username").equalTo(_adminname);

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                    if (datasnapshot.exists()) {

                        String _code = datasnapshot.child(_adminname).child("invitationcode").getValue().toString();
                        invicode = _code;

                        Query checkadmin = FirebaseDatabase.getInstance().getReference(invicode).child("Admins").orderByChild("username").equalTo(_adminname);
                        checkadmin.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    Adminusername.setError(null);
                                    Adminusername.setErrorEnabled(false);

                                    String adminpassword = snapshot.child(_adminname).child("password").getValue(String.class);
                                    if (adminpassword.equals(_password)) {
                                        Adminpassword.setError(null);
                                        Adminpassword.setErrorEnabled(false);

                                        String _adminusername = snapshot.child(_adminname).child("username").getValue(String.class);
                                        String _adminfullname = snapshot.child(_adminname).child("name").getValue(String.class);
                                        String _adminpassword = snapshot.child(_adminname).child("password").getValue(String.class);
                                        String _adminic = snapshot.child(_adminname).child("ic").getValue(String.class);
                                        String _adminemail = snapshot.child(_adminname).child("email").getValue(String.class);
                                        String _adminphoneno = snapshot.child(_adminname).child("phoneno").getValue(String.class);
                                        String _admincode = snapshot.child(_adminname).child("invitationcode").getValue(String.class);
                                        String _admincreatedate = snapshot.child(_adminname).child("date").getValue(String.class);
                                        String _adminstate = snapshot.child(_adminname).child("state").getValue(String.class);

                                        //Create a Session
                                        SessionManagerAdmin sessionManagerAdmin = new SessionManagerAdmin(AdminLogin.this, SessionManagerAdmin.SESSION_ADMINSESSION);
                                        sessionManagerAdmin.createAdminLoginSession(_adminusername, _adminfullname, _adminpassword, _adminic, _adminemail, _adminphoneno, _admincode, _admincreatedate, _adminstate);


                                        if (_adminstate.equals("mainadmin")) {
                                            Intent intent = new Intent(getApplicationContext(), MainAdminDashBoard.class);
                                            startActivity(intent);
                                            finish();
                                            Toast.makeText(AdminLogin.this, _adminusername + "\n" + _admincode + "\n" + "Login", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Intent intent = new Intent(getApplicationContext(), AdminDashDoard.class);
                                            startActivity(intent);
                                            finish();
                                            Toast.makeText(AdminLogin.this, _adminusername + "\n" + _admincode + "\n" + "Login", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        progressbar.setVisibility(View.GONE);
                                        Toast.makeText(AdminLogin.this, "wrong password!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    progressbar.setVisibility(View.GONE);
                                    Toast.makeText(AdminLogin.this, "wrong username", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                progressbar.setVisibility(View.GONE);
                                Toast.makeText(AdminLogin.this, "wrong id", Toast.LENGTH_SHORT).show();
                            }

                        });
                    } else {
                        progressbar.setVisibility(View.GONE);
                        Toast.makeText(AdminLogin.this, "wrong username", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(AdminLogin.this, "wrong id", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            progressbar.setVisibility(View.GONE);
            Toast.makeText(AdminLogin.this, "Please select your position", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }
}