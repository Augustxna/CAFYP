package com.example.cafyp.domain;

public class AllMemberData {

    String fullName, phoneno;

    public AllMemberData() {
    }

    public AllMemberData(String fullName, String phoneno) {
        this.fullName = fullName;
        this.phoneno = phoneno;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
}
