package com.gikee.app.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.Observer.CollectObserverService;
import com.gikee.app.Observer.base_observe.ConstObserver;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.activity.LeaderboardActivity;
import com.gikee.app.activity.ProjectDetailActivity;
import com.gikee.app.activity.SearchActivity;
import com.gikee.app.activity.ZhanghuNumActivity;
import com.gikee.app.adapter.LeaderTitleAdapter;
import com.gikee.app.adapter.LeaderboardAdapter;
import com.gikee.app.beans.CollectTrandBean;
import com.gikee.app.greendao.CollectBean;
import com.gikee.app.greendao.SQLiteUtils;
import com.gikee.app.greendao.TrandBean;
import com.gikee.app.language.LanguageType;
import com.gikee.app.language.MultiLanguageUtil;
import com.gikee.app.presenter.search.RankingLabelPresenter;
import com.gikee.app.presenter.search.RankingLabelView;
import com.gikee.app.resp.RankingDetailResp;
import com.gikee.app.resp.RankingLabelBean;
import com.gikee.app.resp.RankingLabelResp;
import com.gikee.app.share.ShowShareDialog;
import com.gikee.app.views.MyRefreshBottom;
import com.gikee.app.views.MyRefreshHeader;
import com.gikee.app.views.PopMenuMore;
import com.gikee.app.views.PopMenuMoreItem;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;

public class LeaderboardFragment extends BaseFragment<RankingLabelPresenter> implements RankingLabelView,View.OnClickListener {

    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @Bind(R.id.viewstub)
    LinearLayout nocontext;

//    @Bind(R.id.today_add_title2)
    TextView title2;
//    @Bind(R.id.today_add_title3)
    TextView title3;
//    @Bind(R.id.today_add_title4)
    TextView title4;



//    @Bind(R.id.title2_img)
    ImageView title2_img;
//    @Bind(R.id.title3_img)
    ImageView title3_img;
//    @Bind(R.id.title4_img)
    ImageView title4_img;





    private RankingLabelPresenter mPresenter;

    private LeaderboardAdapter leaderboardAdapter;

    private String lableId="all";

    private int page=1;

    private int limit=15;

    private String choseType="day";

//    private List<RankingLabelBean> rankingLabelBeanList = new ArrayList<>();


    private static final int USER_SHARE = 0;

    private  int currect_positio;

    private String language = "";

    private int title2_flag=0;//0正序 1倒叙排列 2 无序

    private int title3_flag=2;//0正序 1倒叙排列 2 无序

    private int title4_flag=2;//0正序 1倒叙排列 2 无序

    private String rankedBy = "marketValue";

    private int reverse=0;//正序

    private boolean issort=false;

    private PopupWindow mPopupWindow;

    private CollectBean collectBean;

    public static LeaderboardFragment getInstance(String lableId){

        LeaderboardFragment leaderboardFragment = new LeaderboardFragment();

        leaderboardFragment.setParams(lableId);

        return leaderboardFragment;

    }

    public void setParams(String lableId){

        this.lableId  =lableId;

    }



    @Override
    protected void setupViews(LayoutInflater inflater) {
        setContentView(inflater, R.layout.fragment_leaderboard);
    }

    @Override
    protected void initView() {
        mPresenter = new RankingLabelPresenter(this);

        MyRefreshHeader headerView = new MyRefreshHeader(getContext());
        MyRefreshBottom bottomView = new MyRefreshBottom(getContext());
         View view = LayoutInflater.from(getContext()).inflate(R.layout.view_all_shuju_zhanghu_header, null);

        title2_img = view.findViewById(R.id.title2_img);
        title3_img = view.findViewById(R.id.title3_img);
        title4_img = view.findViewById(R.id.title4_img);

        title2=view.findViewById(R.id.today_add_title2);
        title3=view.findViewById(R.id.today_add_title3);
        title4=view.findViewById(R.id.today_add_title4);

        refreshLayout.setAutoLoadMore(true);
        refreshLayout.setHeaderView(headerView);
        refreshLayout.setEnableLoadmore(true);
        refreshLayout.setBottomView(bottomView);

        leaderboardAdapter = new LeaderboardAdapter();

        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerview.setAdapter(leaderboardAdapter);

        leaderboardAdapter.addHeaderView(view);


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
        initOnclick();

//        getData();

    }

    private void getLeadData() {

        mPresenter.getRankDetail(lableId,page,rankedBy,reverse);
    }


    private void initOnclick() {

        title2.setOnClickListener(this);
        title3.setOnClickListener(this);
        title4.setOnClickListener(this);

        title2_img.setOnClickListener(this);
        title3_img.setOnClickListener(this);
        title4_img.setOnClickListener(this);

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page=1;
                issort=true;

                getLeadData();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {

                issort=false;
//                footerView.setVisibility(View.GONE);
                page++;
                getLeadData();


            }
        });




        leaderboardAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getContext(), ProjectDetailActivity.class)
                        .putExtra("id", leaderboardAdapter.getData().get(position).getId())
                        .putExtra("logo",leaderboardAdapter.getData().get(position).getLogo())
                        .putExtra("symbol", leaderboardAdapter.getData().get(position).getSymbol())
                );
            }
        });


        leaderboardAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                  String id =  leaderboardAdapter.getData().get(position).getId();
                  String logo =leaderboardAdapter.getData().get(position).getLogo();
                  String symbol = leaderboardAdapter.getData().get(position).getSymbol();
                showPlayAgainPopup(view,id,logo,symbol);
                return false;
            }
        });



    }


    private void showPlayAgainPopup(View view, final String id, final String logo, final String coinSymbol) {

        if(mPopupWindow!=null){
            mPopupWindow=null;
        }
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.result_page_popup_window, null);

        TextView popupText = contentView.findViewById(R.id.play_again_text);
        popupText.setText(getContext().getResources().getString(R.string.add_choose));

        mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {



                List<CollectBean> collectBeanList=SQLiteUtils.getInstance().getEntityID(coinSymbol);
                if(collectBeanList.size()!=0){
                    ToastUtils.show(getString(R.string.collect_success));
                }else{
                    //添加收藏
                    collectBean = new CollectBean();
                    collectBean.setName(coinSymbol);
                    collectBean.setLogo(logo);
                    collectBean.setType("project");
                    collectBean.setAddressid(id);
                    collectBean.setTag(coinSymbol);
                    collectBean.setIscollect(true);
                    SQLiteUtils.getInstance().addContacts(collectBean);
                    CollectObserverService.getInstance().notifyDataChanged(ConstObserver.COLLECT_PROJECT_CHANGE);
                    ToastUtils.show(getString(R.string.collect_success));
                    CollectTrandBean collectTrandBean = new CollectTrandBean();
                    collectTrandBean.setCollect(true);
                    collectTrandBean.setTrandname(coinSymbol);
                    collectBean=null;
                }

                mPopupWindow.dismiss();


                return false;
            }
        });
        mPopupWindow.setBackgroundDrawable(getContext().getResources().getDrawable(R.color.black));
        int width = mPopupWindow.getWidth();
        int[] xy = new int[2];
        view.getLocationInWindow(xy);
//        mPopupWindow.showAtLocation(view, Gravity.BOTTOM,
//                xy[0] + (MyUtils.dip2px(width) - view.getWidth()) / 2, xy[1] - MyUtils.dip2px(50));

        mPopupWindow.showAsDropDown(view, 200, 0);
    }





    @Override
    protected void lazyLoad() {

        if (isViewCreated && isUIVisible) {
            refreshLayout.startRefresh();
            isViewCreated = false;
            isUIVisible = false;

        }

    }

    @Override
    protected void onChangeEvent(int type) {

        if(type== LanguageType.UNIT_CNY||type== LanguageType.UNIT_USD){
            refreshLayout.startRefresh();
        }



    }

    @Override
    public void onRanklabel(RankingLabelResp resp) {

//        if(TextUtils.isEmpty(resp.getErrInfo())){
//
//            if(resp.getResult()!=null){
//
//                rankingLabelBeanList = resp.getResult().getData();
//                //设置不同为排行榜纬度
//                setTablayout(rankingLabelBeanList);
//
////                title3.setText(getString(R.string.market_title));
//
//            }
//
//            refreshLayout.startRefresh();
//
//        }

    }


    private void setTablayout(List<RankingLabelBean> rankingLabelBeanList) {

//        TabLayout.Tab tab1 = tabslayout_top.newTab();//获得每一个tab
//        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.tab_item, null);
//        {
//            TextView textView = view1.findViewById(R.id.tab_tx);
//
//            textView.setTextColor(getResources().getColor(R.color.gray_33));
//
//            textView.setText(getString(R.string.optional));
//
//
//        }
//
//        tab1.setCustomView(view1);//给每一个tab设置view
//
//        tabslayout_top.addTab(tab1);
//
//
//
//        for(int i=0;i<rankingLabelBeanList.size();i++){
//
//
//            TabLayout.Tab tab = tabslayout_top.newTab();//获得每一个tab
//            View view = LayoutInflater.from(getContext()).inflate(R.layout.tab_item, null);
//            {
//                TextView textView = view.findViewById(R.id.tab_tx);
//
//                textView.setTextColor(getResources().getColor(R.color.gray_33));
//
//                if("zh_CN".equals(language)){
//                    textView.setText(rankingLabelBeanList.get(i).getCnLabel());
//                }else
//                    textView.setText(rankingLabelBeanList.get(i).getEnLabel());
//
//            }
//
//            tab.setCustomView(view);//给每一个tab设置view
//
//            tabslayout_top.addTab(tab);
//
//
//        }

//        setTabWidth(tabslayout_top,50);


    }

    @Override
    public void onRankDetail(RankingDetailResp resp) {

        refreshLayout.finishRefreshing();

        refreshLayout.finishLoadmore();


        if (TextUtils.isEmpty(resp.getErrInfo())) {

            if(resp.getResult()!=null){

                if(resp.getResult().getData().size()!=0){


                    nocontext.setVisibility(View.GONE);

//                    footerView.setVisibility(View.VISIBLE);

                    if(issort){
                        leaderboardAdapter.setNewData(resp.getResult().getData());
                    }else
                        leaderboardAdapter.addData(resp.getResult().getData());

                }else{
                    if(choseType.equals("week")){
                        ToastUtils.show(getString(R.string.noweekdata));
                    }else if(choseType.equals("month")){
                        ToastUtils.show(getString(R.string.nohourdata));
                    }
//                    footerView.setVisibility(View.GONE);
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

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.today_add_title2||view.getId()==R.id.title2_img){

            rankedBy = "marketValue";

            if(title2_flag==0){

                reverse =1;

                title2_img.setBackground(getContext().getResources().getDrawable(R.mipmap.daoxu));

                title2_flag = 1;

            }else if(title2_flag==1){

                reverse =0;

                title2_img.setBackground(getContext().getResources().getDrawable(R.mipmap.zhengxu));

                title2_flag = 0;

            }else if(title2_flag==2){

                title2_img.setBackground(getContext().getResources().getDrawable(R.mipmap.zhengxu));

                title2_flag = 0;

                reverse =0;

            }

            title3_flag = 2;

            title4_flag = 2;

            title3_img.setBackground(getContext().getResources().getDrawable(R.mipmap.wuxu));

            title4_img.setBackground(getContext().getResources().getDrawable(R.mipmap.wuxu));



        }else if(view.getId()==R.id.today_add_title3||view.getId()==R.id.title3_img){

            rankedBy = "price";

            if(title3_flag==0){

                reverse =1;

                title3_img.setBackground(getContext().getResources().getDrawable(R.mipmap.daoxu));

                title3_flag = 1;

            }else if(title3_flag==1){

                reverse =0;

                title3_img.setBackground(getContext().getResources().getDrawable(R.mipmap.zhengxu));

                title3_flag = 0;

            }else if(title3_flag==2){

                title3_img.setBackground(getContext().getResources().getDrawable(R.mipmap.zhengxu));

                title3_flag = 0;

                reverse =0;

            }

            title2_flag = 2;

            title4_flag = 2;

            title2_img.setBackground(getContext().getResources().getDrawable(R.mipmap.wuxu));

            title4_img.setBackground(getContext().getResources().getDrawable(R.mipmap.wuxu));

        }else if(view.getId()==R.id.today_add_title4||view.getId()==R.id.title4_img){

            rankedBy = "priceChange";

            if(title4_flag==0){

                reverse =1;

                title4_img.setBackground(getContext().getResources().getDrawable(R.mipmap.daoxu));

                title4_flag = 1;

            }else if(title4_flag==1){

                reverse =0;

                title4_img.setBackground(getContext().getResources().getDrawable(R.mipmap.zhengxu));

                title4_flag = 0;

            }else if(title4_flag==2){

                title4_img.setBackground(getContext().getResources().getDrawable(R.mipmap.zhengxu));

                title4_flag = 0;

                reverse =0;

            }

            title2_flag = 2;

            title3_flag = 2;

            title2_img.setBackground(getContext().getResources().getDrawable(R.mipmap.wuxu));

            title3_img.setBackground(getContext().getResources().getDrawable(R.mipmap.wuxu));

        }

        issort=true;

        page=1;

        limit = 15;

        refreshLayout.startRefresh();

//        getLeadData();

    }


}
