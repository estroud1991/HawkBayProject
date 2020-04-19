 package com.stroud.hawkbay.model;

import com.google.firebase.firestore.IgnoreExtraProperties;


@IgnoreExtraProperties
public class Listing {

    public static final String FIELD_PRICE = "price";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_EMAIL = "email";

    private String name;
    private String description;
    private String price;
    private String sellerEmail;

    public Listing() {}

    public Listing(String name,String description, String price, String sellerEmail) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.sellerEmail = sellerEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSellerEmail(){return sellerEmail;}

    public void setSellerEmail(String email){
        this.sellerEmail = email;
    }

}
