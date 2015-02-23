package com.robertoallende.servicesample;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Roberto Allende on 22-02-15.
 *
 * Following:
 *  http://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-timeouts
 *  http://www.javacodegeeks.com/2014/06/saving-data-to-a-file-in-your-android-application.html
 *  http://stackoverflow.com/questions/14376807/how-to-read-write-string-from-a-file-in-android
 *  http://stackoverflow.com/questions/5739140/mediascannerconnection-produces-android-app-serviceconnectionleaked
 */

public final class AppTools {

    public static boolean isOnline(Context c) {
        ConnectivityManager cm =
                (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static boolean saveLog(Context c, String logEntry) {
        String fileName = "ServiceSampleLog.txt";
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm - ");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        try
        {   String entry = strDate + logEntry + "\n";
            String currentContent = getLogs(c);
            // Creates a trace file in the primary external storage space of the
            // current application.
            // If the file does not exists, it is created.
            File traceFile = new File(c.getExternalFilesDir(null), fileName);
            if (!traceFile.exists())
                traceFile.createNewFile();
            // Adds a line to the trace file
            BufferedWriter writer = new BufferedWriter(new FileWriter(traceFile, false));
            writer.write(entry + currentContent);
            writer.close();
            // Refresh the data so it can seen when the device is plugged in a
            // computer. You may have to unplug and replug the device to see the
            // latest changes. This is not necessary if the user should not modify
            // the files.
            Uri contentUri = Uri.fromFile(traceFile);
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(contentUri);
            c.sendBroadcast(mediaScanIntent);
        }
        catch (IOException e)
        {
            String myError = String.format("Unable to write to the %s file.", fileName);
            Log.e("com.robertoallende.servicesample",  myError);
            return false;
        }
        return true;
    }

    public static String getLogs(Context c) {
        String fileName = "/ServiceSampleLog.txt";
        String contents = "";
        try
        {
            String path = c.getExternalFilesDir(null).getAbsolutePath();
            File file = new File(path + fileName);
            int length = (int) file.length();

            byte[] bytes = new byte[length];

            FileInputStream in = new FileInputStream(file);
            try {
                in.read(bytes);
            } finally {
                in.close();
            }
            contents = new String(bytes);
        }
        catch (IOException e)
        {
            String myError = String.format("Unable to read to the %s file.", fileName);
            Log.e("com.robertoallende.servicesample",  myError);
            return contents;
        }
        return contents;
    }
}
