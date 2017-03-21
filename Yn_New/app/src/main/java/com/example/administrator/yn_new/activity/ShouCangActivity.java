package com.example.administrator.yn_new.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.yn_new.R;
import com.example.administrator.yn_new.baen.ShouBean;
import com.example.administrator.yn_new.helper.NewsDao;

import org.xutils.x;

import java.util.ArrayList;

import maqian.baidu.com.xlistviewlibrary.XListView;

/**
 * 作者：仝晓雅 on 2017/3/20 20:14
 * 类的注释：
 */

public class ShouCangActivity extends Activity {
    private Handler handler;
    private XListView list1;
    private ArrayList<ShouBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_fragment_main);
        NewsDao dao =new NewsDao(ShouCangActivity.this);
        list = dao.query_SC();
        list1 = (XListView) findViewById(R.id.f_list);
        list1.setPullLoadEnable(true);
        list1.setPullRefreshEnable(true);
        list1.setAdapter(new MyList());
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(ShouCangActivity.this,WebActivity.class);
               intent.putExtra("name",list.get(position-1).title);
                intent.putExtra("text1",list.get(position-1).text1);
                intent.putExtra("text2",list.get(position-1).text2);
                intent.putExtra("text3",list.get(position-1).text3);
                intent.putExtra("pic1",list.get(position-1).pic1);
                intent.putExtra("pic2",list.get(position-1).pic2);
                intent.putExtra("pic3",list.get(position-1).pic3);
                intent.putExtra("url",list.get(position-1).url);
                startActivity(intent);
            }
        });
        list1.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list1.stopRefresh();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list1.stopLoadMore();
                    }
                }, 2000);
            }
        });
    }
    class MyList extends BaseAdapter {

        final int type1 = 0;
        final int type2 = 1;
        final int type3 = 2;

        @Override
        public int getViewTypeCount() {
            return 3;

        }
        @Override
        public int getItemViewType(int position) {
            if (list.get(position).pic1 != null && list.get(position).pic2 == null && list.get(position).pic3 == null) {
                return type1;
            } else if (list.get(position).pic1 != null && list.get(position).pic2!= null && list.get(position).pic3 == null) {
                return type2;
            } else  if(list.get(position).pic1 != null && list.get(position).pic2 != null && list.get(position).pic3 != null){
                return type3;
            }
            return type3;
        }

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
            int Type = getItemViewType(position);
            if (convertView == null) {
                holder = new ViewHolder();
                switch (Type) {
                    case type1:
                        convertView = View.inflate(ShouCangActivity.this, R.layout.re_1, null);
                        holder.t1_text1 = (TextView) convertView.findViewById(R.id.r1_text);
                        holder.t1_text2 = (TextView) convertView.findViewById(R.id.r1_text2);
                        holder.t1_text3 = (TextView) convertView.findViewById(R.id.r1_text3);
                        holder.t1_text4 = (TextView) convertView.findViewById(R.id.r1_text4);
                        holder.pic1 = (ImageView) convertView.findViewById(R.id.r1_pic1);

                        break;
                    case type2:
                        convertView = View.inflate(ShouCangActivity.this, R.layout.re_2, null);
                        holder.t1_text1 = (TextView) convertView.findViewById(R.id.r2_text1);
                        holder.t1_text2 = (TextView) convertView.findViewById(R.id.r2_text2);
                        holder.t1_text3 = (TextView) convertView.findViewById(R.id.r2_text3);
                        holder.t1_text4 = (TextView) convertView.findViewById(R.id.r2_text4);
                        holder.pic1 = (ImageView) convertView.findViewById(R.id.r2_pic1);
                        holder.pic2 = (ImageView) convertView.findViewById(R.id.r2_pic2);

                        break;
                    case type3:
                        convertView = View.inflate(ShouCangActivity.this, R.layout.re_3, null);
                        holder.t1_text1 = (TextView) convertView.findViewById(R.id.r3_text1);
                        holder.t1_text2 = (TextView) convertView.findViewById(R.id.r3_text2);
                        holder.t1_text3 = (TextView) convertView.findViewById(R.id.r3_text3);
                        holder.t1_text4 = (TextView) convertView.findViewById(R.id.r3_text4);
                        holder.pic1 = (ImageView) convertView.findViewById(R.id.r3_pic1);
                        holder.pic2 = (ImageView) convertView.findViewById(R.id.r3_pic2);
                        holder.pic3 = (ImageView) convertView.findViewById(R.id.r3_pic3);


                        break;
                }
                convertView.setTag(holder);
            } else {
                holder = (MyList.ViewHolder) convertView.getTag();
            }
            switch (Type) {
                case type1:
                    holder.t1_text1.setText(list.get(position).title);
                    holder.t1_text2.setText(list.get(position).text1);
                    holder.t1_text3.setText(list.get(position).text2);
                    holder.t1_text4.setText(list.get(position).text3);
                    x.image().bind(holder.pic1, list.get(position).pic1);

                    break;
                case type2:
                    holder.t1_text1.setText(list.get(position).title);
                    holder.t1_text2.setText(list.get(position).text1);
                    holder.t1_text3.setText(list.get(position).text2);
                    holder.t1_text4.setText(list.get(position).text3);
                    x.image().bind(holder.pic1, list.get(position).pic1);
                    x.image().bind(holder.pic2, list.get(position).pic2);

                    break;
                case type3:
                    holder.t1_text1.setText(list.get(position).title);
                    holder.t1_text2.setText(list.get(position).text1);
                    holder.t1_text3.setText(list.get(position).text2);
                    holder.t1_text4.setText(list.get(position).text3);
                    x.image().bind(holder.pic1, list.get(position).pic1);
                    x.image().bind(holder.pic2, list.get(position).pic2);
                    x.image().bind(holder.pic3, list.get(position).pic3);
                    break;
            }


            return convertView;
        }

        class ViewHolder{
            TextView t1_text1;
            TextView t1_text2;
            TextView t1_text3;
            TextView t1_text4;
            ImageView pic1;
            ImageView pic2;
            ImageView pic3;
        }
    }
}
