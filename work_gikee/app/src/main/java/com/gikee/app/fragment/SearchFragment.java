package com.gikee.app.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.Observer.CollectObserverService;
import com.gikee.app.Observer.base_observe.ConstObserver;
import com.gikee.app.Observer.base_observe.IObserver;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.activity.AddressDetailActivity;
import com.gikee.app.activity.BTCAddressDetailActivity;
import com.gikee.app.activity.LeaderboardActivity;
import com.gikee.app.activity.MPEditTrandActivity;
import com.gikee.app.activity.ProjectDetailActivity;
import com.gikee.app.activity.SearchActivity;
import com.gikee.app.activity.TradeDetailActivity;
import com.gikee.app.adapter.BaseLineAdapter;
import com.gikee.app.adapter.HomeLeaderboardAdapter;
import com.gikee.app.adapter.HotProjectAdapter;
import com.gikee.app.beans.BaseLineBean;
import com.gikee.app.beans.HomeBaseLineBean;
import com.gikee.app.beans.LineBean;
import com.gikee.app.beans.SearchResp;
import com.gikee.app.datas.Commons;
import com.gikee.app.preference_config.PreferenceUtil;
import com.gikee.app.presenter.search.InterfaceSearchView;
import com.gikee.app.presenter.search.SearchPresenter;
import com.gikee.app.resp.BTCTradeDetailResp;
import com.gikee.app.resp.BaseLineResp;
import com.gikee.app.resp.HotProiectBean;
import com.gikee.app.resp.HotProjectResp;
import com.gikee.app.resp.IsAddressResp;
import com.gikee.app.resp.LookAroundResp;
import com.gikee.app.resp.RankingDetailBean;
import com.gikee.app.resp.RankingDetailResp;
import com.gikee.app.resp.TokenTypeResp;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static android.content.Context.INPUT_METHOD_SERVICE;


@SuppressLint("ValidFragment")
public class SearchFragment extends BaseFragment<SearchPresenter> implements InterfaceSearchView,IObserver {


    @Bind(R.id.search_layout)
    RelativeLayout searchLayout;
    @Bind(R.id.leaderboard)
    TextView leaderboard;
//    @Bind(R.id.browse_context)
//    FlowLayout browsecontext;
    @Bind(R.id.search)
    LinearLayout root;
    @Bind(R.id.recyclerview_hot)
    RecyclerView recyclerView;
    @Bind(R.id.bg)
    LinearLayout linearLayout;
    @Bind(R.id.leader_recycler)
    RecyclerView leader_recycler;
    @Bind(R.id.table_loadmore)
    TextView table_loadmore;
    @Bind(R.id.leader_layout)
    RelativeLayout leader_layout;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.app_logo)
    ImageView app_logo;
    @Bind(R.id.recyclerview_baseline)
    RecyclerView recyclerview_baseline;
    @Bind(R.id.baseline)
    RelativeLayout baseline;
    @Bind(R.id.bottom_title)
    TextView bottom_title;
    @Bind(R.id.nocontent_repeat)
    RelativeLayout nocontent_repeat;
    @Bind(R.id.add_title)
    TextView footerView;
    @Bind(R.id.browse_layout)
    LinearLayout browse_layout;
    @Bind(R.id.card_hot)
    LinearLayout card_hot;
    @Bind(R.id.not_netwrok)
    ImageView not_netwrok;

    @Bind(R.id.search_layout_card)
    CardView search_layout_card;


    private Context mcontext;
    private String keywords = "";
    private LayoutInflater mInflater;
    private  SearchPresenter mPresenter;
    private HotProjectAdapter hotProjectAdapter;
    private HomeLeaderboardAdapter leaderboardAdapter;

    private BaseLineAdapter baseLineAdapter;

    private List<String> history_list;

    private int start=1;

    private int limit=5;

    public  int REQUESCODE = 400;

    private  HomeBaseLineBean baseLineBean;

    private LineBean lineBean;

    private  List<HomeBaseLineBean> lineBeanList = new ArrayList<>();//基础链数据

    private  List<HotProiectBean> hotProiectBeanList = new ArrayList<>();

    private List<RankingDetailBean> rankingDetailBeanList = new ArrayList<>();

    private List<BaseLineBean> baseLineBeanList= new ArrayList<>();

    private int conut=0;

    private  RelativeLayout.LayoutParams params;

    public SearchFragment() {

    }


    public SearchFragment(Context context) {
        this.mcontext = context;
    }
    @Override
    protected void setupViews(LayoutInflater inflater) {

        CollectObserverService.getInstance().registerObserver(this);

        setContentView(inflater,R.layout.fragment_search);


    }

    @Override
    protected void initView() {

        mPresenter = new SearchPresenter(this);

        mInflater = LayoutInflater.from(getContext());

        scrollView.setVerticalScrollBarEnabled(false);

        hotProjectAdapter = new HotProjectAdapter();

        leaderboardAdapter= new HomeLeaderboardAdapter();

        baseLineAdapter = new BaseLineAdapter();
        //控件大小位置动态设置
        params= (RelativeLayout.LayoutParams) app_logo.getLayoutParams();

        params.topMargin=(int)(MyUtils.getHieght()*(0.18));

        app_logo.setLayoutParams(params);

        params= (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();

        params.height=(int)(MyUtils.getHieght()*(0.6));

        linearLayout.setLayoutParams(params);

//        params= (RelativeLayout.LayoutParams) card_browse.getLayoutParams();
//
//        params.topMargin =(int)(MyUtils.getHieght()*(0.05));
//
//        card_browse.setLayoutParams(params);

        //recycleview样式
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);

        LinearLayoutManager ms= new LinearLayoutManager(getContext());

        ms.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(manager);

        recyclerview_baseline.setLayoutManager(ms);

        leader_recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        //热点项目适配器
        recyclerView.setAdapter(hotProjectAdapter);

        recyclerView.setNestedScrollingEnabled(false);

        //排行榜适配器
        leader_recycler.setNestedScrollingEnabled(false);
        leader_recycler.setAdapter(leaderboardAdapter);

        //基础链适配器
        recyclerview_baseline.setNestedScrollingEnabled(false);
        recyclerview_baseline.setAdapter(baseLineAdapter);

        initOnclick();

        //检查本地持久化数据是否存在，优先加载本地数据
        lineBeanList = PreferenceUtil.getListData(Commons.BASELINE_DATA,HomeBaseLineBean.class);

        hotProiectBeanList = PreferenceUtil.getListData(Commons.HOTPROJECT_DATA,HotProiectBean.class);

        rankingDetailBeanList = PreferenceUtil.getListData(Commons.LEADER_DATA,RankingDetailBean.class);

        getData();

        if(hotProiectBeanList.size()>0){

            intLocalHotData(hotProiectBeanList);

        }

        if(rankingDetailBeanList.size()>0){
            intLocalLeaderData(rankingDetailBeanList);
        }



        //启动轮播首页基础信息的线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    mhandler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(1000*15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }




    private Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what==0){

                lineBeanList.clear();

                if(baseLineBeanList.size()!=0){

                    if(conut>=baseLineBeanList.get(0).getLineData().size()){

                        conut=0;
                    }



                    for(int i=0;i<baseLineBeanList.size();i++){
                        baseLineBean = new HomeBaseLineBean();
                        baseLineBean.setSymbol(baseLineBeanList.get(i).getSymbol());
                        baseLineBean.setId(baseLineBeanList.get(i).getId());
                        baseLineBean.setLogo(baseLineBeanList.get(i).getLogo());
                         lineBean = new LineBean();
                        lineBean.setCurrentValue(baseLineBeanList.get(i).getLineData().get(conut).getCurrentValue());
                        lineBean.setCurrentName(baseLineBeanList.get(i).getLineData().get(conut).getCurrentName());
                        lineBean.setDot(baseLineBeanList.get(i).getLineData().get(conut).getDot());
                        baseLineBean.setLineData(lineBean);
                        lineBeanList.add(baseLineBean);
                    }

//                    baseLineBean = new HomeBaseLineBean();
//
//                    baseLineBean.setSymbol("BCH");
//
//                    lineBean = new LineBean();
//
//                     lineBean.setCurrentValue(mcontext.getString(R.string.more_item));
//
//                    if(mcontext!=null){
//
//                        lineBean.setCurrentName(mcontext.getString(R.string.recently_launched));
//
//                    }

                  //  baseLineBean.setLineData(lineBean);

                   // lineBeanList.add(baseLineBean);

                    baseLineAdapter.setNewData(lineBeanList);

                    conut++;

                }

            }else if(msg.what==1){

                getData();
            }




        }
    };

    private void initOnclick() {

        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });


        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivityForResult(intent, REQUESCODE);

            }
        });


        search_layout_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivityForResult(intent, REQUESCODE);

            }
        });



        hotProjectAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getContext(), ProjectDetailActivity.class)
                        .putExtra("id", hotProjectAdapter.getData().get(position).getId())
                        .putExtra("logo",hotProjectAdapter.getData().get(position).getLogo())
                        .putExtra("symbol", hotProjectAdapter.getData().get(position).getSymbol())
                );
            }
        });

        leader_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LeaderboardActivity.class);
                startActivity(intent);
            }
        });

        leaderboardAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                String symbol=leaderboardAdapter.getData().get(position).getSymbol();
                String id=leaderboardAdapter.getData().get(position).getId();
                String logo=leaderboardAdapter.getData().get(position).getLogo();
                //if(symbol.equals(Commons.BTC)||symbol.equals(Commons.ETH)){

                startActivity(new Intent(mcontext, ProjectDetailActivity.class)
                        .putExtra("logo",logo)
                        .putExtra("id", id)
                        .putExtra("symbol", symbol));

            }
        });




        baseLineAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                String symbol=baseLineAdapter.getData().get(position).getSymbol();
                String id=baseLineAdapter.getData().get(position).getId();
                String logo=baseLineAdapter.getData().get(position).getLogo();
                //if(symbol.equals(Commons.BTC)||symbol.equals(Commons.ETH)){

                    startActivity(new Intent(mcontext, ProjectDetailActivity.class)
                            .putExtra("logo",logo)
                            .putExtra("id", id)
                            .putExtra("symbol", symbol));

               // }
            }
        });



        table_loadmore.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //点击跳转排行页面

                Intent intent = new Intent(getContext(), LeaderboardActivity.class);
                startActivity(intent);


            }
        });




    }





    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onChangeEvent(int type) {



    }

    private void getData() {


       // Glide.with(mcontext).load(R.drawable.loading_02).into(not_netwrok);

        nocontent_repeat.setVisibility(View.GONE);

        not_netwrok.setVisibility(View.GONE);

        browse_layout.setVisibility(View.VISIBLE);

        leader_layout.setVisibility(View.VISIBLE);

        card_hot.setVisibility(View.VISIBLE);

        bottom_title.setText(getString(R.string.moredata));

        if(mPresenter!=null){
            mPresenter.getHomeBaseLine();
        }

    }


    //加载本地基础链数据
    private void initLocalData(List<HomeBaseLineBean> lineBeanList) {

        baseLineAdapter.setNewData(lineBeanList);

    }


    //加载本地热点数据
    private void intLocalHotData(List<HotProiectBean> hotProiectBeanList) {

        hotProjectAdapter.setNewData(hotProiectBeanList);
    }
    //加载本地排行榜数据
    private void intLocalLeaderData(List<RankingDetailBean> rankingDetailBeanList) {

        leaderboardAdapter.setNewData(rankingDetailBeanList);
    }

    @Override
    public void onSearchView(SearchResp searchBean) {

    }

    @Override
    public void onFuzzySearch(SearchResp searchBean) {

    }

    @Override
    public void onSearchAddressView(TokenTypeResp tokenTypeResp) {

        if(tokenTypeResp.getResult()!=null){

            //搜索地址入口时间参数传空

            if(tokenTypeResp.getResult().size()!=0){


                Intent intent = new Intent(getContext(), AddressDetailActivity.class);

                intent.putExtra("address",keywords);

                startActivityForResult(intent,REQUESCODE);
            }else
                ToastUtils.show("该地址不存在");

        }else{
            ToastUtils.show("该地址不存在");
        }

    }

    @Override
    public void onLookAround(final LookAroundResp lookAroundResp) {

//        /**
//         *随便看看数据展示
//         */
//        if(TextUtils.isEmpty(lookAroundResp.getErrInfo())){
//
//            if(lookAroundResp.getResult().getData().size()!=0){
//
//                browsecontext.removeAllViews();
//
//                for(int i=0;i<lookAroundResp.getResult().getData().size();i++){
//
//                    TextView textView = (TextView)mInflater.inflate(R.layout.search_label_tv, browsecontext,false);
//
//                    String title="";
//
//                    if(i==0){
//                        title= mcontext.getString(R.string.hot1);
//                    }else if(i==1){
//                        title= mcontext.getString(R.string.hot2);
//                    }else if(i==2){
//                        title= mcontext.getString(R.string.hot3);
//                    }else if(i==3){
//                        title= mcontext.getString(R.string.hot4);
//                    }
//
//                    textView.setText(title);
//
//                    final String address = lookAroundResp.getResult().getData().get(i).getJump();
//
//                    if(i%2==0){
//
//                        textView.setBackgroundResource(R.drawable.shape_btn_browse);
//                    }else
//                        textView.setBackgroundResource(R.drawable.shape_btn_browse_selected);
//
//
//                    //点击事件
//                    textView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//
//                            Intent intent = new Intent(getContext(), MyAddressWebviewActivity.class);
//
//                            intent.putExtra("address",address);
//
//                            intent.putExtra("startDate","");
//
//                            intent.putExtra("endDate","");
//
//                            startActivity(intent);
//
//                        }
//                    });
//                    browsecontext.addView(textView);
//                }
//            }
//
//
//
//        }else
//            ToastUtils.show(lookAroundResp.getErrInfo());
    }

    @Override
    public void onHot(final HotProjectResp searchBean) {

        /**
         * 模拟热点项目数据
         */
        not_netwrok.setVisibility(View.GONE);

//        mPresenter.getRankDetail("tradeCount",start,limit,"day");
//
        if(TextUtils.isEmpty(searchBean.getErrInfo())){

            if(searchBean.getResult().getData().size()!=0){

                hotProiectBeanList = searchBean.getResult().getData();

                PreferenceUtil.putListData(Commons.HOTPROJECT_DATA,hotProiectBeanList);

                hotProjectAdapter.setNewData(hotProiectBeanList);
        }


        }else{
            ToastUtils.show(searchBean.getErrInfo());

            if(mcontext!=null){
                bottom_title.setText(mcontext.getString(R.string.moredata));
            }
        }


    }

    @Override
    public void onRankDetail(RankingDetailResp resp) {

        if(resp.getResult()!=null){
            if(resp.getResult().getData().size()>0){

                rankingDetailBeanList = resp.getResult().getData();

                leaderboardAdapter.setNewData(rankingDetailBeanList);

                PreferenceUtil.putListData(Commons.LEADER_DATA,rankingDetailBeanList);

            }
        }

    }

    @Override
    public void getRankDetail2(RankingDetailResp resp) {

    }


    //基础链数据回调
    @Override
    public void ontHomeBaseLine(BaseLineResp resp) {

        mPresenter.getHot(0,8);

        if(TextUtils.isEmpty(resp.getErrInfo())){

        if(resp.getResult()!=null){

            if(resp.getResult().getData().size()!=0) {

                lineBeanList.clear();

                baseLineBeanList  = resp.getResult().getData();


                for(int i=0;i<baseLineBeanList.size();i++){
                    baseLineBean = new HomeBaseLineBean();
                    baseLineBean.setSymbol(baseLineBeanList.get(i).getSymbol());
                    baseLineBean.setLogo(baseLineBeanList.get(i).getLogo());
                    baseLineBean.setId(baseLineBeanList.get(i).getId());
                    LineBean lineBean = new LineBean();
                    lineBean.setCurrentValue(baseLineBeanList.get(i).getLineData().get(0).getCurrentValue());
                    lineBean.setCurrentName(baseLineBeanList.get(i).getLineData().get(0).getCurrentName());
                    lineBean.setDot(baseLineBeanList.get(i).getLineData().get(0).getDot());
                    baseLineBean.setLineData(lineBean);
                    lineBeanList.add(baseLineBean);
                }


//
//                baseLineBean.setLineData(lineBean);
//
//                lineBeanList.add(baseLineBean);

                baseLineAdapter.setNewData(lineBeanList);

                PreferenceUtil.putListData(Commons.BASELINE_DATA,lineBeanList);


            }

        }
        }

    }

    @Override
    public void onBTCTradeDetail(BTCTradeDetailResp btcTradeDetailResp) {

    }

    @Override
    public void onEOSTradeDetail(IsAddressResp resp) {

    }

    @Override
    public void onError() {

        not_netwrok.setBackground(getResources().getDrawable(R.mipmap.no_network));

        not_netwrok.setVisibility(View.VISIBLE);

        nocontent_repeat.setVisibility(View.VISIBLE);

        browse_layout.setVisibility(View.GONE);

        leader_layout.setVisibility(View.GONE);

        card_hot.setVisibility(View.GONE);

        bottom_title.setText("");

        footerView.setText(getString(R.string.reload));

      //  bottom_title.setText(mcontext.getString(R.string.check_net));
    }

    @Override
    public void onChange(Object o, int actionCode, int requestCode) {
        if(requestCode== ConstObserver.NETWORKCHANGE){
            //收藏成功或者取消，刷新数据源
           // getData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CollectObserverService.getInstance().unRegisterObserver(this);


    }

    @Override
    public void onPause() {
        super.onPause();
    }
}


