package com.example.administrator.yn_new.utiles;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 作者：仝晓雅 on 2017/3/14 21:16
 * 类的注释：(联网判断)
 */

public class MyNexTO {
    public static boolean isNext(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info!=null){
            return true;
        }else{
            return false;
        }
    }
    //判断是否是wifi
    public static boolean isWifi(Context context) {
        //网络连接管理器
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取网络信息
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.getType() == connectivityManager.TYPE_WIFI) {
            return true;
        }

        return false;
    }

    //判断是否是手机流量
    public static boolean isMobile(Context context) {
        //网络连接管理器
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取网络信息
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.getType() == connectivityManager.TYPE_MOBILE) {
            return true;
        }

        return false;
    }
}
