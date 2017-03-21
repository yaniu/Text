package com.example.administrator.suofangdemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * 作者：仝晓雅 on 2017/3/19 09:58
 * 类的注释：
 */

public class MyGrid extends BaseAdapter {
    private  Context context;
    private JSONArray data;

    public MyGrid(Context  context, JSONArray data) {
        this.context=context;
        this.data =data;
    }

    @Override
    public int getCount() {
        return data.length();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView =View.inflate(context,R.layout.img_main,null);
        ImageView pic = (ImageView) convertView.findViewById(R.id.g_pic);
        try {
            String img_url = data.getJSONObject(position).getString("image_url");
            Picasso.with(context).load(img_url).into(pic);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }
}
