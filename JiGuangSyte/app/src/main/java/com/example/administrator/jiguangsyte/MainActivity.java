package com.example.administrator.jiguangsyte;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化JPush
        JPushInterface.init(this);
        //设置debug模式
        JPushInterface.setDebugMode(true);

    }
}
