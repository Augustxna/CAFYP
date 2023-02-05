package com.example.cafyp.Database;

public class UserHelpherClass {

    String fullName, email, NRIC, address, postCode, state, password, invitationcode, date, phoneNo, memberusername;

    public UserHelpherClass() {
    }

    public UserHelpherClass(String fullName, String email, String NRIC, String address, String postCode, String state, String password, String invitationcode, String date, String phoneNo, String memberusername) {
        this.fullName = fullName;
        this.email = email;
        this.NRIC = NRIC;
        this.address = address;
        this.postCode = postCode;
        this.state = state;
        this.password = password;
        this.invitationcode = invitationcode;
        this.date = date;
        this.phoneNo = phoneNo;
        this.memberusername = memberusername;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNRIC() {
        return NRIC;
    }

    public void setNRIC(String NRIC) {
        this.NRIC = NRIC;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInvitationcode() {
        return invitationcode;
    }

    public void setInvitationcode(String invitationcode) {
        this.invitationcode = invitationcode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getMemberusername() {
        return memberusername;
    }

    public void setMemberusername(String memberusername) {
        this.memberusername = memberusername;
    }
}
