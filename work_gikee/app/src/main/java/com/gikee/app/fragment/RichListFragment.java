package com.gikee.app.fragment;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.R;
import com.gikee.app.activity.AddressDetailActivity;
import com.gikee.app.activity.BTCAddressDetailActivity;
import com.gikee.app.activity.ETHAddressDetailActivity;
import com.gikee.app.activity.ProjectDetailActivity;
import com.gikee.app.activity.TopPlayerActivity;
import com.gikee.app.adapter.TopPlayerAdapter;
import com.gikee.app.datas.Commons;
import com.gikee.app.presenter.home.HomePresenter;
import com.gikee.app.presenter.home.HomeView;
import com.gikee.app.resp.MarketRateResp;
import com.gikee.app.resp.PowerResp;
import com.gikee.app.resp.SummaryResp;
import com.gikee.app.resp.Top100Bean;
import com.gikee.app.resp.TopFreqAddrResp;
import com.gikee.app.resp.ValueResp;
import com.gikee.app.views.MyRefreshBottom;
import com.gikee.app.views.MyRefreshHeader;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class RichListFragment extends BaseFragment<HomePresenter> implements HomeView {

    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    @Bind(R.id.nodata)
    TextView nodata;
    @Bind(R.id.moredata)
    TextView moredata;
    @Bind(R.id.collect_rg)
    RadioGroup collect_rg;
    @Bind(R.id.btc)
    RadioButton btc_btn;
    @Bind(R.id.eth)
    RadioButton eth_btn;
    @Bind(R.id.eos)
    RadioButton eos_btn;


    Intent intent=null;

    private TopPlayerAdapter topPlayerAdapter;

    private String id = "bitcoin";

    private String symbol="BTC";

    @Override
    protected void setupViews(LayoutInflater inflater) {

        setContentView(inflater, R.layout.fragment_rich);

    }

    @Override
    protected void initView() {

        mPresenter = new HomePresenter(this);


        MyRefreshHeader headerView = new MyRefreshHeader(getContext());
        MyRefreshBottom bottomView = new MyRefreshBottom(getContext());

        refreshLayout.setAutoLoadMore(false);
        refreshLayout.setHeaderView(headerView);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setBottomView(bottomView);


        topPlayerAdapter = new TopPlayerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration divider = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);

        divider.setDrawable(ContextCompat.getDrawable(getContext(),R.color.linewave1));


        recyclerView.addItemDecoration(divider);

        recyclerView.setAdapter(topPlayerAdapter);


        onClick();


    }

    private void onClick() {

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                getNetData();
            }


        });

        moredata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getContext().startActivity(new Intent(getContext(), ProjectDetailActivity.class)
                        .putExtra("symbol", symbol)
                        .putExtra("id", Commons.id)
                        .putExtra("index",2));


            }
        });



        topPlayerAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(symbol.equals("BTC")){
                    intent = new Intent(getContext(), BTCAddressDetailActivity.class);

                    intent.putExtra("type","BTC");

                    intent.putExtra("paramstype","address");

                    intent.putExtra("address",topPlayerAdapter.getData().get(position).getAddress());

                    startActivity(intent);
                }else if(symbol.equals("EOS")) {
                    intent = new Intent(getContext(), AddressDetailActivity.class);

                    intent.putExtra("type",symbol);

                    intent.putExtra("paramstype","address");

                    intent.putExtra("address",topPlayerAdapter.getData().get(position).getAddress());

                    startActivity(intent);
                }else{
                    intent = new Intent(getContext(), ETHAddressDetailActivity.class);

                    intent.putExtra("type","ETH");

                    intent.putExtra("paramstype","address");

                    intent.putExtra("address",topPlayerAdapter.getData().get(position).getAddress());

                    startActivity(intent);
                }
            }
        });

        collect_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.btc:
                        id="bitcoin";
                        symbol = "BTC";
                        btc_btn.setBackgroundResource(R.drawable.sharp_btn_project);
                        btc_btn.setTextColor(getResources().getColor(R.color.white));
                        eth_btn.setBackgroundResource(R.drawable.shape_btn_nom);
                        eth_btn.setTextColor(getResources().getColor(R.color.gray_33));
                        eos_btn.setBackgroundResource(R.drawable.sharp_btn_addressnormal);
                        eos_btn.setTextColor(getResources().getColor(R.color.gray_33));
                        refreshLayout.startRefresh();
                        break;
                    case R.id.eth:
                        id="ethereum";
                        symbol = "ETH";
                        btc_btn.setBackgroundResource(R.drawable.shape_btn_leftline);
                        btc_btn.setTextColor(getResources().getColor(R.color.gray_33));
                        eth_btn.setBackgroundResource(R.drawable.shape_btn_press);
                        eth_btn.setTextColor(getResources().getColor(R.color.white));
                        eos_btn.setBackgroundResource(R.drawable.sharp_btn_rightline);
                        eos_btn.setTextColor(getResources().getColor(R.color.gray_33));
                        refreshLayout.startRefresh();
                        break;
                    case R.id.eos:
                        id="eos";
                        symbol = "EOS";
                        btc_btn.setBackgroundResource(R.drawable.sharp_btn_project_normal);
                        btc_btn.setTextColor(getResources().getColor(R.color.gray_33));
                        eth_btn.setBackgroundResource(R.drawable.shape_btn_nom);
                        eth_btn.setTextColor(getResources().getColor(R.color.gray_33));
                        eos_btn.setBackgroundResource(R.drawable.sharp_btn_address);
                        eos_btn.setTextColor(getResources().getColor(R.color.white));
                        refreshLayout.startRefresh();
                        break;
                    default:
                        refreshLayout.startRefresh();
                }
            }
        });
    }

    @Override
    protected void lazyLoad() {

        if(isViewCreated&&isUIVisible) {
            refreshLayout.startRefresh();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }

    }

    @Override
    protected void onChangeEvent(int type) {

    }

    private void getNetData() {

        Commons.id=id;

        mPresenter.TopPlayer(id);
    }

    @Override
    public void onTopPlayer(TopFreqAddrResp resp) {

        refreshLayout.finishRefreshing();
        topPlayerAdapter.getData().clear();
        if(TextUtils.isEmpty(resp.getErrInfo())){

            if(resp.getResult().getData().size()>0){


                List<Top100Bean>  top100BeanList = new ArrayList<>();

                for(int i=0;i<10;i++){

                    Top100Bean top100Bean = resp.getResult().getData().get(i);

                    top100BeanList.add(top100Bean);

                }

                nodata.setVisibility(View.GONE);

                topPlayerAdapter.setNewData(top100BeanList);

                moredata.setVisibility(View.VISIBLE);

            }else
                nodata.setVisibility(View.VISIBLE);



        }else{
            nodata.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onMarketRate(MarketRateResp marketRateResp) {

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
    public void onError() {

    }
}
