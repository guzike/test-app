package com.android.example.numberstest.helpers.storage;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class RealmEntry extends RealmObject {
//
//    @Index
//    private int id;

    @PrimaryKey
    private String phoneNumber;

    @Required
    private String phoneNumberPrice;

    @Required
    private String phoneNumberOwner;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumberPrice() {
        return phoneNumberPrice;
    }

    public void setPhoneNumberPrice(String phoneNumberPrice) {
        this.phoneNumberPrice = phoneNumberPrice;
    }

    public String getPhoneNumberOwner() {
        return phoneNumberOwner;
    }

    public void setPhoneNumberOwner(String phoneNumberOwner) {
        this.phoneNumberOwner = phoneNumberOwner;
    }
}
