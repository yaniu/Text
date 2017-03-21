package com.example.administrator.yn_one.utulies;

import android.os.AsyncTask;
import android.util.Log;

import com.example.administrator.yn_one.bean.News;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * 作者：仝晓雅 on 2017/3/11 09:23
 * 类的注释：
 */

public class MyTask extends AsyncTask<String,Integer,String> {

    public  static  List<News.ResultBean.DataBean> list =new ArrayList<>();
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
                InputStream  is= response.getEntity().getContent();
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
            Log.i("xxx",list.get(i).getAuthor_name());
        }

    }
}
