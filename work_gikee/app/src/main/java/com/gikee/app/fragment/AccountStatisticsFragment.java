package com.gikee.app.fragment;

import android.content.ClipData;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.LayoutInflater;
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
import com.gikee.app.activity.ZhanghuNumActivity;
import com.gikee.app.adapter.AccountListAdapter;
import com.gikee.app.adapter.AssestsDataAdapter;
import com.gikee.app.adapter.PopAllprojectAdapter;
import com.gikee.app.base.GikeeApplication;
import com.gikee.app.beans.AssetBean;
import com.gikee.app.beans.AssetLineBean;
import com.gikee.app.beans.MarkerViewBean;
import com.gikee.app.beans.SpecialAccountBean;
import com.gikee.app.beans.ValueBean;
import com.gikee.app.beans.ZhanghuPopBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.presenter.project.MineProjectPresenter;
import com.gikee.app.presenter.project.MineProjectView;
import com.gikee.app.resp.AddressAddedResp;
import com.gikee.app.resp.AddressCountResp;
import com.gikee.app.resp.AssetResp;
import com.gikee.app.resp.HashTradeResp;
import com.gikee.app.resp.TokensAddedResp;
import com.gikee.app.views.IconView;
import com.gikee.app.views.LineChartEntity;
import com.gikee.app.views.MyLineChart;
import com.gikee.app.views.MyPieChart;
import com.gikee.app.views.MyPieChartEntity;
import com.gikee.app.views.MyRefreshHeader;
import com.gikee.app.views.PieChartEntity;
import com.gikee.app.views.XYMarkerView;
import com.gikee.app.views.dialogs.AccountChooseDialog;
import com.gikee.app.views.dialogs.InfoDialog;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import cn.qqtheme.framework.picker.DatePicker;

public class AccountStatisticsFragment extends BaseFragment<MineProjectPresenter> implements MineProjectView,OnChartValueSelectedListener {

    @Bind(R.id.all_time)
    LinearLayout all_time;
    @Bind(R.id.time_choose_layout)
    LinearLayout time_choose_layout;
    @Bind(R.id.time_recycle)
    RecyclerView time_recycle;
    @Bind(R.id.date_choode)
    TextView date_choode;
    @Bind(R.id.assets_layout)
    RecyclerView assets_layout_recycle;
    @Bind(R.id.start_time)
    TextView start_time;
    @Bind(R.id.end_time)
    TextView end_time;
    @Bind(R.id.all_account)
    LinearLayout all_account;

    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;

    @Bind(R.id.line_chat)
    MyLineChart mLineChart;

    @Bind(R.id.pie_chat)
    PieChart pie_chat;

    @Bind(R.id.account_choose_layout)
    LinearLayout account_choose_layout;

    @Bind(R.id.account_list_recycle)
    RecyclerView account_list_recycle;

    @Bind(R.id.legend_layout)
    LinearLayout legend_layout;

    @Bind(R.id.nocontent_tx)
    TextView nocontent_tx;

    @Bind(R.id.nocontent_layout)
    LinearLayout nocontent_layout;


    @Bind(R.id.pop_rechose)
    TextView btn_reset;

    @Bind(R.id.pop_sure)
    TextView btn_sure;

    @Bind(R.id.choose_background)
    View choose_background;
    @Bind(R.id.pop_cancle)
    View pop_cancle;

    @Bind(R.id.total_vol)
    TextView total_vol_text;

    @Bind(R.id.total_account)
    TextView total_account;

    @Bind(R.id.balance)
    TextView balance_text;

    @Bind(R.id.symbol)
    TextView symbol_text;

    @Bind(R.id.balance_usd)
    TextView balance_usd;

    @Bind(R.id.total_vol_symbol)
    TextView total_vol_symbol;

    @Bind(R.id.total_vol_img)
    IconView total_vol_img;

    @Bind(R.id.total_account_img)
    IconView total_account_img;

    @Bind(R.id.all_account_img)
    ImageView all_account_img;

    @Bind(R.id.all_time_img)
    ImageView all_time_img;


    private boolean isChange=false;

    private List<SpecialAccountBean> mSpecialAccountBean;
    private Thread thread;
    private boolean exit=true;
    private List<ZhanghuPopBean> list_pop = new ArrayList<>();
    private int timeType=0;
    private boolean isshow=false;
    private boolean isgetData=false;
    private String  date_chose_start = "2009-06-17", date_chose_end = "2009-07-17";
    private Map<String,Integer> color_map = new HashMap<>();
    private AccountListAdapter AccountListAdapter;
    private AssestsDataAdapter assestsDataAdapter;
    private Date dateToday;
    private String mbalance;
    private String msymbol;
    private String mtype;

    public static AccountStatisticsFragment getInstance(List<SpecialAccountBean> specialAccountBean ,String balance,String symbol,String type){

        AccountStatisticsFragment accountStatisticsFragment = new AccountStatisticsFragment();

        accountStatisticsFragment.setParam(specialAccountBean,balance,symbol,type);

        return accountStatisticsFragment;

    }

    private void setParam(List<SpecialAccountBean> specialAccountBean,String balance,String symbol,String type){

        mSpecialAccountBean = specialAccountBean;

        mbalance = balance;

        msymbol = symbol;

        mtype = type;

    }

    @Override
    protected void setupViews(LayoutInflater inflater) {

        setContentView(inflater, R.layout.fragment_exchangedetail);



    }

    @Override
    protected void initView() {
        mPresenter = new MineProjectPresenter(this);
        dateToday = new Date();
        date_chose_end = MyUtils.getCurrectDateNoTime() + "";
        date_chose_start = MyUtils.getOldDateNoTime(dateToday,-7) + "";
        start_time.setText(date_chose_start);
        end_time.setText(date_chose_end);
        if("exchange".equals(mtype)){
            all_account.setVisibility(View.VISIBLE);
        }else
            all_account.setVisibility(View.GONE);

        MyRefreshHeader headerView = new MyRefreshHeader(getContext());

        refreshLayout.setAutoLoadMore(false);
        refreshLayout.setHeaderView(headerView);
        refreshLayout.setEnableLoadmore(false);

        mLineChart.setNoDataText(getString(R.string.getdata));

        mLineChart.setOnChartValueSelectedListener(this);

        xyMarkerView = new XYMarkerView(getContext());

        xyMarkerView.setChartView(mLineChart);

        mLineChart.setMarker(xyMarkerView);

        assestsDataAdapter = new AssestsDataAdapter(msymbol);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.line_gray));

        assets_layout_recycle.setLayoutManager(linearLayoutManager);

        assets_layout_recycle.addItemDecoration(divider);

        assets_layout_recycle.setAdapter(assestsDataAdapter);

        color_map.put(Commons.newAccount,R.color.pie4);
        color_map.put(Commons.activeDis,R.color.C2F5);

        initDateChoose();
        initAccountList();
        onClick();
        thread = new MyThread();
        thread.start();

        if(!TextUtils.isEmpty(mbalance)){
            balance_text.setText(MyUtils.fmtMicrometer(mbalance));
            double usd_balance=0;
            if("ETH".equals(msymbol.toUpperCase())){
                usd_balance = MyUtils.getUnit()?(Commons.rateUSD_eth*Double.parseDouble(mbalance)):(Commons.rateCNY_eth*Double.parseDouble(mbalance));
            }else
                usd_balance = MyUtils.getUnit()?(Commons.rateUSD_btc*Double.parseDouble(mbalance)):(Commons.rateCNY_btc*Double.parseDouble(mbalance));

            balance_usd.setText(MyUtils.getUnitSymbol()+MyUtils.getBigNumber(usd_balance));

        }

        if(!TextUtils.isEmpty(msymbol)){
            symbol_text.setText(msymbol.toUpperCase());
            total_vol_symbol.setVisibility(View.VISIBLE);
            total_vol_symbol.setText(msymbol.toUpperCase());
        }else
            total_vol_symbol.setVisibility(View.GONE);







        refreshLayout.startRefresh();
    }

    private void getData() {

        if(mPresenter!=null){

            Map map = new HashMap ();

            List<String> list = new ArrayList<String>();

            List<String> list_time = new ArrayList<String>();

            list_time.add(date_chose_start);

            list_time.add(date_chose_end);


            for(SpecialAccountBean specialAccountBean:mSpecialAccountBean){

                if(specialAccountBean.isChoose()){
                    list.add(specialAccountBean.getAddress_item());
                }


                }


            map.put("type",msymbol.toUpperCase());

            map.put("address",list);

            map.put("time",list_time);



            mPresenter.getAssetData(map);
        }


    }

    private void initAccountList() {

        AccountListAdapter = new AccountListAdapter();
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.line_gray));

        account_list_recycle.addItemDecoration(divider);

        account_list_recycle.setLayoutManager(new LinearLayoutManager(getContext()));

        account_list_recycle.setAdapter(AccountListAdapter);

        AccountListAdapter.setNewData(mSpecialAccountBean);
    }

    private void onClick() {

        total_vol_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new InfoDialog(getContext(),R.style.dialog,getContext().getResources().getString(R.string.total_vol)).setTitle().show();
            }
        });

        total_account_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new InfoDialog(getContext(),R.style.dialog,getContext().getResources().getString(R.string.total_count)).setTitle().show();
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDateChoose();
            }
        });


        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date_chose_start = start_time.getText().toString();
                date_chose_end = end_time.getText().toString();

                refreshLayout.startRefresh();
                endAnimOut();
            }
        });


        choose_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                account_choose_layout.setVisibility(View.GONE);
                if(isgetData){
                    assets_layout_recycle.setVisibility(View.VISIBLE);
                    legend_layout.setVisibility(View.VISIBLE);
                    mLineChart.setVisibility(View.VISIBLE);
                    pie_chat.setVisibility(View.VISIBLE);
                }
                all_account_img.setBackground(getContext().getResources().getDrawable(R.mipmap.down));
                if(isChange){
                    refreshLayout.startRefresh();
                    isChange=false;
                }
            }
        });


        pop_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isshow){
                    isshow=false;
                    endAnimOut();
                    if(isgetData){
                        assets_layout_recycle.setVisibility(View.VISIBLE);
                        legend_layout.setVisibility(View.VISIBLE);
                        mLineChart.setVisibility(View.VISIBLE);
                        pie_chat.setVisibility(View.VISIBLE);
                    }
                    all_time_img.setBackground(getContext().getResources().getDrawable(R.mipmap.down));
                    if(isChange){
                        refreshLayout.startRefresh();
                        isChange=false;
                    }

                }
            }
        });


        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                assets_layout_recycle.setVisibility(View.GONE);

                legend_layout.setVisibility(View.GONE);
                mLineChart.setVisibility(View.GONE);
                pie_chat.setVisibility(View.GONE);
                getData();
            }
        });


        all_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isshow){
                    isshow=false;
                    endAnimOut();
                    if(isgetData){
                        assets_layout_recycle.setVisibility(View.VISIBLE);
                        legend_layout.setVisibility(View.VISIBLE);
                        mLineChart.setVisibility(View.VISIBLE);
                        pie_chat.setVisibility(View.VISIBLE);
                    }

                    all_time_img.setBackground(getContext().getResources().getDrawable(R.mipmap.down));
                }else{
                    isshow=true;
                    starAnimEnter();
                    assets_layout_recycle.setVisibility(View.GONE);

                    legend_layout.setVisibility(View.GONE);

                    mLineChart.setVisibility(View.GONE);

                    pie_chat.setVisibility(View.GONE);

                    all_time_img.setBackground(getContext().getResources().getDrawable(R.mipmap.up));
                }


            }
        });




        all_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(account_choose_layout.getVisibility()==View.GONE){

                    account_choose_layout.setVisibility(View.VISIBLE);

                    assets_layout_recycle.setVisibility(View.GONE);

                    legend_layout.setVisibility(View.GONE);

                    mLineChart.setVisibility(View.GONE);

                    pie_chat.setVisibility(View.GONE);

                    all_account_img.setBackground(getContext().getResources().getDrawable(R.mipmap.up));

                }else{
                    all_account_img.setBackground(getContext().getResources().getDrawable(R.mipmap.down));
                    if(isgetData){
                        assets_layout_recycle.setVisibility(View.VISIBLE);
                        legend_layout.setVisibility(View.VISIBLE);
                        mLineChart.setVisibility(View.VISIBLE);
                        pie_chat.setVisibility(View.VISIBLE);
                    }

                    account_choose_layout.setVisibility(View.GONE);
                    if(isChange){
                        refreshLayout.startRefresh();
                        isChange=false;
                    }
                }


            }
        });



        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Drawable drawableRight = getResources().getDrawable(
                            R.mipmap.icon_up);
                    start_time.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, drawableRight, null);
                    start_time.setCompoundDrawablePadding(MyUtils.dip2px(15));
                    DatePicker picker = new DatePicker(getActivity());
                    picker.setSubmitTextColor(Color.parseColor("#39384c"));
                    picker.setCancelTextColor(Color.parseColor("#39384c"));
                    picker.setCancelText(R.string.mp_add_cancle);
                    picker.setSubmitText(R.string.dg_uploadcomplete_sure);
                    picker.setLabel("", "", "");
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
                    date = sdf.parse(start_time.getText().toString());
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
                            String str = year + "-" + month + "-" + day;
                            start_time.setText(str);
                            Drawable drawableRight = getResources().getDrawable(
                                    R.mipmap.icon_bottom);
                            start_time.setCompoundDrawablesWithIntrinsicBounds(null,
                                    null, drawableRight, null);
                            start_time.setCompoundDrawablePadding(MyUtils.dip2px(15));
                            // getEndTime();
                        }
                    });
                    picker.show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                MyUtils.chooseDate(tx_end,getActivity());
                try {
                    Drawable drawableRight = getResources().getDrawable(
                            R.mipmap.icon_up);
                    end_time.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, drawableRight, null);
                    end_time.setCompoundDrawablePadding(MyUtils.dip2px(15));

                    DatePicker picker = new DatePicker(getActivity());
                    picker.setSubmitTextColor(Color.parseColor("#39384c"));
                    picker.setCancelTextColor(Color.parseColor("#39384c"));
                    picker.setCancelText(R.string.mp_add_cancle);
                    picker.setSubmitText(R.string.dg_uploadcomplete_sure);
                    picker.setLabel("", "", "");
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
                    date = sdf.parse(end_time.getText().toString());
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
                            String str = year + "-" + month + "-" + day;
                            end_time.setText(str);
                            Drawable drawableRight = getResources().getDrawable(
                                    R.mipmap.icon_bottom);
                            end_time.setCompoundDrawablesWithIntrinsicBounds(null,
                                    null, drawableRight, null);
                            end_time.setCompoundDrawablePadding(MyUtils.dip2px(15));
                        }
                    });
                    picker.show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });


        AccountListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.choose_img:
                        boolean ischoose=false;

                        if(AccountListAdapter.getData().get(position).isChoose()){
                            ischoose=false;
                            AccountListAdapter.getData().get(position).setChoose(false);
                        }else{
                            ischoose=true;
                            AccountListAdapter.getData().get(position).setChoose(true);
                        }


                        for(SpecialAccountBean specialAccountBean:mSpecialAccountBean){

                            if(specialAccountBean.getAddress_item().equals( AccountListAdapter.getData().get(position).getAddress_item())){
                                specialAccountBean.setChoose(ischoose);
                            }

                        }

                        isChange=true;


                        AccountListAdapter.notifyItemChanged(position);
                        break;
                    case R.id.copy:
                        String text =  AccountListAdapter.getData().get(position).getAddress_item();
                        ClipData myClip = ClipData.newPlainText("text", text);
                        GikeeApplication.getMyApplicationContext().getMyClipboard().setPrimaryClip(myClip);
                        ToastUtils.show(getString(R.string.copied));
                        break;
                }



            }
        });
    }



    private void initDateChoose() {
        list_pop.clear();
        list_pop.add(new ZhanghuPopBean(true, getResources().getString(R.string.lastweek)));
        list_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.last_30)));
        list_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.last_90)));
        list_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.last_180)));
        list_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.last_year)));
        list_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.last_threeyear)));

        PopAllprojectAdapter adapter = new PopAllprojectAdapter();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        adapter.getData().addAll(list_pop);
        time_recycle.setLayoutManager(layoutManager);
        time_recycle.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ((List<ZhanghuPopBean>) adapter.getData()).get(timeType).setChose(false);

                ((List<ZhanghuPopBean>) adapter.getData()).get(position).setChose(true);
                timeType = position;
                adapter.notifyDataSetChanged();

                int choosesize=30;
//

                if(position==0){
                    date_chose_start = MyUtils.getOldDateNoTime(dateToday,-7) + "";
                }else if(position==1){
                    date_chose_start = MyUtils.getOldDateNoTime(dateToday,-30) + "";
                }else if(position==2){
                    date_chose_start = MyUtils.getOldDateNoTime(dateToday,-90) + "";
                }else if(position==3){
                    date_chose_start = MyUtils.getOldDateNoTime(dateToday,-180) + "";
                }else if(position==4){
                    date_chose_start = MyUtils.getOldDateNoTime(dateToday,-365) + "";
                }else if(position==5){
                    date_chose_start = MyUtils.getOldDateNoTime(dateToday,-(365*3)) + "";
                    choosesize=-1;
                }

                date_choode.setText(((List<ZhanghuPopBean>) adapter.getData()).get(position).getName());

                endAnimOut();

                all_time_img.setBackground(getContext().getResources().getDrawable(R.mipmap.down));

                refreshLayout.startRefresh();

            }
        });
    }


    private void starAnimEnter() {
        time_choose_layout.setVisibility(View.VISIBLE);
        for (int i = 0; i < time_choose_layout.getChildCount(); i++) {
            time_choose_layout.getChildAt(i).setVisibility(View.VISIBLE);
        }
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(300);
        animation.setFillAfter(true);
        if (time_choose_layout != null)
            time_choose_layout.startAnimation(animation);
    }


    private void endAnimOut() {
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(300);
        animation.setFillAfter(true);
        if (time_choose_layout != null)
            time_choose_layout.startAnimation(animation);
        time_choose_layout.setVisibility(View.GONE);
        for (int i = 0; i < time_choose_layout.getChildCount(); i++) {
            time_choose_layout.getChildAt(i).setVisibility(View.GONE);
        }
    }



    @Override
    public void onAssetData(AssetResp resp) {

        refreshLayout.finishRefreshing();

        if(TextUtils.isEmpty(resp.getErrInfo())){

            if(resp.getResult().getAssetdata()!=null){
                if(resp.getResult().getAssetdata().size()!=0){
                    assestsDataAdapter.setNewData(resp.getResult().getAssetdata());
                    total_trade(resp.getResult().getCount_sum()+"",resp.getResult().getValue_sum());
                    setmPieChartData(resp.getResult().getAssetdata());

                }else{

                    showContext(View.GONE);
                }



            }else{

                showContext(View.GONE);
            }


            if(resp.getResult().getLinedata()!=null){

                if(resp.getResult().getLinedata().size()!=0){
                    showContext(View.VISIBLE);
                    isgetData=true;
                    setmLineChartData(resp.getResult().getLinedata());
                }else{
                    isgetData=false;
                    showContext(View.GONE);
                }

            }else{
                isgetData=false;
                showContext(View.GONE);
            }

        }
    }

    private void total_trade(String count,String vol) {


        if(!TextUtils.isEmpty(count)){
            total_vol_text.setText(MyUtils.fmtMicrometer(vol));
        }
        if(!TextUtils.isEmpty(vol)){
            total_account.setText(MyUtils.fmtMicrometer(count));
        }


    }

    private void setmPieChartData(List<AssetBean> linedata) {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        for(int i=0;i<linedata.size();i++){
            float  asset =  (float) linedata.get(i).getAsset();
            String label=null;
            if("in".equals(linedata.get(i).getType())){
                label = getContext().getResources().getString(R.string.asset_transfer);
            }else
                label = getContext().getResources().getString(R.string.asset_transfer_out);

            entries.add(new PieEntry(Float.valueOf(asset), label));

            ArrayList<Integer> colors = new ArrayList<Integer>();

            colors.add(getContext().getResources().getColor(R.color.pie4));

            colors.add(getContext().getResources().getColor(R.color.C2F5));

            PieChartEntity  pieChartEntity = new PieChartEntity(pie_chat,entries,null,colors,11f,R.color.gray_33,PieDataSet.ValuePosition.OUTSIDE_SLICE,msymbol);
            pieChartEntity.setLegendEnabled(false);





        }

    }

    private void showContext(int  isshow) {

        if(isshow==View.GONE){
            nocontent_layout.setVisibility(View.VISIBLE);
        }else{
            nocontent_layout.setVisibility(View.GONE);
        }

        assets_layout_recycle.setVisibility(isshow);

        legend_layout.setVisibility(isshow);
        mLineChart.setVisibility(isshow);
        pie_chat.setVisibility(isshow);

        nocontent_tx.setText(getString(R.string.no_context_time));




    }


    LineDataSet lineDataSet=null,lineDataSet2=null;

    List<String> xValue = new ArrayList<>();

    ArrayList<Entry> pointValues = new ArrayList<>();
    ArrayList<Entry> pointValues2 = new ArrayList<>();
    List<ILineDataSet> dataSets = new ArrayList<>();
    LineData lineData=null;
    private XYMarkerView xyMarkerView;
    private  LineChartEntity   lineChartEntity;

    Map<String,List<Entry>> point_list = new HashMap<>();

    private void setmLineChartData(List<AssetLineBean> list) {

        xValue.clear();
        pointValues.clear();
        pointValues2.clear();
        dataSets.clear();

        for (int i = 0; i < list.size(); i++) {
            float y=0,y2=0;
            if(!TextUtils.isEmpty(list.get(i).getValue_in()+"")&&!"null".equals(list.get(i).getValue_in()+"")&&!TextUtils.isEmpty(list.get(i).getValue_out()+"")&&!"null".equals(list.get(i).getValue_out()+"")){

                if(MyUtils.isNumeric((list.get(i).getValue_in())+"")&&MyUtils.isNumeric((list.get(i).getValue_out())+"")){

                    String value = MyUtils.getUnit()?(list.get(i).getValue_in())+"":String.valueOf(Float.parseFloat((list.get(i).getValue_in())+"")* Commons.rate);

                    String value2 = MyUtils.getUnit()?(list.get(i).getValue_out())+"":String.valueOf(Float.parseFloat((list.get(i).getValue_out())+"")* Commons.rate);

                    y = Float.parseFloat(value);

                    y2 = Float.parseFloat(value2);
                }

                xValue.add(list.get(i).getTime());
                pointValues.add(new Entry((float) i, y));
                pointValues2.add(new Entry((float) i, y2));
            }

        }

        point_list.put(Commons.assest_in,pointValues);

        point_list.put(Commons.assest_out,pointValues2);

        lineDataSet = new LineDataSet(pointValues,"" );
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet.setColor(getResources().getColor(R.color.pie4));
        lineDataSet.setLineWidth(0.8f);
        lineDataSet.setFillAlpha(40);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawFilled(false);
        lineDataSet.enableDashedHighlightLine(5f,5f,0);
        lineDataSet.setHighLightColor(getResources().getColor(R.color.pie4));
        lineDataSet.setDrawValues(false);
        dataSets.add(lineDataSet);

        lineDataSet2 = new LineDataSet(pointValues2,"" );
        lineDataSet2.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet2.setColor(getResources().getColor(R.color.C2F5));
        lineDataSet2.setLineWidth(0.8f);
        lineDataSet2.setFillAlpha(40);
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setDrawFilled(false);
        lineDataSet2.enableDashedHighlightLine(5f,5f,0);
        lineDataSet2.setHighLightColor(getResources().getColor(R.color.C2F5));
        lineDataSet2.setDrawValues(false);
        dataSets.add(lineDataSet2);

        lineData = new LineData(dataSets);


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
        xyMarkerView.setIAxisValueFormatter(xValue);




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

                    if(mLineChart!=null){
                        mLineChart.highlightValue(null);
                    }

                }
            }
            super.handleMessage(msg);

        }
    };

    class MyThread extends Thread {

        @Override
        public void run() {
            while (exit) {
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

        currentTimeMillis =  System.currentTimeMillis();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        exit=false;
    }

    @Override
    public void onNothingSelected() {

    }



    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @Override
    public void onMineProject(TokensAddedResp allProjectCollBean) {

    }

    @Override
    public void onMineAddress(AddressAddedResp resp) {

    }

    @Override
    public void onMineCount(AddressCountResp resp) {

    }

    @Override
    public void onAccountTrade(HashTradeResp resp) {

    }


    @Override
    public void onError() {

    }
}
