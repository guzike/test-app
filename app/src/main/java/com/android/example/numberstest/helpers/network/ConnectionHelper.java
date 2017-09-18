package com.android.example.numberstest.helpers.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

class ConnectionHelper {

    private static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    static boolean isConnected(Context context) {
        NetworkInfo info = ConnectionHelper.getNetworkInfo(context);
        return (info != null && info.isConnected());
    }
}
