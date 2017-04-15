package com.example.administrator.yn_yunifang.adpater;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.yn_yunifang.R;
import com.example.administrator.yn_yunifang.activity.NextActivity;
import com.example.administrator.yn_yunifang.bean.Bean;
import com.example.administrator.yn_yunifang.view.RecyclerViewDivider;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.administrator.yn_yunifang.R.id.f1_list;

/**
 * 作者：仝晓雅 on 2017/4/14 20:53
 * 类的注释：
 */

public class MyGrid extends BaseAdapter {
    private Context context;
    private List<Bean.DataBean.DefaultGoodsListBean> f1_grid;

    public MyGrid(Context context, List<Bean.DataBean.DefaultGoodsListBean> f1_grid) {
        this.context = context;
        this.f1_grid = f1_grid;
    }


    @Override
    public int getCount() {
        return f1_grid.size();
    }

    @Override
    public Object getItem(int position) {
        return f1_grid.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        if(convertView ==null){
            convertView =View.inflate(context,R.layout.mygrid_main,null);
            holder =new ViewHolder();
            holder.pic = (ImageView) convertView.findViewById(R.id.f1_grid1_pic);
            holder.text1= (TextView) convertView.findViewById(R.id.f1_grid1_text);
            holder.text2= (TextView) convertView.findViewById(R.id.f1_grid1_text1);
            holder.price1= (TextView) convertView.findViewById(R.id.f1_grid1_price1);
            holder.price2= (TextView) convertView.findViewById(R.id.f1_grid1_price2);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.text1.setText(f1_grid.get(position).getEfficacy());
        holder.text2.setText(f1_grid.get(position).getGoods_name());
        holder.price1.setText("￥ "+f1_grid.get(position).getShop_price());
        holder.price2.setText("￥ "+f1_grid.get(position).getMarket_price());
        Picasso.with(context).load(f1_grid.get(position).getGoods_img()).error(R.mipmap.default_2).error(R.mipmap.default_2).into(holder.pic);


     return convertView;
    }
    class ViewHolder{
        TextView text1;
        TextView text2;
        TextView price1;
        TextView price2;
        ImageView pic;

    }
}

