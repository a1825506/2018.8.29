package com.gikee.app.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gikee.app.R;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.adapter.TokenListAdapter;
import com.gikee.app.beans.AddressDetailBean;
import com.gikee.app.beans.TokendBean;
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

public class ETHTokenDetailFragment extends BaseFragment<AddressDetailPresenter> implements AddressDetailView {

    @Bind(R.id.assetlist_recycle)
    RecyclerView recyclerView;
    @Bind(R.id.address_recycle_layout)
    TwinklingRefreshLayout twinklingRefreshLayout;
    @Bind(R.id.layout)
    RelativeLayout layout;
    private TokenListAdapter tokenBalanceAdapter;

    private View footerView;

    private TextView foottext;


    private String params="";

    private String type;

    private OnUpdateTken onUpdateTken;


    private List<TokendBean> tokendBeanList = new ArrayList<>();

    private static volatile ETHTokenDetailFragment baseLineFragment;


    public static ETHTokenDetailFragment getInstance() {

        if (baseLineFragment == null) {

            synchronized (ETHAddressDetailFragment.class) {

                if (baseLineFragment == null) {

                    baseLineFragment = new ETHTokenDetailFragment();


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


    public void setAdapter(List<TokendBean> tokendBeanList){

        twinklingRefreshLayout.finishRefreshing();
        if(tokendBeanList!=null){
            if(tokendBeanList.size()!=0){
                tokenBalanceAdapter.setNewData(tokendBeanList);
            }else{
                if(getContext()!=null)
                    ToastUtils.show(getContext().getString(R.string.nodata));
            }

        }else
        if(getContext()!=null)
            ToastUtils.show(getContext().getString(R.string.nodata));


    }





    @Override
    protected void setupViews(LayoutInflater inflater) {
        setContentView(inflater, R.layout.activity_ethassetlist);
    }

    @Override
    protected void initView() {
        mPresenter = new AddressDetailPresenter(this);
        tokenBalanceAdapter = new TokenListAdapter();

        MyRefreshHeader headerView = new MyRefreshHeader(getContext());
        MyRefreshBottom bottomView = new MyRefreshBottom(getContext());

        twinklingRefreshLayout.setBottomView(bottomView);
        twinklingRefreshLayout.setAutoLoadMore(true);
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setEnableLoadmore(true);

        footerView = LayoutInflater.from(getContext()).inflate(R.layout.view_myproject_footer, null);

        foottext=footerView.findViewById(R.id.myproject_footer_add);

        foottext.setText("");

        tokenBalanceAdapter.addFooterView(footerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(tokenBalanceAdapter);

        twinklingRefreshLayout.startRefresh();

        initOnClick();

        if(!type.equals("ETH")){
            layout.setVisibility(View.GONE);
        }


    }

    private void initOnClick() {

        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                onUpdateTken.updateTokes();
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


        twinklingRefreshLayout.finishRefreshing();

        if (TextUtils.isEmpty(addressDetailResp.getErrInfo())) {

            footerView.setVisibility(View.VISIBLE);

            tokendBeanList.clear();

            if (addressDetailResp.getResult() != null) {

                AddressDetailBean addressDetailBean = addressDetailResp.getResult();

                TokendBean tokendBean = new TokendBean();

                tokendBean.setTitle("Ethereum");

                tokendBean.setValue(addressDetailBean.getBalance());

                tokendBeanList.add(tokendBean);

                for(int i=0;i<addressDetailBean.getToken_detail().size();i++){
                    tokendBeanList.add(addressDetailBean.getToken_detail().get(i));
                }

                tokenBalanceAdapter.setNewData(tokendBeanList);

            }
        }else{

            ToastUtils.show(getString(R.string.nodata));
        }

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
    public void onEOSTradeDetail(EOSTradeDetailResp eosTradeDetailResp) {

    }

    @Override
    public void onError() {

    }


    public void setOnUpdateToken(OnUpdateTken mListener) {
        this.onUpdateTken = mListener;
    }


    public interface OnUpdateTken {
        void updateTokes();
    }
}
