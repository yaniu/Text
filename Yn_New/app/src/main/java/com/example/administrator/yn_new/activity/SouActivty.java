package com.example.administrator.yn_new.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.yn_new.R;
import com.example.administrator.yn_new.baen.SouBean;

import java.util.ArrayList;

/**
 * 作者：仝晓雅 on 2017/3/15 11:03
 * 类的注释：（搜索界面）
 */

public class SouActivty extends Activity {

    private ListView listview;
    private ArrayList<SouBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sou_man);
        initView();
        getServerData();
    }

    private void getServerData() {

        list = new ArrayList<>();
        String [] str1 =new String[]{"奇闻轶事","电击","棋牌",
                "棋牌游戏","宠物","F1","搞笑",
                "游艇","娱乐","社会",};
        String [] str2 =new String[]{"已有12.6万人关注","已有11.2万人关注","已有7.5万人关注",
                "已有1.6万人关注","已有15.6万人关注","已有10.6万人关注","已有8.8万人关注",
                "已有15万人关注","已有23.6万人关注","已有2.6万人关注",};
        int  [] pic =new int[]{R.mipmap.qqicon_login_profile,R.mipmap.renren_sdk_login,
                R.mipmap.sinaicon_login_profile,R.mipmap.tianyi_login,R.mipmap.weibo_sdk_login,
                R.mipmap.qqicon_login_profile,R.mipmap.renren_sdk_login,
                R.mipmap.sinaicon_login_profile,R.mipmap.tianyi_login,R.mipmap.weibo_sdk_login
        };
        for(int i =0;i<10;i++){
            SouBean  bean =new SouBean();
            bean.text1=str1[i];
            bean.text2=str2[i];
            bean.pic=pic[i];
            list.add(bean);
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
            convertView=View.inflate(SouActivty.this,R.layout.sou_list,null);
            ImageView pic = (ImageView) convertView.findViewById(R.id.sou_list_pic);
            TextView text1= (TextView) convertView.findViewById(R.id.sou_list_text1);
            TextView text2= (TextView) convertView.findViewById(R.id.sou_list_text2);
            pic.setImageResource(list.get(position).pic);
            text1.setText(list.get(position).text1);
            text2.setText(list.get(position).text2);
            return convertView;
        }

    }
    private void initView() {
        ImageView fan = (ImageView) findViewById(R.id.sou_fan);
        EditText edit = (EditText) findViewById(R.id.sou_edit);
        listview = (ListView) findViewById(R.id.sou_list);
        fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SouActivty.this,NextActivity.class);
                startActivity(intent);
            }
        });

    }
}
