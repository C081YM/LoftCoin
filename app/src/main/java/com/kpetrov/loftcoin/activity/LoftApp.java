package com.kpetrov.loftcoin.activity;

import android.app.Application;
import android.os.StrictMode;

import com.kpetrov.loftcoin.BuildConfig;

public class LoftApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults();
        }
    }
}
