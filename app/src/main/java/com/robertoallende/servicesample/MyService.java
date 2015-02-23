package com.robertoallende.servicesample;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.logging.Logger;

/**
 * Created by Roberto Allende on 22-02-15.
 *
 * Following:
 *  http://stackoverflow.com/questions/9092134/broadcast-receiver-within-a-service
 *  https://stackoverflow.com/questions/5888502/how-to-detect-when-wifi-connection-has-been-established-in-android
 */

public class MyService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();
        AppTools.saveLog(this, "Service Created");
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        AppTools.saveLog(this, "Service Started");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
        AppTools.saveLog(this, "Service Stopped");
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                int networkType = intent.getIntExtra(android.net.ConnectivityManager.EXTRA_NETWORK_TYPE, -1);
                if (ConnectivityManager.TYPE_WIFI == networkType) {
                    NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                    if (networkInfo != null) {
                        if (networkInfo.isConnected()) {
                            AppTools.saveLog(context, "Connected to a WiFi Network");
                        } else {
                            AppTools.saveLog(context, "Disconnected from a Wifi Network");
                        }
                    } else {
                        AppTools.saveLog(context, "Not using wifi");
                    }
                }
            }
        }
    };
}
