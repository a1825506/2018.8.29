package com.gikee.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.greendao.LoginBean;
import com.gikee.app.greendao.SQLiteUtils;
import com.gikee.app.language.LanguageType;
import com.gikee.app.preference_config.PreferenceUtil;

import butterknife.Bind;

public class SettingActivity extends BaseActivity{

    @Bind(R.id.jump)
    TextView jump;

    @Bind(R.id.setting)
    TextView setting;

    @Bind(R.id.cny_img)
    ImageView cny_img;

    @Bind(R.id.cny_choose)
    ImageView cny_choose;

    @Bind(R.id.usd_img)
    ImageView usd_img;

    @Bind(R.id.usd_choose)
    ImageView usd_choose;

    @Bind(R.id.quate_cny_img)
    ImageView quate_cny_img;

    @Bind(R.id.quate_cny_choose)
    ImageView quate_cny_choose;

    @Bind(R.id.quate_usd_img)
    ImageView quate_usd_img;

    @Bind(R.id.quate_usd_choose)
    ImageView quate_usd_choose;

    private int lanuage_type=4;

    private boolean quate_type=true;

    private boolean isSetting=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

    }

    @Override
    protected void onChangeEvent(int type) {

    }


    @Override
    protected void setUpView() {
        hideTitleBar();

        PreferenceUtil.commitInt(LanguageType.SAVE_UNIT,lanuage_type);

        PreferenceUtil.commitBoolean(LanguageType.SAVE_QUATE, quate_type);


    }

    @Override
    protected void initOnclick() {
        cny_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cny_choose.setVisibility(View.VISIBLE);
                usd_choose.setVisibility(View.GONE);
                lanuage_type = 4;
                isSetting=true;

            }
        });
        usd_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cny_choose.setVisibility(View.GONE);
                usd_choose.setVisibility(View.VISIBLE);
                lanuage_type = 5;
                isSetting=true;
            }
        });

        quate_cny_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quate_cny_choose.setVisibility(View.VISIBLE);
                quate_usd_choose.setVisibility(View.GONE);
                quate_type=true;
            }
        });

        quate_usd_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quate_cny_choose.setVisibility(View.GONE);
                quate_usd_choose.setVisibility(View.VISIBLE);
                quate_type=false;
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceUtil.commitInt(LanguageType.SAVE_UNIT,lanuage_type);
                PreferenceUtil.commitBoolean(LanguageType.SAVE_QUATE, quate_type);
                toHomePage();
            }
        });
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                toHomePage();
            }
        });
    }

    private void toHomePage() {
        LoginBean loginBean = new LoginBean();
        loginBean.setFristRun(false);
        loginBean.setLoginflax(MyUtils.getVersionCode(SettingActivity.this));//将版本好存入数据库
        SQLiteUtils.getInstance().addLogin(loginBean);
        Intent intent = new Intent(SettingActivity.this, HomePageActivity.class);
        startActivity(intent);
        finish();
    }
}
