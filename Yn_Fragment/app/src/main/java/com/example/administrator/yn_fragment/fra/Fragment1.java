package com.example.administrator.yn_fragment.fra;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.yn_fragment.R;
import com.example.administrator.yn_fragment.activity.MainActivity;
import com.example.administrator.yn_fragment.bean.News;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * 作者：仝晓雅 on 2017/3/21 10:35
 * 类的注释：
 */

public class Fragment1 extends Fragment {


    private ListView lv;
    private MainActivity activity;
    private  List<News> nlist;
    private ArrayList<String> listm=new ArrayList<>() ;
    private OnThresValues onThresValues;
    private TextView tv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view =inflater.inflate(R.layout.f1_main,null);
        initView(view);
        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getServerData();
    }

    private void getServerData() {
        activity = (MainActivity) getActivity();
        AsyncHttpClient client =new AsyncHttpClient();
        String path="http://www.meirixue.com/api.php?c=category&a=getall";
        client.get(activity, path, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson =new Gson();
                Type type = new TypeToken<ArrayList<News>>() {}.getType();
                nlist=gson.fromJson(responseString,type);
                for(int i=0;i<nlist.size();i++){
                    String c = nlist.get(i).getCname();
                    listm.add(c);


                }
                lv.setAdapter(new MyList());

            }
        });
    }



    class MyList extends BaseAdapter{



        @Override
        public int getCount() {
            return listm.size();
        }

        @Override
        public Object getItem(int position) {
            return listm.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           convertView =View.inflate(activity,R.layout.f1_text,null);
            tv = (TextView) convertView.findViewById(R.id.f1_tv);
            tv.setText(listm.get(position).toString());

            return convertView;
        }
    }

    private void initView(View view) {
        lv = (ListView) view.findViewById(R.id.f1_lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                onThresValues.OnThres((ArrayList<News.NodesBean>) nlist.get(position).getNodes());
            }
        });

    }
    public  interface  OnThresValues{
        public  void OnThres(ArrayList<News.NodesBean> bean);
    }
    public  void setOnThresValues(OnThresValues onThresValues){
        this.onThresValues =onThresValues;

    }
}
