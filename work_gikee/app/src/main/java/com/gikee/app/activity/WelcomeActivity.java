package com.gikee.app.activity;

import android.Manifest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.adapter.ViewPagerAdapter;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.greendao.LoginBean;
import com.gikee.app.greendao.SQLiteUtils;
import com.gikee.app.language.MultiLanguageUtil;
import com.gikee.app.preference_config.PreferenceUtil;
import com.gikee.app.views.MyBoldTextView;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WelcomeActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;
    private  final int REQUEST_CODE_PERMISSION = 1001;
    private  final int REQUEST_CODE_SETTING  = 3001;
    private  final int[] pics = new int[5];
    //底部导航小图片
    private ImageView[] dots ;
    //记录当前选中位置
    private int currentIndex;
    private Button logo;
    private String language = "";


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void onChangeEvent(int type) {

    }


    @Override
    protected void setUpView() {
        hideTitleBar();
        logo =(Button)findViewById(R.id.app_logo_btn);

        Locale savedLanguageType = MultiLanguageUtil.getInstance().getLanguageLocale();
        if (savedLanguageType == Locale.ENGLISH) {
            language = "en";
        } else if (savedLanguageType == Locale.SIMPLIFIED_CHINESE) {
            language = "zh_CN";
        }else if (savedLanguageType == Locale.CHINESE) {
            language = "zh_CN";
        }else
            language=savedLanguageType.getLanguage();
        if("zh".equals(language)){
            language="zh_CN";
        }

        if("en".equals(language)){
            pics[0] = R.mipmap.gikeeen_01;
            pics[1] = R.mipmap.gikeeen_02;
            pics[2] = R.mipmap.gikeeen_03;
            pics[3] = R.mipmap.gikeeen_04;
            pics[4] = R.mipmap.gikeeen_05;
        }else{
            pics[0] = R.mipmap.gikee_01;
            pics[1] = R.mipmap.gikee_02;
            pics[2] = R.mipmap.gikee_03;
            pics[3] = R.mipmap.gikee_04;
            pics[4] = R.mipmap.gikee_05;
        }
        views = new ArrayList<View>();

        init();

        vp = (ViewPager) findViewById(R.id.viewpager);
        //初始化Adapter
        vpAdapter = new ViewPagerAdapter(views);
        vp.setAdapter(vpAdapter);
        //绑定回调
        vp.setOnPageChangeListener(this);

        //初始化底部小点
        initDots();
        checkPermission();
    }

    private void init() {

        //初始化引导图片列表
        for(int i=0; i<pics.length; i++) {
            View view = LayoutInflater.from(WelcomeActivity.this).inflate(R.layout.item_guide, null);
            ImageView imageView = view.findViewById(R.id.view_background);
           // TextView textView = view.findViewById(R.id.title);
           // TextView textView1 = view.findViewById(R.id.context);
            if(i==0){
                imageView.setImageResource(pics[i]);
                //textView.setText(getString(R.string.guide_title1));
               // textView1.setText(getString(R.string.guide_title2));
            }else if(i==1){
                imageView.setImageResource(pics[i]);
//                textView.setText(getString(R.string.guide_title2));
//                textView1.setText(getString(R.string.guide_title5));
            }else if(i==2){
                imageView.setImageResource(pics[i]);
//                textView.setText(getString(R.string.guide_title3));
//                textView1.setText(getString(R.string.guide_title7));
            }else if(i==3){
                imageView.setImageResource(pics[i]);
//                textView.setText(getString(R.string.guide_title4));
//                textView1.setText(getString(R.string.guide_title7));
            }else if(i==4){
                imageView.setImageResource(pics[i]);

            }else if(i==5){
                imageView.setVisibility(View.GONE);
//                textView.setVisibility(View.GONE);
//                textView1.setVisibility(View.GONE);
                LinearLayout logolayout = view.findViewById(R.id.logo_layout);
                logolayout.setVisibility(View.VISIBLE);
            }

            views.add(view);
        }
    }

    @Override
    protected void initOnclick() {
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String savedLanguageType = MyUtils.getLocalLanguage();
                if ("zh_CN".equals(savedLanguageType)) {
                    toSettingPage();
                } else
                    toHomePage();

            }
        });
    }

    private void toSettingPage() {
        Intent intent = new Intent(WelcomeActivity.this,SettingActivity.class);
        startActivity(intent);
        finish();
    }

    private void toHomePage() {
        Intent intent = new Intent(WelcomeActivity.this,HomePageActivity.class);
        startActivity(intent);
        finish();
    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

        dots = new ImageView[pics.length];

        //循环取得小点图片
        for (int i = 0; i < pics.length; i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(false);//都设为灰色
            dots[i].setOnClickListener(this);
            dots[i].setTag(i);//设置位置tag，方便取出与当前位置对应
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(true);//设置为白色，即选中状态
    }

    /**
     *设置当前的引导页
     */
    private void setCurView(int position)
    {
        if (position < 0 || position >= pics.length) {
            return;
        }

        vp.setCurrentItem(position);
    }

    /**
     *这只当前引导小点的选中
     */
    private void setCurDot(int positon)
    {
        if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
            return;
        }

        dots[positon].setEnabled(true);
        dots[currentIndex].setEnabled(false);

        currentIndex = positon;
    }

    //当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    //当当前页面被滑动时调用
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    //当新的页面被选中时调用
    @Override
    public void onPageSelected(int arg0) {
        //设置底部小点选中状态
        setCurDot(arg0);
        if(arg0==4){
            logo.setVisibility(View.VISIBLE);
        }else
            logo.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        int position = (Integer)v.getTag();
        setCurView(position);
        setCurDot(position);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener);
    }



    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。
            if (requestCode == REQUEST_CODE_PERMISSION) {
                // TODO 相应代码。
                //doNext();
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。

            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(WelcomeActivity.this, deniedPermissions)) {
                // 第一种：用默认的提示语。
                AndPermission.defaultSettingDialog(WelcomeActivity.this, REQUEST_CODE_SETTING).setNegativeButton(R.string.cannel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(SplashActivity.this, "取消", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }).show();

                // 第二种：用自定义的提示语。
                // AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
                // .setTitle("权限申请失败")
                // .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                // .setPositiveButton("好，去设置")
                // .show();

            } else {
                finish();
            }
        }
    };

    public void checkPermission() {

        // 先判断是否有权限。
        if (AndPermission.hasPermission(this, //Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // 有权限，直接do anything.

           // doNext();

        } else {
            // 申请多个权限。
            AndPermission.with(this)
                    .requestCode(REQUEST_CODE_PERMISSION)
                    .permission(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                    .rationale(new RationaleListener() {
                        @Override
                        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                            // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                            AndPermission.rationaleDialog(WelcomeActivity.this, rationale)
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    }).show();
                        }
                    })
                    .send();

        }
    }

    }
