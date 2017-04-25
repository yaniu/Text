package com.example.administrator.yn_yunifang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.yn_yunifang.R;
import com.example.administrator.yn_yunifang.adpater.MyGrids;
import com.example.administrator.yn_yunifang.bean.F2_Bean;
import com.example.administrator.yn_yunifang.bean.Goods;
import com.example.administrator.yn_yunifang.view.InnerGridView;
import com.example.administrator.yn_yunifang.view.MyCallBack;
import com.example.administrator.yn_yunifang.view.OkHttpUtils;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：仝晓雅 on 2017/4/13 21:15
 * 类的注释：
 */

public class NextActivity2 extends Fragment implements  View.OnClickListener{
    View view ;
    private NextActivity activity;
    private InnerGridView grid;
    List<F2_Bean.DataBean.GoodsBriefBean> list =new ArrayList<>();
    private ArrayList<Goods> g_list;
   /* private  Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what ==0){

            }else{
                String str  = (String) msg.obj;


            }
        }
    };

*/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null){
            view = inflater.inflate(R.layout.next2_main,null);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
          getServerData();
    }

    private void getServerData() {
        String url ="http://m.yunifang.com/yunifang/mobile/category/list?random=96333&encode=bf3386e14fe5bb0bcef234baebca2414";
        OkHttpUtils.getOkHttpObject(url, new MyCallBack() {
            @Override
            public void Ok(String s) {
                Gson gson =new Gson();
                F2_Bean f2_bean = gson.fromJson(s, F2_Bean.class);
                List<F2_Bean.DataBean.GoodsBriefBean> goodsBrief = f2_bean.getData().getGoodsBrief();
                for(int i =0;i<goodsBrief.size();i++){
                   list.add(goodsBrief.get(i));
               }
                MyGrids my =new MyGrids(activity,list);
                grid.setAdapter(my);
                g_list = new ArrayList<>();
                for(int i =0;i<list.size();i++){
                    String goods_name = list.get(i).getGoods_name();
                    String goods_img = list.get(i).getGoods_img();
                    double market_price = list.get(i).getMarket_price();
                    double shop_price = list.get(i).getShop_price();
                    Goods good =new Goods();
                    good.name=goods_name;
                    good.pic=goods_img;
                    good.price1= (int) shop_price;
                    good.price2 = (int) market_price;
                    g_list.add(good);
                }
                grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent1 =new Intent(activity,ShangActivity.class);
                        intent1.putExtra("list", g_list);
                        intent1.putExtra("position",position);
                        startActivity(intent1);
                    }
                });
            }
        });
    }

    private void initView(View view) {
        grid = (InnerGridView) view.findViewById(R.id.f2_rec);
        //按面膜
        ImageView mo1 = (ImageView) view.findViewById(R.id.f2_mo1);
        ImageView mo2 = (ImageView) view.findViewById(R.id.f2_mo2);
        ImageView mo3 = (ImageView) view.findViewById(R.id.f2_mo3);
        ImageView mo4 = (ImageView) view.findViewById(R.id.f2_mo4);
        ImageView mo5 = (ImageView) view.findViewById(R.id.f2_mo5);
        ImageView mo6 = (ImageView) view.findViewById(R.id.f2_mo6);
        mo1.setOnClickListener(this);
        mo2.setOnClickListener(this);
        mo3.setOnClickListener(this);
        mo4.setOnClickListener(this);
        mo5.setOnClickListener(this);
        mo6.setOnClickListener(this);
        //按功效
        ImageView gong1 = (ImageView) view.findViewById(R.id.f2_gong1);
        ImageView gong2 = (ImageView) view.findViewById(R.id.f2_gong2);
        ImageView gong3 = (ImageView) view.findViewById(R.id.f2_gong3);
        ImageView gong4 = (ImageView) view.findViewById(R.id.f2_gong4);
        ImageView gong5 = (ImageView) view.findViewById(R.id.f2_gong5);
        gong1.setOnClickListener(this);
        gong2.setOnClickListener(this);
        gong3.setOnClickListener(this);
        gong4.setOnClickListener(this);
        gong5.setOnClickListener(this);
        //按肤质
        Button fu1 = (Button) view.findViewById(R.id.f2_fu1);
        Button fu2 = (Button) view.findViewById(R.id.f2_fu2);
        Button fu3 = (Button) view.findViewById(R.id.f2_fu3);
        Button fu4 = (Button) view.findViewById(R.id.f2_fu4);
        Button fu5 = (Button) view.findViewById(R.id.f2_fu5);
        Button fu6 = (Button) view.findViewById(R.id.f2_fu6);
       fu1.setOnClickListener(this);
        fu2.setOnClickListener(this);
        fu3.setOnClickListener(this);
        fu4.setOnClickListener(this);
        fu5.setOnClickListener(this);
        fu6.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        String url =null;
        switch (v.getId()){
            case R.id.f2_mo1:
                url ="http://m.yunifang.com/yunifang/mobile/goods/getall?random=13819&encode=d58e53c4b9e24bd5ba276ba68f8e98ec&category_id=17";
                Intent intent =new Intent(activity,MoActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("name","面膜");
                startActivityForResult(intent,1);
                break;
            case R.id.f2_mo2:
                url = "http://m.yunifang.com/yunifang/mobile/goods/getall?random=91873&encode=68301f6ea0d6adcc0fee63bd21815d69&category_id=39";
                Intent intent1 =new Intent(activity,MoActivity.class);
                intent1.putExtra("url",url);
                intent1.putExtra("name","润肤水");
                startActivityForResult(intent1,1);
                break;
            case R.id.f2_mo3:
                url = "http://m.yunifang.com/yunifang/mobile/goods/getall?random=91873&encode=68301f6ea0d6adcc0fee63bd21815d69&category_id=40";
                Intent intent2 =new Intent(activity,MoActivity.class);
                intent2.putExtra("url",url);
                intent2.putExtra("name","润肤乳");
                startActivityForResult(intent2,1);
                break;
            case R.id.f2_mo4:
                url = "http://m.yunifang.com/yunifang/mobile/goods/getall?random=91873&encode=68301f6ea0d6adcc0fee63bd21815d69&category_id=24";
                Intent intent3 =new Intent(activity,MoActivity.class);
                intent3.putExtra("url",url);
                intent3.putExtra("name","洁面乳");
                startActivityForResult(intent3,1);
                break;
            case R.id.f2_mo5:
                url = "http://m.yunifang.com/yunifang/mobile/goods/getall?random=91873&encode=68301f6ea0d6adcc0fee63bd21815d69&category_id=35";
                Intent intent4 =new Intent(activity,MoActivity.class);
                intent4.putExtra("url",url);
                intent4.putExtra("name","其他");
                startActivityForResult(intent4,1);
                break;
            case R.id.f2_mo6:
                url = "http://m.yunifang.com/yunifang/mobile/goods/getall?random=91873&encode=68301f6ea0d6adcc0fee63bd21815d69&category_id=33";
                Intent intent5 =new Intent(activity,MoActivity.class);
                intent5.putExtra("url",url);
                intent5.putExtra("name","实惠套装");
                startActivityForResult(intent5,1);
                break;
            case R.id.f2_gong1:
                url = " http://m.yunifang.com/yunifang/mobile/goods/getall?random=60305&encode=b0358434fda8d7478bfc325b5828b721&category_id=17";
                Intent intent6 =new Intent(activity,MoActivity.class);
                intent6.putExtra("url",url);
                intent6.putExtra("name","补水保湿");
                startActivityForResult(intent6,1);
                break;
            case R.id.f2_gong2:
                url = " http://m.yunifang.com/yunifang/mobile/goods/getall?random=60305&encode=b0358434fda8d7478bfc325b5828b721&category_id=31";
                Intent intent7 =new Intent(activity,MoActivity.class);
                intent7.putExtra("url",url);
                intent7.putExtra("name","舒缓修复");
                startActivityForResult(intent7,1);
                break;
            case R.id.f2_gong3:
                url = " http://m.yunifang.com/yunifang/mobile/goods/getall?random=60305&encode=b0358434fda8d7478bfc325b5828b721&category_id=19";
                Intent intent8 =new Intent(activity,MoActivity.class);
                intent8.putExtra("url",url);
                intent8.putExtra("name","控油祛痘");
                startActivityForResult(intent8,1);
                break;
            case R.id.f2_gong4:
                url = " http://m.yunifang.com/yunifang/mobile/goods/getall?random=60305&encode=b0358434fda8d7478bfc325b5828b721&category_id=18";
                Intent intent9 =new Intent(activity,MoActivity.class);
                intent9.putExtra("url",url);
                intent9.putExtra("name","美白提亮");
                startActivityForResult(intent9,1);
                break;
            case R.id.f2_gong5:
                url = " http://m.yunifang.com/yunifang/mobile/goods/getall?random=60305&encode=b0358434fda8d7478bfc325b5828b721&category_id=20";
                Intent intent10 =new Intent(activity,MoActivity.class);
                intent10.putExtra("url",url);
                intent10.putExtra("name","紧皱抗皱");
                startActivityForResult(intent10,1);
                break;
            case R.id.f2_fu1:
                url = "http://m.yunifang.com/yunifang/mobile/goods/getall?random=86588&encode=102e81c24a35dbdc9bb130c3c164434b&category_id=14";
                Intent intent11 =new Intent(activity,MoActivity.class);
                intent11.putExtra("url",url);
                intent11.putExtra("name","混合型肤质");
                startActivityForResult(intent11,1);
                break;
            case R.id.f2_fu2:
                url = "http://m.yunifang.com/yunifang/mobile/goods/getall?random=86588&encode=102e81c24a35dbdc9bb130c3c164434b&category_id=13";
                Intent intent12 =new Intent(activity,MoActivity.class);
                intent12.putExtra("url",url);
                intent12.putExtra("name","中型肤质");
                startActivityForResult(intent12,1);
                break;
            case R.id.f2_fu3:
                url = "http://m.yunifang.com/yunifang/mobile/goods/getall?random=86588&encode=102e81c24a35dbdc9bb130c3c164434b&category_id=29";
                Intent intent13 =new Intent(activity,MoActivity.class);
                intent13.putExtra("url",url);
                intent13.putExtra("name","干型肤质");
                startActivityForResult(intent13,1);
                break;
            case R.id.f2_fu4:
                url = "http://m.yunifang.com/yunifang/mobile/goods/getall?random=86588&encode=102e81c24a35dbdc9bb130c3c164434b&category_id=28";
                Intent intent14 =new Intent(activity,MoActivity.class);
                intent14.putExtra("url",url);
                intent14.putExtra("name","油型肤质");
                startActivityForResult(intent14,1);
                break;
            case R.id.f2_fu5:
                url = "http://m.yunifang.com/yunifang/mobile/goods/getall?random=86588&encode=102e81c24a35dbdc9bb130c3c164434b&category_id=15";
                Intent intent15 =new Intent(activity,MoActivity.class);
                intent15.putExtra("url",url);
                intent15.putExtra("name","痘痘型肤质");
                startActivityForResult(intent15,1);
                break;
            case R.id.f2_fu6:
                url = "http://m.yunifang.com/yunifang/mobile/goods/getall?random=86588&encode=102e81c24a35dbdc9bb130c3c164434b&category_id=37";
                Intent intent16 =new Intent(activity,MoActivity.class);
                intent16.putExtra("url",url);
                intent16.putExtra("name","敏感型肤质");
                startActivityForResult(intent16,1);
                break;
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data!=null){
            if(requestCode==1&&resultCode==20){

            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
