package com.robertoallende.servicesample;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/*
 *  Following
 *   https://www.youtube.com/watch?v=Zs85mt6ZRbU
 */

public class ServiceSample extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_sample);
    }

    public void startMyService (View v) {
        Intent i = new Intent(this, MyService.class);
        startService(i);
    }

    public void stopMyService (View v) {
        Intent i = new Intent(this, MyService.class);
        stopService(i);
    }

    public void myServiceLog (View v) {
        Intent intent = new Intent(this, ServiceLog.class);
        startActivity(intent);
    }
}

