package com.example.administrator.tongxiaoya1502l2017321.fragment;

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
import android.widget.Toast;

import com.example.administrator.tongxiaoya1502l2017321.R;
import com.example.administrator.tongxiaoya1502l2017321.activity.MainActivity;
import com.example.administrator.tongxiaoya1502l2017321.bean.News;
import com.example.administrator.tongxiaoya1502l2017321.utilues.Utilues;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * 作者：仝晓雅 on 2017/3/21 08:45
 * 类的注释：
 */

public class Fragment1 extends Fragment {
    private String path="http://www.meirixue.com/api.php?c=category&a=getall";
    private ListView lv;
    ArrayList<String> list =new ArrayList<>();
    ArrayList<News.NodesBean> list1 =new ArrayList<>();
    String [] str =new String[]{"热门分类","多彩生活","兴趣爱好","职场提高","考试考级","语言学习"};
    private MainActivity acyivity;
    String [] str1 =new String[]{"热门,","多彩","兴趣","职场","考试","语言"};
    ArrayList<String> list2 =new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.f1_main,null);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       for(int i =0;i<str.length;i++){
           list.add(str[i]);

       }
        for(int j =0;j<str1.length;j++){
            list2.add(str1[j]);
        }
        lv.setAdapter(new MyList());
       // getSerbverData();
    }

    private void getSerbverData() {
        AsyncHttpClient client =new AsyncHttpClient();
        client.get(getActivity(), path, new TextHttpResponseHandler() {



            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Toast.makeText(getActivity(), ""+responseString, Toast.LENGTH_SHORT).show();
              /*  Gson gson =new Gson();
                News news = gson.fromJson(responseString, News.class);
                String  c = news.getCname();
                list.add(c);
                List<News.NodesBean> nodes = news.getNodes();
                for(int i =0;i<nodes.size();i++){
                    list1.add(nodes.get(i));
                }*/




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
            convertView =View.inflate(getActivity(),R.layout.f1_lv_text_main,null);
            TextView text = (TextView) convertView.findViewById(R.id.f1_l_text);
            text.setText(list.get(position));
            return convertView;
        }
    }

    private void initView(View view) {
        acyivity = (MainActivity) getActivity();
        lv = (ListView) view.findViewById(R.id.f1_lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               Fragment2 fragment2 = (Fragment2) acyivity.getSupportFragmentManager().findFragmentById(R.id.f2);
              fragment2.getNae(list2.get(position));
            }
        });
    }
}
