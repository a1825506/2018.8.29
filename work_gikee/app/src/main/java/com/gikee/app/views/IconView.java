package com.gikee.app.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class IconView extends AppCompatTextView {

    public IconView(Context context) {
        super(context);
        init(context);
    }
    public IconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public IconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context) {
//        设置字体图标
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"icons/iconfont.ttf"));
    }

}
