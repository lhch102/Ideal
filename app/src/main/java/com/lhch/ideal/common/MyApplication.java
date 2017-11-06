package com.lhch.ideal.common;

import android.app.Application;
import android.content.Context;

/**
 * 获取getContext
 * Created by Administrator on 2017/11/3.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();
    }

    public static Context getContext(){
        return context;
    }
}
