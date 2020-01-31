package com.martinboy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private Handler mHandler;
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    };
    public static final int DELAY_MILLISECONDS = 2000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(mRunnable, DELAY_MILLISECONDS);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        removeCallBack();
    }

    @Override
    protected void onStop() {
        super.onStop();
        removeCallBack();
    }

    private void removeCallBack() {
        if (mHandler != null)
            mHandler.removeCallbacks(mRunnable);
    }

}