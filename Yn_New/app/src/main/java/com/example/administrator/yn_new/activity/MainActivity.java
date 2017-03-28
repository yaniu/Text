package com.example.administrator.yn_new.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.yn_new.R;
import com.jaeger.library.StatusBarUtil;

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
        //设置与标题栏颜色一样
        StatusBarUtil.setColor(MainActivity.this,getResources().getColor(R.color.chenColorPrimary),0);
        //设置他的透明度
        StatusBarUtil.setTransparent(MainActivity.this);
        StatusBarUtil.setTranslucent(MainActivity.this,112);//设置标题栏为半透明颜色
    }
    //再按退出
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() ==
                KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
