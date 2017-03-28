package com.example.administrator.yn_new.utiles;

/**
 * 作者：仝晓雅 on 2017/3/28 08:52
 * 类的注释：
 */

public class MyZiti {
    public static int px2dip(int px) {
        //获取像素密度
        float density = MyApp.getContext().getResources().getDisplayMetrics().density;
        int dip = (int) (px / density + 0.5f);
        return dip;
    }
}
