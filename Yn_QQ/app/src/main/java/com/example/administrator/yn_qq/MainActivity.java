package com.example.administrator.yn_qq;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.Log;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public ArrayList<SnsPlatform> platforms = new ArrayList<SnsPlatform>();
    private SHARE_MEDIA[] list = {SHARE_MEDIA.QQ};
    private boolean isauth;
    private TextView name;
    private ImageView pic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPlatforms();
        pic = (ImageView) findViewById(R.id.pic);
        name = (TextView) findViewById(R.id.name);
        ImageView fem = (ImageView) findViewById(R.id.fen);
        isauth = UMShareAPI.get(this).isAuthorize(this, platforms.get(0).mPlatform);
         pic.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (isauth) {
                     Toast.makeText(MainActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
                     UMShareAPI.get(MainActivity.this).deleteOauth(MainActivity.this, platforms.get(0).mPlatform, authListener);
                 } else {
                     Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                     UMShareAPI.get(MainActivity.this).doOauthVerify(MainActivity.this, platforms.get(0).mPlatform, authListener);
                 }

                 UMShareAPI.get(MainActivity.this).getPlatformInfo(MainActivity.this, platforms.get(0).mPlatform, authListener);
             }
         });
    }
    private void initPlatforms() {
        for (SHARE_MEDIA e : list) {
            if (!e.toString().equals(SHARE_MEDIA.GENERIC.toString())) {
                platforms.add(e.toSnsPlatform());
            }
        }

    }
    //授权监听
    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            // SocializeUtils.safeShowDialog(dialog);
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            //  SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(MainActivity.this, "成功了", Toast.LENGTH_LONG).show();
            //Log.i("xxx", data.toString());
            for (String key : data.keySet()) {


            }
            String profile_image_url = data.get("profile_image_url");
            String name = data.get("name");
            Log.i("xxx", "profile_image_url:" + profile_image_url.toString());
            Log.i("xxx", "name:" + name.toString());

            ImageOptions options =new ImageOptions.Builder().setCircular(true).setCrop(true).build();
            x.image().bind(pic,profile_image_url);



        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            //SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(MainActivity.this, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            //  SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(MainActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        //  Log.i("xxx", data.toString());

    }
}
