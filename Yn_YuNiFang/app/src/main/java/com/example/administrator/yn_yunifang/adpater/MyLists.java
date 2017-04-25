package com.example.administrator.yn_yunifang.adpater;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.yn_yunifang.R;
import com.example.administrator.yn_yunifang.activity.NextActivity;
import com.example.administrator.yn_yunifang.activity.ShangActivity;
import com.example.administrator.yn_yunifang.activity.ShangActivity1;
import com.example.administrator.yn_yunifang.bean.Bean;
import com.example.administrator.yn_yunifang.bean.Goods;
import com.example.administrator.yn_yunifang.view.FullyLinearLayoutManager;
import com.example.administrator.yn_yunifang.view.RecyclerViewDivider;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：仝晓雅 on 2017/4/14 19:51
 * 类的注释：
 */

public class MyLists extends BaseAdapter {
    private Context context;
    private List<Bean.DataBean.SubjectsBean> f1_list;
  //  private ArrayList<Goods> g_list;
    public MyLists(Context context, List<Bean.DataBean.SubjectsBean> f1_list) {
        this.context =context;
        this.f1_list =f1_list;
    }

    @Override
    public int getCount() {
        return f1_list.size();
    }

    @Override
    public Object getItem(int position) {
        return f1_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        if(convertView ==null){
            convertView =View.inflate(context, R.layout.mylists_main,null);
            holder =new ViewHolder();
            holder.pic = (ImageView) convertView.findViewById(R.id.f1_lists_pic);
            holder.rec= (RecyclerView) convertView.findViewById(R.id.f1_lists_rcv);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(f1_list.get(position).getImage()).placeholder(R.mipmap.default_2).error(R.mipmap.default_2).into(holder.pic);

        holder.rec.setLayoutManager(new FullyLinearLayoutManager(context,RecyclerView.HORIZONTAL,true));
        MyList2 my =new MyList2(context,f1_list.get(position).getGoodsList());
        holder.rec.setAdapter(my);
      /*  g_list = new ArrayList<>();
        List<Bean.DataBean.SubjectsBean.GoodsListBean> goodsList = f1_list.get(position).getGoodsList();
        for(int i =0;i<goodsList.size();i++){
            String goods_name = goodsList.get(i).getGoods_name();
            String goods_img = goodsList.get(i).getGoods_img();
            double market_price = goodsList.get(i).getMarket_price();
            double shop_price = goodsList.get(i).getShop_price();
            Goods good =new Goods();
            good.name=goods_name;
            good.pic=goods_img;
            good.price1= (int) shop_price;
            good.price2 = (int) market_price;
            g_list.add(good);
        }*/
        my.setOnItemClickListener(new MyList2.OnItemClickListener() {
            @Override
            public void OnClickListener(int position, List<Bean.DataBean.SubjectsBean.GoodsListBean> list) {
                Intent intent =new Intent(context, ShangActivity1.class);
                intent.putExtra("list1", (Serializable) list);
                intent.putExtra("position1",position);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
    class ViewHolder{
        ImageView pic;
        RecyclerView rec;
    }
}
