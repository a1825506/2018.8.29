package com.gikee.app.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.gikee.app.Http.Api;
import com.gikee.app.R;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.beans.AddressJSBean;
import com.gikee.app.preference_config.PreferenceUtil;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;

import pl.droidsonroids.gif.GifImageView;

public class SpecialTradeDetailActivity extends BaseActivity{

    private final static int FILECHOOSER_RESULTCODE = 0x100;
    private BridgeWebView webview;
    private GifImageView loadingProgressBar;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private String txHash;
    private AddressJSBean addressJSBean;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mineaddress);
    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void setUpView() {
        hideTitleBar();
        txHash = getIntent().getStringExtra("txHash");
        loadingProgressBar = (GifImageView) findViewById(R.id.loading);
        initWebView();
        initJSWebView();
        webview.setDownloadListener(new MyWebViewDownLoadListener());
        webview.loadUrl(Api.loadUrl+"?"+txHash);

    }

    @Override
    protected void initOnclick() {

    }


    private void initJSWebView() {
        webview.setDefaultHandler(new JsBridgeHandler());

        webview.setWebChromeClient(new WebChromeClient() {
            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {

                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
            }


            //For Android 4.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {

                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
            }


            // For Android 3.0+
            public void openFileChooser(ValueCallback uploadMsg, String acceptType) {

                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(
                        Intent.createChooser(i, "File Browser"),
                        FILECHOOSER_RESULTCODE);
            }

            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mUploadCallbackAboveL = filePathCallback;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(
                        Intent.createChooser(i, "File Browser"),
                        FILECHOOSER_RESULTCODE);
                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if (newProgress == 100) {
                    loadingProgressBar.setVisibility(View.GONE);
                } else {
                    if (loadingProgressBar.getVisibility() == View.GONE)
                        loadingProgressBar.setVisibility(View.VISIBLE);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }
        });



        webview.registerHandler("closeWebview", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("closeWebview","closeWebview");
                finish();
            }

        });

        addressJSBean =new AddressJSBean();
        addressJSBean.setLanguage(PreferenceUtil.getString("yuyan","简体中文"));

        //顶部信息
        webview.registerHandler("getHeadInfo", new BridgeHandler() {

            Gson gson = new Gson();


            @Override
            public void handler(String data, CallBackFunction function) {

                Log.e("getHeadInfo",gson.toJson(addressJSBean));


                function.onCallBack(gson.toJson(addressJSBean));

            }

        });


    }


    /**
     * 初始化
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressWarnings("deprecation")
    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    private void initWebView() {

        webview = (BridgeWebView) findViewById(R.id.webview);
        webview.setVerticalScrollBarEnabled(false);
        webview.setWebContentsDebuggingEnabled(true);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(false);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSaveFormData(false);
        settings.setSavePassword(false);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        String databasePath = getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        settings.setDatabasePath(databasePath);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        //清空所有Cookie
        CookieSyncManager.createInstance(SpecialTradeDetailActivity.this);  //Create a singleton CookieSyncManager within a context
        CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
        cookieManager.removeAllCookie();// Removes all cookies.
        CookieSyncManager.getInstance().sync(); // forces sync manager to sync now

        webview.setWebChromeClient(null);
        webview.setWebViewClient(null);
        webview.getSettings().setJavaScriptEnabled(false);
        webview.clearCache(true);


        webview.removeAllViews();
        webview.destroy();
    }



    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }


    private class JsBridgeHandler implements BridgeHandler {
        @Override
        public void handler(String data, CallBackFunction function) {

        }



    }



}
