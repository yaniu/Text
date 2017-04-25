package com.example.administrator.yn_yunifang.adpater;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.yn_yunifang.R;
import com.example.administrator.yn_yunifang.activity.NextActivity;
import com.example.administrator.yn_yunifang.bean.Bean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 作者：仝晓雅 on 2017/4/14 14:42
 * 类的注释：春季热销
 */

public class MyList1 extends RecyclerView.Adapter<MyList1.ViewHolder> {
    private  Context context;
    private List<Bean.DataBean.BestSellersBean.GoodsListBeanX> list_2_rcv;
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public  interface  OnItemClickListener{
        void OnClickListener(View view,int position);
    }
    public MyList1(Context context, List<Bean.DataBean.BestSellersBean.GoodsListBeanX> list_2_rcv) {
        this.context=context;
        this.list_2_rcv=list_2_rcv;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =View.inflate(context, R.layout.mylist1_main,null);
        final ViewHolder holder =new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getLayoutPosition();
                if(onItemClickListener !=null){
                            onItemClickListener.OnClickListener(v,position);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String goods_name = list_2_rcv.get(position).getGoods_name();
        double shop_price = list_2_rcv.get(position).getShop_price();
        double market_price = list_2_rcv.get(position).getMarket_price();
        String goods_img = list_2_rcv.get(position).getGoods_img();
        Log.i("ccccc",position+"");
        holder.text.setText(goods_name);
        holder.price1.setText("￥"+shop_price+"");
        holder.price2.setText("￥"+market_price);
        Picasso.with(context).load(goods_img).placeholder(R.mipmap.default_2).error(R.mipmap.default_2).into(holder.pic);
        holder.price2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public int getItemCount() {
        return list_2_rcv.size();
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
