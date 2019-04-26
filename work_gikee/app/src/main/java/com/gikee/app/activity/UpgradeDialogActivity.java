package com.gikee.app.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.datas.Commons;
import com.gikee.app.resp.VersionBean;
import com.gikee.app.service.AppUpgradeService;
import com.google.gson.Gson;

public class UpgradeDialogActivity extends Activity implements OnClickListener {
	
	private ProgressBar mProgressBar;
	private TextView mProgressTextView;
	private TextView versioninfo,alerdy_download;
	
	private VersionBean appVersion;
	private boolean isIgnore = false;
//	private int progress = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		Gson gson = new Gson();

		appVersion=gson.fromJson(getIntent().getStringExtra("AppVersion"),VersionBean.class);

		
		setContentView(R.layout.activity_upgrade);

		setFinishOnTouchOutside(false);
		
		initView();
		
		registerBoradcastReceiver();
	}
	
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.upgrade_ok:
			Intent service = new Intent(UpgradeDialogActivity.this, AppUpgradeService.class);
			service.putExtra("downloadUrl",appVersion.getDownUrl());
			startService(service);

			if(appVersion!=null){

				if("force".equals(appVersion.getMode())){
					findViewById(R.id.upgrade_action_view).setVisibility(View.GONE);
					findViewById(R.id.upgrade_progress_view).setVisibility(View.VISIBLE);

					updateProgress(0);
				} else {
					finish();
				}

			}


			break;
		case R.id.upgrade_cancel:
			if(isIgnore){
				MyUtils.addSysMap(this, Commons.SHARED_UPGRADE_IGNORE_VERSION, appVersion.getNewVersion());
			}
			finish();
			break;

		default:
			break;
		}
	}

	
	@Override
	public void finish() {
		super.finish();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterBroadcastReceiver();
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			return true;
		}
		return super.dispatchKeyEvent(event);
	}
	
	private void initView(){

		if(appVersion!=null){
			TextView content = (TextView) findViewById(R.id.upgrade_version);
			content.setText(appVersion.getNewVersion());
		}



		versioninfo = (TextView) findViewById(R.id.versioninfo);

		alerdy_download = (TextView)findViewById(R.id.versioninfo);


		if(!TextUtils.isEmpty(appVersion.getVersioninfo_zh_CN())){

			versioninfo.setText(appVersion.getVersioninfo_zh_CN());
		}

		if(!TextUtils.isEmpty(appVersion.getVersioninfo_en())){

			versioninfo.setText(appVersion.getVersioninfo_en());
		}

		//versioninfo.setText(appVersion.getVersioninfo());
		
		((CheckBox) findViewById(R.id.upgrade_ignore)).setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean selected) {
				isIgnore = selected;
			}
		});
		
		findViewById(R.id.upgrade_ok).setOnClickListener(this);
		findViewById(R.id.upgrade_cancel).setOnClickListener(this);
		
		mProgressBar = (ProgressBar) findViewById(R.id.upgrade_progress);
		mProgressTextView = (TextView) findViewById(R.id.upgrade_progress_text);


		if(appVersion!=null){
			if("force".equals(appVersion.getMode())){

				findViewById(R.id.view_letf).setVisibility(View.GONE);
				findViewById(R.id.upgrade_cancel).setVisibility(View.GONE);
			}
		}


		Window dialogWindow = getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
	//	int width = GikeeApplication.getDisplayMetrics(this).widthPixels;
		lp.width =MyUtils.dip2px(250);
		lp.height =WindowManager.LayoutParams.WRAP_CONTENT; //(int) (GikeeApplication.getDisplayMetrics(this).heightPixels * 0.48);
		dialogWindow.setAttributes(lp);
	}


	private void updateProgress(int progress){
		if(progress == 100){
			alerdy_download.setVisibility(View.VISIBLE);
			findViewById(R.id.upgrade_action_view).setVisibility(View.VISIBLE);
			findViewById(R.id.upgrade_progress_view).setVisibility(View.GONE);
		}else {
			mProgressTextView.setText(progress+"%");//size * progress / 100 + "K / " + size + "K"
			mProgressBar.setProgress(progress);
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
		LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, filter);
	}
	
	private void unregisterBroadcastReceiver(){
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
	}
	
}
