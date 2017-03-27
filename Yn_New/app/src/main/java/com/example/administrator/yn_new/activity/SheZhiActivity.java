package com.example.administrator.yn_new.activity;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shezhi_mal);
        /* f_list.add("网络");
         f_list.add("字体");
         z_list.add("");*/

          ExpandableListView elv = (ExpandableListView) findViewById(R.id.shezhi_elv);
           elv.setAdapter(new MyList());

    }
    class MyList extends BaseExpandableListAdapter{

        @Override
        public int getGroupCount() {
          //  return f_list.size();
            return fu_str.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            //return z_list.size();
            return  zi_str.length;
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
            }
            if(groupPosition==1&&childPosition==0){
                //MyApp.info=0;

            }if(groupPosition==1&&childPosition==1){
               // MyApp.info=1;
            }
            return true;
        }
    }
}
