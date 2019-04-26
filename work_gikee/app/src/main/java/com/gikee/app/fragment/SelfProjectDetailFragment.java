package com.gikee.app.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.R;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.activity.MPEditTrandActivity;
import com.gikee.app.activity.OwnerDistributeActivity;
import com.gikee.app.activity.ProjectDetailActivity;
import com.gikee.app.activity.SpecialTradeActivity;
import com.gikee.app.activity.Top100CurrencyActivity;
import com.gikee.app.activity.Top100TradeActivity;
import com.gikee.app.activity.TransactionDistributionActivity;
import com.gikee.app.activity.ZhanghuNumActivity;
import com.gikee.app.adapter.ShuJUFenXiAdapter;
import com.gikee.app.adapter.ShuJuFenXiMultipleItem;
import com.gikee.app.datas.Commons;
import com.gikee.app.greendao.SQLiteUtils;
import com.gikee.app.greendao.TrandBean;
import com.gikee.app.presenter.project.ShuJuFenXiPresentetr;
import com.gikee.app.presenter.project.ShuJuFenXiView;
import com.gikee.app.resp.PriceChangeBean;
import com.gikee.app.resp.ProjectCompaResp;
import com.gikee.app.resp.ProjectInfoResp;
import com.gikee.app.resp.SummaryBean;
import com.gikee.app.resp.SummaryResp;
import com.gikee.app.views.MyRefreshHeader;
import com.gikee.app.views.MyTextView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.meetsl.scardview.SCardView;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class SelfProjectDetailFragment extends BaseFragment<ShuJuFenXiPresentetr> implements ShuJuFenXiView {


   // private static volatile SelfProjectDetailFragment selfProjectDetailFragment;

    private String symbol;

    private StringBuffer sb;

    @Bind(R.id.fm_all_shuju_refreshLayout)
    TwinklingRefreshLayout twinklingRefreshLayout;
    @Bind(R.id.fm_all_shuju_recyclerview)
    RecyclerView recyclerView;
    @Bind(R.id.new_price_layout)
    SCardView new_price_layout;
    @Bind(R.id.remind_title)
    TextView remind_title;
    @Bind(R.id.txt_price)
    MyTextView txt_price;


    private ShuJUFenXiAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private List<ShuJuFenXiMultipleItem> list;
    private List<String>  items=new ArrayList<>();//自选时的标签合集
    private String coinSymbol="";
    private List<TrandBean> trandlist = new ArrayList<>();
    public static final int REQUESCODE = 400;
    private Intent intent;
    private  List<SummaryBean> lists;
    private  View footerView;
    private String id;

    public static  SelfProjectDetailFragment getInstance(String symbol,String id,List<String> sb){

        SelfProjectDetailFragment allProjectDetailFragment = new SelfProjectDetailFragment();

        allProjectDetailFragment.setParams(symbol,id,sb);


        return allProjectDetailFragment;

    }


    public void setParams(String symbol,String id,List<String>  sb) {

        this.coinSymbol =  symbol ;

        this.items = sb;

        this.id=id;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void setupViews(LayoutInflater inflater) {



        setContentView(inflater, R.layout.fragment_selfproject);


    }


    @Override
    protected void initView() {
        mPresenter = new ShuJuFenXiPresentetr(this);
        if(Commons.smybl!=null){
            trandlist = SQLiteUtils.getInstance().getTrand(Commons.smybl);
        }
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        MyRefreshHeader headerView = new MyRefreshHeader(getContext());
        twinklingRefreshLayout.setEnableLoadmore(true);
        twinklingRefreshLayout.setEnableOverScroll(false);
        twinklingRefreshLayout.setHeaderView(headerView);

        list = new ArrayList<>();
        adapter = new ShuJUFenXiAdapter(list,getContext());

        footerView = LayoutInflater.from(getContext()).inflate(R.layout.view_nocontent, null);
        ((TextView)(footerView.findViewById(R.id.add_title))).setText(getString(R.string.edittrend));
        footerView.findViewById(R.id.add_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(getContext(),MPEditTrandActivity.class);

                intent.putExtra("chooseType",0);

                startActivityForResult(intent,REQUESCODE);
            }
        });
        adapter.addFooterView(footerView);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        // recyclerView.setNestedScrollingEnabled(false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                switch (adapter.getItemViewType(position)) {
                    case ShuJUFenXiAdapter.lineWave:
                        return 2;
                    case ShuJUFenXiAdapter.table:
                        return 2;
                    case ShuJUFenXiAdapter.pieLeft:
                        return 1;
                    case ShuJUFenXiAdapter.pieRight:
                        return 1;
                }

                return 2;

            }
        });

        initOnClick();

//        if(!TextUtils.isEmpty(price)){
//            new_price_layout.setVisibility(View.VISIBLE);
//
//            txt_price.setText("¥"+price);
//        }

    }

    private void initOnClick() {

        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                remind_title.setVisibility(View.VISIBLE);
                getNetData();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {

                twinklingRefreshLayout.finishLoadmore();

            }
        });




        //点击指标跳转详情
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter ad, View view, int position) {

                if (adapter.getData().get(position).getItemName().equals(Commons.top100)){

                    intent = new Intent(getContext(),Top100CurrencyActivity.class);

                    intent.putExtra("id", coinSymbol);

                    startActivity(intent);

                }else if(adapter.getData().get(position).getItemName().equals(Commons.participateAddress)){

                    intent = new Intent(getContext(),ZhanghuNumActivity.class);

                    intent.putExtra("id", id);

                    intent.putExtra("symbol", coinSymbol);

                    intent.putExtra("title", getString(R.string.participateAddress));

                    intent.putExtra("type", Commons.participateAddress);

                    startActivity(intent);


                }else if(adapter.getData().get(position).getItemName().equals(Commons.ownAddr)){

                    intent = new Intent(getContext(),ZhanghuNumActivity.class);

                    intent.putExtra("id", id);

                    intent.putExtra("symbol", coinSymbol);

                    intent.putExtra("title", getString(R.string.ownAddr_title));

                    intent.putExtra("type", Commons.ownAddr);

                    startActivity(intent);

                }else if(adapter.getData().get(position).getItemName().equals(Commons.avgTrdValue)){

                    intent = new Intent(getContext(),ZhanghuNumActivity.class);

                    intent.putExtra("id", id);

                    intent.putExtra("symbol", coinSymbol);

                    intent.putExtra("title", getString(R.string.avgTrdValue));

                    intent.putExtra("type", Commons.avgTrdValue);

                    startActivity(intent);

                }else if(adapter.getData().get(position).getItemName().equals(Commons.tradeValue)){
                    intent = new Intent(getContext(),ZhanghuNumActivity.class);

                    intent.putExtra("id", id);

                    intent.putExtra("symbol", coinSymbol);

                    intent.putExtra("title", getString(R.string.tradeValue));

                    intent.putExtra("type", Commons.tradeValue);

                    startActivity(intent);

                }else if(adapter.getData().get(position).getItemName().equals(Commons.price)){

                    intent = new Intent(getContext(),ZhanghuNumActivity.class);

                    intent.putExtra("id", id);

                    intent.putExtra("symbol", coinSymbol);

                    intent.putExtra("title", getString(R.string.price_title));

                    intent.putExtra("type", Commons.price);

                    startActivity(intent);

                }else if(adapter.getData().get(position).getItemName().equals(Commons.newAccount)){

                    intent = new Intent(getContext(),ZhanghuNumActivity.class);

                    intent.putExtra("id", id);

                    intent.putExtra("symbol", coinSymbol);

                    intent.putExtra("title", getString(R.string.newCount));

                    intent.putExtra("type", Commons.newAccount);

                    startActivity(intent);

                }else if(adapter.getData().get(position).getItemName().equals(Commons.wakeCount)){

                    intent = new Intent(getContext(),ZhanghuNumActivity.class);

                    intent.putExtra("id", id);

                    intent.putExtra("symbol", coinSymbol);

                    intent.putExtra("title", getString(R.string.wakeCount));

                    intent.putExtra("type", Commons.wakeCount);

                    startActivity(intent);

                }else if(adapter.getData().get(position).getItemName().equals(Commons.inactiveCount)){

                    intent = new Intent(getContext(),ZhanghuNumActivity.class);

                    intent.putExtra("id", id);

                    intent.putExtra("symbol", coinSymbol);

                    intent.putExtra("title", getString(R.string.inactiveCount));

                    intent.putExtra("type", Commons.inactiveCount);

                    startActivity(intent);

                }else if(adapter.getData().get(position).getItemName().equals(Commons.topDis)){

                    intent = new Intent(getContext(),OwnerDistributeActivity.class);

                    intent.putExtra("id", coinSymbol);

                    intent.putExtra("title", getString(R.string.topDis_title));

                    intent.putExtra("type", Commons.topDis);


                    startActivity(intent);

                }else if(adapter.getData().get(position).getItemName().equals(Commons.specialTrade)){

                    intent = new Intent(getContext(),SpecialTradeActivity.class);

                    intent.putExtra("id", coinSymbol);

                    intent.putExtra("title",getString(R.string.specialTrade_title));

                    startActivity(intent);

                }else if(adapter.getData().get(position).getItemName().equals(Commons.activeDis)){

                    intent = new Intent(getContext(),ZhanghuNumActivity.class);

                    intent.putExtra("id", id);

                    intent.putExtra("symbol", coinSymbol);

                    intent.putExtra("title", getString(R.string.activeDis_title));

                    intent.putExtra("type", Commons.activeDis);

                    startActivity(intent);
                    // startActivity(new Intent(context, ZhanghuNumActivity.class).putExtra("id", coinSymbol));
                }else if(adapter.getData().get(position).getItemName().equals(Commons.tradevolume)){

                    intent = new Intent(getContext(),ZhanghuNumActivity.class);

                    intent.putExtra("id", id);

                    intent.putExtra("symbol", coinSymbol);

                    intent.putExtra("title", getString(R.string.tradevolume_title));

                    intent.putExtra("type", Commons.tradevolume);


                    startActivity(intent);

                }else if(adapter.getData().get(position).getItemName().equals(Commons.tradeCount)){

                    intent = new Intent(getContext(),ZhanghuNumActivity.class);

                    intent.putExtra("id", id);

                    intent.putExtra("symbol", coinSymbol);

                    intent.putExtra("title", getString(R.string.tradeCount_title));

                    intent.putExtra("type", Commons.tradeCount);


                    startActivity(intent);

                }else if(adapter.getData().get(position).getItemName().equals(Commons.bigTradeCountDis)){

                    intent = new Intent(getContext(),ZhanghuNumActivity.class);

                    intent.putExtra("id", id);

                    intent.putExtra("symbol", coinSymbol);

                    intent.putExtra("title", getString(R.string.bigTradeCountDis_title));

                    intent.putExtra("type", Commons.bigTradeCountDis);

                    startActivity(intent);



                }else if(adapter.getData().get(position).getItemName().equals(Commons.topFreqAddr)){

                    startActivity(new Intent(getContext(), Top100TradeActivity.class).putExtra("id", coinSymbol));

                }else if(adapter.getData().get(position).getItemName().equals(Commons.trade)){

//                    Intent intent = new Intent(getContext(),TradeDetailActivity.class);
//
//                    intent.putExtra("id", coinSymbol);
//
//                    intent.putExtra("title", MyprojectType.tradehot.getContent());
//
//                    intent.putExtra("type", Commons.trade);
//
//                    startActivity(intent);

                }else if(adapter.getData().get(position).getItemName().equals(Commons.diffPower)){

//                    Intent intent = new Intent(getContext(),TradeDetailActivity.class);
//
//                    intent.putExtra("id", coinSymbol);
//
//                    intent.putExtra("title", getString(R.string.diffPower_title));
//
//                    intent.putExtra("type", Commons.diffPower);
//
//                    startActivity(intent);

                }else if(adapter.getData().get(position).getItemName().equals(Commons.allAddr)){

                    intent = new Intent(getContext(),ZhanghuNumActivity.class);

                    intent.putExtra("id", id);

                    intent.putExtra("symbol", coinSymbol);

                    intent.putExtra("title", getString(R.string.allAddr_title));

                    intent.putExtra("type", Commons.allAddr);

                    startActivity(intent);

                }else if(adapter.getData().get(position).getItemName().equals(Commons.ownAddr)) {

                    intent = new Intent(getContext(), ZhanghuNumActivity.class);

                    intent.putExtra("id", id);

                    intent.putExtra("symbol", coinSymbol);

                    intent.putExtra("title", getString(R.string.ownAddr_title));

                    intent.putExtra("type", Commons.ownAddr);

                    startActivity(intent);

                }else if(adapter.getData().get(position).getItemName().equals(Commons.avgTrdVol)){

                    intent = new Intent(getContext(),ZhanghuNumActivity.class);

                    intent.putExtra("id", id);

                    intent.putExtra("symbol", coinSymbol);

                    intent.putExtra("title",getString(R.string.avgTrdVol_title));

                    intent.putExtra("type", Commons.avgTrdVol);

                    startActivity(intent);

                }else if(adapter.getData().get(position).getItemName().equals(Commons.allGas)){

                    intent = new Intent(getContext(),ZhanghuNumActivity.class);

                    intent.putExtra("id", id);

                    intent.putExtra("symbol", coinSymbol);

                    intent.putExtra("title",getString(R.string.allGas_title));

                    intent.putExtra("type", Commons.allGas);

                    startActivity(intent);

                }else if(adapter.getData().get(position).getItemName().equals(Commons.avgGas)){

                    intent = new Intent(getContext(),ZhanghuNumActivity.class);

                    intent.putExtra("id", id);

                    intent.putExtra("symbol", coinSymbol);

                    intent.putExtra("title",getString(R.string.avgGas_title));

                    intent.putExtra("type", Commons.avgGas);

                    startActivity(intent);

                }else if(adapter.getData().get(position).getItemName().equals(Commons.marketValue)){

                    intent = new Intent(getContext(),ZhanghuNumActivity.class);

                    intent.putExtra("id", id);

                    intent.putExtra("symbol", coinSymbol);

                    intent.putExtra("title",getString(R.string.marketValue_title));

                    intent.putExtra("type", Commons.marketValue);

                    startActivity(intent);

                }else if(adapter.getData().get(position).getItemName().equals(Commons.tradeVolDis)){

                    intent = new Intent(getContext(),TransactionDistributionActivity.class);

                    intent.putExtra("id", coinSymbol);

                    intent.putExtra("title",getString(R.string.tradeVolDis_title));

                    intent.putExtra("type", Commons.tradeVolDis);

                    startActivity(intent);

                }else if(adapter.getData().get(position).getItemName().equals(Commons.tradeCountDis)){

                    intent = new Intent(getContext(),TransactionDistributionActivity.class);

                    intent.putExtra("id", coinSymbol);

                    intent.putExtra("title", getString(R.string.tradeCountDis_title));

                    intent.putExtra("type", Commons.tradeCountDis);

                    startActivity(intent);

                }

                intent = null;

            }
        });
    }


    public void getNetData() {



        if(mPresenter!=null){
            mPresenter.getAllIndex(id);
        }

    }

    @Override
    protected void lazyLoad() {

        if(isViewCreated&&isUIVisible){
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUESCODE){
            //自选参数改变，刷新界面
            //  currentindex=0;
            items.clear();
            trandlist = SQLiteUtils.getInstance().getTrand(Commons.smybl);
            if(trandlist.size()>0){
                for(int j=0;j<trandlist.size();j++){
                    if(trandlist.get(j).getIscollect()){
                        items.add(trandlist.get(j).getTrandid());
                    }

                }
            }
            twinklingRefreshLayout.startRefresh();

        }else
            UMShareAPI.get(getContext()).onActivityResult(requestCode, resultCode, data);

    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PriceChangeBean event) {

        if(event.getSymbol().toLowerCase().contains(coinSymbol.toLowerCase())){

                new_price_layout.setVisibility(View.VISIBLE);

                 txt_price.setText("¥"+event.getPrice());

        }

    }

    @Override
    public void onShuJuFenXi(SummaryResp shuJuFenXiBean) {

        twinklingRefreshLayout.finishRefreshing();

        remind_title.setVisibility(View.GONE);



        if(shuJuFenXiBean.getResult()!=null){

            List<SummaryBean> summaryBeanList = shuJuFenXiBean.getResult().getData();

            if(summaryBeanList!=null){

            //需要做的排序。
            List<SummaryBean> summaryBeanList1 = new ArrayList<>();

            for(int i=0;i<summaryBeanList.size();i++){

                summaryBeanList1.add(null);

            }
            //按自选进行排序
            for(int i=0;i<summaryBeanList.size();i++){
                for(int j=0;j<trandlist.size();j++){
                    if(summaryBeanList.get(i).getItemName().equals(trandlist.get(j).getTrandid())){
                        if(trandlist.get(j).getIscollect()){
                            summaryBeanList1.set(j,summaryBeanList.get(i));
                        }
                    }
                }
            }

            lists = new ArrayList<>();

            for(int i=0;i<summaryBeanList1.size();i++){
                if(summaryBeanList1.get(i)!=null){
                    lists.add(summaryBeanList1.get(i));
                }

            }


            if(lists.size()>0) {

                // if(recyclerView.getScrollState() == 0) {
                adapter.setNewData(lists);
                adapter.notifyDataSetChanged();
                // }

                //PreferenceUtil.putListData(coinSymbol,lists);
            }else{
                adapter.getData().clear();
                adapter.notifyDataSetChanged();
                ToastUtils.show(getString(R.string.nodata));
            }
            }


        }else
            ToastUtils.show(getString(R.string.nodata));

    }

    @Override
    public void onIndexContrast(SummaryResp shuJuFenXiBean) {

    }

    @Override
    public void onProjectInfo(ProjectInfoResp projectInfoResp) {

    }

    @Override
    public void onProjectCompar(ProjectCompaResp projectCompaResp) {

    }

    @Override
    public void onError() {

    }
}
