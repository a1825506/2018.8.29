package com.gikee.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.Observer.CollectObserverService;
import com.gikee.app.Observer.base_observe.ConstObserver;
import com.gikee.app.R;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.adapter.SpecialAccountListAdapter;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.beans.AddressAddedBean;
import com.gikee.app.beans.SpecialAccountBean;
import com.gikee.app.greendao.CollectBean;
import com.gikee.app.greendao.SQLiteUtils;
import com.gikee.app.presenter.search.SpecialSearchPresenter;
import com.gikee.app.presenter.search.SpecialSearchView;
import com.gikee.app.resp.AddressAddedResp;
import com.gikee.app.resp.MonitorTradeResp;
import com.gikee.app.resp.SpecialAccountResp;
import com.gikee.app.views.MyRefreshBottom;
import com.gikee.app.views.MyRefreshHeader;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 交易所账户列表页
 */
public class ExchangeAccountActivity extends BaseActivity<SpecialSearchPresenter> implements SpecialSearchView {


    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout twinklingRefreshLayout;

    @Bind(R.id.recycleview)
    RecyclerView search_recycleview;

    @Bind(R.id.choose_all)
    ImageView choose_all;

    @Bind(R.id.choose_title)
    TextView choose_title;

    @Bind(R.id.choose_layout)
    LinearLayout choose_layout;

    @Bind(R.id.bottom_layout)
    RelativeLayout  bottom_layout;


    private String symbol,context,coin;

    private boolean chooseall=false;

    private CollectBean collectBean;

    private SpecialAccountListAdapter specialAccountListAdapter;

    List<SpecialAccountBean> collectBeanList;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_account);
    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @Override
    protected void setUpView() {

        setTitleColor(R.color.black);

        hideRightCollect(View.GONE);

        mPresenter = new SpecialSearchPresenter(this);

        symbol = getIntent().getStringExtra("symbol");

        context = getIntent().getStringExtra("context");

        coin= getIntent().getStringExtra("coin");

        setTitle(symbol+getString(R.string.exchange_account));

        MyRefreshHeader headerView = new MyRefreshHeader(ExchangeAccountActivity.this);
        MyRefreshBottom bottomView = new MyRefreshBottom(ExchangeAccountActivity.this);
        twinklingRefreshLayout.setAutoLoadMore(true);
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setEnableLoadmore(true );
        twinklingRefreshLayout.setBottomView(bottomView);

        specialAccountListAdapter = new SpecialAccountListAdapter();

        search_recycleview.setLayoutManager(new LinearLayoutManager(this));

        search_recycleview.setAdapter(specialAccountListAdapter);

        twinklingRefreshLayout.startRefresh();
    }

    @Override
    protected void initOnclick() {

        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {

                getData();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {

                refreshLayout.finishLoadmore();

            }
        });

        specialAccountListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(specialAccountListAdapter.getData().get(position).isChoose()){
                    specialAccountListAdapter.getData().get(position).setChoose(false);
                }else
                    specialAccountListAdapter.getData().get(position).setChoose(true);

                specialAccountListAdapter.notifyItemChanged(position);

                checkChooseCount();
            }
        });

        choose_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count=0;
                double balance=0;
                for(int i=0;i<specialAccountListAdapter.getData().size();i++){
                    if(specialAccountListAdapter.getData().get(i).isChoose()){
                        count++;
                        String addressid = specialAccountListAdapter.getData().get(i).getAddress().get(0);
                        String logo = specialAccountListAdapter.getData().get(i).getLogo();
                        String coin = specialAccountListAdapter.getData().get(i).getCoin();
                        balance = balance+specialAccountListAdapter.getData().get(i).getBalance();

                        String tag = "exchange";
                        if (!SQLiteUtils.getInstance().isAddressCollect(addressid)) {
                            List<CollectBean> collectBeanList=SQLiteUtils.getInstance().selectAllContacts("address");
                            collectBean = new CollectBean();
                            collectBean.setName(getString(R.string.my_address)+collectBeanList.size());
                            collectBean.setAddressid(addressid);
                            collectBean.setType("address");
                            collectBean.setLogo(logo);
                            collectBean.setDetail(tag);
                            collectBean.setAddress_type(coin);
                            collectBean.setTag(tag+symbol+coin);
                            collectBean.setIscollect(true);
                            SQLiteUtils.getInstance().addContacts(collectBean);
                        }

                    }
                }


               //合并监控
                if (!SQLiteUtils.getInstance().isAddressCollect("exchange"+symbol+coin)) {
                    collectBean = new CollectBean();
                    collectBean.setName(symbol+getString(R.string.exchange));
                    collectBean.setAddressid("exchange"+symbol+coin);
                    collectBean.setType("address");
                    collectBean.setTag(symbol+coin);
                    collectBean.setDetail(symbol);
                    collectBean.setBalance(String.valueOf(balance));
                    collectBean.setCount(count+"");
                    collectBean.setLogo(specialAccountListAdapter.getData().get(0).getLogo());
                    collectBean.setAddress_type(coin);
                    collectBean.setIscollect(true);
                    SQLiteUtils.getInstance().addContacts(collectBean);
                    CollectObserverService.getInstance().notifyDataChanged(ConstObserver.COLLECT_ADDRESS_CHANGE);

                }
                finish();





            }
        });

        choose_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                chooseOnclick();
            }
        });

    }

    private void checkChooseCount() {

        boolean showBtn=false;

        for(int i=0;i<specialAccountListAdapter.getData().size();i++){

            if(specialAccountListAdapter.getData().get(i).isChoose()){
                showBtn=true;
            }
        }

        if(showBtn){
            choose_title.setVisibility(View.VISIBLE);
        }else
            choose_title.setVisibility(View.GONE);
    }

    private void chooseOnclick() {

        if(!chooseall){
            chooseall=true;
            Glide.with(ExchangeAccountActivity.this).load(getResources().getDrawable(R.mipmap.choose)).into((ImageView)choose_all);
            updateItem(true);
            choose_title.setVisibility(View.VISIBLE);

        }else{
            chooseall=false;
            Glide.with(ExchangeAccountActivity.this).load(getResources().getDrawable(R.mipmap.no_choose)).into((ImageView) choose_all);
            updateItem(false);
            choose_title.setVisibility(View.GONE);
        }
    }

    private void updateItem(boolean b) {

        for(int i=0;i<specialAccountListAdapter.getData().size();i++){
            specialAccountListAdapter.getData().get(i).setChoose(b);
            specialAccountListAdapter.notifyItemChanged(i);
        }
    }

    private void getData() {

//        mPresenter.getSpecialAccountList(context,symbol,coin);
    }

    @Override
    public void onSpecialSearchView(SpecialAccountResp resp) {

    }

    @Override
    public void onAllAccount(SpecialAccountResp resp) {

    }

//    @Override
//    public void onSpecialAccountList(SpecialAccountResp resp) {
//
//
//
//        if(TextUtils.isEmpty(resp.getErrInfo())){
//
//            if(resp.getResult().getData()!=null){
//
//                collectBeanList=resp.getResult().getData();
//
//                getBalance(collectBeanList);
//
//            }
//
//        }
//
//
//
//    }

    private void getBalance(List<SpecialAccountBean> collectBeanList) {

        Map<String,List<String>> map = new HashMap<String,List<String>>();

        List<String> eth_list  = new ArrayList<>();
        List<String> eos_list  = new ArrayList<>();
        List<String> btc_list  = new ArrayList<>();

        for(int i=0;i<collectBeanList.size();i++){

            SpecialAccountBean collectBean =  collectBeanList.get(i);

            if("eth".equals(coin)){
                eth_list.add(collectBean.getAddress().get(0));
            }else if("btc".equals(coin)){
                btc_list.add(collectBean.getAddress().get(0));
            }else if("eos".equals(coin)){
                eos_list.add(collectBean.getAddress().get(0));
            }
        }

        if(eth_list.size()>0){

            map.put("eth",eth_list);

        }

        if(btc_list.size()>0){

            map.put("btc",btc_list);

        }

        if(eos_list.size()>0){

            map.put("eos",eos_list);
        }


        mPresenter.getMineAddress(map);
    }

    @Override
    public void onMineAddress(AddressAddedResp resp) {

        twinklingRefreshLayout.finishRefreshing();

        if(TextUtils.isEmpty(resp.getErrInfo())){

            if(resp.getData()!=null){

                if(resp.getData().size()!=0){

                    List<AddressAddedBean> addressAddedBeanList = resp.getData();

                    for(SpecialAccountBean collectBean:collectBeanList){

                        for(AddressAddedBean addressAddedBean:addressAddedBeanList){

                            if(collectBean.getAddress().equals(addressAddedBean.getAddress())){

                                collectBean.setBalance(addressAddedBean.getBalance());

                            }

                        }

                    }



                }

            }

        }

        specialAccountListAdapter.setNewData(collectBeanList);

        bottom_layout.setVisibility(View.VISIBLE);

    }

    @Override
    public void onMonitorTrade(MonitorTradeResp resp) {

    }

    @Override
    public void onError() {

    }
}
