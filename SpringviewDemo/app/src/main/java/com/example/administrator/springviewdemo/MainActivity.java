package com.example.administrator.springviewdemo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.liaoinstan.springview.container.AcFunHeader;
import com.liaoinstan.springview.container.MeituanFooter;
import com.liaoinstan.springview.container.MeituanHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private SpringView sv;
    private Handler halder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getServerData();
    }

    private void getServerData() {
        halder =new Handler();
        ArrayList<String> list =new ArrayList<>();
        for(int i=0;i<50;i++){
            list.add("条目--"+i);
        }
        lv.setAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,list));
        sv.setType(SpringView.Type.FOLLOW);
        sv.setHeader(new MeituanHeader(this));
        sv.setFooter(new MeituanFooter(this));
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                halder.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.onFinishFreshAndLoad();
                    }
                },2000);
            }

            @Override
            public void onLoadmore() {
                halder.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.onFinishFreshAndLoad();
                    }
                },2000);
            }
        });
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.lv);
        sv = (SpringView) findViewById(R.id.sv);
    }
}
