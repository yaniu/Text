package com.example.administrator.yn_one_b.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.yn_one_b.R;
import com.example.administrator.yn_one_b.activity.NextActivity;
import com.example.administrator.yn_one_b.bean.News;
import com.example.administrator.yn_one_b.utiles.Utiles;
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

/**
 * 作者：仝晓雅 on 2017/3/12 10:22
 * 类的注释：
 */

public class Fragmnet_s extends Fragment {
    private  List<News.ResultBean.DataBean> list =new ArrayList<>();
    private ListView listview;
    private  String url ;


    public void setUrl(String url) {
        this.url = url;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view =inflater.inflate(R.layout.fragmnet_main,null);
        initView(view);
        return  view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getServerData();
    }

    private void getServerData() {
        MyTask my =new MyTask();
        my.execute(url);

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
                    InputStream is = response.getEntity().getContent();
                    String s = Utiles.path(is);
                    return s;
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
            Gson gaon =new Gson();
            News news = gaon.fromJson(s, News.class);
            List<News.ResultBean.DataBean> data = news.getResult().getData();
             for(int i =0;i<data.size();i++){
                 list.add(data.get(i));
             }
            listview.setAdapter(new MyList());

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
                if(convertView==null){
                    holder =new ViewHolder();
                   convertView =View.inflate(getActivity(),R.layout.list_main,null);
                    holder.title= (TextView) convertView.findViewById(R.id.l_title);
                    holder.conn= (TextView) convertView.findViewById(R.id.l_conn);
                    holder.pic = (ImageView) convertView.findViewById(R.id.l_pic);
                    convertView.setTag(holder);
                }else{
                    holder= (ViewHolder) convertView.getTag();
                }
                holder.title.setText(list.get(position).getAuthor_name());
                holder.conn.setText(list.get(position).getTitle());
                ImageLoader.getInstance().displayImage(list.get(position).getThumbnail_pic_s(),holder.pic);

                return convertView;
            }
            class ViewHolder{
                ImageView pic;
                TextView title;
                TextView conn;

            }
        }
    }

    private void initView(View view) {
        listview = (ListView) view.findViewById(R.id.list);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(getActivity(), NextActivity.class);
                intent.putExtra("url",list.get(position).getUrl());
                startActivity(intent);
            }
        });

    }
}
