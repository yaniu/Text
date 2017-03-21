package com.example.administrator.yn_new.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.yn_new.R;
import com.example.administrator.yn_new.activity.NextActivity;
import com.example.administrator.yn_new.baen.VidoeBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private String http_url = "http://c.3g.163.com/nc/video/list/"+st[a]+"/n/10-10.html";

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
        getServerData();

    }

    private void getServerData() {
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
        public View getView(int position, View convertView, ViewGroup parent) {
           ViewHolder holder ;
            if(convertView ==null){
                holder =new ViewHolder();
                convertView =View.inflate(activity,R.layout.vidoe_main,null);

                holder.title = (TextView) convertView.findViewById(R.id.video_title);
                holder.name = (TextView) convertView.findViewById(R.id.video_name);
                holder.conn = (TextView) convertView.findViewById(R.id.video_connt);
                holder.zan = (TextView) convertView.findViewById(R.id.video_zan);
                holder.jcv= (JCVideoPlayerStandard) convertView.findViewById(R.id.video_jcv);
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

            return convertView;
        }
        class ViewHolder {
            TextView title;
             JCVideoPlayerStandard jcv;
            TextView name;
            TextView conn;
            TextView zan;

        }
    }
    private void initView(View view) {
        xlv = (XListView) view.findViewById(R.id.voideo_xlv);
        xlv.setPullRefreshEnable(true);
        xlv.setPullLoadEnable(true);
        xlv.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
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
                getServerData();
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
