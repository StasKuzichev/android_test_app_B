package com.rdc.android_test_app_b;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String text = "";
        btn = (Button) findViewById(R.id.button) ;
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
        int status =0;
        if(status == 1){
            btn.setVisibility(Button.VISIBLE);
        }else if(status == 2){
            btn.setVisibility(Button.VISIBLE);
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
    public void onButtonClick(View view)
    {
       String url="";
       url=null;
    }
}
