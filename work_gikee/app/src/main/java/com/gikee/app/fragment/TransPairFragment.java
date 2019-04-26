package com.gikee.app.fragment;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.gikee.app.R;
import com.gikee.app.adapter.SearchResultAdapter;
import com.gikee.app.beans.SearchResp;
import com.gikee.app.beans.SearchResultBean;
import com.gikee.app.presenter.project.AccountPresenter;
import com.gikee.app.presenter.search.InterfaceSearchView;
import com.gikee.app.presenter.search.SearchPresenter;
import com.gikee.app.resp.BTCTradeDetailResp;
import com.gikee.app.resp.BaseLineResp;
import com.gikee.app.resp.HotProjectResp;
import com.gikee.app.resp.IsAddressResp;
import com.gikee.app.resp.LookAroundResp;
import com.gikee.app.resp.RankingDetailResp;
import com.gikee.app.resp.TokenTypeResp;
import com.gikee.app.type.ShowType;
import com.gikee.app.views.MyRefreshBottom;
import com.gikee.app.views.MyRefreshHeader;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class TransPairFragment extends BaseFragment<SearchPresenter> implements InterfaceSearchView {

    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @Bind(R.id.recyclerview)
    RecyclerView search_recycleview;
    @Bind(R.id.nodata)
    TextView nodata;
    private String symbol;

    private SearchResultAdapter searchResultAdapter;
    private List<SearchResultBean> list = new ArrayList<>();

    private String type= ShowType.quotes.getContent();

    public static TransPairFragment getInstance(String symbol){

        TransPairFragment indexCompFragment = new TransPairFragment();

        indexCompFragment.setParams(symbol);

        return indexCompFragment;

    }


    public void setParams(String symbol){
        this.symbol = symbol;

    }


    @Override
    protected void setupViews(LayoutInflater inflater) {
        setContentView(inflater, R.layout.fragment_transpair);
    }

    @Override
    protected void initView() {

        mPresenter = new SearchPresenter(this);
        MyRefreshHeader headerView = new MyRefreshHeader(getContext());
        MyRefreshBottom bottomView = new MyRefreshBottom(getContext());

        refreshLayout.setAutoLoadMore(false);
        refreshLayout.setHeaderView(headerView);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setBottomView(bottomView);

        searchResultAdapter = new SearchResultAdapter(list,getContext());

        search_recycleview.setLayoutManager(new LinearLayoutManager(getContext()));


        search_recycleview.setAdapter(searchResultAdapter);


        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.line_gray));

        search_recycleview.addItemDecoration(divider);

        initOnclick();
    }

    private void initOnclick() {

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {

                searchResultAdapter.getData().clear();
                searchResultAdapter.notifyDataSetChanged();
                getNetData();

            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {



            }
        });
    }

    private void getNetData() {

        mPresenter.getSearch(symbol,type);
    }

    @Override
    protected void lazyLoad() {

        if(isViewCreated&&isUIVisible){
            refreshLayout.startRefresh();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;


        }

    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @Override
    public void onSearchView(SearchResp searchBean) {

        refreshLayout.finishRefreshing();

        if(TextUtils.isEmpty(searchBean.getErrInfo())){

            searchResultAdapter.setNewData(searchBean.getResult().getData());

        }

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
