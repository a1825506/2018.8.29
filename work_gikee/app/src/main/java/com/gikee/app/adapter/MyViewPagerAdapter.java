package com.gikee.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gikee.app.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> mList =new ArrayList<>();
    private String[] titles;

    public MyViewPagerAdapter(FragmentManager fm, String[] titles, List<BaseFragment> mList) {
        super(fm);
        this.titles=titles;
        this.mList=mList;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }
    //3个条目
    @Override
    public int getCount() {
        return mList.size();
    }

    //将标题与适配器绑定
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}

