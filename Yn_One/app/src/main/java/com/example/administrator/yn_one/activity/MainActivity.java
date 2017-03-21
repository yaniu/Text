package com.example.administrator.yn_one.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.example.administrator.yn_one.R;
import com.example.administrator.yn_one.fragment.Fragments;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> s_list =new ArrayList<>();
    private  ArrayList<Fragment> f_list =new ArrayList<>();
    private ArrayList<String> u_list =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getServerData();
        initView();



    }

    private void getServerData() {
        String path1="http://v.juhe.cn/toutiao/index?type=shehui&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String path2="http://v.juhe.cn/toutiao/index?type=huonei&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String path3="http://v.juhe.cn/toutiao/index?type=guomao&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String path4="http://v.juhe.cn/toutiao/index?type=yule&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String path5="http://v.juhe.cn/toutiao/index?type=tiyu&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String path6="http://v.juhe.cn/toutiao/index?type=jingji&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String path7="http://v.juhe.cn/toutiao/index?type=shiwu&key=32b9973df2e6ee0c2bf094b61c7d7844";
        u_list.add(path1);
        u_list.add(path2);
        u_list.add(path3);
        u_list.add(path4);
        u_list.add(path5);
        u_list.add(path6);
        u_list.add(path7);
    }

    private void initView() {

       TabLayout tab = (TabLayout) findViewById(R.id.o_tab);
        ViewPager pager = (ViewPager) findViewById(R.id.o_pager);
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        s_list.add("社会");
        s_list.add("国内");
        s_list.add("头条");
        s_list.add("娱乐");
        s_list.add("体育");
        s_list.add("经济");
        s_list.add("军事");
        for(int i =0;i<s_list.size();i++){
            Fragments fr =new Fragments();
            fr.setUrl(u_list.get(i));
            f_list.add(fr);
        }

        MyPager my =new MyPager(getSupportFragmentManager());
        pager.setAdapter(my);
        tab.setupWithViewPager(pager);
        tab.setTabsFromPagerAdapter(my);

    }
    class MyPager extends FragmentPagerAdapter{

        public MyPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return f_list.get(position);
        }

        @Override
        public int getCount() {
            return f_list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return s_list.get(position);
        }
    }
}
