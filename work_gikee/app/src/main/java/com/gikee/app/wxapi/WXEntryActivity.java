package com.gikee.app.wxapi;
import android.content.Intent;
import android.os.Bundle;
import com.umeng.socialize.weixin.view.WXCallbackActivity;


public class WXEntryActivity extends WXCallbackActivity {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }






}