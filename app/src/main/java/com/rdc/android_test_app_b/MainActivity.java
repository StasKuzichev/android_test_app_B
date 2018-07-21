package com.rdc.android_test_app_b;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {


    ImageView iV;
    String url = "https://skat/img.=./attach/12000/12669.jpg";
    int status =0;
    static TextView tv;
    String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iV = findViewById(R.id.imageView);
        tv = findViewById(R.id.tvid);

        text = getIntent().getStringExtra("param1");
        boolean check = getIntent().getBooleanExtra("bool", false);

        if (check) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("url", text);
            startActivity(intent);
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
        loadImgByUrl(url, iV, this);
    }
   public void loadImgByUrl(String url, ImageView imageView, final Context context) {
        Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher)               //default image(error)
                .error(R.mipmap.ic_launcher)
                .into(imageView, new com.squareup.picasso.Callback() {

                    @Override
                    public void onSuccess() {
                        Toast.makeText(context, "Loading success", Toast.LENGTH_LONG).show();
                        status = 1;
                        nextAct(status);
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(context, "Loading error", Toast.LENGTH_LONG).show();
                        status = 2;
                        nextAct(status);
                    }
                });
    }
    public void nextAct(int status){

    }
}