package com.example.administrator.xiazaistye.utilues;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

/**
 * 作者：仝晓雅 on 2017/3/17 09:37
 * 类的注释：
 */

public class NextWoke {
    public  static  boolean isNextWoke(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info!=null){
            return  true;
        }else{
            return  false;
        }
    }
    public  static  boolean isNextWokeWifi(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info!=null&&info.getType()==manager.TYPE_WIFI){
            return  true;
        }else{
            return  false;
        }
    }
    public  static  boolean isNextWokePhoe(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info!=null&&info.getType()==manager.TYPE_MOBILE){
            return  true;
        }else{
            return  false;
        }
    }
}
