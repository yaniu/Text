package com.example.administrator.yn_textman.man;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.yn_textman.R;
import com.example.administrator.yn_textman.activity.LogActivity;
import com.example.administrator.yn_textman.activity.ThreeActivity;

/**
 * 作者：仝晓雅 on 2017/3/9 18:50
 * 类的注释：
 */

public class GuanFragment extends Fragment {

    private ThreeActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.man_guan,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        activity = (ThreeActivity) getActivity();
        ImageView button = (ImageView) view.findViewById(R.id.guan_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(activity,LogActivity.class);
                startActivity(intent);
            }
        });
    }
}
