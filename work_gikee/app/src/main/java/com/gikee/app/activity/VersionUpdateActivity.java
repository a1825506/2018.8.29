package com.gikee.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.datas.Commons;
import com.gikee.app.language.MultiLanguageUtil;
import com.gikee.app.presenter.version.VersionPresenter;
import com.gikee.app.presenter.version.VersionView;
import com.gikee.app.resp.RateBeanResp;
import com.gikee.app.resp.RateResp;
import com.gikee.app.resp.VersionResp;
import com.gikee.app.views.dialogs.VersionUpdataDialog;

import java.util.Locale;

import butterknife.Bind;

public class VersionUpdateActivity extends BaseActivity implements VersionView {
    @Bind(R.id.add_title)
    TextView checkout_update;
    @Bind(R.id.version_info)
    TextView version_info;
    private VersionPresenter mPrsenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showRightCollect();
        setContentView(R.layout.activity_version_update);
        mPrsenter = new VersionPresenter(this);
        version_info.setText(Commons.version_info);
    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @Override
    protected void setUpView() {

    }

    private void checkAppUpdate() {

        String language = MyUtils.getLocalLanguage();

        mPrsenter.checkAppUpdate(MyUtils.getAppVersionName(VersionUpdateActivity.this),language);
    }

    @Override
    protected void initOnclick() {
        checkout_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.show(getString(R.string.checking));
                checkAppUpdate();
            }
        });
    }

    @Override
    public void onVersionInfo(VersionResp versionResp) {

        if(versionResp.getResult()!=null){

            if(versionResp.getResult().getNewVersion()!=null){


                if(versionResp.getResult().getNewVersion().compareTo(MyUtils.getAppVersionName(VersionUpdateActivity.this))>0){

                    new VersionUpdataDialog(VersionUpdateActivity.this,R.style.dialog,versionResp.getResult()).setTitle().show();

                }

            }else
                ToastUtils.show(getString(R.string.newversion));

        }else
            ToastUtils.show(getString(R.string.newversion));



    }

    @Override
    public void onExchangeRate(RateResp resp) {

    }

    @Override
    public void onBTCRate(RateBeanResp resp) {

    }
}
