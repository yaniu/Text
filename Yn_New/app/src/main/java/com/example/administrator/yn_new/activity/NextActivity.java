package com.example.administrator.yn_new.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.yn_new.R;
import com.example.administrator.yn_new.baen.Biao;
import com.example.administrator.yn_new.baen.BiaoBean;
import com.example.administrator.yn_new.fragment.Fragment_s;
import com.example.administrator.yn_new.fragment.Fragment_v;
import com.example.administrator.yn_new.helper.NewsDao;
import com.example.administrator.yn_new.utiles.Utils;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import cz.msebera.android.httpclient.Header;

/**
 * 作者：仝晓雅 on 2017/3/10 21:14
 * 类的注释：主页（侧滑设置第三方的QQ,即主界面展示）
 */

public class NextActivity extends FragmentActivity {

    private SlidingMenu menu;
    private ArrayList<Fragment> f_list =new ArrayList<>();
    private ArrayList<String> t_list =new ArrayList<>();
    private static final String TAG = "MainActivity";
    private static final String APP_ID = "1105602574";//官方获取的APPID
    private Tencent mTencent;
    private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;
    private LinearLayout lin;
    private LinearLayout qq_lin;
    private TextView qq_name;
    private ImageView pic;
    private  String path="http://ic.snssdk.com/article/category/get/v2/?user_city=%E5%AE%89%E9%98%B3&bd_latitude=4.9E-324&bd_longitude=4.9E-324&bd_loc_time=1465099837&categories=%5B%22video%22%2C%22news_hot%22%2C%22news_local%22%2C%22news_society%22%2C%22subscription%22%2C%22news_entertainment%22%2C%22news_tech%22%2C%22news_car%22%2C%22news_sports%22%2C%22news_finance%22%2C%22news_military%22%2C%22news_world%22%2C%22essay_joke%22%2C%22image_funny%22%2C%22image_ppmm%22%2C%22news_health%22%2C%22positive%22%2C%22jinritemai%22%2C%22news_house%22%5D&version=17375902057%7C14%7C1465030267&iid=4471477475&device_id=17375902057&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=Samsung+Galaxy+S3+-+4.3+-+API+18+-+720x1280&os_api=18&os_version=4.3&openudid=7036bc89d44f680c";
    private List<BiaoBean.DataBeanX.DataBean> data;
    private TabLayout tab;
    private ViewPager pager;
    private ImageView pic1;
    private TextView xiaoxi;
    private String figureurl_qq_1;
    private String nickname;
    private ImageOptions options;
   // 默认是日间模式
    private int theme = R.style.AppTheme;
    private ImageView c_ri_pic;
    private TextView c_ri_text;
    private boolean falg=true;
    private ImageView c_phone;
    private RadioButton c_ri_jian;
    private ArrayList<String> u_list;
    int posotion =0;

    private NewsDao dao;
    private ArrayList<Biao> b1;
    private Fragment_v v;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    private static final int REQUESTCODE_PICK =1 ;
    protected static Uri tempUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 判断是否有主题存储

        if(savedInstanceState != null){

            theme = savedInstanceState.getInt("theme");
            falg=savedInstanceState.getBoolean("flag");


            setTheme(theme);

        }
        //传入参数APPID和全局Context上下文
        mTencent = Tencent.createInstance(APP_ID,NextActivity.this.getApplicationContext());
        setContentView(R.layout.next_main);
        //获取数据库对象
        dao = new NewsDao(this);
        //初始化SMSSDK
        SMSSDK.initSDK(this, "1c10824f56564", "635fa9c75d70b765f33c5a49cdf0fe5f");
        //创建侧滑对象
        menu = new SlidingMenu(this);
        //设置分向
        menu.setMode(SlidingMenu.LEFT);
        menu.setBehindOffset(100);
        menu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.ce_main);
        //侧滑的一些内容
        getSereverCe();
        //找控件
        initView();
        //获取posotion位置
        Intent intent =getIntent();
        posotion= intent.getIntExtra("url", -1);
        //当前界面的内容
        getServerData();
        //设置位置下标
        pager.setCurrentItem(posotion);
    }
    //当前界面的内容
    private void getServerData() {
        //从数据库获取信息进行赋值
        b1 = dao.jia_grid1_query();
        Biao biao =new Biao();
        biao.text="视频";
        b1.add(biao);
        for(int i = 0; i< b1.size()-1; i++){

            Fragment_s fragment =new Fragment_s();
           // fragment.setUrl(u_list.get(i));
          //  fragment.setUrl("http://v.juhe.cn/toutiao/index?type="+b1.get(i).text+"&key=32b9973df2e6ee0c2bf094b61c7d7844");
            Bundle bundle=new Bundle();
            bundle.putString("path",b1.get(i).url);
            fragment.setArguments(bundle);
            f_list.add(fragment);

        }
        if(v==null){
            v = new Fragment_v();

            f_list.add(v);
        }


        MyPager  my =new MyPager(getSupportFragmentManager());
        pager.setAdapter(my);
        tab.setupWithViewPager(pager);
        tab.setTabsFromPagerAdapter(my);


    }

    //侧滑的控件
    private void getSereverCe() {
        //找控件
        lin = (LinearLayout) menu.findViewById(R.id.c_lin);
        c_phone = (ImageView) menu.findViewById(R.id.c_phone);
        qq_lin = (LinearLayout) menu.findViewById(R.id.c_qq_lin);
        ImageView qq = (ImageView)menu.findViewById(R.id.c_QQ);

        final TextView c_sms= (TextView) menu.findViewById(R.id.c_sms);
        LinearLayout c_sc = (LinearLayout) menu.findViewById(R.id.c_sc);

        qq_name = (TextView) menu.findViewById(R.id.c_qq_name);
        pic = (ImageView) menu.findViewById(R.id.c_qq_phone);

        xiaoxi = (TextView) menu.findViewById(R.id.c_qq_xiaoxi);
        lin.setVisibility(View.VISIBLE);
        RadioGroup group = (RadioGroup) menu.findViewById(R.id.c_ri_group);
        c_ri_jian = (RadioButton) menu.findViewById(R.id.c_ri_jian);
        RadioButton c_ri_she = (RadioButton) menu.findViewById(R.id.c_ri_she);
        RadioButton c_ri_xia = (RadioButton) menu.findViewById(R.id.c_ri_xia);
        c_ri_xia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NextActivity.this, "ssdsadsd", Toast.LENGTH_SHORT).show();
            }
        });
        //设置网络
        c_ri_she.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(NextActivity.this,SheZhiActivity.class);
                startActivity(intent);
               /* final AlertDialog.Builder builder = new AlertDialog.Builder(NextActivity.this);
               builder.setTitle("请选择要选的网络");
                String [] srt =new String[]{"wifi","手机流量","网络"};
                builder.setSingleChoiceItems(srt, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                           Intent inten1 =new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                           startActivity(inten1);

                    }

                });
                builder.setSingleChoiceItems(srt, 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent =new Intent("android.settings.WIFI_SETTINGS");
                        startActivity(intent);
                    }

                });
                builder.show();*/
            }
        });
        //点击QQ头像可以更换自己的自拍
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showChoosePicDialog();
            }
        });
        //点击收藏进行跳转
        c_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(NextActivity.this,ShouCangActivity.class);
                startActivity(intent);
            }
        });
        //设置夜间模式
        c_ri_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theme = (theme == R.style.AppTheme) ? R.style.NightAppTheme : R.style.AppTheme;
                NextActivity.this.recreate();
            }
        });
        //通过手机信息进行验证登录
        c_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开注册页面
                final RegisterPage registerPage = new RegisterPage();
                registerPage.setRegisterCallback(new EventHandler() {
                    @Override
                    public void afterEvent(int event, int result, Object data) {
                        super.afterEvent(event, result, data);
                        //解析注册结果
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            HashMap<String, Object> map = (HashMap<String, Object>) data;
                            String country = (String) map.get("country");
                            String phone = (String) map.get("phone");
                            lin.setVisibility(View.GONE);
                            qq_lin.setVisibility(View.VISIBLE);

                            qq_name.setText(phone+"");
                            x.image().bind(pic, figureurl_qq_1,options);
                            x.image().bind(pic1, figureurl_qq_1,options);
                          // qq_name.setText(phone+"");
                        }
                    }
                });
                registerPage.show(NextActivity.this);

            }
        });
       //qq头像点击进行第三方登录
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NextActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
                /**通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限
                 官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
                 第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类 */
                mIUiListener = new BaseUiListener();
                //all表示获取所有权限
                mTencent.login(NextActivity.this,"all", mIUiListener);


            }
        });
    }
    //缓存数据
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putInt("theme", theme);


    }
    //恢复数据
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        theme = savedInstanceState.getInt("theme");


    }

    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            Toast.makeText(NextActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "response:" + response);
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getApplicationContext(),qqToken);
                mUserInfo.getUserInfo(new IUiListener() {

                    @Override
                    public void onComplete(Object response) {
                        Log.e(TAG,"登录成功"+response.toString());
                        lin.setVisibility(View.GONE);
                        qq_lin.setVisibility(View.VISIBLE);
                        options = new ImageOptions.Builder().setCircular(true).build();
                        JSONObject res= (JSONObject) response;
                        nickname = res.optString("nickname");
                        figureurl_qq_1 = res.optString("figureurl_qq_1");
                        Log.e(TAG, nickname +"      "+ figureurl_qq_1);
                        qq_name.setText(nickname);
                        x.image().bind(pic, figureurl_qq_1, options);
                        x.image().bind(pic1, figureurl_qq_1, options);


                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG,"登录失败"+uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG,"登录取消");

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(NextActivity.this, "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(NextActivity.this, "授权取消", Toast.LENGTH_SHORT).show();

        }

    }

    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }


    //找主界面的控件
    private void initView() {
        pic1 = (ImageView) findViewById(R.id.o_pic);
        ImageView o_sou= (ImageView) findViewById(R.id.o_sou);
        tab = (TabLayout) findViewById(R.id.o_tab);
        pager = (ViewPager) findViewById(R.id.o_pager);

        ImageView o_jia = (ImageView) findViewById(R.id.o_jia);
        o_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent =new Intent(NextActivity.this,JiaActivity.class);
                startActivity(intent);
            }
        });
        pic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             menu.toggle();
            }
        });
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        o_sou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(NextActivity.this,SouActivty.class);
                startActivity(intent);
            }
        });





    }
    /**
     * 显示修改头像的对话框
     */
    protected void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置头像");
        String[] items = { "选择本地照片", "拍照" };
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                        // 如果朋友们要限制上传到服务器的图片类型时可以直接写如：image/jpeg 、 image/png等的类型
                        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(pickIntent, REQUESTCODE_PICK);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "image.jpg"));
                        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.create().show();
    }



    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            photo = Utils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
           pic.setImageBitmap(photo);
            pic1.setImageBitmap(photo);
            uploadPic(photo);
        }
    }

    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了

        String imagePath = Utils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        Log.e("imagePath", imagePath+"");
        if(imagePath != null){
            // 拿着imagePath上传了
            // ...
        }
    }
    //viewpager进行适配
    class MyPager extends FragmentPagerAdapter{
        public MyPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return f_list.get(position);
        }

        @Override
        public int getCount() {
            return f_list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return b1.get(position).text;
        }
    }

}
