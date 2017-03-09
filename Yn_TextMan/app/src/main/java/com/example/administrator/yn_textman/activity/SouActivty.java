package com.example.administrator.yn_textman.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.yn_textman.R;

/**
 * 作者：仝晓雅 on 2017/3/9 20:10
 * 类的注释：点击搜索图标进行搜索界面
 */

public class SouActivty extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sou_main);
        initView();
    }

    private void initView() {
        TextView no = (TextView) findViewById(R.id.s_no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(SouActivty.this,ThreeActivity.class);
                startActivity(in);
            }
        });
    }
}
