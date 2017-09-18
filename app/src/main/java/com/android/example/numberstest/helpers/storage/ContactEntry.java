package com.android.example.numberstest.helpers.storage;

import java.io.Serializable;
import java.util.Map;

public class ContactEntry implements Serializable {
    private String phoneNumber;
    private String phoneNumberPrice;
    private String phoneNumberOwner;

    public ContactEntry() {
    }

    public ContactEntry(RealmEntry realmEntry) {
        this.phoneNumber = realmEntry.getPhoneNumber();
        this.phoneNumberPrice = realmEntry.getPhoneNumberPrice();
        this.phoneNumberOwner = realmEntry.getPhoneNumberOwner();
    }

    public ContactEntry(Map map) {
        this.phoneNumber = (String) map.get("phoneNumber");
        this.phoneNumberPrice = (String) map.get("phoneNumberPrice");
        this.phoneNumberOwner = (String) map.get("phoneNumberOwner");
    }

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
