package com.gikee.app.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sea Zhang on 2017/3/25.
 */

public abstract class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = getMTitles();

    private List<String> mTAutoitles = getAutoMTitles();

    protected abstract Fragment getItemFragment(int position);

    protected abstract String[] getMTitles();

    protected  abstract List<String> getAutoMTitles();


    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return getItemFragment(position);
    }

    @Override
    public int getCount() {
        if(mTAutoitles!=null&&mTAutoitles.size()!=0){
            return mTAutoitles.size();
        }else if(mTitles!=null&&mTitles.length!=0){
            return mTitles.length;
        }else
            return 0;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    public List<String> getTitles() {
        List<String> titlesList = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            titlesList.add(mTitles[i]);
        }
        return titlesList;

    }

    public List<String> getAutoTitles() {
        List<String> titlesList = new ArrayList<>();
        for (int i = 0; i < mTAutoitles.size(); i++) {
            titlesList.add(mTAutoitles.get(i));
        }
        return titlesList;

    }

}