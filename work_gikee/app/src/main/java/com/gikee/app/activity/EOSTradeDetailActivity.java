package com.gikee.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.gikee.app.R;
import com.gikee.app.adapter.EosTokenTradeAdapter;
import com.gikee.app.adapter.TokenBalanceAdapter;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.beans.EOSBaseInfoBean;
import com.gikee.app.beans.TokendBean;
import com.gikee.app.preference_config.PreferenceUtil;
import com.gikee.app.presenter.baseline.AddressDetailPresenter;
import com.gikee.app.presenter.baseline.AddressDetailView;
import com.gikee.app.resp.AddressDetailResp;
import com.gikee.app.resp.BTCAddressReap;
import com.gikee.app.resp.BTCTradeDetailResp;
import com.gikee.app.resp.BTCTradeListResp;
import com.gikee.app.resp.EOSTradeDetailResp;
import com.gikee.app.resp.ERC20ListResp;
import com.gikee.app.resp.HashDetailResp;
import com.gikee.app.resp.HashTradeResp;
import com.gikee.app.views.MyRefreshHeader;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class EOSTradeDetailActivity extends BaseActivity<AddressDetailPresenter> implements AddressDetailView {

    @Bind(R.id.address_recycle_layout)
    TwinklingRefreshLayout twinklingRefreshLayout;
    @Bind(R.id.base_recycle)
    RecyclerView base_recycle;
    @Bind(R.id.token_recycle)
    RecyclerView token_recycle;

    private TokenBalanceAdapter tokenBalanceAdapter;

    private EosTokenTradeAdapter eosTokenTradeAdapter;

    private String type="BTC";

    private String params="1Nh7uHdvY6fNwtQtM1G5EZAFPLC33B59rB";

    private String paramstype = "address";

    private List<TokendBean> tokendBeanList = new ArrayList<>();

    private EOSBaseInfoBean eosBaseInfoBean;

    private   TokendBean tokendBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eostradedetail);
        mPresenter = new AddressDetailPresenter(this);
    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @Override
    protected void setUpView() {

        MyRefreshHeader headerView = new MyRefreshHeader(getApplicationContext());
        twinklingRefreshLayout.setAutoLoadMore(false);
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setEnableLoadmore(true);

        tokenBalanceAdapter = new TokenBalanceAdapter();

        base_recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        base_recycle.setAdapter(tokenBalanceAdapter);

        token_recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        token_recycle.setAdapter(eosTokenTradeAdapter);


    }



    @Override
    protected void initOnclick() {

        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {


                getData();


            }

        });

    }

    private void getData() {

        mPresenter.getEOSTradeDetail(type,paramstype,params);
    }


    @Override
    public void onEOSTradeDetail(EOSTradeDetailResp eosTradeDetailResp) {

        if(eosTradeDetailResp.getResult()!=null){

            twinklingRefreshLayout.finishRefreshing();

            if(tokendBeanList.size()!=0){

                tokendBeanList.clear();

            }

            eosBaseInfoBean = eosTradeDetailResp.getResult().getBaseInfoBean();

            if(!TextUtils.isEmpty(eosBaseInfoBean.getExpireDate())){
                tokendBean = new TokendBean();
                tokendBean.setTitle(getString(R.string.expire_date));
                tokendBean.setValue(eosBaseInfoBean.getExpireDate());
                tokendBeanList.add(tokendBean);
                tokendBean = null;
            }

            if(!TextUtils.isEmpty(eosBaseInfoBean.getStatus())){
                tokendBean = new TokendBean();
                tokendBean.setTitle(getString(R.string.status));
                tokendBean.setValue(eosBaseInfoBean.getStatus());
                tokendBeanList.add(tokendBean);
                tokendBean = null;
            }

            if(!TextUtils.isEmpty(eosBaseInfoBean.getBlockHeight())){
                tokendBean = new TokendBean();
                tokendBean.setTitle(getString(R.string.block_height));
                tokendBean.setValue(eosBaseInfoBean.getBlockHeight());
                tokendBeanList.add(tokendBean);
                tokendBean = null;
            }

            if(!TextUtils.isEmpty(eosBaseInfoBean.getBlockHash())){
                tokendBean = new TokendBean();
                tokendBean.setTitle(getString(R.string.block_hash));
                tokendBean.setValue(eosBaseInfoBean.getBlockHash());
                tokendBeanList.add(tokendBean);
                tokendBean = null;
            }


            PreferenceUtil.putListData(params,tokendBeanList);

        }

    }

    @Override
    public void onAddressDetail(AddressDetailResp addressDetailResp) {

    }

    @Override
    public void onTradeList(HashTradeResp specialAddressResp) {

    }

    @Override
    public void onAddressTrans(ERC20ListResp resp) {

    }

    @Override
    public void onBTCTradeList(BTCTradeListResp resp) {

    }

    @Override
    public void onBTCAddressDetail(BTCAddressReap btcAddressReap) {

    }

    @Override
    public void HashDetail(HashDetailResp hashDetailResp) {

    }

    @Override
    public void onEOSAddress(AddressDetailResp eosAddressResp) {

    }


    @Override
    public void onBTCTradeDetail(BTCTradeDetailResp btcTradeDetailResp) {

    }


    @Override
    public void onError() {

    }
}
