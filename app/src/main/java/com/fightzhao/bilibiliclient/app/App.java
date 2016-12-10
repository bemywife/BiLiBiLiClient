package com.fightzhao.bilibiliclient.app;

import android.app.Application;

/**
 * 作者 ：fightzhao on 2016/11/25 11:37.
 * 功能 : Application
 */

public class App extends Application {
    private static App mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static App getInstance() {

        return mInstance;
    }
}
