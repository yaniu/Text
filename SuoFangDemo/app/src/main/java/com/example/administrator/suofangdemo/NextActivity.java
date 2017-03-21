package com.example.administrator.suofangdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

/**
 * 作者：仝晓雅 on 2017/3/19 10:07
 * 类的注释：
 */

public class NextActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_mxin);
        ViewPager pager = (ViewPager) findViewById(R.id.n_pager);
        Intent intent =getIntent();
        ArrayList<String> url = intent.getStringArrayListExtra("url");
        MyPager my =new MyPager(getSupportFragmentManager(),url);
        pager.setAdapter(my);
    }
}
