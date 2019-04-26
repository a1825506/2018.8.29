package com.gikee.app.base;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.multidex.MultiDexApplication;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.coloros.mcssdk.PushManager;
import com.coloros.mcssdk.callback.PushAdapter;
import com.coloros.mcssdk.callback.PushCallback;
import com.gikee.app.Utils.ChannelUtil;
import com.gikee.app.Utils.CrashHandler;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.datas.Commons;
import com.gikee.app.datas.MyOpenHelper;
import com.gikee.app.greendao.DaoMaster;
import com.gikee.app.greendao.DaoSession;
import com.gikee.app.greendao.RemindBean;
import com.gikee.app.greendao.SQLiteUtils;
import com.gikee.app.language.MultiLanguageUtil;
import com.gikee.app.preference_config.PreferenceUtil;
import com.gikee.app.resp.RateBeanResp;
import com.gikee.app.resp.RateResp;
import com.gikee.app.resp.RemindResp;
import com.google.gson.Gson;
import com.meituan.android.walle.WalleChannelReader;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.entity.UMessage;
import com.umeng.message.tag.TagManager;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.android.agoo.huawei.HuaWeiRegister;
import org.android.agoo.mezu.MeizuRegister;
import org.android.agoo.xiaomi.MiPushRegistar;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Locale;
import com.gikee.app.presenter.version.VersionPresenter;


/**
 *
 *
 * 继承 MultiDexApplication 解决Dex超出方法数的限制问题：
 * Android 的 classLoader 在加载 APK 的时候限制了class.dex 包含的 Java 方法数，其总数不能超过65535（64k）
 */

public class GikeeApplication extends MultiDexApplication implements com.gikee.app.presenter.version.VersionView {
    private Typeface typeface, typeface_bold,typeface_icons;

    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static GikeeApplication instances;
    private Handler mHandler;
    private ClipboardManager myClipboard;
    private String TAG = "GikeeApplication";
    public static final String UPDATE_STATUS_ACTION = "com.umeng.message.example.action.UPDATE_STATUS";
    private Handler handler;
    private VersionPresenter mpresenter;
    @Override
    public void onCreate() {
        super.onCreate();
        instances = (GikeeApplication) getApplicationContext();
        MultiLanguageUtil.init(this);
        mpresenter = new com.gikee.app.presenter.version.VersionPresenter(this);
        PreferenceUtil.init(instances);

        //初始化数据库
        setDatabase();

        //记录应用的语言
        //setLanguage();

        //工具类初始化
        MyUtils.init(this);
        //捕获未知异常
        CrashHandler.getInstance().init(this);

        //统一字体格式
        typeface = Typeface.createFromAsset(instances.getAssets(), "fonts/number.ttf");
        typeface_bold = Typeface.createFromAsset(instances.getAssets(), "fonts/number_bold.ttf");
      //  typeface_icons = Typeface.createFromAsset(instances.getAssets(), "fonts/iconfont.ttf");


        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //友盟初始化
                initUMConfigure();

            }
        };

        mHandler.sendEmptyMessage(0);

        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);


    }

    private void initUMConfigure() {

        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(Commons.isDebug);

        UMShareAPI.get(this);

        try {
            Class<?> aClass = Class.forName("com.umeng.commonsdk.UMConfigure");
            Field[] fs = aClass.getDeclaredFields();
            for (Field f:fs){
                Log.e("xxxxxx","ff="+f.getName()+"   "+f.getType().getName());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //友盟社会法分享

        PlatformConfig.setWeixin(Commons.WX_APP_ID, Commons.WX_APP_Secret);
        PlatformConfig.setQQZone(Commons.TC_APP_ID, Commons.TC_APP_key);
        PlatformConfig.setSinaWeibo(Commons.WEIBO_APP_ID, Commons.WEIBO_APP_Secret,Commons.REDIRECT_URL);

        /**
         * 初始化common库
         * 参数1:上下文，必须的参数，不能为空
         * 参数2:友盟 app key，非必须参数，如果Manifest文件中已配置app key，该参数可以传空，则使用Manifest中配置的app key，否则该参数必须传入
         * 参数3:友盟 channel，非必须参数，如果Manifest文件中已配置channel，该参数可以传空，则使用Manifest中配置的channel，否则该参数必须传入，channel命名请详见channel渠道命名规范
         * 参数4:设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机
         * 参数5:Push推送业务的secret，需要集成Push功能时必须传入Push的secret，否则传空
         */
        String channel = WalleChannelReader.getChannel(this.getApplicationContext());
        UMConfigure.init(this, "5b75373af43e48206b000235", channel, UMConfigure.DEVICE_TYPE_PHONE,
                "1c3d934bcf235bb8e16e71eb0cac2cf3");

//        UMConfigure.init(this,UMConfigure.DEVICE_TYPE_PHONE,"");


        initUpush();


    }

    private void initUpush() {



        PushAgent mPushAgent = PushAgent.getInstance(this);
        handler = new Handler(getMainLooper());

        //sdk开启通知声音
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);

        UmengMessageHandler messageHandler = new UmengMessageHandler() {

            /**
             * 通知的回调方法（通知送达时会回调）
             */
            @Override
            public void dealWithNotificationMessage(Context context, UMessage msg) {
                //调用super，会展示通知，不调用super，则不展示通知。
                super.dealWithNotificationMessage(context, msg);
                Log.e("通知==========",msg.custom);
              //  RemindServerInfo(msg.custom);

            }

            /**
             * 自定义消息的回调方法
             */
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {

                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        // 对自定义消息的处理方式，点击或者忽略
                        boolean isClickOrDismissed = true;
                        if (isClickOrDismissed) {
                            //自定义消息的点击统计
                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
                        } else {
                            //自定义消息的忽略统计
                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
                        }
                      //  RemindInfo(msg.custom);
                        Log.e("消息==========",msg.custom);
                    }
                });
            }


        };
        mPushAgent.setMessageHandler(messageHandler);

        /**
         * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {

            @Override
            public void launchApp(Context context, UMessage msg) {
                super.launchApp(context, msg);
            }

            @Override
            public void openUrl(Context context, UMessage msg) {
                super.openUrl(context, msg);
            }

            @Override
            public void openActivity(Context context, UMessage msg) {
                super.openActivity(context, msg);
            }

            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
            }
        };
        //使用自定义的NotificationHandler
        mPushAgent.setNotificationClickHandler(notificationClickHandler);

        //注册推送服务 每次调用register都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                Log.e(TAG, "device token: " + deviceToken);
                mpresenter.sendToken(deviceToken,"Android");
              //  sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e(TAG, "register failed: " + s + " " + s1);
               // sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
            }
        });

        mPushAgent.setAlias( Commons.isDebug?"debug":"release", Commons.isDebug?"debug":"release", new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean isSuccess, String message) {
                if(isSuccess){
                    Log.e(TAG, "setAlias success: " + message);
                }else
                    Log.e(TAG, "setAlias failed: " + message);
            }
        });





        //小米通道
        MiPushRegistar.register(this, Commons.MIAPP_ID, Commons.MIAPP_KEY);
        //华为通道
        HuaWeiRegister.register(this);
        //魅族通道
        MeizuRegister.register(this, Commons.Meizu_APP_ID, Commons.Meizu_APP_KEY);

        try {

            PushManager.getInstance().register(this, Commons.oppo_appId, Commons.oppo_appKey, mPushCallback);//setPushCallback接口也可设置callback
        } catch (Exception e) {
            e.printStackTrace();
        }

        //使用完全自定义处理
        //mPushAgent.setPushIntentServiceClass(UmengNotificationService.class);

    }

    private void RemindServerInfo(String message) {

        if (!TextUtils.isEmpty(message)) {

            Gson gs = new Gson();

            try {

                Intent msgIntent = new Intent(Commons.MESSAGE_RECEIVED_ACTION);

                JSONObject extraJson = new JSONObject(message);
                if (extraJson.length() > 0) {
                    msgIntent.putExtra(Commons.KEY_MESSAGE, message);
                }

                RemindResp remindBean = gs.fromJson(message, RemindResp.class);//把JSON字符串转为对象

                remindBean.getResult().setTime(MyUtils.getRemindTime());

                SQLiteUtils.getInstance().addRemind(remindBean.getResult());

                // msgIntent.putExtra(HomePageActivity.KEY_MESSAGE, message);


                LocalBroadcastManager.getInstance(this).sendBroadcast(msgIntent);

            } catch (JSONException e) {


            }

        }


    }


    /************************************************************************************
     * ***************************callbacks from mcs************************************
     ***********************************************************************************/
    private PushCallback mPushCallback = new PushAdapter() {
        @Override
        public void onRegister(int code, String s) {
            if (code == 0) {
                Log.e("OPPO注册成功", "registerId:" + s);
            } else {
                Log.e("OPPO注册失败", "code=" + code + ",msg=" + s);
            }
        }

    };

    private void RemindInfo(String message) {

        if (!TextUtils.isEmpty(message)) {
            if (Commons.isForeground) {
                //  String message = msg.custom;

                Gson gs = new Gson();

                try {

                    Intent msgIntent = new Intent(Commons.MESSAGE_RECEIVED_ACTION);

                    JSONObject extraJson = new JSONObject(message);
                    if (extraJson.length() > 0) {
                        msgIntent.putExtra(Commons.KEY_MESSAGE, message);
                    }

                    RemindBean remindBean = gs.fromJson(message, RemindBean.class);//把JSON字符串转为对象

                    remindBean.setTime(MyUtils.getRemindTime());

                    SQLiteUtils.getInstance().addRemind(remindBean);

                    // msgIntent.putExtra(HomePageActivity.KEY_MESSAGE, message);


                    LocalBroadcastManager.getInstance(this).sendBroadcast(msgIntent);

                } catch (JSONException e) {


                }
            }
        }


    }


    /**
     * 设置语言
     *
     * @param lauType
     */
    private void set(String lauType) {
        // 本地语言设置
        if("en".equals(lauType)){
            PreferenceUtil.commitString("yuyan","English");
            Commons.type = Locale.ENGLISH;
        }else if("zh_CN".equals(lauType)){
            PreferenceUtil.commitString("yuyan","简体中文");
            Commons.type = Locale.SIMPLIFIED_CHINESE;
        }
    }



    private void setDatabase() {

        myOpenHelper = new MyOpenHelper(this, "notes-db", null);
        db = myOpenHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();

    }


    public DaoSession getDaoSession() {
        return mDaoSession;
    }



    public static GikeeApplication getMyApplicationContext() {
        return instances;
    }

    public Typeface getTypeface() {
        return typeface;
    }


    public Typeface getTypeface_bold() {
        return typeface_bold;
    }


    public ClipboardManager getMyClipboard() {
        return myClipboard;
    }

    public void setMyClipboard(ClipboardManager myClipboard) {
        this.myClipboard = myClipboard;
    }

     @Override
    public void onVersionInfo(com.gikee.app.resp.VersionResp versionResp) {

    }

    @Override
    public void onExchangeRate(RateResp resp) {

    }

    @Override
    public void onBTCRate(RateBeanResp resp) {

    }
}
