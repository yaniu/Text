package com.example.administrator.yn_new.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.yn_new.R;
import com.example.administrator.yn_new.baen.ShouBean;
import com.example.administrator.yn_new.helper.NewsDao;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;

/**
 * 作者：仝晓雅 on 2017/3/15 19:27
 * 类的注释：（Web展示）
 */

public class WebActivity extends Activity {

    private EditText edit;
    private ImageView say;
    private ImageView shou;
    private ImageView xiang;
    private static final String APP_ID = "1105602574"; //获取的APPID
    private ShareUiListener mIUiListener;
    private Tencent mTencent;
    private String url;
    private String name;
    private String text1;
    private String text2;
    private String text3;
    private String pic1;
    private String pic2;
    private String pic3;
    private NewsDao dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //传入参数APPID
        mTencent = Tencent.createInstance(APP_ID, WebActivity.this.getApplicationContext());

        setContentView(R.layout.web_main);
        initView();
        dao = new NewsDao(WebActivity.this);

        WebView web = (WebView) findViewById(R.id.w_web);
        TextView text = (TextView) findViewById(R.id.w_text);

        Intent intent =getIntent();
        url = intent.getStringExtra("url");
      name= intent.getStringExtra("name");
        text1 = intent.getStringExtra("text2");
        text2 = intent.getStringExtra("text3");
        text3 = intent.getStringExtra("text4");
        pic1 = intent.getStringExtra("pic1");
        pic2 = intent.getStringExtra("pic2");
        pic3 = intent.getStringExtra("pic3");


        web.loadUrl(url);
        findViewById(R.id.w_fan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(WebActivity.this,NextActivity.class);
                startActivity(in);

            }
        });


    }


    /**
     * 自定义监听器实现IUiListener，需要3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class ShareUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            //分享成功

        }

        @Override
        public void onError(UiError uiError) {
            //分享失败

        }

        @Override
        public void onCancel() {
            //分享取消

        }
    }

    /**
     * 回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null != mTencent) {
            mTencent.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void initView() {
        edit = (EditText) findViewById(R.id.w_edit);
        say = (ImageView) findViewById(R.id.w_say);
        shou = (ImageView) findViewById(R.id.w_sc);
        xiang = (ImageView) findViewById(R.id.w_fen);
        edit.setFocusable(false);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WebActivity.this);
                final AlertDialog dialog = builder.create();
                View view =View.inflate(WebActivity.this,R.layout.edite_alder,null);
                dialog.setView(view);
                Window window = dialog.getWindow();
                window.setGravity(Gravity.BOTTOM);
                view.findViewById(R.id.w_e_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(WebActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        shou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ArrayList<ShouBean> shouBeen = dao.query_SC();
                boolean falg =true;
                for(ShouBean s:shouBeen){
                    if(s.title.equals(name)) {
                        falg=false;
                    }
                }
                if(falg){
                    dao.sc_add(name, text1, text2, text3, pic1, pic2, pic3, url);
                    shou.setSelected(true);
                    Toast.makeText(WebActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(WebActivity.this, "已收藏过", Toast.LENGTH_SHORT).show();
                }



            }
        });
        xiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(WebActivity.this);
                final AlertDialog alertDialog = builder.create();
                View vs =View.inflate(WebActivity.this,R.layout.web_alder,null);
                Window window = alertDialog.getWindow();
                window.setGravity(Gravity.BOTTOM);
                RadioButton qq = (RadioButton) vs.findViewById(R.id.w_f_qq);
                RadioButton kj = (RadioButton) vs.findViewById(R.id.w_f_kj);
                TextView tx = (TextView) vs.findViewById(R.id.w_f_tx);
                tx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                qq.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        final Bundle params = new Bundle();
                        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);//分享的类型
                     /*   params.putString(QQShare.SHARE_TO_QQ_TITLE, "然了个然CSDN博客");//分享标题
                        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,"不管是怎样的过程,最终目的还是那个理想的结果。");//要分享的内容摘要
                        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,"http://blog.csdn.net/sandyran");//内容地址
                        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://avatar.csdn.net/B/3/F/1_sandyran.jpg");//分享的图片URL
                        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试");//应用名称*/
                        params.putString(QQShare.SHARE_TO_QQ_TITLE, name);//分享标题
                        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,url);//内容地址
                        mTencent.shareToQQ(WebActivity.this, params, new ShareUiListener());
                        Toast.makeText(WebActivity.this, "成功", Toast.LENGTH_SHORT).show();
                    }
                });
                kj.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        int QzoneType = QzoneShare.SHARE_TO_QZONE_TYPE_NO_TYPE;
                        Bundle params = new Bundle();
                        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneType);
                       /* params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "然了个然CSDN博客");//分享标题
                        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "不管是怎样的过程,最终目的还是那个理想的结果。");//分享的内容摘要
                        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://blog.csdn.net/sandyran/article/details/53204529");//分享的链接
                        //分享的图片, 以ArrayList<String>的类型传入，以便支持多张图片（注：图片最多支持9张图片，多余的图片会被丢弃）
                        ArrayList<String> imageUrls = new ArrayList<String>();
                        imageUrls.add("http://avatar.csdn.net/B/3/F/1_sandyran.jpg");//添加一个图片地址
                        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);//分享的图片URL*/
                        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, name);//分享标题
                        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);//分享的链接
                        mTencent.shareToQzone(WebActivity.this, params, new ShareUiListener());
                        Toast.makeText(WebActivity.this, "成功", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.setView(vs);
                alertDialog.dismiss();
                alertDialog.show();
            }
        });

    }
}
