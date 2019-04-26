package com.gikee.app.views.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.datas.Commons;
import com.gikee.app.language.LanguageType;
import com.gikee.app.language.MultiLanguageUtil;
import com.gikee.app.language.OnChangeLanguageEvent;
import com.gikee.app.preference_config.PreferenceUtil;

import org.greenrobot.eventbus.EventBus;

import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

/**
 * Created by THINKPAD on 2018/3/16.
 */

public class ChoseUnitDialog extends Dialog {

    private Activity context;
    public static int  type_unit=0;
    public static int  type_lanuage=1;
    public static int  type_project=2;
    public static int  type_address=3;
    public static int  type_address_exchange=4;
    private int type ;
    private Button btn_cancle, btn_cny, btn_usd,btn_follow;
    private int selectedLanguage = 0;

    public ChoseUnitDialog(Activity context, int tyoe) {
        super(context);
        this.context = context;
        this.type = tyoe;
        initView();
        initDate();
        initListener();
    }

    /**
     * 该方法提供于操作布局控件
     */
    public void initView() {
        Window window = getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_chose_unit);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        //这里是设置dialog弹出、隐藏的动画效果
        //params.windowAnimations = R.style.MyDialogAnimation;
        btn_cancle = findViewById(R.id.dialog_chose_unit_cancel);
        btn_cny = findViewById(R.id.dialog_chose_unit_cny);
        btn_usd = findViewById(R.id.dialog_chose_unit_usd);
        btn_follow = findViewById(R.id.dialog_chose_unit_system);
        if(type==type_unit){
            btn_follow.setVisibility(View.GONE);
           btn_usd.setText(context.getString(R.string.dg_chose_unit_china));
           btn_cny.setText(context.getString(R.string.dg_chose_unit_america));
        }else if(type==type_project){
            btn_follow.setVisibility(View.GONE);
            btn_usd.setText(context.getString(R.string.mp_edit_top));
            btn_cny.setText(context.getString(R.string.delete));
        }else if(type==type_address||type_address_exchange==type){
            btn_follow.setVisibility(View.VISIBLE);
            btn_usd.setVisibility(View.GONE);
            btn_follow.setText(context.getString(R.string.mp_edit_top));
            btn_usd.setText(context.getString(R.string.edit));
            btn_cny.setText(context.getString(R.string.delete));
        }
    }

    /**
     * 该方法提供于操作数据
     */
    public void initDate() {
    }

    /**
     * 该方法提供于设置监听事件
     */
    public void initListener() {

        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

//
        btn_cny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type==type_lanuage){
                    selectedLanguage= LanguageType.LANGUAGE_EN;
                }
                //0置顶操作 1删除操作 type=type_delete时生效
                setLanguage(0);
                dismiss();
            }
        });
        btn_usd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type==type_lanuage){
                    selectedLanguage= LanguageType.LANGUAGE_CHINESE_SIMPLIFIED;
                }
                setLanguage(1);
                dismiss();
            }
        });

        btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //0置顶操作 1删除操作 type=type_delete时生效
                setLanguage(2);
                dismiss();
            }
        });
    }



    /**设置语言或价格单位*/
    private void setLanguage(int ope_type) {
        if(type==type_lanuage){
            MultiLanguageUtil.getInstance().updateLanguage(selectedLanguage);
        }else if(type==type_unit){
            boolean unit_type =  MyUtils.getUnit();
            if(!unit_type){
                PreferenceUtil.commitInt(LanguageType.SAVE_UNIT,5);
                MyUtils.setUnit();
                EventBus.getDefault().post(new OnChangeLanguageEvent(LanguageType.UNIT_USD));
            }else{
                PreferenceUtil.commitInt(LanguageType.SAVE_UNIT,4);
                MyUtils.setUnit();
                EventBus.getDefault().post(new OnChangeLanguageEvent(LanguageType.UNIT_CNY));
            }
        }else if(type==type_project){
            if(ope_type==0){
                EventBus.getDefault().post(new OnChangeLanguageEvent(LanguageType.PROJECT_DELETE));
            }else
                EventBus.getDefault().post(new OnChangeLanguageEvent(LanguageType.PROJECT_TOP));


        }else if(type==type_address){
            if(ope_type==0){
                EventBus.getDefault().post(new OnChangeLanguageEvent(LanguageType.ADDRESS_DELETE));
            }else if(ope_type==1){
                EventBus.getDefault().post(new OnChangeLanguageEvent(LanguageType.ADDRESS_EDIT));
            }else if(ope_type==2){
                EventBus.getDefault().post(new OnChangeLanguageEvent(LanguageType.ADDRESS_TOP));
            }


        }else if(type==type_address_exchange){
            if(ope_type==0){
                EventBus.getDefault().post(new OnChangeLanguageEvent(LanguageType.ADDRESS_TOP_EXCHANGE));
            }else if(ope_type==1){
                EventBus.getDefault().post(new OnChangeLanguageEvent(LanguageType.ADDRESS_EDIT_EXCHANGE));
            }else if(ope_type==2){
                EventBus.getDefault().post(new OnChangeLanguageEvent(LanguageType.ADDRESS_TOP_EXCHANGE));
            }


        }

    }
}
