package com.gikee.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.beans.ExchangeHeadBean;
import com.gikee.app.fragment.BaseFragment;
import com.gikee.app.fragment.ExchangeProfileFragment;
import com.gikee.app.fragment.PairFragment;
import com.gikee.app.presenter.project.ExchangePresenter;
import com.gikee.app.presenter.project.ExchangeView;
import com.gikee.app.resp.ExchangeResp;
import com.gikee.app.views.AutoHeightViewPager;
import com.gikee.app.views.BaseFragmentPagerAdapter;
import com.gikee.app.views.MyBoldTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

public class ExchangeDetailActivity extends BaseActivity<ExchangePresenter> implements ExchangeView {

    @Bind(R.id.tabslayout)
    TabLayout tabslayout;
    @Bind(R.id.viewpager)
    AutoHeightViewPager viewpager;
    @Bind(R.id.project_img)
    CircleImageView project_img;
    @Bind(R.id.piar_type)
    TextView piar_type;
    @Bind(R.id.ranking)
    TextView ranking;
    @Bind(R.id.marketvalue)
    MyBoldTextView marketvalue;
    @Bind(R.id.markerranke)
    TextView markerranke;
    @Bind(R.id.currect_price)
    MyBoldTextView currect_price;

    private String id;

    private String exchange;

    private String type="intro";

    private List<BaseFragment> fragments = new ArrayList<>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ExchangePresenter(this);
        setContentView(R.layout.activity_exchangedetail);
    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @Override
    protected void setUpView() {

        id = getIntent().getStringExtra("id");

        exchange=getIntent().getStringExtra("exchange");

        showRightCollect();

        setTitleColor(R.color.black);

        setTitle(exchange);

        final String[] mTitle =new String[2];

        mTitle[0]=getString(R.string.pair);

        mTitle[1]=getString(R.string.platform_profile);

        fragments.add(PairFragment.getInstance(id,exchange));

        fragments.add(ExchangeProfileFragment.getInstance(id));



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
        viewpager.setScanScroll(false);

        viewpager.setAdapter(baseFragmentPagerAdapter);

        viewpager.setOffscreenPageLimit(2);

        viewpager.setCurrentItem(0);

        tabslayout.setupWithViewPager(viewpager);

        getData();

    }

    private void getData() {

        mPresenter.getExchange(id,type,"1");

    }

    @Override
    protected void initOnclick() {

    }

    @Override
    public void onExchange(ExchangeResp resp) {

        if(TextUtils.isEmpty(resp.getErrInfo())){

            initHeadView(resp.getResult().getExchange_data());

        }

    }

    private void initHeadView(ExchangeHeadBean exchange_data) {

        String language = MyUtils.getLocalLanguage();

        currect_price.setText(exchange);

        if(!TextUtils.isEmpty(exchange_data.getLogo())){
            Glide.with(this).load(exchange_data.getLogo()).into((CircleImageView) project_img);
        }
        if(language.equals("en")){
            piar_type.setText(exchange_data.getTags().replace("['","").replace("']","").replace("'",""));
        }else
            piar_type.setText(exchange_data.getTags_cn().replace("['","").replace("']","").replace("'",""));

        ranking.setText(String.format(getString(R.string.rank_com),exchange_data.getRank()+""));

        double value = Double.parseDouble(exchange_data.getVolume_24H());

        String str_value =null;

        if(language=="zh_CN") {
            if (value >= 100000000) {
                str_value =  MyUtils.fmtMicrometer(value / 100000000 + "") + "亿";
            } else if (value >= 10000) {
                str_value =  MyUtils.fmtMicrometer(value / 10000 + "") + "万";
            }else
                str_value = MyUtils.fmtMicrometer(value+"");
        }else{
            if(value>100000){
                str_value =  MyUtils.fmtMicrometer(value/100000+"")+"M";
            }else if(value>=1000){
                str_value = MyUtils.fmtMicrometer(value/1000+"")+"K";
            }else
                str_value = MyUtils.fmtMicrometer(value+"");
        }

        marketvalue.setText("$"+str_value);


        double value1 = Double.parseDouble(exchange_data.getVolume_24H_cny());

        String str_value1 =null;

        if(language=="zh_CN") {
            if (value >= 100000000) {
                str_value1 =  MyUtils.fmtMicrometer(value1 / 100000000 + "") + "亿";
            } else if (value >= 10000) {
                str_value1 =  MyUtils.fmtMicrometer(value1 / 10000 + "") + "万";
            }else
                str_value1 = MyUtils.fmtMicrometer(value1+"");
        }else{
            if(value>100000){
                str_value1 =  MyUtils.fmtMicrometer(value1/100000+"")+"M";
            }else if(value>=1000){
                str_value1 = MyUtils.fmtMicrometer(value1/1000+"")+"K";
            }else
                str_value1 = MyUtils.fmtMicrometer(value1+"");
        }

        markerranke.setText("¥"+str_value1);

    }

    @Override
    public void onError() {

    }
}
