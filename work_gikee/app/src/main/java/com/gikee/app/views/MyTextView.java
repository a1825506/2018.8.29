package com.gikee.app.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

import com.gikee.app.base.GikeeApplication;

/**
 * Created by THINKPAD on 2018/6/11.
 */

public class MyTextView extends android.support.v7.widget.AppCompatTextView {
    public MyTextView(Context context) {
        super(context);
        //设置字体
        setTypeface(((GikeeApplication)(((Activity)context).getApplication())).getTypeface());
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //设置字体
        setTypeface(((GikeeApplication)(((Activity)context).getApplication())).getTypeface());
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置字体
        setTypeface(((GikeeApplication)(((Activity)context).getApplication())).getTypeface());
    }

}

