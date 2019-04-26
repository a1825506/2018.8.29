package com.gikee.app.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter{


    private List<View> views;

    public  ViewPagerAdapter(List<View> views){

        this.views=views;

    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /*
     * 删除元素
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View iv =  views.get(position);
        container.addView(iv);// 1. 向ViewPager中添加一个view对象
        return iv; // 2. 返回当前添加的view对象
    }
}
