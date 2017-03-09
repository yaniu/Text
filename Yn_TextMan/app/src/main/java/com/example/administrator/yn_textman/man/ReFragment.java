package com.example.administrator.yn_textman.man;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.yn_textman.R;

/**
 * 作者：仝晓雅 on 2017/3/9 18:50
 * 类的注释：
 */

public class ReFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.man_re,null);
        return view;
    }
}
