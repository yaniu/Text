package com.example.administrator.yn_yunifang.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.yn_yunifang.R;
import com.example.administrator.yn_yunifang.adpater.MyGrid;
import com.example.administrator.yn_yunifang.adpater.MyList1;
import com.example.administrator.yn_yunifang.adpater.MyList2;
import com.example.administrator.yn_yunifang.adpater.MyLists;
import com.example.administrator.yn_yunifang.adpater.MyPager;
import com.example.administrator.yn_yunifang.bean.Bean;
import com.example.administrator.yn_yunifang.utules.GlideImageLoader;
import com.example.administrator.yn_yunifang.view.InnerGridView;
import com.example.administrator.yn_yunifang.view.InnerListView;
import com.example.administrator.yn_yunifang.view.RecyclerViewDivider;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：仝晓雅 on 2017/4/13 21:23
 * 类的注释：第一个fragment的内容
 */

public class NextActivity1 extends Fragment {

    private NextActivity activity;
    private ImageView f1_pic;
    private ImageView f1_person;
    List<String> pic_list =new ArrayList<>();
    private Bean.DataBean data;
    private Banner banner;
    private TextView text1;
    private RecyclerView rcv;
    private TextView text2;
    private ViewPager rcv_pic;
    private InnerListView listview;
    private InnerGridView gridview;
    List<Bean.DataBean.BestSellersBean> list_2_text =new ArrayList<>();
    List<Bean.DataBean.BestSellersBean.GoodsListBeanX> list_2_rcv =new ArrayList<>();
    List<Bean.DataBean.SubjectsBean> f1_list =new ArrayList<>();
    List<ImageView> pics =new ArrayList<>();
    List<Bean.DataBean.DefaultGoodsListBean> f1_grid =new ArrayList<>();
    private Handler handler =new Handler(){

        private Bean.DataBean data;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                String str = (String) msg.obj;
                Gson gson =new Gson();
                Bean bean = gson.fromJson(str, Bean.class);
                data = bean.getData();
                List<Bean.DataBean.Ad1Bean> ad1 = data.getAd1();
                for(int i =0;i<ad1.size();i++){
                    pic_list.add(ad1.get(i).getImage());
                }
               //Grid专题
                List<Bean.DataBean.DefaultGoodsListBean> defaultGoodsList = data.getDefaultGoodsList();
                for(int i =0;i<defaultGoodsList.size();i++){
                    f1_grid.add(defaultGoodsList.get(i));
                }
                MyGrid myGrid =new MyGrid(activity,f1_grid);
                 gridview.setAdapter(myGrid);


                //热门专题
                List<Bean.DataBean.SubjectsBean> subjects = data.getSubjects();
                for(int i =0;i<subjects.size();i++){
                    f1_list.add(subjects.get(i));
                }
                MyLists mys =new MyLists(activity,f1_list);
                listview.setAdapter(mys);
                //优惠活动
                List<Bean.DataBean.ActivityInfoBean.ActivityInfoListBean> activityInfoList = data.getActivityInfo().getActivityInfoList();
                for(int i =0;i<activityInfoList.size();i++){
                    ImageView img =new ImageView(activity);
                    Picasso.with(activity).load(activityInfoList.get(i).getActivityImg()).into(img);
                    pics.add(img);
                }

                MyPager mypager =new MyPager(pics);
                rcv_pic.setAdapter(mypager);


                //设置图片的动画
                banner.setBannerAnimation(Transformer.FlipVertical);
                //加载图片
                banner.setImages(pic_list).setImageLoader(new GlideImageLoader()).start();
                //春节热销
                List<Bean.DataBean.BestSellersBean> bestSellers = data.getBestSellers();
                for(int i =0;i<bestSellers.size();i++){
                    list_2_text.add(bestSellers.get(i));
                    text1.setText("~~~ " +list_2_text.get(i).getName() +" ~~~`");
                    List<Bean.DataBean.BestSellersBean.GoodsListBeanX> goodsList = list_2_text.get(i).getGoodsList();
                    for(int j =0;j<goodsList.size();j++){
                        list_2_rcv.add(goodsList.get(i));
                    }

                }
                rcv.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false));
                    /* type_recyclerView.addItemDecoration(new RecyclerViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 10, Color.parseColor("#FFE9EAE2")));*/
                MyList1 my1 =new MyList1(activity,list_2_rcv);
                rcv.setAdapter(my1);
                rcv.addItemDecoration(new RecyclerViewDivider(activity, LinearLayoutManager.VERTICAL, 5, Color.parseColor("#ffffff")));



            }
        }
    };



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.next1_main,null);
        initView(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (NextActivity) context;
    }

    private void initView(View view) {
        f1_person = (ImageView) view.findViewById(R.id.f1_person);
        f1_pic = (ImageView) view.findViewById(R.id.f1_pic);
        banner = (Banner) view.findViewById(R.id.f1_banner);
        text1 = (TextView) view.findViewById(R.id.f1_text1);
        rcv = (RecyclerView) view.findViewById(R.id.f1_rcv);
        text2 = (TextView) view.findViewById(R.id.f1_text2);
        rcv_pic = (ViewPager) view.findViewById(R.id.f1_rcv_pic);
        listview = (InnerListView) view.findViewById(R.id.f1_list);
        gridview = (InnerGridView ) view.findViewById(R.id.f1_grid);
        //点击显示侧滑
         getMenu();
        //解析数据
        getData();

    }
    //解析数据
    private void getData() {
        OkHttpClient mOkHttpClient =new OkHttpClient();
        String url ="http://m.yunifang.com/yunifang/mobile/home?random=84831&encode=9dd34239798e8cb22bf99a75d1882447";
        Request mRequest =new Request.Builder().url(url).build();
        Call call = mOkHttpClient.newCall(mRequest);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String string = response.body().string();
                Message message = handler.obtainMessage(0, string);
                message.sendToTarget();
            }
        });


    }

    //点击显示侧滑
    private void getMenu() {
        final SlidingMenu menu =new SlidingMenu(activity);
        menu.setMode(SlidingMenu.LEFT);
        menu.setBehindOffset(100);
        menu.attachToActivity(activity,SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.menu_main);
        f1_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.toggle();
            }
        });
    }

}
