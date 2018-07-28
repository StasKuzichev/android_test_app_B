package com.rdc.android_test_app_b;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.rdc.android_test_app_b.SecondActivity.ImageActivity;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MY_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean check = getIntent().getBooleanExtra("bool", false);
        String tab_name = getIntent().getStringExtra("type");

        if (check) {
            Log.d(TAG, (String) getIntent().getStringExtra("url"));
            String linkToImg = getIntent().getStringExtra("url");
            Intent intent = new Intent(MainActivity.this, ImageActivity.class);
            intent.putExtra("url", linkToImg);
            intent.putExtra("type", tab_name);
            startActivity(intent);
            finish();
        } else {
            closeActivity();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 10000);
        }
    }

    private void closeActivity() {
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

    }
}