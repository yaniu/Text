package com.example.administrator.yn_new.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.yn_new.R;
import com.example.administrator.yn_new.activity.WebActivity;
import com.example.administrator.yn_new.baen.ReDianBean;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import maqian.baidu.com.xlistviewlibrary.XListView;
import weeks.amiao.com.imageloader.BitmapUtils;

/**
 * 作者：仝晓雅 on 2017/3/12 20:59
 * 类的注释：(Fragment 多条目展示)
 */

public class Fragment_s extends Fragment {
    //private  String url ="http://ic.snssdk.com/2/article/v25/stream/?category=news_hot&count=20&min_behot_time=1457659116&bd_latitude=4.9E-324&bd_longitude=4.9E-324&bd_loc_time=1457672153&loc_mode=5&lac=4527&cid=28883&iid=3839760160&device_id=12246291682&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=iToolsAVM&os_api=19&os_version=4.4.4&uuid=352284045861006&openudid=84c1c7b192991cc6";
    //private  String url="http://v.juhe.cn/toutiao/index?type=redian&key=32b9973df2e6ee0c2bf094b61c7d7844";
  /*

    public void setUrl(String url) {
        this.url = url;
    }*/
    private String url;

    List<ReDianBean.ResultBean.DataBean> list =new ArrayList<>();
    private XListView list1;
    private Handler handler;
    private BitmapUtils bitmapUtils;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.next_fragment_main,null);
        Bundle bundle = getArguments();
         url= bundle.getString("path");
        initView(view);
        handler =new Handler();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getServerData();
    }

    private void getServerData() {
        bitmapUtils = new BitmapUtils(getActivity());
        AsyncHttpClient client =new AsyncHttpClient();
        client.get(getActivity(), url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
              Gson gson =new Gson();
                ReDianBean bean = gson.fromJson(responseString, ReDianBean.class);
                List<ReDianBean.ResultBean.DataBean> data = bean.getResult().getData();
                for(int i=0;i<data.size();i++){
                    list.add(data.get(i));
                }
                list1.setAdapter(new MyList());
            }
        });
    }

    private void initView(View view) {
        list1 = (XListView) view.findViewById(R.id.f_list);
        list1.setPullLoadEnable(true);
        list1.setPullRefreshEnable(true);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(getActivity(),WebActivity.class);
                intent.putExtra("name",list.get(position-1).getTitle());
                intent.putExtra("url",list.get(position-1).getUrl());
                intent.putExtra("text2",list.get(position-1).getCategory());
                intent.putExtra("text3",list.get(position-1).getAuthor_name());
                intent.putExtra("text4",list.get(position-1).getDate());
                intent.putExtra("pic1",list.get(position-1).getThumbnail_pic_s());
                intent.putExtra("pic2",list.get(position-1).getThumbnail_pic_s02());
                intent.putExtra("pic3",list.get(position-1).getThumbnail_pic_s03());
                startActivity(intent);
            }
        });
        list1.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                getTime();
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
            if (list.get(position).getThumbnail_pic_s() != null && list.get(position).getThumbnail_pic_s02() == null && list.get(position).getThumbnail_pic_s03() == null) {
                return type1;
            } else if (list.get(position).getThumbnail_pic_s() != null && list.get(position).getThumbnail_pic_s02() != null && list.get(position).getThumbnail_pic_s03() == null) {
                return type2;
            } else  if(list.get(position).getThumbnail_pic_s() != null && list.get(position).getThumbnail_pic_s02() != null && list.get(position).getThumbnail_pic_s03() != null){
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
                        convertView = View.inflate(getActivity(), R.layout.re_1, null);
                        holder.t1_text1 = (TextView) convertView.findViewById(R.id.r1_text);
                        holder.t1_text2 = (TextView) convertView.findViewById(R.id.r1_text2);
                        holder.t1_text3 = (TextView) convertView.findViewById(R.id.r1_text3);
                        holder.t1_text4 = (TextView) convertView.findViewById(R.id.r1_text4);
                        holder.pic1 = (ImageView) convertView.findViewById(R.id.r1_pic1);

                        break;
                    case type2:
                        convertView = View.inflate(getActivity(), R.layout.re_2, null);
                        holder.t1_text1 = (TextView) convertView.findViewById(R.id.r2_text1);
                        holder.t1_text2 = (TextView) convertView.findViewById(R.id.r2_text2);
                        holder.t1_text3 = (TextView) convertView.findViewById(R.id.r2_text3);
                        holder.t1_text4 = (TextView) convertView.findViewById(R.id.r2_text4);
                        holder.pic1 = (ImageView) convertView.findViewById(R.id.r2_pic1);
                        holder.pic2 = (ImageView) convertView.findViewById(R.id.r2_pic2);

                        break;
                    case type3:
                        convertView = View.inflate(getActivity(), R.layout.re_3, null);
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
                holder = (ViewHolder) convertView.getTag();
            }
            switch (Type) {
                case type1:
                    holder.t1_text1.setText(list.get(position).getTitle());
                    holder.t1_text2.setText(list.get(position).getCategory());
                    holder.t1_text3.setText(list.get(position).getAuthor_name());
                    holder.t1_text4.setText(list.get(position).getDate());

                    bitmapUtils.disPlay(holder.pic1,list.get(position).getThumbnail_pic_s());
                  //  x.image().bind(holder.pic1, list.get(position).getThumbnail_pic_s());

                    break;
                case type2:
                    holder.t1_text1.setText(list.get(position).getTitle());
                    holder.t1_text2.setText(list.get(position).getCategory());
                    holder.t1_text3.setText(list.get(position).getAuthor_name());
                    holder.t1_text4.setText(list.get(position).getDate());
                    bitmapUtils.disPlay(holder.pic1, list.get(position).getThumbnail_pic_s());
                    bitmapUtils.disPlay(holder.pic2, list.get(position).getThumbnail_pic_s02());

                    break;
                case type3:
                    holder.t1_text1.setText(list.get(position).getTitle());
                    holder.t1_text2.setText(list.get(position).getCategory());
                    holder.t1_text3.setText(list.get(position).getAuthor_name());
                    holder.t1_text4.setText(list.get(position).getDate());
                    bitmapUtils.disPlay(holder.pic1, list.get(position).getThumbnail_pic_s());
                    bitmapUtils.disPlay(holder.pic2, list.get(position).getThumbnail_pic_s02());
                    bitmapUtils.disPlay(holder.pic3, list.get(position).getThumbnail_pic_s03());
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
    public void getTime(){
        long millis = System.currentTimeMillis();
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy--MM--dd HH:mm:ss");
        Date date =new Date(millis);
        String format = sdf.format(date);
        list1.setRefreshTime(format);
    }
}
