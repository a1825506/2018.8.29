package com.gikee.app.activity;

import android.content.Intent;
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
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.adapter.PopAllprojectAdapter;
import com.gikee.app.adapter.TransationLegendAdapter;
import com.gikee.app.adapter.ZhangHuTopListAdapter;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.beans.ZhanghuPopBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.greendao.SQLiteUtils;
import com.gikee.app.greendao.TrandBean;
import com.gikee.app.language.MultiLanguageUtil;
import com.gikee.app.presenter.project.AccountPresenter;
import com.gikee.app.presenter.project.AccountView;
import com.gikee.app.resp.IntroInfoResp;
import com.gikee.app.resp.OwnerDistributeResp;
import com.gikee.app.resp.Top100Bean;
import com.gikee.app.resp.Top100Resp;
import com.gikee.app.resp.TopFreqAddrResp;
import com.gikee.app.resp.TradeCountDisResp;
import com.gikee.app.resp.TradeVolDisResp;
import com.gikee.app.resp.ValueResp;
import com.gikee.app.share.ShowShareDialog;
import com.gikee.app.views.IconView;
import com.gikee.app.views.MyPieChart;
import com.gikee.app.views.MyRefreshHeader;
import com.gikee.app.views.PieChartEntity;
import com.gikee.app.views.PopMenuMore;
import com.gikee.app.views.PopMenuMoreItem;
import com.gikee.app.views.dialogs.InfoDialog;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.qqtheme.framework.picker.DatePicker;

/**
 * Top100持币分布详情页面
 *
 * 上面拼图 下面表格
 * */

public class Top100CurrencyActivity extends BaseActivity<AccountPresenter> implements AccountView {
    private int timeType = 1;//对应list_pop的id
    private int clickType = 2;//0.间隔1天、1.间隔<=7、2.7<间隔<30、3.间隔>30
    private String coinSymbol = "", date_chose_little = "2009-06-17", date_chose_big = "2009-07-17";
    private TextView tx_title1, tx_title2, tx_title3, tx_title4,tx_choosedate,nodata, tx_minute, tx_hour, tx_day, tx_mouth, tx_week, tx_numdes;
    private String choseType = "day";//minute.5分钟、hour.小时、day  .日、week.周、month.月
    private RelativeLayout top100;
    private MyPieChart pieChart;

    private TwinklingRefreshLayout twinklingRefreshLayout;
    private ZhangHuTopListAdapter adapter;
    private TransationLegendAdapter adapter_legend;
    private RecyclerView recyclerView, recyclerView_legend;
    private LinearLayout lin_poplayout;
    private RecyclerView re_timeinterval;
    private TextView tx_start, tx_end;
    private TextView btn_reset, btn_sure,time;
    private IconView call_back,info,img_date,img_more;
    private List<ZhanghuPopBean> list_pop = new ArrayList<>();
    private AccountPresenter mPresenter;
    private LinearLayout lin_des;
    private Date dateToday;
    private TextView tx_title_1, tx_title_2, tx_title_3, tx_title_4;
    private PopMenuMore mMenu;
    private static final int USER_SHARE = 0;
    private static final int USER_INFO = 1;
    private List<TrandBean> trandBeanList = new ArrayList<>();
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_all_shuju_today_add);
        initMenu();
    }

    @Override
    protected void onChangeEvent(int type) {

    }


    @Override
    protected void setUpView() {


        hideTitleBar();
        mPresenter = new AccountPresenter(this);
        try {
            dateToday = new Date();
            coinSymbol = getIntent().getStringExtra("id");
            String title = coinSymbol+" "+getString(R.string.TOP00);
            date_chose_big = "2018-11-01:00:00:00";
            ((TextView) findViewById(R.id.all_shuju_today_add_title)).setText(title);
        } catch (Exception e) {
        }


        top100 = findViewById(R.id.top100);
        twinklingRefreshLayout = findViewById(R.id.all_shuju_today_add_refreshLayout);
        MyRefreshHeader headerView = new MyRefreshHeader(Top100CurrencyActivity.this);
        twinklingRefreshLayout.setEnableLoadmore(false);
        twinklingRefreshLayout.setHeaderView(headerView);
        recyclerView = findViewById(R.id.all_shuju_today_add_recyclerview);
        nodata = findViewById(R.id.nodata);


        lin_des = findViewById(R.id.today_add_center);


        tx_numdes = findViewById(R.id.all_shuju_today_add_des);
        img_date = findViewById(R.id.all_shuju_today_add_date);
        img_more = findViewById(R.id.all_shuju_today_add_right);
        call_back = findViewById(R.id.all_shuju_today_add_back);
       // img_date.setImageResource(R.mipmap.screen);

        lin_poplayout = findViewById(R.id.all_shuju_zhanghu_pop_layout);
        re_timeinterval = findViewById(R.id.all_shuju_zhanghu_pop_recycler);
        re_timeinterval.setVisibility(View.GONE);

        findViewById(R.id.all_shuju_zhanghu_pop_timedes).setVisibility(View.GONE);

        findViewById(R.id.all_shuju_zhanghu_pop_enddes).setVisibility(View.GONE);
        tx_title_1 = findViewById(R.id.zhanghu_title1);
        tx_title_2 = findViewById(R.id.zhanghu_title2);
        tx_title_3 = findViewById(R.id.zhanghu_title3);
        tx_title_4 = findViewById(R.id.zhanghu_title4);
        tx_title_1.setText(getResources().getString(R.string.ranking));
        tx_title_2.setText(getResources().getString(R.string.address));
        tx_title_3.setText(getResources().getString(R.string.volume)+"("+coinSymbol+")");
        tx_title_4.setText(getResources().getString(R.string.ratio));


        tx_choosedate = findViewById(R.id.all_shuju_zhanghu_pop_startdes);
        tx_choosedate.setText(getString(R.string.choosedate));
        tx_start = findViewById(R.id.all_shuju_zhanghu_pop_start);
        tx_end = findViewById(R.id.all_shuju_zhanghu_pop_end);
        tx_end.setVisibility(View.GONE);
        tx_numdes = findViewById(R.id.all_shuju_zhanghu_num_des);
        btn_reset = findViewById(R.id.all_shuju_zhanghu_pop_rechose);
        btn_sure = findViewById(R.id.all_shuju_zhanghu_pop_sure);
        initPop();


        View view = LayoutInflater.from(Top100CurrencyActivity.this).inflate(R.layout.view_zhanghufenbu_header, null);
        time = view.findViewById(R.id.time);
        time.setText(date_chose_big.substring(0,10));
        info = view.findViewById(R.id.all_shuju_today_add_info);
        pieChart = view.findViewById(R.id.zhanghu_fenbu_piechart);
        recyclerView_legend = view.findViewById(R.id.zhanghu_fenbu_piechart_legend);
        tx_title1 = view.findViewById(R.id.zhanghu_fenbu_title1);
        tx_title2 = view.findViewById(R.id.zhanghu_fenbu_title2);
        tx_title3 = view.findViewById(R.id.zhanghu_fenbu_title3);
        tx_title4 = view.findViewById(R.id.zhanghu_fenbu_title4);


        tx_title1.setText(getResources().getString(R.string.ranking));
        tx_title2.setText(getResources().getString(R.string.address));
        tx_title3.setText(getResources().getString(R.string.volume)+"("+coinSymbol+")");
        tx_title4.setText(getResources().getString(R.string.ratio));
        tx_title4.setVisibility(View.VISIBLE);
        recyclerView_legend.setLayoutManager(new GridLayoutManager(Top100CurrencyActivity.this, 6));
        recyclerView.setLayoutManager(new LinearLayoutManager(Top100CurrencyActivity.this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration line = new DividerItemDecoration(Top100CurrencyActivity.this, DividerItemDecoration.VERTICAL);
        line.setDrawable(ContextCompat.getDrawable(this, R.drawable.line_gray));
        recyclerView.addItemDecoration(line);
        adapter = new ZhangHuTopListAdapter();
        adapter_legend = new TransationLegendAdapter();
        adapter.addHeaderView(view);
        recyclerView.setAdapter(adapter);
        recyclerView_legend.setAdapter(adapter_legend);

        twinklingRefreshLayout.startRefresh();


    }

    private void initMenu() {

        mMenu = new PopMenuMore(this);
        // mMenu.setCorner(R.mipmap.demand_icon_location);
        // mMenu.setBackgroundColor(Color.parseColor("#ff8800"));
        ArrayList<PopMenuMoreItem> items = new ArrayList<>();
//        items.add(new PopMenuMoreItem(USER_SHARE, R.mipmap.share_gray,getString(R.string.pop_share)));
//        // items.add(new PopMenuMoreItem(USER_INFO, R.mipmap.icon_right,getString(R.string.profile)));

        items.add(new PopMenuMoreItem(USER_SHARE,getString(R.string.pop_share)));
        items.add(new PopMenuMoreItem(USER_INFO,getString(R.string.collect)));

        mMenu.addItems(items);
        mMenu.setOnItemSelectedListener(new PopMenuMore.OnItemSelectedListener() {
            @Override
            public void selected(View view, PopMenuMoreItem item, int position) {
                switch (item.id) {
                    case USER_SHARE:
                        ShowShareDialog showShareDialog = new ShowShareDialog();

                        Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.mipmap.bottom_share );
                        Bitmap bmp2 = MyUtils.shotActivityNoBar(Top100CurrencyActivity.this);
                        showShareDialog.setImagePath(MyUtils.newBitmap(bmp2,bmp));
                        showShareDialog.setShareType(ShowShareDialog.share_img);
                        showShareDialog.show(getSupportFragmentManager(),Top100CurrencyActivity.this);

                        break;
                    case USER_INFO:
                        trandBeanList= SQLiteUtils.getInstance().getTrand(coinSymbol);
                        for(int i=0;i<trandBeanList.size();i++){
                            if(trandBeanList.get(i).getTrandid().equals(Commons.top100)){

                                if(!trandBeanList.get(i).getIscollect()){
                                    //暂未收藏
                                    TrandBean trandBean = new TrandBean();

                                    trandBean.setIscollect(true);

                                    trandBean.setId(trandBeanList.get(position).getId());

                                    trandBean.setSymbol(trandBeanList.get(position).getSymbol());

                                    trandBean.setTrandname(trandBeanList.get(position).getTrandname());

                                    trandBean.setTrandname_en(trandBeanList.get(position).getTrandname_en());

                                    trandBean.setTrandid(trandBeanList.get(position).getTrandid());

                                    SQLiteUtils.getInstance().updateTrand(trandBean);

                                }

                                ToastUtils.showCenter(getString(R.string.added));

                            }

                        }

                        break;
                }
            }
        });
    }

    protected void getNetData() {

        mPresenter.getTop100(coinSymbol,date_chose_big);
    }



    private int[] labelscolors;//颜色集合
    private String[] labels;//标签文本

    private void initPiechart(List<Top100Bean> list) {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
            float otherRatio=0;
            if(list.size()>100){
                for (int i = 0; i < 100; i++) {
                    if(i<5) {
                        if(!TextUtils.isEmpty(list.get(i).getRatio())){
                            otherRatio = otherRatio+ Float.parseFloat(list.get(i).getRatio())*100;
                            entries.add(new PieEntry(Float.parseFloat(list.get(i).getRatio() + "") * 100, ""));
                        }
                    }
                }
            }else{
                for (int i = 0; i < list.size(); i++) {

                    if(i<5){
                        if(!TextUtils.isEmpty(list.get(i).getRatio())){
                            otherRatio = otherRatio+ Float.parseFloat(list.get(i).getRatio())*100;
                            entries.add(new PieEntry( Float.parseFloat(list.get(i).getRatio()+"")*100, ""));
                        }
                    }
                }
            }
        entries.add(new PieEntry(100-otherRatio, getString(R.string.other_a)));



        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.piechat1));
        colors.add(getResources().getColor(R.color.piechat2));
        colors.add(getResources().getColor(R.color.piechat3));
        colors.add(getResources().getColor(R.color.piechat4));
        colors.add(getResources().getColor(R.color.piechat6));
        colors.add(getResources().getColor(R.color.piechat5));

        PieChartEntity pieChartEntity = new PieChartEntity(pieChart,entries,null,colors,13f,R.color.gray_33,PieDataSet.ValuePosition.OUTSIDE_SLICE,"");
        pieChartEntity.setLegendEnabled(false);


    }

    private void initPop() {
        list_pop.clear();
        list_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.zh_week)));
        list_pop.add(new ZhanghuPopBean(true, getResources().getString(R.string.zh_month)));
        list_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.zh_twomonth)));
        list_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.zh_halfyear)));
        list_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.zh_year)));
        PopAllprojectAdapter adapter = new PopAllprojectAdapter();
        GridLayoutManager layoutManager = new GridLayoutManager(Top100CurrencyActivity.this, 3);
        adapter.getData().addAll(list_pop);
        re_timeinterval.setLayoutManager(layoutManager);
        re_timeinterval.setAdapter(adapter);
        tx_start.setText(date_chose_big);
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
            switch (timeType) {
                case 0:
                    tx_end.setText(MyUtils.getOldDate(new SimpleDateFormat("yyyy-MM-dd").parse(tx_start.getText().toString()), 7));
                    break;
                case 1:
                    tx_end.setText(MyUtils.getOldDate(new SimpleDateFormat("yyyy-MM-dd").parse(tx_start.getText().toString()), 30));
                    break;
                case 2:
                    tx_end.setText(MyUtils.getOldDate(new SimpleDateFormat("yyyy-MM-dd").parse(tx_start.getText().toString()), 60));
                    break;
                case 3:
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = sdf.parse(tx_start.getText().toString());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
//                    if (calendar.get(Calendar.MONTH) + 7 > 12) {
//                        calendar.set(calendar.YEAR, 1);
//                        calendar.set(calendar.MONTH, -5);
//                    } else
//                        calendar.set(calendar.MONTH, 7);
                    calendar.add(calendar.MONTH, 6);
                    tx_end.setText(sdf.format(calendar.getTime()));
                    break;
                case 4:
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                    Date date1 = sdf1.parse(tx_start.getText().toString());
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(date1);
                    calendar1.add(calendar1.YEAR, 1);
                    tx_end.setText(sdf1.format(calendar1.getTime()));
                    break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
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


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView re, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (adapter.getData().size() < 1)
                    return;
                if (((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition() >= 1) {
                    lin_des.setVisibility(View.VISIBLE);
                } else if (((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition() == 0 &&
                        ((LinearLayoutManager) recyclerView.getLayoutManager()).findViewByPosition(1).getTop() < MyUtils.dip2px(40))
                    lin_des.setVisibility(View.VISIBLE);
                else
                    lin_des.setVisibility(View.GONE);
            }
        });

        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                getNetData();
            }
        });

        call_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        img_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                starAnimEnter();
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
                    DatePicker picker = new DatePicker(Top100CurrencyActivity.this);
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
                            String str = year + "-" + month + "-" + day+" 00:00:00";
                            tx_start.setText(str);
                            Drawable drawableRight = getResources().getDrawable(
                                    R.mipmap.icon_bottom);
                            tx_start.setCompoundDrawablesWithIntrinsicBounds(null,
                                    null, drawableRight, null);
                            tx_start.setCompoundDrawablePadding(MyUtils.dip2px(15));
                            //getEndTime();
                        }
                    });
                    picker.show();
                } catch (ParseException e) {
                    e.printStackTrace();
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
               // date_chose_little = tx_start.getText().toString();
                date_chose_big = tx_start.getText().toString();
                long days = MyUtils.dateDiff(date_chose_little, date_chose_big, "yyyy-MM-dd");

               // setUI();
                twinklingRefreshLayout.startRefresh();
                time.setText(date_chose_big.substring(0,10));
                endAnimOut();
            }
        });

        findViewById(R.id.all_shuju_zhanghu_pop_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endAnimOut();
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取简介
                String language = "";

                Locale savedLanguageType = MultiLanguageUtil.getInstance().getLanguageLocale();
                if (savedLanguageType == Locale.ENGLISH) {
                    language = "en";
                } else if (savedLanguageType == Locale.SIMPLIFIED_CHINESE) {
                    language = "ch_zn";
                } else {
                    language = "en";
                }
                mPresenter.getIntroInfo(Commons.top100,language);
            }
        });


        adapter.setOnAddressClick(new ZhangHuTopListAdapter.OnAddressClick() {
            @Override
            public void onClickAddress(String address) {

                Intent intent = new Intent(getApplicationContext(), AddressDetailActivity.class);

                intent.putExtra("address",address);

                intent.putExtra("type","ETH");

                startActivity(intent);

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
    public void onTradeVolDis(TradeVolDisResp resp) {

    }

    @Override
    public void onTradeCountDis(TradeCountDisResp resp) {

    }

    @Override
    public void onOwnerDistribute(OwnerDistributeResp resp) {

    }

    @Override
    public void ontop(Top100Resp items) {

        twinklingRefreshLayout.finishRefreshing();

        if(TextUtils.isEmpty(items.getErrInfo())){

            if(items.getResult().getData().size()>0){

                adapter.getData().clear();
                recyclerView.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.GONE);
                initPiechart(items.getResult().getData());
                adapter.getData().addAll(items.getResult().getData());
                adapter.notifyDataSetChanged();

            }else{
                recyclerView.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
            }


        }else{
            recyclerView.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);
        }


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

            new InfoDialog(Top100CurrencyActivity.this,R.style.dialog,resp.getResult()).setTitle().show();

        }

    }

    @Override
    public void onError() {
        twinklingRefreshLayout.finishRefreshing();
    }
}
