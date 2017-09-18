package com.android.example.numberstest.helpers.network;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.example.numberstest.helpers.storage.StorageService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

class GetRequest {
    private static final String TAG = GetRequest.class.getSimpleName();

    private void run(final Context context, String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.connectTimeout(20, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(20, TimeUnit.SECONDS);
        clientBuilder.readTimeout(20, TimeUnit.SECONDS);

        OkHttpClient client = clientBuilder.build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Failure with data receiving.", e);
//                onFailure();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                if (response.code() != 200) {
                    Log.i(TAG, "Error with data receiving. Code: " + response.code());
                    return;
                }

                ResponseBody body = response.body();
                if (body != null) {
                    String json = body.string();
                    Log.i(TAG, json);

                    processResponse(json);
                } else {
                    Log.i(TAG, "Error: Response body is null");
//                    onFailure();
                }
            }

            void processResponse(String json) {
                Log.i(TAG, "Processing response...");
                Intent saveIntent = new Intent(context, StorageService.class);
                saveIntent.setAction(StorageService.ACTION_STORAGE_SERVICE_SAVE);
                saveIntent.putExtra(StorageService.EXTRA_RESPONSE_JSON, json);
                context.startService(saveIntent);
            }
//
//            void onFailure() {
//                switch (mType) {
//                    case NetworkClient.GET_CONFIGURATION:
//                        EventBus.getDefault().post(new EventActivation(EventActivation.UNSUCCESSFUL));
//                        break;
//                    default:
//                        break;
//                }
//            }
        });
    }

    static void makeGet(Context context, String url) throws IOException {
        Log.i(TAG, "URL= " + url);
        if (!ConnectionHelper.isConnected(context)) {
            Log.i(TAG, "No internet connection");
            return;
        }
        GetRequest getRequest = new GetRequest();
        getRequest.run(context, url);
    }
}
