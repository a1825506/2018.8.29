package com.gikee.app.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.activity.ProjectDetailActivity;
import com.gikee.app.activity.SearchActivity;
import com.gikee.app.adapter.BaseChainTodayAdapter;
import com.gikee.app.adapter.ChooseItemAdapter;
import com.gikee.app.adapter.MarkViewAdapter;
import com.gikee.app.adapter.MyViewPagerAdapter;
import com.gikee.app.adapter.PopAllprojectAdapter;
import com.gikee.app.adapter.ViewPagerAdapter;
import com.gikee.app.beans.BaseChainBean;
import com.gikee.app.beans.ChooseItemBean;
import com.gikee.app.beans.MarkerViewBean;
import com.gikee.app.beans.ValueBean;
import com.gikee.app.beans.ZhanghuPopBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.language.LanguageType;
import com.gikee.app.language.MultiLanguageUtil;
import com.gikee.app.presenter.home.HomePresenter;
import com.gikee.app.presenter.home.HomeView;
import com.gikee.app.resp.DotBean;
import com.gikee.app.resp.LineDataBean;
import com.gikee.app.resp.MarketRateResp;
import com.gikee.app.resp.PowerResp;
import com.gikee.app.resp.SummaryBean;
import com.gikee.app.resp.SummaryResp;
import com.gikee.app.resp.TopFreqAddrResp;
import com.gikee.app.resp.ValueResp;
import com.gikee.app.views.AutoHeightViewPager;
import com.gikee.app.views.BaseFragmentPagerAdapter;
import com.gikee.app.views.GikeeLineChatEntity;
import com.gikee.app.views.LineChartEntity;
import com.gikee.app.views.MainDemoActivity;
import com.gikee.app.views.MyLineChart;


import com.gikee.app.views.MyRefreshHeader;
import com.gikee.app.views.ScrollInterceptScrollView;
import com.gikee.app.views.XYMarkerView;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeView,OnChartValueSelectedListener {


    @Bind(R.id.scroll_view)
    ScrollView scroll_view;
    @Bind(R.id.tablelayout)
    TabLayout tabLayout;
    @Bind(R.id.base_tablelayout)
    TabLayout base_tablelayout;
    @Bind(R.id.viewPager)
    AutoHeightViewPager viewPager;
    @Bind(R.id.linechart_baseline)
    MyLineChart mLineChart;
    @Bind(R.id.recycle_baseLine)
    RecyclerView recycle_baseLine;
    @Bind(R.id.recycle_baseLine_today)
    RecyclerView recycle_baseLine_today;
    @Bind(R.id.text_time)
    TextView text_time;
    @Bind(R.id.layout_choosedate)
    LinearLayout lin_poplayout;
    @Bind(R.id.all_deatil)
    TextView all_deatil;

    @Bind(R.id.recycle_data)
    RecyclerView re_timeinterval;

    @Bind(R.id.search)
    LinearLayout root;
    @Bind(R.id.layout_time)
    LinearLayout layout_time;

    @Bind(R.id.all_layout)
    RelativeLayout all_layout;

    @Bind(R.id.markerview_layout)
    LinearLayout markerview_layout;
    @Bind(R.id.recycle_markerview)
    RecyclerView recycle_markerview;

    @Bind(R.id.fm_all_shuju_refreshLayout)
    TwinklingRefreshLayout twinklingRefreshLayout;
    @Bind(R.id.txt_price)
    TextView txt_price;

    private MyViewPagerAdapter viewPagerAdapter;

    private ChooseItemAdapter chooseItemAdapter;

    private BaseChainTodayAdapter baseChainTodayAdapter;
    private List<ZhanghuPopBean> list_pop = new ArrayList<>();

    private int timeType=0;

    private boolean isshow=false;

    private List<BaseFragment> fragments = new ArrayList<>();
    private String[] titles=new String[4];//{getString(R.string.market_value_trend),getString(R.string.market_share),getString(R.string.hash_rate),getString(R.string.rich_list)};

    private String[] titles_base={"BTC","ETH","EOS","USDT","BCH","LTC"};

    private int[] images={R.mipmap.trend,R.mipmap.proportion,R.mipmap.power,R.mipmap.regal};

    private int[] images_gray={R.mipmap.trend_gray,R.mipmap.proportion_gray,R.mipmap.power_gray,R.mipmap.regal_gray};

    private List<String> choose_title=new ArrayList();

    private  List<DotBean> dot_price = new ArrayList<>();

    private  List<DotBean> dot_activityAccount = new ArrayList<>();

    private  List<DotBean> dot_newAccount = new ArrayList<>();

    private  List<DotBean> dot_tradecount = new ArrayList<>();

    private  List<DotBean> dot_tradevalue = new ArrayList<>();

    private String id="bitcoin";

    private String date="30day";//30day   90day   180day 365day 3year All

    private String symbol="BTC";

    List<SummaryBean> data_all;

    private Date dateToday;

    List <ChooseItemBean> chooseItemBeanList = new ArrayList<>();

    int choosesize=30;

    private String language = "";

    private  LineChartEntity lineChartEntity=null;

    private LineDataSet lineDataSet=null;

    private LineData lineData;

    private MarkViewAdapter markViewAdapter;

    private Map<String,Integer> color_map = new HashMap<>();

    private boolean isChatScroll=false;

    private XYMarkerView xyMarkerView;

    public  int REQUESCODE = 400;

    private String price_title;

    public static HomeFragment getInstance(){

        HomeFragment homeFragment = new HomeFragment();

        return homeFragment;

    }

    @Override
    protected void setupViews(LayoutInflater inflater) {

        setContentView(inflater, R.layout.fragment_home);

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


    }

    @Override
    protected void initView() {

        titles[0]=getString(R.string.market_value_trend);
        titles[1]=getString(R.string.market_share);
        titles[2]=getString(R.string.hash_rate);
        titles[3]=getString(R.string.rich_list);

        mLineChart.setNoDataText(getString(R.string.getdata));

        mLineChart.setOnChartValueSelectedListener(this);


        xyMarkerView = new XYMarkerView(getContext());

        xyMarkerView.setChartView(mLineChart);

        mLineChart.setMarker(xyMarkerView);


        MyRefreshHeader headerView = new MyRefreshHeader(getContext());
        twinklingRefreshLayout.setEnableLoadmore(false);
        twinklingRefreshLayout.setEnableOverScroll(false);
        twinklingRefreshLayout.setHeaderView(headerView);


        dateToday = new Date();

        choose_title.add(Commons.price);

        choose_title.add(Commons.newAccount);

        mPresenter = new HomePresenter(this);


        fragments.add(new MarketTrendFragment());

        fragments.add(new MarketRateFragment());

        fragments.add(new PowerFragment());

        fragments.add(new RichListFragment());

        BaseFragmentPagerAdapter baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            protected Fragment getItemFragment(int position) {
                return fragments.get(position);
            }

            @Override
            protected String[] getMTitles() {
                return  titles;
            }

            @Override
            protected List<String> getAutoMTitles() {
                return null;
            }
        };

        viewPager.setScanScroll(false);

        viewPager.setAdapter(baseFragmentPagerAdapter);

        viewPager.setOffscreenPageLimit(4);

        viewPager.setCurrentItem(0);

        viewPagerAdapter = new MyViewPagerAdapter(getFragmentManager(),titles,fragments);

        //将tablelayout与viewpager一起绑定
        tabLayout.setupWithViewPager(viewPager);




        for(int i=0;i<titles.length;i++){
//
//            Drawable drawable = getResources().getDrawable(images[i]);
//            drawable.setBounds(1, 1, drawable.getMinimumWidth(), drawable.getMinimumHeight());


            TabLayout.Tab tab = tabLayout.getTabAt(i);//获得每一个tab
            View view = LayoutInflater.from(getContext()).inflate(R.layout.tab_item, null);
            {
                TextView textView = view.findViewById(R.id.tab_tx);

                ImageView imageView = view.findViewById(R.id.tab_img);

                 imageView.setVisibility(View.VISIBLE);

                 textView.setText(titles[i]);

                 if(i==0){
                     imageView.setBackground(getContext().getResources().getDrawable(images[i]));
                     textView.setTextColor(getContext().getResources().getColor(R.color.title_color));
                 }else{
                     imageView.setBackground(getContext().getResources().getDrawable(images_gray[i]));
                     textView.setTextColor(getContext().getResources().getColor(R.color.gray_33));
                 }



            }

            tab.setCustomView(view);//给每一个tab设置view
            //tabLayout.addTab(tab);

            // tabLayout.addTab(tabLayout.newTab().setIcon(images[i]).setText(titles[i]),false);

        }




        chooseItemAdapter = new ChooseItemAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recycle_baseLine.setLayoutManager(linearLayoutManager);

        recycle_baseLine.setAdapter(chooseItemAdapter);

        for(String tab:titles_base){
            base_tablelayout.addTab(base_tablelayout.newTab().setText(tab));
        }


        baseChainTodayAdapter = new BaseChainTodayAdapter();

        GridLayoutManager  gridLayoutManager = new GridLayoutManager(getContext(), 3);

        recycle_baseLine_today.setLayoutManager(gridLayoutManager);

        recycle_baseLine_today.setAdapter(baseChainTodayAdapter);

        initPop();

        onClick();


        price_title = MyUtils.getUnit()?getString(R.string.price_us):getString(R.string.price_cny);

        txt_price.setText(price_title);



        new Thread(new MyThread()).start();
    }

    private void getNetData() {

        mPresenter.getchain(id,date);
    }




    private void onClick() {


        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivityForResult(intent, REQUESCODE);

            }
        });

        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {

                getNetData();
            }

        });




        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                View view =  tab.getCustomView();

               // View view = LayoutInflater.from(getContext()).inflate(R.layout.tab_item, null);
                {

                    ImageView imageView = view.findViewById(R.id.tab_img);

                    TextView textView = view.findViewById(R.id.tab_tx);

                    textView.setTextColor(getContext().getResources().getColor(R.color.title_color));

                    imageView.setVisibility(View.VISIBLE);

                    imageView.setBackground(getContext().getResources().getDrawable(images[tab.getPosition()]));

                }

                tab.setCustomView(view);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                View view = tab.getCustomView();

                ImageView imageView = view.findViewById(R.id.tab_img);

                TextView textView = view.findViewById(R.id.tab_tx);

                textView.setTextColor(getContext().getResources().getColor(R.color.gray_33));

                imageView.setVisibility(View.VISIBLE);

                imageView.setBackground(getContext().getResources().getDrawable(images_gray[tab.getPosition()]));

                tab.setCustomView(view);


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                // 切换到当前页面，重置高度
                viewPager.requestLayout();


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        all_deatil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), ProjectDetailActivity.class)
                        .putExtra("logo","")
                        .putExtra("symbol", symbol)
                        .putExtra("id", id));


            }
        });

        base_tablelayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

               if(tab.getPosition()==0){

                   id ="bitcoin";
                   symbol = "BTC";
               }else if(tab.getPosition()==1){

                   id ="ethereum";
                   symbol = "ETH";
               }else if(tab.getPosition()==2){

                   id ="eos";
                   symbol = "EOS";
               }else if(tab.getPosition()==3){

                   id ="tether";
                   symbol = "USDT";
               }else if(tab.getPosition()==4){

                   id ="bitcoin-cash";
                   symbol = "BCH";
               }else if(tab.getPosition()==5){

                   id ="litecoin";
                   symbol = "LTC";
               }

                twinklingRefreshLayout.startRefresh();



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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



                initChatData();


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

//                initChatData();

                twinklingRefreshLayout.startRefresh();

                endAnimOut();

            }
        });
    }

    private void initChatData() {

        dot_price.clear();
        dot_newAccount.clear();
        dot_activityAccount.clear();
        dot_tradecount.clear();
        dot_tradevalue.clear();

        //改变数据源重新绘制折线图
        for (int i = 0; i < data_all.size(); i++) {

            for(int j=0;j<choose_title.size();j++){

                if(data_all.get(i).getItemName().equals(choose_title.get(j))){
                    //需要对比的指标,绘制折线
                      List<DotBean> dot =data_all.get(i).getLineData().get(0).getDot();
                      initLineData(dot,data_all.get(i).getItemName());

                }

            }

        }

        setmLineChartData(dot_price,dot_newAccount,dot_activityAccount,dot_tradecount,dot_tradevalue);

    }

    private void initLineData( List<DotBean> dot,String type) {



        if(choosesize!=-1){
            if(dot.size()>choosesize){
                initItemData(dot,type);

            }else{
                initAllData(dot,type);

            }
        }else{
            initAllData(dot,type);

        }
    }

    private void initItemData(List<DotBean> dot, String type) {

        DotBean dotBean=null;

        for(int n=dot.size()-choosesize;n<dot.size();n++){
            dotBean = new DotBean();
            dotBean.setTime(dot.get(n).getTime());
            dotBean.setValue(dot.get(n).getValue());
            if(type.equals(Commons.price)){
                dot_price.add(dotBean);
            }else if(type.equals(Commons.newAccount)){
                dot_newAccount.add(dotBean);
            }else if(type.equals(Commons.activeDis)){
                dot_activityAccount.add(dotBean);
            }else if(type.equals(Commons.tradeCount)){
                dot_tradecount.add(dotBean);
            }else if(type.equals(Commons.tradeValue)){
                dot_tradevalue.add(dotBean);
            }

        }
    }

    private void initAllData(List<DotBean> dot, String type) {

        DotBean dotBean=null;

        for(int n=0;n<dot.size();n++){
            dotBean = new DotBean();
            dotBean.setTime(dot.get(n).getTime());
            dotBean.setValue(dot.get(n).getValue());
            if(type.equals(Commons.price)){
                dot_price.add(dotBean);
            }else if(type.equals(Commons.newAccount)){
                dot_newAccount.add(dotBean);
            }else if(type.equals(Commons.activeDis)){
                dot_activityAccount.add(dotBean);
            }else if(type.equals(Commons.tradeCount)){
                dot_tradecount.add(dotBean);
            }else if(type.equals(Commons.tradeValue)){
                dot_tradevalue.add(dotBean);
            }
        }
    }

    private void starAnimEnter() {

        lin_poplayout.setVisibility(View.VISIBLE);
        for (int i = 0; i < lin_poplayout.getChildCount(); i++) {
            lin_poplayout.getChildAt(i).setVisibility(View.VISIBLE);
        }
        //view_cancle.setVisibility(View.VISIBLE);
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0);
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
       // view_cancle.setVisibility(View.GONE);
    }


    @Override
    public void onChain(SummaryResp summaryResp) {

        twinklingRefreshLayout.finishRefreshing();

        if(TextUtils.isEmpty(summaryResp.getErrInfo())){

            if(summaryResp.getResult()!=null){

                data_all = summaryResp.getResult().getData();
                List<BaseChainBean> baseChainBeanList= new ArrayList<>();
                DotBean dotBean;
                chooseItemBeanList.clear();
                dot_activityAccount.clear();
                dot_tradevalue.clear();
                dot_tradecount.clear();
                dot_price.clear();
                dot_newAccount.clear();

                choose_title.clear();

                choose_title.add(Commons.price);

                choose_title.add(Commons.newAccount);

                if(data_all.size()!=0){

                    for(SummaryBean summaryBean:data_all){

                        if(summaryBean.getItemName().equals(Commons.price)){
                            List<DotBean> dot =summaryBean.getLineData().get(0).getDot();
                            initAllData(dot,summaryBean.getItemName());
//                            if(choosesize!=-1){
//                                if(dot.size()>choosesize){
//                                    //首次展示近30天的数据
//                                    initItemData(dot,summaryBean.getItemName());
//                                }else{
//                                    initAllData(dot,summaryBean.getItemName());
//                                }
//                            }else{
//                                initAllData(dot,summaryBean.getItemName());
//
//                            }

                        }


                        if(summaryBean.getItemName().equals(Commons.newAccount)){
                            List<DotBean> dot =summaryBean.getLineData().get(0).getDot();
                            initAllData(dot,summaryBean.getItemName());
//                            if(choosesize!=-1){
//                                if(dot.size()>choosesize){
//                                    //首次展示近30天的数据
//                                    initItemData(dot,summaryBean.getItemName());
//                                }else{
//                                    initAllData(dot,summaryBean.getItemName());
//
//                                }
//                            }else{
//                                initAllData(dot,summaryBean.getItemName());
//
//                            }
                        }

                        if(!summaryBean.getItemName().equals("price")){
                            ChooseItemBean chooseItemBean = new ChooseItemBean();
                            chooseItemBean.setTitle(summaryBean.getTitile());
                            chooseItemBean.setId(summaryBean.getItemName());
                            if(summaryBean.getItemName().equals(Commons.newAccount)){
                                chooseItemBean.setChoose(true);
                            }else
                                chooseItemBean.setChoose(false);

                            chooseItemBeanList.add(chooseItemBean);
                        }
                        BaseChainBean  baseChainBean = new BaseChainBean();

                        baseChainBean.setTitle(summaryBean.getTitile());

                        baseChainBean.setItemname(summaryBean.getItemName());

                        baseChainBean.setQuote(summaryBean.getLineData().get(0).getQuoteChange());

                        baseChainBean.setValue(summaryBean.getLineData().get(0).getCurrentValue().get(0));

                        baseChainBeanList.add(baseChainBean);



                    }
                    //折线图数据
                    setmLineChartData(dot_price,dot_newAccount,dot_activityAccount,dot_tradevalue,dot_tradecount);
                }

                chooseItemAdapter.setNewData(chooseItemBeanList);

                baseChainTodayAdapter.setNewData(baseChainBeanList);


                color_map.put(Commons.price,R.color.F398C0);
                color_map.put(Commons.newAccount,R.color.piechat1);
                color_map.put(Commons.activeDis,R.color.piechat4);
                color_map.put(Commons.tradeCount,R.color.A1DF79);
                color_map.put(Commons.tradeValue,R.color.piechat6);

                markViewAdapter = new MarkViewAdapter(color_map);

                GridLayoutManager  gridLayoutManager1 = new GridLayoutManager(getContext(), 2);

                recycle_markerview.setLayoutManager(gridLayoutManager1);

                recycle_markerview.setAdapter(markViewAdapter);

            }


        }

    }

    @Override
    public void onTopPlayer(TopFreqAddrResp topFreqAddrResp) {

    }


    List<String> xValue = new ArrayList<>();

    ArrayList<Entry> pointValues = new ArrayList<>();//价格
    ArrayList<Entry> pointValues1 = new ArrayList<>();//新增账户
    ArrayList<Entry> pointValues2 = new ArrayList<>();//活跃账户
    ArrayList<Entry> pointValues3 = new ArrayList<>();//交易笔数
    ArrayList<Entry> pointValues4 = new ArrayList<>();//交易金额

    Map<String,List<Entry>> point_list = new HashMap<>();

    List<ILineDataSet> dataSets = new ArrayList<>();

    private void setmLineChartData(List<DotBean> list,List<DotBean> list1,List<DotBean> list2,List<DotBean> list3,List<DotBean> list4) {


        float y = 0, y1 = 0, y2 = 0, y3 = 0, y4 = 0;

        xValue.clear();
        pointValues.clear();
        pointValues1.clear();
        pointValues2.clear();
        pointValues3.clear();
        pointValues4.clear();
        dataSets.clear();
        if(symbol=="EOS"){
            for (int i = 0; i < list.size(); i++) {


                if (list.get(i) != null && list.get(i).getTime() != null) {
                    xValue.add(list.get(i).getTime());

                }



                if (list.get(i) != null && !TextUtils.isEmpty(list.get(i).getValue()) && !"null".equals(list.get(i).getValue())) {
                    if (MyUtils.isNumeric(list.get(i).getValue())) {
                        y = Float.parseFloat(list.get(i).getValue());
                        pointValues.add(new Entry((float) i, y));
                    }
                }

            }

            int count=0;
            for (int i = list.size()-list1.size(); i <list.size(); i++) {

                if (list1.get(count) != null && !TextUtils.isEmpty(list1.get(count).getValue()) && !"null".equals(list1.get(count).getValue())) {
                    if (MyUtils.isNumeric(list1.get(count).getValue())) {
                        y = Float.parseFloat(list1.get(count).getValue());
                        pointValues1.add(new Entry((float) i, y));
                    }
                }
                count++;
            }

//            for (int i = 0; i < list1.size(); i++) {
//
//                if (list1.get(i) != null && !TextUtils.isEmpty(list1.get(i).getValue()) && !"null".equals(list1.get(i).getValue())) {
//                    if (MyUtils.isNumeric(list1.get(i).getValue())) {
//                        y1 = Float.parseFloat(list1.get(i).getValue());
//                        pointValues1.add(new Entry((float) i, y1));
//                    }
//                }
//            }


            int count1=0;
            for (int i = list.size()-list3.size(); i <list.size(); i++) {

                if (list3.get(count1) != null && !TextUtils.isEmpty(list3.get(count1).getValue()) && !"null".equals(list3.get(count1).getValue())) {
                    if (MyUtils.isNumeric(list3.get(count1).getValue())) {
                        y3 = Float.parseFloat(list3.get(count1).getValue());
                        pointValues3.add(new Entry((float) i, y3));
                    }
                }
                count1++;
            }


//            for (int i = 0; i < list3.size(); i++) {
//
//                if (list3.get(i) != null && !TextUtils.isEmpty(list3.get(i).getValue()) && !"null".equals(list3.get(i).getValue())) {
//                    if (MyUtils.isNumeric(list3.get(i).getValue())) {
//                        y3 = Float.parseFloat(list3.get(i).getValue());
//                        pointValues3.add(new Entry((float) i, y3));
//                    }
//                }
//            }
        }else{
            int count=0;
            for (int i = list1.size()-list.size(); i < list1.size(); i++) {

                if (list.get(count) != null && !TextUtils.isEmpty(list.get(count).getValue()) && !"null".equals(list.get(count).getValue())) {
                    if (MyUtils.isNumeric(list.get(count).getValue())) {
                        y = Float.parseFloat(list.get(count).getValue());
                        pointValues.add(new Entry((float) i, y));
                    }
                }
                count++;
            }



            for (int i = 0; i < list1.size(); i++) {

                    if (list1.get(i) != null && list1.get(i).getTime() != null) {
                        xValue.add(list1.get(i).getTime());
                    }


                if (list1.get(i) != null && !TextUtils.isEmpty(list1.get(i).getValue()) && !"null".equals(list1.get(i).getValue())) {
                    if (MyUtils.isNumeric(list1.get(i).getValue())) {
                        y1 = Float.parseFloat(list1.get(i).getValue());
                        pointValues1.add(new Entry((float) i, y1));
                    }
                }

            }

            for (int i = 0; i < list2.size(); i++) {

                if (list2.get(i) != null && !TextUtils.isEmpty(list2.get(i).getValue()) && !"null".equals(list2.get(i).getValue())) {
                    if (MyUtils.isNumeric(list2.get(i).getValue())) {
                        y2 = Float.parseFloat(list2.get(i).getValue());
                        pointValues2.add(new Entry((float) i, y2));
                    }
                }
            }

            for (int i = 0; i < list3.size(); i++) {



                if (list3.get(i) != null && !TextUtils.isEmpty(list3.get(i).getValue()) && !"null".equals(list3.get(i).getValue())) {
                    if (MyUtils.isNumeric(list3.get(i).getValue())) {
                        y3 = Float.parseFloat(list3.get(i).getValue());
                        pointValues3.add(new Entry((float) i, y3));
                    }
                }
            }


            int count4=0;
           // for (int i = 0; i < list4.size(); i++) {
            for (int i = list1.size()-list4.size(); i < list1.size(); i++) {


                if (list4.get(count4) != null && !TextUtils.isEmpty(list4.get(count4).getValue()) && !"null".equals(list4.get(count4).getValue())) {
                    if (MyUtils.isNumeric(list4.get(count4).getValue())) {
                        y4 = Float.parseFloat(list4.get(count4).getValue());
                        pointValues4.add(new Entry((float) i, y4));
                    }
                }
                count4++;
            }

        }



        if (pointValues.size() != 0) {
            //点构成的某条线
            lineDataSet = new LineDataSet(pointValues, "");
            lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            lineDataSet.setColor(getResources().getColor(R.color.F398C0));
            lineDataSet.setLineWidth(0.8f);
            lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            lineDataSet.setFillAlpha(40);
            lineDataSet.setDrawCircles(false);
            lineDataSet.setDrawFilled(false);
            lineDataSet.enableDashedHighlightLine(5f, 5f, 0);
            lineDataSet.setHighLightColor(getResources().getColor(R.color.F398C0));
            lineDataSet.setDrawValues(false);
             dataSets.add(lineDataSet);
            point_list.put(Commons.price,pointValues);
            xyMarkerView.setChoose_title(Commons.price);
//            lineData.addDataSet(lineDataSet);
//            totalCount++;
        }


        if (pointValues1.size() != 0) {
            lineDataSet = new LineDataSet(pointValues1, "");
            lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            lineDataSet.setColor(getResources().getColor(R.color.piechat1));
            lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
            lineDataSet.setLineWidth(0.8f);
            lineDataSet.setFillAlpha(40);
            lineDataSet.setDrawCircles(false);
            lineDataSet.setDrawFilled(false);
            lineDataSet.enableDashedHighlightLine(5f, 5f, 0);
//            lineDataSet.setHighLightColor(getResources().getColor(R.color.piechat1));
            lineDataSet.setDrawValues(false);
            dataSets.add(lineDataSet);
            point_list.put(Commons.newAccount,pointValues1);
            xyMarkerView.setChoose_title(Commons.newAccount);
//            lineData.addDataSet(lineDataSet1);
//            totalCount++;
        }

        if (pointValues2.size() != 0) {
            lineDataSet = new LineDataSet(pointValues2, "");
            lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            lineDataSet.setColor(getResources().getColor(R.color.piechat4));
            lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
            lineDataSet.setLineWidth(0.8f);
            lineDataSet.setFillAlpha(40);
            lineDataSet.setDrawCircles(false);
            lineDataSet.setDrawFilled(false);
            lineDataSet.enableDashedHighlightLine(5f, 5f, 0);
//            lineDataSet.setHighLightColor(getResources().getColor(R.color.piechat4));
            lineDataSet.setDrawValues(false);
            dataSets.add(lineDataSet);
            point_list.put(Commons.activeDis,pointValues2);
            xyMarkerView.setChoose_title(Commons.activeDis);
        }

        if (pointValues3.size() != 0) {
            lineDataSet = new LineDataSet(pointValues3, "");
            lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            lineDataSet.setColor(getResources().getColor(R.color.A1DF79));
            lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
            lineDataSet.setLineWidth(0.8f);
            lineDataSet.setFillAlpha(40);
            lineDataSet.setDrawCircles(false);
            lineDataSet.setDrawFilled(false);
            lineDataSet.enableDashedHighlightLine(5f, 5f, 0);
//            lineDataSet.setHighLightColor(getResources().getColor(R.color.A1DF79));
            lineDataSet.setDrawValues(false);
            dataSets.add(lineDataSet);
            point_list.put(Commons.tradeCount,pointValues3);
            xyMarkerView.setChoose_title(Commons.tradeCount);
//            lineData.addDataSet(lineDataSet3);
//            totalCount++;
        }

        if (pointValues4.size() != 0) {
            lineDataSet = new LineDataSet(pointValues4, "");
            lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            lineDataSet.setColor(getResources().getColor(R.color.piechat6));
            lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
            lineDataSet.setLineWidth(0.8f);
            lineDataSet.setFillAlpha(40);
            lineDataSet.setDrawCircles(false);
            lineDataSet.setDrawFilled(false);
            lineDataSet.enableDashedHighlightLine(5f, 5f, 0);
            // lineDataSet3.setFillDrawable(getResources().getDrawable(R.drawable.line_background04));
//            lineDataSet.setHighLightColor(getResources().getColor(R.color.piechat6));
            lineDataSet.setDrawValues(false);
           // lineData.addDataSet(lineDataSet4);
            dataSets.add(lineDataSet);
            point_list.put(Commons.tradeValue,pointValues4);
//            xyMarkerView.setChoose_title(Commons.tradeValue);
//            totalCount++;
        }



        lineData = new LineData(dataSets);

        if(lineChartEntity!=null){

            mLineChart.setData(lineData);

            lineChartEntity.initXValue(xValue);

            mLineChart.invalidate();

            mLineChart.animateX(1000);

        }else{
            lineChartEntity = new LineChartEntity(getContext(), mLineChart, lineData, xValue, "day", Commons.price);

            lineChartEntity.setLegendEnabled(false);

            lineChartEntity.showRightY(false, "");

        }

        xyMarkerView.setLineData(point_list);
        xyMarkerView.setColormap(color_map);

        xyMarkerView.setIAxisValueFormatter(xValue);



    }


    @Override
    protected void lazyLoad() {

        if (isViewCreated && isUIVisible) {

            twinklingRefreshLayout.startRefresh();

            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;

        }

    }

    @Override
    protected void onChangeEvent(int type) {

        if(type== LanguageType.UNIT_CNY||type== LanguageType.UNIT_USD){
            price_title = MyUtils.getUnit()?getString(R.string.price_us):getString(R.string.price_cny);

            txt_price.setText(price_title);

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
    public void onPower(PowerResp powerResp) {

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


        isChatScroll=true;

        currentTimeMillis =  System.currentTimeMillis();

    }

    @Override
    public void onNothingSelected() {

    }






}
