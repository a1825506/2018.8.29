package com.gikee.app.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gikee.app.R;
import com.gikee.app.Utils.LogUtils;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.beans.RemindVlaueBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.fragment.BaseFragment;
import com.gikee.app.fragment.MineFragment;
import com.gikee.app.fragment.NewAddressCollectFragment;
import com.gikee.app.fragment.NewHomeFragment;
import com.gikee.app.fragment.RemindFragment;
import com.gikee.app.language.LanguageType;
import com.gikee.app.language.MultiLanguageUtil;
import com.gikee.app.language.OnChangeLanguageEvent;
import com.gikee.app.preference_config.PreferenceUtil;
import com.gikee.app.presenter.version.VersionPresenter;
import com.gikee.app.presenter.version.VersionView;
import com.gikee.app.receiver.NetStatusReceiver;
import com.gikee.app.resp.RateBeanResp;
import com.gikee.app.resp.RateResp;
import com.gikee.app.resp.RemindInfoResp;
import com.gikee.app.resp.VersionResp;
import com.gikee.app.service.AccountBalanceService;
import com.gikee.app.service.WebSocketService;
import com.gikee.app.views.dialogs.VersionUpdataDialog;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class HomePageActivity extends BaseActivity implements VersionView {

    private LinearLayout lin_allproject, lin_mineproject, lin_mine,line_leader,tip_layout;
    private FrameLayout lin_find;
    private ImageView   img_remind,delete;
    private ImageView img_find,img_allproject,img_mineproject,img_mine,img_leader;
    private TextView tx_allproject, tx_mineproject, tx_mine, tx_find,tx_leader;
    private BaseFragment currentFragment;
    private BaseFragment mineProjectFragment,mineFragment, mineAddressFragment,leaderboardFragment;
   // private MessageReceiver mMessageReceiver;
    private VersionPresenter mPrsenter;
    private boolean isRemind=false;
    private String IsReadRemind="IsReadRemind";
    private  Intent intent,intent2;

    private Intent msgIntent = new Intent(Commons.MESSAGE_RECEIVED_ACTION);

    private Context context;
    private NetStatusReceiver netWorkChangReceiver;


    List<RemindVlaueBean> remindVlaueBeanList=new ArrayList<>();

    @Override
    protected VersionPresenter createPresenter() {
        return new VersionPresenter(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mPrsenter = new VersionPresenter(this);
        context = this;
        MyUtils.setUnit();
        MyUtils.setQuateSymbol();
       // CollectObserverService.getInstance().registerObserver(this);
        registerMessageReceiver();

        mhandle.sendEmptyMessage(0);

        getNetData();

        Log.e("ppppp",MyUtils.getRemindTime());

    }

    @Override
    protected void onChangeEvent(int type) {

    }


    Handler   mhandle = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);


                intent = new Intent(context, WebSocketService.class);

//                startService(intent);


//                intent2 = new Intent(context, AccountBalanceService.class);
//
//                startService(intent2);




            }
        };



    private void getNetData() {

        String language = "zh_CN";
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

        mPrsenter.checkAppUpdate(MyUtils.getAppVersionName(HomePageActivity.this),language);
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
         int   messageType = intent.getIntExtra("message1", 0);

        if (messageType == Commons.Remind) {
            onTabSelected(R.id.main_tab_layout);
        }
    }


    @Override
    protected void setUpView() {

        hideTitleBar();
        delete = findViewById(R.id.delete);
        tip_layout = findViewById(R.id.tip_layout);
        line_leader = findViewById(R.id.main_leader_layout);
        lin_find = findViewById(R.id.main_tab_layout);
        lin_allproject = findViewById(R.id.main_message_layout);
        lin_mine = findViewById(R.id.main_mine_layout);
        lin_mineproject = findViewById(R.id.main_vip_layout);
        img_allproject = findViewById(R.id.main_message_img);
        tx_allproject = findViewById(R.id.main_message_tx);
        img_find = findViewById(R.id.main_tab_img);
        img_remind = findViewById(R.id.remind);
        img_mineproject = findViewById(R.id.main_vip_img);
        tx_mineproject = findViewById(R.id.main_vip_tx);
        tx_find = findViewById(R.id.main_tab_tx);
        img_mine = findViewById(R.id.main_mine_img);
        tx_mine = findViewById(R.id.main_mine_tx);
        tx_leader = findViewById(R.id.main_leader_tx);
        img_leader = findViewById(R.id.main_leader_img);
        findViewById(R.id.base_line).setVisibility(View.GONE);

//        if(PreferenceUtil.getBoolean(IsReadRemind,false)){
//            img_remind.setVisibility(View.GONE);
//        }else
//            img_remind.setVisibility(View.GONE);

        mineProjectFragment = new NewAddressCollectFragment(HomePageActivity.this);
//        searchFragment = new HomeFragment();
        mineFragment = new MineFragment(HomePageActivity.this);
        mineAddressFragment = new RemindFragment(HomePageActivity.this);
        leaderboardFragment = new NewHomeFragment();
        onTabSelected(lin_mineproject.getId());

        regiestBoardcast();


        if(getIntent()!=null&&getIntent().getData()!=null){
            String action = getIntent().getData().getQueryParameter("action");
            if("remind".equals(action)){
                //点击通知直接跳转提醒fragment
                onTabSelected(R.id.main_tab_layout);

            }

        }

    }


    private void regiestBoardcast(){

        netWorkChangReceiver = new NetStatusReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkChangReceiver, filter);

    }


    @Override
    protected void initOnclick() {


        lin_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mineAddressFragment.isVisible()){
                    onTabSelected(lin_find.getId());
                    LocalBroadcastManager.getInstance(HomePageActivity.this).sendBroadcast(msgIntent);
                }

            }
        });
        lin_allproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mineProjectFragment.isVisible())
                    onTabSelected(lin_allproject.getId());
            }
        });
        lin_mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mineFragment.isVisible())
                    onTabSelected(lin_mine.getId());
            }
        });


        lin_mineproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!leaderboardFragment.isVisible())
                    onTabSelected(lin_mineproject.getId());
            }
        });
    }

    private FragmentTransaction switchFragment(BaseFragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            //第一次使用switchFragment()时currentFragment为null，所以要判断一下
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.main_fragment, targetFragment, targetFragment.getClass().getName());
//            transaction.replace(R.id.main_fragment, targetFragment,targetFragment.getClass().getName());
//            transaction.addToBackStack(targetFragment.getClass().getName());
//            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);



        } else {
            transaction.hide(currentFragment).show(targetFragment);
        }
        currentFragment = targetFragment;
        return transaction;
    }

    public void onTabSelected(@IdRes int tabId) {
        LogUtils.showLog("id=" + tabId);
        if (tabId == R.id.main_tab_layout) {
            isRemind=true;
            switchFragment(mineAddressFragment).commitAllowingStateLoss();
        }
        if (tabId == R.id.main_message_layout) {
            isRemind=false;
            switchFragment(mineProjectFragment).commitAllowingStateLoss();
        }
        if (tabId == R.id.main_mine_layout) {
            isRemind=false;
            switchFragment(mineFragment).commitAllowingStateLoss();
        }
        if (tabId == R.id.main_vip_layout) {
            isRemind=false;
            switchFragment(leaderboardFragment).commitAllowingStateLoss();
        }
//        if (tabId == R.id.main_leader_layout) {
//            isRemind=false;
//            switchFragment(searchFragment).commitAllowingStateLoss();
//        }
        setUI(tabId);
    }

    private void setUI(@IdRes int tabId) {
        tx_mine.setTextColor(ContextCompat.getColor(HomePageActivity.this, R.color.home_navite));
        tx_find.setTextColor(ContextCompat.getColor(HomePageActivity.this, R.color.home_navite));
        tx_allproject.setTextColor(ContextCompat.getColor(HomePageActivity.this, R.color.home_navite));
        tx_mineproject.setTextColor(ContextCompat.getColor(HomePageActivity.this, R.color.home_navite));
        tx_leader.setTextColor(ContextCompat.getColor(HomePageActivity.this, R.color.home_navite));
        img_find.setBackground(getResources().getDrawable(R.mipmap.remind));
        img_allproject.setBackground(getResources().getDrawable(R.mipmap.monitor_gray));
        img_mineproject.setBackground(getResources().getDrawable(R.mipmap.home_gray));
        img_mine.setBackground(getResources().getDrawable(R.mipmap.me_gray));
        img_leader.setBackground(getResources().getDrawable(R.mipmap.ranking_gray));
        switch (tabId) {
            case R.id.main_tab_layout:
                img_remind.setVisibility(View.GONE);
                PreferenceUtil.commitBoolean(IsReadRemind,true);
                tx_find.setTextColor(ContextCompat.getColor(HomePageActivity.this, R.color.title_color));
               // img_find.setTextColor(ContextCompat.getColor(HomePageActivity.this, R.color.title_color));
                img_find.setBackground(getResources().getDrawable(R.mipmap.remind_gray));
                break;
            case R.id.main_message_layout:
                tx_allproject.setTextColor(ContextCompat.getColor(HomePageActivity.this, R.color.title_color));
                img_allproject.setBackground(getResources().getDrawable(R.mipmap.monitor));
                break;
            case R.id.main_mine_layout:
                tx_mine.setTextColor(ContextCompat.getColor(HomePageActivity.this, R.color.title_color));
                img_mine.setBackground(getResources().getDrawable(R.mipmap.me));
                break;
            case R.id.main_vip_layout:
                tx_mineproject.setTextColor(ContextCompat.getColor(HomePageActivity.this, R.color.title_color));
                img_mineproject.setBackground(getResources().getDrawable(R.mipmap.home));
                break;
            case R.id.main_leader_layout:
                tx_leader.setTextColor(ContextCompat.getColor(HomePageActivity.this, R.color.title_color));
                img_leader.setBackground(getResources().getDrawable(R.mipmap.ranking));
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    // 第一次按下返回键的事件
    private long firstPressedTime;

    // System.currentTimeMillis() 当前系统的时间
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - firstPressedTime < 2000) {
            super.onBackPressed();
        } else {
            ToastUtils.showCenter(getString(R.string.press_exit));
            firstPressedTime = System.currentTimeMillis();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netWorkChangReceiver);
        if(intent!=null){
            stopService(intent);
        }

        if(intent2!=null){
            stopService(intent2);
        }

    }

    public void registerMessageReceiver() {
       // mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(Commons.MESSAGE_RECEIVED_ACTION);
       // LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }


//    public class MessageReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            try {
//                if (Commons.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
//
//                    if(!isRemind){
//                        PreferenceUtil.commitBoolean(IsReadRemind,false);
//                        img_remind.setVisibility(View.VISIBLE);
//                    }
//                }
//            } catch (Exception e){
//
//            }
//        }
//    }



    @Override
    public void onVersionInfo(VersionResp versionResp) {

        if(versionResp.getResult()!=null){

            if(versionResp.getResult().getNewVersion()!=null){

                if(versionResp.getResult().getNewVersion().compareTo(MyUtils.getAppVersionName(HomePageActivity.this))>0){

                    new VersionUpdataDialog(HomePageActivity.this,R.style.dialog,versionResp.getResult()).setTitle().show();


                }


            }


        }

        mPrsenter.getExchangeRate();



    }



    @Override
    public void onExchangeRate(RateResp resp) {

        if(TextUtils.isEmpty(resp.getErrInfo())){

            if(resp.getResult()!=null){

                Commons.rate = resp.getResult().getRate();

                mPrsenter.RateBeanResp();

            }


        }



    }

    @Override
    public void onBTCRate(RateBeanResp resp) {

        if(TextUtils.isEmpty(resp.getErrInfo())){

            if(resp.getResult().getData()!=null){

                if(resp.getResult().getData().size()>1){

                    Commons.rateUSD_btc = Float.parseFloat(resp.getResult().getData().get(0).getUsd());

                    Commons.rateCNY_btc = Float.parseFloat(resp.getResult().getData().get(0).getCny());

                    Commons.rateUSD_eth = Float.parseFloat(resp.getResult().getData().get(1).getUsd());

                    Commons.rateCNY_eth = Float.parseFloat(resp.getResult().getData().get(1).getCny());
                }



            }

        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeLanguageEvent(OnChangeLanguageEvent event){
        if (event.languageType== LanguageType.LANGUAGE_EN||event.languageType==LanguageType.LANGUAGE_CHINESE_SIMPLIFIED||event.languageType==LanguageType.LANGUAGE_FOLLOW_SYSTEM){
            set();
        }


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RemindInfoResp remindInfoResp) {

        boolean newRemindInfo=true;

        int totalsize_local = PreferenceUtil.getInt(Commons.totalMessageSize,0);

        int totalsize_now = remindInfoResp.getResult().getData().size();

        if(totalsize_local==totalsize_now){
            newRemindInfo=false;
        }



        if(!isRemind){
            if(newRemindInfo){
                PreferenceUtil.commitBoolean(IsReadRemind,false);
                img_remind.setVisibility(View.VISIBLE);
            }else{
                PreferenceUtil.commitBoolean(IsReadRemind,true);
                img_remind.setVisibility(View.GONE);
            }

        }

        remindVlaueBeanList = remindInfoResp.getResult().getData();

    }

    /**
     * 设置语言
     */
    private void set() {
        // 本地语言设置
        finish();
        startActivity(getIntent());

    }


}



