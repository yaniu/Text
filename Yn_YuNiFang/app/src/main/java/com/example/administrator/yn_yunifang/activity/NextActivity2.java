package com.example.administrator.yn_yunifang.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.yn_yunifang.R;

/**
 * 作者：仝晓雅 on 2017/4/13 21:15
 * 类的注释：
 */

public class NextActivity2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.next2_main,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
    }
}
