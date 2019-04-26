package com.gikee.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.R;
import com.gikee.app.adapter.TopPlayerAdapter;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.datas.Commons;
import com.gikee.app.presenter.project.AccountPresenter;
import com.gikee.app.presenter.project.AccountView;
import com.gikee.app.resp.IntroInfoResp;
import com.gikee.app.resp.OwnerDistributeResp;
import com.gikee.app.resp.Top100Resp;
import com.gikee.app.resp.TopFreqAddrResp;
import com.gikee.app.resp.TradeCountDisResp;
import com.gikee.app.resp.TradeVolDisResp;
import com.gikee.app.resp.ValueResp;
import com.gikee.app.views.MyRefreshBottom;
import com.gikee.app.views.MyRefreshHeader;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import butterknife.Bind;


public class TopPlayerActivity extends BaseActivity<AccountPresenter> implements AccountView {


    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    @Bind(R.id.nodata)
    TextView nodata;

    private TopPlayerAdapter topPlayerAdapter;

    private String symbol;
    private Intent intent;
    private String id;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topplayer);
    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @Override
    protected void setUpView() {
        setBackground(R.color.title_color);
        mPresenter = new AccountPresenter(this);
        symbol = getIntent().getStringExtra("symbol");
        id = getIntent().getStringExtra("id");
        setTitle(symbol+" "+getString(R.string.rich_list));
        showRightCollect();

        MyRefreshHeader headerView = new MyRefreshHeader(getApplicationContext());
        MyRefreshBottom bottomView = new MyRefreshBottom(TopPlayerActivity.this);

        refreshLayout.setAutoLoadMore(false);
        refreshLayout.setHeaderView(headerView);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setBottomView(bottomView);
        refreshLayout.startRefresh();

        topPlayerAdapter = new TopPlayerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);

        divider.setDrawable(ContextCompat.getDrawable(this,R.color.linewave1));


        recyclerView.addItemDecoration(divider);

        recyclerView.setAdapter(topPlayerAdapter);

    }

    @Override
    protected void initOnclick() {

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {

                getData();
            }

        });

        topPlayerAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {


                if(symbol.equals("BTC")){
                    intent = new Intent(getApplicationContext(), BTCAddressDetailActivity.class);

                    intent.putExtra("type","BTC");

                    intent.putExtra("paramstype","address");

                    intent.putExtra("address",topPlayerAdapter.getData().get(position).getAddress());

                    startActivity(intent);
                }else if(symbol.equals("EOS")) {
                    intent = new Intent(getApplicationContext(), AddressDetailActivity.class);

                    intent.putExtra("type",symbol);

                    intent.putExtra("paramstype","address");

                    intent.putExtra("address",topPlayerAdapter.getData().get(position).getAddress());

                    startActivity(intent);
                }else{
                    intent = new Intent(getApplicationContext(), ETHAddressDetailActivity.class);

                    intent.putExtra("type","ETH");

                    intent.putExtra("paramstype","address");

                    intent.putExtra("address",topPlayerAdapter.getData().get(position).getAddress());

                    startActivity(intent);
                }


            }
        });

    }



    private void getData() {

        mPresenter.TopPlayer(id);
    }


    @Override
    public void TopPlayer(TopFreqAddrResp resp) {
        refreshLayout.finishRefreshing();
        topPlayerAdapter.getData().clear();
        if(TextUtils.isEmpty(resp.getErrInfo())){

            if(resp.getResult().getData().size()>0){

                nodata.setVisibility(View.GONE);

                topPlayerAdapter.setNewData(resp.getResult().getData());

                topPlayerAdapter.notifyDataSetChanged();
            }else
                nodata.setVisibility(View.VISIBLE);



        }else{
            nodata.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onValue(ValueResp valueResp) {

    }

    @Override
    public void onTradeVolDis(TradeVolDisResp resp) {

    }

    @Override
    public void onTradeCountDis(TradeCountDisResp resp) {

    }

    @Override
    public void onOwnerDistribute(OwnerDistributeResp resp) {

    }

    @Override
    public void ontop(Top100Resp resp) {

    }

    @Override
    public void TopTrade(TopFreqAddrResp resp) {

    }



    @Override
    public void onntroInfo(IntroInfoResp resp) {

    }

    @Override
    public void onError() {

    }
}
