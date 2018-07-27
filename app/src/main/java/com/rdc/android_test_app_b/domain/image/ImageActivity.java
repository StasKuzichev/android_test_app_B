package com.rdc.android_test_app_b.domain.image;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rdc.android_test_app_b.LinkOperations;
import com.rdc.android_test_app_b.R;
import com.rdc.android_test_app_b.models.Link;
import com.squareup.picasso.Picasso;

import java.util.Date;

import com.rdc.android_test_app_b.utils.DownloadImage;

public class ImageActivity extends AppCompatActivity implements ImageContract.View {

    private ImageView outImage;
    private TextView textView;
    private Button buttonDeleteLink;
    private String url = "";
    private String tabName = "";
    private int status = 2;
    private LinkOperations linkData;
    private ImagePresenter imagePresenter;


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


    @Override
    public void getValues() {
        Intent getLink = getIntent();
        url = getLink.getStringExtra("url");
        tabName = getLink.getStringExtra("type");
        Link link = new Link();
        linkData = new LinkOperations(this);
        linkData.open();
        link.setUrl(url);
        // TODO change this to class constant
        link.setStatus(2);
        String createdAt = new Date().toString();
        link.setCreatedAt(createdAt);
        linkData.addLink(link);
        long linkId = link.getId();
        loadImgByUrl(url, outImage, this, linkId);
    }

    @Override
    public void loadImgByUrl(String url, ImageView imageView, final Context context, final long linkId) {
        if (imagePresenter.internetConnection(context)) {
            Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imageView, new com.squareup.picasso.Callback() {

                        @Override
                        public void onSuccess() {
                            Toast.makeText(context, "Loading success", Toast.LENGTH_LONG).show();
                            status = 0;
                            Link link = linkData.getLink(linkId);
                            if (tabName.equals("history")) {
                                DownloadImage.downloadFile(context, link);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        linkData.deleteLinkById(linkId);
                                        //TODO Send Request to app a to update history
                                    }
                                }, 15000);
                            } else {
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
        } else {
            textView.setVisibility(View.INVISIBLE);
            buttonDeleteLink.setVisibility(View.INVISIBLE);
        }
        buttonDeleteLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
