package com.example.cafyp.Database;

public class PaymentHelpherClass {

    String pid, pcost, pname, pdescription, pdate;

    public PaymentHelpherClass() {
    }

    public PaymentHelpherClass(String pid, String pcost, String pname, String pdescription, String pdate) {
        this.pid = pid;
        this.pcost = pcost;
        this.pname = pname;
        this.pdescription = pdescription;
        this.pdate = pdate;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPcost() {
        return pcost;
    }

    public void setPcost(String pcost) {
        this.pcost = pcost;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPdescription() {
        return pdescription;
    }

    public void setPdescription(String pdescription) {
        this.pdescription = pdescription;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }
}
