package com.example.administrator.yn_twodemo.utilues;

import android.app.Application;

import org.xutils.x;

/**
 * 作者：仝晓雅 on 2017/3/19 18:55
 * 类的注释：
 */

public class MyApption extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
