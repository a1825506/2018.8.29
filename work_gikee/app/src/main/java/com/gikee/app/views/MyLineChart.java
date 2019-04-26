package com.gikee.app.views;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.github.mikephil.charting.charts.LineChart;

/**
 * Created by THINKPAD on 2018/7/26.
 */


public class MyLineChart extends LineChart  {

    private int downX, downY;
    private int mTouchSlop;


    public MyLineChart(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void init() {
        super.init();
        mXAxisRenderer = new MyXAxisRenderer(mViewPortHandler, mXAxis, mLeftAxisTransformer);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getRawX();
                downY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) event.getRawY();
                int moveX = (int) event.getRawX();
                if (Math.abs(moveY - downY) < 100) {
                    Log.e("MotionEvent ", "自己处理");
                    getParent().requestDisallowInterceptTouchEvent(true);//这句话是告诉父view，我的事件自己处理
                }else{
                    Log.e("MotionEvent ", "父布局处理:");
                    getParent().requestDisallowInterceptTouchEvent(false);

                }
                break;

        }
        return super.onTouchEvent(event);
    }
}
