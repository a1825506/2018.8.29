package com.gikee.app.views;

import android.content.Context;
import android.util.AttributeSet;

import com.gikee.app.base.GikeeApplication;

/**
 * Created by THINKPAD on 2018/6/11.
 */

public class MyBoldTextView extends android.support.v7.widget.AppCompatTextView {
    public MyBoldTextView(Context context) {
        super(context);
        //设置字体
        setTypeface(GikeeApplication.getMyApplicationContext().getTypeface_bold());
    }

    public MyBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //设置字体
        setTypeface(GikeeApplication.getMyApplicationContext().getTypeface_bold());
    }

    public MyBoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置字体
        setTypeface(GikeeApplication.getMyApplicationContext().getTypeface_bold());
    }

}

