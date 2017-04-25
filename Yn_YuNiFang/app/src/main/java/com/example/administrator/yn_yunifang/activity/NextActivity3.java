package com.example.administrator.yn_yunifang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.yn_yunifang.R;
import com.example.administrator.yn_yunifang.bean.GoodsBean;
import com.example.administrator.yn_yunifang.bean.InfoBean;
import com.example.administrator.yn_yunifang.view.MyCallBack;
import com.example.administrator.yn_yunifang.view.OkHttpUtils;
import com.example.administrator.yn_yunifang.view.QQListView;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * 作者：仝晓雅 on 2017/4/13 21:15
 * 类的注释：
 */

public class NextActivity3 extends Fragment {

    private NextActivity activity;
    private View view;
    private   List<GoodsBean.CartItemList> list  =new ArrayList<>();
    private QQListView listview;
    private RelativeLayout lin_shop;
    private RelativeLayout lin_xiang;
    private CheckBox da_box;
    private boolean checked;
    private MyList my;
    private TextView price;
    private TextView f3_num;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null){
            view = inflater.inflate(R.layout.next3_main,null);
        }
        ViewGroup  parent = (ViewGroup) view.getParent();
        if(parent!=null){
            parent.addView(view);
        }
        initView(view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (NextActivity) context;
    }

    private void initView(View view) {
        lin_shop = (RelativeLayout) view.findViewById(R.id.f3_lin2);
        lin_xiang = (RelativeLayout) view.findViewById(R.id.f3_xiang);
        Button guang = (Button) view.findViewById(R.id.f3_guang);
        price = (TextView) view.findViewById(R.id.f3_price);
        TextView jiesuan = (TextView) view.findViewById(R.id.f3_jiesuan);
        f3_num = (TextView) view.findViewById(R.id.f3_num);
        listview = (QQListView) view.findViewById(R.id.f3_listview);

        da_box = (CheckBox) view.findViewById(R.id.f3_box);
        da_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked = ((CheckBox) v).isChecked();
                if(checked){
                    for(int i =0;i<list.size();i++){
                        list.get(i).setFlag(true);
                    }
                    my.notifyDataSetChanged();
                }else{
                    for(int i =0;i<list.size();i++){
                        list.get(i).setFlag(false);
                    }
                    my.notifyDataSetChanged();
                }
                setNum();
            }
        });
         final boolean falg =LogActivity.falg;
        if(falg==true){
            questDatas();
        }

        jiesuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_l =new Intent(activity,ZhiActivity_lie.class);
                intent_l.putExtra("l_list", (Serializable) list);
                //intent.putExtra("z_position",posotion);
                startActivity(intent_l);
            }
        });

        guang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(activity,NextActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onResume() {
        super.onResume();
        questDatas();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
           ViewHolder holder ;
            if(convertView ==null){
                convertView =View.inflate(activity,R.layout.list2_maiin,null);
                holder =new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.l_name);
                holder.price = (TextView) convertView.findViewById(R.id.l_price);
                holder.num = (TextView) convertView.findViewById(R.id.l_num);
                holder.pic = (ImageView) convertView.findViewById(R.id.l_pic);
                holder.box = (CheckBox) convertView.findViewById(R.id.l_box);
                holder.delete = (TextView) convertView.findViewById(R.id.delete);
                convertView.setTag(holder);


            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.name.setText(list.get(position).getName());

            holder.price.setText("￥"+list.get(position).getPrice()+"");
            holder.num.setText(list.get(position).getCount()+"");
            Picasso.with(activity).load(list.get(position).getPic()).placeholder(R.mipmap.default_2).error(R.mipmap.default_2).into(holder.pic);
            holder.box.setChecked(list.get(position).getFlag());
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int productID = list.get(position).getProductID();
                    deleteDatas(productID);
                    list.remove(position);
                    notifyDataSetChanged();
                    listview.turnToNormal();

                }
            });
            holder.box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean falgs = ((CheckBox) v).isChecked();
                    boolean f=true;
                    if(!falgs){
                        list.get(position).setFlag(false);
                    }else{
                        list.get(position).setFlag(true);
                    }
                    for(int i =0;i<list.size();i++){
                        if(!list.get(i).getFlag()){
                            f=false;
                            da_box.setChecked(false);
                        }
                    }
                    if(f){
                        da_box.setChecked(true);
                    }
                    setNum();
                }
            });
            holder.box.setChecked(list.get(position).getFlag());
            return convertView;
        }
        class ViewHolder{
            TextView name;
            TextView price;
            TextView num;
            CheckBox box;
            ImageView pic;
            TextView delete;
        }
    }
    //写个请求的方法
    public void  questDatas(){

        String url="http://169.254.94.62:8080/bullking1/cart?userID=68";
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(activity, url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                if(responseString!=null){
                    lin_xiang.setVisibility(View.VISIBLE);
                    lin_shop.setVisibility(View.INVISIBLE);
                    Gson gson =new Gson();
                    GoodsBean goodsBean = gson.fromJson(responseString, GoodsBean.class);
                    list = goodsBean.getCartItemList();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setFlag(false);
                    }
                    my = new MyList();
                    listview.setAdapter(my);

                }else{
                    lin_shop.setVisibility(View.VISIBLE);
                    lin_xiang.setVisibility(View.INVISIBLE);
                }
            }
        });

    }
    public void  deleteDatas(int posotion){

        String url="http://169.254.94.62:8080/bullking1/cart?productid=" + posotion+"&&userID=68";

        AsyncHttpClient client=new AsyncHttpClient();
        client.get(activity, url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(activity, "删除失败", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i("qqqqq",responseString);
                Toast.makeText(activity, "删除成功", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public  void setNum(){
        int nums =0;
        int prices =0;
        double pp =0;

        for(int i =0;i<list.size();i++){
            if(list.get(i).getFlag()==true){
                nums  += list.get(i).getCount();
                int count = list.get(i).getCount();
                prices = (int) list.get(i).getPrice();
                pp += prices*count;
          }
        }
        int k = (int)(pp*100);
        price.setText( k/100d+ "元");
        f3_num.setText(nums+" 件");

        }

    }



