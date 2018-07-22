package com.rdc.android_test_app_b;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rdc.android_test_app_b.models.Link;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class SecondActivity extends AppCompatActivity {
    ImageView out_image;
    TextView t_hint_del_link;
    Button b_del_link;
    String url = "";
    int status = 2;
    LinkOperations linkData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        t_hint_del_link = findViewById(R.id.t_hint_del_link);
        b_del_link = findViewById(R.id.b_del_link);
        out_image = findViewById(R.id.imageViewOut);

        Intent get_link = getIntent();
        url = get_link.getStringExtra("url");
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
        loadImgByUrl(url, out_image, this, linkId);
    }

    public void loadImgByUrl(String url, ImageView imageView, final Context context, final long linkId) {
        if (internetConnection(context)) {
            Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imageView, new com.squareup.picasso.Callback() {

                        @Override
                        public void onSuccess() {
                            Toast.makeText(context, "Loading success", Toast.LENGTH_LONG).show();
                            status = 0;
                            updateLinkStatus(linkId);
                        }

                        @Override
                        public void onError() {
                            Toast.makeText(context, "Loading error", Toast.LENGTH_LONG).show();
                            status = 1;
                            updateLinkStatus(linkId);
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

    private void updateLinkStatus(long linkId) {
        Link link = linkData.getLink(linkId);
        link.setStatus(status);
        linkData.updateLink(link);
        nextAct(status);
    }

    public void nextAct(int status) {
        if (status == 1) {
            t_hint_del_link.setVisibility(TextView.VISIBLE);
            b_del_link.setVisibility(Button.VISIBLE);
        } else {
            t_hint_del_link.setVisibility(TextView.INVISIBLE);
            b_del_link.setVisibility(Button.INVISIBLE);
        }
        b_del_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public static boolean internetConnection(final Context context) {
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
