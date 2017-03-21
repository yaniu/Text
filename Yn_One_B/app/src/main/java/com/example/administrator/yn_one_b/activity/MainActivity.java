package com.example.administrator.yn_one_b.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.yn_one_b.R;
import com.example.administrator.yn_one_b.fragment.Fragmnet_s;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> s_list =new ArrayList<>();
    private  ArrayList<Fragment> f_list =new ArrayList<>();
    private ArrayList<String> u_list =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SlidingMenu menu =new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setBehindOffset(200);
        menu.attachToActivity(MainActivity.this,SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.ce_main);
        initView();
    }

    private void initView() {
        TabLayout tab = (TabLayout) findViewById(R.id.tab);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        s_list.add("头条");s_list.add("社会");s_list.add("娱乐");
        s_list.add("军事");s_list.add("科技");s_list.add("汽车");
        s_list.add("头条");s_list.add("社会");s_list.add("娱乐");
        s_list.add("军事");s_list.add("科技");s_list.add("汽车");
        String path1="http://v.juhe.cn/toutiao/index?type=toutiao&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String path2="http://v.juhe.cn/toutiao/index?type=shehui&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String path3="http://v.juhe.cn/toutiao/index?type=yule&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String path4="http://v.juhe.cn/toutiao/index?type=junshi&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String path5="http://v.juhe.cn/toutiao/index?type=keji&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String path6="http://v.juhe.cn/toutiao/index?type=qiche&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String path7="http://v.juhe.cn/toutiao/index?type=toutiao&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String path8="http://v.juhe.cn/toutiao/index?type=shehui&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String path9="http://v.juhe.cn/toutiao/index?type=yule&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String path10="http://v.juhe.cn/toutiao/index?type=junshi&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String path11="http://v.juhe.cn/toutiao/index?type=keji&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String path12="http://v.juhe.cn/toutiao/index?type=qiche&key=32b9973df2e6ee0c2bf094b61c7d7844";
        u_list.add(path1);
        u_list.add(path2);
        u_list.add(path3);
        u_list.add(path4);
        u_list.add(path5);
        u_list.add(path6);
        u_list.add(path7);
        u_list.add(path8);
        u_list.add(path9);
        u_list.add(path10);
        u_list.add(path11);
        u_list.add(path12);
        for(int i =0;i<s_list.size();i++){
            Fragmnet_s fragment =new Fragmnet_s();
            fragment.setUrl(u_list.get(i));
            f_list.add(fragment);
        }
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
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
