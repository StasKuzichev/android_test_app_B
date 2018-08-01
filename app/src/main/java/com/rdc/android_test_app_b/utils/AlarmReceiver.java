package com.rdc.android_test_app_b.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import com.rdc.android_test_app_b.R;
import com.rdc.android_test_app_b.domain.image.ImageActivity;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);

        mBuilder.setContentTitle("D");
        mBuilder.setContentText("Hi, This is Android Notification Detail!");

        final Intent resultIntent = new Intent(context, Notification.class);
        final TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        stackBuilder.addParentStack(ImageActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_launcher_background);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Toast.makeText(context, "link was deleted", Toast.LENGTH_SHORT).show();
        int notificationID = 1;
        mNotificationManager.notify(notificationID, mBuilder.build());
    }
}
