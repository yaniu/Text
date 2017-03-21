package com.example.administrator.yn_qq;

import android.app.Application;

import org.xutils.x;


/**
 * 作者：仝晓雅 on 2017/3/13 18:48
 * 类的注释：
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
