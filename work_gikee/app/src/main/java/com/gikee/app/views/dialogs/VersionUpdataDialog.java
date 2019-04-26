package com.gikee.app.views.dialogs;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.datas.Commons;
import com.gikee.app.itcast.net.download.DownloadProgressListener;
import com.gikee.app.itcast.net.download.FileDownloader;
import com.gikee.app.resp.VersionBean;
import com.gikee.app.service.AppUpgradeService;

import java.io.File;

public class VersionUpdataDialog extends Dialog implements View.OnClickListener {

    private TextView versioninfo,alerdy_download,content,mProgressTextView;
    private Context mcontext;
    private VersionBean appVersion;
    private ProgressBar mProgressBar;
    private File destDir = new File(MyUtils.getAppDirPath());


    public VersionUpdataDialog( Context context,int themeResId,VersionBean appVersion) {
        super(context,themeResId);
        this.mcontext = context;
        this.appVersion = appVersion;

    }

    public VersionUpdataDialog setTitle(){

        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);
        if(appVersion!=null){

            if("force".equals(appVersion.getMode())){
                findViewById(R.id.upgrade_cancel).setVisibility(View.GONE);
                findViewById(R.id.upgrade_ok).setVisibility(View.VISIBLE);
                setCanceledOnTouchOutside(false);
                setCancelable(false);
            } else {
                setCanceledOnTouchOutside(true);
                setCancelable(true);
            }

        }
        initView();

        registerBoradcastReceiver();
    }

    private void initView() {

        content = (TextView) findViewById(R.id.upgrade_version);

        versioninfo = (TextView) findViewById(R.id.versioninfo);

        alerdy_download = (TextView)findViewById(R.id.versioninfo);

        mProgressBar = (ProgressBar) findViewById(R.id.upgrade_progress);

        mProgressTextView = (TextView) findViewById(R.id.upgrade_progress_text);

        if(!TextUtils.isEmpty(appVersion.getNewVersion())){
            content.setText(appVersion.getNewVersion());
        }

        if(!TextUtils.isEmpty(appVersion.getVersioninfo_zh_CN())){

            String version_info= appVersion.getVersioninfo_zh_CN();

            if(appVersion.getVersioninfo_zh_CN().contains("\\n")){

                version_info =appVersion.getVersioninfo_zh_CN().replace("\\n","\n");

            }

            versioninfo.setText(version_info);
        }

        if(!TextUtils.isEmpty(appVersion.getVersioninfo_en())){


            String version_info= appVersion.getVersioninfo_en();

            if(appVersion.getVersioninfo_en().contains("\\n")){

                version_info =appVersion.getVersioninfo_en().replace("\\n","\n");

            }

            versioninfo.setText(version_info);

          //  versioninfo.setText(appVersion.getVersioninfo_en().replace("//","/"));
        }


        findViewById(R.id.upgrade_ok).setOnClickListener(this);


        findViewById(R.id.upgrade_cancel).setOnClickListener(this);



    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.upgrade_ok:
            Intent service = new Intent(mcontext, AppUpgradeService.class);
            service.putExtra("downloadUrl",appVersion.getDownUrl());
            mcontext.startService(service);
            ToastUtils.show(mcontext.getResources().getString(R.string.downloading));

            if(appVersion!=null){

                if("ask".equals(appVersion.getMode())){
                    findViewById(R.id.upgrade_action_view).setVisibility(View.GONE);
                    findViewById(R.id.upgrade_progress_view).setVisibility(View.GONE);
                    setCanceledOnTouchOutside(false);
                    updateProgress(0);
                    dismiss();
                }else if("force".equals(appVersion.getMode())){
                    findViewById(R.id.upgrade_action_view).setVisibility(View.GONE);
                    findViewById(R.id.upgrade_progress_view).setVisibility(View.VISIBLE);
                    setCanceledOnTouchOutside(false);
                    updateProgress(0);
                }

            }


            break;
            case R.id.upgrade_cancel:
                dismiss();
                break;

            default:
                break;
        }
    }

    private BroadcastReceiver mBroadcastReceiver;

    private void registerBoradcastReceiver(){
        mBroadcastReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent data) {
                if(data.getAction().equals(Commons.BROADCAST_MSG_UPGRADE_PROGRESS)){
                    int progress = data.getIntExtra(Commons.EXTRA_UPGRADE_PROGRESS, 0);
                    if(progress >= 0){
                        updateProgress(progress);
                    }else {
                        findViewById(R.id.upgrade_action_view).setVisibility(View.VISIBLE);
                        findViewById(R.id.upgrade_progress_view).setVisibility(View.GONE);
                    }
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(Commons.BROADCAST_MSG_UPGRADE_PROGRESS);
        LocalBroadcastManager.getInstance(mcontext).registerReceiver(mBroadcastReceiver, filter);
    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            if(!(Thread.currentThread().isInterrupted())){//��ǰ�̴߳��ڷ��ж�ʱ
                switch (msg.what) {
                    case 1:
                        int size = msg.getData().getInt("size");
                        mProgressBar.setProgress(size);
                        float result = (float)mProgressBar.getProgress()/ (float)mProgressBar.getMax();
                        int p = (int)(result*100);
                        mProgressTextView.setText(p+"%");
                        if(mProgressBar.getProgress()==mProgressBar.getMax())//��ǰ���ȵ������ֵʱ
                            ToastUtils.show(mcontext.getString(R.string.success));
                        break;

                    case -1:
                        ToastUtils.show(mcontext.getString(R.string.error));
                        break;
                }
            }
        }
    };
    private void updateProgress(int progress){
        if(progress == 100){
            alerdy_download.setVisibility(View.VISIBLE);
            findViewById(R.id.upgrade_action_view).setVisibility(View.GONE);
            findViewById(R.id.upgrade_progress_view).setVisibility(View.VISIBLE);
        }
            mProgressTextView.setText(progress+"%");//size * progress / 100 + "K / " + size + "K"
            mProgressBar.setProgress(progress);

    }



    private void download(final String path, final File dir){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileDownloader loader = new FileDownloader(mcontext, path, dir, 5);
                    int length = loader.getFileSize();
                    mProgressBar.setMax(length);
                    loader.download(new DownloadProgressListener(){
                        @Override
                        public void onDownloadSize(int size) {
                            Message msg = new Message();
                            msg.what = 1;
                            msg.getData().putInt("size", size);
                            handler.sendMessage(msg);

                        }});
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = -1;
                    msg.getData().putString("error", "����ʧ��");
                    handler.sendMessage(msg);

                }
            }
        }).start();

    }
}
