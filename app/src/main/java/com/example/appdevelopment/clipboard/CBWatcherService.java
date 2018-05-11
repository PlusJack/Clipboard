package com.example.appdevelopment.clipboard;

/**
 * Created by jackv on 5/10/2018.
 */

import android.app.Service;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class CBWatcherService extends Service {

    private final String tag = "[[ClipboardWatcherService]] ";
    private OnPrimaryClipChangedListener listener = new OnPrimaryClipChangedListener() {
        public void onPrimaryClipChanged() {
            performClipboardCheck();
        }
    };

    @Override
    public void onCreate() {
        ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).addPrimaryClipChangedListener(listener);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        File directory = getFilesDir();
        File file = new File(directory, "ClipStorage");

        if (!file.exists()) { file.mkdir(); }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void performClipboardCheck() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (clipboard.hasPrimaryClip()) {
            android.content.ClipDescription description = clipboard.getPrimaryClipDescription();
            android.content.ClipData data = clipboard.getPrimaryClip();
            if (description.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                try {
                    File folder = new File(getFilesDir(), "ClipStorage");
                    if (!folder.exists()) { folder.mkdir(); }
                    Calendar cal = Calendar.getInstance();
                    String newCachedClip =
                            cal.get(Calendar.YEAR) + "-" +
                                    cal.get(Calendar.MONTH) + "-" +
                                    cal.get(Calendar.DAY_OF_MONTH) + "-" +
                                    cal.get(Calendar.HOUR_OF_DAY) + "-" +
                                    cal.get(Calendar.MINUTE) + "-" +
                                    cal.get(Calendar.SECOND);

                    // The name of the file acts as the timestamp (ingenious, uh?)
                    File file = new File(getFilesDir().toString()+"/ClipStorage", newCachedClip);
                    file.createNewFile();
                    BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));
                    bWriter.write((data.getItemAt(0).getText()).toString());
                    bWriter.newLine();
                    bWriter.write("false");
                    bWriter.close();
                    Toast.makeText(getApplicationContext(), data.getItemAt(0).getText() + " " + file.getName(),
                            Toast.LENGTH_SHORT).show();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
