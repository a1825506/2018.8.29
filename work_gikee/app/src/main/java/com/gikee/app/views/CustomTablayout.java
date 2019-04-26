package com.gikee.app.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.gikee.app.R;
import com.gikee.app.Utils.ConvertDipPx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuhao on 2017/1/10.
 * 当设置indicator的长度为0时，默认自动根据标题的长度来设置indicator的长度，如果想取消indicator，可以设置。
 * 目前，只能添加文字，不能添加图片。
 */
public class CustomTablayout extends HorizontalScrollView {
    private static final String TAG = "CustomTablayout";
    /**
     * tab容器
     */
    private TabLayout mTabLayout;
    /**
     * 正常字体颜色
     */
    private int TEXTNORMALCOLOR;
    /**
     * 选中字体颜色
     */
    private int TEXTSELECTEDCOLOR;
    /**
     * 正常字体大小
     */
    private float TEXTNORMALSIZE;
    /**
     * 选中字体大小
     */
    private float TEXTSELECTEDSIZE;
    /**
     * 背景颜色
     */
    private int BACKGROUNDCOLOR;
    /**
     * 指示器颜色
     */
    private int INDICATORCOLOR;
    /**
     * 指示器宽度(attr)
     */
    private float INDICATORWIDTH;
    /**
     * 指示器高度(attr)
     */
    private float INDICATORHEIGHT;
    /**
     * 是否添加指示器(attr)标题下的线条
     */
    private boolean ADDFORINDICATOR;
    /**
     * 是否添平均分配指示器(attr)标题下的线条
     */
    private boolean AVERAGEFORINDICATOR;

    /**
     * title内容
     */
    private List<String> mTabTitles;
    /**
     * 标题宽度
     */
    private int mTitleWidth;
    /**
     * 指示器宽度
     */
    private int mIndicatorWidth;
    List<Integer> integerList = new ArrayList<Integer>();
    /**
     * 指示器高度(dp)
     */
    private int mIndicatorHeight;
    /**
     * 默认的Tab数量
     */
    private static int COUNT_DEFAULT_TAB = 4;
    /**
     * 默认最大Tab可见数量
     */
    private static final int COUNT_DEFAULT_MAX_TAB = 6;
    /**
     * tab数量
     */
    private int mTabVisibleCount = COUNT_DEFAULT_TAB;
    /**
     * 初始时，指示器的偏移量
     */
    private int mInitTranslationX;
    /**
     * 滑动时指示器的偏移量
     */
    private float mTranslationX;
    /**
     * 指示器的宽度为单个Tab的1/3
     */
    private static float RADIO_TRIANGEL = 1.0f / 3;
    /**
     * ViewPager
     */
    private ViewPager mViewPager;
    /**
     * Tab是否被点击
     */
    private boolean isClick;

    public CustomTablayout(Context context) {
        this(context, null);

    }

    public CustomTablayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        //读取xml中
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTablayout);
            TEXTNORMALCOLOR = typedArray.getColor(R.styleable.CustomTablayout_textNormalColor, 0x80FFFFFF);
            TEXTSELECTEDCOLOR = typedArray.getColor(R.styleable.CustomTablayout_textSelectedColor, 0xFFFFFFFF);
            TEXTNORMALSIZE = typedArray.getDimension(R.styleable.CustomTablayout_textNormalSize, 13);
            TEXTSELECTEDSIZE = typedArray.getDimension(R.styleable.CustomTablayout_textSelectedSize, 15);
            BACKGROUNDCOLOR = typedArray.getColor(R.styleable.CustomTablayout_backGroundColor, 0xFF242425);
            INDICATORCOLOR = typedArray.getColor(R.styleable.CustomTablayout_indicatorColor, 0xFFFFFFFF);
            INDICATORWIDTH = typedArray.getDimension(R.styleable.CustomTablayout_indicatorWidth, 0);
            INDICATORHEIGHT = typedArray.getDimension(R.styleable.CustomTablayout_indicatorHeight, 3);
            ADDFORINDICATOR = typedArray.getBoolean(R.styleable.CustomTablayout_addforindicator, true);
            AVERAGEFORINDICATOR = typedArray.getBoolean(R.styleable.CustomTablayout_averageforindicator, false);
            typedArray.recycle();
        } else {
            TEXTNORMALCOLOR = Color.parseColor("#80FFFFFF");
            TEXTSELECTEDCOLOR = Color.parseColor("#FFFFFF");
            TEXTNORMALSIZE = 13;
            TEXTSELECTEDSIZE = 15;
            BACKGROUNDCOLOR = Color.parseColor("#242425");
            INDICATORCOLOR = Color.parseColor("#FFFFFF");
            INDICATORWIDTH = 0;
            INDICATORHEIGHT = 3;
            ADDFORINDICATOR = true;
            AVERAGEFORINDICATOR = false;
        }
        Log.i("textsize", "TEXTNORMALSIZE:" + INDICATORWIDTH + "  TEXTSELECTEDSIZE:" + INDICATORHEIGHT);
        //隐藏滚动条
        setHorizontalScrollBarEnabled(false);
        //添加tab容器
        mTabLayout = new TabLayout(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mTabLayout.setLayoutParams(params);
        addView(mTabLayout);
        mIndicatorHeight = (int) INDICATORHEIGHT;
        //初始化title宽度
        mTitleWidth = getScreenWidth() / mTabVisibleCount;
    }

    public CustomTablayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //读取xml中
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTablayout);
            TEXTNORMALCOLOR = typedArray.getColor(R.styleable.CustomTablayout_textNormalColor, 0x80FFFFFF);
            TEXTSELECTEDCOLOR = typedArray.getColor(R.styleable.CustomTablayout_textSelectedColor, 0xFFFFFFFF);
            TEXTNORMALSIZE = typedArray.getDimension(R.styleable.CustomTablayout_textNormalSize, 13);
            TEXTSELECTEDSIZE = typedArray.getDimension(R.styleable.CustomTablayout_textSelectedSize, 15);
            BACKGROUNDCOLOR = typedArray.getColor(R.styleable.CustomTablayout_backGroundColor, 0xFF242425);
            INDICATORCOLOR = typedArray.getColor(R.styleable.CustomTablayout_indicatorColor, 0xFFFFFFFF);
            INDICATORWIDTH = typedArray.getDimension(R.styleable.CustomTablayout_indicatorWidth, 0);
            INDICATORHEIGHT = typedArray.getDimension(R.styleable.CustomTablayout_indicatorHeight, 3);
            ADDFORINDICATOR = typedArray.getBoolean(R.styleable.CustomTablayout_addforindicator, true);
            typedArray.recycle();
        } else {
            TEXTNORMALCOLOR = Color.parseColor("#80FFFFFF");
            TEXTSELECTEDCOLOR = Color.parseColor("#FFFFFF");
            TEXTNORMALSIZE = 13 / 2;
            TEXTSELECTEDSIZE = 15 / 2;
            BACKGROUNDCOLOR = Color.parseColor("#242425");
            INDICATORCOLOR = Color.parseColor("#FFFFFF");
            INDICATORWIDTH = 0;
            INDICATORHEIGHT = 3;
            ADDFORINDICATOR = true;
        }
        Log.i("textsize", "TEXTNORMALSIZE:" + TEXTNORMALSIZE + "  TEXTSELECTEDSIZE:" + TEXTSELECTEDSIZE);
        //隐藏滚动条
        setHorizontalScrollBarEnabled(false);
        //添加tab容器
        mTabLayout = new TabLayout(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mTabLayout.setLayoutParams(params);
        addView(mTabLayout);
        mIndicatorHeight = (int) INDICATORHEIGHT;
        //初始化title宽度
        mTitleWidth = getScreenWidth() / mTabVisibleCount;
    }

//    public static int getCountDefaultMaxTab() {
//        return COUNT_DEFAULT_MAX_TAB;
//    }
//
//    public static void setCountDefaultTab(int countDefaultTab) {
//        COUNT_DEFAULT_TAB = countDefaultTab;
//    }

    /**
     * 固定位置填充标题
     *
     * @param datas
     * @param selected_position
     */
    public void setTitles(List<String> datas, int selected_position) {
        this.mTabTitles = datas;
        // 如果传入的list有值，则移除布局文件中设置的view
        if (datas != null && datas.size() > 0) {
            int size = datas.size();
            if (size <= selected_position) {
                selected_position = 0;
            }

            mTabLayout.removeAllViews();
            this.mTabTitles = datas;

            mTabVisibleCount = Math.min(size, COUNT_DEFAULT_MAX_TAB);
            mTitleWidth = getScreenWidth() / mTabVisibleCount;


            for (String title : mTabTitles) {
                // 添加view
                mTabLayout.addView(getTitleView(title));
            }
            if (AVERAGEFORINDICATOR) {
                mIndicatorWidth = mTitleWidth;
            } else {
                if (ADDFORINDICATOR) {
                    if (INDICATORWIDTH > 0) {
                        mIndicatorWidth = Math.min(mTitleWidth, (int) INDICATORWIDTH);
                    } else {
                        mIndicatorWidth = integerList.get(0);
                    }
                } else {
                    mIndicatorWidth = 0;
                }
            }

            // 初始时的偏移量
            mInitTranslationX = (int) ((mTitleWidth - mIndicatorWidth) / 2.0);
            // 设置item的click事件
            setTitleItemClickEvent();

            //初始时tab的位移量
            mTranslationX = mTitleWidth * selected_position;
            highLightTextView(selected_position);
        }
    }

    /**
     * 填充标题
     *
     * @param datas
     */
    public void setTitles(List<String> datas) {
        this.mTabTitles = datas;
        // 如果传入的list有值，则移除布局文件中设置的view
        if (datas != null && datas.size() > 0) {
            mTabLayout.removeAllViews();
            this.mTabTitles = datas;
            mTabVisibleCount = Math.min(datas.size(), COUNT_DEFAULT_MAX_TAB);
            mTitleWidth = getScreenWidth() / mTabVisibleCount;
            for (String title : mTabTitles) {
                // 添加view
                mTabLayout.addView(getTitleView(title));
            }
            if (AVERAGEFORINDICATOR) {
                mIndicatorWidth = mTitleWidth;
            } else {
                if (ADDFORINDICATOR) {
                    if (INDICATORWIDTH > 0) {
                        mIndicatorWidth = Math.min(mTitleWidth, (int) INDICATORWIDTH);
                    } else {
                        mIndicatorWidth = integerList.get(0);
                    }
                } else {
                    mIndicatorWidth = 0;
                }
            }
            // 初始时的偏移量
            mInitTranslationX = (int) ((mTitleWidth - mIndicatorWidth) / 2.0);
            // 设置item的click事件
           setTitleItemClickEvent();

            highLightTextView(0);
        }
    }

    public void setClicked(int j) {
        if (!isClick) {
            isClick = true;
            if (mViewPager == null) {
                Message msg = handler.obtainMessage();
                mTranslationX++;
                msg.what = 3;
                msg.arg1 = j;
                handler.sendMessage(msg);
                resetTextViewColor();
                highLightTextView(j);
                if (tabSelectedListener != null) {
                    tabSelectedListener.tabClicked(j);
                }
            } else {
                mViewPager.setCurrentItem(j, false);
            }
        } else {
            if (!handler.hasMessages(3)) {
                isClick = false;
            }
        }
    }

    /**
     * 标题点击事件
     */
    private void setTitleItemClickEvent() {
        int cCount = mTabLayout.getChildCount();
        for (int i = 0; i < cCount; i++) {
            final int j = i;
            View view = mTabLayout.getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isClick) {
                        isClick = true;
                        if (mViewPager == null) {
                            Message msg = handler.obtainMessage();
                            mTranslationX++;
                            msg.what = 3;
                            msg.arg1 = j;
                            handler.sendMessage(msg);
                            resetTextViewColor();
                            highLightTextView(j);
                            if (tabSelectedListener != null) {
                                tabSelectedListener.tabClicked(j);
                            }
                        } else {
                            mViewPager.setCurrentItem(j, false);
                        }
                    } else {
                        if (!handler.hasMessages(3)) {
                            isClick = false;
                        }
                    }
                }
            });
        }
    }

    /**
     * 高亮文本
     *
     * @param position
     */
    protected void highLightTextView(int position) {
        View view = mTabLayout.getChildAt(position);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(TEXTSELECTEDCOLOR);
            ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, TEXTSELECTEDSIZE);

        }

    }


    /**
     * 重置文本颜色
     */
    private void resetTextViewColor() {
        for (int i = 0; i < mTabLayout.getChildCount(); i++) {
            View view = mTabLayout.getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(TEXTNORMALCOLOR);
                //设置文字大小为dip的
                //   ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_DIP, TEXTNORMALSIZE);
                //设置文字大小为sp的。说明数值单位的。（因为该数值是px的）
                ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, TEXTNORMALSIZE);
            }
        }
    }

    /**
     * 获取title view
     *
     * @param title
     * @return
     */
    private View getTitleView(String title) {
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.width = mTitleWidth;
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(TEXTNORMALCOLOR);
        tv.setText(title);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, TEXTNORMALSIZE);
        tv.setLayoutParams(lp);
        int mIndicatorWidth = (int) getTextWidth(getContext(), title, (int) TEXTNORMALSIZE);
        integerList.add(mIndicatorWidth);
        return tv;
    }

    //获取文字的实际宽度。
    public float getTextWidth(Context Context, String text, int textSize) {
        TextPaint paint = new TextPaint();
        float scaledDensity = getResources().getDisplayMetrics().scaledDensity;
        //paint.setTextSize()放入的是px。
        //当textsize是sp是用这个。
        // paint.setTextSize(scaledDensity * textSize);
        paint.setTextSize(textSize);
        return paint.measureText(text);
    }

    int speed = -1;
    /**
     * 点击时，
     * 回滚的速度
     */
    public int SCROLL_SPEED = -10;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            switch (msg.what) {
                case 3:
                    if (!isClick) break;
                    final int position = msg.arg1;
                    float a = getWidth() / mTabVisibleCount * position - mTranslationX;
                    if (speed == -1) {
                        speed = (int) (a / mIndicatorWidth * 2);
                        speed = Math.abs(speed);
                        if (speed < 20) {
                            speed = 20;
                        }
                    }
                    if (a > 0) {
                        SCROLL_SPEED = speed;
                    } else if (a < 0) {
                        SCROLL_SPEED = -speed;
                    }
                    mTranslationX += SCROLL_SPEED;

                    if (a > -speed && a < speed) {
                        mTranslationX = getWidth() / mTabVisibleCount * position;
                        isClick = false;
                        speed = -1;
                    } else {
                        Message msg1 = handler.obtainMessage();

                        msg1.what = 3;
                        msg1.arg1 = position;
                        handler.sendMessage(msg1);

                    }
                    mTabLayout.invalidate();
                    break;
            }
        }
    };


    private class TabLayout extends LinearLayout {

        private final Paint mPaintIndicator;

        public TabLayout(Context context) {
            this(context, null);
        }

        public TabLayout(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            setOrientation(HORIZONTAL);
            setBackgroundColor(BACKGROUNDCOLOR);

            mPaintIndicator = new Paint();
            mPaintIndicator.setColor(INDICATORCOLOR);
            mPaintIndicator.setStyle(Paint.Style.FILL);
            mPaintIndicator.setStrokeWidth(ConvertDipPx.dip2px(context, 2));
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            //绘制指示器
            canvas.save();
            if (AVERAGEFORINDICATOR) {
                float x1 = mInitTranslationX + mTranslationX;
                float x2 = x1 + mIndicatorWidth;
                float y1 = getHeight() - mIndicatorHeight;
                float y2 = getHeight();
                canvas.drawRect(x1, y1, x2, y2, mPaintIndicator);
                // canvas.drawLine(x1, y1, x2, y2, mPaintIndicator);
                canvas.restore();
            } else {
                if (ADDFORINDICATOR) {
                    if (INDICATORWIDTH > 0) {
                        float x1 = mInitTranslationX + mTranslationX;
                        float x2 = x1 + mIndicatorWidth;
                        float y1 = getHeight() - mIndicatorHeight;
                        float y2 = getHeight();
                        canvas.drawRect(x1, y1, x2, y2, mPaintIndicator);
                        // canvas.drawLine(x1, y1, x2, y2, mPaintIndicator);
                        canvas.restore();
                    } else {
                        //两个的适合自动识别标题的难度。
                        for (int j = 0; j < integerList.size(); j++) {
                            if (mTranslationX <= j * mTitleWidth + 20 && mTranslationX >= (mTitleWidth * j - 20)) {
                                mIndicatorWidth = integerList.get(j);
                                mInitTranslationX = (int) ((mTitleWidth - mIndicatorWidth) / 2.0);
                            }
                        }
                        float x1 = mInitTranslationX + mTranslationX;
                        float x2 = x1 + mIndicatorWidth;
                        float y1, y2;
                        y1 = getHeight() - mIndicatorHeight;
                        //  y2 = y1 + Util.dip2px(mIndicatorHeight, getContext());
                        y2 = getHeight();
                        canvas.drawRect(x1, y1, x2, y2, mPaintIndicator);
                        // canvas.drawLine(x1, y1, x2, y2, mPaintIndicator);
                        canvas.restore();
                    }

                } else {

                }
            }


        }
    }

    /**
     * 获得屏幕的宽度
     *
     * @return
     */
    public int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    private TabSelectedListener tabSelectedListener;

    public void setTabSelectedListener(TabSelectedListener tabSelectedListener) {
        this.tabSelectedListener = tabSelectedListener;
    }

    public interface TabSelectedListener {
        void tabClicked(int position);
    }

    /**
     * 对外的ViewPager的回调接口
     */
    public interface PageChangeListener {
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        void onPageSelected(int position);

        void onPageScrollStateChanged(int state);
    }

    // 对外的ViewPager的回调接口
    private PageChangeListener onPageChangeListener;

    // 对外的ViewPager的回调接口的设置
    public void setOnPageChangeListener(PageChangeListener pageChangeListener) {
        this.onPageChangeListener = pageChangeListener;
    }

    // 设置关联的ViewPager
    public void setViewPager(ViewPager mViewPager, int pos) {
        this.mViewPager = mViewPager;

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // 设置字体颜色高亮
                resetTextViewColor();
                highLightTextView(position);

                // 回调
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                // 滚动
                scroll(position, positionOffset);

                // 回调
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrolled(position,
                            positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // 回调
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrollStateChanged(state);
                }

            }
        });
        // 设置当前页
        mViewPager.setCurrentItem(pos);
        // 高亮
        highLightTextView(pos);
    }

    /**
     * 指示器跟随手指滚动，以及容器滚动
     *
     * @param position
     * @param offset
     */
    public void scroll(final int position, float offset) {

        if (offset != 0.0) {
            isClick = false;
        }

        int tabWidth = getScreenWidth() / mTabVisibleCount;

        int last = 1;
        // 容器滚动，当移动到倒数最后一个的时候，开始滚动
        if (offset > 0 && position >= (mTabVisibleCount - last)
                && mTabLayout.getChildCount() > mTabVisibleCount) {
            if (mTabVisibleCount != 1) {
                scrollTo((position - (mTabVisibleCount - last)) * tabWidth
                        + (int) (tabWidth * offset), 0);
            } else {
                // 为count为1时 的特殊处理
                scrollTo(position * tabWidth + (int) (tabWidth * offset), 0);

            }
        } else if (position < (mTabVisibleCount - last)) {
            scrollTo(0, 0);
        }
        if (!isClick) {
            // 不断改变偏移量，invalidate
            mTranslationX = getWidth() / mTabVisibleCount * (position + offset);

            mTabLayout.invalidate();
        } else {
            Message msg = handler.obtainMessage();
            mTranslationX++;
            msg.what = 3;
            msg.arg1 = position;
            handler.sendMessage(msg);
        }
    }

    public int getmTitleWidth() {
        return mTitleWidth;
    }

    public void setmTitleWidth(int mTitleWidth) {
        this.mTitleWidth = mTitleWidth;
    }

}
