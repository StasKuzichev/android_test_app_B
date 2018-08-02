package com.rdc.android_test_app_b.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.rdc.android_test_app_b.models.Link;

import java.io.File;

public class DownloadImage {

    final static String DIR_SD = "sdcard/BIGDIG/test/B";

    public static   void downloadFile(Context context, Link link) {

            File direct = new File(Environment.getExternalStorageDirectory() + DIR_SD);

            if (!direct.exists()) {
                direct.mkdirs();
            }
            DownloadManager mgr = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

            Uri downloadUri = Uri.parse(link.getUrl());
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);

            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false).setTitle("Download")
                    .setDescription("Something useful.")
                    .setDestinationInExternalPublicDir(DIR_SD, link.getCreatedAt() + ".jpg");

            mgr.enqueue(request);
    }

}
