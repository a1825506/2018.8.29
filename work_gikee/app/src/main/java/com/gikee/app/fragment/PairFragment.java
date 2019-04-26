package com.gikee.app.fragment;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.gikee.app.R;
import com.gikee.app.activity.ProjectDetailActivity;
import com.gikee.app.activity.SearchActivity;
import com.gikee.app.adapter.SearchResultAdapter;
import com.gikee.app.beans.PairItemBean;
import com.gikee.app.beans.SearchResultBean;
import com.gikee.app.presenter.project.ExchangePresenter;
import com.gikee.app.presenter.project.ExchangeView;
import com.gikee.app.presenter.project.ShuJuFenXiPresentetr;
import com.gikee.app.resp.ExchangeResp;
import com.gikee.app.type.ShowType;
import com.gikee.app.views.MyRefreshBottom;
import com.gikee.app.views.MyRefreshHeader;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 交易所详情-交易对页面
 */
public class PairFragment extends BaseFragment<ExchangePresenter> implements ExchangeView {

    @Bind(R.id.fm_pair_refreshLayout)
    TwinklingRefreshLayout twinklingRefreshLayout;
    @Bind(R.id.fm_pair_recyclerview)
    RecyclerView search_recycleview;

    private SearchResultAdapter searchResultAdapter;
    private List<SearchResultBean> list = new ArrayList<>();
    private String id;
    private String exchange;
    private String type="pair";
    private String p="1";
    private int limite = 25;
    private int total_count=0;
    private int start=1;
    private boolean issort=true;

    public static PairFragment getInstance(String id,String exchange){

        PairFragment pairFragment = new PairFragment();

        pairFragment.setParams(id,exchange);

        return pairFragment;

    }

    public void setParams(String id,String exchange){
        this.id = id;
        this.exchange = exchange;
    }

    @Override
    protected void setupViews(LayoutInflater inflater) {

        setContentView(inflater, R.layout.fragment_pair);

    }

    @Override
    protected void initView() {

        mPresenter = new ExchangePresenter(this);

        searchResultAdapter = new SearchResultAdapter(list,getContext());

        MyRefreshHeader headerView = new MyRefreshHeader(getContext());
        MyRefreshBottom bottomView = new MyRefreshBottom(getContext());
        twinklingRefreshLayout.setEnableLoadmore(true);
        twinklingRefreshLayout.setAutoLoadMore(true);
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setBottomView(bottomView);

        search_recycleview.setLayoutManager(new LinearLayoutManager(getContext()));


        search_recycleview.setAdapter(searchResultAdapter);


        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.line_gray));

        search_recycleview.addItemDecoration(divider);
        initOnclick();

    }

    private void initOnclick() {

        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                start=1;
                searchResultAdapter.getData().clear();
                searchResultAdapter.notifyDataSetChanged();
                getNetData();

            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                issort = false;
                if(start*limite<total_count){
                    start++;
                    getNetData();
                }else{
                    refreshLayout.finishLoadmore();

                }


            }
        });
    }

    private void getNetData() {

        mPresenter.getExchange(id,type,start+"");
    }

    @Override
    protected void lazyLoad() {

        if(isViewCreated&&isUIVisible){

            twinklingRefreshLayout.startRefresh();
            isViewCreated = false;
            isUIVisible = false;

        }

    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @Override
    public void onExchange(ExchangeResp resp) {

        twinklingRefreshLayout.finishRefreshing();

        twinklingRefreshLayout.finishLoadmore();

        if(TextUtils.isEmpty(resp.getErrInfo())){

            if(resp.getResult().getPair_data()!=null){

                List<PairItemBean> data =resp.getResult().getPair_data().getData();

                List<SearchResultBean> searchResultBeanList = new ArrayList<>();

                total_count = resp.getResult().getPair_data().getTotal_count();

                for(PairItemBean pairItemBean:data){

                    SearchResultBean searchResultBean = new SearchResultBean();

                    searchResultBean.setShowType(ShowType.quotes.getContent());

                    searchResultBean.setExchange(exchange);

                    searchResultBean.setTransaction_pair(pairItemBean.getPair_one()+"/"+pairItemBean.getPair_two());

                    searchResultBean.setTurnover(pairItemBean.getVolume_24H());

                    searchResultBean.setPrice_usd(pairItemBean.getPrice_one());

                    searchResultBean.setPrice_pair(pairItemBean.getPrice_pair());

                    searchResultBean.setQuateChange(pairItemBean.getPrice_change());

                    searchResultBeanList.add(searchResultBean);

                }

                if(issort){
                    searchResultAdapter.setNewData(searchResultBeanList);
                }else
                    searchResultAdapter.addData(searchResultBeanList);


            }

        }



    }

    @Override
    public void onError() {

    }
}
