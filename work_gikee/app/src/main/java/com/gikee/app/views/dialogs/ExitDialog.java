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
import com.gikee.app.events.MineChoseUnitEvent;
import com.gikee.app.preference_config.PreferenceUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by THINKPAD on 2018/3/16.
 */

public class ExitDialog extends Dialog {

    private Activity context;
    private Button btn_cancle, btn_sure;

    public ExitDialog(Activity context) {
        super(context);
        this.context = context;
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
        setContentView(R.layout.dialog_exit);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        //这里是设置dialog弹出、隐藏的动画效果
        //params.windowAnimations = R.style.MyDialogAnimation;
        btn_cancle = findViewById(R.id.dialog_exit_cancel);
        btn_sure = findViewById(R.id.dialog_exit_exit);

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
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // PreferenceUtil.removeAll();
                PreferenceUtil.commitString( "uuid", "");
                EventBus.getDefault().postSticky(new MineChoseUnitEvent("exit",""));
                dismiss();
            }
        });
    }
}
