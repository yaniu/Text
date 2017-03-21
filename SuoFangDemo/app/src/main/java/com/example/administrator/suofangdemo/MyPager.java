package com.example.administrator.suofangdemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 作者：仝晓雅 on 2017/3/19 10:16
 * 类的注释：
 */

public class MyPager extends FragmentPagerAdapter {
    private ArrayList<String> url;
    public MyPager(FragmentManager fm, ArrayList<String> url) {

        super(fm);
        this.url=url;
    }



    @Override
    public Fragment getItem(int position) {
        Fragment_s fragment =new Fragment_s();
        Bundle bundle =new Bundle();
        bundle.putString("pic",url.get(position));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return url.size();
    }
}
