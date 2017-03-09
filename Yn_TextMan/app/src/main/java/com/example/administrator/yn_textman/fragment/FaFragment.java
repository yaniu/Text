package com.example.administrator.yn_textman.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 作者：仝晓雅 on 2017/3/9 18:12
 * 类的注释：
 */

public class FaFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView t=new TextView(getActivity());
        t.setText("f2");
        return t;
    }
}
