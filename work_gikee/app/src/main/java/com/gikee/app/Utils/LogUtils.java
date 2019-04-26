package com.gikee.app.Utils;

import android.util.Log;

/**
 * Created by THINKPAD on 2018/3/14.
 */

public class LogUtils {

    private static boolean isShow = true;

    public static void showLog(String msg) {
        if (isShow)
            Log.e("hou", msg);
    }
}
