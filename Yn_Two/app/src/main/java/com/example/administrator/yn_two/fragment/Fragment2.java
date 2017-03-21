package com.example.administrator.yn_two.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.yn_two.R;

/**
 * 作者：仝晓雅 on 2017/3/19 11:14
 * 类的注释：
 */

public class Fragment2 extends Fragment {

    private TextView text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.f2_main,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        text = (TextView) view.findViewById(R.id.f2_text);

    }

    public  void Data(String name){
        text.setText(name);
    }

}
