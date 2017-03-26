package com.example.administrator.yn_new.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.yn_new.R;

import cn.jpush.android.api.JPushInterface;

/**
 * 作者：仝晓雅 on 2017/3/10 21:14
 * 类的注释：欢迎页 （倒计时3秒跳到主页）
 */
public class MainActivity extends AppCompatActivity {

    private Handler handler =new Handler(){
        int time =3;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(time>0){
                time--;
                handler.sendEmptyMessageDelayed(0,1000);
            }else{
                Intent in =new Intent(MainActivity.this,NextActivity.class);
                startActivity(in);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler.sendEmptyMessageDelayed(0,1000);

        //初始化JPush
        JPushInterface.init(this);
        //设置debug模式
        JPushInterface.setDebugMode(true);
    }
}
