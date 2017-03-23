package com.example.administrator.yn_new.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.yn_new.R;
import com.example.administrator.yn_new.activity.NextActivity;
import com.example.administrator.yn_new.activity.WebActivity;
import com.example.administrator.yn_new.baen.VidoeBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import cz.msebera.android.httpclient.Header;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import maqian.baidu.com.xlistviewlibrary.XListView;

/**
 * 作者：仝晓雅 on 2017/3/17 18:27
 * 类的注释：
 */

public class Fragment_v extends Fragment {


    private NextActivity activity;
    private ArrayList<VidoeBean> list =new ArrayList<>();
    private XListView xlv;
    private MyVoldeo voldeo;
    private Handler handler;
    int a=0;
    private String[] st = {"V9LG4CHOR", "V9LG4E6VR", "00850FRB"};

    private static final String APP_ID = "1105602574"; //获取的APPID
    private ShareUiListener mIUiListener;
    private Tencent mTencent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.vidoe_xlv,null);
        initView(view);

        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activity = (NextActivity) getActivity();
        handler=new Handler();
        //传入参数APPID
        mTencent = Tencent.createInstance(APP_ID, activity.getApplicationContext());
        getSrver(a);


    }


    public  void getSrver(int a){

         String http_url = "http://c.3g.163.com/nc/video/list/"+st[a]+"/n/10-10.html";
        getServerData(http_url);
    }
    private void getServerData(String http_url) {
        AsyncHttpClient client =new AsyncHttpClient();
        client.get(activity,http_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
               Gson gson =new Gson();
                try {
                    JSONObject obj =new JSONObject(responseString);
                    Iterator<String> keys = obj.keys();
                    while (keys.hasNext()){
                        String next = keys.next();
                        JSONArray array = obj.optJSONArray(next);
                        for(int i=0;i<array.length();i++){
                            JSONObject jsonObject = array.optJSONObject(i);
                            VidoeBean vidoeBean = gson.fromJson(jsonObject.toString(), VidoeBean.class);

                            list.add(vidoeBean);
                        }
                        if(voldeo==null){
                            voldeo = new MyVoldeo();
                            xlv.setAdapter(new MyVoldeo());
                        }else{
                            voldeo.notifyDataSetChanged();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null != mTencent) {
            mTencent.onActivityResult(requestCode, resultCode, data);
        }
    }


    class MyVoldeo  extends BaseAdapter{

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
           ViewHolder holder ;
            if(convertView ==null){
                holder =new ViewHolder();
                convertView =View.inflate(activity,R.layout.vidoe_main,null);

                holder.title = (TextView) convertView.findViewById(R.id.video_title);
                holder.name = (TextView) convertView.findViewById(R.id.video_name);
                holder.conn = (TextView) convertView.findViewById(R.id.video_connt);
                holder.zan = (TextView) convertView.findViewById(R.id.video_zan);
                holder.jcv= (JCVideoPlayerStandard) convertView.findViewById(R.id.video_jcv);
                holder.fen= (TextView) convertView.findViewById(R.id.view_fen);
                convertView.setTag(holder);

            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            holder.title.setText(list.get(position).getTitle());
            holder.name.setText(list.get(position).getTopicName());
            holder.conn.setText(list.get(position).getLength()+"万次播放");
            holder.zan.setText(list.get(position).getPlayCount()+"");

            boolean up = holder.jcv.setUp(list.get(position).getMp4_url(),  "");
            if(up){
                Glide.with(activity).load(list.get(position).getCover()).into(holder.jcv.thumbImageView);
            }
            holder.fen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    final AlertDialog alertDialog = builder.create();
                    View vs =View.inflate(activity,R.layout.web_alder,null);
                    Window window = alertDialog.getWindow();
                    window.setGravity(Gravity.BOTTOM);
                    RadioButton qq = (RadioButton) vs.findViewById(R.id.w_f_qq);
                    RadioButton kj = (RadioButton) vs.findViewById(R.id.w_f_kj);
                    TextView tx = (TextView) vs.findViewById(R.id.w_f_tx);
                    RadioButton xia = (RadioButton) vs.findViewById(R.id.w_f_xia);
                    tx.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                    xia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                            final AlertDialog alertDialog1 = builder1.create();
                            View view =View.inflate(activity,R.layout.v_v_xia_alder,null);
                            alertDialog1.setView(view);
                           Button yes = (Button) view.findViewById(R.id.v_v_xia_yes);
                            Button no = (Button) view.findViewById(R.id.v_v_xia_no);
                           final ProgressBar bar = (ProgressBar) view.findViewById(R.id.v_v_xia_jin);
                          no.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View v) {
                                  alertDialog1.dismiss();
                              }
                          });
                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    RequestParams params =new RequestParams(list.get(position).getMp4_url());
                                    params.setSaveFilePath(Environment.getExternalStorageDirectory()+"/"+list.get(position).getTitle());
                                    params.setUseCookie(true);
                                    params.setAutoRename(true);
                                    params.setAutoResume(false);
                                    x.http().get(params, new Callback.ProgressCallback<File>() {
                                        @Override
                                        public void onSuccess(File result) {
                                       alertDialog1.dismiss();
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

                                        @Override
                                        public void onWaiting() {

                                        }

                                        @Override
                                        public void onStarted() {

                                        }

                                        @Override
                                        public void onLoading(long total, long current, boolean isDownloading) {
                                          bar.setMax((int)total);
                                            bar.setProgress((int)current);
                                        }
                                    });
                                }
                            });
                          alertDialog1.show();
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
                            params.putString(QQShare.SHARE_TO_QQ_TITLE, list.get(position).getTitle());//分享标题
                            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,list.get(position).getMp4_url());//内容地址
                            mTencent.shareToQQ(activity, params, new ShareUiListener());
                            Toast.makeText(activity, "成功", Toast.LENGTH_SHORT).show();
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
                            params.putString(QzoneShare.SHARE_TO_QQ_TITLE, list.get(position).getTitle());//分享标题
                            params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, list.get(position).getMp4_url() );//分享的链接
                            mTencent.shareToQzone(activity, params, new ShareUiListener());
                            Toast.makeText(activity, "成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alertDialog.setView(vs);
                    alertDialog.dismiss();
                    alertDialog.show();

                }
            });

            return convertView;
        }
        class ViewHolder {
            TextView title;
             JCVideoPlayerStandard jcv;
            TextView name;
            TextView conn;
            TextView zan;
            TextView fen;

        }
    }


    private void initView(View view) {
        xlv = (XListView) view.findViewById(R.id.voideo_xlv);
        xlv.setPullRefreshEnable(true);
        xlv.setPullLoadEnable(true);
        xlv.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
               getSrver(a);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        xlv.stopRefresh();
                    }
                },2000);
            }

            @Override
            public void onLoadMore() {
                a++;
                getSrver(a);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xlv.stopLoadMore();
                    }
                }, 2000);

            }
        });

    }
}
