package com.gikee.app.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;
import com.gikee.app.R;
import com.gikee.app.Utils.DownloadUtils;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.datas.Commons;

import java.io.File;
import java.net.URLEncoder;


public class AppUpgradeService extends Service {

    public static final int APP_VERSION_LATEST = 0;
    public static final int APP_VERSION_OLDER = 1;

    public static final int mNotificationId = 0x118419;
    private String mDownloadUrl = null;
    private NotificationManager mNotificationManager = null;
    private Notification mNotification = null;
    private PendingIntent mPendingIntent = null;

    private File destDir = null;
    private File destFile = null;

    private static final int DOWNLOAD_FAIL = -1;
    private static final int DOWNLOAD_SUCCESS = 0;
    
    private int lastprogress = 0;
    
    private AppUpgradeThread mAppUpgradeThread;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case DOWNLOAD_SUCCESS:
                Toast.makeText(AppUpgradeService.this, R.string.installing,Toast.LENGTH_SHORT).show();
                install(destFile);
                break;
            case DOWNLOAD_FAIL:
                Toast.makeText(AppUpgradeService.this,R.string.downloadfaild,Toast.LENGTH_SHORT).show();
                mNotificationManager.cancel(mNotificationId);
               // mNotificationManager.deleteNotificationChannel("1");
                updateDownloadProgress(-1);
                break;
            default:
                break;
            }
        }

    };
    private DownloadUtils.DownloadListener downloadListener = new DownloadUtils.DownloadListener() {
        @Override
        public void downloading(int progress) {
        	if(progress > lastprogress){
        		updateDownloadProgress(progress);
        	}
        	
        	if(lastprogress > 0 && lastprogress > progress -3){
        		return;
        	}
        	lastprogress = progress;
        	
            mNotification.contentView.setProgressBar(R.id.app_upgrade_progressbar, 100, progress, false);
            mNotification.contentView.setTextViewText(R.id.app_upgrade_progresstext, progress + "%");
            mNotificationManager.notify(mNotificationId, mNotification);
        }

        @Override
        public void downloaded() {
            updateDownloadProgress(100);
            
            mNotification.contentView.setViewVisibility(R.id.app_upgrade_progressblock, View.GONE);
            mNotification.defaults = Notification.DEFAULT_SOUND;
            mNotification.contentIntent = mPendingIntent;
            mNotification.contentView.setTextViewText(R.id.app_upgrade_progresstext, getString(R.string.Downloaded));
            mNotificationManager.notify(mNotificationId, mNotification);
            if (destFile.exists() && destFile.isFile() && checkApkFile(destFile.getPath())) {
                Message msg = mHandler.obtainMessage();
                msg.what = DOWNLOAD_SUCCESS;
                mHandler.sendMessage(msg);
            }
            mNotificationManager.cancel(mNotificationId);
        }

        @Override
        public void downloadfailer() {

            ToastUtils.show(getString(R.string.downloadfaild));

        }

    };
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	extractColors();
        if(intent!=null)
            mDownloadUrl = intent.getStringExtra("downloadUrl");
        else
            return super.onStartCommand(intent, Service.START_REDELIVER_INTENT, startId);

        if(TextUtils.isEmpty(mDownloadUrl)){
            ToastUtils.show(getString(R.string.downloadfaild));
            return super.onStartCommand(intent, Service.START_REDELIVER_INTENT, startId);
        }else{
            if(mAppUpgradeThread != null && mAppUpgradeThread.isAlive()){
                Toast.makeText(AppUpgradeService.this, R.string.downloading,Toast.LENGTH_SHORT).show();
                return super.onStartCommand(intent, flags, startId);
            }
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                destDir = new File(MyUtils.getAppDirPath());
                if (destDir.exists()) {
                    @SuppressWarnings("deprecation")
                    File destFile = new File(destDir.getPath() + "/" + URLEncoder.encode(mDownloadUrl));
                    if (destFile.exists() && destFile.isFile() && checkApkFile(destFile.getPath())) {
                        destFile.delete();
//                    updateDownloadProgress(100);
//                   install(destFile);
//                    stopSelf();
                        //return super.onStartCommand(intent, flags, startId);
                    }
                }
            }else {
                return super.onStartCommand(intent, flags, startId);
            }

            mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            mNotification = new Notification();

            mNotification.contentView = new RemoteViews(getApplication().getPackageName(), R.layout.notification_upgrade);

            Intent completingIntent = new Intent();
            completingIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            completingIntent.setClass(getApplication().getApplicationContext(), AppUpgradeService.class);

            mPendingIntent = PendingIntent.getActivity(AppUpgradeService.this, R.string.app_name, completingIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            mNotification.icon = R.mipmap.ic_launcher;
            mNotification.tickerText = getString(R.string.downloading);
            mNotification.contentIntent = mPendingIntent;
            mNotification.contentView.setProgressBar(R.id.app_upgrade_progressbar, 100, 0, false);
            mNotification.contentView.setTextViewText(R.id.app_upgrade_progresstext, "0%");
            mNotification.contentView.setTextColor(R.id.app_upgrade_progresstext,
                    (notification_text_color == null ? Color.parseColor("#ff3c3c3c") : notification_text_color));
            mNotification.contentView.setTextColor(R.id.upgrade_name,
                    (notification_text_color == null ? Color.parseColor("#ff3c3c3c") : notification_text_color));
            mNotificationManager.cancel(mNotificationId);
            mNotificationManager.notify(mNotificationId, mNotification);
           // showChannel1Notification();
            mAppUpgradeThread = new AppUpgradeThread();
            mAppUpgradeThread.start();
            return super.onStartCommand(intent, flags, startId);
        }



    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showChannel1Notification(){
        mNotificationManager= (NotificationManager) AppUpgradeService.this.getSystemService(Context.NOTIFICATION_SERVICE);

        //创建 通知通道  channelid和channelname是必须的（自己命名就好）
        NotificationChannel channel = new NotificationChannel("1",
                "Channel1", NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);//是否在桌面icon右上角展示小红点
        channel.setLightColor(Color.GREEN);//小红点颜色
        channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
        mNotificationManager.createNotificationChannel(channel);

        int notificationId = 0x1234;
        Notification.Builder builder = new Notification.Builder(AppUpgradeService.this,"1");

//设置通知显示图标、文字等
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("正在下载新版本，请稍后...")
                .setAutoCancel(true);
       Notification notification=builder.build();
        mNotificationManager.notify(notificationId,notification);

//设置下载进度条
       // if (view == null) {
            notification.contentView = new RemoteViews(getPackageName(), R.layout.notification_upgrade);

            notification.contentView.setProgressBar(R.id.app_upgrade_progressbar, 100, 0, false);
       // }

//延迟意图
        PendingIntent contentIntent = PendingIntent.getActivity(this, R.string.app_name, new Intent(),
                PendingIntent.FLAG_UPDATE_CURRENT);
        notification.contentIntent = contentIntent;
        notification.flags |= Notification.FLAG_ONGOING_EVENT;// 滑动或者clear都不会清空
    }




    class AppUpgradeThread extends Thread {

        @SuppressWarnings("deprecation")
		@Override
        public void run() {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                if (destDir == null) {
                    destDir = new File(MyUtils.getAppDirPath());
                }
                if (destDir.exists() || destDir.mkdirs()) {
                    destFile = new File(destDir.getPath() + "/" + URLEncoder.encode(mDownloadUrl));
                    if (destFile.exists() && destFile.isFile() && checkApkFile(destFile.getPath())) {
                        install(destFile);
                    } else {
                        try {
                            DownloadUtils.download(mDownloadUrl, destFile, false, downloadListener);
                        } catch (Exception e) {
                            Message msg = mHandler.obtainMessage();
                            msg.what = DOWNLOAD_FAIL;
                            mHandler.sendMessage(msg);
                            e.printStackTrace();
                        }
                    }
                }
            }
            stopSelf();
        }
    }

    public boolean checkApkFile(String apkFilePath) {
        boolean result = false;
        try{
            PackageManager pManager = getPackageManager();
            PackageInfo pInfo = pManager.getPackageArchiveInfo(apkFilePath, PackageManager.GET_ACTIVITIES);
            if (pInfo == null) {
                result =  false;
            } else {
                result =  true;
            }
        } catch(Exception e) {
            result =  false;
            e.printStackTrace();
        }
        return result;
    }

    public void install(File apkFile){

        openFile(apkFile, AppUpgradeService.this);
//        Uri uri = Uri.fromFile(apkFile);
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setDataAndType(uri, "application/vnd.android.package-archive");
//        startActivity(intent);
    }


    public void openFile(File var0, Context var1) {
        Intent var2 = new Intent();
        var2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        var2.setAction(Intent.ACTION_VIEW);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            Uri uriForFile = FileProvider.getUriForFile(var1, var1.getApplicationContext().getPackageName() + ".provider", var0);
            var2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            var2.setDataAndType(uriForFile, var1.getContentResolver().getType(uriForFile));
        }else{
            var2.setDataAndType(Uri.fromFile(var0), getMIMEType(var0));
        }
        try {
            var1.startActivity(var2);
        } catch (Exception var5) {
            var5.printStackTrace();
            Toast.makeText(AppUpgradeService.this, R.string.nofindprogram,Toast.LENGTH_SHORT).show();
        }
    }


    public String getMIMEType(File var0) {
        String var1 = "";
        String var2 = var0.getName();
        String var3 = var2.substring(var2.lastIndexOf(".") + 1, var2.length()).toLowerCase();
        var1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var3);
        return var1;
    }


    private Integer notification_text_color = null;
	private final String COLOR_SEARCH_RECURSE_TIP = "SOME_SAMPLE_TEXT";

	private boolean recurseGroup(ViewGroup gp) {
		final int count = gp.getChildCount();
		for (int i = 0; i < count; ++i) {
			if (gp.getChildAt(i) instanceof TextView) {
				final TextView text = (TextView) gp.getChildAt(i);
				final String szText = text.getText().toString();
				if (COLOR_SEARCH_RECURSE_TIP.equals(szText)) {
					notification_text_color = text.getTextColors()
							.getDefaultColor();
					DisplayMetrics metrics = new DisplayMetrics();
					WindowManager systemWM = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
					systemWM.getDefaultDisplay().getMetrics(metrics);
					return true;
				}
			} else if (gp.getChildAt(i) instanceof ViewGroup)
				if(recurseGroup((ViewGroup) gp.getChildAt(i))){
					return true;
				}
		}
		return false;
	}

	@SuppressLint("NewApi") @SuppressWarnings("deprecation")
	private void extractColors() {
		if (notification_text_color != null)
			return;

		try {
			Notification ntf;
            if(Build.VERSION.SDK_INT >= 16){
                ntf = new Notification.Builder(this).setContentTitle(COLOR_SEARCH_RECURSE_TIP)
                        .setContentText("Utest").build();
            } else {
                ntf = new Notification();
//                ntf.setLatestEventInfo(this, COLOR_SEARCH_RECURSE_TIP, "Utest",
//                        null);
            }

			LinearLayout group = new LinearLayout(this);
			ViewGroup event = (ViewGroup) ntf.contentView.apply(this, group);
			recurseGroup(event);
			group.removeAllViews();
		} catch (Exception e) {
			notification_text_color = android.R.color.black;
		}
	}
	
	private void updateDownloadProgress(int progress){
		Intent intent = new Intent(Commons.BROADCAST_MSG_UPGRADE_PROGRESS);
		intent.putExtra(Commons.EXTRA_UPGRADE_PROGRESS, progress);
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}
}
