package com.example.cafyp.Database;

public class PaymentUpdateHelpherClass {

    String pid, pname, pdescription, pcost, pdate;

    public PaymentUpdateHelpherClass() {
    }

    public PaymentUpdateHelpherClass(String pid, String pname, String pdescription, String pcost, String pdate) {
        this.pid = pid;
        this.pname = pname;
        this.pdescription = pdescription;
        this.pcost = pcost;
        this.pdate = pdate;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public String getPcost() {
        return pcost;
    }

    public void setPcost(String pcost) {
        this.pcost = pcost;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }
}
