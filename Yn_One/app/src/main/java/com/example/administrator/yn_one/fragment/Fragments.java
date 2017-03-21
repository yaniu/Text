package com.example.administrator.yn_one.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.yn_one.R;
import com.example.administrator.yn_one.activity.MainActivity;
import com.example.administrator.yn_one.activity.NextActivity;
import com.example.administrator.yn_one.bean.News;
import com.example.administrator.yn_one.utulies.MyTask;
import com.example.administrator.yn_one.utulies.Utilues;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import maqian.baidu.com.xlistviewlibrary.XListView;

import static android.R.id.list;

/**
 * 作者：仝晓雅 on 2017/3/11 08:44
 * 类的注释：
 */

public class Fragments extends Fragment {

    private String url;
    private Handler handler =null;
    private   List<News.ResultBean.DataBean> list =new ArrayList<>();
    private XListView xlv;

    public void setUrl(String url) {
        this.url = url;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view =inflater.inflate(R.layout.fragment_main,null);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        handler =new Handler();
        getServerData();
    }

    private void getServerData() {
       MyTask myTask =new MyTask();
        myTask.execute(url);
    }
    class MyTask extends AsyncTask<String,Integer,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String url =params[0];
            HttpClient client =new DefaultHttpClient();
            HttpGet get =new HttpGet(url);
            try {
                HttpResponse response = client.execute(get);
                if(response.getStatusLine().getStatusCode()==200){
                    InputStream is= response.getEntity().getContent();
                    String s = Utilues.path(is);
                    return  s;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson =new Gson();
            News news = gson.fromJson(s, News.class);
            List<News.ResultBean.DataBean> data = news.getResult().getData();
            for(int i=0;i<data.size();i++){
                list.add(data.get(i));

            }
            xlv.setAdapter(new MyList());

            xlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent =new Intent(getActivity(),NextActivity.class);
                    intent.putExtra("url",list.get(position).getUrl());
                    startActivity(intent);

                }
            });

        }
    }

    private void initView(View view) {
        xlv = (XListView) view.findViewById(R.id.xlv);
        xlv.setPullLoadEnable(true);
        xlv.setPullRefreshEnable(true);

        xlv.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                getServerData();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xlv.stopRefresh();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                getServerData();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xlv.stopRefresh();
                    }
                }, 2000);
            }
        });

    }
    class MyList extends BaseAdapter{

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
                convertView =View.inflate(getActivity(),R.layout.list_main,null);
                holder.pic= (ImageView) convertView.findViewById(R.id.l_pic);
                holder.title= (TextView) convertView.findViewById(R.id.l_title);
                holder.conn= (TextView) convertView.findViewById(R.id.l_conn);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.conn.setText(list.get(position).getTitle());
            holder.title.setText(list.get(position).getAuthor_name());
            ImageLoader.getInstance().displayImage(list.get(position).getThumbnail_pic_s(),holder.pic);

            return convertView;
        }
        class ViewHolder{
            ImageView pic ;
            TextView title;
            TextView conn;
        }
    }
}
