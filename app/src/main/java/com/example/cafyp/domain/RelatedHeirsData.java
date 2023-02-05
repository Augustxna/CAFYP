package com.example.cafyp.domain;

public class RelatedHeirsData {

    String name, ic, email, phoneno, relationship;

    public RelatedHeirsData() {
    }

    public RelatedHeirsData(String name, String ic, String email, String phoneno, String relationship) {
        this.name = name;
        this.ic = ic;
        this.email = email;
        this.phoneno = phoneno;
        this.relationship = relationship;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
}
