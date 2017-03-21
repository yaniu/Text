package com.example.administrator.yn_twodemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.yn_twodemo.QQBean;
import com.example.administrator.yn_twodemo.R;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * 作者：仝晓雅 on 2017/3/19 18:46
 * 类的注释：
 */

public class NextActivity extends Activity {

    private ListView listview;
    private   List<QQBean.DataBean> list =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_main);
        initView();
        getSreverData();
    }

    private void getSreverData() {
        AsyncHttpClient client =new AsyncHttpClient();
        String url ="http://mock.eoapi.cn/success/aDpzG1ZiKPbEI6JdXjqasb958Q1rBg9j";
        client.get(NextActivity.this, url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
             Gson gson =new Gson();
                QQBean qqBean = gson.fromJson(responseString, QQBean.class);
                List<QQBean.DataBean> data = qqBean.getData();
                for(int i =0;i<data.size();i++){
                    list.add(data.get(i));
                }
listview.setAdapter(new MyList());
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
            ViewHolder holder;
            if(convertView==null){
                holder =new ViewHolder();
                convertView =View.inflate(NextActivity.this,R.layout.list_main,null);
                holder.pic= (ImageView) convertView.findViewById(R.id.q_pic);
             holder.text1= (TextView) convertView.findViewById(R.id.q_text1);
               holder.text2= (TextView) convertView.findViewById(R.id.q_text2);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.text1.setText(list.get(position).getTitle());
            holder.text2.setText(list.get(position).getContent());

            x.image().bind(holder.pic,list.get(position).getImage_url());
            return convertView;
        }
        class ViewHolder{
            ImageView pic;
            TextView text1;
            TextView text2;
        }
    }
    private void initView() {
        listview = (ListView) findViewById(R.id.t_lv);

    }
}
