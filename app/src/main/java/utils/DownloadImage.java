package utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.util.Random;
//метод для сохранения картинки по URl
//прописана SD карта, но пишет во внутреннюю память телефона
public class DownloadImage {
    //Context context;

    final String DIR_SD = "BIGDIG/test/B";

    public  void downloadFile(Context context,String url) {
        //this.context = context;
        File direct = new File(Environment.getExternalStorageDirectory()+ DIR_SD);

        if (!direct.exists()) {
            direct.mkdirs();
        }
        int x = new Random().nextInt(10000);
        String fileName = "" + x;
        //DownloadManager mgr = (DownloadManager) getBaseContext().getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager mgr = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle("Demo")
                .setDescription("Something useful. No, really.")
                .setDestinationInExternalPublicDir(DIR_SD, fileName + ".jpg");

        mgr.enqueue(request);
    }

}
