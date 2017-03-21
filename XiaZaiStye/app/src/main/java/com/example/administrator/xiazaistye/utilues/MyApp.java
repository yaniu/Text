package com.example.administrator.xiazaistye.utilues;

import android.app.Application;

import org.xutils.x;

/**
 * 作者：仝晓雅 on 2017/3/17 13:33
 * 类的注释：
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
