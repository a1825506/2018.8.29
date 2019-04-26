package com.gikee.app.receiver;

import android.content.Context;
import android.util.Log;

import com.gikee.app.presenter.version.VersionPresenter;
import com.gikee.app.presenter.version.VersionView;
import com.gikee.app.resp.RateBeanResp;
import com.gikee.app.resp.RateResp;
import com.gikee.app.resp.VersionResp;
import com.huawei.android.pushagent.PushReceiver;

public class HUAWEIPushRevicer extends PushReceiver implements VersionView {

    private VersionPresenter mpresenter;


    @Override
    public void onToken(Context context, String token) {

        mpresenter = new VersionPresenter(this);

        Log.e("HuaWeiReceiver", "接收到:  "+token);

        mpresenter.sendToken(token,"HUAWEI");

    }

    @Override
    public void onPushMsg(Context context, byte[] bytes, String s) {

        Log.e("HuaWeiReceiver",s);

    }

    @Override
    public void onVersionInfo(VersionResp versionResp) {


    }

    @Override
    public void onExchangeRate(RateResp resp) {

    }

    @Override
    public void onBTCRate(RateBeanResp resp) {

    }
}
