package com.gikee.app.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.R;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.adapter.LeaderTitleAdapter;
import com.gikee.app.adapter.LeaderboardAdapter;
import com.gikee.app.base.BaseActivity;

import com.gikee.app.language.MultiLanguageUtil;
import com.gikee.app.presenter.search.RankingLabelPresenter;
import com.gikee.app.presenter.search.RankingLabelView;
import com.gikee.app.resp.RankingDetailResp;
import com.gikee.app.resp.RankingLabelBean;
import com.gikee.app.resp.RankingLabelResp;
import com.gikee.app.views.MyRefreshBottom;
import com.gikee.app.views.MyRefreshHeader;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;

public class  LeaderboardActivity extends BaseActivity<RankingLabelPresenter> implements RankingLabelView{

    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
//    @Bind(R.id.tablayout)
//    RecyclerView tabLayout;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @Bind(R.id.viewstub)
    LinearLayout nocontext;
    @Bind(R.id.today_add_title1)
    TextView title1;
    @Bind(R.id.today_add_title2)
    TextView title2;
    @Bind(R.id.today_add_title3)
    TextView title3;
    @Bind(R.id.tabslayout_top)
    TabLayout tabslayout_top;





    private RankingLabelPresenter mPresenter;

    private LeaderboardAdapter leaderboardAdapter;

//    private LeaderTitleAdapter leaderTitleAdapter;

    private String lableId="1";

    private int start=1;

    private int limit=15;

    private String choseType="day";

    private List<RankingLabelBean> rankingLabelBeanList;

    private  View footerView;

    private TextView foottext;

    private  int currect_positio;

    private String language = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
    }

    @Override
    protected void onChangeEvent(int type) {

    }


    @Override
    protected void setUpView() {
        mPresenter = new RankingLabelPresenter(this);
       // showTitleBar();
        //showRightCollect();
       // setTitleColor(R.color.black);
        //setTitle(getString(R.string.leaderboard));

        MyRefreshHeader headerView = new MyRefreshHeader(LeaderboardActivity.this);
        MyRefreshBottom bottomView = new MyRefreshBottom(LeaderboardActivity.this);

        refreshLayout.setAutoLoadMore(false);
        refreshLayout.setHeaderView(headerView);
        refreshLayout.setEnableLoadmore(true);
        refreshLayout.setBottomView(bottomView);

        leaderboardAdapter = new LeaderboardAdapter();

//        leaderTitleAdapter = new LeaderTitleAdapter();

        footerView = LayoutInflater.from(LeaderboardActivity.this).inflate(R.layout.view_myproject_footer, null);

        foottext=footerView.findViewById(R.id.myproject_footer_add);

        foottext.setText(R.string.loadmore);

        leaderboardAdapter.addFooterView(footerView);

        recyclerview.setLayoutManager(new LinearLayoutManager(LeaderboardActivity.this));

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LeaderboardActivity.this);
//
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//
//        tabLayout.setLayoutManager(linearLayoutManager);
//
//        tabLayout.setAdapter(leaderTitleAdapter);


        recyclerview.setAdapter(leaderboardAdapter);

        getData();





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

    private void getData() {

        mPresenter.getRanklabel();
    }

    private void getLeadData() {

//        mPresenter.getRankDetail(lableId,start,limit,choseType);
    }


    @Override
    protected void initOnclick() {



        tabslayout_top.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                currect_positio = tab.getPosition();

//                TextView textView = tab.;
//
//                textView.setTextColor(getResources().getColor(R.color.title_color));

                    lableId= rankingLabelBeanList.get(currect_positio).getEnLabel();

             if("zh_CN".equals(language)){
                 title3.setText(rankingLabelBeanList.get(currect_positio).getCnLabel());
             }else
                 title3.setText(rankingLabelBeanList.get(currect_positio).getEnLabel());


                View view =  tab.getCustomView();

                // View view = LayoutInflater.from(getContext()).inflate(R.layout.tab_item, null);
                {

                    TextView textView = view.findViewById(R.id.tab_tx);

                    textView.setTextColor(getResources().getColor(R.color.title_color));

                }

                tab.setCustomView(view);


//                    if(currect_positio==1){
//                        lastweek_btn.setEnabled(false);
//                        lastmonth_btn.setEnabled(false);
//                        lastweek_btn.setTextColor(getResources().getColor(R.color.mineproject));
//                        lastmonth_btn.setTextColor(getResources().getColor(R.color.mineproject));
//                        yestoday_btn.setBackgroundResource(R.drawable.sharp_btn_project);
//                        yestoday_btn.setTextColor(getResources().getColor(R.color.white));
//                        lastweek_btn.setBackgroundResource(R.drawable.shape_btn_nom);
//                        lastmonth_btn.setBackgroundResource(R.drawable.sharp_btn_addressnormal);
//                        choseType = "day";
//                    }else{
//                        lastweek_btn.setEnabled(true);
//                        lastmonth_btn.setEnabled(true);
//                        lastmonth_btn.setChecked(false);
//                        yestoday_btn.setBackgroundResource(R.drawable.sharp_btn_project);
//                        yestoday_btn.setTextColor(getResources().getColor(R.color.white));
//                        lastweek_btn.setBackgroundResource(R.drawable.shape_btn_nom);
//                        lastweek_btn.setTextColor(getResources().getColor(R.color.gray_33));
//                        lastmonth_btn.setBackgroundResource(R.drawable.sharp_btn_addressnormal);
//                        lastmonth_btn.setTextColor(getResources().getColor(R.color.gray_33));
//                        choseType = "day";
//                    }
                    //setTitleItem(rankingLabelBeanList.get(currect_positio).getHeadlable());
                    refreshLayout.startRefresh();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

//                TextView textView = (TextView)tab.getCustomView();
//
//                textView.setTextColor(getResources().getColor(R.color.gray_33));

                View view =  tab.getCustomView();

                // View view = LayoutInflater.from(getContext()).inflate(R.layout.tab_item, null);
                {

                    TextView textView = view.findViewById(R.id.tab_tx);

                    textView.setTextColor(getResources().getColor(R.color.title_color));

                }

                tab.setCustomView(view);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });






        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                start=1;

                footerView.setVisibility(View.GONE);

                leaderboardAdapter.getData().clear();

                leaderboardAdapter.notifyDataSetChanged();

                getLeadData();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                footerView.setVisibility(View.GONE);
                start=start+limit;
                getLeadData();

            }
        });

        leaderboardAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(LeaderboardActivity.this, ProjectDetailActivity.class)
                        .putExtra("id", leaderboardAdapter.getData().get(position).getId())
                        .putExtra("logo",leaderboardAdapter.getData().get(position).getLogo())
                        .putExtra("symbol", leaderboardAdapter.getData().get(position).getSymbol())
                );
            }
        });



    }


    @Override
    public void onRanklabel(RankingLabelResp resp) {

        if(TextUtils.isEmpty(resp.getErrInfo())){

            if(resp.getResult()!=null){

                List<String> rank_title = new ArrayList<>();

                rankingLabelBeanList = resp.getResult().getData();

                //设置不同为排行榜纬度
                 setTablayout(rankingLabelBeanList);

              //  leaderTitleAdapter.addData(rankingLabelBeanList);

                title3.setText(getString(R.string.zh_header_ownnum));

            }

            refreshLayout.startRefresh();

        }


    }

    private void setTablayout(List<RankingLabelBean> rankingLabelBeanList) {



        for(int i=0;i<rankingLabelBeanList.size();i++){


            TabLayout.Tab tab = tabslayout_top.newTab();//获得每一个tab
            View view = LayoutInflater.from(LeaderboardActivity.this).inflate(R.layout.tab_item, null);
            {
                TextView textView = view.findViewById(R.id.tab_tx);

                textView.setTextColor(getResources().getColor(R.color.gray_33));

                if("zh_CN".equals(language)){
                    textView.setText(rankingLabelBeanList.get(i).getCnLabel());
                }else
                    textView.setText(rankingLabelBeanList.get(i).getEnLabel());

            }

            tab.setCustomView(view);//给每一个tab设置view

            tabslayout_top.addTab(tab);


        }


    }

    private String gettitle(String title1) {

        String title="";

        if("交易笔数".equals(title1)){
            title = getResources().getString(R.string.tradecount);
        }else if("持币地址数".equals(title1)){
            title = getResources().getString(R.string.onweraddressnum);
        }else if("新增账户数".equals(title1)){
            title = getResources().getString(R.string.zh_newadd_addnum);
        }else if("活跃账户数".equals(title1)){
            title = getResources().getString(R.string.zh_active_addnum);
        }else if("市值".equals(title1)){
            title = getResources().getString(R.string.value);
        }


        return title;

    }

    @Override
    public void onRankDetail(RankingDetailResp resp) {

        refreshLayout.finishRefreshing();

        refreshLayout.finishLoadmore();


        if (TextUtils.isEmpty(resp.getErrInfo())) {

            if(resp.getResult()!=null){

            if(resp.getResult().getData().size()!=0){


                nocontext.setVisibility(View.GONE);

                footerView.setVisibility(View.VISIBLE);


                leaderboardAdapter.addData(resp.getResult().getData());

            }else{
                if(choseType.equals("week")){
                    ToastUtils.show(getString(R.string.noweekdata));
                }else if(choseType.equals("month")){
                    ToastUtils.show(getString(R.string.nohourdata));
                }
                footerView.setVisibility(View.GONE);
                nocontext.setVisibility(View.VISIBLE);
            }
            }


        }else
             ToastUtils.show(getString(R.string.nodata));
    }

    @Override
    public void onError() {
        refreshLayout.finishRefreshing();
        ToastUtils.show(getString(R.string.nodata));
    }

    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

}
