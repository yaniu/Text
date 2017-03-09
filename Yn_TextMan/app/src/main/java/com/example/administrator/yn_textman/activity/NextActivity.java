package com.example.administrator.yn_textman.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.administrator.yn_textman.R;

/**
 * 作者：仝晓雅 on 2017/3/8 15:12
 * 类的注释：欢迎页（ Handler延迟发送消息）
 */

public class NextActivity extends Activity {
    int time =3;
    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(time>0){
                time--;
                handler.sendEmptyMessageDelayed(0,1000);
            }else{
                Intent intent =new Intent(NextActivity.this,ThreeActivity.class);
                startActivity(intent);
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_main);
        handler.sendEmptyMessageDelayed(0,2000);

    }
}
