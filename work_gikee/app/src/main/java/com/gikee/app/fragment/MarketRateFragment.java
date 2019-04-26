package com.gikee.app.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.R;
import com.gikee.app.activity.ProjectDetailActivity;
import com.gikee.app.adapter.MarketRateAdapter;
import com.gikee.app.presenter.home.HomePresenter;
import com.gikee.app.presenter.home.HomeView;
import com.gikee.app.resp.MarketRateResp;
import com.gikee.app.resp.PowerResp;
import com.gikee.app.resp.SummaryResp;
import com.gikee.app.resp.TopFreqAddrResp;
import com.gikee.app.resp.ValueResp;

import butterknife.Bind;

public class MarketRateFragment extends BaseFragment<HomePresenter> implements HomeView{

    @Bind(R.id.recyclerview_marketRate)
    RecyclerView recyclerview_marketRate;

    private MarketRateAdapter marketRateAdapter;
    private  HomePresenter mPresenter;


    @Override
    protected void setupViews(LayoutInflater inflater) {

        setContentView(inflater, R.layout.fragment_marketrate);



    }

    @Override
    protected void initView() {

        mPresenter = new HomePresenter(this);

        marketRateAdapter = new MarketRateAdapter();

        recyclerview_marketRate.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerview_marketRate.setAdapter(marketRateAdapter);

        marketRateAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getContext(), ProjectDetailActivity.class)
                        .putExtra("logo",marketRateAdapter.getData().get(position).getLogo())
                        .putExtra("symbol", marketRateAdapter.getData().get(position).getSymbol())
                        .putExtra("id",  marketRateAdapter.getData().get(position).getId()));

            }
        });

    }

    private void getNetData(){

        mPresenter.getMarketRate();
    }

    @Override
    protected void lazyLoad() {

        if(isViewCreated&&isUIVisible) {
            getNetData();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }

    }

    @Override
    protected void onChangeEvent(int type) {

//        getNetData();

    }

    @Override
    public void onMarketRate(MarketRateResp marketRateResp) {

        if(TextUtils.isEmpty(marketRateResp.getErrInfo())){

            if(marketRateResp.getResult()!=null){
                marketRateAdapter.setNewData(marketRateResp.getResult().getData());
            }



        }



    }

    @Override
    public void onMarketTrend(ValueResp valueResp) {

    }

    @Override
    public void onPower(PowerResp powerResp) {

    }

    @Override
    public void onChain(SummaryResp summaryResp) {

    }

    @Override
    public void onTopPlayer(TopFreqAddrResp topFreqAddrResp) {

    }

    @Override
    public void onError() {

    }
}
