package com.gikee.app.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.adapter.OwnerDistributeAdapter;
import com.gikee.app.adapter.PopAllprojectAdapter;
import com.gikee.app.adapter.TransationLegendAdapter;
import com.gikee.app.adapter.ZhangHuListAdapter;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.beans.LegendBean;
import com.gikee.app.beans.ZhangHuBean;
import com.gikee.app.beans.ZhanghuPopBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.language.MultiLanguageUtil;
import com.gikee.app.preference_config.PreferenceUtil;
import com.gikee.app.presenter.project.AccountPresenter;
import com.gikee.app.presenter.project.AccountView;
import com.gikee.app.resp.ActiveAccountResp;
import com.gikee.app.resp.AllAddCountResp;
import com.gikee.app.resp.AllGasResp;
import com.gikee.app.resp.AvgGasResp;
import com.gikee.app.resp.AvgTrdVolResp;
import com.gikee.app.resp.BigTradeCountDisResp;
import com.gikee.app.resp.DiffPowerResp;
import com.gikee.app.resp.IntroInfoResp;
import com.gikee.app.resp.LastTradeCountDisBean;
import com.gikee.app.resp.MarketValueResp;
import com.gikee.app.resp.NewAndInactivityResp;
import com.gikee.app.resp.OwnerDistributeBean;
import com.gikee.app.resp.OwnerDistributeResp;
import com.gikee.app.resp.Top100Resp;
import com.gikee.app.resp.TopFreqAddrResp;
import com.gikee.app.resp.TradeCountDisBean;
import com.gikee.app.resp.TradeCountDisResp;
import com.gikee.app.resp.TradeDetailResp;
import com.gikee.app.resp.TradeResp;
import com.gikee.app.resp.TradeVolDisBean;
import com.gikee.app.resp.TradeVolDisResp;
import com.gikee.app.resp.ValueResp;
import com.gikee.app.share.ShowShareDialog;
import com.gikee.app.type.ShowType;
import com.gikee.app.views.IconView;
import com.gikee.app.views.LineChartEntity;
import com.gikee.app.views.MyLineChart;
import com.gikee.app.views.MyPieChart;
import com.gikee.app.views.MyRefreshHeader;
import com.gikee.app.views.PieChartEntity;
import com.gikee.app.views.PopMenuMore;
import com.gikee.app.views.PopMenuMoreItem;
import com.gikee.app.views.dialogs.InfoDialog;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.qqtheme.framework.picker.DatePicker;

public class OwnerDistributeActivity extends BaseActivity<AccountPresenter> implements AccountView,OnChartValueSelectedListener {

    private int timeType = 1;//对应list_pop的id
    private int clickType = 2;//0.间隔1天、1.间隔<=7、2.7<间隔<30、3.间隔>30
    private String coinSymbol = "0xBTC", date_chose_little = "2009-06-17", date_chose_big = "2009-07-17";
    private String choseType = "day";//minute.5分钟、hour.小时、day  .日、week.周、month.月
    private TextView tx_title1, tx_title2, tx_title3, tx_title4, nodata,tx_minute, tx_hour, tx_day, tx_mouth, tx_week, tx_numdes;
    private MyLineChart mLineChart;
    private MyPieChart pieChart;
    private IconView img_more;
    private IconView img_date, call_back,info_img;
    private TwinklingRefreshLayout twinklingRefreshLayout;
    private OwnerDistributeAdapter adapter;
    private RecyclerView recyclerView,recyclerView_legend;
    private static final int USER_SHARE = 0;
    private static final int USER_INFO = 1;
    private LinearLayout lin_poplayout;
    private RecyclerView re_timeinterval;
    private TextView tx_start, tx_end;
    private TextView btn_reset, btn_sure;
    private List<ZhanghuPopBean> list_pop = new ArrayList<>();
    private TransationLegendAdapter adapter_legend;
    private AccountPresenter mPresenter;
    private String title="";
    private String type="";
    List<String> xValue = new ArrayList<>();
    private LineChartEntity lineChartEntity;
    private String[] Legendtitle=new String[5];
    private Date dateToday;
    private PopMenuMore mMenu;
    private float hugecount=0,bigcount=0,mediumcount=0,smallcoumt=0,tinycount=0;
    private PieChartEntity pieChartEntity;
    private List<OwnerDistributeBean> ownerDistributeBeanList;//原始数据源
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pie_line_table);
        initMenu();
    }

    @Override
    protected void onChangeEvent(int type) {

    }


    @Override
    protected void setUpView() {

        hideTitleBar();
        mPresenter=new AccountPresenter(this);
        try {
            coinSymbol = getIntent().getStringExtra("id");
            title = coinSymbol+" "+getIntent().getStringExtra("title");
            type = getIntent().getStringExtra("type");
            dateToday = new Date();
            date_chose_big = MyUtils.getCurrectDate() + "";
            date_chose_little = MyUtils.getOldDate(dateToday,-30) + "";
        } catch (Exception e) {
        }

        ((TextView) findViewById(R.id.all_shuju_zhanghu_num_title)).setText(title);

        twinklingRefreshLayout = findViewById(R.id.all_shuju_zhanghu_num_refreshLayout);
        nodata = findViewById(R.id.nodata);
        MyRefreshHeader headerView = new MyRefreshHeader(OwnerDistributeActivity.this);
        twinklingRefreshLayout.setEnableLoadmore(false);
        twinklingRefreshLayout.setHeaderView(headerView);
        recyclerView = findViewById(R.id.all_shuju_zhanghu_num_recyclerview);
        img_date = findViewById(R.id.all_shuju_zhanghu_num_date);
        img_more = findViewById(R.id.all_shuju_zhanghu_num_right);
        call_back = findViewById(R.id.all_shuju_zhanghu_num_back);

        lin_poplayout = findViewById(R.id.all_shuju_zhanghu_pop_layout);
        re_timeinterval = findViewById(R.id.all_shuju_zhanghu_pop_recycler);
        tx_start = findViewById(R.id.all_shuju_zhanghu_pop_start);
        tx_end = findViewById(R.id.all_shuju_zhanghu_pop_end);
        tx_numdes = findViewById(R.id.all_shuju_zhanghu_num_des);
        btn_reset = findViewById(R.id.all_shuju_zhanghu_pop_rechose);
        btn_sure = findViewById(R.id.all_shuju_zhanghu_pop_sure);
        findViewById(R.id.shuju_zhanghu_linechart_layout).setVisibility(View.GONE);
        initPop();

        View view = LayoutInflater.from(OwnerDistributeActivity.this).inflate(R.layout.view_piechat_linechat, null,false);
        mLineChart = (MyLineChart)view.findViewById(R.id.shuju_zhanghu_linechart);
        mLineChart.setOnChartValueSelectedListener(this);
        info_img = view.findViewById(R.id.all_shuju_today_add_info);
        pieChart = view.findViewById(R.id.zhanghu_fenbu_piechart);
        tx_title1 = view.findViewById(R.id.zhanghu_fenbu_title1);
        tx_title2 = view.findViewById(R.id.zhanghu_fenbu_title2);
        tx_title3 = view.findViewById(R.id.zhanghu_fenbu_title3);
        tx_title4 = view.findViewById(R.id.zhanghu_fenbu_title4);

        tx_minute = view.findViewById(R.id.shuju_zhanghu_minute);
        tx_day = view.findViewById(R.id.shuju_zhanghu_day);
        tx_hour = view.findViewById(R.id.shuju_zhanghu_hour);
        tx_week = view.findViewById(R.id.shuju_zhanghu_week);
        tx_mouth = view.findViewById(R.id.shuju_zhanghu_month);


        recyclerView_legend = view.findViewById(R.id.zhanghu_fenbu_piechart_legend);
        recyclerView_legend.setLayoutManager(new GridLayoutManager(OwnerDistributeActivity.this, 5));

        recyclerView.setLayoutManager(new LinearLayoutManager(OwnerDistributeActivity.this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration line = new DividerItemDecoration(OwnerDistributeActivity.this, DividerItemDecoration.VERTICAL);
        line.setDrawable(ContextCompat.getDrawable(this, R.drawable.line_gray));
        recyclerView.addItemDecoration(line);
        adapter = new OwnerDistributeAdapter();
        adapter.addHeaderView(view);
        adapter_legend = new TransationLegendAdapter();
        recyclerView_legend.setAdapter(adapter_legend);
        recyclerView.setAdapter(adapter);
        setUI();
        twinklingRefreshLayout.startRefresh();
        showTitleBarBtn();



    }

    private void showTitleBarBtn() {

       if(Commons.topDis.equals(type)){

            tx_title1.setText(R.string.address_rank);
            tx_title2.setText(getString(R.string.volume)+"("+coinSymbol+")");
            tx_title3.setText(R.string.ratio);
            tx_title4.setVisibility(View.GONE);

           Legendtitle[0]="Top1-10";
           Legendtitle[1]="Top11-50";
           Legendtitle[2]="Top51-100";
           Legendtitle[3]="Top101-1000";
           Legendtitle[4]=getString(R.string.other_a);

           List<LegendBean> legendlist = new ArrayList<>();

           for(int i=0;i<Legendtitle.length;i++){
               LegendBean legendBean = new LegendBean();

               legendBean.setLegendName(Legendtitle[i]);

               legendlist.add(legendBean);

           }

           adapter_legend.setNewData(legendlist);

        }




    }

    private void initMenu() {

        mMenu = new PopMenuMore(this);
        // mMenu.setCorner(R.mipmap.demand_icon_location);
        // mMenu.setBackgroundColor(Color.parseColor("#ff8800"));
        ArrayList<PopMenuMoreItem> items = new ArrayList<>();
        items.add(new PopMenuMoreItem(USER_SHARE, R.mipmap.share_gray,getString(R.string.pop_share)));
        // items.add(new PopMenuMoreItem(USER_INFO, R.mipmap.icon_right,getString(R.string.profile)));
        mMenu.addItems(items);
        mMenu.setOnItemSelectedListener(new PopMenuMore.OnItemSelectedListener() {
            @Override
            public void selected(View view, PopMenuMoreItem item, int position) {
                switch (item.id) {
                    case USER_SHARE:
                        ShowShareDialog showShareDialog = new ShowShareDialog();
                        Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.mipmap.bottom_share );
                        Bitmap bmp2 = MyUtils.shotActivityNoBar(OwnerDistributeActivity.this);
                        showShareDialog.setImagePath(MyUtils.newBitmap(bmp2,bmp));
                        showShareDialog.setShareType(ShowShareDialog.share_img);
                        showShareDialog.show(getSupportFragmentManager(),OwnerDistributeActivity.this);

                        break;
                    case USER_INFO:
                        break;
                }
            }
        });
    }

    private void getNetData() {

            if(Commons.topDis.equals(type)){

                mPresenter.getOwnerDistribute(coinSymbol,date_chose_little,date_chose_big,choseType);
            }


    }



    private void setmLineChartData(List<OwnerDistributeBean>list) {

      //  initLineChart(list);

        hugecount=0;
        bigcount=0;
        mediumcount=0;
        smallcoumt=0;
        tinycount=0;

        xValue.clear();
        ArrayList<Entry> pointValues = new ArrayList<>();
        ArrayList<Entry> pointValues2 = new ArrayList<>();
        ArrayList<Entry> pointValues3 = new ArrayList<>();
        ArrayList<Entry> pointValues4 = new ArrayList<>();
       ArrayList<Entry> pointValues5 = new ArrayList<>();
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();




            hugecount =  hugecount+Float.parseFloat(list.get(list.size()-1).getTop10Ratio());
            bigcount =  bigcount+Float.parseFloat(list.get(list.size()-1).getTop50Ratio());
            mediumcount =  mediumcount+Float.parseFloat(list.get(list.size()-1).getTop100Ratio());
            smallcoumt =  smallcoumt+Float.parseFloat(list.get(list.size()-1).getTop1000Ratio());
            tinycount =  100-hugecount*100-bigcount*100-mediumcount*100-smallcoumt*100;




        entries.add(new PieEntry(hugecount*100, "", null));

        entries.add(new PieEntry(bigcount*100, "", null));

        entries.add(new PieEntry(mediumcount*100,  "", null));

        entries.add(new PieEntry(smallcoumt*100, "", null));

        entries.add(new PieEntry(tinycount,  "", null));





        float y=0,y2=0,y3=0,y4=0;
        for (int i = 0; i < list.size(); i++) {
            if(!TextUtils.isEmpty(list.get(i).getTime())){
                xValue.add(list.get(i).getTime());
            }

            if(!TextUtils.isEmpty(list.get(i).getTop10Ratio())){
                y = Float.parseFloat(list.get(i).getTop10Ratio())*100;
                pointValues.add(new Entry((float) i, y));
            }

            if(!TextUtils.isEmpty(list.get(i).getTop50Ratio())){
                y2 = Float.parseFloat(list.get(i).getTop50Ratio())*100+y;
                pointValues2.add(new Entry((float) i, y2));
            }

            if(!TextUtils.isEmpty(list.get(i).getTop100Ratio())){
                y3 =  Float.parseFloat(list.get(i).getTop100Ratio())*100+y2;
                pointValues3.add(new Entry((float)i, y3));
            }

            if(!TextUtils.isEmpty(list.get(i).getTop1000Ratio())){
                y4 = Float.parseFloat(list.get(i).getTop1000Ratio())*100+y3;
                pointValues4.add(new Entry((float)i, y4));
            }



            float y5 = 100;
            pointValues5.add(new Entry((float)i, y5));
        }



        LineDataSet lineDataSet1 = new LineDataSet(pointValues5, Legendtitle[4]);
        lineDataSet1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet1.setLineWidth(0.8f);
        lineDataSet1.setHighLightColor(Color.rgb(244, 117, 117));
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setDrawFilled(true);
        lineDataSet1.setColor(getResources().getColor(R.color.piechat5));
        lineDataSet1.setFillDrawable(getResources().getDrawable(R.drawable.line_background05));
        lineDataSet1.setDrawValues(false);




        LineDataSet lineDataSet2 = new LineDataSet(pointValues4, Legendtitle[3]);
        lineDataSet2.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet2.setFillDrawable(getResources().getDrawable(R.drawable.line_background04));
        lineDataSet2.setColor(getResources().getColor(R.color.piechat4));
        lineDataSet2.setLineWidth(0.8f);
        lineDataSet2.setHighLightColor(Color.rgb(244, 117, 117));
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setDrawFilled(true);
        lineDataSet2.setDrawValues(false);


        LineDataSet lineDataSet3 = new LineDataSet(pointValues3, Legendtitle[2]);
        lineDataSet3.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet3.setColor(getResources().getColor(R.color.piechat3));
        lineDataSet3.setFillDrawable(getResources().getDrawable(R.drawable.line_background03));
        lineDataSet3.setLineWidth(0.8f);
        lineDataSet3.setHighLightColor(Color.rgb(244, 117, 117));
        lineDataSet3.setDrawCircles(false);
        lineDataSet3.setDrawFilled(true);
        lineDataSet3.setDrawValues(false);

        LineDataSet lineDataSet4 = new LineDataSet(pointValues2, Legendtitle[1]);
        lineDataSet4.setColor(getResources().getColor(R.color.piechat2));
        lineDataSet4.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet4.setFillDrawable(getResources().getDrawable(R.drawable.line_background02));
        lineDataSet4.setLineWidth(0.8f);
        lineDataSet4.setHighlightEnabled(false);
        lineDataSet4.setHighLightColor(Color.rgb(244, 117, 117));
        lineDataSet4.setDrawCircles(false);
        lineDataSet4.setDrawFilled(true);
        lineDataSet4.setDrawValues(false);

        LineDataSet lineDataSet5 = new LineDataSet(pointValues, Legendtitle[0]);
        lineDataSet5.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet5.setColor(getResources().getColor(R.color.piechat1));
        lineDataSet5.setFillDrawable(getResources().getDrawable(R.drawable.line_background01));
        lineDataSet5.setLineWidth(0.8f);
        lineDataSet5.setHighLightColor(Color.rgb(244, 117, 117));
        lineDataSet5.setDrawCircles(false);
        lineDataSet5.setDrawFilled(true);
        lineDataSet5.setDrawValues(false);

        List<ILineDataSet> dataSets = new ArrayList<>();
        //线的集合（可单条或多条线）
        dataSets.add(lineDataSet1);
        dataSets.add(lineDataSet2);
        dataSets.add(lineDataSet3);
        dataSets.add(lineDataSet4);
        dataSets.add(lineDataSet5);
        LineData lineData = new LineData(dataSets);

        lineChartEntity = new LineChartEntity(OwnerDistributeActivity.this,mLineChart,lineData,xValue,choseType,type);
        // 设置是否可以触摸
        mLineChart.setTouchEnabled(true);

        // 是否可以拖拽
        mLineChart.setDragEnabled(true);
        // 是否可以缩放
        mLineChart.setScaleEnabled(false);

        mLineChart.setHighlightPerDragEnabled(true);

        lineChartEntity.setLegendEnabled(false);
        lineChartEntity.showRightY(false,type);

      //  mLineChart.setData(lineData);

        ArrayList<Integer> colors = new ArrayList<>();
//        colors.add(getResources().getColor(R.color.FFAF2C));
//        colors.add(getResources().getColor(R.color.A1DF79));
//        colors.add(getResources().getColor(R.color.C2F5));
//        colors.add(getResources().getColor(R.color.F398C0));
//        colors.add(getResources().getColor(R.color.DFDFE4));

        colors.add(getResources().getColor(R.color.piechat1));
        colors.add(getResources().getColor(R.color.piechat2));
        colors.add(getResources().getColor(R.color.piechat3));
        colors.add(getResources().getColor(R.color.piechat4));
        colors.add(getResources().getColor(R.color.piechat5));

        pieChartEntity = new PieChartEntity(pieChart,entries,null,colors,13f,R.color.gray_33,PieDataSet.ValuePosition.OUTSIDE_SLICE,"");
        pieChartEntity.setLegendEnabled(false);

        if(!TextUtils.isEmpty(list.get(list.size()-1).getTime())){
            if(list.get(list.size()-1).getTime().length()>10){
                pieChart.setCenterText(list.get(list.size()-1).getTime().substring(5,10));
            }
        }

      //  pieChart.setCenterText(list.get(list.size()-1).getTime().substring(5,10));

    }


    private void initPop() {
        list_pop.clear();
        list_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.zh_week)));
        list_pop.add(new ZhanghuPopBean(true, getResources().getString(R.string.zh_month)));
        list_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.zh_twomonth)));
        list_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.zh_halfyear)));
        list_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.zh_year)));
        PopAllprojectAdapter adapter = new PopAllprojectAdapter();
        GridLayoutManager layoutManager = new GridLayoutManager(OwnerDistributeActivity.this, 3);
        adapter.getData().addAll(list_pop);
        re_timeinterval.setLayoutManager(layoutManager);
        re_timeinterval.setAdapter(adapter);
        tx_start.setText(date_chose_little);
        tx_end.setText(date_chose_big);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ((List<ZhanghuPopBean>) adapter.getData()).get(timeType).setChose(false);
                ((List<ZhanghuPopBean>) adapter.getData()).get(position).setChose(true);
                timeType = position;
                adapter.notifyDataSetChanged();
                getEndTime();
            }
        });
    }



    public void getEndTime() {

        try {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Date date = sdf.parse(tx_end.getText().toString());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);


            switch (timeType) {
                case 0:
                    //date_chose_little = MyUtils.getOldDate(new SimpleDateFormat("yyyy-MM-dd").parse(tx_end.getText().toString()), -7);
                    tx_start.setText(MyUtils.getOldDate(new SimpleDateFormat("yyyy-MM-dd").parse(tx_end.getText().toString()), -7));
                    break;
                case 1:
                    // date_chose_little = MyUtils.getOldDate(new SimpleDateFormat("yyyy-MM-dd").parse(tx_end.getText().toString()), -30);
                    tx_start.setText(MyUtils.getOldDate(new SimpleDateFormat("yyyy-MM-dd").parse(tx_end.getText().toString()), -30));
                    break;
                case 2:
                    date_chose_little = MyUtils.getOldDate(new SimpleDateFormat("yyyy-MM-dd").parse(tx_end.getText().toString()), -60);
                    tx_start.setText(MyUtils.getOldDate(new SimpleDateFormat("yyyy-MM-dd").parse(tx_end.getText().toString()), -60));
                    break;
                case 3:
                    date_chose_little = MyUtils.getOldDate(new SimpleDateFormat("yyyy-MM-dd").parse(tx_end.getText().toString()), -182);
                    tx_start.setText(MyUtils.getOldDate(new SimpleDateFormat("yyyy-MM-dd").parse(tx_end.getText().toString()), -182));
                    break;
                case 4:
                    date_chose_little = MyUtils.getOldDate(new SimpleDateFormat("yyyy-MM-dd").parse(tx_end.getText().toString()), -365);
                    tx_start.setText(MyUtils.getOldDate(new SimpleDateFormat("yyyy-MM-dd").parse(tx_end.getText().toString()), -365));
                    break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private void setUI() {

        switch (clickType) {
            case 0:
                tx_minute.setBackgroundResource(R.drawable.btn_gray_white);
                tx_mouth.setBackgroundResource(R.drawable.btn_gray_white);
                tx_week.setBackgroundResource(R.drawable.btn_gray_white_top_bttom);
                tx_hour.setBackgroundResource(R.drawable.btn_gray_white_top_bttom);
                tx_day.setBackgroundResource(R.drawable.btn_gray_white);
                tx_minute.setTextColor(Color.parseColor("#4a4a4a"));
                tx_hour.setTextColor(Color.parseColor("#c7c7c7"));
                tx_day.setTextColor(Color.parseColor("#c7c7c7"));
                tx_week.setTextColor(Color.parseColor("#c7c7c7"));
                tx_mouth.setTextColor(Color.parseColor("#c7c7c7"));
                if (!choseType.equals("minute")) {
                    choseType = "minute";
                }
                tx_day.setEnabled(false);
                tx_hour.setEnabled(false);
                tx_minute.setEnabled(true);
                tx_week.setEnabled(false);
                tx_mouth.setEnabled(false);
                break;
            case 1:
                tx_minute.setBackgroundResource(R.drawable.btn_gray_white);
                tx_mouth.setBackgroundResource(R.drawable.btn_gray_white);
                tx_week.setBackgroundResource(R.drawable.btn_gray_white_top_bttom);
                tx_hour.setBackgroundResource(R.drawable.btn_gray_white_top_bttom);
                tx_day.setBackgroundResource(R.drawable.btn_gray_white);
                tx_minute.setTextColor(Color.parseColor("#c7c7c7"));
                tx_hour.setTextColor(Color.parseColor("#4a4a4a"));
                tx_day.setTextColor(Color.parseColor("#4a4a4a"));
                tx_week.setTextColor(Color.parseColor("#c7c7c7"));
                tx_mouth.setTextColor(Color.parseColor("#c7c7c7"));
                tx_day.setEnabled(true);
                tx_hour.setEnabled(true);
                if (!choseType.equals("hour") && !choseType.equals("day")) {
                    choseType = "day";
                }
                tx_minute.setEnabled(false);
                tx_week.setEnabled(false);
                tx_mouth.setEnabled(false);
                break;
            case 2:
                tx_minute.setBackgroundResource(R.drawable.btn_gray_white);
                tx_mouth.setBackgroundResource(R.drawable.btn_gray_white);
                tx_week.setBackgroundResource(R.drawable.btn_gray_white);
                tx_hour.setBackgroundResource(R.drawable.btn_gray_white);
                tx_day.setBackgroundResource(R.drawable.btn_gray_white);
                tx_minute.setTextColor(Color.parseColor("#c7c7c7"));
                tx_hour.setTextColor(Color.parseColor("#c7c7c7"));
                tx_day.setTextColor(Color.parseColor("#4a4a4a"));
                tx_week.setTextColor(Color.parseColor("#4a4a4a"));
                tx_mouth.setTextColor(Color.parseColor("#4a4a4a"));

                tx_day.setEnabled(true);
                tx_hour.setEnabled(false);
                tx_minute.setEnabled(false);
                tx_week.setEnabled(true);
                tx_mouth.setEnabled(true);
                break;
            case 3:
                tx_minute.setBackgroundResource(R.drawable.btn_gray_white);
                tx_mouth.setBackgroundResource(R.drawable.btn_gray_white);
                tx_week.setBackgroundResource(R.drawable.btn_gray_white);
                tx_hour.setBackgroundResource(R.drawable.btn_gray_white);
                tx_day.setBackgroundResource(R.drawable.btn_gray_white);
                tx_minute.setTextColor(Color.parseColor("#4a4a4a"));
                tx_hour.setTextColor(Color.parseColor("#4a4a4a"));
                tx_day.setTextColor(Color.parseColor("#4a4a4a"));
                tx_week.setTextColor(Color.parseColor("#4a4a4a"));
                tx_mouth.setTextColor(Color.parseColor("#4a4a4a"));
//                if (!choseType.equals("week") && !choseType.equals("day") && !choseType.equals("month")) {
//                    choseType = "day";
//                }
                tx_day.setEnabled(true);
                tx_hour.setEnabled(true);
                tx_minute.setEnabled(true);
                tx_week.setEnabled(true);
                tx_mouth.setEnabled(true);
                break;
        }
        switch (choseType) {
            case "5min":
                tx_minute.setBackgroundResource(R.drawable.btn_appcolor);
                tx_minute.setTextColor(Color.parseColor("#ffffff"));
                img_date.setVisibility(View.GONE);
                break;
            case "hour":
                tx_hour.setBackgroundResource(R.drawable.btn_appcolor);
                tx_hour.setTextColor(Color.parseColor("#ffffff"));
                break;
            case "day":
                tx_day.setBackgroundResource(R.drawable.btn_appcolor);
                tx_day.setTextColor(Color.parseColor("#ffffff"));
                img_date.setVisibility(View.VISIBLE);
                break;
            case "week":
                tx_week.setBackgroundResource(R.drawable.btn_appcolor);
                tx_week.setTextColor(Color.parseColor("#ffffff"));
                break;
            case "month":
                tx_mouth.setBackgroundResource(R.drawable.btn_appcolor);
                tx_mouth.setTextColor(Color.parseColor("#ffffff"));
                break;
        }
    }

    @Override
    protected void initOnclick() {

        img_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMenu.showAsDropDown(view);
            }
        });

        info_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String language = "";

                Locale savedLanguageType = MultiLanguageUtil.getInstance().getLanguageLocale();
                if (savedLanguageType == Locale.ENGLISH) {
                    language = "en";
                } else if (savedLanguageType == Locale.SIMPLIFIED_CHINESE) {
                    language = "ch_zn";
                } else {
                    language = "en";
                }

                mPresenter.getIntroInfo(type,language);
            }
        });

        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                getNetData();
            }
        });

        img_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                starAnimEnter();
            }
        });

        call_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.all_shuju_zhanghu_pop_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endAnimOut();
            }
        });
        tx_minute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!choseType.equals("5min")) {
                    choseType = "5min";
                    setUI();
                    date_chose_little = MyUtils.getOldDate(dateToday,-Commons.distanceDayHour);

                    twinklingRefreshLayout.startRefresh();
                }
            }
        });
        tx_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!choseType.equals("hour")) {
                    choseType = "hour";
                    setUI();
                    date_chose_little = MyUtils.getOldDate(dateToday,-Commons.distanceDayHour);

                    twinklingRefreshLayout.startRefresh();

                }
            }
        });
        tx_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!choseType.equals("day")) {
                    choseType = "day";
                    setUI();
                  //  date_chose_little = MyUtils.getOldDate(dateToday,-Commons.distanceDayDay);
                    twinklingRefreshLayout.startRefresh();
                }
            }
        });
        tx_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!choseType.equals("week")) {
                    choseType = "week";
                    setUI();
                  //  date_chose_little = MyUtils.getOldDate(dateToday,-Commons.distanceDayWeek);
                    twinklingRefreshLayout.startRefresh();
                }
            }
        });
        tx_mouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!choseType.equals("month")) {
                    choseType = "month";
                    setUI();
                 //   date_chose_little = MyUtils.getOldDate(dateToday,-Commons.distanceDayMouth);

                    twinklingRefreshLayout.startRefresh();
                }
            }
        });
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initPop();
            }
        });
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date_chose_little = tx_start.getText().toString();
                date_chose_big = tx_end.getText().toString();
                long days = MyUtils.dateDiff(date_chose_little, date_chose_big, "yyyy-MM-dd");
//                if (days <= 1) {
//                    clickType = 0;
//                } else if (days <= 7) {
//                    clickType = 1;
//                } else if (7 < days && days <= 30) {
//                    clickType = 2;
//                } else if (days > 30) {
//                    clickType = 3;
//                }
                setUI();
                twinklingRefreshLayout.startRefresh();
                endAnimOut();
            }
        });

        tx_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Drawable drawableRight = getResources().getDrawable(
                            R.mipmap.icon_up);
                    tx_start.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, drawableRight, null);
                    tx_start.setCompoundDrawablePadding(MyUtils.dip2px(15));
                    DatePicker picker = new DatePicker(OwnerDistributeActivity.this);
                    picker.setSubmitTextColor(Color.parseColor("#39384c"));
                    picker.setCancelTextColor(Color.parseColor("#39384c"));
                    picker.setLabel("", "", "");
                    picker.setCancelText(R.string.mp_add_cancle);
                    picker.setSubmitText(R.string.dg_uploadcomplete_sure);
                    picker.setHeight(MyUtils.dip2px(250));
                    picker.setTopLineColor(Color.parseColor("#f7f7f8"));
                    picker.setTopLineHeight(2);
                    picker.setPressedTextColor(Color.parseColor("#39384c"));
                    picker.setDividerColor(Color.parseColor("#39384c"));
                    picker.setTextColor(Color.parseColor("#39384c"));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Time time = new Time();
                    time.setToNow();
                    Date date = null;
                    date = sdf.parse(tx_start.getText().toString());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int monthDay = calendar.get(Calendar.DATE);
                    //设置时间区间
                    picker.setRangeStart(2009, 1, 1);
                    picker.setRangeEnd(time.year, time.month + 1, time.monthDay);
                    //设置默认显示时间
                    picker.setSelectedItem(year, month + 1, monthDay);
//                //加入动画
//                picker.setAnimationStyle(R.style.Animation_CustomPopup);
                    //回传数据
                    picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                        @Override
                        public void onDatePicked(String year, String month, String day) {
                            String str = year + "-" + month + "-" + day+" 00:00:00";;
                            tx_start.setText(str);
                            Drawable drawableRight = getResources().getDrawable(
                                    R.mipmap.icon_bottom);
                            tx_start.setCompoundDrawablesWithIntrinsicBounds(null,
                                    null, drawableRight, null);
                            tx_start.setCompoundDrawablePadding(MyUtils.dip2px(15));
                        }
                    });
                    picker.show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        tx_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Drawable drawableRight = getResources().getDrawable(
                            R.mipmap.icon_up);
                    tx_end.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, drawableRight, null);
                    tx_end.setCompoundDrawablePadding(MyUtils.dip2px(15));
                    DatePicker picker = new DatePicker(OwnerDistributeActivity.this);
                    picker.setSubmitTextColor(Color.parseColor("#39384c"));
                    picker.setCancelTextColor(Color.parseColor("#39384c"));
                    picker.setLabel("", "", "");
                    picker.setCancelText(R.string.mp_add_cancle);
                    picker.setSubmitText(R.string.dg_uploadcomplete_sure);
                    picker.setHeight(MyUtils.dip2px(250));
                    picker.setTopLineColor(Color.parseColor("#f7f7f8"));
                    picker.setTopLineHeight(2);
                    picker.setPressedTextColor(Color.parseColor("#39384c"));
                    picker.setDividerColor(Color.parseColor("#39384c"));
                    picker.setTextColor(Color.parseColor("#39384c"));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Time time = new Time();
                    time.setToNow();
                    Date date = null;
                    date = sdf.parse(tx_end.getText().toString());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int monthDay = calendar.get(Calendar.DATE);
                    Date date1 = null;
                    date1 = sdf.parse(tx_start.getText().toString());
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(date1);
                    //设置时间区间
                    picker.setRangeStart(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH) + 1, calendar1.get(Calendar.DATE));
                    picker.setRangeEnd(time.year, time.month + 1, time.monthDay);
                    //设置默认显示时间
                    picker.setSelectedItem(year, month + 1, monthDay);
//                //加入动画
//                picker.setAnimationStyle(R.style.Animation_CustomPopup);
                    //回传数据
                    picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                        @Override
                        public void onDatePicked(String year, String month, String day) {
                            String str = year + "-" + month + "-" + day+" 00:00:00";
                            tx_end.setText(str);
                            Drawable drawableRight = getResources().getDrawable(
                                    R.mipmap.icon_bottom);
                            tx_end.setCompoundDrawablesWithIntrinsicBounds(null,
                                    null, drawableRight, null);
                            tx_end.setCompoundDrawablePadding(MyUtils.dip2px(15));
                        }
                    });
                    picker.show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

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
        findViewById(R.id.all_shuju_zhanghu_pop_cancle).setVisibility(View.GONE);
    }

    private void starAnimEnter() {
        lin_poplayout.setVisibility(View.VISIBLE);
        for (int i = 0; i < lin_poplayout.getChildCount(); i++) {
            lin_poplayout.getChildAt(i).setVisibility(View.VISIBLE);
        }
        findViewById(R.id.all_shuju_zhanghu_pop_cancle).setVisibility(View.VISIBLE);
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(300);
        animation.setFillAfter(true);
        if (lin_poplayout != null)
            lin_poplayout.startAnimation(animation);
    }


    @Override
    public void onValue(ValueResp valueResp) {

    }

    @Override
    public void onTradeVolDis(TradeVolDisResp items) {



    }

    @Override
    public void onTradeCountDis(TradeCountDisResp items) {




    }


    @Override
    public void onOwnerDistribute(OwnerDistributeResp items) {

        twinklingRefreshLayout.finishRefreshing();


        if(TextUtils.isEmpty(items.getErrInfo())){

            if(items.getResult()!=null) {

                if(items.getResult().getData().size()>0){
                    recyclerView.setVisibility(View.VISIBLE);
                    nodata.setVisibility(View.GONE);
                    List<LastTradeCountDisBean> lastTradeCountDisBeanslist = new ArrayList<>();
                    ownerDistributeBeanList = items.getResult().getData();
                    setmLineChartData(items.getResult().getData());

                    for(int i=0;i<items.getResult().getData().size();i++){
                        if(i==items.getResult().getData().size()-1){

                            for(int j=0;j<5;j++){

                                LastTradeCountDisBean lastTradeCountDisBean = new LastTradeCountDisBean();

                                lastTradeCountDisBean.setType(Commons.topDis);

                                if(j==0){

                                    lastTradeCountDisBean.setValue(items.getResult().getData().get(i).getTop10Ratio());

                                    lastTradeCountDisBean.setValue2(items.getResult().getData().get(i).getTop10Volume());

                                }else if(j==1){

                                    lastTradeCountDisBean.setValue(items.getResult().getData().get(i).getTop50Ratio());

                                    lastTradeCountDisBean.setValue2(items.getResult().getData().get(i).getTop50Volume());

                                }else if(j==2){

                                    lastTradeCountDisBean.setValue(items.getResult().getData().get(i).getTop100Ratio());

                                    lastTradeCountDisBean.setValue2(items.getResult().getData().get(i).getTop100Volume());

                                }else if(j==3){

                                    lastTradeCountDisBean.setValue(items.getResult().getData().get(i).getTop1000Ratio());

                                    lastTradeCountDisBean.setValue2(items.getResult().getData().get(i).getTop1000Volume());

                                }else if(j==4){

                                    //计算其他的百分比和数量

                                    lastTradeCountDisBean.setValue(tinycount/100+"");

                                    float total = Float.parseFloat(items.getResult().getData().get(i).getTop1000Volume())/Float.parseFloat(items.getResult().getData().get(i).getTop1000Ratio());

                                    lastTradeCountDisBean.setValue2((total*(tinycount/100))+"");

                                }


                                lastTradeCountDisBeanslist.add(lastTradeCountDisBean);

                            }

                        }

                    }

                    adapter.setNewData(lastTradeCountDisBeanslist);
                }else
                    showNoDataInfo();

            }else
                showNoDataInfo();

        }else
            showNoDataInfo();

    }



    private void showNoDataInfo() {
        if(choseType.equals("day")){
            recyclerView.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);
        }else{
            String info=getString(R.string.nodata);
            if(choseType.equals("week")){

                info=getString(R.string.noweekdata);

            }else if(choseType.equals("month")){

                info=getString(R.string.nomonthdata);
            }else if("minute".equals(choseType)){
                info=getString(R.string.nominutedata);
            }else if("hour".equals(choseType)){
                info=getString(R.string.nohourdata);
            }
            ToastUtils.show(info);
        }
    }

    @Override
    public void ontop(Top100Resp resp) {

    }

    @Override
    public void TopTrade(TopFreqAddrResp resp) {

    }

    @Override
    public void TopPlayer(TopFreqAddrResp resp) {

    }




    @Override
    public void onntroInfo(IntroInfoResp resp) {

        if(!TextUtils.isEmpty(resp.getResult())){

            new InfoDialog(OwnerDistributeActivity.this,R.style.dialog,resp.getResult()).setTitle().show();

        }

    }

    @Override
    public void onError() {
        twinklingRefreshLayout.finishRefreshing();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        //点击折线图上的点更新饼图和表格的数据
        Log.e("chat",xValue.get((int)e.getX()));
        //更新饼图数据
        hugecount=0;
        bigcount=0;
        mediumcount=0;
        smallcoumt=0;
        tinycount=0;
        hugecount =  hugecount+Float.parseFloat(ownerDistributeBeanList.get((int)e.getX()).getTop10Ratio());
        bigcount =  bigcount+Float.parseFloat(ownerDistributeBeanList.get((int)e.getX()).getTop50Ratio());
        mediumcount =  mediumcount+Float.parseFloat(ownerDistributeBeanList.get((int)e.getX()).getTop100Ratio());
        smallcoumt =  smallcoumt+Float.parseFloat(ownerDistributeBeanList.get((int)e.getX()).getTop1000Ratio());
        tinycount =  100-hugecount*100-bigcount*100-mediumcount*100-smallcoumt*100;
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        entries.add(new PieEntry(hugecount*100, "", null));

        entries.add(new PieEntry(bigcount*100, "", null));

        entries.add(new PieEntry(mediumcount*100,  "", null));

        entries.add(new PieEntry(smallcoumt*100, "", null));

        entries.add(new PieEntry(tinycount,  "", null));

        pieChartEntity.updatePieData(entries);

        if(!TextUtils.isEmpty(ownerDistributeBeanList.get((int)e.getX()).getTime())){
            if(ownerDistributeBeanList.get((int)e.getX()).getTime().length()>10){
                pieChart.setCenterText(ownerDistributeBeanList.get((int)e.getX()).getTime().substring(5,10));
            }
        }


        //更新表格数据
        List<LastTradeCountDisBean> lastTradeCountDisBeanslist = new ArrayList<>();
        for(int j=0;j<5;j++){

            LastTradeCountDisBean lastTradeCountDisBean = new LastTradeCountDisBean();

            lastTradeCountDisBean.setType(Commons.topDis);

            if(j==0){

                lastTradeCountDisBean.setValue(ownerDistributeBeanList.get((int)e.getX()).getTop10Ratio());

                lastTradeCountDisBean.setValue2(ownerDistributeBeanList.get((int)e.getX()).getTop10Volume());

            }else if(j==1){

                lastTradeCountDisBean.setValue(ownerDistributeBeanList.get((int)e.getX()).getTop50Ratio());

                lastTradeCountDisBean.setValue2(ownerDistributeBeanList.get((int)e.getX()).getTop50Volume());

            }else if(j==2){

                lastTradeCountDisBean.setValue(ownerDistributeBeanList.get((int)e.getX()).getTop100Ratio());

                lastTradeCountDisBean.setValue2(ownerDistributeBeanList.get((int)e.getX()).getTop100Volume());

            }else if(j==3){

                lastTradeCountDisBean.setValue(ownerDistributeBeanList.get((int)e.getX()).getTop1000Ratio());

                lastTradeCountDisBean.setValue2(ownerDistributeBeanList.get((int)e.getX()).getTop1000Volume());

            }else if(j==4){

                //计算其他的百分比和数量

                lastTradeCountDisBean.setValue(tinycount/100+"");

                float total = Float.parseFloat(ownerDistributeBeanList.get((int)e.getX()).getTop1000Volume())/Float.parseFloat(ownerDistributeBeanList.get((int)e.getX()).getTop1000Ratio());

                lastTradeCountDisBean.setValue2((total*(tinycount/100))+"");

            }


            lastTradeCountDisBeanslist.add(lastTradeCountDisBean);

        }

        adapter.setNewData(lastTradeCountDisBeanslist);

    }


    @Override
    public void onNothingSelected() {

    }
}
