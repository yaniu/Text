package com.example.administrator.yn_yunifang.activity;




import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
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

        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        f1 = new NextActivity1();
        f2 = new NextActivity2();
        f3 = new NextActivity3();
        transaction.add(R.id.n_f, f1);
        transaction.add(R.id.n_f, f2);
        transaction.add(R.id.n_f, f3);
        transaction.commit();
        initView();
    }

    private void initView() {
        pic1 = (ImageView) findViewById(R.id.n_pic1);
        pic2 = (ImageView) findViewById(R.id.n_pic2);
        pic3 = (ImageView) findViewById(R.id.n_pic3);
        pic1.setOnClickListener(this);
        pic2.setOnClickListener(this);
        pic3.setOnClickListener(this);

        show(f1,f2,f3);
        pic1.setSelected(true);
        pic2.setSelected(false);
        pic3.setSelected(false);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.n_pic1:
                pic1.setSelected(true);
                pic2.setSelected(false);
                pic3.setSelected(false);
               show(f1,f2,f3);

                break;
            case  R.id.n_pic2:
                pic2.setSelected(true);
                pic1.setSelected(false);
                pic3.setSelected(false);
               show(f2,f1,f3);

                break;
            case  R.id.n_pic3:
                boolean falg =LogActivity.falg;
                if(!falg){
                    Intent intent =new Intent(NextActivity.this,LogActivity.class);
                   startActivityForResult(intent,1);
                }
                    pic3.setSelected(true);
                    pic1.setSelected(false);
                    pic2.setSelected(false);
                   show(f3,f1,f2);

                break;
        }
    }
    public  void show(Fragment f1,Fragment f2,Fragment f3){
        FragmentTransaction transactions = manager.beginTransaction();
        transactions.show(f1);
        transactions.hide(f2);
        transactions.hide(f3);
        transactions.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            if(requestCode==1&&resultCode==2){
                pic3.setSelected(true);
                pic1.setSelected(false);
                pic2.setSelected(false);
              show(f3,f1,f2);
            }
        }
    }
}
