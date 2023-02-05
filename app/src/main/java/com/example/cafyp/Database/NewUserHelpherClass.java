package com.example.cafyp.Database;

public class NewUserHelpherClass {

    String fullName, nric, email, phoneno, birth, address, password, invitationcode, resadminid, memberusername;

    public NewUserHelpherClass() {
    }

    public NewUserHelpherClass(String fullName, String nric, String email, String phoneno, String birth, String address, String password, String invitationcode, String resadminid, String memberusername) {
        this.fullName = fullName;
        this.nric = nric;
        this.email = email;
        this.phoneno = phoneno;
        this.birth = birth;
        this.address = address;
        this.password = password;
        this.invitationcode = invitationcode;
        this.resadminid = resadminid;
        this.memberusername = memberusername;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNric() {
        return nric;
    }

    public void setNric(String nric) {
        this.nric = nric;
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

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getResadminid() {
        return resadminid;
    }

    public void setResadminid(String resadminid) {
        this.resadminid = resadminid;
    }

    public String getMemberusername() {
        return memberusername;
    }

    public void setMemberusername(String memberusername) {
        this.memberusername = memberusername;
    }
}
