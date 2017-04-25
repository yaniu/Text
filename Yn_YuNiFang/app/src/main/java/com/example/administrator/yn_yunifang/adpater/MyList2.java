package com.example.administrator.yn_yunifang.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.yn_yunifang.R;
import com.example.administrator.yn_yunifang.bean.Bean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 作者：仝晓雅 on 2017/4/14 14:42
 * 类的注释：春季热销
 */

public class MyList2 extends RecyclerView.Adapter<MyList2.ViewHolder> {
    private  Context context;
    private  List<Bean.DataBean.SubjectsBean.GoodsListBean> list_3_rcv;
    private MyList2.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public  interface  OnItemClickListener{
        void OnClickListener(int position,List<Bean.DataBean.SubjectsBean.GoodsListBean> list);
    }
    public MyList2(Context context, List<Bean.DataBean.SubjectsBean.GoodsListBean> list_3_rcv) {
        this.context=context;
        this.list_3_rcv=list_3_rcv;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =View.inflate(context, R.layout.mylist1_main,null);
        final ViewHolder holder =  new ViewHolder(view);
       /* view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    int position = holder.getLayoutPosition();
                    onItemClickListener.OnClickListener(position,list_3_rcv);
                }
            }
        });*/
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.text.setText(list_3_rcv.get(position).getGoods_name());
             holder.price1.setText("￥"+list_3_rcv.get(position).getShop_price()+"");
             holder.price2.setText("￥ "+list_3_rcv.get(position).getMarket_price());
             Picasso.with(context).load(list_3_rcv.get(position).getGoods_img()).placeholder(R.mipmap.default_2).error(R.mipmap.default_2).into(holder.pic);
            holder.pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener!=null){
                        onItemClickListener.OnClickListener(position,list_3_rcv);
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return list_3_rcv.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{


        private  ImageView pic;
        private  TextView text;
        private  TextView price1;
        private  TextView price2;

        public ViewHolder(View itemView) {
            super(itemView);
            pic = (ImageView) itemView.findViewById(R.id.f1_list1_pic);
            text = (TextView) itemView.findViewById(R.id.f1_list1_text);
            price1 = (TextView) itemView.findViewById(R.id.f1_list1_price1);
            price2 = (TextView) itemView.findViewById(R.id.f1_list1_price2);
        }
    }
}
