package com.example.cafyp.domain;

public class Calculate {
    String paymentid;

    public Calculate() {
    }

    public Calculate(String paymentid) {
        this.paymentid = paymentid;
    }

    public String getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(String paymentid) {
        this.paymentid = paymentid;
    }
}
