package com.gikee.app.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gikee.app.R;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.base.BaseActivity;

/**
 * Created by THINKPAD on 2018/6/29.
 */

public class MineContactActivity extends BaseActivity {

    private ImageView img_head;
    private TextView tx_wechat, tx_weibo, tx_email, tx_facebook,dianbao,twitter,contact_facebook;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mine_contactus);
    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @Override
    protected void setUpView() {
        showTitleBar();
        setTitleColor(R.color.black);
        hideRightCollect(View.GONE);
        //findViewById(R.id.toolbar_layout).setBackgroundColor(Color.parseColor("#39384c"));
        findViewById(R.id.toolbar_right).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.toolbar_title)).setText(getResources().getString(R.string.mine_telephone));
        img_head = findViewById(R.id.contact_headimg);
        tx_email = findViewById(R.id.contact_email);
        tx_facebook = findViewById(R.id.contact_email);
        tx_wechat = findViewById(R.id.contact_wechat);
        tx_weibo = findViewById(R.id.contact_weibo);
        dianbao=findViewById(R.id.dianbao);
        twitter=findViewById(R.id.twitter);
        contact_facebook=findViewById(R.id.contact_facebook);
    }



    @Override
    protected void initOnclick() {
        findViewById(R.id.toolbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tx_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData myClip;
                myClip = ClipData.newPlainText("text", tx_wechat.getText().toString());//text是内容
                cm.setPrimaryClip(myClip);
                ToastUtils.show("已复制到剪切板");
            }
        });
        tx_weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData myClip;
                myClip = ClipData.newPlainText("text", tx_weibo.getText().toString());//text是内容
                cm.setPrimaryClip(myClip);
                ToastUtils.show("已复制到剪切板");
            }
        });
        tx_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData myClip;
                myClip = ClipData.newPlainText("text", tx_email.getText().toString());//text是内容
                cm.setPrimaryClip(myClip);
                ToastUtils.show("已复制到剪切板");
            }
        });
        tx_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData myClip;
                myClip = ClipData.newPlainText("text", tx_facebook.getText().toString());//text是内容
                cm.setPrimaryClip(myClip);
                ToastUtils.show("已复制到剪切板");
            }
        });

        dianbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData myClip;
                myClip = ClipData.newPlainText("text", dianbao.getText().toString());//text是内容
                cm.setPrimaryClip(myClip);
                ToastUtils.show("已复制到剪切板");
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData myClip;
                myClip = ClipData.newPlainText("text", twitter.getText().toString());//text是内容
                cm.setPrimaryClip(myClip);
                ToastUtils.show("已复制到剪切板");
            }
        });

        contact_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData myClip;
                myClip = ClipData.newPlainText("text", contact_facebook.getText().toString());//text是内容
                cm.setPrimaryClip(myClip);
                ToastUtils.show("已复制到剪切板");
            }
        });

    }
}
