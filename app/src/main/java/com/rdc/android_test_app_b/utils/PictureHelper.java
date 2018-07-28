package com.rdc.android_test_app_b.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rdc.android_test_app_b.LinkOperations;
import com.rdc.android_test_app_b.MainActivity;
import com.rdc.android_test_app_b.R;
import com.rdc.android_test_app_b.domain.image.ImageActivity;
import com.rdc.android_test_app_b.models.Link;
import com.squareup.picasso.Picasso;

import static android.content.Context.ALARM_SERVICE;

public class PictureHelper {
    int status = 2;
    Link link = new Link();
    public static LinkOperations link_data_deleted;

    public void load_picture(String url, ImageView imageView, final Context context, final LinkOperations linkData, final long linkId) {
        Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView, new com.squareup.picasso.Callback() {

                    @Override
                    public void onSuccess() {
                        Toast.makeText(context, "Loading success", Toast.LENGTH_LONG).show();
                        status = 0;
                        updateLinkStatus(linkData);
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(context, "Loading error", Toast.LENGTH_LONG).show();
                        status = 1;
                        link = linkData.getLink(linkId);
                        updateLinkStatus(linkData);
                    }
                });
    }

    public void download_picture(Context context, LinkOperations linkData, long linkId) {
        link = linkData.getLink(linkId);
        DownloadImage.downloadFile(context, link);

        link_data_deleted = linkData;
        Intent i = new Intent(context, AlarmLinkDeleted.class);
        i.putExtra("linkID", linkId);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getBroadcast(context.getApplicationContext(), 0, i, 0);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 15000, pi);
        link_data_deleted = null;
    }

    private void updateLinkStatus(LinkOperations linkData) {
        link.setStatus(status);
        linkData.updateLink(link);
    }
}
