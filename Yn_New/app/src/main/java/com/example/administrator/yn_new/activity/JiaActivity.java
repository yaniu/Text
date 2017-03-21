package com.example.administrator.yn_new.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.yn_new.R;
import com.example.administrator.yn_new.baen.Biao;
import com.example.administrator.yn_new.helper.NewsDao;

import java.util.ArrayList;

/**
 * 作者：仝晓雅 on 2017/3/15 20:33
 * 类的注释：加号选择频道（Grid条目展示）
 */

public class JiaActivity extends Activity {


    private GridView grid1;
    private GridView grid2;
    private ArrayList<Biao> list1;
    private ArrayList<Biao> str1;
    private ArrayList<Biao> list2;
    private ArrayList<Biao> str2;
    private MyGrid2 my2;
    private MyGrid1 my1;
    private NewsDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.jia_main);
        dao = new NewsDao(JiaActivity.this);
       /* str2 = new ArrayList<>();
        String [] strs =new String[]{"小说","历史","美食","时尚","育儿","搞笑",
                "数码","养生","宠物","家具","旅游","文化","星座","精选",
                "收藏","股票","情感","唱将",
        };
        Biao biao2 =new Biao();
        for(int j = 0; j< strs.length; j++){

            biao2.text =strs[j];

            str2.add(biao2);
            dao.jia_grid2(strs[j]);


        }*/
        initView();


    }

    private void initView() {
        ImageView jia_fan = (ImageView) findViewById(R.id.jia_fan);
        jia_fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(JiaActivity.this,NextActivity.class);
                startActivity(intent);
            }
        });
        grid1 = (GridView) findViewById(R.id.jia_grid1);
        grid2 = (GridView) findViewById(R.id.jia_grid2);

       /* str1 = new ArrayList<>();
        String [] str =new String[]{"阳光","热点","北京","社会",
                "订阅","娱乐","科技","汽车","体育","财经"
        };
        final Biao biao =new Biao();
        for(int i = 0; i< str.length; i++){

            biao.text =str[i];

           str1.add(biao);
        dao.jia_grid1(str[i]);


        }*/
        list1 = dao.jia_grid1_query();
        my1 = new MyGrid1();
        grid1.setAdapter(my1);
        list2 = dao.jia_grid2_query();
        my2 = new MyGrid2();
        grid2.setAdapter(my2);
        grid1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Intent intent =new Intent(JiaActivity.this,NextActivity.class);
                 intent.putExtra("url",position);
                startActivity(intent);
            }
        });
      grid2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              String url ="http://v.juhe.cn/toutiao/index?type=shehui&key=32b9973df2e6ee0c2bf094b61c7d7844";
              dao.jia_grid1(list2.get(position).text,url);
              Biao bean =new Biao();
              String text = list2.get(position).text;
              int i = list2.get(position).id;
              bean.id=i;
              bean.text=text;
              bean.url=url;
              list1.add(bean);
              int ids = list2.get(position).id;
              dao.jia_grid2_delete(ids);
              list2.remove(position);
              my1.notifyDataSetChanged();
              my2.notifyDataSetChanged();
          /*    sd.execSQL("insert into title(name) values('"+titless.get(position)+"')");
              titles.add(titless.get(position));
              sd.execSQL("delete from titlemore where name='"+titless.get(position)+"'");
              titless.remove(position);
              adapter1.notifyDataSetChanged();
              adapter2.notifyDataSetChanged();*/








          }
      });

    }
    class MyGrid2 extends BaseAdapter{

        @Override
        public int getCount() {
            return list2.size();
        }

        @Override
        public Object getItem(int position) {
            return list2.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView =View.inflate(JiaActivity.this,R.layout.jia_grid2,null);
            TextView text1 = (TextView) convertView.findViewById(R.id.jia_grid2_text);
            text1.setText(list2.get(position).text);
            return convertView;
        }
    }
    class MyGrid1 extends BaseAdapter{

        @Override
        public int getCount() {
            return list1.size();
        }

        @Override
        public Object getItem(int position) {
            return list1.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView =View.inflate(JiaActivity.this,R.layout.jia_grid1,null);
            TextView text1 = (TextView) convertView.findViewById(R.id.jia_grid1_text);
            text1.setText(list1.get(position).text);
            return convertView;
        }
    }
}
