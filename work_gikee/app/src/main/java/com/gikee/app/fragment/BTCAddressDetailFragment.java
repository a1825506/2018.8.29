package com.gikee.app.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.gikee.app.R;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.activity.BTCAddressDetailActivity;
import com.gikee.app.activity.BTCTradeDetailActivity;
import com.gikee.app.adapter.BTCAddressDetailAdapter;
import com.gikee.app.adapter.BTCAddressFromAdpter;
import com.gikee.app.beans.BTCAddressTradeBean;
import com.gikee.app.datas.Commons;
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
import com.gikee.app.views.MyRefreshBottom;
import com.gikee.app.views.MyRefreshHeader;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class BTCAddressDetailFragment extends BaseFragment<AddressDetailPresenter> implements AddressDetailView {

    @Bind(R.id.address_recycle_layout)
    TwinklingRefreshLayout twinklingRefreshLayout;
    @Bind(R.id.address_recycle)
    RecyclerView recyclerView;



    private static volatile BTCAddressDetailFragment baseLineFragment;

    private BTCAddressDetailAdapter btcAddressDetailAdapter;

    private OnUpdateAddress onUpdateAddress;

    private View footerView;

    private TextView foottext;

    private String type="BTC";

    private String params="";

    private String paramstype = "address";

    private int page = 2;

    private int limit=25;

    private List<BTCAddressTradeBean> btcAddressTradeBeans = new ArrayList<>();


    public static BTCAddressDetailFragment getInstance() {

        if (baseLineFragment == null) {

            synchronized (BTCAddressDetailFragment.class) {

                if (baseLineFragment == null) {

                    baseLineFragment = new BTCAddressDetailFragment();

                }

            }

        }

        return baseLineFragment;

    }

    public void setParams(String type,String param) {

        this.params =  param ;

        this.type = type;

        Commons.ETH_ADDRESS.clear();
        Commons.ETH_ADDRESS.add(params);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            params = data.getStringExtra("address");
            Commons.ETH_ADDRESS.clear();
            Commons.ETH_ADDRESS.add(params);
            twinklingRefreshLayout.startRefresh();
        }

    }




    public void setAdapter(List<BTCAddressTradeBean> btcAddressTradeBeans){

        twinklingRefreshLayout.finishRefreshing();
        btcAddressDetailAdapter.getData().clear();
        btcAddressDetailAdapter.notifyDataSetChanged();

        if(btcAddressTradeBeans!=null){
            if(btcAddressTradeBeans.size()!=0){
                btcAddressDetailAdapter.addData(btcAddressTradeBeans);
                if(btcAddressTradeBeans.size()<=limit){
                    footerView.setVisibility(View.GONE);
                }else
                    footerView.setVisibility(View.VISIBLE);

            }else{
                if(getContext()!=null)
                  ToastUtils.show(getContext().getString(R.string.nodata));

            }


        }else{
            if(getContext()!=null)
                ToastUtils.show(getContext().getString(R.string.nodata));
        }


    }


    @Override
    protected void setupViews(LayoutInflater inflater) {
        setContentView(inflater, R.layout.fragment_btcaddress_detail);
    }

    @Override
    protected void initView() {

        mPresenter = new AddressDetailPresenter(this);
        MyRefreshHeader headerView = new MyRefreshHeader(getContext());
        MyRefreshBottom bottomView = new MyRefreshBottom(getContext());

        twinklingRefreshLayout.setBottomView(bottomView);
        twinklingRefreshLayout.setAutoLoadMore(true);
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setEnableLoadmore(true);
        btcAddressDetailAdapter = new BTCAddressDetailAdapter(params);

        footerView = LayoutInflater.from(getContext()).inflate(R.layout.view_myproject_footer, null);

        foottext=footerView.findViewById(R.id.myproject_footer_add);

        foottext.setText(R.string.loadmore);

        btcAddressDetailAdapter.addFooterView(footerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(btcAddressDetailAdapter);

        initOnClick();

    }

    private void initOnClick() {

        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                onUpdateAddress.updateAddress(params);

            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                // footerView.setVisibility(View.GONE);
                mPresenter.getBTCTradeList(params,page);
            }
        });


        btcAddressDetailAdapter.setIOnItemClick(new BTCAddressDetailAdapter.IOnItemClick() {
            @Override
            public void onItemClick(BTCAddressFromAdpter.ViewName viewName, String address, int position) {

                if(viewName==BTCAddressFromAdpter.ViewName.ADDRESS){

                    params = address;

                    btcAddressDetailAdapter.setParams(params);

                    twinklingRefreshLayout.startRefresh();

                }else if(viewName==BTCAddressFromAdpter.ViewName.ITEM){

                    Intent intent = new Intent(getContext(), BTCTradeDetailActivity.class);

                    intent.putExtra("type","BTC");

                    intent.putExtra("paramstype","hash");

                    intent.putExtra("type",1);

                    intent.putExtra("address",btcAddressDetailAdapter.getData().get(position).getTx_hash());

                    startActivityForResult(intent,ETHAddressDetailFragment.REQUESCODE);


                }

            }
        });
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onChangeEvent(int type) {

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
    public void onBTCTradeList(BTCTradeListResp btcTradeListResp) {

        twinklingRefreshLayout.finishLoadmore();

        if(TextUtils.isEmpty(btcTradeListResp.getErrInfo())){

            if(btcTradeListResp.getResult()!=null) {

                if (btcTradeListResp.getResult().getHashDetail().size() != 0) {

                    if (page * limit <= BTCAddressDetailActivity.getTotalTrade()) {
                        page++;
                    } else
                        foottext.setText(R.string.nomoredata);

                    btcAddressTradeBeans = btcTradeListResp.getResult().getHashDetail();

                    btcAddressDetailAdapter.addData(btcAddressTradeBeans);

                }
            }
        }else
            ToastUtils.show(getString(R.string.net_bad));

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
    public void onEOSTradeDetail(EOSTradeDetailResp eosTradeDetailResp) {

    }

    @Override
    public void onError() {

    }


    public void setOnUpdateAddress(OnUpdateAddress mListener) {
        this.onUpdateAddress = mListener;
    }


    public interface OnUpdateAddress {
        void updateAddress(String address);
    }
}
