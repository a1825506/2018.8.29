package com.gikee.app.receiver;

import android.content.Context;
import android.util.Log;

import com.gikee.app.presenter.version.VersionPresenter;
import com.gikee.app.presenter.version.VersionView;
import com.gikee.app.resp.RateBeanResp;
import com.gikee.app.resp.RateResp;
import com.gikee.app.resp.VersionResp;
import com.umeng.message.meizu.UmengMeizuPushReceiver;

public class MeizuTestReceiver extends UmengMeizuPushReceiver implements VersionView {
    private VersionPresenter mpresenter;

    @Override
    public void onRegister(Context context, String pushid) {
        super.onRegister(context, pushid);
        mpresenter = new VersionPresenter(this);

        Log.e("MeizuReceiver", "接收到:  "+pushid);

        mpresenter.sendToken(pushid,"MEIZU");
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

    //通知点击回调
    @Override
    public void onNotificationClicked(Context context, String s, String s1, String s2) {
        super.onNotificationClicked(context, s, s1, s2);
    }
}
