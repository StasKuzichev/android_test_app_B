package com.rdc.android_test_app_b.domain.image;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.rdc.android_test_app_b.LinkOperations;
import com.rdc.android_test_app_b.models.Link;

public class ImagePresenter implements ImageContract.Presenter {
    private ImageContract.View view;

    @Override
    public void setView(ImageContract.View view) {
        this.view = view;
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

    @Override
    public void updateLinkStatus(Link link, int status, LinkOperations linkData) {
        link.setStatus(status);
        linkData.updateLink(link);
        view.nextAct(status);
    }


}
