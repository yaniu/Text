package com.example.administrator.yn_new.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
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
    //手势滑动，使用手势监视器这个对象GestureDetector
    final int RIGHT = 0;
    private GestureDetector gestureDetector;
    final int LEFT = 1;
    private WebView web;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //传入参数APPID
        mTencent = Tencent.createInstance(APP_ID, WebActivity.this.getApplicationContext());

        setContentView(R.layout.web_main);
        initView();
        dao = new NewsDao(WebActivity.this);

        web = (WebView) findViewById(R.id.w_web);
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
        //手指滑动
        gestureDetector=new GestureDetector(WebActivity.this,onGestureListener);

    }
    //手指滑动
    private GestureDetector.OnGestureListener onGestureListener=new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //e1就是初始状态的MotionEvent对象，e2就是滑动了过后的MotionEvent对象
            //velocityX和velocityY就是滑动的速率
            float x = e2.getX() - e1.getX();//滑动后的x值减去滑动前的x值 就是滑动的横向水平距离(x)
            float y = e2.getY() - e1.getY();//滑动后的y值减去滑动前的y值 就是滑动的纵向垂直距离(y)
            Log.w("tag", "x>" + x);
            Log.w("tag", "y>" + y);
            Log.w("tag", "velocityX>" + velocityX);
            Log.w("tag", "velocityY>" + velocityY);
            //如果滑动的横向距离大于100，表明是右滑了，那么就执行下面的方法，可以是关闭当前的activity
            if (x > 100) {
                doResult(RIGHT);
                Log.w("tag", "RIGHT>" + x);
            }
            //如果滑动的横向距离大于100，表明是左滑了(因为左滑为负数，所以距离大于100就是x值小于-100)
            if (x < -100) {
                Log.w("tag", "LEFT>" + x);
                doResult(LEFT);
            }

            return true;
        }
    };
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {    //注意这里不能用ONTOUCHEVENT方法，不然无效的
        gestureDetector.onTouchEvent(ev);

        web.onTouchEvent(ev);//这几行代码也要执行，将webview载入MotionEvent对象一下，况且用载入把，不知道用什么表述合适

        return super.dispatchTouchEvent(ev);
    }

    //触摸监听的方法
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                System.out.println(" ACTION_DOWN");//手指在屏幕上按下
                break;
            case MotionEvent.ACTION_MOVE:

                System.out.println(" ACTION_MOVE");//手指正在屏幕上滑动
                break;
            case MotionEvent.ACTION_UP:
                overridePendingTransition(R.anim.anim_1, R.anim.anim_2);
                System.out.println(" ACTION_UP");//手指从屏幕抬起了
                break;
            default:
                break;
        }
        return gestureDetector.onTouchEvent(event);
    }

    //结果
    public void doResult(int action) {

        switch (action) {
            case RIGHT:
                System.out.println("go right");

                finish();
                break;
            case LEFT:
                System.out.println("go left");
                break;
        }
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
