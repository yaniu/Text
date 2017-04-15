package com.example.administrator.yn_yunifang.adpater;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.yn_yunifang.activity.NextActivity;
import com.example.administrator.yn_yunifang.bean.Bean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 作者：仝晓雅 on 2017/4/14 19:15
 * 类的注释：
 */

public class MyPager extends PagerAdapter {

private List<ImageView> pics;
    public MyPager(List<ImageView> pics) {
        this.pics =pics;
    }

    @Override
    public int getCount() {
        return pics.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view ==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(pics.get(position));


        return pics.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(pics.get(position));
    }
}
