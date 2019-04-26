package com.gikee.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;

import com.gikee.app.R;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.beans.SearchResp;
import com.gikee.app.presenter.search.InterfaceSearchView;
import com.gikee.app.presenter.search.SearchPresenter;
import com.gikee.app.resp.BTCTradeDetailResp;
import com.gikee.app.resp.BaseLineResp;
import com.gikee.app.resp.HotProjectResp;
import com.gikee.app.resp.IsAddressResp;
import com.gikee.app.resp.LookAroundResp;
import com.gikee.app.resp.RankingDetailResp;
import com.gikee.app.resp.TokenTypeResp;

import butterknife.Bind;

public class SearchResultActivity extends BaseActivity<SearchPresenter> implements InterfaceSearchView {

    @Bind(R.id.base_tablelayout)
    TabLayout base_tablelayout;
    @Bind(R.id.search_recycleview)
    RecyclerView search_recycleview;

//    private String[] titles_base={"BTC","ETH","EOS","USDT","BCH","LTC"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresult);
    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @Override
    protected void setUpView() {

        String[] titles_base={getString(R.string.quotes),getString(R.string.title_mineproject),getString(R.string.address),getString(R.string.exchange)};

        for(String tab:titles_base){

            base_tablelayout.addTab(base_tablelayout.newTab().setText(tab));
        }


    }

    @Override
    protected void initOnclick() {

    }

    @Override
    public void onSearchView(SearchResp searchBean) {

    }

    @Override
    public void onFuzzySearch(SearchResp searchBean) {

    }

    @Override
    public void onSearchAddressView(TokenTypeResp tokenTypeResp) {

    }

    @Override
    public void onLookAround(LookAroundResp lookAroundResp) {

    }

    @Override
    public void onHot(HotProjectResp searchBean) {

    }

    @Override
    public void onRankDetail(RankingDetailResp resp) {

    }

    @Override
    public void getRankDetail2(RankingDetailResp resp) {

    }

    @Override
    public void ontHomeBaseLine(BaseLineResp resp) {

    }

    @Override
    public void onBTCTradeDetail(BTCTradeDetailResp btcTradeDetailResp) {

    }

    @Override
    public void onEOSTradeDetail(IsAddressResp resp) {

    }

    @Override
    public void onError() {

    }
}
