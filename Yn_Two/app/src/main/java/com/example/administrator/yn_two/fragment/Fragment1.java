package com.example.administrator.yn_two.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.administrator.yn_two.MainActivity;
import com.example.administrator.yn_two.R;

import java.util.ArrayList;

/**
 * 作者：仝晓雅 on 2017/3/19 11:14
 * 类的注释：
 */

public class Fragment1 extends Fragment {

    private ListView lisview;
    private ArrayList<String> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f1_main, null);
        initView(view);
        getServerData();
        return  view;
    }

    private void getServerData() {
        MainActivity activity = (MainActivity) getActivity();
        list = new ArrayList<>();
        for(int i =0;i<5;i++){
            list.add("第"+i+"页");
        }
        lisview.setAdapter(new ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1, list));
        lisview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment2 fragment2 = (Fragment2) getActivity().getSupportFragmentManager().findFragmentById(R.id.f2);
                fragment2.Data(list.get(position));
            }
        });
    }

    private void initView(View view) {

        lisview = (ListView) view.findViewById(R.id.f1_listview);
    }
}
