package com.example.cafyp.session;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManagerAdmin {

    //variables
    SharedPreferences adminSession;
    SharedPreferences.Editor admineditor;
    Context admincontext;

    public static final String SESSION_ADMINSESSION = "adminLoginSession";
    public static final String SESSION_ADMINREMEMBERME = "adminrememberMe";

    private static final String IS_ADMINLOGIN = "IsLoggedIn";

    public static final String KEY_ADMINNAME = "name";
    public static final String KEY_ADMINUSERNAME = "username";
    public static final String KEY_ADMINIC = "ic";
    public static final String KEY_ADMINPHONENO = "phoneno";
    public static final String KEY_ADMINEMAIL = "email";
    public static final String KEY_ADMININVITATIONCODE = "invitationcode";
    public static final String KEY_ADMINPASSWORD = "password";
    public static final String KEY_ADMINCREATEDATE = "date";
    public static final String KEY_ADMINSTATE = "state";


    private static final String IS_ADMINREMEMBERME = "IsRememberMe";
    public static final String KEY_ADMINREMEMBERMEUSERNAME = "username";
    public static final String KEY_ADMINREMEMBERMEPASSWORD = "password";
    public static final String KEY_ADMINREMEMBERMESTATE = "state";

    public SessionManagerAdmin(Context _admincontext, String sessionName){

        admincontext = _admincontext;
        adminSession = admincontext.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        admineditor = adminSession.edit();
    }

    public void createAdminLoginSession(String username, String name, String password, String ic, String email, String phoneno, String invitationcode, String date, String state){

        admineditor.putBoolean(IS_ADMINLOGIN, true);

        admineditor.putString(KEY_ADMINUSERNAME, username);
        admineditor.putString(KEY_ADMINNAME, name);
        admineditor.putString(KEY_ADMINIC, ic);
        admineditor.putString(KEY_ADMINPHONENO, phoneno);
        admineditor.putString(KEY_ADMINEMAIL, email);
        admineditor.putString(KEY_ADMININVITATIONCODE, invitationcode);
        admineditor.putString(KEY_ADMINPASSWORD, password);
        admineditor.putString(KEY_ADMINCREATEDATE, date);
        admineditor.putString(KEY_ADMINSTATE, state);

        admineditor.commit();
    }

    public HashMap<String, String> getAdminDataDetailFromSession(){

        HashMap<String, String> adminData = new HashMap<String, String>();

        adminData.put(KEY_ADMINNAME, adminSession.getString(KEY_ADMINNAME, null));
        adminData.put(KEY_ADMINUSERNAME, adminSession.getString(KEY_ADMINUSERNAME, null));
        adminData.put(KEY_ADMINIC, adminSession.getString(KEY_ADMINIC, null));
        adminData.put(KEY_ADMINPHONENO, adminSession.getString(KEY_ADMINPHONENO, null));
        adminData.put(KEY_ADMINEMAIL, adminSession.getString(KEY_ADMINEMAIL, null));
        adminData.put(KEY_ADMININVITATIONCODE, adminSession.getString(KEY_ADMININVITATIONCODE, null));
        adminData.put(KEY_ADMINPASSWORD, adminSession.getString(KEY_ADMINPASSWORD, null));
        adminData.put(KEY_ADMINCREATEDATE, adminSession.getString(KEY_ADMINCREATEDATE, null));
        adminData.put(KEY_ADMINSTATE, adminSession.getString(KEY_ADMINSTATE, null));

        return adminData;

    }

    public boolean checkAdminLogin(){
        if(adminSession.getBoolean(IS_ADMINLOGIN, false)){
            return true;
        }else
            return false;
    }

    public void logoutAdminFromSession(){
        admineditor.clear();
        admineditor.commit();
    }

    public void createAdminRememberSession(String username, String password, String state){

        admineditor.putBoolean(IS_ADMINREMEMBERME, true);
        admineditor.putString(KEY_ADMINREMEMBERMEUSERNAME, username);
        admineditor.putString(KEY_ADMINREMEMBERMEPASSWORD, password);
        admineditor.putString(KEY_ADMINREMEMBERMESTATE, state);

        admineditor.commit();
    }

    public HashMap<String, String> getAdminRememberDetailFromSession(){

        HashMap<String, String> adminData = new HashMap<String, String>();

        adminData.put(KEY_ADMINREMEMBERMEUSERNAME, adminSession.getString(KEY_ADMINREMEMBERMEUSERNAME, null));
        adminData.put(KEY_ADMINREMEMBERMEPASSWORD, adminSession.getString(KEY_ADMINREMEMBERMEPASSWORD, null));
        adminData.put(KEY_ADMINREMEMBERMESTATE, adminSession.getString(KEY_ADMINREMEMBERMESTATE, null));

        return adminData;

    }

    public boolean checkAdminRemember(){
        if(adminSession.getBoolean(IS_ADMINREMEMBERME, false)){
            return true;
        }else
            return false;
    }

    public void logoutAdminFromRememberMe() {
        admineditor.clear();
        admineditor.commit();
    }

}
