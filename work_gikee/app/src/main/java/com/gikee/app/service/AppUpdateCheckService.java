package com.gikee.app.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.datas.Commons;
import com.gikee.app.language.MultiLanguageUtil;
import com.gikee.app.presenter.version.VersionPresenter;
import com.gikee.app.presenter.version.VersionView;
import com.gikee.app.resp.RateBeanResp;
import com.gikee.app.resp.RateResp;
import com.gikee.app.resp.VersionResp;
import com.gikee.app.views.dialogs.VersionUpdataDialog;

import java.util.Locale;

public class AppUpdateCheckService extends Service implements VersionView{

    private boolean isBackground = true;
    private VersionPresenter mPrsenter;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null){

            mPrsenter = new VersionPresenter(this);

            isBackground = intent.getBooleanExtra(Commons.EXTRA_BOOLEAN,true);

            checkAppUpdate();

        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void checkAppUpdate() {

        String language = "";

        Locale savedLanguageType = MultiLanguageUtil.getInstance().getLanguageLocale();
        if (savedLanguageType == Locale.ENGLISH) {
            language = "en";
        } else if (savedLanguageType == Locale.SIMPLIFIED_CHINESE) {
            language = "ch_zn";
        } else {
            language = "en";
        }

        mPrsenter.checkAppUpdate(MyUtils.getAppVersionName(AppUpdateCheckService.this),language);
    }

    @Override
    public void onVersionInfo(VersionResp versionResp) {

        //1.0.01.10001

       if(versionResp.getResult().getNewVersion()!=null){

            if(versionResp.getResult().getNewVersion().compareTo(MyUtils.getAppVersionName(AppUpdateCheckService.this))>0){

                new VersionUpdataDialog(AppUpdateCheckService.this,R.style.dialog,versionResp.getResult()).setTitle().show();


            }

        }


        stopSelf();

    }

    @Override
    public void onExchangeRate(RateResp resp) {

    }

    @Override
    public void onBTCRate(RateBeanResp resp) {

    }
}
