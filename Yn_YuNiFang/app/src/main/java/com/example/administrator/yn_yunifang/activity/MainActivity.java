package com.example.administrator.yn_yunifang.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.midi.MidiManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.yn_yunifang.R;

import java.util.ArrayList;
/**
 * 作者：仝晓雅 on 2017/4/13 19:35
 * 类的注释：引导页
 */
public class MainActivity extends AppCompatActivity {

    private ArrayList<ImageView> p_list;
    private ViewPager pager;
    private LinearLayout lin;
    int time =5;
    private TextView num ;
    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(time>0){
                time--;
                num.setText(time+"");
                handler.sendEmptyMessageDelayed(0,2000);
            }else{
                Intent intent =new Intent(MainActivity.this,NextActivity.class);
                edit.putBoolean("falg",true);
                edit.commit();
                startActivity(intent);
            }
        }
    };
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (ViewPager) findViewById(R.id.t_pager);
        lin = (LinearLayout) findViewById(R.id.t_lin);
        num = (TextView) findViewById(R.id.t_num);
        SharedPreferences sh =getSharedPreferences("",MODE_PRIVATE);
        edit = sh.edit();
        boolean falg = sh.getBoolean("falg", false);
        if(falg){
            Intent intent =new Intent(MainActivity.this,NextActivity.class);
            startActivity(intent);
        }
        int []pic = new int[]{R.mipmap.huawei_guidance_1,R.mipmap.huawei_guidance_2,R.mipmap.huawei_guidance_3};
        p_list = new ArrayList<>();
        for(int i =0;i<3;i++){
            ImageView img =new ImageView(MainActivity.this);
            img.setImageResource(pic[i]);
            p_list.add(img);
        }

        pager.setAdapter(new MyPager());
        handler.sendEmptyMessageDelayed(0,2000);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               for(int i =0;i<p_list.size();i++){
                   if(position==p_list.size()-1){
                       lin.setVisibility(View.VISIBLE);

                       lin.setOnClickListener(new View.OnClickListener() {
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
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    class MyPager extends PagerAdapter{

        @Override
        public int getCount() {
            return p_list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view ==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
           container.addView(p_list.get(position));
            return p_list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(p_list.get(position));
        }
    }
}

