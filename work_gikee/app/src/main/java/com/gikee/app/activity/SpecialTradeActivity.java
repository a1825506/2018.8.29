package com.gikee.app.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.fragment.BaseFragment;
import com.gikee.app.fragment.BigDataFragment;
import com.gikee.app.fragment.FrequentlyTradeFragment;
import com.gikee.app.fragment.SpecialTradeFragment;
import com.gikee.app.type.ShowType;
import com.gikee.app.views.BaseFragmentPagerAdapter;
import com.gikee.app.views.CustomTablayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;


public class SpecialTradeActivity extends BaseActivity  {


    @Bind(R.id.tabslayout)
    CustomTablayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;

     private List<BaseFragment> fragments = new ArrayList<>();

    private String symbol="XNK";

    private int start=0;

    private int limit=10;

    private String starDate;

    private String endDate;

    private MessageReceiver mMessageReceiver;

    private String title;

    private String type;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialtrade);

    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @Override
    protected void setUpView() {


            symbol = getIntent().getStringExtra("id");
            title = symbol+" "+getIntent().getStringExtra("title");
            type =  getIntent().getStringExtra("type");

        setTitle(title);
        Date dt = new Date();
        endDate= MyUtils.getCurrectDate();
        starDate = MyUtils.getOldDate(dt,-7);
        getNetData();
        registerMessageReceiver();
    }

    private void getNetData() {

        fragments.add(SpecialTradeFragment.getInstance(symbol,start,limit,starDate,endDate));

        fragments.add(BigDataFragment.getInstance(symbol,start,limit,starDate,endDate));

        fragments.add(FrequentlyTradeFragment.getInstance(symbol,start,limit,starDate,endDate));


        final String[] mTitle =new String[3];//getResources().getStringArray(R.array.specialtrade_title);

        mTitle[0]=getString(R.string.specialtrade);

        mTitle[1]=getString(R.string.bigTradeCountDis);

        mTitle[2]=getString(R.string.frequentlyTrade);

        BaseFragmentPagerAdapter baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            protected Fragment getItemFragment(int position) {
                return fragments.get(position);
            }

            @Override
            protected String[] getMTitles() {
                return  mTitle;
            }

            @Override
            protected List<String> getAutoMTitles() {
                return null;
            }
        };
        mViewPager.setAdapter(baseFragmentPagerAdapter);

        mViewPager.setOffscreenPageLimit(3);

        tabLayout.setTitles(baseFragmentPagerAdapter.getTitles());



        if(ShowType.bigTrade.getContent().equals(type)){
            tabLayout.setViewPager(mViewPager,1);
        }else if(ShowType.frequentTrade.getContent().equals(type)){
            tabLayout.setViewPager(mViewPager,2);
        }else
            tabLayout.setViewPager(mViewPager,0);



    }



    @Override
    protected void initOnclick() {



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public static final String MESSAGE_RECEIVED_ACTION = "com.example.frequent.MESSAGE_RECEIVED_ACTION";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }


    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {




                }
            } catch (Exception e){

            }
        }
    }


}
