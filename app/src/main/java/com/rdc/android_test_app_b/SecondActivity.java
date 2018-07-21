package com.rdc.android_test_app_b;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class SecondActivity extends AppCompatActivity {


    ImageView out_image;
    TextView t_hint_del_link;
    Button b_del_link;
    String url = "";
    int status = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        t_hint_del_link = findViewById(R.id.t_hint_del_link);
        b_del_link = findViewById(R.id.b_del_link);
        out_image = findViewById(R.id.imageViewOut);

        Intent get_link = getIntent();
        url = get_link.getStringExtra("url");
        loadImgByUrl(url, out_image, this);
    }
    public void loadImgByUrl(String url, ImageView imageView, final Context context) {
        Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher)               //default image(error)
                .error(R.mipmap.ic_launcher)
                .into(imageView, new com.squareup.picasso.Callback() {

                    @Override
                    public void onSuccess() {
                        Toast.makeText(context, "Loading success", Toast.LENGTH_LONG).show();
                        status = 0;
                        nextAct(status);
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(context, "Loading error", Toast.LENGTH_LONG).show();
                        status = 1;
                        nextAct(status);
                    }
                });
    }
    public void nextAct(int status){
        if(status==1){
            t_hint_del_link.setVisibility(TextView.VISIBLE);
            b_del_link.setVisibility(Button.VISIBLE);
        }else{
            t_hint_del_link.setVisibility(TextView.INVISIBLE);
            b_del_link.setVisibility(Button.INVISIBLE);
        }
    }
}
