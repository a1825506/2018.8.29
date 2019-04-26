package com.gikee.app.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.adapter.BaseChainTodayAdapter;
import com.gikee.app.adapter.ChooseItemAdapter;
import com.gikee.app.adapter.MarkViewAdapter;
import com.gikee.app.adapter.PopAllprojectAdapter;
import com.gikee.app.beans.BaseChainBean;
import com.gikee.app.beans.ChooseItemBean;
import com.gikee.app.beans.MarkerViewBean;
import com.gikee.app.beans.TotalHashRateBean;
import com.gikee.app.beans.ZhanghuPopBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.language.LanguageType;
import com.gikee.app.presenter.home.HomePresenter;
import com.gikee.app.presenter.home.HomeView;
import com.gikee.app.resp.DotBean;
import com.gikee.app.resp.MarketRateResp;
import com.gikee.app.resp.PowerResp;
import com.gikee.app.resp.SummaryBean;
import com.gikee.app.resp.SummaryResp;
import com.gikee.app.resp.TopFreqAddrResp;
import com.gikee.app.resp.ValueResp;
import com.gikee.app.views.LineChartEntity;
import com.gikee.app.views.MyBoldTextView;
import com.gikee.app.views.MyLineChart;
import com.gikee.app.views.MyRefreshHeader;
import com.gikee.app.views.XYMarkerView;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

public class PowerFragment extends BaseFragment<HomePresenter> implements HomeView {


    @Bind(R.id.text_value)
    TextView text_value;
    @Bind(R.id.text_quote)
    TextView text_quote;
    @Bind(R.id.linechart_power)
    MyLineChart mLineChart;
    @Bind(R.id.linechart_market)
    MyLineChart mLineChart2;
    @Bind(R.id.recycle_detailitem)
    RecyclerView recycle_detailitem;


    @Bind(R.id.text_time)
    TextView text_time;
    @Bind(R.id.layout_choosedate)
    LinearLayout lin_poplayout;
    @Bind(R.id.recycle_data)
    RecyclerView re_timeinterval;

    @Bind(R.id.ethtext_time)
    TextView ethtext_time;
    @Bind(R.id.ethlayout_choosedate)
    LinearLayout ethlin_poplayout;
    @Bind(R.id.ethrecycle_data)
    RecyclerView ethre_timeinterval;
    @Bind(R.id.ethtext_quote)
    MyBoldTextView ethtext_quote;

    @Bind(R.id.ethtext_value)
    MyBoldTextView ethtext_choosedate;


    @Bind(R.id.ethtext_quotetitle)
    TextView ethtext_quotetitle;


    @Bind(R.id.layout_ethtext_time)
    LinearLayout layout_ethtext_time;
    @Bind(R.id.layout_time)
    LinearLayout layout_choosedate;
    @Bind(R.id.all_layout)
    RelativeLayout all_layout;
    @Bind(R.id.recycle_title)
    RecyclerView recycle_title;


    @Bind(R.id.markerview_layout)
    LinearLayout markerview_layout;
    @Bind(R.id.recycle_markerview)
    RecyclerView recycle_markerview;

    @Bind(R.id.ethmarkerview_layout)
    LinearLayout ethmarkerview_layout;
    @Bind(R.id.ethrecycle_markerview)
    RecyclerView ethrecycle_markerview;

    @Bind(R.id.fm_all_shuju_refreshLayout)
    TwinklingRefreshLayout twinklingRefreshLayout;

    @Bind(R.id.head_layout)
    RelativeLayout head_layout;
    @Bind(R.id.price_layout)
    ConstraintLayout price_layout;
    @Bind(R.id.eth_head_title)
    RelativeLayout eth_head_title;
    @Bind(R.id.price_text)
    TextView price_text;
    @Bind(R.id.eth_price_title)
    TextView eth_price_title;

    private Date dateToday;

    private String date="30day";

    private BaseChainTodayAdapter baseChainTodayAdapter;

    List<ChooseItemBean> chooseItemBeanList = new ArrayList<>();

    private ChooseItemAdapter chooseItemAdapter;

    private List<ZhanghuPopBean> list_pop = new ArrayList<>();

    private int timeType=0;

    private List<ZhanghuPopBean> ethlist_pop = new ArrayList<>();

    private  List<DotBean> dot_max = new ArrayList<>();

    private int ethtimeType=0;

    private int ethchoosesize=30;

    private int choosesize=30;

    List<SummaryBean> data_all=new ArrayList<>();
    private List<String> choose_title=new ArrayList();

    private boolean isshow=false;

    private boolean iethsshow=false;



    private  List<DotBean> dot_price = new ArrayList<>();

    private  List<DotBean> dot_btcrate = new ArrayList<>();

    private  List<DotBean> dot_bchrate = new ArrayList<>();

    private  List<DotBean> dot_ltcrate = new ArrayList<>();

    private  List<DotBean> dot_ethprice = new ArrayList<>();

    private  List<DotBean> dot_ethrate = new ArrayList<>();


    private  LineChartEntity lineChartEntity_eth,lineChartEntity=null;

    private LineDataSet lineDataSet_eth,lineDataSet=null;

    private LineData lineData_eth,lineData;

    long currentTimeMillis;
    long ethcurrentTimeMillis;
    private List<MarkerViewBean>  markerViewBeanList = new ArrayList<>();
    private List<MarkerViewBean>  ethmarkerViewBeanList = new ArrayList<>();

    private MarkViewAdapter markViewAdapter;

    private Map<String,Integer> color_map = new HashMap<>();

    private MarkViewAdapter ethmarkViewAdapter;

    private Map<String,Integer> ethcolor_map = new HashMap<>();

    private XYMarkerView xyMarkerView,ethxyMarkerView;

    private String price_title;



    @Override
    protected void setupViews(LayoutInflater inflater) {

        setContentView(inflater, R.layout.fragment_power);

    }


    @Override
    protected void initView() {

        mPresenter = new HomePresenter(this);

        MyRefreshHeader headerView = new MyRefreshHeader(getContext());

        twinklingRefreshLayout.setEnableLoadmore(false);
        twinklingRefreshLayout.setEnableOverScroll(false);
        twinklingRefreshLayout.setHeaderView(headerView);

        dateToday = new Date();

        mLineChart.setNoDataText(getString(R.string.getdata));

        xyMarkerView = new XYMarkerView(getContext());

        xyMarkerView.setChartView(mLineChart);

        mLineChart.setMarker(xyMarkerView);

        mLineChart2.setNoDataText(getString(R.string.getdata));

        ethxyMarkerView = new XYMarkerView(getContext());

        ethxyMarkerView.setChartView(mLineChart2);

        mLineChart2.setMarker(ethxyMarkerView);



        choose_title.add(Commons.btcHashRate);

        choose_title.add(Commons.btcPrice);

        baseChainTodayAdapter = new BaseChainTodayAdapter();

        GridLayoutManager  gridLayoutManager = new GridLayoutManager(getContext(), 3);

        recycle_detailitem.setLayoutManager(gridLayoutManager);

        recycle_detailitem.setAdapter(baseChainTodayAdapter);


        chooseItemAdapter = new ChooseItemAdapter();

        GridLayoutManager  gridLayoutManager1 = new GridLayoutManager(getContext(), 3);

        recycle_title.setLayoutManager(gridLayoutManager1);

        recycle_title.setAdapter(chooseItemAdapter);

        price_title = MyUtils.getUnit()?getString(R.string.price_us):getString(R.string.price_cny);

        price_text.setText(price_title);

        eth_price_title.setText(price_title);

        initPop();

        initEthPop();

        onClick();

        new Thread(new MyThread()).start();

    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if(System.currentTimeMillis()-currentTimeMillis>1000){
                    markerview_layout.setVisibility(View.GONE);
                    if(mLineChart!=null){
                        mLineChart.highlightValue(null);
                    }

                }
            }
            if (msg.what == 2) {
                if(System.currentTimeMillis()-ethcurrentTimeMillis>1000){
                    ethmarkerview_layout.setVisibility(View.GONE);
                    if(mLineChart2!=null){
                        mLineChart2.highlightValue(null);
                    }

                }
            }
            super.handleMessage(msg);

        }
    };


    class MyThread extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);//每隔1s执行一次
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);

                    Message msg1 = new Message();
                    msg1.what = 2;
                    handler.sendMessage(msg1);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    private void onClick() {




        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {

                getNetData();
            }

        });


        layout_choosedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(isshow){
                    endAnimOut();
                    isshow=false;
                }else{
                    starAnimEnter();
                    isshow=true;
                }
            }
        });

        layout_ethtext_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(iethsshow){
                    ethstarAnimEnter();
                    iethsshow=false;
                }else{
                    ethendAnimOut();
                    iethsshow=true;
                }
            }
        });




        chooseItemAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                if(chooseItemAdapter.getData().get(position).isChoose()){
                    choose_title.remove(chooseItemAdapter.getData().get(position).getId());
                    chooseItemAdapter.getData().get(position).setChoose(false);
                }else{
                    choose_title.add(chooseItemAdapter.getData().get(position).getId());
                    chooseItemAdapter.getData().get(position).setChoose(true);
                }
                chooseItemAdapter.notifyItemChanged(position);

                invalidate();

                initChatData();


            }
        });


        mLineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                currentTimeMillis =  System.currentTimeMillis();

            }

            @Override
            public void onNothingSelected() {

            }
        });

        mLineChart2.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                ethcurrentTimeMillis =  System.currentTimeMillis();


            }

            @Override
            public void onNothingSelected() {

            }
        });



    }

    private void invalidate() {

        if(choose_title.size()>2){
            if(choose_title.contains(Commons.btcHashRate)){
                choose_title.remove(Commons.btcPrice);
            }
                if(choose_title.contains(Commons.bchHashRate)){
                choose_title.remove(Commons.bchPrice);
            }
                if(choose_title.contains(Commons.ltcHashRate)){
                choose_title.remove(Commons.ltcPrice);
            }
        }else if(choose_title.size()<2){
            if(choose_title.contains(Commons.btcHashRate)){
                choose_title.add(Commons.btcPrice);
            }
                if(choose_title.contains(Commons.bchHashRate)){
                choose_title.add(Commons.bchPrice);
            }
                if(choose_title.contains(Commons.ltcHashRate)){
                choose_title.add(Commons.ltcPrice);
            }

        }


    }




    private void starAnimEnter() {

        lin_poplayout.setVisibility(View.VISIBLE);
        for (int i = 0; i < lin_poplayout.getChildCount(); i++) {
            lin_poplayout.getChildAt(i).setVisibility(View.VISIBLE);
        }
       // view_cancle.setVisibility(View.VISIBLE);
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(300);
        animation.setFillAfter(true);
        if (lin_poplayout != null)
            lin_poplayout.startAnimation(animation);
    }



    private void endAnimOut() {
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(300);
        animation.setFillAfter(true);
        if (lin_poplayout != null)
            lin_poplayout.startAnimation(animation);
        lin_poplayout.setVisibility(View.GONE);
        for (int i = 0; i < lin_poplayout.getChildCount(); i++) {
            lin_poplayout.getChildAt(i).setVisibility(View.GONE);
        }
       // view_cancle.setVisibility(View.GONE);
    }

    private void ethstarAnimEnter() {

        ethlin_poplayout.setVisibility(View.VISIBLE);
        for (int i = 0; i < ethlin_poplayout.getChildCount(); i++) {
            ethlin_poplayout.getChildAt(i).setVisibility(View.VISIBLE);
        }
       // ethview_cancle.setVisibility(View.VISIBLE);
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(300);
        animation.setFillAfter(true);
        if (ethlin_poplayout != null)
            ethlin_poplayout.startAnimation(animation);
    }



    private void ethendAnimOut() {
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(300);
        animation.setFillAfter(true);
        if (ethlin_poplayout != null)
            ethlin_poplayout.startAnimation(animation);
        ethlin_poplayout.setVisibility(View.GONE);
        for (int i = 0; i < lin_poplayout.getChildCount(); i++) {
            ethlin_poplayout.getChildAt(i).setVisibility(View.GONE);
        }
        //ethview_cancle.setVisibility(View.GONE);
    }


    private void initPop() {
        list_pop.clear();
        list_pop.add(new ZhanghuPopBean(true, getResources().getString(R.string.last_30)));
        list_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.last_90)));
        list_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.last_180)));
        list_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.last_year)));
        list_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.last_threeyear)));
        list_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.last_all)));
        PopAllprojectAdapter adapter = new PopAllprojectAdapter();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        adapter.getData().addAll(list_pop);
        re_timeinterval.setLayoutManager(layoutManager);
        re_timeinterval.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ((List<ZhanghuPopBean>) adapter.getData()).get(timeType).setChose(false);
                ((List<ZhanghuPopBean>) adapter.getData()).get(position).setChose(true);
                timeType = position;
                adapter.notifyDataSetChanged();


                if(position==0){
                    choosesize=30;
                    date="30day";
                }else if(position==1){
                    choosesize=90;
                    date="90day";
                }else if(position==2){
                    choosesize=180;
                    date="180day";
                }else if(position==3){
                    choosesize=365;
                    date="365day";
                }else if(position==4){
                    choosesize=365*3;
                    date="3year";
                }else if(position==5){
                    choosesize=-1;
                    date="All";
                }

                text_time.setText(((List<ZhanghuPopBean>) adapter.getData()).get(position).getName());

                twinklingRefreshLayout.startRefresh();

                endAnimOut();

            }

        });
    }


    private void initChatData() {

        DotBean dotBean=null;
        dot_btcrate.clear();
        dot_bchrate.clear();
        dot_ltcrate.clear();
        dot_price.clear();

        //改变数据源重新绘制折线图
        for (int i = 0; i < data_all.size(); i++) {

            for(int j=0;j<choose_title.size();j++){

                if(data_all.get(i).getItemName().equals(choose_title.get(j))){
                    //需要对比的指标,绘制折线
                    if( data_all.get(i).getItemName().equals("btcHashRate")){

                        List<DotBean> dot =data_all.get(i).getLineData().get(0).getDot();
                        chooseData(dot,data_all.get(i).getItemName());
                    }else if( data_all.get(i).getItemName().equals("bchHashRate")){
                        List<DotBean> dot =data_all.get(i).getLineData().get(0).getDot();
                        chooseData(dot,data_all.get(i).getItemName());

                    }else if( data_all.get(i).getItemName().equals("ltcHashRate")){
                        List<DotBean> dot =data_all.get(i).getLineData().get(0).getDot();
                        chooseData(dot,data_all.get(i).getItemName());
                    }else if( data_all.get(i).getItemName().equals("btcPrice")){
                        List<DotBean> dot =data_all.get(i).getLineData().get(0).getDot();
                        chooseData(dot,data_all.get(i).getItemName());
                    }else if( data_all.get(i).getItemName().equals("bchPrice")){

                        List<DotBean> dot =data_all.get(i).getLineData().get(0).getDot();
                        chooseData(dot,data_all.get(i).getItemName());
                    }else if( data_all.get(i).getItemName().equals("ltcPrice")){

                        List<DotBean> dot =data_all.get(i).getLineData().get(0).getDot();
                        chooseData(dot,data_all.get(i).getItemName());
                    }
                }

            }

        }
        setmLineChartData(dot_price,dot_btcrate,dot_bchrate,dot_ltcrate);
    }


    private void initEthPop() {
        ethlist_pop.clear();
        ethlist_pop.add(new ZhanghuPopBean(true, getResources().getString(R.string.last_30)));
        ethlist_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.last_90)));
        ethlist_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.last_180)));
        ethlist_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.last_year)));
        ethlist_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.last_threeyear)));
        ethlist_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.last_all)));
        PopAllprojectAdapter adapter = new PopAllprojectAdapter();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        adapter.getData().addAll(ethlist_pop);
        ethre_timeinterval.setLayoutManager(layoutManager);
        ethre_timeinterval.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ((List<ZhanghuPopBean>) adapter.getData()).get(ethtimeType).setChose(false);
                ((List<ZhanghuPopBean>) adapter.getData()).get(position).setChose(true);
                ethtimeType = position;
                adapter.notifyDataSetChanged();


                if(position==0){
                    ethchoosesize=30;
                    date="30day";
                }else if(position==1){
                    ethchoosesize=90;
                    date="90day";
                }else if(position==2){
                    ethchoosesize=180;
                    date="180day";
                }else if(position==3){
                    ethchoosesize=365;
                    date="365day";
                }else if(position==4){
                    ethchoosesize=365*3;
                    date="3year";
                }else if(position==5){
                    date="All";
                    ethchoosesize=-1;
                }
                ethtext_time.setText(((List<ZhanghuPopBean>) adapter.getData()).get(position).getName());

                DotBean dotBean=null;


                dot_ethrate.clear();

                dot_ethprice.clear();

                //改变数据源重新绘制折线图
                for (int i = 0; i < data_all.size(); i++) {

                        if(data_all.get(i).getItemName().equals(Commons.ethHashRate)){
                            //需要对比的指标,绘制折线
                            List<DotBean> dot =data_all.get(i).getLineData().get(0).getDot();
                            if(ethchoosesize!=-1) {
                                if (dot.size() > ethchoosesize) {
                                    //首次展示近30天的数据
                                    for (int n = dot.size() - ethchoosesize; n < dot.size(); n++) {
                                        dotBean = new DotBean();
                                        dotBean.setTime(dot.get(n).getTime());
                                        dotBean.setValue(dot.get(n).getValue());
                                        dot_ethrate.add(dotBean);
                                    }

                                }else{
                                    for(int n=0;n<dot.size();n++) {
                                        dotBean = new DotBean();
                                        dotBean.setTime(dot.get(n).getTime());
                                        dotBean.setValue(dot.get(n).getValue());
                                        dot_ethrate.add(dotBean);
                                    }
                                }
                            }else{
                                for(int n=0;n<dot.size();n++) {
                                    dotBean = new DotBean();
                                    dotBean.setTime(dot.get(n).getTime());
                                    dotBean.setValue(dot.get(n).getValue());
                                    dot_ethrate.add(dotBean);
                                }

                            }
                         }

                    if(data_all.get(i).getItemName().equals(Commons.ethPrice)){
                        //需要对比的指标,绘制折线
                        List<DotBean> dot =data_all.get(i).getLineData().get(0).getDot();
                            if(ethchoosesize!=-1) {
                                if (dot.size() > ethchoosesize) {
                                    //首次展示近30天的数据
                                    for (int n = dot.size() - ethchoosesize; n < dot.size(); n++) {
                                        dotBean = new DotBean();
                                        dotBean.setTime(dot.get(n).getTime());
                                        dotBean.setValue(dot.get(n).getValue());
                                        dot_ethprice.add(dotBean);
                                    }

                                }else{
                                    for(int n=0;n<dot.size();n++) {
                                        dotBean = new DotBean();
                                        dotBean.setTime(dot.get(n).getTime());
                                        dotBean.setValue(dot.get(n).getValue());
                                        dot_ethprice.add(dotBean);
                                    }

                                }
                            }else{

                                    for(int n=0;n<dot.size();n++) {
                                        dotBean = new DotBean();
                                        dotBean.setTime(dot.get(n).getTime());
                                        dotBean.setValue(dot.get(n).getValue());
                                        dot_ethprice.add(dotBean);
                                    }


                            }
                    }

                }

                setEthmLineChartData(dot_ethprice,dot_ethrate);

//                twinklingRefreshLayout.startRefresh();

                ethendAnimOut();

            }
        });
    }

    private void getNetData() {

        mPresenter.getPower(date);
    }


    @Override
    public void onPower(PowerResp powerResp) {

        twinklingRefreshLayout.finishRefreshing();

        if(TextUtils.isEmpty(powerResp.getErrInfo())){



            if(powerResp.getResult()!=null&&powerResp.getResult().getData()!=null){

                head_layout.setVisibility(View.VISIBLE);
                price_layout.setVisibility(View.VISIBLE);
                eth_head_title.setVisibility(View.VISIBLE);

                data_all.clear();

                chooseItemBeanList.clear();

                dot_price.clear();

                dot_btcrate.clear();

                dot_bchrate.clear();

                dot_ltcrate.clear();

                dot_ethprice.clear();

                dot_ethrate.clear();

                data_all = powerResp.getResult().getData();

                List<BaseChainBean> baseChainBeanList= new ArrayList<>();


                DotBean dotBean;

                    for(SummaryBean summaryBean:data_all){

                        if(summaryBean.getItemName().equals(Commons.btcHashRate)){

                            List<DotBean> dot =summaryBean.getLineData().get(0).getDot();
                            initAllData(dot,summaryBean.getItemName());
//                            if(choosesize!=-1){
//                                if(dot.size()>choosesize){
//                                    //首次展示近30天的数据
//                                    initItemData(dot,summaryBean.getItemName());
//                                }else
//                                    initAllData(dot,summaryBean.getItemName());
//                            }else
//                                initAllData(dot,summaryBean.getItemName());
                        }


                        if(summaryBean.getItemName().equals(Commons.btcPrice)){
                            List<DotBean> dot =summaryBean.getLineData().get(0).getDot();
                            initAllData(dot,summaryBean.getItemName());
//                            if(choosesize!=-1){
//                                if(dot.size()>choosesize){
//                                    //首次展示近30天的数据
//                                    initItemData(dot,summaryBean.getItemName());
//                                }else
//                                    initAllData(dot,summaryBean.getItemName());
//                            }else
//                                initAllData(dot,summaryBean.getItemName());
                            setmLineChartData(dot_price,dot_btcrate,dot_bchrate,dot_ltcrate);
                        }


//                        setmLineChartData(summaryBean.getItemName(),dot_choose);

                        if(summaryBean.getItemName().equals(Commons.ethHashRate)){

                            List<DotBean> dot =summaryBean.getLineData().get(0).getDot();
                            if(ethchoosesize!=-1){
                                //首次展示近30天的数据
                                if(dot.size()>ethchoosesize){
                                    //首次展示近30天的数据
                                    for(int n=dot.size()-ethchoosesize;n<dot.size();n++){
                                        dotBean = new DotBean();
                                        dotBean.setTime(dot.get(n).getTime());
                                        dotBean.setValue(dot.get(n).getValue());
                                        dot_ethrate.add(dotBean);
                                    }

                                }else{

                                    for(int n=0;n<dot.size();n++){
                                        dotBean = new DotBean();
                                        dotBean.setTime(dot.get(n).getTime());
                                        dotBean.setValue(dot.get(n).getValue());
                                        dot_ethrate.add(dotBean);
                                    }


                                }
                            }else{
                                for(int n=0;n<dot.size();n++){
                                    dotBean = new DotBean();
                                    dotBean.setTime(dot.get(n).getTime());
                                    dotBean.setValue(dot.get(n).getValue());
                                    dot_ethrate.add(dotBean);
                                }

                            }
                            ethtext_choosedate.setText(MyUtils.fmtMicrometer(summaryBean.getLineData().get(0).getCurrentValue().get(0))+"EH/s");

                            if(!TextUtils.isEmpty(summaryBean.getLineData().get(0).getQuoteChange())) {

                                if(summaryBean.getLineData().get(0).getQuoteChange().contains("-")){
                                    ethtext_quote.setText("↓"+MyUtils.fmtMicrometer1(summaryBean.getLineData().get(0).getQuoteChange().replace("-",""))+"%");
//                                    ethtext_quote.setTextColor(getContext().getResources().getColor(R.color.red));
                                    if(MyUtils.getQuateSymbol()){
                                        ethtext_quote.setTextColor(getContext().getResources().getColor(R.color.faa3c));
                                    }else
                                        ethtext_quote.setTextColor(getContext().getResources().getColor(R.color.red));
                                }else{
//                                    ethtext_quote.setTextColor(getContext().getResources().getColor(R.color.faa3c));
                                    if(MyUtils.getQuateSymbol()){
                                        ethtext_quote.setTextColor(getContext().getResources().getColor(R.color.red));
                                    }else
                                        ethtext_quote.setTextColor(getContext().getResources().getColor(R.color.faa3c));
                                    ethtext_quote.setText("↑"+MyUtils.fmtMicrometer1(summaryBean.getLineData().get(0).getQuoteChange())+"%");
                                }
                            }


                        }

                        if(summaryBean.getItemName().equals(Commons.ethPrice)){
                            List<DotBean> dot =summaryBean.getLineData().get(0).getDot();
                            if(ethchoosesize!=-1){
                                if(dot.size()>ethchoosesize){
                                    //首次展示近30天的数据
                                    for(int n=dot.size()-ethchoosesize;n<dot.size();n++){
                                        dotBean = new DotBean();
                                        dotBean.setTime(dot.get(n).getTime());
                                        dotBean.setValue(dot.get(n).getValue());
                                        dot_ethprice.add(dotBean);
                                    }

                                }else{

                                    for(int n=0;n<dot.size();n++){
                                        dotBean = new DotBean();
                                        dotBean.setTime(dot.get(n).getTime());
                                        dotBean.setValue(dot.get(n).getValue());
                                        dot_ethprice.add(dotBean);
                                    }


                                }
                            }else{

                                for(int n=0;n<dot.size();n++){
                                    dotBean = new DotBean();
                                    dotBean.setTime(dot.get(n).getTime());
                                    dotBean.setValue(dot.get(n).getValue());
                                    dot_ethprice.add(dotBean);
                                }
                            }

                            setEthmLineChartData(dot_ethprice,dot_ethrate);

                        }


                        if(!summaryBean.getItemName().contains("Price")&&!summaryBean.getItemName().equals("ethHashRate")){

                            BaseChainBean  baseChainBean = new BaseChainBean();

                            baseChainBean.setTitle(summaryBean.getTitile());

                            baseChainBean.setType("power");

                            baseChainBean.setQuote(summaryBean.getLineData().get(0).getQuoteChange());

                            baseChainBean.setValue(summaryBean.getLineData().get(0).getCurrentValue().get(0));

                            baseChainBeanList.add(baseChainBean);

                            ChooseItemBean chooseItemBean = new ChooseItemBean();
                            chooseItemBean.setTitle(summaryBean.getTitile());
                            chooseItemBean.setId(summaryBean.getItemName());
                            if(summaryBean.getItemName().equals(Commons.btcHashRate)){
                                chooseItemBean.setChoose(true);
                            }else
                                chooseItemBean.setChoose(false);

                            chooseItemBeanList.add(chooseItemBean);

                        }

                    }

                baseChainTodayAdapter.setNewData(baseChainBeanList);

                chooseItemAdapter.setNewData(chooseItemBeanList);

                initHead(powerResp.getResult().getTotalHashRate());

                color_map.put(Commons.price,R.color.F398C0);
                color_map.put(Commons.btcHashRate,R.color.piechat1);
                color_map.put(Commons.bchHashRate,R.color.piechat4);
                color_map.put(Commons.ltcHashRate,R.color.A1DF79);

                ethcolor_map.put(Commons.price,R.color.F398C0);
                ethcolor_map.put(Commons.ethHashRate,R.color.piechat1);

                markViewAdapter = new MarkViewAdapter(color_map);

                GridLayoutManager  gridLayoutManager1 = new GridLayoutManager(getContext(), 2);

                recycle_markerview.setLayoutManager(gridLayoutManager1);

                recycle_markerview.setAdapter(markViewAdapter);

                ethmarkViewAdapter = new MarkViewAdapter(ethcolor_map);

                GridLayoutManager  gridLayoutManager2 = new GridLayoutManager(getContext(), 2);

                ethrecycle_markerview.setLayoutManager(gridLayoutManager2);

                ethrecycle_markerview.setAdapter(ethmarkViewAdapter);

            }

        }

    }

    private void chooseData(List<DotBean> dot, String itemName) {

        if(choosesize!=-1){
            if(dot.size()>choosesize){
                //首次展示近30天的数据
                initItemData(dot,itemName);
            }else
                initAllData(dot,itemName);
        }else
            initAllData(dot,itemName);


    }

    private void initItemData(List<DotBean> dot, String type) {
        DotBean dotBean=null;

        for(int n=dot.size()-choosesize;n<dot.size();n++){
            dotBean = new DotBean();
            dotBean.setTime(dot.get(n).getTime());
            dotBean.setValue(dot.get(n).getValue());
            if(type.equals(Commons.btcPrice)||type.equals(Commons.bchPrice)||type.equals(Commons.ltcPrice)){
                dot_price.add(dotBean);
            }else if(type.equals(Commons.btcHashRate)){
                dot_btcrate.add(dotBean);
            }else if(type.equals(Commons.bchHashRate)){
                dot_bchrate.add(dotBean);
            }else if(type.equals(Commons.ltcHashRate)){
                dot_ltcrate.add(dotBean);
            }else if(type.equals(Commons.ethPrice)){
                dot_ethprice.add(dotBean);
            }else if(type.equals(Commons.ethHashRate)){
                dot_ethrate.add(dotBean);
            }

        }
    }

    private void initAllData(List<DotBean> dot, String type) {

        DotBean dotBean=null;

        for(int n=0;n<dot.size();n++){
            dotBean = new DotBean();
            dotBean.setTime(dot.get(n).getTime());
            dotBean.setValue(dot.get(n).getValue());
            if(type.equals(Commons.btcPrice)||type.equals(Commons.bchPrice)||type.equals(Commons.ltcPrice)){
                dot_price.add(dotBean);
            }else if(type.equals(Commons.btcHashRate)){
                dot_btcrate.add(dotBean);
            }else if(type.equals(Commons.bchHashRate)){
                dot_bchrate.add(dotBean);
            }else if(type.equals(Commons.ltcHashRate)){
                dot_ltcrate.add(dotBean);
            }else if(type.equals(Commons.ethPrice)){
                dot_ethprice.add(dotBean);
            }else if(type.equals(Commons.ethHashRate)){
                dot_ethrate.add(dotBean);
            }
        }
    }


    List<String> xValue = new ArrayList<>();


    ArrayList<Entry> pointValues = new ArrayList<>();//价格
    ArrayList<Entry> pointValues1 = new ArrayList<>();//btc算力
    ArrayList<Entry> pointValues2 = new ArrayList<>();//bch算力
    ArrayList<Entry> pointValues3 = new ArrayList<>();//ltc算力
    Map<String,List<Entry>> point_list = new HashMap<>();

    private void setmLineChartData(List<DotBean> list,List<DotBean> list1,List<DotBean> list2,List<DotBean> list3) {


        float frist_Yvalue = 0;
        float y=0,y1=0,y2=0,y3=0;
        xValue.clear();
        pointValues.clear();
        pointValues1.clear();
        pointValues2.clear();
        pointValues3.clear();

        int count=0;
        for (int i = list1.size()-list.size(); i < list1.size(); i++) {

            if (list.get(count) != null && !TextUtils.isEmpty(list.get(count).getValue()) && !"null".equals(list.get(count).getValue())) {
                if (MyUtils.isNumeric(list.get(count).getValue())) {
                    String price = MyUtils.getUnit()?list.get(count).getValue():String.valueOf(Float.parseFloat(list.get(count).getValue()));
                    y = Float.parseFloat(price);
                    pointValues.add(new Entry((float) i, y));
                }
            }
            count++;
        }


        for (int i = 0; i < list1.size(); i++) {

            if(list1.get(i)!=null&&list1.get(i).getTime()!=null){
                xValue.add(list1.get(i).getTime());
            }

            if (!TextUtils.isEmpty(list1.get(i).getValue()) && !"null".equals(list1.get(i).getValue())) {
                if (MyUtils.isNumeric(list1.get(i).getValue())) {
                    y1 = Float.parseFloat(list1.get(i).getValue());
                    pointValues1.add(new Entry((float) i, y1));
                }

            }
        }


        int count2=0;
        for (int i = list1.size()-list2.size(); i < list1.size(); i++) {

            if (list2.get(count2) != null && !TextUtils.isEmpty(list2.get(count2).getValue()) && !"null".equals(list2.get(count2).getValue())) {
                if (MyUtils.isNumeric(list2.get(count2).getValue())) {
                    y2 = Float.parseFloat(list2.get(count2).getValue());
                    pointValues2.add(new Entry((float) i, y2));
                }
            }
            count2++;
        }

        int count3=0;
        for (int i = list1.size()-list3.size(); i < list1.size(); i++) {

            if (list3.get(count3) != null && !TextUtils.isEmpty(list3.get(count3).getValue()) && !"null".equals(list3.get(count3).getValue())) {
                if (MyUtils.isNumeric(list3.get(count3).getValue())) {
                    y3 = Float.parseFloat(list3.get(count3).getValue());
                    pointValues3.add(new Entry((float) i, y3));
                }
            }
            count3++;
        }

        List<ILineDataSet> dataSets = new ArrayList<>();
        if(pointValues.size()!=0){

            //点构成的某条线
            lineDataSet = new LineDataSet(pointValues,"" );
            lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            lineDataSet.setColor(getResources().getColor(R.color.F398C0));
            lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
            lineDataSet.setLineWidth(0.8f);
            lineDataSet.setFillAlpha(40);
            lineDataSet.setDrawCircles(false);
            lineDataSet.setDrawFilled(false);
           // lineDataSet.setFillDrawable(getResources().getDrawable(R.drawable.line_background01));
            lineDataSet.setHighLightColor(getResources().getColor(R.color.F398C0));
            lineDataSet.enableDashedHighlightLine(5f,5f,0);
            lineDataSet.setDrawValues(false);
            dataSets.add(lineDataSet);
            point_list.put(Commons.price,pointValues);
            xyMarkerView.setChoose_title(Commons.price);
        }


        if(pointValues1.size()!=0){
            lineDataSet = new LineDataSet(pointValues1,"" );
            lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            lineDataSet.setColor(getResources().getColor(R.color.piechat1));
            lineDataSet.setLineWidth(0.8f);
            lineDataSet.setFillAlpha(40);
            lineDataSet.setDrawCircles(false);
            lineDataSet.setDrawFilled(false);
            lineDataSet.enableDashedHighlightLine(5f,5f,0);
            //lineDataSet1.setFillDrawable(getResources().getDrawable(R.drawable.line_background06));
            lineDataSet.setHighLightColor(getResources().getColor(R.color.piechat1));
            lineDataSet.setDrawValues(false);
            dataSets.add(lineDataSet);
            point_list.put(Commons.btcHashRate,pointValues1);
            xyMarkerView.setChoose_title(Commons.btcHashRate);
        }

        if(pointValues2.size()!=0){
            lineDataSet = new LineDataSet(pointValues2,"" );
            lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            lineDataSet.setColor(getResources().getColor(R.color.piechat4));
            lineDataSet.setLineWidth(0.8f);
            lineDataSet.setFillAlpha(40);
            lineDataSet.setDrawCircles(false);
            lineDataSet.setDrawFilled(false);
            lineDataSet.enableDashedHighlightLine(5f,5f,0);
           // lineDataSet2.setFillDrawable(getResources().getDrawable(R.drawable.line_background05));
            lineDataSet.setHighLightColor(getResources().getColor(R.color.piechat4));
            lineDataSet.setDrawValues(false);
            dataSets.add(lineDataSet);
            point_list.put(Commons.bchHashRate,pointValues2);
            xyMarkerView.setChoose_title(Commons.bchHashRate);
        }

        if(pointValues3.size()!=0){
            lineDataSet = new LineDataSet(pointValues3,"" );
            lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            lineDataSet.setColor(getResources().getColor(R.color.piechat3));
            lineDataSet.setLineWidth(0.8f);
            lineDataSet.setFillAlpha(40);
            lineDataSet.setDrawCircles(false);
            lineDataSet.setDrawFilled(false);
            lineDataSet.enableDashedHighlightLine(5f,5f,0);
            lineDataSet.setHighLightColor(getResources().getColor(R.color.piechat3));
            lineDataSet.setDrawValues(false);
            dataSets.add(lineDataSet);
            point_list.put(Commons.ltcHashRate,pointValues3);
            xyMarkerView.setChoose_title(Commons.ltcHashRate);
        }
        //把要画的所有线(线的集合)添加到LineData里
            lineData = new LineData(dataSets);


        if(lineChartEntity!=null){

            mLineChart.setData(lineData);

            lineChartEntity.initXValue(xValue);

            mLineChart.invalidate();

            mLineChart.animateX(1000);

        }else{
            lineChartEntity = new LineChartEntity(getContext(), mLineChart, lineData, xValue, "day", "");

            lineChartEntity.setLegendEnabled(false);

            lineChartEntity.showRightY(false,"");

        }

        xyMarkerView.setLineData(point_list);
        xyMarkerView.setColormap(color_map);

        xyMarkerView.setIAxisValueFormatter(xValue);


    }

    List<String> xValue_eth = new ArrayList<>();


    //每个点的坐标,自己随便搞点（x,y）坐标就可以了
    ArrayList<Entry> pointValues_eth = new ArrayList<>();//价格
    ArrayList<Entry> pointValues1_eth = new ArrayList<>();//算力
    List<ILineDataSet> dataSets_eth = new ArrayList<>();

    Map<String,List<Entry>> ethpoint_list = new HashMap<>();

    private void setEthmLineChartData(List<DotBean> list,List<DotBean> list1) {

        xValue_eth.clear();
        pointValues_eth.clear();
        pointValues1_eth.clear();
        dataSets_eth.clear();
        float frist_Yvalue = 0;

        float y=0,y1=0;
        int count=0;
        for (int i = list1.size()-list.size(); i < list1.size(); i++) {
            if(!TextUtils.isEmpty(list.get(count).getValue())&&!"null".equals(list.get(count).getValue())){
                if(MyUtils.isNumeric(list.get(count).getValue())){
                    String price = MyUtils.getUnit()?list.get(count).getValue():String.valueOf(Float.parseFloat(list.get(count).getValue()));
                    y = Float.parseFloat(price);
                    pointValues_eth.add(new Entry((float) i, y));
                }
            }
            count++;

        }
        for (int i = 0; i < list1.size(); i++) {

            if(list1.get(i)!=null&&list1.get(i).getTime()!=null){
                xValue_eth.add(list1.get(i).getTime());
            }

            if(!TextUtils.isEmpty(list1.get(i).getValue())&&!"null".equals(list1.get(i).getValue())){
                if(MyUtils.isNumeric(list1.get(i).getValue())){
                    y1 = Float.parseFloat(list1.get(i).getValue());

                }
            }
            pointValues1_eth.add(new Entry((float) i, y1));
        }



        if(pointValues_eth.size()!=0){

            //点构成的某条线
            lineDataSet_eth = new LineDataSet(pointValues_eth,"" );
            lineDataSet_eth.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            lineDataSet_eth.setColor(getResources().getColor(R.color.F398C0));
            lineDataSet_eth.setAxisDependency(YAxis.AxisDependency.RIGHT);
            lineDataSet_eth.setLineWidth(0.8f);
            lineDataSet_eth.setFillAlpha(40);
            lineDataSet_eth.setDrawCircles(false);
            lineDataSet_eth.setDrawFilled(false);
            // lineDataSet.setFillDrawable(getResources().getDrawable(R.drawable.line_background01));
            lineDataSet_eth.setHighLightColor(getResources().getColor(R.color.F398C0));
            lineDataSet_eth.enableDashedHighlightLine(5f,5f,0);
            lineDataSet_eth.setDrawValues(false);
            dataSets_eth.add(lineDataSet_eth);
            ethpoint_list.put(Commons.price,pointValues_eth);
            ethxyMarkerView.setChoose_title(Commons.price);
        }


        if(pointValues1_eth.size()!=0){
            lineDataSet_eth = new LineDataSet(pointValues1_eth,"" );
            lineDataSet_eth.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            lineDataSet_eth.setColor(getResources().getColor(R.color.piechat1));
            lineDataSet_eth.setLineWidth(0.8f);
            lineDataSet_eth.setFillAlpha(40);
            lineDataSet_eth.setDrawCircles(false);
            lineDataSet_eth.setDrawFilled(false);
            lineDataSet_eth.enableDashedHighlightLine(5f,5f,0);
            //lineDataSet1.setFillDrawable(getResources().getDrawable(R.drawable.line_background06));
            lineDataSet_eth.setHighLightColor(getResources().getColor(R.color.piechat1));
            lineDataSet_eth.setDrawValues(false);
            dataSets_eth.add(lineDataSet_eth);
            ethpoint_list.put(Commons.ethHashRate,pointValues1_eth);
            ethxyMarkerView.setChoose_title(Commons.ethHashRate);
        }


        //把要画的所有线(线的集合)添加到LineData里
         lineData_eth = new LineData(dataSets_eth);



        if(lineChartEntity_eth!=null){

            mLineChart2.setData(lineData_eth);

            lineChartEntity_eth.initXValue(xValue_eth);

            mLineChart2.invalidate();

            mLineChart2.animateX(1000);

        }else{
            lineChartEntity_eth = new LineChartEntity(getContext(), mLineChart2, lineData_eth, xValue_eth, "day", "");

            lineChartEntity_eth.setLegendEnabled(false);

            lineChartEntity_eth.showRightY(false, Commons.price);

        }

        ethxyMarkerView.setLineData(ethpoint_list);
        ethxyMarkerView.setColormap(ethcolor_map);

        ethxyMarkerView.setIAxisValueFormatter(xValue_eth);



    }


    @Override
    public void onChain(SummaryResp summaryResp) {

    }

    @Override
    public void onTopPlayer(TopFreqAddrResp topFreqAddrResp) {

    }

    private void initHead(TotalHashRateBean totalHashRateBean) {

        if(totalHashRateBean!=null){

            if(!TextUtils.isEmpty(totalHashRateBean.getValue())) {

                text_value.setText(MyUtils.fmtMicrometer(totalHashRateBean.getValue())+"EH/s");

            }


            if(!TextUtils.isEmpty(totalHashRateBean.getQuoteChange())) {

                if(totalHashRateBean.getQuoteChange().contains("-")){
                    text_quote.setText("↓"+MyUtils.fmtMicrometer(totalHashRateBean.getQuoteChange().replace("-",""))+"%");
//                    text_quote.setTextColor(getContext().getResources().getColor(R.color.red));
                    if(MyUtils.getQuateSymbol()){
                        text_quote.setTextColor(getContext().getResources().getColor(R.color.faa3c));
                    }else
                        text_quote.setTextColor(getContext().getResources().getColor(R.color.red));
                }else{

//                    text_quote.setTextColor(getContext().getResources().getColor(R.color.faa3c));
                    if(MyUtils.getQuateSymbol()){
                        text_quote.setTextColor(getContext().getResources().getColor(R.color.red));
                    }else
                        text_quote.setTextColor(getContext().getResources().getColor(R.color.faa3c));
                    text_quote.setText("↑"+MyUtils.fmtMicrometer(totalHashRateBean.getQuoteChange())+"%");
                }
            }


        }


    }

    @Override
    protected void lazyLoad() {

        if(isViewCreated&&isUIVisible) {
            //数据加载完毕,恢复标记,防止重复加载
            twinklingRefreshLayout.startRefresh();
            isViewCreated = false;
            isUIVisible = false;
        }

    }

    @Override
    protected void onChangeEvent(int type) {

        if(type== LanguageType.UNIT_CNY||type== LanguageType.UNIT_USD){
            price_title = MyUtils.getUnit()?getString(R.string.price_us):getString(R.string.price_cny);

            price_text.setText(price_title);

            eth_price_title.setText(price_title);

            twinklingRefreshLayout.startRefresh();

        }



    }

    @Override
    public void onMarketRate(MarketRateResp marketRateResp) {

    }

    @Override
    public void onMarketTrend(ValueResp valueResp) {

    }



    @Override
    public void onError() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if(mLineChart!=null){
            mLineChart.highlightValue(null);
        }

    }
}
