package weeks.amiao.com.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;



import java.io.File;
import java.io.FileOutputStream;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * Created by lenovo on 2017/3/22.
 */

public class BitmapUtils {
    //建立一个图片缓存的文件夹
    private final  String filePath= Environment.getExternalStorageDirectory()+"/imagecache";
    File fileDir=new File(filePath);
    Handler hand=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                ImageViewToBitmap ivp= (ImageViewToBitmap) msg.obj;
                 ivp.iv.setImageBitmap(ivp.bitmap);//将拿到对象取值bitmap赋值给控件

            }

        }
    };
    //建立一个缓存的对象
    HashMap<String,SoftReference<Bitmap>> map=new HashMap<>();

    //声明对象
    private Context context;
    private InputStream stream;
    private FileOutputStream fos;
    //1.构造器
    public BitmapUtils(Context context) {
        this.context=context;
        //进行判断
        if(!fileDir.exists()){
            fileDir.mkdirs();//创建文件夹
        }

    }
    //2.加载的三种方法
    public void disPlay(ImageView iv, String url) {
        //对内存进行缓存
        Bitmap bitmap = loadMemory(iv, url);
        if(bitmap!=null){
            iv.setImageBitmap(bitmap);
        }else{
            //对sd卡进行缓存
            Bitmap bitmap1 = loadSD(iv, url);
            if(bitmap1!=null){
                iv.setImageBitmap(bitmap1);
            }else{
                //进行网络图片的加载
                loadIntenetImage(iv,url);
            }
        }

 }

    //7.缓存到内存
    private Bitmap loadMemory(ImageView iv, String url) {
        SoftReference<Bitmap> softReference = map.get(url);
        //拿到软引用的对象
        if(softReference!=null){
            //拿到其中的对象
            Bitmap bitmap = softReference.get();
            return bitmap;

        }
           return null;
    }
    //6.缓存到sd卡
    private  Bitmap loadSD(ImageView iv, String url) {
        //缓存到sd卡，首先拿到缓存的路径和文件的名称
        String name=getFileName(url);
        File file=new File(fileDir,name);
        //进行写入sd卡中
        //需要对图片进行压缩，采用了二次采样的方法
        //获得BitmapFactory的选项对象
        if(file.exists()){
            BitmapFactory.Options options=new BitmapFactory.Options();
            //第一次采样，进行设置图片的宽高
            options.inJustDecodeBounds=true;
            //将图片的路径和选项进行编码
            BitmapFactory.decodeFile(name,options);
            //获得图片的宽高
            int width = options.outWidth;
            int height = options.outHeight;
            //获得屏幕的宽高,获得显示的度量
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int widthPixels = metrics.widthPixels;
            int heightPixels = metrics.heightPixels;
            //定义一个变量
            int scale=0;
            int scale_x=width/widthPixels;
            int scale_y=height/widthPixels;
            //三目运算符，如果判断的关系为真，就选择以x轴的比例
            scale=scale_x>scale_y?scale_x:scale_y;
             if(scale==0){
                 scale=1;
             }
            //进行第二次的采样，是对内容的裁剪
            options.inJustDecodeBounds=false;
             //将比值赋值给图片的尺寸
            options.inSampleSize=scale;
            //存入图片的绝对路径，和选项对象，返回一个bitmap对象
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
           //拿到bitmap对象之后，需要将压缩好的图片放入内存的缓存中
            //因为内存大小是有限的，所以随时会被回收，就需要软引用
            //设置软引用的值
            SoftReference<Bitmap> soft=new SoftReference<Bitmap>(bitmap);
            map.put(name,soft);


            return bitmap;

        }
         return  null;
    }

    //3.网络加载图片
    private void loadIntenetImage(ImageView iv, String url) {
        //可以开启子线程进行联网操作
        new Thread(new DownLoadImage(iv,url)).start();

    }
    //写个类实现Runnable接口
    private class DownLoadImage implements Runnable{
        private ImageView iv;
        private  String url;
        public DownLoadImage(ImageView iv, String url) {
            this.iv=iv;
            this.url=url;

        }

        @Override
        public void run() {
            //进行解析
            HttpClient client=new DefaultHttpClient();
            HttpGet get=new HttpGet(url);
            Log.i("xxx",url+"");
            try {
                HttpResponse response = client.execute(get);
                if(response.getStatusLine().getStatusCode()==200){

                    stream = response.getEntity().getContent();
                     //获得图片的名称，写个方法
                    String name = getFileName(url);
                    Log.i("xxx",name+"");
                    //将文件夹放入，和图片的名称
                    File file=new File(fileDir,name);
                    //通过文件的输出流进行写入
                    fos = new FileOutputStream(file);
                    byte[] b=new byte[1024];
                    int len=0;
                    while((len= stream.read(b))!=-1){
                        fos.write(b,0,len);

                    }
                    fos.close();
                    stream.close();
                    //将图片缓存到sd卡中,拿到缓存的对象
                    Bitmap bitmap = loadSD(iv, url);
                    //将ImageView转换成Bitmap,原生的就是将ImageView通过BItmap的格式加载
                    //所以需要将ImageView转换为Bitmap
                    ImageViewToBitmap itb=new ImageViewToBitmap(iv,bitmap);
                    //通过Handler进行发送信息
                    Message message=Message.obtain(hand,0,itb);
                    message.sendToTarget();//发送消息


              }

            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                /*try {

                } catch (IOException e) {
                    e.printStackTrace();
                }
*/
            }
        }
    }

    //4.获得图片的名称
    private String getFileName(String url) {
        return Md5Utils.encode(url)+".jpg";

    }
    //5.将ImageView转换为Bitmap对象
    private class ImageViewToBitmap {
        private ImageView iv;
        private  Bitmap bitmap;
        public ImageViewToBitmap(ImageView iv, Bitmap bitmap) {
            this.bitmap=bitmap;
            this.iv=iv;

        }
    }
}
