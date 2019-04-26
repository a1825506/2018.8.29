package com.gikee.app.itcast.download;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gikee.app.R;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.itcast.net.download.DownloadProgressListener;
import com.gikee.app.itcast.net.download.FileDownloader;

public class DownloadActivity extends Activity {
    private ProgressBar downloadbar;
    private EditText pathText;
    private TextView resultView;
    private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			if(!(Thread.currentThread().isInterrupted())){//��ǰ�̴߳��ڷ��ж�ʱ
			switch (msg.what) {
			case 1:
				int size = msg.getData().getInt("size");
				downloadbar.setProgress(size);
				float result = (float)downloadbar.getProgress()/ (float)downloadbar.getMax();
				int p = (int)(result*100);
				resultView.setText(p+"%");
				if(downloadbar.getProgress()==downloadbar.getMax())//��ǰ���ȵ������ֵʱ
					ToastUtils.show(getString(R.string.success));
				break;

			case -1:
				ToastUtils.show(getString(R.string.error));
				break;
			}
		  }
		}    	
    };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);
        
        Button button = (Button)this.findViewById(R.id.button);
        downloadbar = (ProgressBar)this.findViewById(R.id.downloadbar);
        pathText = (EditText)this.findViewById(R.id.path);
        resultView = (TextView)this.findViewById(R.id.result);
        button.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				String path = pathText.getText().toString();
				if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
					File dir = Environment.getExternalStorageDirectory();
					download(path, dir);
				}else{
					ToastUtils.show(getString(R.string.sdcarderror));
				}
			}
		});
    }

    private void download(final String path, final File dir){
    	new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					FileDownloader loader = new FileDownloader(DownloadActivity.this, path, dir, 3);
					int length = loader.getFileSize();//��ȡ�ļ��ĳ���
					downloadbar.setMax(length);
					loader.download(new DownloadProgressListener(){
						@Override
						public void onDownloadSize(int size) {//����ʵʱ�õ��ļ����صĳ���
							Message msg = new Message();
							msg.what = 1;
							msg.getData().putInt("size", size);							
							handler.sendMessage(msg);//������Ϣ
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