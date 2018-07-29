package com.rdc.android_test_app_b;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rdc.android_test_app_b.db.LinkDBHandler;
import com.rdc.android_test_app_b.models.Link;

import java.util.ArrayList;
import java.util.List;

public class LinkOperations {
    public static final String LOGTAG = "EMP_MNGMNT_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            LinkDBHandler.COLUMN_ID,
            LinkDBHandler.COLUMN_URL,
            LinkDBHandler.COLUMN_STATUS,
            LinkDBHandler.COLUMN_CREATED_AT
    };

    public LinkOperations(Context context){
        dbhandler = new LinkDBHandler(context);
    }

    public void open(){
        Log.i(LOGTAG,"Database Opened");
        database = dbhandler.getWritableDatabase();


    }
    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();

    }
    public Link addLink(Link link){
        ContentValues values  = new ContentValues();

        values.put(LinkDBHandler.COLUMN_URL,link.getUrl());
        values.put(LinkDBHandler.COLUMN_STATUS,link.getStatus());
        values.put(LinkDBHandler.COLUMN_CREATED_AT, link.getCreatedAt());

        long insertId = database.insert(LinkDBHandler.TABLE_LINKS,null,values);
        link.setId(insertId);

        return link;
    }

    // Getting single Link
    public Link getLink(long id) {
        Cursor cursor = database.query(
                LinkDBHandler.TABLE_LINKS,
                allColumns,
                LinkDBHandler.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null
        );

        if (cursor != null) cursor.moveToFirst();

        Link link = new Link(
                Long.parseLong(cursor.getString(0)),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getString(3)
        );

        return link;
    }

    public List<Link> getAllLinks() {
        Cursor cursor = database.query(
                LinkDBHandler.TABLE_LINKS,
                allColumns,
                null,
                null,
                null,
                null,
                null
        );

        List<Link> links = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Link link = new Link();
                link.setId(cursor.getLong(cursor.getColumnIndex(LinkDBHandler.COLUMN_ID)));
                link.setUrl(cursor.getString(cursor.getColumnIndex(LinkDBHandler.COLUMN_URL)));
                link.setStatus(cursor.getInt(cursor.getColumnIndex(LinkDBHandler.COLUMN_STATUS)));
                link.setCreatedAt(cursor.getString(cursor.getColumnIndex(LinkDBHandler.COLUMN_CREATED_AT)));
                links.add(link);
            }
        }

        return links;
    }

    public int updateLink(Link link) {
        ContentValues values = new ContentValues();
        values.put(LinkDBHandler.COLUMN_URL, link.getUrl());
        values.put(LinkDBHandler.COLUMN_STATUS, link.getStatus());
        values.put(LinkDBHandler.COLUMN_CREATED_AT, link.getCreatedAt());

        return database.update(
                LinkDBHandler.TABLE_LINKS,
                values,
                LinkDBHandler.COLUMN_ID + "=?",
                new String[] {
                        String.valueOf(link.getId())
                }
        );
    }

    public void deleteLink(Link link) {
        database.delete(
                LinkDBHandler.TABLE_LINKS,
                LinkDBHandler.COLUMN_ID + "=" + link.getId(),
                null
        );
    }

    public void deleteLinkById(long id) {
        database.delete(
                LinkDBHandler.TABLE_LINKS,
                LinkDBHandler.COLUMN_ID + "=" + id,
                null
        );
    }



}
