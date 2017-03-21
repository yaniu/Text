package com.example.administrator.yn_new.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.yn_new.baen.Biao;
import com.example.administrator.yn_new.baen.ShouBean;

import java.util.ArrayList;

/**
 * 作者：仝晓雅 on 2017/3/14 21:22
 * 类的注释：
 */

public class NewsDao {

    private final DBHelper helper;

    public NewsDao(Context context){

        helper = new DBHelper(context);
    }

    public  void sc_add(String title,String text1,String text2,String text3,String pic1,String pic2,String pic3,String url){
        SQLiteDatabase database = helper.getWritableDatabase();
        String sql ="insert into sc(title,text1,text2,text3,pic1,pic2,pic3,url) values(?,?,?,?,?,?,?,?);";
        database.execSQL(sql,new Object[]{title,text1,text2,text3,pic1,pic2,pic3,url});
    }
    public  ArrayList<ShouBean> query_SC(){
        ArrayList<ShouBean> list =new ArrayList<>();
        SQLiteDatabase database = helper.getWritableDatabase();
        String sql ="select * from sc";
        Cursor cursor = database.rawQuery(sql, new String[]{});
        while (cursor.moveToNext()){
            ShouBean bean =new ShouBean();
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String text1 = cursor.getString(cursor.getColumnIndex("text1"));
            String text2 = cursor.getString(cursor.getColumnIndex("text2"));
            String text3 = cursor.getString(cursor.getColumnIndex("text3"));
            String pic1= cursor.getString(cursor.getColumnIndex("pic1"));
            String pic2 = cursor.getString(cursor.getColumnIndex("pic2"));
            String pic3 = cursor.getString(cursor.getColumnIndex("pic3"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            bean.id=id;
           bean.title=title;
            bean.pic1=pic1;
            bean.pic2=pic2;
            bean.pic3=pic3;
            bean.text1=text1;
            bean.text2=text2;
            bean.text3=text3;
            bean.url=url;
            list.add(bean);

        }
        return  list;
    }
    public  void biao_add(String text){
        SQLiteDatabase database = helper.getWritableDatabase();
        String sql ="insert into biao(text) values(?)";
        database.execSQL(sql,new Object[]{text});
    }
    public  void jia_grid1(String text,String url){
        SQLiteDatabase database = helper.getWritableDatabase();
        String sql ="insert into jia_grid1(text,url) values(?,?)";

        database.execSQL(sql,new Object[]{text,url});
    }
    public  void jia_grid2(String text){
        SQLiteDatabase database = helper.getWritableDatabase();
        String sql ="insert into jia_grid2(text) values(?)";
        database.execSQL(sql,new Object[]{text});
    }
    public  void jia_grid2_delete(int id){
        SQLiteDatabase database = helper.getWritableDatabase();
        String sql ="delete from jia_grid2 where _id =?";
        database.execSQL(sql,new Object[]{id});
    }
    public   ArrayList<Biao> biao_query(){
        ArrayList<Biao> list =new ArrayList<>();
        SQLiteDatabase database = helper.getWritableDatabase();
        String sql ="select * from biao";
        Cursor cursor = database.rawQuery(sql, new String[]{});
        while (cursor.moveToNext()){
            Biao biao =new Biao();
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String text = cursor.getString(cursor.getColumnIndex("text"));
            biao.id=id;
            biao.text=text;
            list.add(biao);
        }
        return  list;
    }
    public   ArrayList<Biao> jia_grid1_query(){
        ArrayList<Biao> list =new ArrayList<>();
        SQLiteDatabase database = helper.getWritableDatabase();
        String sql ="select * from jia_grid1";
        Cursor cursor = database.rawQuery(sql, new String[]{});
        while (cursor.moveToNext()){
            Biao biao =new Biao();
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String text = cursor.getString(cursor.getColumnIndex("text"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            biao.id=id;
            biao.text=text;
            biao.url=url;
            list.add(biao);
        }
        return  list;
    }
    public   ArrayList<Biao> jia_grid2_query(){
        ArrayList<Biao> list =new ArrayList<>();
        SQLiteDatabase database = helper.getWritableDatabase();
        String sql ="select * from jia_grid2";
        Cursor cursor = database.rawQuery(sql, new String[]{});
        while (cursor.moveToNext()){
            Biao biao =new Biao();
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String text = cursor.getString(cursor.getColumnIndex("text"));
            biao.id=id;
            biao.text=text;
            list.add(biao);
        }
        return  list;
    }

}
