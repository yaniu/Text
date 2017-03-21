package com.example.administrator.yn_viewpager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.util.ArrayList;

/**
 * 作者：仝晓雅 on 2017/3/21 15:00
 * 类的注释：
 */

public class NextActivite extends Activity{

    private WebView web;
    private  ProgressDialog dialog =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_main);
        web = (WebView) findViewById(R.id.web);
        Intent intent = getIntent();
        int i = intent.getIntExtra("position", -1);
        String list = intent.getStringExtra("list");
        //ArrayList<String> url = intent.getStringArrayListExtra("list");
        web.loadUrl(list);

       web.setWebChromeClient(new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {

            if (newProgress == 100) {
                // 加载完毕
                closeDialog(newProgress);
            } else {
                openDialog(newProgress);
            }



            super.onProgressChanged(view, newProgress);
        }

    private void openDialog(int newProgress) {
        if (dialog == null) {
            dialog = new ProgressDialog(NextActivite.this);
            dialog.setTitle("正在加载");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setProgress(newProgress);
            dialog.show();
        } else {
            dialog.setProgress(newProgress);
        }
    }

    private void closeDialog(int newProgress) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }
});
    }
}
