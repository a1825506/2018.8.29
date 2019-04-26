package com.gikee.app.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gikee.app.R;
import com.gikee.app.Utils.GlideCircleTransformWithBorder;
import com.gikee.app.Utils.MyUtils;

import com.gikee.app.Utils.ToastUtils;

import com.gikee.app.activity.MineContactActivity;
import com.gikee.app.activity.VersionUpdateActivity;


import com.gikee.app.datas.Commons;
import com.gikee.app.language.LanguageType;
import com.gikee.app.language.MultiLanguageUtil;
import com.gikee.app.language.OnChangeLanguageEvent;

import com.gikee.app.preference_config.PreferenceUtil;
import com.gikee.app.share.ShowShareDialog;
import com.gikee.app.views.dialogs.ChoseUnitDialog;
import com.gikee.app.views.dialogs.ExitDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;



/**
 * Created by THINKPAD on 2018/3/14.
 */

@SuppressLint("ValidFragment")
public class MineFragment extends BaseFragment  {

    private boolean isCheck = true;
    private View view;
    private Context context;
    private ImageView img_head,img_js,img_quate;
    private TextView tx_nikename, tx_unit, tx_edition, tx_language;
    public static final String SETTINGS_ACTION =
            "android.settings.APPLICATION_DETAILS_SETTINGS";
    private int savedLanguageType=0;

    public MineFragment() {

    }

    public MineFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = LayoutInflater.from(context).inflate(R.layout.fm_mine, container,false);

        EventBus.getDefault().register(this);
        img_js = view.findViewById(R.id.mine_warning_receive_check);

        img_quate = view.findViewById(R.id.mine_quate_receive_check);
        tx_edition = view.findViewById(R.id.mine_edition_resource);
        tx_language = view.findViewById(R.id.mine_language_resource);
        tx_nikename = view.findViewById(R.id.mine_nickname);
        tx_unit = view.findViewById(R.id.mine_unit_resource);
        img_head = view.findViewById(R.id.mine_headimg);
        tx_edition.setText(MyUtils.getLocalVersionName());

       String language = MyUtils.getLocalLanguage();
        if ("en".equals(language)) {
            tx_language.setText(context.getResources().getString(R.string.dg_chose_language_english));
//            PreferenceUtil.commitBoolean(LanguageType.SAVE_QUATE, false);
//            img_quate.setImageResource(R.mipmap.check_box_gray);
//            tx_unit.setText(context.getResources().getString(R.string.dg_chose_unit_america));
        }else{
            tx_language.setText(context.getResources().getString(R.string.dg_chose_language_chana));
//            PreferenceUtil.commitBoolean(LanguageType.SAVE_QUATE, true);
//            img_quate.setImageResource(R.mipmap.check_box_green);
//            tx_unit.setText(context.getResources().getString(R.string.dg_chose_unit_china));
        }

        if(MyUtils.getUnit()){

            tx_unit.setText(context.getResources().getString(R.string.dg_chose_unit_america));

        }else{

            tx_unit.setText(context.getResources().getString(R.string.dg_chose_unit_china));

        }

        boolean ischeck = PreferenceUtil.getBoolean(LanguageType.SAVE_QUATE,false);

        if(ischeck){
            img_quate.setImageResource(R.mipmap.check_box_green);
        }else{
            img_quate.setImageResource(R.mipmap.check_box_gray);
        }


        initOnclick();
       // getData();
        return view;
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void setupViews(LayoutInflater inflater) {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onResume() {
        super.onResume();
        NotificationManagerCompat notification = NotificationManagerCompat.from(getContext());
        boolean  isEnabled = notification.areNotificationsEnabled();
        if(isEnabled){
            img_js.setImageResource(R.mipmap.check_box_green);
        }else
            img_js.setImageResource(R.mipmap.check_box_gray);
    }

    @Override
    protected void lazyLoad() {



    }

    @Override
    protected void onChangeEvent(int type) {
//        onResume();
    }


    private void initOnclick() {

        NotificationManagerCompat notification = NotificationManagerCompat.from(getContext());
        final boolean  isEnabled = notification.areNotificationsEnabled();

        view.findViewById(R.id.mine_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new ExitDialog((Activity) context)).show();
            }
        });

        tx_nikename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.show(getString(R.string.wait_login));
//                if (!MyUtils.isLogin())
//                    startActivity(new Intent(context, LoginActivity.class));
           }
        });


        img_js.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                OpenNotification();
                img_js.setImageResource(R.mipmap.check_box_green);


            }
        });

        img_quate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean ischeck = PreferenceUtil.getBoolean(LanguageType.SAVE_QUATE,false);

                if(ischeck){
                    PreferenceUtil.commitBoolean(LanguageType.SAVE_QUATE, false);
                    img_quate.setImageResource(R.mipmap.check_box_gray);
                }else{
                    PreferenceUtil.commitBoolean(LanguageType.SAVE_QUATE, true);
                    img_quate.setImageResource(R.mipmap.check_box_green);
                }

                MyUtils.setQuateSymbol();
                EventBus.getDefault().post(new OnChangeLanguageEvent(LanguageType.UNIT_QUATE_INT));

            }
        });

        view.findViewById(R.id.mine_unit_resource).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new ChoseUnitDialog((Activity) context, ChoseUnitDialog.type_unit)).show();
            }
        });
        view.findViewById(R.id.mine_unit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new ChoseUnitDialog((Activity) context, ChoseUnitDialog.type_unit)).show();
            }
        });
        view.findViewById(R.id.mine_language_resource).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new ChoseUnitDialog((Activity) context, ChoseUnitDialog.type_lanuage)).show();
            }
        });
        view.findViewById(R.id.mine_language).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new ChoseUnitDialog((Activity) context, ChoseUnitDialog.type_lanuage)).show();
            }
        });
        view.findViewById(R.id.mine_telephone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, MineContactActivity.class));
            }
        });
        view.findViewById(R.id.mine_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(context, MineShareActivity.class));
                ShowShareDialog showShareDialog = new ShowShareDialog();
                showShareDialog.setShareType(ShowShareDialog.share_url);
                showShareDialog.show(getFragmentManager(),getActivity());
            }
        });

        view.findViewById(R.id.mine_edition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, VersionUpdateActivity.class));
            }
        });
    }

    private void OpenNotification() {

        Intent intent = new Intent();

        if(Build.VERSION.SDK_INT >=26){
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
        }
//android 5.0-7.0
        if(Build.VERSION.SDK_INT >=21 && Build.VERSION.SDK_INT <26) {
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
        }
//其他
        if(Build.VERSION.SDK_INT <21){
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));}

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    public void getData() {

        if (MyUtils.isLogin()) {
            view.findViewById(R.id.mine_more).setVisibility(View.VISIBLE);
            Glide.with(context).load(R.mipmap.icon_head_full).apply(new RequestOptions().error(context.getResources().getDrawable(R.mipmap.icon_head_full))
                    .placeholder(R.mipmap.icon_head_full).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new GlideCircleTransformWithBorder(context, 5, Color.parseColor("#00ffffff")))).into(img_head);
            tx_nikename.setText(MyUtils.getUserName());
        } else {
            view.findViewById(R.id.mine_more).setVisibility(View.GONE);
            tx_nikename.setText(context.getResources().getString(R.string.mine_login));
            Glide.with(context).load(R.mipmap.icon_head).apply(new RequestOptions().error(context.getResources().getDrawable(R.mipmap.icon_head))
                    .placeholder(R.mipmap.icon_head).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new GlideCircleTransformWithBorder(context, 5, Color.parseColor("#00ffffff")))).into(img_head);
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeLanguageEvent(OnChangeLanguageEvent event){
        if (event.languageType==LanguageType.LANGUAGE_EN){
            tx_language.setText(context.getResources().getString(R.string.dg_chose_language_english));
        }else if(event.languageType==LanguageType.LANGUAGE_CHINESE_SIMPLIFIED){
            tx_language.setText(context.getResources().getString(R.string.dg_chose_language_chana));
        }else if (event.languageType==LanguageType.LANGUAGE_FOLLOW_SYSTEM){
            tx_language.setText(context.getResources().getString(R.string.follow_system));
        }else if(event.languageType==LanguageType.UNIT_USD){
            tx_unit.setText(context.getResources().getString(R.string.dg_chose_unit_america));
        }else if(event.languageType==LanguageType.UNIT_CNY){
            tx_unit.setText(context.getResources().getString(R.string.dg_chose_unit_china));
        }

    }



}
