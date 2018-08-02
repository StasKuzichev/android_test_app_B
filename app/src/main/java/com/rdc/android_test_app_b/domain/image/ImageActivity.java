package com.rdc.android_test_app_b.domain.image;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rdc.android_test_app_b.LinkOperations;

import com.rdc.android_test_app_b.LinkProviderConstants;
import com.rdc.android_test_app_b.R;
import com.rdc.android_test_app_b.models.Link;
import com.rdc.android_test_app_b.utils.AlarmReceiver;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.rdc.android_test_app_b.utils.DownloadImage;

public class ImageActivity extends AppCompatActivity implements ImageContract.View {
    private static final int PERMISSION_REQUEST_CODE = 123;
    private ImageView outImage;
    private TextView textView;
    private Button buttonDeleteLink;
    private String url = "";
    private String tabName = "";
    private int status = 2;
    private String idLink;
    private LinkOperations linkData;
    private ImagePresenter imagePresenter;
    private ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        textView = findViewById(R.id.textHintDeleteLink);
        buttonDeleteLink = findViewById(R.id.buttonDeleteLink);
        outImage = findViewById(R.id.imageViewOut);
        imagePresenter = new ImagePresenter();
        imagePresenter.setView(this);
        getValues();
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed to save your picture in the folder")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(ImageActivity.this,
                                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
    @Override
    public void getValues() {
        Intent getLink = getIntent();
        url = getLink.getStringExtra("url");
        tabName = getLink.getStringExtra("type");
        idLink = getLink.getStringExtra("idLink");
        Link link = new Link();
        linkData = new LinkOperations(this);
        linkData.open();
        link.setUrl(url);
        contentResolver = getContentResolver();
        // TODO change this to class constant
        link.setStatus(2);
        String createdAt = new Date().toString();
        link.setCreatedAt(createdAt);
        linkData.addLink(link);
        long linkId = link.getId();
        loadImgByUrl(url, outImage, this, linkId);
    }

    public void addLink(String url, String date, int status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LinkProviderConstants.COLUMN_URL, url);
        contentValues.put(LinkProviderConstants.COLUMN_STATUS, status);
        contentValues.put(LinkProviderConstants.COLUMN_DATE, date);
        contentResolver.insert(LinkProviderConstants.CONTENT_URI, contentValues);
    }

    public void deleteLink(long id) {
        String linkId = String.valueOf(id);
        contentResolver.delete(LinkProviderConstants.CONTENT_URI,
                LinkProviderConstants.COLUMN_LINK_ID + " = ?",
                new String[]{linkId});

    }


    @Override
    public void loadImgByUrl(final String url, ImageView imageView, final Context context,
                             final long linkId) {
        if (imagePresenter.internetConnection(context)) {
            Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imageView, new com.squareup.picasso.Callback() {
                        Date date = new Date();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
                        String dateStr = simpleDateFormat.format(date).toString();

                        @Override
                        public void onSuccess() {
                            Toast.makeText(context, "Loading success", Toast.LENGTH_LONG).show();
                            status = 0;
                            Link link = linkData.getLink(linkId);
                            if (tabName.equals("history")) {
                                requestStoragePermission();
                                if (ContextCompat.checkSelfPermission(ImageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                    DownloadImage.downloadFile(context, link);
                                } else {
                                    Toast.makeText(context, "You did not give the permission so your picture won't be saved", Toast.LENGTH_SHORT).show();
                                }
                                alarmHandler();
                                Handler handler = new Handler();
                                final int realId = Integer.parseInt(idLink);
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        deleteLink(realId);;
                                    }
                                }, 15000);

                            } else {

                                addLink(url, dateStr, status);
                                imagePresenter.updateLinkStatus(link, status, linkData);
                            }
                        }
                        @Override
                        public void onError() {
                            Toast.makeText(context, "Loading error", Toast.LENGTH_LONG).show();
                            status = 1;
                            Link link = linkData.getLink(linkId);
                            imagePresenter.updateLinkStatus(link, status, linkData);
                        }
                    });
        } else {
            AlertDialog.Builder no_internet_connection = new AlertDialog.Builder(this);
            no_internet_connection.setCancelable(false)
                    .setTitle("No internet connection!")
                    .setMessage("Please check your network connection and try again.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            finish();
                        }
                    }).show();
        }
    }

    @Override
    public void nextAct(int status) {
        if (status == 1) {
            textView.setVisibility(View.VISIBLE);
            buttonDeleteLink.setVisibility(View.VISIBLE);
            if(tabName.equals("edit")){
                buttonDeleteLink.setText("SAVE ANYWAY");
            }
        } else {
            textView.setVisibility(View.INVISIBLE);
            buttonDeleteLink.setVisibility(View.INVISIBLE);
        }
        final int localId = Integer.parseInt(idLink);
        buttonDeleteLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tabName.equals("history")){
                    deleteLink(localId);
                } else {
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
                    String dateStr = simpleDateFormat.format(date).toString();
                    addLink(url, dateStr, 1);
                }
            }
        });
    }
    public void alarmHandler(){
        Intent i = new Intent(ImageActivity.this, AlarmReceiver.class);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(),0, i, 0);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ 15000, pi);
    }
}