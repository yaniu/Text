package com.example.administrator.yn_new.utiles;

import android.app.Application;

import org.xutils.x;

/**
 * 作者：仝晓雅 on 2017/3/12 20:41
 * 类的注释：X utiles3
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
