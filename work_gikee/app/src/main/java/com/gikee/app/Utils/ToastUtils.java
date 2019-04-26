package com.gikee.app.Utils;

import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

import com.gikee.app.base.GikeeApplication;


/**
 * Created by THINKPAD on 2018/3/14.
 */

public class ToastUtils {

    private static Handler handler = new Handler(Looper.getMainLooper());
    private static Toast toast = null;
    private static Object synObj = new Object();

    /**
     * Toast发送消息
     *
     * @param msg
     */
    public static void show(final String msg) {
        new Thread(new Runnable() {
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (synObj) {
                            if (toast != null) {
                                toast.cancel();
                                toast = null;
                            }
                            toast = Toast.makeText(GikeeApplication.getMyApplicationContext(), msg, Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                });
            }
        }).start();
    }

    public static void showCenter(final String msg) {
        new Thread(new Runnable() {
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (synObj) {
                            if (toast != null) {
                                toast.cancel();
                                toast = null;
                            }
                            toast = Toast.makeText(GikeeApplication.getMyApplicationContext(), msg, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                });
            }
        }).start();
    }
}
