package com.murach.reminder;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.app.PendingIntent;
import android.util.Log;
import android.os.IBinder;
import android.content.Intent;


public class ReminderService extends Service {

    private Timer timer;

    @Override
    public void onCreate() {
        Log.d("Reminder", "Service created");
        startTimer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Reminder", "Service started");
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Reminder", "No binding for this service");
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d("Reminder", "Service stopped (destroyed)");
        stopTimer();
    }

    private void startTimer() {
        //create task
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Log.d("Reminder", "Look into the distance. It's good for your eyes!");
                sendNotification("Look into the distance. It's good for your eyes.");
            }
        };

        //create and start timer
        timer = new Timer(true);
        int delay = 1000 * 5;     //5 seconds
        int interval = 1000 * 10;  //10 seconds -- for demonstration in class
        //int interval = 1000 * 60; //1 hour -- as the assignment requested
        timer.schedule(task, delay, interval);

    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private void sendNotification(String text) {
        //create the intent for the notification
        Intent notificationIntent = new Intent(this, ReminderActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //create the pending intent
        int flag = PendingIntent.FLAG_UPDATE_CURRENT;
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, flag);

        //create the variables for the notification
        int icon = R.drawable.ic_launcher;
        CharSequence tickerText = "New notification!";
        CharSequence contentTitle = getText(R.string.app_name);
        CharSequence contentText = text;

        //create the notification and set its data
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(icon)
                .setTicker(tickerText)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        //display the notification
        NotificationManager manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        final int NOTIFICATION_ID = 1;
        manager.notify(NOTIFICATION_ID, notification);
    }
}
