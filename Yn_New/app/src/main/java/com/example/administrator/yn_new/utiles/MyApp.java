package com.example.administrator.yn_new.utiles;

import android.app.Application;
import android.content.Context;

import org.xutils.x;

/**
 * 作者：仝晓雅 on 2017/3/12 20:41
 * 类的注释：X utiles3
 */

public class MyApp extends Application {
    public static int fontInt = 1;
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        x.Ext.init(this);
    }
    /**
     * 得到上下文
     * @return
     */
    public  static Context getContext(){
        return context;
    }
}
