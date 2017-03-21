package com.example.administrator.yn_new.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * 作者：仝晓雅 on 2017/3/14 21:18
 * 类的注释：
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context,"news.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sc ="create table sc(_id Integer primary key autoincrement,title char(20),text1 char(20),text2 char(20),text3 char(20),pic1 char(20),pic2 char(20),pic3 char(20),url char(20))";
        db.execSQL(sc);
        String biao_sql ="create table biaoti(_id Integer primary key autoincrement,text char(20))";
        db.execSQL(biao_sql);
        String jia_sql1 ="create table jia_grid1(_id Integer primary key autoincrement,text char(20), url char(20))";
        db.execSQL(jia_sql1);
        String sql ="insert into jia_grid1(text,url) values(?,?)";
        String [] str =new String[]{"阳光","热点","北京","社会",
                "订阅","娱乐","科技","汽车","体育","财经"
        };
        ArrayList<String> u_list= new ArrayList<>();
        u_list.add("http://v.juhe.cn/toutiao/index?type=shehui&key=32b9973df2e6ee0c2bf094b61c7d7844");
        u_list.add("http://v.juhe.cn/toutiao/index?type=guonei&key=32b9973df2e6ee0c2bf094b61c7d7844");
        u_list.add("http://v.juhe.cn/toutiao/index?type=guoji&key=32b9973df2e6ee0c2bf094b61c7d7844");
        u_list.add("http://v.juhe.cn/toutiao/index?type=top&key=32b9973df2e6ee0c2bf094b61c7d7844");
        u_list.add("http://v.juhe.cn/toutiao/index?type=shishang&key=32b9973df2e6ee0c2bf094b61c7d7844");
        u_list.add("http://v.juhe.cn/toutiao/index?type=yule&key=32b9973df2e6ee0c2bf094b61c7d7844");
        u_list.add("http://v.juhe.cn/toutiao/index?type=keji&key=32b9973df2e6ee0c2bf094b61c7d7844");
        u_list.add("http://v.juhe.cn/toutiao/index?type=qiche&key=32b9973df2e6ee0c2bf094b61c7d7844");
        u_list.add("http://v.juhe.cn/toutiao/index?type=tiyu&key=32b9973df2e6ee0c2bf094b61c7d7844");
        u_list.add("http://v.juhe.cn/toutiao/index?type=caijing&key=32b9973df2e6ee0c2bf094b61c7d7844");

      for(int i =0;i<str.length;i++){
          db.execSQL(sql,new Object[]{str[i],u_list.get(i)});
      }
        String jia_sql2 ="create table jia_grid2(_id Integer primary key autoincrement,text char(20))";
        db.execSQL(jia_sql2);
        String sql2 ="insert into jia_grid2(text) values(?)";
        String [] str2 =new String[]{"小说","历史","美食","时尚","育儿","搞笑",
                "数码","养生","宠物","家具","旅游","文化","星座","精选",
                "收藏","股票","情感","唱将",
        };
        for(int j =0;j<str2.length;j++){
            db.execSQL(sql2,new Object[]{str2[j]});
        }



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
