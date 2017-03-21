package com.example.administrator.yn_new.utiles;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 作者：仝晓雅 on 2017/3/14 21:16
 * 类的注释：(联网判断)
 */

public class MyNexTO {
    public boolean isNext(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info!=null){
            return true;
        }else{
            return false;
        }
    }
}
