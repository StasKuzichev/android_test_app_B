package com.rdc.android_test_app_b.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.rdc.android_test_app_b.databinding.MainActivityBinding;

import com.rdc.android_test_app_b.R;
import com.rdc.android_test_app_b.picture.PictureActivity;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {
    public static final String TAG = "MY_TAG";
    private MainPresenter mPresenter;
//    DialogInterface dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        MainActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        mPresenter = new MainPresenter(this);
        binding.setPresenter(mPresenter);

        boolean check = getIntent().getBooleanExtra("bool", false);
        String url = getIntent().getStringExtra("url");
        String tabName = getIntent().getStringExtra("type");

        if (check) {
            mPresenter.handlePictureScreen(url, tabName);
        } else {
            mPresenter.handleWarningDialog();
        }
    }

    public void showWarningDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.warning)
                .setMessage(R.string.warning_dialog)
                .setCancelable(false)
                .setNegativeButton(
                        R.string.back,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                finish();
                            }
                        }
                );
        AlertDialog alert = builder.create();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 10000);

        alert.show();
    }

    @Override
    public void showPictureScreen(String url, String tabName) {
        Intent intent = new Intent(MainActivity.this, PictureActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("type", tabName);
        startActivity(intent);
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (dialog != null) {
//            dialog.dismiss();
//            dialog = null;
//        }
//    }
}