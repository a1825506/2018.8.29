package com.gikee.app.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.adapter.MarkViewAdapter;
import com.gikee.app.adapter.PopAllprojectAdapter;
import com.gikee.app.beans.CurrentValueBean;
import com.gikee.app.beans.MarkerViewBean;
import com.gikee.app.beans.ValueBean;
import com.gikee.app.beans.ZhanghuPopBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.language.LanguageType;
import com.gikee.app.presenter.home.HomePresenter;
import com.gikee.app.presenter.home.HomeView;
import com.gikee.app.resp.MarketRateResp;
import com.gikee.app.resp.PowerResp;
import com.gikee.app.resp.SummaryResp;
import com.gikee.app.resp.TopFreqAddrResp;
import com.gikee.app.resp.ValueResp;
import com.gikee.app.views.LineChartEntity;
import com.gikee.app.views.MyLineChart;
import com.gikee.app.views.MyRefreshHeader;
import com.gikee.app.views.XYMarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

public class MarketTrendFragment extends BaseFragment<HomePresenter> implements HomeView,OnChartValueSelectedListener {


    @Bind(R.id.text_value)
    TextView text_value;
    @Bind(R.id.text_quote)
    TextView text_quote;
    @Bind(R.id.text_choosedate)
    TextView text_choosedate;
    @Bind(R.id.text_time)
    TextView text_time;
    @Bind(R.id.layout_choosedate)
    LinearLayout lin_poplayout;
    @Bind(R.id.recycle_data)
    RecyclerView re_timeinterval;
    @Bind(R.id.linechart_market)
    MyLineChart mLineChart;
    @Bind(R.id.layout_time)
    LinearLayout layout_time;

    @Bind(R.id.all_layout)
    RelativeLayout all_layout;

    @Bind(R.id.markerview_layout)
    LinearLayout markerview_layout;
    @Bind(R.id.recycle_markerview)
    RecyclerView recycle_markerview;

    @Bind(R.id.head_layout)
    RelativeLayout head_layout;
    @Bind(R.id.price_layout)
    RelativeLayout price_layout;
    @Bind(R.id.title_price)
    TextView title_price;



    @Bind(R.id.fm_all_shuju_refreshLayout)
    TwinklingRefreshLayout twinklingRefreshLayout;

    private boolean isShowChat=false;


    private boolean isshow=false;
    private int timeType=0;

    private  List<ValueBean> data_all;

    private List<ZhanghuPopBean> list_pop = new ArrayList<>();

    private  LineChartEntity   lineChartEntity;


    private MarkViewAdapter markViewAdapter;

    private Map<String,Integer> color_map = new HashMap<>();

    private XYMarkerView xyMarkerView;

    private String price_title;

    private String date="30day";

    @Override
    protected void setupViews(LayoutInflater inflater) {

        setContentView(inflater, R.layout.fragment_markettrend);



    }

    private void onClick() {



        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {

                getNetData();
            }

        });



        all_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLineChart.highlightValue(null);
            }
        });

        layout_time.setOnClickListener(new View.OnClickListener() {
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


    }



    private void starAnimEnter() {

        lin_poplayout.setVisibility(View.VISIBLE);
        for (int i = 0; i < lin_poplayout.getChildCount(); i++) {
            lin_poplayout.getChildAt(i).setVisibility(View.VISIBLE);
        }
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(300);
        animation.setFillAfter(true);
        if (lin_poplayout != null)
            lin_poplayout.startAnimation(animation);
    }



    private void endAnimOut() {
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(300);
        animation.setFillAfter(true);
        if (lin_poplayout != null)
            lin_poplayout.startAnimation(animation);
        lin_poplayout.setVisibility(View.GONE);
        for (int i = 0; i < lin_poplayout.getChildCount(); i++) {
            lin_poplayout.getChildAt(i).setVisibility(View.GONE);
        }
       // all_shuju_zhanghu_pop_cancle.setVisibility(View.GONE);
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

                int choosesize=30;


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


//                List<ValueBean> data_choose = new ArrayList<>();
//
//                if(choosesize!=-1){
//
//                    if(data_all.size()>choosesize){
//
//                        for(int i=data_all.size()-choosesize;i<data_all.size();i++){
//
//                            data_choose.add(data_all.get(i));
//                        }
//
//                    }else
//                        data_choose = data_all;
//                }else
//                    data_choose = data_all;
//
//
//
//                setmLineChartData(data_choose);

                endAnimOut();

            }
        });
    }

    @Override
    protected void initView() {

        mPresenter = new HomePresenter(this);


        MyRefreshHeader headerView = new MyRefreshHeader(getContext());
        twinklingRefreshLayout.setEnableLoadmore(false);
        twinklingRefreshLayout.setEnableOverScroll(false);
        twinklingRefreshLayout.setHeaderView(headerView);

        text_choosedate.setText(MyUtils.getCurrectDateNoTime());

        mLineChart.setNoDataText(getString(R.string.getdata));

        mLineChart.setOnChartValueSelectedListener(this);

        xyMarkerView = new XYMarkerView(getContext());

        xyMarkerView.setChartView(mLineChart);

        mLineChart.setMarker(xyMarkerView);

        initPop();

        onClick();


        color_map.put(Commons.price,R.color.F398C0);

        markViewAdapter = new MarkViewAdapter(color_map);

        GridLayoutManager  gridLayoutManager1 = new GridLayoutManager(getContext(), 2);

        recycle_markerview.setLayoutManager(gridLayoutManager1);

        recycle_markerview.setAdapter(markViewAdapter);

        price_title = MyUtils.getUnit()?getString(R.string.price_us):getString(R.string.price_cny);

        title_price.setText(price_title);

        new Thread(new MyThread()).start();


    }

    private void getNetData(){

        mPresenter.getMarketTrend(date);
    }

    @Override
    protected void lazyLoad() {

        if(isViewCreated&&isUIVisible) {
            mPresenter.getMarketTrend(date);
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }

    }

    @Override
    protected void onChangeEvent(int type) {

        price_title = MyUtils.getUnit()?getString(R.string.price_us):getString(R.string.price_cny);

        title_price.setText(price_title);

        if(type== LanguageType.UNIT_USD||type== LanguageType.UNIT_CNY){
            twinklingRefreshLayout.startRefresh();
        }



    }

    @Override
    public void onMarketRate(MarketRateResp marketRateResp) {

    }

    @Override
    public void onMarketTrend(ValueResp valueResp) {

        twinklingRefreshLayout.finishRefreshing();

        if(TextUtils.isEmpty(valueResp.getErrInfo())){

            if(valueResp.getResult()!=null){

                head_layout.setVisibility(View.VISIBLE);

                price_layout.setVisibility(View.VISIBLE);

                initHead(valueResp.getResult().getCurrentValue());

               data_all =valueResp.getResult().getData();

//                List<ValueBean> data_choose =data_all;
//
////                if(data_all.size()>30){
////
////                    for(int i=data_all.size()-30;i<data_all.size();i++){
////
////                        data_choose.add(data_all.get(i));
////                    }
////
////                }else
//                    data_choose = data_all;

                //折线图数据
                setmLineChartData(data_all);

            }

        }

    }

    private void initHead(CurrentValueBean currentValue) {

        if(!TextUtils.isEmpty(currentValue.getValue())){
            String value = MyUtils.getUnit()?currentValue.getValue():String.valueOf(Float.parseFloat(currentValue.getValue())*Commons.rate);
            text_value.setText(MyUtils.getUnitSymbol()+""+MyUtils.fmtMicrometer(value));
        }

        if(!TextUtils.isEmpty(currentValue.getQuoteChange())){
            if(currentValue.getQuoteChange().contains("-")){
                if(MyUtils.getQuateSymbol()){
                    text_quote.setTextColor(getContext().getResources().getColor(R.color.faa3c));
                }else
                    text_quote.setTextColor(getContext().getResources().getColor(R.color.red));

                text_quote.setText("↓"+MyUtils.getNumber(currentValue.getQuoteChange())+"%");
            }else{
                if(MyUtils.getQuateSymbol()){
                    text_quote.setTextColor(getContext().getResources().getColor(R.color.red));
                }else
                    text_quote.setTextColor(getContext().getResources().getColor(R.color.faa3c));


//                text_quote.setTextColor(getContext().getResources().getColor(R.color.faa3c));
                text_quote.setText("↑"+MyUtils.getNumber(currentValue.getQuoteChange())+"%");
            }


        }






    }

    @Override
    public void onPower(PowerResp powerResp) {

    }

    @Override
    public void onChain(SummaryResp summaryResp) {

    }

    @Override
    public void onTopPlayer(TopFreqAddrResp topFreqAddrResp) {

    }

    LineDataSet lineDataSet=null;

    List<String> xValue = new ArrayList<>();

    ArrayList<Entry> pointValues = new ArrayList<>();

     Map<String,List<Entry>> point_list = new HashMap<>();

    List<ILineDataSet> dataSets = new ArrayList<>();

    LineData lineData=null;

    private void setmLineChartData(List<ValueBean> list) {


        xValue.clear();
        pointValues.clear();
        dataSets.clear();
        //每个点的坐标,自己随便搞点（x,y）坐标就可以了


        float frist_Yvalue = 0;


        for (int i = 0; i < list.size(); i++) {
            float y=0;
            if(!TextUtils.isEmpty(list.get(i).getValue())&&!"null".equals(list.get(i).getValue())){
                if(MyUtils.isNumeric(list.get(i).getValue())){

                    String value = MyUtils.getUnit()?list.get(i).getValue():String.valueOf(Float.parseFloat(list.get(i).getValue())*Commons.rate);

                    y = Float.parseFloat(value);
                }


                if(i==0){
                    frist_Yvalue = y;
                }
                xValue.add(list.get(i).getTime());
                pointValues.add(new Entry((float) i, y));
            }

        }

        point_list.put(Commons.marketValue,pointValues);

        //点构成的某条线
         lineDataSet = new LineDataSet(pointValues,"" );
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet.setColor(getResources().getColor(R.color.F398C0));
        lineDataSet.setLineWidth(0.8f);
        lineDataSet.setFillAlpha(40);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawFilled(false);
        lineDataSet.enableDashedHighlightLine(5f,5f,0);
        lineDataSet.setHighLightColor(getResources().getColor(R.color.F398C0));
        lineDataSet.setDrawValues(false);
        //线的集合（可单条或多条线）
        dataSets.add(lineDataSet);
        //把要画的所有线(线的集合)添加到LineData里
         lineData = new LineData(dataSets);
        //把最终的数据setData



        if(lineChartEntity!=null){

            mLineChart.setData(lineData);

            lineChartEntity.initXValue(xValue);

            mLineChart.invalidate();

            mLineChart.animateX(1000);

        }else{
            lineChartEntity = new LineChartEntity(getContext(), mLineChart, lineData, xValue, "day", Commons.price);

            lineChartEntity.setLegendEnabled(false);

            lineChartEntity.showRightY(false, Commons.price);
        }

        xyMarkerView.setLineData(point_list);
        xyMarkerView.setColormap(color_map);
        xyMarkerView.setChoose_title(Commons.marketValue);
        xyMarkerView.setIAxisValueFormatter(xValue);




       // }


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


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if(System.currentTimeMillis()-currentTimeMillis>1000){
                    markerview_layout.setVisibility(View.GONE);
                    mLineChart.highlightValue(null);
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

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }



    long currentTimeMillis;


    @Override
    public void onValueSelected(Entry e, Highlight h) {

        Log.e("IndexCompFragment",e.getX()+"");

        currentTimeMillis =  System.currentTimeMillis();


    }

    @Override
    public void onNothingSelected() {

    }
}
