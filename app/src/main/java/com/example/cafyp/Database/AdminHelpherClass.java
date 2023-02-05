package com.example.cafyp.Database;

public class AdminHelpherClass {
    String name,username, ic, email, phoneno, password, date, state, invitationcode;

    public void AdminHelpherClass(){};


    public AdminHelpherClass(String name, String username, String ic, String email, String phoneno, String password, String date, String state, String invitationcode) {
        this.name = name;
        this.username = username;
        this.ic = ic;
        this.email = email;
        this.phoneno = phoneno;
        this.password = password;
        this.date = date;
        this.state = state;
        this.invitationcode = invitationcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getInvitationcode() {
        return invitationcode;
    }

    public void setInvitationcode(String invitationcode) {
        this.invitationcode = invitationcode;
    }
}
