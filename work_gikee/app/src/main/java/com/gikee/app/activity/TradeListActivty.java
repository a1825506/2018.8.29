package com.gikee.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.adapter.SpecialTradeAdapter;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.beans.FrequenTradeResp;
import com.gikee.app.datas.Commons;
import com.gikee.app.presenter.mineaddress.SpecialAddressPresenter;
import com.gikee.app.presenter.mineaddress.SpecialAddressView;
import com.gikee.app.resp.SpecialAddressResp;
import com.gikee.app.views.MyRefreshHeader;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.Date;

import butterknife.Bind;

public class TradeListActivty extends BaseActivity<SpecialAddressPresenter> implements SpecialAddressView {

    @Bind(R.id.special_recycle)
    RecyclerView specialrecycle;
    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @Bind(R.id.nodata)
    TextView nocontext_layout;

    private String symbol="XNK";

    private int type=1;

    private int start=0;

    private int limit=15;

    private String starDate="2018-07-22";

    private String endDate="2018-08-22";

    private SpecialTradeAdapter specialTradeAdapter;

    private SpecialAddressPresenter mPresenter;

    private String address;

    private Date dateToday;

    private  View footerView;

    private TextView foottext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_specialtrade);

    }

    @Override
    protected void onChangeEvent(int type) {

    }


    @Override
    protected void setUpView() {

        setTitle(getString(R.string.trade_list));

        dateToday = new Date();

        address = getIntent().getStringExtra("address");

        type = getIntent().getIntExtra("type",1);

        symbol = Commons.smybl;

        endDate = MyUtils.getCurrectDate() + "";

        starDate = MyUtils.getOldDate(dateToday,-30) + "";

        MyRefreshHeader headerView = new MyRefreshHeader(TradeListActivty.this);
        refreshLayout.setAutoLoadMore(false);
        refreshLayout.setHeaderView(headerView);
        refreshLayout.setEnableLoadmore(true);

        mPresenter = new SpecialAddressPresenter(this);

        specialTradeAdapter = new SpecialTradeAdapter();

        specialrecycle.setLayoutManager(new LinearLayoutManager(TradeListActivty.this));

        specialrecycle.setAdapter(specialTradeAdapter);

        footerView = LayoutInflater.from(TradeListActivty.this).inflate(R.layout.view_myproject_footer, null);

        foottext=footerView.findViewById(R.id.myproject_footer_add);

        foottext.setText(R.string.loadmore);

        specialTradeAdapter.addFooterView(footerView);



        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                start=0;

                getData();

                specialTradeAdapter.getData().clear();

                specialTradeAdapter.notifyDataSetChanged();

                footerView.setVisibility(View.GONE);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                footerView.setVisibility(View.GONE);
                getData();

            }
        });

        specialTradeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent =new Intent(TradeListActivty.this, SpecialTradeDetailActivity.class);

                intent.putExtra("txHash",specialTradeAdapter.getData().get(position).getTxHash());

                startActivity(intent);

            }
        });

        refreshLayout.startRefresh();

    }

    private void getData() {


        mPresenter.getSpeciallist(type+"",address,symbol,start,limit,starDate,endDate);
    }


    @Override
    public void onSpecialAddress(SpecialAddressResp specialAddressBean) {


        // if(TextUtils.isEmpty(specialAddressBean.getErrInfo())){


    }

    @Override
    public void onSpecialList(SpecialAddressResp specialAddressBean) {

        if(specialAddressBean.getResult()!=null){

            nocontext_layout.setVisibility(View.GONE);

            if(specialAddressBean.getResult().getData().size()!=0){

                int size = specialAddressBean.getResult().getData().size();

                footerView.setVisibility(View.VISIBLE);

                if(size<limit){

                    foottext.setText(R.string.nomoredata);

                }
                start++;

                specialTradeAdapter.addData(specialAddressBean.getResult().getData());


            }else{

                if(start==0){
                    nocontext_layout.setVisibility(View.VISIBLE);
                }else
                    footerView.setVisibility(View.VISIBLE);
            }


        }else {
            nocontext_layout.setVisibility(View.VISIBLE);

            ToastUtils.show(specialAddressBean.getErrInfo());
        }


        refreshLayout.finishRefreshing();

        refreshLayout.finishLoadmore();

    }

    @Override
    public void onBigData(SpecialAddressResp specialAddressBean) {

    }

    @Override
    public void onFrequentlyTrade(FrequenTradeResp specialAddressBean) {

    }

    @Override
    protected void initOnclick() {

    }
}
