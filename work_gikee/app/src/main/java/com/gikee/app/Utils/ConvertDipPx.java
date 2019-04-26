package com.gikee.app.Utils;

/**
 * @author tgh
 * @date 18-8-6
 * @time 下午12:06
 * @describe TODO
 * @email a18255064049@163.com
 */

import android.content.Context;
import android.view.WindowManager;

public class ConvertDipPx {

    public static int dip2px(Context context,float dipValue){
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dipValue*scale+0.5f);
    }

    public static int px2dip(Context context,float pxValue){
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale+0.5f);
    }

    public static int getWindowWidth (Context context) {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }
    public static int getWindowHeight (Context context) {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight() ;
    }

}
