package com.example.administrator.tongxiaoya1502l2017321.utilues;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * 作者：仝晓雅 on 2017/3/21 08:55
 * 类的注释：
 */

public class Utilues {
    public  static  String path (InputStream is){
        byte [] by =new byte[1024];
        int num =0;
        ByteArrayOutputStream bos =new ByteArrayOutputStream();
        try {
            while ((num =is.read(by))!=-1){
               bos.write(by,0,num);
            }
            return  bos.toString();
        }catch (Exception e){

        }
        return  null;
    }
}
