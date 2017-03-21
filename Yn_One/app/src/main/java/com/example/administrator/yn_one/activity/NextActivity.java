package com.example.administrator.yn_one.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.webkit.WebView;

import com.example.administrator.yn_one.R;

/**
 * 作者：仝晓雅 on 2017/3/11 10:37
 * 类的注释：
 */

public class NextActivity extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.next_main);
        WebView web = (WebView) findViewById(R.id.web);
        Intent intent =getIntent();
        String url = intent.getStringExtra("url");
        web.loadUrl(url);

    }
}
