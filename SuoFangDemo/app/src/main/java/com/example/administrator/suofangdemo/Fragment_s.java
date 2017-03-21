package com.example.administrator.suofangdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import uk.co.senab.photoview.PhotoView;

/**
 * 作者：仝晓雅 on 2017/3/19 10:28
 * 类的注释：
 */

public class Fragment_s extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmnet_pic, null);
       PhotoView pic = (PhotoView) view.findViewById(R.id.f_pic);
        Bundle bundle = getArguments();
        String pic1 = bundle.getString("pic");
        Glide.with(this).load(pic1).into(pic);
        return view;
    }
}
