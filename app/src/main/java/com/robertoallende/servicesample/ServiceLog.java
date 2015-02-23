package com.robertoallende.servicesample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by Roberto Allende on 22-02-15.
 *
 * Following:
 *  http://stackoverflow.com/questions/1748977/making-textview-scrollable-in-android
 */


public class ServiceLog extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_log);
        TextView log_container = (TextView)findViewById(R.id.service_log_container);
        log_container.setMovementMethod(new ScrollingMovementMethod());
        String currentLogs = AppTools.getLogs(this);
        log_container.setText(currentLogs);
    }
}
