package com.rdc.android_test_app_b.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.rdc.android_test_app_b.LinkOperations;
import com.rdc.android_test_app_b.R;
import com.rdc.android_test_app_b.utils.PictureHelper;

public class AlarmLinkDeleted extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        LinkOperations linkData = PictureHelper.link_data_deleted;
        long linkId = intent.getLongExtra("linkID", 0);
        if (linkId != 0) {
            linkData.deleteLinkById(linkId);
            show_notification(context, "Successful", "Link was deleted from database");
        } else {
            show_notification(context, "Error", "Link was not deleted from database");
        }
        //TODO Send Request to app a to update history
    }

    private void show_notification(Context context, String not_title, String not_text) {
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);

        mBuilder.setContentTitle(not_title);
        mBuilder.setContentText(not_text);
        final Intent resultIntent = new Intent(context, Notification.class);
        final TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        stackBuilder.addParentStack(Notification.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_launcher_foreground);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationID = 1;
        mNotificationManager.notify(notificationID, mBuilder.build());
    }
}
