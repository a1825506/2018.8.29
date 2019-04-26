package com.gikee.app.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.greendao.LoginBean;
import com.gikee.app.greendao.SQLiteUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private   int TIME=1000;
    private  final int GO_MAIN=100;
    private  final int GO_GUIDE=101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        init();

    }

    private void init() {

       List<LoginBean> loginBeanList = SQLiteUtils.getInstance().getLoginStatus();

       if(loginBeanList!=null){

           if(loginBeanList.size()!=0){
               // MyUtils.getVersionCode(GuideActivity.this)

               if(loginBeanList.get(0).getLoginflax()==MyUtils.getVersionCode(GuideActivity.this)){
                   mhandler.sendEmptyMessageDelayed(GO_MAIN,TIME);//将欢迎页停留5秒，并且将message设置为跳转到
               }else
                   mhandler.sendEmptyMessageDelayed(GO_GUIDE,TIME);//将欢迎页停留5秒，并且将message设置为跳转到

           }else
               mhandler.sendEmptyMessageDelayed(GO_GUIDE,TIME);//将欢迎页停留5秒，并且将message设置为跳转到

       }else
           mhandler.sendEmptyMessageDelayed(GO_GUIDE,TIME);



    }


    Handler mhandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GO_MAIN:
                    goMain();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
            }

        }
    };


    private void goMain() {
        Intent intent=new Intent(GuideActivity.this,HomePageActivity.class);
        startActivity(intent);
        finish();

    }

    private void goGuide() {
        Intent intent=new Intent(GuideActivity.this,WelcomeActivity.class);
        startActivity(intent);
        finish();
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
