package com.example.administrator.yn_yunifang.adpater;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.administrator.yn_yunifang.R;
import com.example.administrator.yn_yunifang.activity.NextActivity;
import com.example.administrator.yn_yunifang.bean.Bean;
import com.example.administrator.yn_yunifang.view.FullyLinearLayoutManager;
import com.example.administrator.yn_yunifang.view.RecyclerViewDivider;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 作者：仝晓雅 on 2017/4/14 19:51
 * 类的注释：
 */

public class MyLists extends BaseAdapter {
    private Context context;
    private List<Bean.DataBean.SubjectsBean> f1_list;
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
        holder.rec.setLayoutManager(new FullyLinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        MyList2 my =new MyList2(context,f1_list.get(position).getGoodsList());
        holder.rec.setAdapter(my);



        return convertView;
    }
    class ViewHolder{
        ImageView pic;
        RecyclerView rec;
    }
}
