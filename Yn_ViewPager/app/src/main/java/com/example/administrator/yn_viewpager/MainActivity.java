package com.example.administrator.yn_viewpager;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.impl.cookie.BestMatchSpec;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private LinearLayout lin;
    private ViewPager vp;
    List<ImageView> list =new ArrayList<>();
    List<ImageView> xlist =new ArrayList<>();
    ArrayList<String>  ulist =new ArrayList<>();
    List<String>  tlist =new ArrayList<>();
    private List<Bean.DataBean.BannerBean> banner;

    private Handler halder =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                int itme =vp.getCurrentItem();
                itme++;
                vp.setCurrentItem(itme);

            }
            halder.sendEmptyMessageDelayed(0,2000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getServerData();
        halder.sendEmptyMessageDelayed(0,2000);
    }

    private void getServerData() {
        AsyncHttpClient clinet =new AsyncHttpClient();
        String path="http://www.meirixue.com/api.php?c=circle&a=getCircleNamesIndexV2";
        clinet.get(MainActivity.this, path, new TextHttpResponseHandler() {



            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson =new Gson();
                Bean bean = gson.fromJson(responseString, Bean.class);
                banner = bean.getData().getBanner();
                for(int i = 0; i< banner.size(); i++){
                   ImageView imv =new ImageView(MainActivity.this);
                    ImageLoader.getInstance().displayImage(banner.get(i).getImg(),imv);
                    list.add(imv);
                    ImageView xiao =new ImageView(MainActivity.this);
                    xiao.setImageResource(R.drawable.select);
                    xlist.add(xiao);
                    lin.addView(xiao);
                    tlist.add(banner.get(i).getTitle());
                   ulist.add(banner.get(i).getUrl());

                }
                text.setText(tlist.get(0));
                xlist.get(0).setSelected(true);
                vp.setAdapter(new MyPager());
                vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                       for(int i =0;i<list.size();i++){
                           if(i==position%list.size()){
                               text.setText(tlist.get(0).toString());

                               xlist.get(i).setSelected(true);
                           }else{
                               text.setText(tlist.get(1).toString());

                               xlist.get(i).setSelected(false);
                           }
                        /*  if(i==position%tlist.size()){
                               text.setText(tlist.get(0).toString());
                              Toast.makeText(MainActivity.this, ""+tlist.get(0).toString(), Toast.LENGTH_SHORT).show();
                           }else{
                               text.setText(tlist.get(1).toString());
                              Toast.makeText(MainActivity.this, ""+tlist.get(1).toString(), Toast.LENGTH_SHORT).show();

                           }*/
                       }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }
        });
    }
    class MyPager extends PagerAdapter{
        @Override
        public int getCount() {
            return Integer.MAX_VALUE ;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
         //   vp.removeView(list.get(position%list.size()));

        }


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
           try {
               vp.addView(list.get(position%list.size()));
           }catch (Exception e){

           }
            list.get(position%list.size()).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, NextActivite.class);
                    intent.putExtra("list",banner.get(position%list.size()).getUrl());
                    intent.putExtra("position",position%list.size());
                    startActivity(intent);
                }
            });
           /*vp.setOnTouchListener(new View.OnTouchListener() {
               int flage = 0 ;
               // ★★★这句话很重要！！！别忘了写！！！
               @Override
               public boolean onTouch(View v, MotionEvent event) {
                   switch (event.getAction()){
                       case MotionEvent.ACTION_DOWN:
                           flage = 0 ;
                           break ;
                       case MotionEvent.ACTION_MOVE:
                           flage = 1 ;
                           break ;
                       case  MotionEvent.ACTION_UP :
                           if (flage == 0) {
                               int item = vp.getCurrentItem();
                               if (item == 0) {
                                   Intent intent = new Intent(MainActivity.this, NextActivite.class);
                                   intent.putExtra("list",ulist);
                                   intent.putExtra("position",item%list.size());
                                   startActivity(intent);
                               } else if (item == 1) {
                                   Intent intent = new Intent(MainActivity.this, NextActivite.class);
                                   intent.putStringArrayListExtra("list",ulist);
                                   intent.putExtra("position",item%list.size());
                                   startActivity(intent);
                               }
                           }
                           break ;


                   }
                   return true;
               }
           });*/
            return  list.get(position%list.size());
        }
    }

    private void initView() {
        text = (TextView) findViewById(R.id.o_text);
        lin = (LinearLayout) findViewById(R.id.o_lin);
        vp = (ViewPager) findViewById(R.id.o_pv);
    }
}
