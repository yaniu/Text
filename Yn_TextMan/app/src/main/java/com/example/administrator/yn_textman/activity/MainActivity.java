package com.example.administrator.yn_textman.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.yn_textman.R;

import java.util.ArrayList;

/**
 * 作者：仝晓雅 on 2017/3/8 15:12
 * 类的注释：引导页（3）
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private ArrayList<ImageView> list;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        pager = (ViewPager) findViewById(R.id.o_pager);
        list = new ArrayList<>();
        int [] pics =new int[]{R.mipmap.bg_guide1_background,R.mipmap.bg_guide2_background,R.mipmap.bg_guide3_background};

        for(int i=0;i<3;i++){
            ImageView pic =new ImageView(this);
            pic.setImageResource(pics[i]);
            list.add(pic);
        }
        pager.setAdapter(new MyPager());
        SharedPreferences sh=getSharedPreferences("mamhua",MODE_PRIVATE);
        edit = sh.edit();
        boolean falg = sh.getBoolean("falg", false);
        if(falg){
            Intent intent =new Intent(MainActivity.this,NextActivity.class);
            startActivity(intent);

        }

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               if(position==list.size()-1){
                   list.get(position).setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Intent intent =new Intent(MainActivity.this,NextActivity.class);
                          edit.putBoolean("falg",true);
                           edit.commit();

                           startActivity(intent);
                       }
                   });
               }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    class MyPager extends PagerAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            pager.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            pager.removeView(list.get(position));
        }

    }
}
