package com.gikee.app.activity;



import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.adapter.TokenBalanceAdapter;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.base.GikeeApplication;
import com.gikee.app.beans.ETHBaseInfoBean;
import com.gikee.app.beans.GasdetailBean;
import com.gikee.app.beans.TokenBean;
import com.gikee.app.beans.TokendBean;
import com.gikee.app.beans.TokendetailBean;
import com.gikee.app.fragment.ETHAddressDetailFragment;
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
import com.gikee.app.views.IconView;
import com.gikee.app.views.MyRefreshBottom;
import com.gikee.app.views.MyRefreshHeader;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


public class TradeDetailActivity extends BaseActivity<AddressDetailPresenter> implements AddressDetailView {



    @Bind(R.id.address_recycle_layout)
    TwinklingRefreshLayout twinklingRefreshLayout;
    @Bind(R.id.base_recycle)
    RecyclerView base_recycle;
    @Bind(R.id.trade_address_layout)
    LinearLayout trade_address_layout;
    @Bind(R.id.layout)
    RelativeLayout layout;
    @Bind(R.id.address_id)
    TextView address_id;
    @Bind(R.id.copy)
    IconView copy;
    @Bind(R.id.total_account_title)
    TextView total_account_title;
    @Bind(R.id.line_view)
    View line_view;



    private AddressDetailPresenter mPresenter;

    private TokenBalanceAdapter baseInfoAdapter;

    private List<TokendBean> tokendBeanList = new ArrayList<>();

    private List<TokendetailBean> tokendetailBeanList = new ArrayList<>();

    private List<TokendetailBean> transferdetailBeanList = new ArrayList<>();

    private ETHBaseInfoBean ethBaseInfoBean;

    private GasdetailBean gasdetailBean;

    private  TokendBean tokendBean;

    private String trade_type = "ETH";

    private String type="ETH";

    private String params="0x88281fcd275f3503c9c23c41dc3043ab750fb38bbbe8d3b282bdb4c0e25bcd5d";

    private String paramstype = "hash";

    private ClipData myClip;

    private Intent intent;

    private int type_jump=0;


    private  View footerView;

    private TextView foottext;

    public static  OnUpdateAddress onUpdateAddress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tradedetail);
//        layout.setBackgroundResource(R.color.white);
        layout.setBackground(getResources().getDrawable(R.mipmap.bg_trade));
        total_account_title.setVisibility(View.GONE);
        line_view.setVisibility(View.GONE);
    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @Override
    protected void setUpView() {
        setTitleColor(R.color.white);
        setBackground(R.color.title_color);
        hideRightCollect(View.GONE);
        mPresenter = new AddressDetailPresenter(this);
        type = getIntent().getStringExtra("type");
        params = getIntent().getStringExtra("hash");
        type_jump =  getIntent().getIntExtra("jumptype",-1);
        trade_type = getIntent().getStringExtra("trade_type");
        copy.setTextColor(getResources().getColor(R.color.white));
        address_id.setTextColor(getResources().getColor(R.color.white));
        showTitleBar();
        setTitle(type+getString(R.string.trade_detail));
        MyRefreshHeader headerView = new MyRefreshHeader(TradeDetailActivity.this);
        MyRefreshBottom bottomView = new MyRefreshBottom(TradeDetailActivity.this);

        twinklingRefreshLayout.setBottomView(bottomView);
        twinklingRefreshLayout.setAutoLoadMore(true);
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setEnableLoadmore(false);

        footerView = LayoutInflater.from(TradeDetailActivity.this).inflate(R.layout.view_myproject_footer, null);

        foottext=footerView.findViewById(R.id.myproject_footer_add);

        foottext.setText(R.string.moredata);

        baseInfoAdapter = new TokenBalanceAdapter();

        baseInfoAdapter.addFooterView(footerView);


        LinearLayoutManager layoutManager = new LinearLayoutManager(TradeDetailActivity.this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        base_recycle.setLayoutManager(layoutManager);

        base_recycle.setFocusableInTouchMode(false); //设置不需要焦点
        base_recycle.requestFocus(); //设置焦点不需要

        base_recycle.setAdapter(baseInfoAdapter);

        twinklingRefreshLayout.startRefresh();

    }


    private Handler mhandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mPresenter.getHashDetail(type,paramstype,params);
        }
    };


    @Override
    protected void initOnclick() {


        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {

                getData();


            }


        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = address_id.getText().toString();
                myClip = ClipData.newPlainText("text", text);
                GikeeApplication.getMyApplicationContext().getMyClipboard().setPrimaryClip(myClip);
                ToastUtils.show(getString(R.string.copied));

            }
        });


        baseInfoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                onUpdateAddress.updateAddress(baseInfoAdapter.getData().get(position).getValue());

                finish();



            }
        });


        baseInfoAdapter.setIOnItemClick(new TokenBalanceAdapter.IOnItemClick() {
            @Override
            public void onItemClick(String address) {

                onUpdateAddress.updateAddress(address);

                finish();

            }
        });



    }


    public static void setOnUpdateAddress(OnUpdateAddress mListener) {
        onUpdateAddress = mListener;
    }


    public interface OnUpdateAddress {
        void updateAddress(String address);
    }

    private void getData() {

        mPresenter.getHashDetail(type,paramstype,params);

    }


    @Override
    public void HashDetail(HashDetailResp hashDetailResp) {

        twinklingRefreshLayout.finishRefreshing();

        address_id.setText(params);
        trade_address_layout.setVisibility(View.VISIBLE);
        copy.setVisibility(View.VISIBLE);


        if(!TextUtils.isEmpty(hashDetailResp.getErrInfo())){

            ToastUtils.show(getString(R.string.nodata));
        }else{
            if(hashDetailResp.getResult()!=null){

                ethBaseInfoBean = hashDetailResp.getResult().getBaseInfo();


                if(tokendBeanList.size()!=0){
                    tokendBeanList.clear();
                }

                if(ethBaseInfoBean!=null){

                    if(!TextUtils.isEmpty(ethBaseInfoBean.getFrom())){
                        tokendBean = new TokendBean();
                        if(type.equals("EOS")){
                            tokendBean.setTitle(getString(R.string.from_hash));
                        }else
                            tokendBean.setTitle(getString(R.string.send));
                        tokendBean.setValue(ethBaseInfoBean.getFrom());
                        tokendBean.setType(TokenBalanceAdapter.item_type);
                        tokendBeanList.add(tokendBean);
                        tokendBean = null;
                    }

                    if(!"contract".equals(trade_type)){
                        if(!TextUtils.isEmpty(ethBaseInfoBean.getTo())){
                            tokendBean = new TokendBean();
                            tokendBean.setTitle(getString(R.string.receive));
                            tokendBean.setValue(ethBaseInfoBean.getTo());
                            tokendBean.setType(TokenBalanceAdapter.item_type);
                            tokendBeanList.add(tokendBean);
                            tokendBean = null;
                        }

                    }
                    //合约内部转账
                    if(hashDetailResp.getResult().getTransferdetail()!=null){

                        transferdetailBeanList  = hashDetailResp.getResult().getTransferdetail();

                        if(transferdetailBeanList.size()!=0){

                            tokendBean = new TokendBean();
                            tokendBean.setTransferdetail(transferdetailBeanList);
                            tokendBean.setType(TokenBalanceAdapter.contract_type);
                            tokendBean.setTrade_type("contract");
                            tokendBean.setValue(ethBaseInfoBean.getTo());
                            tokendBeanList.add(tokendBean);
                            tokendBean = null;

                        }
                    }

//                    if(!type.equals("EOS")){


                        //代币转账
                        if(hashDetailResp.getResult().getTokendetail()!=null){

                            tokendetailBeanList = hashDetailResp.getResult().getTokendetail();

                            List<TokendetailBean> tokendetailBeanList1 = new ArrayList<>();

                            if(tokendetailBeanList.size()>0){


                                for(int i=0;i<tokendetailBeanList.size();i++){


                                    TokendetailBean tokendetailBean = new TokendetailBean();

                                    tokendetailBean.setFrom(tokendetailBeanList.get(i).getFrom());

                                    tokendetailBean.setTo(tokendetailBeanList.get(i).getTo());

                                    tokendetailBean.setToken(tokendetailBeanList.get(i).getToken());

                                    tokendetailBeanList1.add(tokendetailBean);


                                }

                                tokendBean = new TokendBean();
                                tokendBean.setTransferdetail(tokendetailBeanList1);
                                tokendBean.setType(TokenBalanceAdapter.contract_type);
                                tokendBean.setTrade_type("erc20");
//                            tokendBean.setValue(ethBaseInfoBean.getTo());
                                tokendBeanList.add(tokendBean);
                                tokendBean = null;
                            }
                        }

//                    }

                    if(!TextUtils.isEmpty(ethBaseInfoBean.getBlock_height())){
                        tokendBean = new TokendBean();
                        tokendBean.setTitle(getString(R.string.block_height));
                        tokendBean.setValue(ethBaseInfoBean.getBlock_height());
                        tokendBean.setType(TokenBalanceAdapter.item_type);
                        tokendBeanList.add(tokendBean);
                        tokendBean = null;
                    }

                    if(!TextUtils.isEmpty(ethBaseInfoBean.getAmount())){
                        tokendBean = new TokendBean();
                        tokendBean.setTitle(getString(R.string.amount));
                        tokendBean.setValue(ethBaseInfoBean.getAmount());
                        tokendBean.setType(TokenBalanceAdapter.item_type);
                        tokendBeanList.add(tokendBean);
                        tokendBean = null;
                    }

                    if(!TextUtils.isEmpty(ethBaseInfoBean.getTime())){
                        tokendBean = new TokendBean();
                        tokendBean.setTitle(getString(R.string.today_tran_time));
                        if(type.equals("EOS")){
                            tokendBean.setValue(MyUtils.timeChange(ethBaseInfoBean.getTime(),type,""));
                        }else
                            tokendBean.setValue(ethBaseInfoBean.getTime());
                        tokendBean.setType(TokenBalanceAdapter.item_type);
                        tokendBeanList.add(tokendBean);
                        tokendBean = null;
                    }

                    if(!TextUtils.isEmpty(ethBaseInfoBean.getStatus())){
                        tokendBean = new TokendBean();
                        tokendBean.setTitle(getString(R.string.status));
                        if(ethBaseInfoBean.getStatus().equals("Success")||ethBaseInfoBean.getStatus().equals("executed")){
                            tokendBean.setValue(getString(R.string.success_status));
                        }else
                            tokendBean.setValue(getString(R.string.failure_status));

                        tokendBean.setType(TokenBalanceAdapter.item_type);
                        tokendBeanList.add(tokendBean);
                        tokendBean = null;
                    }

                }

                gasdetailBean = hashDetailResp.getResult().getGasdetail();

                //Gas详情
                if(gasdetailBean!=null){

                    //Gas详情title
                    tokendBean = new TokendBean();
                    tokendBean.setTitle(getString(R.string.gas_detail));
                    tokendBean.setType(TokenBalanceAdapter.title_type);
                    tokendBeanList.add(tokendBean);
                    tokendBean = null;

                    if(!TextUtils.isEmpty(gasdetailBean.getGaslimit())){
                        tokendBean = new TokendBean();
                        tokendBean.setTitle(getString(R.string.gas_limit));
                        tokendBean.setValue(gasdetailBean.getGaslimit());
                        tokendBean.setType(TokenBalanceAdapter.item_type);
                        tokendBeanList.add(tokendBean);
                        tokendBean = null;
                    }

                    if(!TextUtils.isEmpty(gasdetailBean.getGas_used_by_txn())){
                        tokendBean = new TokendBean();
                        tokendBean.setTitle(getString(R.string.gas_used));
                        tokendBean.setValue(gasdetailBean.getGas_used_by_txn());
                        tokendBean.setType(TokenBalanceAdapter.item_type);
                        tokendBeanList.add(tokendBean);
                        tokendBean = null;
                    }

                    if(!TextUtils.isEmpty(gasdetailBean.getGas_price())){
                        tokendBean = new TokendBean();
                        tokendBean.setTitle(getString(R.string.gas_price));
                        tokendBean.setValue(gasdetailBean.getGas_price());
                        tokendBean.setType(TokenBalanceAdapter.item_type);
                        tokendBeanList.add(tokendBean);
                        tokendBean = null;
                    }

                    if(!TextUtils.isEmpty(gasdetailBean.getActual_tx_cost())){
                        tokendBean = new TokendBean();
                        tokendBean.setTitle(getString(R.string.gas_pay));
                        tokendBean.setValue(gasdetailBean.getActual_tx_cost());
                        tokendBean.setType(TokenBalanceAdapter.item_type);
                        tokendBeanList.add(tokendBean);
                        tokendBean = null;
                    }

                }

                baseInfoAdapter.setNewData(tokendBeanList);



            }else{

                ToastUtils.show(getString(R.string.load_fail));
            }

        }



    }

    @Override
    public void onEOSAddress(AddressDetailResp eosAddressResp) {

    }


    @Override
    public void onBTCTradeDetail(BTCTradeDetailResp btcTradeDetailResp) {

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
    public void onError() {

    }
}