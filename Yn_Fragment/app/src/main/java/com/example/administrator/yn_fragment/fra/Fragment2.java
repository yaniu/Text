package com.example.administrator.yn_fragment.fra;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.administrator.yn_fragment.R;
import com.example.administrator.yn_fragment.bean.News;

import java.util.ArrayList;

/**
 * 作者：仝晓雅 on 2017/3/21 10:35
 * 类的注释：
 */

public class Fragment2 extends Fragment {


    private GridView gv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.f2_main,null);
         initView(view);
        return view;
    }


    private void initView(View view) {
        gv = (GridView) view.findViewById(R.id.f2_gv);
        Fragment1 f1= (Fragment1) getActivity().getSupportFragmentManager().findFragmentById(R.id.f1);
        f1.setOnThresValues(new Fragment1.OnThresValues() {
            @Override
            public void OnThres(final ArrayList<News.NodesBean> bean) {
            gv.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return bean.size();
                }

                @Override
                public Object getItem(int position) {
                    return bean.get(position);
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    convertView =View.inflate(getActivity(),R.layout.f1_text,null);
                   TextView text = (TextView) convertView.findViewById(R.id.f1_tv);
                   text.setText(bean.get(position).getCategory_name());
                    return convertView;
                }
            });
            }
        });
    }
}
