package com.example.administrator.yn_new.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.yn_new.R;
import com.example.administrator.yn_new.baen.XiaBean;
import com.example.administrator.yn_new.utiles.MyNexTO;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * 作者：仝晓雅 on 2017/3/28 08:27
 * 类的注释：
 */

public class XiaActivity extends Activity {
    private String path="http://mapp.qzone.qq.com/cgi-bin/mapp/mapp_subcatelist_qq?yyb_cateid=-10&categoryName=%E8%85%BE%E8%AE%AF%E8%BD%AF%E4%BB%B6&pageNo=1&pageSize=20&type=app&platform=touch&network_type=unknown&resolution=412x732";
    List<XiaBean> list =new ArrayList<>();
    private ListView lv;
    private  String   url1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xiazai_xml);
        initView();
        getServerDat();
      /*  lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                url1 = list.get(position).url;
                download();

            }
        });*/
    }

    private void getServerDat() {
        AsyncHttpClient client =new AsyncHttpClient();
        client.get(XiaActivity.this, path, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Log.i("xxx", responseString);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(responseString);
                    JSONArray jsonArray = jsonObject.getJSONArray("app");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject object = jsonArray.optJSONObject(i);
                        Gson g1=new Gson();
                        XiaBean bean  = g1.fromJson(object.toString(), XiaBean.class);
                        Log.i("llll",bean.toString());
                        list.add(bean);

                    }
                    lv.setAdapter(new MyList());
                } catch (Exception e) {
                }
            }
        });

    }

    class MyList extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView =View.inflate(XiaActivity.this,R.layout.xia_lv_text,null);
            TextView text = (TextView) convertView.findViewById(R.id.c_l_name);
           Button buttom = (Button) convertView.findViewById(R.id.c_l_button);
            text.setText(list.get(position).name);
            buttom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    url1 = list.get(position).url;
                    download();
                }
            });
            return convertView;
        }
    }
    private void initView() {
        lv = (ListView) findViewById(R.id.c_x_lv);
        ImageView img = (ImageView) findViewById(R.id.c_x_fan);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(XiaActivity.this,NextActivity.class);
                startActivity(intent);
            }
        });


    }
    //下载
    private void download() {
        final String[] items = {"wifi", "手机流量"};
        //参数-1 默认被选中的position
        new android.app.AlertDialog.Builder(this).setTitle("网络选择").setIcon(R.mipmap.ic_launcher).setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0:
                        //wifi下 下载apk
                        showChoiseDialog();
                        break;
                    case 1:
                        //手机流量下提醒用户
                        boolean mobile = MyNexTO.isNext(XiaActivity.this);
                        if (mobile) {
                            Toast.makeText(XiaActivity.this, "现在未使用wifi,将耗用手机流量", Toast.LENGTH_SHORT).show();
                            Intent wifiSettingsIntent = new Intent("android.settings.WIFI_SETTINGS");
                            startActivity(wifiSettingsIntent);
                        }

                        break;
                }
                dialog.dismiss();
            }
        }).show();
    }




    //获取版本名称
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


    private void showChoiseDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        android.app.AlertDialog dialog = null;

        builder.setTitle("版本更新");
        builder.setMessage("检测到新版本，是否下载更新？");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //下载
                downLoadApk();

            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    private void downLoadApk() {
        //String url = "http://192.168.190.1:8080/08web/app.apk";

        RequestParams params = new RequestParams(url1);
        //自定义保存路径 Environment.getExternalStorageDirectory() sdcard 根目录

        params.setSaveFilePath(Environment.getExternalStorageDirectory() + "/app/");
        //自动为文件命令
        params.setAutoRename(true);

        x.http().post(params, new Callback.ProgressCallback<File>() {

            private ProgressDialog prog;

            //网络请求成功时回调
            @Override
            public void onSuccess(File result) {
                Toast.makeText(XiaActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                //apk下载完成后 调用系统的安装方法
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(result), "application/vnd.android.package-archive");
                startActivity(intent);
                // Toast.makeText(DownActivity.this, "安装成功", Toast.LENGTH_SHORT).show();
                prog.dismiss();
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
                prog=new ProgressDialog(XiaActivity.this);
                prog.setTitle("正在下载");
                prog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                prog.setProgress(0);
                prog.show();
            }


            //下载的时候不断回调的方法
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                //文件总大小和当前进度
                prog.setProgress((int) current);
                prog.setMax((int) total);

            }
        });
    }

}
