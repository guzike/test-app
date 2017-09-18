package com.android.example.numberstest.helpers.storage;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class DbHelper {

    private DbHelper() {
    }

    public static void writeListToDb(final List<ContactEntry> entryList) {
        Realm realmInstance = Realm.getDefaultInstance();
        for (ContactEntry entry : entryList) {
            realmInstance.executeTransaction(realm -> {
                RealmEntry realmEntry = realm.createObject(RealmEntry.class);
                realmEntry.setPhoneNumber(entry.getPhoneNumber());
                realmEntry.setPhoneNumberPrice(entry.getPhoneNumberPrice());
                realmEntry.setPhoneNumberOwner(entry.getPhoneNumberOwner());
            });
        }
        closeDB(realmInstance);
    }

    public static ArrayList<ContactEntry> getListFromDb() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmEntry> results = realm.where(RealmEntry.class).findAll();

        ArrayList<ContactEntry> entryList = new ArrayList<>();

        Stream.of(results)
                .map(ContactEntry::new)
                .collect(Collectors.toList());

        closeDB(realm);

        return entryList;
    }

    private static void closeDB(Realm realm) {
        if (realm != null && !realm.isClosed()) {
            realm.close();
        }
    }
}
