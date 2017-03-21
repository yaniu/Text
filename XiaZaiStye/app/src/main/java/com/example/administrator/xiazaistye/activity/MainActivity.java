package com.example.administrator.xiazaistye.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.xiazaistye.R;
import com.example.administrator.xiazaistye.utilues.NextWoke;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //首先进行联网的判断
                boolean aviable = NextWoke.isNextWoke(MainActivity.this);
                if(!aviable){
                    //弹出dialog进行判断是否开启网络
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("是否进行联网的操作");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //开启联网
                            Intent in=new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                            startActivity(in);
                        }
                    });
                    builder.create().dismiss();
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder.create().dismiss();
                        }
                    });
                    builder.create().show();
                }else{
                    //进行下载,写个下载的方法
                    //连接成功下载
                    Toast.makeText(MainActivity.this, "网络连接成功", Toast.LENGTH_SHORT).show();

                    Download();

                }

            }
        });
    }

    private void Download() {
        final String[] str={"WiFi","手机流量"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("请选择下载的网络").setIcon(R.mipmap.ic_launcher);
        builder.setSingleChoiceItems(str, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        //进行下载
                        uploadApk();
                        builder.create().dismiss();
                        break;
                    case 1:
                        //手机流量下进行温馨提示
                        final AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                        builder1.setTitle("是否继续使用流");
                        builder1.setPositiveButton("继续使用流量", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //进行加载
                                uploadApk();
                                builder1.create().dismiss();
                            }
                        });
                        builder1.setNegativeButton("开启WIFI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent in=new Intent("android.settings.WIFI_SETTINGS");
                                startActivity(in);
                                builder.create().dismiss();
                            }
                        });
                        builder1.create().show();
                        break;
                }
            }
        });
        builder.create().show();

    }


    private void uploadApk() {
        RequestParams params = new RequestParams("http://mapp.qzone.qq.com/cgi-bin/mapp/mapp_subcatelist_qq?yyb_cateid=-10&categoryName=%E8%85%BE%E8%AE%AF%E8%BD%AF%E4%BB%B6&pageNo=1&pageSize=20&type=app&platform=touch&network_type=unknown&resolution=412x732");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("xxx", result.toString());
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("app");
                    JSONObject jo = jsonArray.getJSONObject(0);
                    //url  apk地址
                    String url = jo.getString("url");
                    String version = jo.getString("versionName");
                    Log.i("xxx", "url:" + url + ",versionName:" + version);
                    String versionName = getVersionName();
                    //判断versionName
                /*if (version.compareTo(versionName) > 0) {
                    showChoiseDialog(url);
                }*/
                    showChoiseDialog(url);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 获取版本名称
     */
    private String getVersionName() {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo info = null;
        try {
            info = packageManager.getPackageInfo(getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String versionName = info.versionName;
        return versionName;
    }

    /**
     * 获取版本号
     *
     * @return
     * @throws Exception
     */
    public String getVersionCode() {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String versionCode = String.valueOf(packInfo.versionCode);
        return versionCode;
    }

    /**
     * 显示更新选择对话框
     *
     * @param url
     */
    private void showChoiseDialog(final String url) {


        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        android.app.AlertDialog dialog = null;

        builder.setTitle("版本更新");
        builder.setMessage("检测到新版本，是否下载更新？");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //下载
                downLoadApk(url);
                builder.create().dismiss();

            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                builder.create().dismiss();
            }
        });
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    private void downLoadApk(String url) {
        //String url = "http://192.168.190.1:8080/08web/app.apk";

        RequestParams params = new RequestParams(url);
        //自定义保存路径 Environment.getExternalStorageDirectory() sdcard 根目录

        params.setSaveFilePath(Environment.getExternalStorageDirectory() + "/app/");
        //自动为文件命令
        params.setAutoRename(true);
        x.http().post(params, new Callback.ProgressCallback<File>() {

            //网络请求成功时回调
            @Override
            public void onSuccess(File result) {
                Toast.makeText(MainActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                //apk下载完成后 调用系统的安装方法
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(result), "application/vnd.android.package-archive");
               startActivity(intent);
                Toast.makeText(MainActivity.this, "安装成功", Toast.LENGTH_SHORT).show();

            }

            //网络请求错误时回调
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            //网络请求取消的时候回调
            @Override
            public void onCancelled(CancelledException cex) {

            }

            //网络请求完成的时候回调
            @Override
            public void onFinished() {

            }

            //网络请求之前回调
            @Override
            public void onWaiting() {

            }

            //网络请求开始的时候回调
            @Override
            public void onStarted() {

            }

            //下载的时候不断回调的方法
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                //文件总大小和当前进度
                Log.i("xxx", total + "," + current);

            }
        });

    }




}
