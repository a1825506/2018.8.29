package com.gikee.app.activity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gikee.app.R;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.adapter.BTCAddressFromAdpter;
import com.gikee.app.adapter.TokenBalanceAdapter;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.base.GikeeApplication;
import com.gikee.app.beans.BTCBaseInfoBean;
import com.gikee.app.beans.BTCTradeDetailBean;
import com.gikee.app.beans.TokendBean;
import com.gikee.app.fragment.ETHAddressDetailFragment;
import com.gikee.app.presenter.baseline.AddressDetailPresenter;
import com.gikee.app.presenter.baseline.AddressDetailView;
import com.gikee.app.resp.AddressDetailResp;
import com.gikee.app.resp.BTCAddressReap;
import com.gikee.app.resp.BTCTradeDetailResp;
import com.gikee.app.resp.BTCTradeListResp;
import com.gikee.app.resp.EOSAddressResp;
import com.gikee.app.resp.EOSTradeDetailResp;
import com.gikee.app.resp.ERC20ListResp;
import com.gikee.app.resp.HashDetailResp;
import com.gikee.app.resp.HashTradeResp;
import com.gikee.app.views.IconView;
import com.gikee.app.views.MyRefreshBottom;
import com.gikee.app.views.MyRefreshHeader;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class BTCTradeDetailActivity extends BaseActivity<AddressDetailPresenter> implements AddressDetailView {

    @Bind(R.id.base_recycle)
    RecyclerView base_recycle;
    @Bind(R.id.total_account)
    TextView total_account;
    @Bind(R.id.account_amount)
    TextView account_amount;
    @Bind(R.id.in_recycle)
    RecyclerView in_recycle;
    @Bind(R.id.output_recycle)
    RecyclerView output_recycle;
    @Bind(R.id.total_out_account)
    TextView total_out_account;
    @Bind(R.id.account_out_amount)
    TextView account_out_amount;
    @Bind(R.id.address_recycle_layout)
    TwinklingRefreshLayout twinklingRefreshLayout;
    @Bind(R.id.token_transfer_layout)
    RelativeLayout token_transfer_layout;
    @Bind(R.id.in_layout)
    RelativeLayout in_layout;
    @Bind(R.id.out_layout)
    RelativeLayout out_layout;
    @Bind(R.id.address_id)
    TextView address_id;
    @Bind(R.id.copy)
    IconView copy;
    @Bind(R.id.layout)
    RelativeLayout layout;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.trade_address_layout)
    LinearLayout trade_address_layout;


    private TokenBalanceAdapter tokenBalanceAdapter;

    private BTCAddressFromAdpter btcAddressFromAdpter,btcAddressFromAdpter_to;

    private String txhash;

    private String type="BTC";

    private String params="";

    private String paramstype = "address";

    private List<TokendBean> tokendBeanList = new ArrayList<>();

    private BTCBaseInfoBean btcBaseInfoBean;

    private TokendBean tokendBean;

    private ClipData myClip;

    private Intent intent;

    private int type_jump=0;

    private BTCTradeDetailBean btcTradeDetailBean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btctradedetail);
        layout.setBackgroundResource(R.color.white);
    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @Override
    protected void setUpView() {
        showTitleBar();
        setTitle("BTC "+getString(R.string.trade_detail));
        setTitleColor(R.color.black);
        hideRightCollect(View.GONE);
        copy.setTextColor(getResources().getColor(R.color.cbcbcb));
        type_jump =  getIntent().getIntExtra("type",-1);
        params = getIntent().getStringExtra("address");
        btcTradeDetailBean = (BTCTradeDetailBean) getIntent().getSerializableExtra("tradeDetail");
        address_id.setTextColor(getResources().getColor(R.color.black));
        mPresenter = new AddressDetailPresenter(this);
        MyRefreshHeader headerView = new MyRefreshHeader(getApplicationContext());
        MyRefreshBottom bottomView = new MyRefreshBottom(getApplicationContext());

        twinklingRefreshLayout.setBottomView(bottomView);

        twinklingRefreshLayout.setAutoLoadMore(false);
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setEnableLoadmore(false);

        tokenBalanceAdapter = new TokenBalanceAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BTCTradeDetailActivity.this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(BTCTradeDetailActivity.this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(BTCTradeDetailActivity.this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };


        base_recycle.setLayoutManager(linearLayoutManager);

        base_recycle.setAdapter(tokenBalanceAdapter);






        btcAddressFromAdpter = new BTCAddressFromAdpter(params);

        btcAddressFromAdpter_to = new BTCAddressFromAdpter(params);


        in_recycle.setLayoutManager(linearLayoutManager1);

        in_recycle.setAdapter(btcAddressFromAdpter);


        output_recycle.setLayoutManager(linearLayoutManager2);

        output_recycle.setAdapter(btcAddressFromAdpter_to);


        if(btcTradeDetailBean==null){
            twinklingRefreshLayout.startRefresh();
        }else{

            showDetail(btcTradeDetailBean);
        }


      //  getData();

    }

    @Override
    protected void initOnclick() {

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = address_id.getText().toString();
                if(!TextUtils.isEmpty(text)){
                    myClip = ClipData.newPlainText("text", text);
                    GikeeApplication.getMyApplicationContext().getMyClipboard().setPrimaryClip(myClip);
                    ToastUtils.show(getString(R.string.copied));
                }

            }
        });


        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {


                getData();


            }

        });


//        btcAddressFromAdpter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//
//                intent = new Intent(getApplicationContext(), BTCAddressDetailActivity.class);
//
//                intent.putExtra("type","BTC");
//
//                intent.putExtra("paramstype","address");
//
//                intent.putExtra("address",btcAddressFromAdpter.getData().get(position).getAddress());
//
//                startActivity(intent);
//            }
//        });

        btcAddressFromAdpter.setIOnItemClick(new BTCAddressFromAdpter.IOnItemClick() {
            @Override
            public void onItemClick(BTCAddressFromAdpter.ViewName viewName, String address) {

                intent = new Intent(getApplicationContext(), BTCAddressDetailActivity.class);

                intent.putExtra("type","BTC");

                intent.putExtra("paramstype","address");

                intent.putExtra("address",address);

                if(type_jump==1){
                    setResult(ETHAddressDetailFragment.REQUESCODE,intent);

                    finish();
                }

                startActivity(intent);

            }
        });

        btcAddressFromAdpter_to.setIOnItemClick(new BTCAddressFromAdpter.IOnItemClick() {
            @Override
            public void onItemClick(BTCAddressFromAdpter.ViewName viewName, String address) {

                intent = new Intent(getApplicationContext(), BTCAddressDetailActivity.class);

                intent.putExtra("type","BTC");

                intent.putExtra("paramstype","address");

                intent.putExtra("address",address);

                if(type_jump==1){
                    setResult(ETHAddressDetailFragment.REQUESCODE,intent);

                    finish();
                }

                startActivity(intent);

            }
        });



    }

    private void getData() {

        mPresenter.getBTCTradeDetail(type,paramstype,params);

    }


    @Override
    public void onBTCTradeDetail(BTCTradeDetailResp btcTradeDetailResp) {
        twinklingRefreshLayout.finishRefreshing();
        if(!TextUtils.isEmpty(btcTradeDetailResp.getErrInfo())){

           // finish();
            ToastUtils.show(getString(R.string.nodata));


        }else{

            if(btcTradeDetailResp.getResult()!=null){

                showDetail(btcTradeDetailResp.getResult());

            }else{

                ToastUtils.show(getString(R.string.load_fail));
            }

        }

    }

    private void showDetail(BTCTradeDetailBean result) {


        trade_address_layout.setVisibility(View.VISIBLE);
        address_id.setText(params);
        copy.setVisibility(View.VISIBLE);

        token_transfer_layout.setVisibility(View.VISIBLE);

        in_layout.setVisibility(View.VISIBLE);

        out_layout.setVisibility(View.VISIBLE);

        twinklingRefreshLayout.finishRefreshing();




        if(tokendBeanList.size()!=0){

            tokendBeanList.clear();

        }

        btcBaseInfoBean = result.getBaseInfo();

        if(!TextUtils.isEmpty(result.getAddress_in_count())){

            String value= String.format(getString(R.string.total_account),result.getAddress_in_count());

            total_account.setText(value);
        }


        if(!TextUtils.isEmpty(result.getAddress_out_count())){
            String value= String.format(getString(R.string.total_account),result.getAddress_out_count());


            account_amount.setText(value);
        }

        if(!TextUtils.isEmpty(btcBaseInfoBean.getBlockHeight())){
            tokendBean = new TokendBean();
            tokendBean.setTitle(getString(R.string.block_height)+":");
            tokendBean.setValue(btcBaseInfoBean.getBlockHeight());
            tokendBeanList.add(tokendBean);
            tokendBean = null;
        }

        if(!TextUtils.isEmpty(btcBaseInfoBean.getConfirmNum())){
            tokendBean = new TokendBean();
            tokendBean.setTitle(getString(R.string.confirm_num)+":");
            tokendBean.setValue(btcBaseInfoBean.getConfirmNum());
            tokendBeanList.add(tokendBean);
            tokendBean = null;
        }

        if(!TextUtils.isEmpty(btcBaseInfoBean.getBlockTime())){
            tokendBean = new TokendBean();
            tokendBean.setTitle(getString(R.string.block_time)+":");
            tokendBean.setValue(btcBaseInfoBean.getBlockTime());
            tokendBeanList.add(tokendBean);
            tokendBean = null;
        }

        if(!TextUtils.isEmpty(btcBaseInfoBean.getBlockSize())){
            tokendBean = new TokendBean();
            tokendBean.setTitle(getString(R.string.block_size)+":");
            tokendBean.setValue(btcBaseInfoBean.getBlockSize());
            tokendBeanList.add(tokendBean);
            tokendBean = null;
        }

        if(!TextUtils.isEmpty(btcBaseInfoBean.getVirtualSize())){
            tokendBean = new TokendBean();
            tokendBean.setTitle(getString(R.string.virtual_size)+":");
            tokendBean.setValue(btcBaseInfoBean.getVirtualSize());
            tokendBeanList.add(tokendBean);
            tokendBean = null;
        }

        if(!TextUtils.isEmpty(btcBaseInfoBean.getWeight())){
            tokendBean = new TokendBean();
            tokendBean.setTitle("Weight:");
            tokendBean.setValue(btcBaseInfoBean.getWeight());
            tokendBeanList.add(tokendBean);
            tokendBean = null;
        }

        if(!TextUtils.isEmpty(btcBaseInfoBean.getInput())){
            tokendBean = new TokendBean();
            tokendBean.setTitle(getString(R.string.input)+":");
            tokendBean.setValue(btcBaseInfoBean.getInput());
            tokendBeanList.add(tokendBean);
            tokendBean = null;
        }

        if(!TextUtils.isEmpty(btcBaseInfoBean.getOutput())){
            tokendBean = new TokendBean();
            tokendBean.setTitle(getString(R.string.output)+":");
            tokendBean.setValue(btcBaseInfoBean.getOutput());
            tokendBeanList.add(tokendBean);
            tokendBean = null;
        }

        if(!TextUtils.isEmpty(btcBaseInfoBean.getSigops())){
            tokendBean = new TokendBean();
            tokendBean.setTitle("Sigops:");
            tokendBean.setValue(btcBaseInfoBean.getSigops());
            tokendBeanList.add(tokendBean);
            tokendBean = null;
        }

        if(!TextUtils.isEmpty(btcBaseInfoBean.getMinerFee())){
            tokendBean = new TokendBean();
            tokendBean.setTitle(getString(R.string.gas_pay)+":");
            tokendBean.setValue(btcBaseInfoBean.getMinerFee());
            tokendBeanList.add(tokendBean);
            tokendBean = null;
        }

        if(!TextUtils.isEmpty(btcBaseInfoBean.getMinerRate())){
            tokendBean = new TokendBean();
            tokendBean.setTitle(getString(R.string.gas_rate)+":");
            tokendBean.setValue(btcBaseInfoBean.getMinerRate());
            tokendBeanList.add(tokendBean);
            tokendBean = null;
        }

        if(!TextUtils.isEmpty(btcBaseInfoBean.getInput())){
            account_amount.setText(btcBaseInfoBean.getInput());
        }

        if(!TextUtils.isEmpty(btcBaseInfoBean.getOutput())){
            account_out_amount.setText(btcBaseInfoBean.getOutput());
        }

        tokenBalanceAdapter.setNewData(tokendBeanList);

        // PreferenceUtil.putListData(params,tokendBeanList);

        btcAddressFromAdpter.setNewData(result.getAddress_in());

        btcAddressFromAdpter_to.setNewData(result.getAddress_out());


        tokenBalanceAdapter.setNewData(tokendBeanList);


    }

    @Override
    public void onEOSTradeDetail(EOSTradeDetailResp eosTradeDetailResp) {

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
    public void onError() {

    }
}
