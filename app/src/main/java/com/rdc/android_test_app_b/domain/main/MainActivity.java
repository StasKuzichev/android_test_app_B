package com.rdc.android_test_app_b.domain.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.rdc.android_test_app_b.R;
import com.rdc.android_test_app_b.domain.image.ImageActivity;


public class MainActivity extends AppCompatActivity implements MainContract.View {
    private boolean check;
    private String type;
    private String idLink;
    private MainPresenter mainPresenter;
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        check = getIntent().getBooleanExtra("bool", false);
        type = getIntent().getStringExtra("type");
        idLink = getIntent().getStringExtra("idLink");

        status = getIntent().getIntExtra("status", 5);
        mainPresenter = new MainPresenter();
        mainPresenter.setView(this);
        mainPresenter.checking(check, type, idLink);
    }

    @Override
    public void transitionToAnotherActivity(String type, String idLink) {
        String linkToImg = getIntent().getStringExtra("url");
        Intent intent = new Intent(MainActivity.this, ImageActivity.class);
        intent.putExtra("url", linkToImg);
        intent.putExtra("type", type);
        intent.putExtra("idLink",idLink);
        intent.putExtra("status", status);
        startActivity(intent);

        finish();
    }

    @Override
    public void closeActivity() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("WARNING")
                .setMessage("Sorry, but you can't call this app from launcher. Please, open first app for following work." +
                        '\n' + "This app will be automatically close in 10 seconds.")
                .setCancelable(false)
                .setNegativeButton("BACK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                finish();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 10000);

    }

}