package com.example.administrator.yn_textman.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.yn_textman.R;
import com.example.administrator.yn_textman.fragment.FaFragment;
import com.example.administrator.yn_textman.fragment.ManFragment;
import com.example.administrator.yn_textman.fragment.MeFragment;
import com.example.administrator.yn_textman.fragment.VFragment;

/**
 * 作者：仝晓雅 on 2017/3/8 17:53
 * 类的注释：主界面
 */

public class ThreeActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener{

    private RadioButton button1;
    private RadioButton button2;
    private RadioButton button3;
    private RadioButton button4;
    private android.support.v4.app.FragmentManager manager;
    private ManFragment man;
    private FaFragment fa;
    private VFragment v;
    private MeFragment me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.san_main);
        initView();
    }

    private void initView() {
        button1 = (RadioButton) findViewById(R.id.s_button1);
        button2 = (RadioButton) findViewById(R.id.s_button2);
        button3 = (RadioButton) findViewById(R.id.s_button3);
        button4 = (RadioButton) findViewById(R.id.s_button4);
        RadioGroup group= (RadioGroup) findViewById(R.id.s_group);
        group.setOnCheckedChangeListener(this);
        manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        man = new ManFragment();
        fa = new FaFragment();
        v = new VFragment();
        me = new MeFragment();
        transaction.add(R.id.s_frag, man);
        transaction.add(R.id.s_frag, fa);
        transaction.add(R.id.s_frag, v);
        transaction.add(R.id.s_frag, me);
        transaction.hide(fa);
        transaction.hide(v);
        transaction.hide(me);
        transaction.commit();
        button1.setTextColor(Color.YELLOW);
        button1.setSelected(true);



    }



 @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case  R.id.s_button1:
                button1.setTextColor(Color.YELLOW);
                button2.setTextColor(Color.parseColor("#696464"));
                button3.setTextColor(Color.parseColor("#696464"));
                button4.setTextColor(Color.parseColor("#696464"));
                button1.setSelected(true);
                button2.setSelected(false);
                button3.setSelected(false);
                button4.setSelected(false);
                android.support.v4.app.FragmentTransaction transaction1 = manager.beginTransaction();
                transaction1.show(man);
                transaction1.hide(fa);
                transaction1.hide(v);
                transaction1.hide(me);
                transaction1.commit();

                break;
            case  R.id.s_button2:
                button2.setTextColor(Color.YELLOW);
                button1.setTextColor(Color.parseColor("#696464"));
                button3.setTextColor(Color.parseColor("#696464"));
                button4.setTextColor(Color.parseColor("#696464"));
                button2.setSelected(true);
                button1.setSelected(false);
                button3.setSelected(false);
                button4.setSelected(false);
                android.support.v4.app.FragmentTransaction transaction2 = manager.beginTransaction();
                transaction2.show(fa);
                transaction2.hide(man);
                transaction2.hide(v);
                transaction2.hide(me);
                transaction2.commit();

                break;
            case  R.id.s_button3:
                button3.setTextColor(Color.YELLOW);
                button2.setTextColor(Color.parseColor("#696464"));
                button1.setTextColor(Color.parseColor("#696464"));
                button4.setTextColor(Color.parseColor("#696464"));
                button3.setSelected(true);
                button2.setSelected(false);
                button1.setSelected(false);
                button4.setSelected(false);
                android.support.v4.app.FragmentTransaction transaction3 = manager.beginTransaction();
                transaction3.show(v);
                transaction3.hide(fa);
                transaction3.hide(man);
                transaction3.hide(me);
                transaction3.commit();
                break;
            case  R.id.s_button4:
                button4.setTextColor(Color.YELLOW);
                button2.setTextColor(Color.parseColor("#696464"));
                button3.setTextColor(Color.parseColor("#696464"));
                button1.setTextColor(Color.parseColor("#696464"));
                button4.setSelected(true);
                button2.setSelected(false);
                button3.setSelected(false);
                button1.setSelected(false);
                android.support.v4.app.FragmentTransaction transaction4 = manager.beginTransaction();
                transaction4.show(me);
                transaction4.hide(fa);
                transaction4.hide(v);
                transaction4.hide(man);
                transaction4.commit();

                break;
        }
    }
}
