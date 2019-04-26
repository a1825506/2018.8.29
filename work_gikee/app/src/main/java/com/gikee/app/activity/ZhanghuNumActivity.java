package com.gikee.app.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
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
import com.gikee.app.adapter.MarkViewAdapter;
import com.gikee.app.adapter.PopAllprojectAdapter;
import com.gikee.app.adapter.TradeDetailsAdapter;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.beans.MarkerViewBean;
import com.gikee.app.beans.ValueBean;
import com.gikee.app.beans.ZhanghuPopBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.fragment.MarketTrendFragment;
import com.gikee.app.greendao.SQLiteUtils;
import com.gikee.app.greendao.TrandBean;
import com.gikee.app.language.LanguageType;
import com.gikee.app.language.MultiLanguageUtil;
import com.gikee.app.presenter.project.AccountPresenter;
import com.gikee.app.presenter.project.AccountView;
import com.gikee.app.resp.IntroInfoResp;
import com.gikee.app.resp.OwnerDistributeResp;
import com.gikee.app.resp.Top100Resp;
import com.gikee.app.resp.TopFreqAddrResp;
import com.gikee.app.resp.TradeCountDisResp;
import com.gikee.app.resp.TradeVolDisResp;
import com.gikee.app.resp.ValueResp;
import com.gikee.app.resp.ZhangHuNumBean;
import com.gikee.app.share.ShowShareDialog;
import com.gikee.app.views.IconView;
import com.gikee.app.views.LineChartEntity;
import com.gikee.app.views.MyLineChart;
import com.gikee.app.views.MyRefreshHeader;
import com.gikee.app.views.PopMenuMore;
import com.gikee.app.views.PopMenuMoreItem;
import com.gikee.app.views.XYMarkerView;
import com.gikee.app.views.dialogs.InfoDialog;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import cn.qqtheme.framework.picker.DatePicker;
/**
 * Created by THINKPAD on 2018/6/19.
 */

public class ZhanghuNumActivity extends BaseActivity<AccountPresenter> implements OnChartValueSelectedListener,AccountView {

    @Bind(R.id.markerview_layout)
    LinearLayout markerview_layout;
    @Bind(R.id.recycle_markerview)
    RecyclerView recycle_markerview;


    private int timeType = 1;//对应list_pop的id
    private int clickType = 3;//0.间隔1天、1.间隔<=7、2.7<间隔<30、3.间隔>30
    private String coinSymbol = "", date_from = "2018-6-25 00:00:00", date_to = "2018-7-25 00:00:00", date_chose_little = "2009-06-17", date_chose_big = "2009-07-17";
    private String choseType = "day";//minute.5分钟、hour.小时、day  .日、week.周、month.月
    private TextView tx_title1, tx_title2, tx_title3, tx_title4, nodata,tx_minute, tx_hour, tx_day, tx_mouth, tx_week, tx_numdes;
    private MyLineChart mLineChart;
    private IconView  img_more;
    private IconView img_date,info_img;
    private TwinklingRefreshLayout twinklingRefreshLayout;
    private TradeDetailsAdapter adapter;
    private RecyclerView recyclerView;

    private LinearLayout lin_poplayout;
    private RecyclerView re_timeinterval;
    private TextView tx_start, tx_end;
    private TextView btn_reset, btn_sure;
    private List<ZhanghuPopBean> list_pop = new ArrayList<>();
    private AccountPresenter mPresenter;
    private String title;
    private String type;
    private Date dateToday;
    private LineChartEntity lineChartEntity;
    private String[] Legendtitle=new String[2];
    private PopMenuMore mMenu;
    private List<TrandBean> trandBeanList = new ArrayList<>();
    private String id;
    private boolean isBaseLine=false;

    private static final int USER_SHARE = 0;
    private static final int USER_INFO = 1;


    private MarkViewAdapter markViewAdapter;

    private Map<String,Integer> color_map = new HashMap<>();

    private XYMarkerView xyMarkerView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_all_shuju_zhanghu_num);
    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @Override
    protected void setUpView() {
        hideTitleBar();
        mPresenter=new AccountPresenter(this);
        try {
            id =  getIntent().getStringExtra("id");
            coinSymbol = getIntent().getStringExtra("symbol");
            for(int i=0;i<Commons.baseLine.length;i++){
                if(id.equals(Commons.baseLine[i])){
                    isBaseLine=true;
                }

            }

            if(isBaseLine){
                clickType=0;
            }

            type = getIntent().getStringExtra("type");
            title = coinSymbol+" "+getIntent().getStringExtra("title");
            dateToday = new Date();
            date_chose_big = MyUtils.getCurrectDate() + "";
            date_chose_little = MyUtils.getOldDate(dateToday,-7) + "";
        } catch (Exception e) {
        }

//        lin_des = findViewById(R.id.zhanghu_center);
//        tx_title_1 = findViewById(R.id.zhanghu_title1);
//        tx_title_2 = findViewById(R.id.zhanghu_title2);
//        tx_title_3 = findViewById(R.id.zhanghu_title3);
//        tx_title_4 = findViewById(R.id.zhanghu_title4);



        ((TextView) findViewById(R.id.all_shuju_zhanghu_num_title)).setText(title);
        twinklingRefreshLayout = findViewById(R.id.all_shuju_zhanghu_num_refreshLayout);
        nodata = findViewById(R.id.nodata);
        MyRefreshHeader headerView = new MyRefreshHeader(ZhanghuNumActivity.this);
        twinklingRefreshLayout.setEnableLoadmore(false);
        twinklingRefreshLayout.setHeaderView(headerView);
        recyclerView = findViewById(R.id.all_shuju_zhanghu_num_recyclerview);
        img_date = findViewById(R.id.all_shuju_zhanghu_num_date);
        img_more = findViewById(R.id.all_shuju_zhanghu_num_right);
        lin_poplayout = findViewById(R.id.all_shuju_zhanghu_pop_layout);
        re_timeinterval = findViewById(R.id.all_shuju_zhanghu_pop_recycler);
        tx_start = findViewById(R.id.all_shuju_zhanghu_pop_start);
        tx_end = findViewById(R.id.all_shuju_zhanghu_pop_end);
        tx_numdes = findViewById(R.id.all_shuju_zhanghu_num_des);
        btn_reset = findViewById(R.id.all_shuju_zhanghu_pop_rechose);
        btn_sure = findViewById(R.id.all_shuju_zhanghu_pop_sure);
        initPop();

       // View view = LayoutInflater.from(ZhanghuNumActivity.this).inflate(R.layout.view_all_shuju_zhanghu_header, null);
        mLineChart = findViewById(R.id.shuju_zhanghu_linechart);
        mLineChart.setOnChartValueSelectedListener(this);


        xyMarkerView = new XYMarkerView(this);

        xyMarkerView.setChartView(mLineChart);

        mLineChart.setMarker(xyMarkerView);

        RelativeLayout.LayoutParams lp1 = ( RelativeLayout.LayoutParams)mLineChart.getLayoutParams();
        lp1.height = (int)(MyUtils.getHieght()/3);
        mLineChart.setLayoutParams(lp1);

        info_img = findViewById(R.id.all_shuju_today_add_info);
        if(Commons.price.equals(type)){
            info_img.setVisibility(View.INVISIBLE);
        }

        tx_title1 = findViewById(R.id.shuju_zhanghu_title1);
        tx_title2 = findViewById(R.id.shuju_zhanghu_title2);
        tx_title3 = findViewById(R.id.shuju_zhanghu_title3);
        tx_title4 = findViewById(R.id.shuju_zhanghu_title4);
        showTitleBarBtn();

        tx_minute = findViewById(R.id.shuju_zhanghu_minute);
        tx_day = findViewById(R.id.shuju_zhanghu_day);
        tx_hour = findViewById(R.id.shuju_zhanghu_hour);
        tx_week = findViewById(R.id.shuju_zhanghu_week);
        tx_mouth = findViewById(R.id.shuju_zhanghu_month);



        recyclerView.setLayoutManager(new LinearLayoutManager(ZhanghuNumActivity.this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration line = new DividerItemDecoration(ZhanghuNumActivity.this, DividerItemDecoration.VERTICAL);
        line.setDrawable(ContextCompat.getDrawable(this, R.drawable.line_gray));
        recyclerView.addItemDecoration(line);
        adapter = new TradeDetailsAdapter();
       // adapter.addHeaderView(view);
        recyclerView.setAdapter(adapter);
        setUI();
        initMenu();
        twinklingRefreshLayout.startRefresh();



        color_map.put(type,R.color.F398C0);

        markViewAdapter = new MarkViewAdapter(color_map);

        recycle_markerview.setLayoutManager(new LinearLayoutManager(this));

        recycle_markerview.setAdapter(markViewAdapter);

        new Thread(new MyThread()).start();

    }

    private void initMenu() {

        mMenu = new PopMenuMore(this);
        // mMenu.setCorner(R.mipmap.demand_icon_location);
        // mMenu.setBackgroundColor(Color.parseColor("#ff8800"));
        ArrayList<PopMenuMoreItem> items = new ArrayList<>();
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
                        Bitmap bmp2 = MyUtils.shotActivityNoBar(ZhanghuNumActivity.this);
                        showShareDialog.setImagePath(MyUtils.newBitmap(bmp2,bmp));
                        showShareDialog.setShareType(ShowShareDialog.share_img);
                        showShareDialog.show(getSupportFragmentManager(),ZhanghuNumActivity.this);
                        break;
                    case USER_INFO:
                        trandBeanList= SQLiteUtils.getInstance().getTrand(coinSymbol);
                        for(int i=0;i<trandBeanList.size();i++){
                            if(trandBeanList.get(i).getTrandid().equals(type)){

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

    private void showTitleBarBtn() {


        if(Commons.activeDis.equals(type)){

            tx_title1.setText(getResources().getString(R.string.zh_header_date));
            tx_title2.setText(R.string.liveaddressnum);
            tx_title3.setVisibility(View.GONE);
            tx_title4.setVisibility(View.GONE);


        }else if(Commons.inactiveCount.equals(type)){
            clickType=0;
            tx_title1.setText(getResources().getString(R.string.zh_header_date));
            tx_title2.setText(R.string.inactiveCount);
            tx_title3.setVisibility(View.GONE);
            tx_title4.setVisibility(View.GONE);


        }else if(Commons.wakeCount.equals(type)){
            clickType=0;
            tx_title1.setText(getResources().getString(R.string.zh_header_date));
            tx_title2.setText(R.string.wakeCount);
            tx_title3.setVisibility(View.GONE);
            tx_title4.setVisibility(View.GONE);


        }else if(Commons.newAccount.equals(type)){

            tx_title1.setText(getResources().getString(R.string.zh_header_date));
            tx_title2.setText(R.string.newCount);
            tx_title3.setVisibility(View.GONE);
            tx_title4.setVisibility(View.GONE);


        }else if(Commons.avgTrdVol.equals(type)){
            clickType=0;
            tx_title1.setText(getResources().getString(R.string.zh_header_date));
            tx_title2.setText(R.string.jiaoyisliang);
            tx_title3.setVisibility(View.GONE);
            tx_title4.setVisibility(View.GONE);


        }else if(Commons.allGas.equals(type)){

            tx_title1.setText(getResources().getString(R.string.zh_header_date));
            tx_title2.setText(R.string.Handfeetotl);
            tx_title3.setVisibility(View.GONE);
            tx_title4.setVisibility(View.GONE);




        }else if(Commons.avgGas.equals(type)){

            tx_title1.setText(R.string.zh_header_date);
            tx_title2.setText(R.string.Handfee);
            tx_title3.setVisibility(View.GONE);
            tx_title4.setVisibility(View.GONE);





        }else if(Commons.marketValue.equals(type)){
            clickType=0;
            tx_title1.setText(R.string.zh_header_date);
            tx_title2.setText(R.string.value);
            tx_title3.setVisibility(View.GONE);
            tx_title4.setVisibility(View.GONE);

        }else if(Commons.tradeCount.equals(type)){
            tx_title1.setText(getResources().getString(R.string.zh_header_date));
            tx_title2.setText(R.string.ap_transationnum);
            tx_title3.setVisibility(View.GONE);
            tx_title4.setVisibility(View.GONE);


        }else if(Commons.tradevolume.equals(type)){
            tx_title1.setText(getResources().getString(R.string.zh_header_date));
            tx_title2.setText(R.string.tradenum);
            tx_title3.setVisibility(View.GONE);
            tx_title4.setVisibility(View.GONE);


        }else if(Commons.allAddr.equals(type)){

            clickType=0;

            tx_title1.setText(R.string.zh_header_date);
            tx_title2.setText(R.string.alladdress);
            tx_title3.setVisibility(View.GONE);
            tx_title4.setVisibility(View.GONE);



            //Legendtitle[0]=getResources().getString(R.string.onweraddress);
           // Legendtitle[1]=getResources().getString(R.string.alladdress);
        }else if(Commons.ownAddr.equals(type)){
            clickType=0;
            tx_title1.setText(getResources().getString(R.string.zh_header_date));
            tx_title2.setText(R.string.onweraddress);
            tx_title3.setVisibility(View.GONE);
            tx_title4.setVisibility(View.GONE);


        }else if(Commons.bigTradeCountDis.equals(type)){
            tx_title1.setText(getResources().getString(R.string.zh_header_date));
            tx_title2.setText(getString(R.string.HugeNum)+"("+getString(R.string.num_unit)+")");
            tx_title3.setText(getString(R.string.BigNum)+"("+getString(R.string.num_unit)+")");
            tx_title4.setVisibility(View.GONE);
            Legendtitle[0]=getResources().getString(R.string.HugeNum);
            Legendtitle[1]=getResources().getString(R.string.BigNum);



        }else if(Commons.price.equals(type)){
            clickType=0;
            tx_title1.setText(R.string.zh_header_date);
            tx_title2.setText(R.string.price_title);
            tx_title3.setVisibility(View.GONE);
            tx_title4.setVisibility(View.GONE);



        }else if(Commons.participateAddress.equals(type)){
            tx_title1.setText(R.string.zh_header_date);
            tx_title2.setText(R.string.participateAddress);
            tx_title3.setVisibility(View.GONE);
            tx_title4.setVisibility(View.GONE);
        }else if(Commons.avgTrdValue.equals(type)){
            clickType=0;
            tx_title1.setText(R.string.zh_header_date);
            tx_title2.setText(R.string.avgTrdValue);
            tx_title3.setVisibility(View.GONE);
            tx_title4.setVisibility(View.GONE);
        }else if(Commons.tradeValue.equals(type)){
            tx_title1.setText(R.string.zh_header_date);
            tx_title2.setText(R.string.tradeValue);
            tx_title3.setVisibility(View.GONE);
            tx_title4.setVisibility(View.GONE);
        }



    }


    private void initPop() {
        list_pop.clear();
        list_pop.add(new ZhanghuPopBean(true, getResources().getString(R.string.zh_week)));
        list_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.zh_month)));
        list_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.zh_twomonth)));
        list_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.zh_halfyear)));
        list_pop.add(new ZhanghuPopBean(false, getResources().getString(R.string.zh_year)));
        PopAllprojectAdapter adapter = new PopAllprojectAdapter();
        GridLayoutManager layoutManager = new GridLayoutManager(ZhanghuNumActivity.this, 3);
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
            Date date = sdf.parse(tx_start.getText().toString());
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
                    Commons.distanceDayDay=60;
                    Commons.distanceDayWeek=60;
                    Commons.distanceDayMouth=60;
                    date_chose_little = MyUtils.getOldDate(new SimpleDateFormat("yyyy-MM-dd").parse(tx_end.getText().toString()), -60);
                    tx_start.setText(MyUtils.getOldDate(new SimpleDateFormat("yyyy-MM-dd").parse(tx_end.getText().toString()), -60));
                    break;
                case 3:
                    Commons.distanceDayDay=182;
                    Commons.distanceDayWeek=182;
                    Commons.distanceDayMouth=182;
                    date_chose_little = MyUtils.getOldDate(new SimpleDateFormat("yyyy-MM-dd").parse(tx_end.getText().toString()), -182);
                    tx_start.setText(MyUtils.getOldDate(new SimpleDateFormat("yyyy-MM-dd").parse(tx_end.getText().toString()), -182));
                    break;
                case 4:

                    Commons.distanceDayDay=365;
                    Commons.distanceDayWeek=365;
                    Commons.distanceDayMouth=365;
                    date_chose_little = MyUtils.getOldDate(new SimpleDateFormat("yyyy-MM-dd").parse(tx_end.getText().toString()), -365);
                    tx_start.setText(MyUtils.getOldDate(new SimpleDateFormat("yyyy-MM-dd").parse(tx_end.getText().toString()), -365));
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

        info_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String language = "";

                Locale savedLanguageType = MultiLanguageUtil.getInstance().getLanguageLocale();
                if (savedLanguageType == Locale.ENGLISH) {
                    language = "en";
                } else if (savedLanguageType == Locale.SIMPLIFIED_CHINESE) {
                    language = "zh_CN";
                }else if (savedLanguageType == Locale.CHINESE) {
                    language = "zh_CN";
                }else
                    language=savedLanguageType.getLanguage();
                if("zh".equals(language)){
                    language="zh_CN";
                }
                mPresenter.getIntroInfo(type,language);
            }
        });


        findViewById(R.id.all_shuju_zhanghu_num_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                    DatePicker picker = new DatePicker(ZhanghuNumActivity.this);
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
                           // getEndTime();
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
                date_chose_little = tx_start.getText().toString();
                date_chose_big = tx_end.getText().toString();

                twinklingRefreshLayout.startRefresh();
                endAnimOut();
            }
        });
        findViewById(R.id.all_shuju_zhanghu_pop_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endAnimOut();
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
                if(choseType.equals("5min")||choseType.equals("hour")){
                    ToastUtils.show(getString(R.string.nodata));
                }else
                     starAnimEnter();
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
                    date_chose_little = MyUtils.getOldDate(dateToday,-Commons.distanceDayDay);
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
                    date_chose_little = MyUtils.getOldDate(dateToday,-Commons.distanceDayWeek);
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
                    date_chose_little = MyUtils.getOldDate(dateToday,-Commons.distanceDayMouth);

                    twinklingRefreshLayout.startRefresh();
                }
            }
        });
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

    private void setUI() {

        switch (clickType) {
            case 0:
                tx_minute.setBackgroundResource(R.drawable.btn_gray_white);
                tx_mouth.setBackgroundResource(R.drawable.btn_gray_white);
                tx_week.setBackgroundResource(R.drawable.btn_gray_white_top_bttom);
                tx_hour.setBackgroundResource(R.drawable.btn_gray_white_top_bttom);
                tx_day.setBackgroundResource(R.drawable.btn_gray_white);
                tx_minute.setTextColor(Color.parseColor("#c7c7c7"));
                tx_hour.setTextColor(Color.parseColor("#c7c7c7"));
                tx_day.setTextColor(Color.parseColor("#4a4a4a"));
                tx_week.setTextColor(Color.parseColor("#c7c7c7"));
                tx_mouth.setTextColor(Color.parseColor("#c7c7c7"));

                tx_day.setEnabled(true);
                tx_hour.setEnabled(false);
                tx_minute.setEnabled(false);
                tx_week.setEnabled(false);
                tx_mouth.setEnabled(false);
                break;
            case 1:
                tx_minute.setBackgroundResource(R.drawable.btn_gray_white_top_bttom);
                tx_mouth.setBackgroundResource(R.drawable.btn_gray_white_top_bttom);
                tx_week.setBackgroundResource(R.drawable.btn_gray_white_top_bttom);
                tx_hour.setBackgroundResource(R.drawable.btn_gray_white_top_bttom);
                tx_day.setBackgroundResource(R.drawable.btn_gray_white);
                tx_minute.setTextColor(Color.parseColor("#c7c7c7"));
                tx_hour.setTextColor(Color.parseColor("#c7c7c7"));
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
                tx_week.setBackgroundResource(R.drawable.btn_gray_white_top_bttom);
                tx_hour.setBackgroundResource(R.drawable.btn_gray_white_top_bttom);
                tx_day.setBackgroundResource(R.drawable.btn_gray_white_top_bttom);
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
                tx_minute.setBackgroundResource(R.drawable.btn_gray_white_top_bttom);
                tx_mouth.setBackgroundResource(R.drawable.btn_gray_white_top_bttom);
                tx_week.setBackgroundResource(R.drawable.btn_gray_white_top_bttom);
                tx_hour.setBackgroundResource(R.drawable.btn_gray_white_top_bttom);
                tx_day.setBackgroundResource(R.drawable.btn_gray_white);
                tx_minute.setTextColor(Color.parseColor("#4a4a4a"));
                tx_hour.setTextColor(Color.parseColor("#4a4a4a"));
                tx_day.setTextColor(Color.parseColor("#4a4a4a"));
                tx_week.setTextColor(Color.parseColor("#4a4a4a"));
                tx_mouth.setTextColor(Color.parseColor("#4a4a4a"));
//                if (!choseType.equals("week") && !choseType.equals("day") && !choseType.equals("month")) {
//                    choseType = "day";
//                }
                if(type.equals(Commons.price)){
                    tx_day.setEnabled(true);
                    tx_hour.setEnabled(false);
                    tx_minute.setEnabled(false);
                    tx_week.setEnabled(false);
                    tx_mouth.setEnabled(false);
                }else{
                    tx_day.setEnabled(true);
                    tx_hour.setEnabled(true);
                    tx_minute.setEnabled(true);
                    tx_week.setEnabled(true);
                    tx_mouth.setEnabled(true);
                }

                break;
        }
        switch (choseType) {
            case "5min":
                tx_minute.setBackgroundResource(R.drawable.btn_appcolor);
                tx_minute.setTextColor(Color.parseColor("#ffffff"));
                break;
            case "hour":
                tx_hour.setBackgroundResource(R.drawable.btn_appcolor);
                tx_hour.setTextColor(Color.parseColor("#ffffff"));
                break;
            case "day":
                tx_day.setBackgroundResource(R.drawable.btn_appcolor);
                tx_day.setTextColor(Color.parseColor("#ffffff"));
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

    List<String> xValue = new ArrayList<>();


    //每个点的坐标,自己随便搞点（x,y）坐标就可以了
    ArrayList<Entry> pointValues = new ArrayList<>();

    private Map<String,List<Entry>> point_list = new HashMap<>();

    //设置数据
    private void setmLineChartData(List<ValueBean> list) {

        xValue.clear();

        pointValues.clear();

        ArrayList<Entry> pointValues2 = new ArrayList<>();
        
        float frist_Yvalue = 0;

        boolean setMin=false;

        for (int i = 0; i < list.size(); i++) {
            float y=0;
            if(!TextUtils.isEmpty(list.get(i).getValue())&&!"null".equals(list.get(i).getValue())){
                if(MyUtils.isNumeric(list.get(i).getValue())){
                    if(type.equals(Commons.price)||type.equals(Commons.marketValue)||type.equals(Commons.tradeValue)||type.equals(Commons.avgTrdValue)){
                        y = MyUtils.getUnit()?Float.parseFloat(list.get(i).getValue()):Float.parseFloat(list.get(i).getValue())*Commons.rate;
                    }else
                        y = Float.parseFloat(list.get(i).getValue());
                }

                if(Float.parseFloat(list.get(i).getValue())<=0){
                    setMin=true;
                }

                    if(i==0){
                        frist_Yvalue = y;
                    }
                    xValue.add(list.get(i).getTime());
                    pointValues.add(new Entry((float) i, y));


            }


           
           // String x = list.get(i).getTime();

            if(Commons.bigTradeCountDis.equals(type)){
                float y2=0;
                if(!TextUtils.isEmpty(list.get(i).getValue1())&&!"null".equals(list.get(i).getValue1())){
                     y2 = Float.parseFloat(list.get(i).getValue1());
                    pointValues2.add(new Entry((float) i, y2));
                }

            }
        }
        List<ILineDataSet> dataSets = new ArrayList<>();
        //点构成的某条线
        LineDataSet lineDataSet = new LineDataSet(pointValues, Legendtitle[0]);
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet.setColor(getResources().getColor(R.color.F398C0));
        lineDataSet.setLineWidth(0.8f);
        lineDataSet.setFillAlpha(40);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillDrawable(getResources().getDrawable(R.drawable.line_background01));
        lineDataSet.setHighLightColor(getResources().getColor(R.color.F398C0));
        lineDataSet.setDrawValues(false);

        point_list.put(type,pointValues);

        if(Commons.bigTradeCountDis.equals(type)){
            //点构成的某条线
            LineDataSet lineDataSet2 = new LineDataSet(pointValues2, Legendtitle[1]);
            lineDataSet2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            //设置该线的颜色
            lineDataSet2.setColor(getResources().getColor(R.color.piechat5));
            //设置每个点的颜色
//        lineDataSet.setCircleColor(Color.YELLOW);
            //设置该线的宽度
            lineDataSet2.setLineWidth(1f);
            lineDataSet.setHighLightColor(getResources().getColor(R.color.piechat5));

            //设置每个坐标点的圆大小
            //lineDataSet.setCircleRadius(1f);
            //设置是否画圆
            lineDataSet2.setDrawCircles(false);

            lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            // 设置平滑曲线模式
//        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            //设置线一面部分是否填充颜色
            lineDataSet2.setFillAlpha(40);
            lineDataSet2.setDrawFilled(true);
            //设置填充的颜色
            lineDataSet2.setFillDrawable(getResources().getDrawable(R.drawable.line_background05));
            //设置是否显示点的坐标值
            lineDataSet2.setDrawValues(false);
            dataSets.add(lineDataSet2);
        }


        //线的集合（可单条或多条线）
        dataSets.add(lineDataSet);
        //把要画的所有线(线的集合)添加到LineData里
        LineData lineData = new LineData(dataSets);
        //把最终的数据setData
        if(lineChartEntity!=null){

            mLineChart.setData(lineData);

            lineChartEntity.initXValue(xValue);

            lineChartEntity.showRight(false);

            mLineChart.invalidate();

            mLineChart.animateX(1000);

        }else {

            String type_str = "";

            if(type.equals(Commons.price)){

                type_str = Commons.currectLine;

            }else
                type_str = type;

            lineChartEntity = new LineChartEntity(ZhanghuNumActivity.this, mLineChart, lineData, xValue, choseType, type_str);

            lineChartEntity.setLegendEnabled(false);

            lineChartEntity.showRightY(false, type);
        }

        xyMarkerView.setLineData(point_list);
        xyMarkerView.setColormap(color_map);
        xyMarkerView.setChoose_title(type);
        xyMarkerView.setIAxisValueFormatter(xValue);

    }


    protected void getNetData() {

        if(choseType.equals("day")||choseType.equals("week")||choseType.equals("month")){
            mPresenter.getValue(type,id,date_chose_little.substring(0,10),date_chose_big.substring(0,10),choseType);
        }else
            mPresenter.getValue(type,id,date_chose_little,date_chose_big,choseType);



    }



    @Override
    public void onValue(ValueResp valueResp) {

        twinklingRefreshLayout.finishRefreshing();
        if(TextUtils.isEmpty(valueResp.getErrInfo())){

            recyclerView.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.GONE);
            List<ZhangHuNumBean> zhangHuNumBeanList =new ArrayList<>();
            //折线图数据
            setmLineChartData(valueResp.getResult().getData());
            //表格数据
            List<ValueBean> valueBeanArrayList =new ArrayList<>();

            for(int i=0;i<valueResp.getResult().getData().size();i++){

                ValueBean valueBean = new ValueBean();

                valueBean.setTime(valueResp.getResult().getData().get(i).getTime());

                valueBean.setValue(valueResp.getResult().getData().get(i).getValue()+"");

                valueBean.setType(type);

                valueBean.setChooseType(choseType);

                valueBeanArrayList.add(valueBean);
            }

            Collections.reverse(valueBeanArrayList);

            adapter.setNewData(valueBeanArrayList);
        }

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
    public void ontop(Top100Resp resp) {

    }

    @Override
    public void TopTrade(TopFreqAddrResp resp) {

    }

    @Override
    public void TopPlayer(TopFreqAddrResp resp) {

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
            }else if("5min".equals(choseType)){
                info=getString(R.string.nominutedata);
            }else if("hour".equals(choseType)){
                info=getString(R.string.nohourdata);
            }
            ToastUtils.show(info);
        }
    }




    @Override
    public void onntroInfo(IntroInfoResp resp) {

        if(!TextUtils.isEmpty(resp.getResult())){

            new InfoDialog(ZhanghuNumActivity.this,R.style.dialog,resp.getResult()).setTitle().show();

        }

    }

    @Override
    public void onError() {
        twinklingRefreshLayout.finishRefreshing();
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
    private List<MarkerViewBean>  markerViewBeanList = new ArrayList<>();
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        currentTimeMillis =  System.currentTimeMillis();

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
