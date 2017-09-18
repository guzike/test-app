package com.android.example.numberstest.helpers.storage;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.android.example.numberstest.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class StorageService extends IntentService {
    public static final String TAG = StorageService.class.getSimpleName();
    public static final String ACTION_STORAGE_SERVICE_SAVE = "ACTION_STORAGE_SERVICE_SAVE";
    public static final String ACTION_STORAGE_SERVICE_READ = "ACTION_STORAGE_SERVICE_READ";
    public static final String EXTRA_RESPONSE_JSON = "EXTRA_RESPONSE_JSON";
    public static final String EXTRA_CONTACTS_LIST = "EXTRA_CONTACTS_LIST";

    public StorageService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) {
            return;
        }
        if (ACTION_STORAGE_SERVICE_SAVE.equals(intent.getAction())) {
            ArrayList<ContactEntry> list = getDataEntryList(intent);
            if (list != null && !list.isEmpty()) {
                DbHelper.writeListToDb(list);
                Intent broadcastIntent = new Intent(MainActivity.UpdateListBroadcastReceiver.ACTION_UPDATE_LIST_FROM_DB);
                LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
            }
        } else if (ACTION_STORAGE_SERVICE_READ.equals(intent.getAction())) {
            ArrayList<ContactEntry> list = DbHelper.getListFromDb();
            Intent broadcastIntent = new Intent(MainActivity.UpdateListBroadcastReceiver.ACTION_UPDATE_LIST_FROM_EXTRA);
            broadcastIntent.putExtra(EXTRA_CONTACTS_LIST, list);
            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
        }
    }

    private ArrayList<ContactEntry> getDataEntryList(Intent intent) {
        String phoneNumbersList = intent.getStringExtra(EXTRA_RESPONSE_JSON);
        if (phoneNumbersList != null) {
            Gson gson = new GsonBuilder().create();
            TypeToken<ArrayList<ContactEntry>> token = new TypeToken<ArrayList<ContactEntry>>() {
            };
            return gson.fromJson(phoneNumbersList, token.getType());
        }
        return null;
    }
}
