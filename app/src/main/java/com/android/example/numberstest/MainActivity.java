package com.android.example.numberstest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.example.numberstest.helpers.network.NetworkClient;
import com.android.example.numberstest.helpers.storage.ContactEntry;
import com.android.example.numberstest.helpers.storage.StorageService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final BroadcastReceiver updateListReceiver = new UpdateListBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final ListView listview = (ListView) findViewById(R.id.contacts_list);
        listview.setAdapter(new ContactsArrayAdapter(this, R.layout.contact_item));
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(updateListReceiver, new IntentFilter(UpdateListBroadcastReceiver.ACTION_UPDATE_LIST_FROM_DB));
        fetchData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(updateListReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {
        String url = getResources().getString(R.string.server_url);
        NetworkClient.get(this, url);
    }

    public class UpdateListBroadcastReceiver extends BroadcastReceiver {
        public static final String ACTION_UPDATE_LIST_FROM_DB = "ACTION_UPDATE_LIST_FROM_DB";
        public static final String ACTION_UPDATE_LIST_FROM_EXTRA = "ACTION_UPDATE_LIST_FROM_EXTRA";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_UPDATE_LIST_FROM_DB.equals(intent.getAction())) {
                Intent saveIntent = new Intent(context, StorageService.class);
                saveIntent.setAction(StorageService.ACTION_STORAGE_SERVICE_READ);
                context.startService(saveIntent);
            } else if (ACTION_UPDATE_LIST_FROM_EXTRA.equals(intent.getAction())) {
                ArrayList<ContactEntry> arrayList = (ArrayList<ContactEntry>) intent.getSerializableExtra(StorageService.EXTRA_CONTACTS_LIST);
                    updateListView(arrayList) ;
            }
        }
    }

    private void updateListView(ArrayList<ContactEntry> arrayList) {

    }

    private class ContactsArrayAdapter extends ArrayAdapter {

        public ContactsArrayAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
        }
    }
}
