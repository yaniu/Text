package com.example.administrator.yn_new.activity;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.yn_new.R;
import com.example.administrator.yn_new.utiles.MyApp;

import java.util.ArrayList;


/**
 * 作者：仝晓雅 on 2017/3/27 20:28
 * 类的注释：
 */

public class SheZhiActivity extends Activity {

    private String [] fu_str =new String[]{"设置网络","设置字体"};
    private String [] []zi_str =new String[][]{{"wifi","手机流量","网络"},{"小","中","大"}};
  /*  private ArrayList<String> f_list =new ArrayList<>();

   private ArrayList<String> z_list =new ArrayList<>();
*/
//手势滑动，使用手势监视器这个对象GestureDetector
  final int RIGHT = 0;
    private GestureDetector gestureDetector;
    final int LEFT = 1;
    private ExpandableListView elv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shezhi_mal);
        /* f_list.add("网络");
         f_list.add("字体");
         z_list.add("");*/

        elv = (ExpandableListView) findViewById(R.id.shezhi_elv);
        ImageView img = (ImageView) findViewById(R.id.c_s_fan);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SheZhiActivity.this,NextActivity.class);
                startActivity(intent);
            }
        });
           elv.setAdapter(new MyList());
        //手指滑动
        gestureDetector=new GestureDetector(SheZhiActivity.this,onGestureListener);

    }
   //手指滑动
    private GestureDetector.OnGestureListener onGestureListener=new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //e1就是初始状态的MotionEvent对象，e2就是滑动了过后的MotionEvent对象
            //velocityX和velocityY就是滑动的速率
            float x = e2.getX() - e1.getX();//滑动后的x值减去滑动前的x值 就是滑动的横向水平距离(x)
            float y = e2.getY() - e1.getY();//滑动后的y值减去滑动前的y值 就是滑动的纵向垂直距离(y)
            Log.w("tag", "x>" + x);
            Log.w("tag", "y>" + y);
            Log.w("tag", "velocityX>" + velocityX);
            Log.w("tag", "velocityY>" + velocityY);
            //如果滑动的横向距离大于100，表明是右滑了，那么就执行下面的方法，可以是关闭当前的activity
            if (x > 100) {
                doResult(RIGHT);
                Log.w("tag", "RIGHT>" + x);
            }
            //如果滑动的横向距离大于100，表明是左滑了(因为左滑为负数，所以距离大于100就是x值小于-100)
            if (x < -100) {
                Log.w("tag", "LEFT>" + x);
                doResult(LEFT);
            }

            return true;
        }
    };
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {    //注意这里不能用ONTOUCHEVENT方法，不然无效的
        gestureDetector.onTouchEvent(ev);
        elv.onTouchEvent(ev);//这几行代码也要执行，将webview载入MotionEvent对象一下，况且用载入把，不知道用什么表述合适
        return super.dispatchTouchEvent(ev);
    }

    //触摸监听的方法
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println(" ACTION_DOWN");//手指在屏幕上按下
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println(" ACTION_MOVE");//手指正在屏幕上滑动
                break;
            case MotionEvent.ACTION_UP:
                System.out.println(" ACTION_UP");//手指从屏幕抬起了
                break;
            default:
                break;
        }
        return gestureDetector.onTouchEvent(event);
    }

    //结果
    public void doResult(int action) {

        switch (action) {
            case RIGHT:
                System.out.println("go right");
                finish();
                break;
            case LEFT:
                System.out.println("go left");
                break;
        }
    }
    class MyList extends BaseExpandableListAdapter{

        @Override
        public int getGroupCount() {

            return fu_str.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {

            return  zi_str[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return fu_str[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return zi_str [childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            convertView =View.inflate(SheZhiActivity.this,R.layout.she_fu_xml,null);
            TextView text = (TextView) convertView.findViewById(R.id.s_f_text);
            text.setText(fu_str[groupPosition]);

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            convertView =View.inflate(SheZhiActivity.this,R.layout.she_fu_xml,null);
            TextView text = (TextView) convertView.findViewById(R.id.s_f_text);
            text.setText(zi_str[groupPosition][childPosition]);

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {

            if(groupPosition==0&&childPosition==0){
                Intent inten1 =new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(inten1);

            }if(groupPosition==0&&childPosition==1){
                Intent inten1 =new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(inten1);
            }if(groupPosition==0&&childPosition==2){
                Intent inten1 =new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(inten1);
            }
            if(groupPosition==1&&childPosition==0){
                MyApp.fontInt=0;

            }if(groupPosition==1&&childPosition==1){
                MyApp.fontInt=1;
            }if(groupPosition==1&&childPosition==2){
                MyApp.fontInt=2;
            }
            return true;
        }
    }
}
