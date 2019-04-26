package com.gikee.app.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gikee.app.R;
import com.gikee.app.Utils.ActManager;
import com.gikee.app.Utils.MyContextWrapper;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.activity.HomePageActivity;
import com.gikee.app.datas.Commons;
import com.gikee.app.language.MultiLanguageUtil;
import com.gikee.app.language.OnChangeLanguageEvent;
import com.gikee.app.presenter.base.BasePresenter;
import com.gikee.app.presenter.base.BaseView;
import com.gikee.app.receiver.NetStatusReceiver;
import com.gikee.app.views.IconView;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

import butterknife.ButterKnife;


/**
 * Created by THINKPAD on 2018/3/13.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {

    protected ImmersionBar mImmersionBar;
    protected P mPresenter;
    private TextView title;
    private IconView title_back,toolbar_right_img;
    private  ImageView toolbar_right_collet;
    private TextView title_right;
    private LinearLayout title_linearLayout;
    private RelativeLayout contentView;
    private LinearLayout right_img_linearLayout;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);

        EventBus.getDefault().register(this);

        //状态栏以及底部的导航栏（虚拟按键）
        if (isImmersionBarEnabled())
            initImmersionBar();

        initView();

        //网络变化监听注册。静态注册网络状态监听器在7.0即系统24之后是失效的，接收不到系统广播的。只能通过动态注册。

        mPresenter = createPresenter();
        ActManager.getAppManager().addActivity(this);
        PushAgent.getInstance(this).onAppStart();
    }


    protected abstract void onChangeEvent(int type);


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeLanguageEvent(OnChangeLanguageEvent event){

        onChangeEvent(event.languageType);

    }





    private void initView() {
        contentView = (RelativeLayout) findViewById(R.id.re_basecontent);
        title = (TextView) findViewById(R.id.toolbar_title);
        title_back =  (IconView) findViewById(R.id.toolbar_back);
        toolbar_right_img =(IconView)findViewById(R.id.toolbar_right_img);
        toolbar_right_collet = (ImageView)findViewById(R.id.toolbar_right_collect);

        title_right = (TextView) findViewById(R.id.toolbar_right);
        title_linearLayout=(LinearLayout) findViewById(R.id.toolbar_layout);
        right_img_linearLayout = (LinearLayout) findViewById(R.id.right_img);
        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                hideSoftInput();
            }
        });


    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MultiLanguageUtil.attachBaseContext(newBase));


//       Context context =  MyContextWrapper.wrap(newBase, MultiLanguageUtil.getLanguageLocale());
//        super.attachBaseContext(context);


    }



    public void setContentView(int layoutResID){
        contentView.removeAllViews();


        getLayoutInflater().inflate(layoutResID,contentView,true);
        ButterKnife.bind(this);
        setUpView();
        initOnclick();
        initData();
    }

    protected abstract void setUpView();


    public void setTitle(String mtitle){
        title.setText(mtitle);
    }

    public void setRightCollect(int imgid){

       // toolbar_right_collet.setTextColor(ContextCompat.getColor(BaseActivity.this, imgid));

        toolbar_right_collet.setBackground(getResources().getDrawable(imgid));



    }

    public void hideRightCollect(int showid){

        toolbar_right_collet.setVisibility(showid);



    }

    public void setBackground(int colorid){
        title_linearLayout.setBackgroundColor(getResources().getColor(colorid));
    }

    public void setTitleColor(int colorid){

        title_back.setTextColor(getResources().getColor(colorid));

        title.setTextColor(getResources().getColor(colorid));

      //  toolbar_right_collet.setTextColor(getResources().getColor(R.color.home_navite));

        toolbar_right_img.setTextColor(getResources().getColor(colorid));

    }



    private IOnclik iOnclik;

    public interface IOnclik{

        void OnClickSave();

        void OnImgClick(View view);

        void OnImgCollect();

    }



    public void showRightCollect(){

        toolbar_right_collet.setVisibility(View.GONE);

    }


    public void showRightTitle(String title,final IOnclik iOnclik){

        this.iOnclik = iOnclik;

        if(!TextUtils.isEmpty(title)){

            title_right.setText(title);

        }

        title_right.setVisibility(View.VISIBLE);


        title_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iOnclik.OnClickSave();

            }
        });

    }


    public void showRightImg(final IOnclik iOnclik){

        this.iOnclik = iOnclik;


       // toolbar_right_img.setVisibility(View.VISIBLE);

        toolbar_right_collet.setVisibility(View.VISIBLE);


        toolbar_right_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iOnclik.OnImgClick(view);

            }
        });

        toolbar_right_collet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iOnclik.OnImgCollect();

            }
        });


    }

    public void showTitleBar(){
        title_linearLayout.setVisibility(View.VISIBLE);
    }

    public void hideTitleBar(){
        title_linearLayout.setVisibility(View.GONE);
    }

    protected abstract void initOnclick();

    protected void initData() {
    }

    private void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        if (checkDeviceHasNavigationBar(this)) {
            mImmersionBar.statusBarDarkFont(true).hideBar(BarHide.FLAG_SHOW_BAR).statusBarDarkFont(false).statusBarColorTransform(R.color.appcolors).navigationBarColor(R.color.transparent).init();
        } else {
            mImmersionBar.statusBarDarkFont(true).statusBarDarkFont(false).statusBarColorTransform(R.color.appcolors).init();
        }
    }


    protected void hideSoftInput(){

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),0);
    }

    protected boolean isImmersionBarEnabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            return true;
        else
            return false;
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        ActManager.getAppManager().finishActivity(this);
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();


    }

    public boolean checkDeviceHasNavigationBar(Context activity) {

        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(activity)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            // 做任何你需要做的,这个设备有一个导航栏
            return true;
        }
        if (hasSoftKeys())
            return true;
        return false;
    }

//    @Override
//    public Resources getResources() {
//        Resources res = super.getResources();
//        Configuration config = new Configuration();
//        config.setToDefaults();
//        res.updateConfiguration(config, res.getDisplayMetrics());
//        return res;
//    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private boolean hasSoftKeys() {
        WindowManager windowManager = getWindowManager();
        Display d = windowManager.getDefaultDisplay();
        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        d.getRealMetrics(realDisplayMetrics);
        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);
        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;
        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
    }

    protected P createPresenter() {

        return null;
    }


    @Override
    public void showErrorMessage(String msg) {
        ToastUtils.showCenter(msg);
    }


    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }



}
