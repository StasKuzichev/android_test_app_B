package com.rdc.android_test_app_b.domain.image;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.rdc.android_test_app_b.LinkProviderConstants;

public class ImagePresenter implements ImageContract.Presenter {
    private ImageContract.View view;
    private ContentResolver contentResolver;

    @Override
    public void setView(ImageContract.View view) {
        this.view = view;
    }

    public void setContentResolver(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
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
    public void addLink(String url, String date, int status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LinkProviderConstants.COLUMN_URL, url);
        contentValues.put(LinkProviderConstants.COLUMN_STATUS, status);
        contentValues.put(LinkProviderConstants.COLUMN_DATE, date);
        contentResolver.insert(LinkProviderConstants.CONTENT_URI, contentValues);
    }

    @Override
    public void deleteLink(long id) {
        String linkId = String.valueOf(id);
        contentResolver.delete(LinkProviderConstants.CONTENT_URI,
                LinkProviderConstants.COLUMN_LINK_ID + " = ?",
                new String[]{linkId});

    }

    @Override
    public void updateLink(long id, int status) {
        String linkId = String.valueOf(id);
        ContentValues contentValues = new ContentValues();
        contentValues.put(LinkProviderConstants.COLUMN_STATUS, status);
        String whereClause = LinkProviderConstants.COLUMN_LINK_ID + " = ?";
        String[] whereValues = new String[]{linkId};
        contentResolver.update(LinkProviderConstants.CONTENT_URI, contentValues, whereClause, whereValues);
    }

}