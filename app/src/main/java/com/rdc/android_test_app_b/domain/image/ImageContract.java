package com.rdc.android_test_app_b.domain.image;

import android.content.Context;
import android.widget.ImageView;

import com.rdc.android_test_app_b.LinkOperations;
import com.rdc.android_test_app_b.models.Link;

public interface ImageContract {
    interface View {
        void getValues();

        void loadImgByUrl(String url, ImageView imageView, final Context context, final long linkId);

        void nextAct(int status);

        void addLink(String url, String date, int status);

        void deleteLink(long id);
    }

    interface Presenter {
        void setView(ImageContract.View view);

        boolean internetConnection(Context context);

        void updateLinkStatus(Link link, int status, LinkOperations linkData);


    }
}
