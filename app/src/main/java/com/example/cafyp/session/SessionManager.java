 package com.example.cafyp.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cafyp.adapter.MessageAdapter;

import java.nio.file.FileAlreadyExistsException;
import java.util.HashMap;

public class SessionManager {

    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    //session name
    public static final String SESSION_USERSESSION = "userLoginSession";
    public static final String SESSION_REMEMBERME = "rememberMe";

    //member session variables
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_FULLNAME = "fullName";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_NRIC = "nric";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_BIRTH = "Birth";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_RESADMINID = "resadminid";
    public static final String KEY_CODE = "invitationcode";
    public static final String KEY_PHONENUMBER = "phoneno";
    public static final String KEY_MEMEBRUSERNAME ="memberusername";

    //remember me variables
    private static final String IS_REMEMBERME = "IsRememberMe";
    public static final String KEY_REMEMBERMEPHONENUMBER = "phoneno";
    public static final String KEY_REMEMBERMEPASSWORD = "password";


    //Constructor
    public SessionManager(Context _context, String sessionName) {

        context = _context;
        userSession = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        editor = userSession.edit();
    }


    //Member Login Session
    public void createLoginSession(String fullName, String email, String nric,String date, String address, String password, String resadminid, String phoneno, String invitationcode, String memberusername) {

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_FULLNAME, fullName);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_NRIC, nric);
        editor.putString(KEY_ADDRESS, address);
        editor.putString(KEY_BIRTH, date);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_RESADMINID, resadminid);
        editor.putString(KEY_CODE, invitationcode);
        editor.putString(KEY_PHONENUMBER, phoneno);
        editor.putString(KEY_MEMEBRUSERNAME, memberusername);

        editor.commit();
    }

    public HashMap<String, String> getUserDataDetailFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_FULLNAME, userSession.getString(KEY_FULLNAME, null));
        userData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL, null));
        userData.put(KEY_NRIC, userSession.getString(KEY_NRIC, null));
        userData.put(KEY_ADDRESS, userSession.getString(KEY_ADDRESS, null));
        userData.put(KEY_BIRTH, userSession.getString(KEY_BIRTH, null));
        userData.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD, null));
        userData.put(KEY_RESADMINID, userSession.getString(KEY_RESADMINID, null));
        userData.put(KEY_CODE, userSession.getString(KEY_CODE, null));
        userData.put(KEY_PHONENUMBER, userSession.getString(KEY_PHONENUMBER, null));
        userData.put(KEY_MEMEBRUSERNAME, userSession.getString(KEY_MEMEBRUSERNAME, null));

        return userData;

    }

    public boolean checkLogin() {
        if (userSession.getBoolean(IS_LOGIN, false)) {
            return true;
        } else
            return false;
    }

    public void logoutUserFromSession() {
        editor.clear();
        editor.commit();
}


    //Remember me session
    public void createRememberSession(String phoneno, String password) {

        editor.putBoolean(IS_REMEMBERME, true);
        editor.putString(KEY_REMEMBERMEPHONENUMBER, phoneno);
        editor.putString(KEY_REMEMBERMEPASSWORD, password);

        editor.commit();
    }

    public HashMap<String, String> getRememberMeDetailFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_REMEMBERMEPHONENUMBER, userSession.getString(KEY_REMEMBERMEPHONENUMBER, null));
        userData.put(KEY_REMEMBERMEPASSWORD, userSession.getString(KEY_REMEMBERMEPASSWORD, null));


        return userData;

    }

    public boolean checkRememberMe() {
        if (userSession.getBoolean(IS_REMEMBERME, false)) {
            return true;
        } else
            return false;
    }


    public void logoutUserFromRememberMe() {
        editor.clear();
        editor.commit();
    }

}
