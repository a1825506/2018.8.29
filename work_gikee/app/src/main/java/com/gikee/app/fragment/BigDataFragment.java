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
import com.gikee.app.activity.SpecialTradeDetailActivity;
import com.gikee.app.activity.TradeListActivty;
import com.gikee.app.adapter.SpecialTradeAdapter;
import com.gikee.app.beans.FrequenTradeResp;
import com.gikee.app.presenter.mineaddress.SpecialAddressPresenter;
import com.gikee.app.presenter.mineaddress.SpecialAddressView;
import com.gikee.app.resp.SpecialAddressResp;
import com.gikee.app.views.MyRefreshHeader;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import butterknife.Bind;


public class BigDataFragment extends BaseFragment <SpecialAddressPresenter> implements SpecialAddressView {

    @Bind(R.id.special_recycle)
    RecyclerView specialrecycle;
    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @Bind(R.id.nodata)
    TextView nocontext_layout;

    private SpecialTradeAdapter specialTradeAdapter;


    private SpecialAddressPresenter mPresenter;

    private String symbol="XNK";

    private int start=0;

    private int limit=15;

    private String starDate;

    private String endDate;

    private  View footerView;

    private TextView foottext;



    public static BigDataFragment getInstance(String symbol,int start,int limit,String starDate,String endDate){

        BigDataFragment specialTradeFragment = new BigDataFragment();

        specialTradeFragment.setParam(symbol,start,limit,starDate,endDate);

        return specialTradeFragment;


    }

    private void  setParam(String symbol,int start,int limit,String starDate,String endDate){

        this.symbol = symbol;

        this.start = start;

        this.limit = limit;

        this.starDate = starDate;

        this.endDate = endDate;
    }



    @Override
    protected void setupViews(LayoutInflater inflater) {
        setContentView(inflater, R.layout.fragment_specialtrade);
    }

    @Override
    protected void initView() {

        MyRefreshHeader headerView = new MyRefreshHeader(getContext());
        refreshLayout.setAutoLoadMore(false);
        refreshLayout.setHeaderView(headerView);
        refreshLayout.setEnableLoadmore(true);

        mPresenter = new SpecialAddressPresenter(this);

        specialTradeAdapter = new SpecialTradeAdapter();

        specialrecycle.setLayoutManager(new LinearLayoutManager(getContext()));

        specialrecycle.setAdapter(specialTradeAdapter);

        footerView = LayoutInflater.from(getContext()).inflate(R.layout.view_myproject_footer, null);

        foottext=footerView.findViewById(R.id.myproject_footer_add);

        foottext.setText(R.string.loadmore);

        foottext.setVisibility(View.GONE);

        specialTradeAdapter.addFooterView(footerView);

        specialTradeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {



            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {


               if(!TextUtils.isEmpty(specialTradeAdapter.getData().get(position).getFrom())&&TextUtils.isEmpty(specialTradeAdapter.getData().get(position).getTo())){

                   Intent intent = new Intent(getContext(),TradeListActivty.class);

                   intent.putExtra("address",specialTradeAdapter.getData().get(position).getFrom());

                   intent.putExtra("type",2);

                   startActivity(intent);

               } else if(TextUtils.isEmpty(specialTradeAdapter.getData().get(position).getFrom())&&!TextUtils.isEmpty(specialTradeAdapter.getData().get(position).getTo())){


                   Intent intent = new Intent(getContext(),TradeListActivty.class);

                   intent.putExtra("address",specialTradeAdapter.getData().get(position).getTo());

                   intent.putExtra("type",3);

                   startActivity(intent);
               }else{

                   Intent intent =new Intent(getContext(), SpecialTradeDetailActivity.class);

                   intent.putExtra("txHash",specialTradeAdapter.getData().get(position).getTxHash());

                   startActivity(intent);
               }



            }
        });



        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {

                start=0;

                getData();

                footerView.setVisibility(View.GONE);


                specialTradeAdapter.getData().clear();

                specialTradeAdapter.notifyDataSetChanged();

            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {

                footerView.setVisibility(View.GONE);

                getData();
            }
        });
       // refreshLayout.startRefresh();

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

    private void getData() {


        mPresenter.getBigData(symbol,start,limit,starDate,endDate);
    }


    @Override
    public void onSpecialAddress(SpecialAddressResp specialAddressBean) {




    }

    @Override
    public void onSpecialList(SpecialAddressResp specialAddressBean) {

    }

    @Override
    public void onBigData(SpecialAddressResp specialAddressBean) {

        if(specialAddressBean.getResult()!=null){

            nocontext_layout.setVisibility(View.GONE);

            if(specialAddressBean.getResult().getData().size()!=0){

                int size = specialAddressBean.getResult().getData().size();

                footerView.setVisibility(View.VISIBLE);

                if(size<=limit){

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
    public void onFrequentlyTrade(FrequenTradeResp specialAddressBean) {

    }
}