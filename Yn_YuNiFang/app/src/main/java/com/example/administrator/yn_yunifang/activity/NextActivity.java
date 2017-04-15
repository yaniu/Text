package com.example.administrator.yn_yunifang.activity;




import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.administrator.yn_yunifang.R;

/**
 * 作者：仝晓雅 on 2017/4/13 19:53
 * 类的注释：主界面：用到了FragmentManager开启事物
 */

public class NextActivity extends FragmentActivity implements View.OnClickListener{

    private FragmentManager manager;
    private NextActivity1 f1;
    private NextActivity2 f2;
    private NextActivity3 f3;
    private ImageView pic1;
    private ImageView pic2;
    private ImageView pic3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_main);
        initView();

    }

    private void initView() {
        pic1 = (ImageView) findViewById(R.id.n_pic1);
        pic2 = (ImageView) findViewById(R.id.n_pic2);
        pic3 = (ImageView) findViewById(R.id.n_pic3);

        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        f1 = new NextActivity1();
        f2 = new NextActivity2();
        f3 = new NextActivity3();
        transaction.add(R.id.n_f, f1);
        transaction.add(R.id.n_f, f2);
        transaction.add(R.id.n_f, f3);
        transaction.hide(f2);
        transaction.hide(f3);
        transaction.commit();
        pic1.setSelected(true);
        pic1.setOnClickListener(this);
        pic2.setOnClickListener(this);
        pic3.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.n_pic:

                break;
            case  R.id.n_pic1:
                pic1.setSelected(true);
                pic2.setSelected(false);
                pic3.setSelected(false);
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.show(f1);
                transaction.hide(f2);
                transaction.hide(f3);
                transaction.commit();

                break;
            case  R.id.n_pic2:
                pic2.setSelected(true);
                pic1.setSelected(false);
                pic3.setSelected(false);
                FragmentTransaction transaction1 = manager.beginTransaction();
                transaction1.show(f2);
                transaction1.hide(f1);
                transaction1.hide(f3);
                transaction1.commit();

                break;
            case  R.id.n_pic3:
                pic3.setSelected(true);
                pic1.setSelected(false);
                pic2.setSelected(false);
                FragmentTransaction transaction2 = manager.beginTransaction();
                transaction2.show(f3);
                transaction2.hide(f2);
                transaction2.hide(f1);
                transaction2.commit();

                break;
        }
    }
}
