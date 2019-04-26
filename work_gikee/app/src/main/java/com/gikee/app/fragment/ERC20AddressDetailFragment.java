package com.gikee.app.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.R;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.activity.AddressDetailActivity;
import com.gikee.app.activity.TradeDetailActivity;
import com.gikee.app.adapter.AddressDetailAdapter;
import com.gikee.app.beans.AddressDetailBean;
import com.gikee.app.beans.ERC20Bean;
import com.gikee.app.beans.HashTradeBean;
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

public class ERC20AddressDetailFragment extends BaseFragment<AddressDetailPresenter> implements AddressDetailView {


    @Bind(R.id.address_recycle_layout)
    TwinklingRefreshLayout twinklingRefreshLayout;
    @Bind(R.id.address_recycle)
    RecyclerView recyclerView;

    private AddressDetailAdapter tradeadapter;

    private OnUpdateAddress onUpdateAddress;

    public  static  final int REQUESCODE = 400;

    private String type="erc20";

    private String params="";

    private String paramstype = "address";

    private View footerView;

    private TextView foottext;

    private int page = 1;

    private int limit=25;

    private Intent intent;

    private int totalTrade=0;

    private static volatile ERC20AddressDetailFragment baseLineFragment;

    List<HashTradeBean> hash_detail = new ArrayList<>();

    private  boolean isloadMore=false;


    @Override
    protected void setupViews(LayoutInflater inflater) {

        setContentView(inflater, R.layout.fragment_ethaddress_detail);

    }

    public static ERC20AddressDetailFragment getInstance() {

        if (baseLineFragment == null) {

            synchronized (ERC20AddressDetailFragment.class) {

                if (baseLineFragment == null) {

                    baseLineFragment = new ERC20AddressDetailFragment();


                }

            }

        }

        return baseLineFragment;

    }


    public void setParams(String type,String param) {

        this.params =  param ;

//        this.type = type;

        Commons.BASELINE = type;

        Commons.ETH_ADDRESS.clear();
        Commons.ETH_ADDRESS.add(params);

    }


    public void setAdapter(List<HashTradeBean> hash_detail){

        twinklingRefreshLayout.finishRefreshing();
        tradeadapter.getData().clear();
        tradeadapter.notifyDataSetChanged();

        if(hash_detail!=null){
            if(hash_detail.size()!=0){
                tradeadapter.addData(hash_detail);


            }else{
                if(getContext()!=null)
                    ToastUtils.show(getContext().getString(R.string.nodata));
            }

        }else
        if(getContext()!=null)
            ToastUtils.show(getContext().getString(R.string.nodata));


    }






    @Override
    protected void initView() {
        mPresenter = new AddressDetailPresenter(this);
        MyRefreshHeader headerView = new MyRefreshHeader(getContext());
        MyRefreshBottom bottomView = new MyRefreshBottom(getContext());

        twinklingRefreshLayout.setBottomView(bottomView);
        twinklingRefreshLayout.setAutoLoadMore(true);
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setBottomView(bottomView);
        twinklingRefreshLayout.setEnableLoadmore(true);

        tradeadapter = new AddressDetailAdapter(params);

        footerView = LayoutInflater.from(getContext()).inflate(R.layout.view_myproject_footer, null);

        foottext=footerView.findViewById(R.id.myproject_footer_add);

        foottext.setText("");

        tradeadapter.addFooterView(footerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(tradeadapter);

        initOnClick();
    }

    private void initOnClick() {

        tradeadapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                intent = new Intent(getContext(),TradeDetailActivity.class);

                intent.putExtra("type","ETH");

                intent.putExtra("hash",tradeadapter.getData().get(position).getTradehash());

                intent.putExtra("jumptype",0);

                intent.putExtra("trade_type", "erc20");

                startActivityForResult(intent,REQUESCODE);
            }
        });

        tradeadapter.setIOnItemClick(new AddressDetailAdapter.IOnItemClick() {
            @Override
            public void onItemClick(String address) {

                onUpdateAddress.updateAddress(address);

            }
        });

        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page=1;
                isloadMore=false;
                getData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (page * limit <= totalTrade) {
                    page++;
                    isloadMore=true;
                    getData();
                } else{
                    twinklingRefreshLayout.finishLoadmore();
                    foottext.setText(R.string.nomoredata);
                }
            }
        });


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




    private void getData() {

        if(mPresenter!=null){
//            mPresenter.getAddressDetail(type,paramstype,params);

            mPresenter.getAddressTrans(params,type,page);
        }


    }

    @Override
    protected void lazyLoad() {

        if (isViewCreated && isUIVisible) {

            twinklingRefreshLayout.startRefresh();

            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;

        }
    }

    @Override
    protected void onChangeEvent(int type) {

    }


    @Override
    public void onAddressDetail(AddressDetailResp addressDetailResp) {



    }

    @Override
    public void onTradeList(HashTradeResp hashTradeResp) {



    }

    @Override
    public void onAddressTrans(ERC20ListResp resp) {

        twinklingRefreshLayout.finishRefreshing();

        twinklingRefreshLayout.finishLoadmore();

        // if(TextUtils.isEmpty(hashTradeResp.getErrInfo())){

        if(resp.getResult()!=null) {

            if (resp.getResult().getData()!=null){

                if (resp.getResult().getData().size() != 0) {

                    List<ERC20Bean> erc20BeanList = resp.getResult().getData();

                    totalTrade =Integer.parseInt(resp.getResult().getTotal_count());


                    List<HashTradeBean> hashTradeBeanList = new ArrayList<>();

                    for(int i=0;i<erc20BeanList.size();i++){

                        HashTradeBean hashTradeBean = new HashTradeBean();

                        hashTradeBean.setFrom(erc20BeanList.get(i).getFrom());

                        hashTradeBean.setTo(erc20BeanList.get(i).getTo());

                        hashTradeBean.setDirect(erc20BeanList.get(i).getDirect());

                        hashTradeBean.setTime(erc20BeanList.get(i).getTradetime());

                        hashTradeBean.setTradehash(erc20BeanList.get(i).getTradehash());

                        hashTradeBean.setAmount(erc20BeanList.get(i).getValue());

                        hashTradeBean.setType("ERC20");

                        hashTradeBean.setLogo(erc20BeanList.get(i).getLogo());

                        hashTradeBean.setSymbol(erc20BeanList.get(i).getSymbol());

                        hashTradeBeanList.add(hashTradeBean);

                    }

                    if(isloadMore){
                        tradeadapter.addData(hashTradeBeanList);
                    }else
                        tradeadapter.setNewData(hashTradeBeanList);

                }else
                    foottext.setText(R.string.noerc20traderecord);
            }
        }
        // }else
        //  ToastUtils.show(getString(R.string.nodata));

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

    public void setOnUpdateAddress(OnUpdateAddress mListener) {
        this.onUpdateAddress = mListener;
    }


    public interface OnUpdateAddress {
        void updateAddress(String address);
    }

}
