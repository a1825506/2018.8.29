package com.gikee.app.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.R;
import com.gikee.app.activity.OwnerDistributeActivity;
import com.gikee.app.activity.ProjectDetailActivity;
import com.gikee.app.activity.SpecialTradeActivity;
import com.gikee.app.activity.Top100CurrencyActivity;
import com.gikee.app.activity.Top100TradeActivity;
import com.gikee.app.activity.TransactionDistributionActivity;
import com.gikee.app.activity.ZhanghuNumActivity;
import com.gikee.app.adapter.ShuJUFenXiAdapter;
import com.gikee.app.adapter.ShuJuFenXiMultipleItem;
import com.gikee.app.beans.ProjectInfoBean;
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
import com.gikee.app.views.MyRefreshBottom;
import com.gikee.app.views.MyRefreshHeader;
import com.gikee.app.views.MyTextView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.meetsl.scardview.SCardView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class AllProjectDetailFragment extends BaseFragment<ShuJuFenXiPresentetr> implements ShuJuFenXiView {

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
    private String coinSymbol;
    private String id;
    public static final int REQUESCODE = 400;
    private Intent  intent;
    private  View footerView;

    private static List<SummaryBean> summaryBeanList;
    private TextView foottext;
    private  TrandBean trandBean;
    private OnUpdateTablayout onUpdateTablayout1;

    public static AllProjectDetailFragment getInstance(String symbol,String id){

        AllProjectDetailFragment allProjectDetailFragment = new AllProjectDetailFragment();

        allProjectDetailFragment.setParams(symbol,id);

        return allProjectDetailFragment;

    }

    public void setParams(String symbol,String id){
        this.coinSymbol = symbol;
        this.id = id;

    }


    @Override
    protected void setupViews(LayoutInflater inflater) {
        setContentView(inflater, R.layout.fragment_allproject);

    }

    @Override
    protected void initView() {
        mPresenter = new ShuJuFenXiPresentetr(this);

        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        MyRefreshHeader headerView = new MyRefreshHeader(getContext());
        MyRefreshBottom bottomView = new MyRefreshBottom(getContext());

        twinklingRefreshLayout.setEnableLoadmore(false);
        twinklingRefreshLayout.setEnableOverScroll(false);
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setBottomView(bottomView);
        adapter = new ShuJUFenXiAdapter(list,getContext());

        footerView = LayoutInflater.from(getContext()).inflate(R.layout.view_myproject_footer, null);

        foottext=footerView.findViewById(R.id.myproject_footer_add);

        foottext.setText(R.string.moredata);

        adapter.addFooterView(footerView);

        footerView.setVisibility(View.GONE);

        list = new ArrayList<>();

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.setNestedScrollingEnabled(false);


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
        initOnclick();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    protected void initOnclick() {

       // getNetData(ProjectDetailActivity.getPosition());

        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {

                getNetData(ProjectDetailActivity.getPosition());
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


    public void getNetData(int position) {

        if(mPresenter!=null){
            remind_title.setVisibility(View.VISIBLE);
            mPresenter.getAllIndex(id);
        }
    }

    private Handler mhandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //getNetData(1);
            twinklingRefreshLayout.startRefresh();
        }
    };


    @Override
    protected void lazyLoad() {
        if(isViewCreated&&isUIVisible){
//            if(ProjectDetailActivity.getPosition()==1){
//                lists = PreferenceUtil.getListData(coinSymbol,SummaryBean.class);
//                if(lists.size()>0){
//                    //先加载本地持久化的数据
//                    downloadLocalData(lists);
//
//                    mhandler.sendEmptyMessage(0);
//
//                   // mhandler.sendEmptyMessageDelayed(0,3000);
//
//                }else{
//                    twinklingRefreshLayout.startRefresh();
//                    //getNetData(ProjectDetailActivity.getPosition());
//                    //
//                }
//                  //  twinklingRefreshLayout.startRefresh();
//            }else
                twinklingRefreshLayout.startRefresh();
                //
                //getNetData(ProjectDetailActivity.getPosition());
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;

        }

    }

    @Override
    protected void onChangeEvent(int type) {

    }


    public static List<SummaryBean> getSummaryList(){

        return summaryBeanList;
    }


    @Override
    public void onShuJuFenXi(SummaryResp items) {


        twinklingRefreshLayout.finishRefreshing();

        remind_title.setVisibility(View.GONE);


        if(items.getResult()!=null){

            if(items.getResult().getData().size()!=0){
                summaryBeanList=items.getResult().getData();

                adapter.setNewData(items.getResult().getData());

                init(items.getResult().getData());
            }



        }

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




    /**
     * 为当前币建立自己的排序规则
     *
     */
    public void init(List<SummaryBean> summaryBeanList){



        if(SQLiteUtils.getInstance().getTrand(Commons.smybl).size()==0) {

            //当前表中没有该数据执行插入操作

               if(summaryBeanList!=null) {
                for(int i=0;i<summaryBeanList.size();i++){

                    trandBean = new TrandBean();

                    trandBean.setSymbol(coinSymbol);

                    trandBean.setIscollect(false);

                    trandBean.setTrandname_en(summaryBeanList.get(i).getItemName());

                    trandBean.setTrandname(summaryBeanList.get(i).getTitile());

                    trandBean.setTrandid(summaryBeanList.get(i).getItemName());

                    trandBean.setTrandnum(i+1);

                    SQLiteUtils.getInstance().addTrand(trandBean);
                }

            }
        }
    }


    public void setOnUpdateTablayout(OnUpdateTablayout mListener) {
        this.onUpdateTablayout1 = mListener;
    }


    public interface OnUpdateTablayout {
        void updateTablayout(int position);
    }



}
