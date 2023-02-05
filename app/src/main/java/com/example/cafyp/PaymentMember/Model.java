package com.example.cafyp.PaymentMember;

public class Model {
    private String ImageUrl;

    public Model() {
    }

    public Model(String imageUrl) {
        this.ImageUrl = imageUrl;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
