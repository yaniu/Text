package com.example.administrator.yn_one.utulies;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * 作者：仝晓雅 on 2017/3/11 09:48
 * 类的注释：
 */

public class MyAppTion extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration img =new ImageLoaderConfiguration.Builder(getApplicationContext()).memoryCacheExtraOptions(480,800).build();
        ImageLoader.getInstance().init(img);
    }
}
