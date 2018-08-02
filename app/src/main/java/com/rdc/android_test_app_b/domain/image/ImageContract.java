package com.rdc.android_test_app_b.domain.image;

import android.content.Context;
import android.widget.ImageView;

import com.rdc.android_test_app_b.models.Link;

public interface ImageContract {
    interface View {
        void getValues();

        void loadImgByUrl(String url, ImageView imageView, final Context context, Link link);

        void nextAct(int status);

        void alarmHandler();

        void requestStoragePermission();


    }

    interface Presenter {
        void setView(ImageContract.View view);

        boolean internetConnection(Context context);


        void addLink(String url, String date, int status);

        void deleteLink(long id);

        void updateLink(long id, int status);

    }
}
