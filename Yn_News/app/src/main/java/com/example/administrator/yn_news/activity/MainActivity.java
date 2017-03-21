package com.example.administrator.yn_news.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.yn_news.R;
import com.example.administrator.yn_news.fragment.Fragemt1;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> list = new ArrayList<>();
    private ArrayList<Fragment> f_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建slidingment
        final SlidingMenu menu = new SlidingMenu(MainActivity.this);
        //设置侧滑方向
        menu.setMode(SlidingMenu.LEFT);
        //设置整个屏幕都能滑出
        //  slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //设置整个屏幕不让滑出
        //  slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        //设置侧滑宽度
        menu.setBehindOffset(200);
        //让侧滑依附到activity上
        menu.attachToActivity(MainActivity.this, SlidingMenu.SLIDING_CONTENT);
        //设置侧滑布局
        menu.setMenu(R.layout.inl_);
        ImageView ia = (ImageView) findViewById(R.id.ia);
        final DrawerLayout dl = (DrawerLayout) findViewById(R.id.dl);

        ia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   dl.openDrawer(Gravity.LEFT);
                //点击图片显示侧滑
                menu.toggle();
            }
        });
        list.add("NO:1");
        list.add("NO:2");
        list.add("NO:3");
        list.add("NO:4");
        list.add("NO:5");
        list.add("NO:6");
        list.add("NO:7");
        list.add("NO:8");
        list.add("NO:9");
        list.add("NO:9");
        list.add("NO:9");
        list.add("NO:9");
        list.add("NO:9");
        list.add("NO:9");
        f_list = new ArrayList<>();
     for(int i=0;i<14;i++){
         f_list.add(new Fragemt1());
     }
        ViewPager vp= (ViewPager) findViewById(R.id.vp);
        TabLayout tab = (TabLayout) findViewById(R.id.tt);
        tab.addTab(tab.newTab().setText(list.get(0)));
        tab.addTab(tab.newTab().setText(list.get(1)));
        tab.addTab(tab.newTab().setText(list.get(2)));
        tab.addTab(tab.newTab().setText(list.get(3)));
        tab.addTab(tab.newTab().setText(list.get(4)));
        tab.addTab(tab.newTab().setText(list.get(5)));
        tab.addTab(tab.newTab().setText(list.get(6)));
        tab.addTab(tab.newTab().setText(list.get(7)));
        tab.addTab(tab.newTab().setText(list.get(8)));
        tab.addTab(tab.newTab().setText(list.get(9)));
        tab.addTab(tab.newTab().setText(list.get(10)));
        tab.addTab(tab.newTab().setText(list.get(11)));
        tab.addTab(tab.newTab().setText(list.get(12)));
        tab.addTab(tab.newTab().setText(list.get(13)));

      //  TabLayout的设置模式
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);

        MyPager my = new MyPager(getSupportFragmentManager());
        vp.setAdapter(my);
        //将TabLayout跟ViewPager关联
        tab.setupWithViewPager(vp);
        //适配
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
            return list.get(position);
        }
    }
}
