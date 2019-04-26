package com.gikee.app.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.gikee.app.Http.Api;
import com.gikee.app.Observer.CollectObserverService;
import com.gikee.app.Observer.base_observe.ConstObserver;
import com.gikee.app.R;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.beans.AddressJSBean;
import com.gikee.app.beans.ErrInfoType;
import com.gikee.app.greendao.CollectBean;
import com.gikee.app.greendao.SQLiteUtils;
import com.gikee.app.preference_config.PreferenceUtil;
import com.gikee.app.resp.StatusBean;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class AddressDetailWebviewActivity extends BaseActivity {



    private final static int FILECHOOSER_RESULTCODE = 0x100;
    private BridgeWebView webview;
   // private ProgressBar loadingProgressBar;
    private GifImageView loading;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private AddressJSBean addressJSBean;
    private ErrInfoType errInfoType;
    private String address;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mineaddress);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onChangeEvent(int type) {

    }


    @Override
    protected void initOnclick() {

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void setUpView() {
        hideTitleBar();
        address=getIntent().getStringExtra("address");
       // loadingProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        loading = (GifImageView)findViewById(R.id.loading);
        initWebView();
        initJSWebView();
        webview.setDownloadListener(new MyWebViewDownLoadListener());
        webview.loadUrl(Api.addressDetail+"?address="+address);
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
                    loading.setVisibility(View.GONE);
                    loading.clearAnimation();
                } else {
                    loading.setVisibility(View.VISIBLE);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }
        });

        addressJSBean =new AddressJSBean();
        addressJSBean.setAddress(address);
        addressJSBean.setStartDate("");
        addressJSBean.setEndDate("");
        addressJSBean.setToken("");
        addressJSBean.setCollectLen(SQLiteUtils.getInstance().selectAllContacts("address").size()+"");
        addressJSBean.setCollectName("");
        addressJSBean.setLanguage(PreferenceUtil.getString("yuyan","简体中文"));

        if(SQLiteUtils.getInstance().isAddressCollect(address)) {
            addressJSBean.setCollectFlag(true);
        }else
            addressJSBean.setCollectFlag(false);



        webview.registerHandler("closeWebview", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                finish();
            }

        });


        webview.registerHandler("getHeadInfo", new BridgeHandler() {

            Gson gson = new Gson();


            @Override
            public void handler(String data, CallBackFunction function) {

                Log.e("getHeanInfo",gson.toJson(addressJSBean));


                function.onCallBack(gson.toJson(addressJSBean));

            }

        });

        //收藏信息
        webview.registerHandler("followAction", new BridgeHandler() {

            Gson gson = new Gson();

            @Override
            public void handler(String json, CallBackFunction function) {

                Log.e("followAction",json);

                AddressJSBean person = gson.fromJson(json, AddressJSBean.class);//对于javabean直接给出class实例

                if(!TextUtils.isEmpty(person.getAddress())){

                    StatusBean statusBean = new StatusBean();

                    if (!SQLiteUtils.getInstance().isAddressCollect(person.getAddress())) {
                        CollectBean collectBean = new CollectBean();
                        collectBean.setName(person.getCollectName());
                        collectBean.setAddressid(person.getAddress());
                        collectBean.setType("address");
                        collectBean.setTag(person.getCollectName());
                        SQLiteUtils.getInstance().addContacts(collectBean);
                        statusBean.setStatus(true);
                        CollectObserverService.getInstance().notifyDataChanged(ConstObserver.COLLECT_ADDRESS_CHANGE);
                        ToastUtils.show(getString(R.string.collect_success));
                        function.onCallBack( gson.toJson(statusBean));
                    } else {

                        List<CollectBean> collectBeanList=SQLiteUtils.getInstance().getAddressID(person.getAddress());

                        CollectBean collectBean = new CollectBean();
                        collectBean.setName(person.getCollectName());
                        collectBean.setAddressid(person.getAddress());
                        collectBean.setId(collectBeanList.get(0).getId());
                        collectBean.setType("address");
                        collectBean.setTag(person.getCollectName());
                        SQLiteUtils.getInstance().deleteContacts(collectBean);
                        CollectObserverService.getInstance().notifyDataChanged(ConstObserver.COLLECT_ADDRESS_CHANGE);
                        statusBean.setStatus(false);
                        ToastUtils.show(getString(R.string.collect_cannel));
                        function.onCallBack( gson.toJson(statusBean));
                    }

                }

            }

        });


        webview.registerHandler("getTradeDetail", new BridgeHandler() {

            Gson gson = new Gson();


            @Override
            public void handler(String data, CallBackFunction function) {

                Log.e("getTradeDetail",data);

                Intent intent = new Intent(AddressDetailWebviewActivity.this,MyAddressWebviewActivity.class);


                AddressJSBean addressJSBean =  gson.fromJson(data, AddressJSBean.class);

                intent.putExtra("address",addressJSBean.getAddress());

                intent.putExtra("startDate",addressJSBean.getStartDate());

                intent.putExtra("endDate",addressJSBean.getEndDate());




                startActivityForResult(intent,FILECHOOSER_RESULTCODE);

                startActivity(intent);

               // finish();

                //页面跳转


                //function.onCallBack(gson.toJson(addressJSBean));

            }

        });


    }


    @Override
    public void finish() {
        super.finish();
    }

    /**
     * 设置加载标志和刷新按键的状态
     *
     * @param bLoading
     */
    private void setLoadingIndictorState(boolean bLoading) {
        //loadingProgressBar.setVisibility(bLoading ? View.VISIBLE : View.GONE);
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
        CookieSyncManager.createInstance(AddressDetailWebviewActivity.this);  //Create a singleton CookieSyncManager within a context
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, intent);
            } else if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE
                || mUploadCallbackAboveL == null) {
            return;
        }
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        mUploadCallbackAboveL.onReceiveValue(results);
        mUploadCallbackAboveL = null;
        return;
    }


    @Override
    public void onBackPressed() {
        atyGoBack();
    }

    private void atyGoBack() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            finish();
        }

    }


    private class JsBridgeHandler implements BridgeHandler {
        @Override
        public void handler(String data, CallBackFunction function) {

        }



    }
}
