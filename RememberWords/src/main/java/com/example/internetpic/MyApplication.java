package com.example.internetpic;

import android.content.Context;


import org.apache.http.HttpHeaders;
import org.litepal.LitePalApplication;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;


/**
 * @description:
 * @date :2019/8/28 14:59
 */
public class MyApplication extends LitePalApplication {
    private static MyApplication mApplication;

    public static MyApplication getApplication() {
        return mApplication;
    }

    public static Context getContextApplication() {
        return mApplication.getApplicationContext();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

}
