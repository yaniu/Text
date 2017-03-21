package com.example.administrator.tongxiaoya1502l2017321.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.administrator.tongxiaoya1502l2017321.R;
import com.example.administrator.tongxiaoya1502l2017321.bean.News;

import java.util.ArrayList;

/**
 * 作者：仝晓雅 on 2017/3/21 08:45
 * 类的注释：
 */

public class Fragment2 extends Fragment{

    private TextView text;
    private GridView gv;
    private TextView text1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.f1_lv_text_main,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        text1 = (TextView) view.findViewById(R.id.f1_l_text);
    }
    public  void getNae(String mame){
        text1.setText(mame);
    }


}
