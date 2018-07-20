package com.rdc.android_test_app_b;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //переменная для директории
    final String DIR_SD = "BIGDIG/test/B";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //пример использования. На вход необходимо подать URL картинки
        downloadFile("https://startandroid.ru/images/stories/lessons/L0075/640x449xL0075_010.JPG.pagespeed.ic.J2ObIHcS6g.webp");
        //конец
    }

    //метод для сохранения картинки по URl
    //прописана SD карта, но пишет во внутреннюю память телефона
    public void downloadFile(String uRl) {
        File direct = new File(Environment.getExternalStorageDirectory()
                + DIR_SD);

        if (!direct.exists()) {
            direct.mkdirs();
        }
        int x = new Random().nextInt(10000);
        String fileName = "" + x;
        DownloadManager mgr = (DownloadManager) getBaseContext().getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(uRl);
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle("Demo")
                .setDescription("Something useful. No, really.")
                .setDestinationInExternalPublicDir(DIR_SD, fileName+".jpg");

        mgr.enqueue(request);

    }
}
