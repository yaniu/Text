package com.example.administrator.yn_one_b.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.administrator.yn_one_b.R;

/**
 * 作者：仝晓雅 on 2017/3/12 10:21
 * 类的注释：
 */

public class NextActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_main);
        WebView web= (WebView) findViewById(R.id.web);
        Intent intent =getIntent();
        String url = intent.getStringExtra("url");
        web.loadUrl(url);
    }
}
