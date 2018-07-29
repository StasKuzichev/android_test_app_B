package com.rdc.android_test_app_b;

import android.net.Uri;


public interface LinkProviderConstants {

    Uri CONTENT_URI = Uri.parse
            ("content://links.com.rdc.androidtestappa/LINK_NAME");

    String COLUMN_LINK_ID = "_id";
    String COLUMN_URL = "url";
    String COLUMN_STATUS = "status";
    String COLUMN_DATE = "date";
}
