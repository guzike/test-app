package com.android.example.numberstest.helpers.network;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

public class NetworkClient {
    private static final String TAG = NetworkClient.class.getSimpleName();

    private NetworkClient() {
    }

    public static void get(Context context, String url) {
        if (!ConnectionHelper.isConnected(context)) {
            Log.i(TAG, "No internet for GET Request with\nURL= " + url);
            return;
        }
        try {
            GetRequest.makeGet(context, url);
        } catch (IOException e) {
            Log.e(TAG, "Error while make GET", e);
        }
    }
}
