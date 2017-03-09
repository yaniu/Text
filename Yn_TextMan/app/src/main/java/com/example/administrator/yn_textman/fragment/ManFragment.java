package com.example.administrator.yn_textman.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.yn_textman.R;
import com.example.administrator.yn_textman.activity.SouActivty;
import com.example.administrator.yn_textman.activity.ThreeActivity;
import com.example.administrator.yn_textman.man.GuanFragment;
import com.example.administrator.yn_textman.man.ReFragment;

import java.util.ArrayList;

/**
 * 作者：仝晓雅 on 2017/3/9 18:12
 * 类的注释：
 */

public class ManFragment extends Fragment {


    private TextView text1;
    private TextView text2;
    private ThreeActivity activity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view =inflater.inflate(R.layout.man_xml,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        activity = (ThreeActivity) getActivity();
        text1 = (TextView) view.findViewById(R.id.inl_text1);
        text2 = (TextView) view.findViewById(R.id.inl_text2);
       ImageView sou= (ImageView) view.findViewById(R.id.inl_sou);
        sou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(activity,SouActivty.class);
                startActivity(intent);
            }
        });
        FragmentTransaction transaction= getFragmentManager().beginTransaction();
        final GuanFragment guan =new GuanFragment();
        final ReFragment re =new ReFragment();
        transaction.add(R.id.m_fragment,guan);
        transaction.add(R.id.m_fragment,re);
        transaction.hide(re);
        transaction.commit();
        text1.setSelected(true);
        text2.setSelected(false);
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1.setText("关注");

                text1.setSelected(true);
                text2.setSelected(false);
                FragmentTransaction transaction1= getFragmentManager().beginTransaction();
                transaction1.show(guan);
                transaction1.hide(re);
                transaction1.commit();
            }
        });
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text2.setText("热门");
                text2.setSelected(true);
                text1.setSelected(false);
                FragmentTransaction transaction2= getFragmentManager().beginTransaction();
                transaction2.show(re);
                transaction2.hide(guan);
                transaction2.commit();
            }
        });





    }

}
