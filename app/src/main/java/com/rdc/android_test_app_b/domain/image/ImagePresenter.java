package com.rdc.android_test_app_b.domain.image;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;

import com.rdc.android_test_app_b.LinkOperations;
import com.rdc.android_test_app_b.models.Link;
import com.rdc.android_test_app_b.utils.PictureHelper;

public class ImagePresenter implements ImageContract.Presenter {
    private ImageContract.View view;
    PictureHelper pictureHelper = new PictureHelper();
    ImageActivity imageActivity = new ImageActivity();

    @Override
    public void setView(ImageContract.View view) {
        this.view = view;
    }

    @Override
    public void loadImg(String url, ImageView imageView, final Context context, final LinkOperations linkData, final long linkId, String tab_name) {
        if (internetConnection(context)){
            pictureHelper.load_picture(url, imageView, context, linkData, linkId);
        }else{
            AlertDialog.Builder no_internet_connection = new AlertDialog.Builder(context);
            no_internet_connection.setCancelable(false)
                    .setTitle("No internet connection!")
                    .setMessage("Please check your network connection and try again.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).show();
        }
        if (tab_name.equals("history")) {
            pictureHelper.download_picture(context, linkData, linkId);
        }
    }

    @Override
    public boolean internetConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        return false;
    }


}


//    @Override
//    public void updateLinkStatus(Link link, int status, LinkOperations linkData) {
//        link.setStatus(status);
//        linkData.updateLink(link);
//        view.nextAct(status);
//    }