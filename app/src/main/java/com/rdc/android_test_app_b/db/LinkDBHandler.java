package com.rdc.android_test_app_b.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LinkDBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "links.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_LINKS = "links";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_CREATED_AT = "createdAt";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_LINKS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_URL + " TEXT, " +
                COLUMN_STATUS + " INTEGER, " +
                COLUMN_CREATED_AT + " TEXT " +
                ")";

    public LinkDBHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_LINKS);
        db.execSQL(TABLE_CREATE);
    }
}
