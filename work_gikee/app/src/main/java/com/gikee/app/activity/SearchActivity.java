package com.gikee.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.Observer.CollectObserverService;
import com.gikee.app.Observer.base_observe.ConstObserver;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.adapter.LeaderItemAdapter;
import com.gikee.app.adapter.LeaderTitleAdapter;
import com.gikee.app.adapter.SearchAdapter;
import com.gikee.app.adapter.SearchResultAdapter;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.beans.CollectTrandBean;
import com.gikee.app.beans.LeadItemBean;
import com.gikee.app.beans.SearchBean;
import com.gikee.app.beans.SearchResp;
import com.gikee.app.beans.SearchResultBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.greendao.CollectBean;
import com.gikee.app.greendao.SQLiteUtils;
import com.gikee.app.preference_config.PreferenceUtil;
import com.gikee.app.presenter.search.InterfaceSearchView;
import com.gikee.app.presenter.search.SearchPresenter;
import com.gikee.app.resp.AddressDetailResp;
import com.gikee.app.resp.BTCTradeDetailResp;
import com.gikee.app.resp.BaseLineResp;
import com.gikee.app.resp.HotProjectResp;
import com.gikee.app.resp.IsAddressResp;
import com.gikee.app.resp.LableBean;
import com.gikee.app.resp.LookAroundResp;
import com.gikee.app.resp.RankingDetailResp;
import com.gikee.app.resp.RankingLabelBean;
import com.gikee.app.resp.TokenTypeResp;
import com.gikee.app.type.ShowType;
import com.gikee.app.views.AutoHeightViewPager;
import com.gikee.app.views.EditTextWithDel;
import com.gikee.app.views.IconView;
import com.gikee.app.views.MyRefreshBottom;
import com.gikee.app.views.MyRefreshHeader;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

/**
 * 搜索结果展示页面
 *
 * 模糊搜索 和 精确搜索
 *
 */

public class  SearchActivity extends BaseActivity<SearchPresenter> implements InterfaceSearchView {

    @Bind(R.id.cannel)
    TextView cannel;
    @Bind(R.id.back)
    IconView back;
    @Bind(R.id.hot_recycle)
    RecyclerView hot_recycle;
    @Bind(R.id.history_layout2)
    RecyclerView history_layout2;

    @Bind(R.id.hisdelete_img)
    IconView history_img;
    @Bind(R.id.hot_search)
    LinearLayout hot_search;
    @Bind(R.id.search_searchtx)
    EditTextWithDel edit_content;

    @Bind(R.id.hot_layout)
    RelativeLayout hot_layout;
    @Bind(R.id.history_recycle)
    RecyclerView history_recycle;
    @Bind(R.id.history_layout)
    RelativeLayout history_layout;

    @Bind(R.id.mineproject_refreshLayout)
    TwinklingRefreshLayout twinklingRefreshLayout;


    @Bind(R.id.searchresult_layout)
    RelativeLayout searchresult_layout;
    @Bind(R.id.base_tablelayout)
    TabLayout base_tablelayout;
    @Bind(R.id.search_recycleview)
    RecyclerView search_recycleview;

    @Bind(R.id.viewpager)
    AutoHeightViewPager viewpager;


    private String keywords = "";
    private  String  eos_address;
    private TextView re_noproject;

    private SearchPresenter mPresenter;
    private List<String> history_list;
     private SearchResultAdapter searchResultAdapter;

    private List<SearchResultBean> list = new ArrayList<>();


    private LeaderItemAdapter hotAdapter1,leaderTitleAdapter2,historyAdapter;

    private boolean isEOS=false;

    private String type=ShowType.quotes.getContent();

    static List<SearchResultBean> searchResultBeanList = new ArrayList<>();

    static List<SearchResultBean> searchAccountList = new ArrayList<>();

    static List<SearchResultBean> searchProjectList = new ArrayList<>();

    static List<SearchResultBean> searchExchangeList = new ArrayList<>();

    private int limite = 15;

    private int startquates=0;

    private int startproject=0;

    private int startaccpunt=0;

    private int startexchange=0;

    private boolean fromhistory=false;

    private boolean isFuzzy=false;



    private   Intent intent;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mineproject_add);
    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @Override
    protected void setUpView() {
        String[] hot={"BTC","ETH","BNB","1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa("+getString(R.string.btcaddress)+")","0x3f5CE5FBFe3E9af3971dD833D26bA9b5C936f0bE("+getString(R.string.binance_address)+")",
                "4a35194ed201fe456e0db468cd256d43606b6abe125135e0174ef6ea20726510("+getString(R.string.btc_big)+")","0xe08a519c03cb0aed0e04b33104112d65fa1d3a48cd3aeab65f047b2abce9d508("+getString(R.string.Fomo3D)+")",
                "b1("+getString(R.string.eos_big)+")"};

        mPresenter = new SearchPresenter(this);
        hideTitleBar();
        keywords=getIntent().getStringExtra("key");
        re_noproject = findViewById(R.id.search_rela_noproject);

        searchResultAdapter = new SearchResultAdapter(list,SearchActivity.this);

        //下拉刷新控件添加头部
        MyRefreshHeader headerView = new MyRefreshHeader(SearchActivity.this);
        MyRefreshBottom bottomView = new MyRefreshBottom(SearchActivity.this);
        twinklingRefreshLayout.setAutoLoadMore(true);
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setEnableLoadmore(true );
        twinklingRefreshLayout.setBottomView(bottomView);


        if(!TextUtils.isEmpty(keywords)){
            edit_content.setText(keywords);
        }

        //热门搜索推荐1 展现形式有区别
        hotAdapter1 = new LeaderItemAdapter();

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);

        hot_recycle.setLayoutManager(manager);

        hot_recycle.setAdapter(hotAdapter1);

        //热门搜索推荐2 展现形式有区别
        leaderTitleAdapter2= new LeaderItemAdapter();

        StaggeredGridLayoutManager manager2 = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);


        history_layout2.setLayoutManager(manager2);

        history_layout2.setAdapter(leaderTitleAdapter2);


        //历史搜索记录
        historyAdapter = new LeaderItemAdapter();

        LinearLayoutManager manager3 = new LinearLayoutManager(getApplicationContext());

        manager3.setAutoMeasureEnabled(true);

        history_recycle.setLayoutManager(manager3);

        history_recycle.setAdapter(historyAdapter);


        //搜索框的长度自适应
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) edit_content.getLayoutParams();

        params.width = (int)(MyUtils.getWidth()*(0.72));

        edit_content.setLayoutParams(params);



        List<LeadItemBean> leadItemBeans=new ArrayList<>();

        //热门搜索推荐1数据源
        for(int i=0;i<3;i++){

            LeadItemBean leadItemBean = new LeadItemBean();

            leadItemBean.setItemName(hot[i]);

            leadItemBeans.add(leadItemBean);

        }

        hotAdapter1.setNewData(leadItemBeans);


        //添加热门推荐2数据源
        List<LeadItemBean> leadItemBeans2=new ArrayList<>();
        for(int i=3;i<8;i++){

            LeadItemBean leadItemBean = new LeadItemBean();

            leadItemBean.setItemName(hot[i]);


            leadItemBeans2.add(leadItemBean);

        }

        leaderTitleAdapter2.setNewData(leadItemBeans2);


        //搜索历史记录展示
        showHistory();



        //搜索结果tab展示
        String[] titles_base={getString(R.string.quotes),getString(R.string.title_mineproject),getString(R.string.address),getString(R.string.exchange)};

        for(String tab:titles_base){

            base_tablelayout.addTab(base_tablelayout.newTab().setText(tab));
        }

        search_recycleview.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

        search_recycleview.setAdapter(searchResultAdapter);

        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.line_gray));

        search_recycleview.addItemDecoration(divider);


    }

    @Override
    protected void onResume() {
        super.onResume();
        //没有搜索结果显示弹出软键盘
        if(searchResultAdapter.getData().size()==0){
            setEditTextState();
        }

    }

    @Override
    protected void initOnclick() {
        history_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                PreferenceUtil.removeKey(Commons.history);

                history_layout.setVisibility(View.GONE);


            }
        });

        hotAdapter1.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                fromhistory=true;
                keywords = hotAdapter1.getItem(position).getItemName();
                if(!TextUtils.isEmpty(keywords)){
                    edit_content.setText(keywords);
                }

                twinklingRefreshLayout.startRefresh();

            }
        });

        leaderTitleAdapter2.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                fromhistory=true;
                keywords = leaderTitleAdapter2.getItem(position).getItemName();
                // searchData();
                twinklingRefreshLayout.startRefresh();
                if(!TextUtils.isEmpty(keywords)){
                    edit_content.setText(keywords);
                }

            }
        });


        //点击搜索历史记录
        historyAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                fromhistory=true;
                keywords =historyAdapter.getItem(position).getItemName();
                twinklingRefreshLayout.startRefresh();

                if(!TextUtils.isEmpty(keywords)){
                    edit_content.setText(keywords);
                }


            }
        });


        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                if(isFuzzy){
                    getFuzzyData();
                }else{
                    startexchange=0;
                    startproject=0;
                    startexchange=0;
                    searchData();
                }

            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {

                refreshLayout.finishLoadmore();

                if(type.equals(ShowType.quotes.getContent())){


                    if(searchResultBeanList.size()-(startquates+1)*limite<=limite&&searchResultBeanList.size()-(startquates+1)*limite>0){

                        searchResultAdapter.addData(searchResultBeanList.subList(((startquates+1)*limite),searchResultBeanList.size()));
                        startquates++;
                    }else if(searchResultBeanList.size()-(startquates+1)*limite>0){
                        searchResultAdapter.addData(searchResultBeanList.subList(((startquates+1)*limite),(startquates+2)*limite));
                        startquates++;
                    }


                }else  if(type.equals(ShowType.project.getContent())){

                    if(searchProjectList.size()-(startproject+1)*limite<=limite&&searchProjectList.size()-(startproject+1)*limite>0){

                        searchResultAdapter.addData(searchProjectList.subList(((startproject+1)*limite),searchProjectList.size()));
                        startproject++;
                    }else if(searchProjectList.size()-(startproject+1)*limite>0){
                        searchResultAdapter.addData(searchProjectList.subList(((startproject+1)*limite),(startproject+2)*limite));
                        startproject++;
                    }


                }else  if(type.equals(ShowType.exchange.getContent())){
//

                    if(searchExchangeList.size()-(startexchange+1)*limite<=limite&&searchExchangeList.size()-(startexchange+1)*limite>0){

                        searchResultAdapter.addData(searchExchangeList.subList(((startexchange+1)*limite),searchExchangeList.size()));
                        startexchange++;
                    }else if(searchExchangeList.size()-(startexchange+1)*limite>0){
                        searchResultAdapter.addData(searchExchangeList.subList(((startexchange+1)*limite),(startexchange+2)*limite));
                        startexchange++;
                    }

                }



            }
        });

        //点击软键盘的搜索按钮
        edit_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    isFuzzy=false;
                    keywords = edit_content.getText().toString().trim();
                    twinklingRefreshLayout.startRefresh();
                    return true;
                }
                return false;
            }
        });

        searchResultAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_search_list_coll:
                        if(ShowType.project.getContent().equals(searchResultAdapter.getData().get(position).getShowType())){
                            String coinname = null;

                            if (searchResultAdapter.getData().get(position).getSymbol().indexOf("/") > 0) {
                                coinname = searchResultAdapter.getData().get(position).getSymbol().substring(0, searchResultAdapter.getData().get(position).getSymbol().indexOf("/"));
                            } else
                                coinname = searchResultAdapter.getData().get(position).getSymbol();
                            if (!SQLiteUtils.getInstance().isCollect(coinname)) {
                                List<CollectBean> tags = SQLiteUtils.getInstance().selectAllContacts("project");
                                CollectBean collectBean = new CollectBean();
                                collectBean.setName(coinname);
                                collectBean.setLogo(searchResultAdapter.getData().get(position).getExchange());
                                collectBean.setType("project");
                                collectBean.setTrandnum(tags.size() + 1);
                                collectBean.setAddressid(searchResultAdapter.getData().get(position).getId());
                                collectBean.setTag(coinname);
                                collectBean.setIscollect(true);
                                SQLiteUtils.getInstance().addContacts(collectBean);
                                searchResultAdapter.getData().get(position).setCollect(true);
                                CollectObserverService.getInstance().notifyDataChanged(ConstObserver.COLLECT_PROJECT_CHANGE);
                                ToastUtils.show(getString(R.string.collect_success));
                            } else {
                                List<CollectBean> collectBeanList = SQLiteUtils.getInstance().getEntityID(coinname);
                                CollectBean collectBean = new CollectBean();
                                collectBean.setName(coinname);
                                collectBean.setLogo(searchResultAdapter.getData().get(position).getLogo());
                                collectBean.setType("project");
                                collectBean.setAddressid(searchResultAdapter.getData().get(position).getId());
                                collectBean.setId(collectBeanList.get(0).getId());
                                collectBean.setTag(coinname);
                                collectBean.setIscollect(false);
                                SQLiteUtils.getInstance().deleteContacts(collectBean);
                                searchResultAdapter.getData().get(position).setCollect(false);
                                CollectObserverService.getInstance().notifyDataChanged(ConstObserver.COLLECT_PROJECT_CHANGE);
                                ToastUtils.show(getString(R.string.collect_cannel));
                            }
                            searchResultAdapter.notifyDataSetChanged();
                        }else if(ShowType.account.getContent().equals(searchResultAdapter.getData().get(position).getShowType())){

                            String coinname =null;

                            if( searchResultAdapter.getData().get(position).getSymbol().indexOf("/")>0){
                                coinname= searchResultAdapter.getData().get(position).getSymbol().substring(0,searchResultAdapter.getData().get(position).getSymbol().indexOf("/"));
                            }else
                                coinname = searchResultAdapter.getData().get(position).getSymbol();
                            if (!SQLiteUtils.getInstance().isAddressCollect(coinname)) {
                                List<CollectBean> collectBeanList=SQLiteUtils.getInstance().selectAllContacts("address");

                                CollectBean collectBean = new CollectBean();
                                collectBean.setName(getString(R.string.my_address)+collectBeanList.size());
                                collectBean.setAddressid(coinname);
                                collectBean.setLogo(searchResultAdapter.getData().get(position).getLogo());
                                collectBean.setType("address");
                                collectBean.setTag(coinname);
                                collectBean.setIscollect(true);
                                SQLiteUtils.getInstance().addContacts(collectBean);
                                searchResultAdapter.getData().get(position).setCollect(true);
                                CollectObserverService.getInstance().notifyDataChanged(ConstObserver.COLLECT_ADDRESS_CHANGE);
                                ToastUtils.show(getString(R.string.collect_success));
                                CollectTrandBean collectTrandBean = new CollectTrandBean();
                                collectTrandBean.setCollect(true);
                                collectTrandBean.setTrandname(searchResultAdapter.getData().get(position).getSymbol());


                            } else {
                                List<CollectBean> collectBeanList=SQLiteUtils.getInstance().getAddressID(coinname);
                                CollectBean collectBean = new CollectBean();


                                collectBean.setAddressid(coinname);
                                collectBean.setType("address");
                                collectBean.setId(collectBeanList.get(0).getId());
                                collectBean.setTag(coinname);
                                collectBean.setIscollect(false);
                                SQLiteUtils.getInstance().deleteContacts(collectBean);
                                searchResultAdapter.getData().get(position).setCollect(false);
                                CollectObserverService.getInstance().notifyDataChanged(ConstObserver.COLLECT_ADDRESS_CHANGE);
                                ToastUtils.show(getString(R.string.collect_cannel));

                            }
                            searchResultAdapter.notifyDataSetChanged();

                        }


                        break;

                    case R.id.layout:

                        showKeyboardshowKeyboard(false);

                        if(ShowType.quotes.getContent().equals(searchResultAdapter.getData().get(position).getShowType())){

                            String symbol = null;

                            if (searchResultAdapter.getData().get(position).getTransaction_pair().indexOf("/") > 0) {
                                symbol = searchResultAdapter.getData().get(position).getTransaction_pair().substring(0, searchResultAdapter.getData().get(position).getTransaction_pair().indexOf("/"));
                            } else
                                symbol = searchResultAdapter.getData().get(position).getTransaction_pair();
                            String id = searchResultAdapter.getData().get(position).getId();
                            startActivity(new Intent(SearchActivity.this, ProjectDetailActivity.class).putExtra("logo", searchResultAdapter.getData().get(position).getLogo())
                                    .putExtra("symbol", symbol).putExtra("id", id).putExtra("type",type));


                        }else if(ShowType.project.getContent().equals(searchResultAdapter.getData().get(position).getShowType())){
                            String symbol = null;

                            if (searchResultAdapter.getData().get(position).getSymbol().indexOf("/") > 0) {
                                symbol = searchResultAdapter.getData().get(position).getSymbol().substring(0, searchResultAdapter.getData().get(position).getSymbol().indexOf("/"));
                            } else
                                symbol = searchResultAdapter.getData().get(position).getSymbol();
                            String id = searchResultAdapter.getData().get(position).getId();
                            startActivity(new Intent(SearchActivity.this, ProjectDetailActivity.class).putExtra("logo", searchResultAdapter.getData().get(position).getLogo())
                                    .putExtra("symbol", symbol).putExtra("id", id));


                        }else if(ShowType.account.getContent().equals(searchResultAdapter.getData().get(position).getShowType())){
                            String symbol =null;

                            if( searchResultAdapter.getData().get(position).getSymbol().indexOf("/")>0){
                                symbol= searchResultAdapter.getData().get(position).getSymbol().substring(0,searchResultAdapter.getData().get(position).getSymbol().indexOf("/"));
                            }else
                                symbol = searchResultAdapter.getData().get(position).getSymbol();


                            Intent  intent = new Intent(getApplicationContext(), AddressDetailActivity.class);

                            intent.putExtra("type","EOS");

                            intent.putExtra("paramstype","address");

                            intent.putExtra("address",symbol);

                            startActivity(intent);

                        }else if(ShowType.exchange.getContent().equals(searchResultAdapter.getData().get(position).getShowType())){

                            Intent  intent = new Intent(getApplicationContext(), ExchangeDetailActivity.class);

                            intent.putExtra("id",searchResultAdapter.getData().get(position).getId());

                            intent.putExtra("exchange",searchResultAdapter.getData().get(position).getSymbol());

                            startActivity(intent);

                        }else if(ShowType.fuzzysearch.getContent().equals(searchResultAdapter.getData().get(position).getShowType())){
                            if(type.equals(ShowType.project.getContent())){
                                String symbol = null;

                                if (searchResultAdapter.getData().get(position).getSymbol().indexOf("/") > 0) {
                                    symbol = searchResultAdapter.getData().get(position).getSymbol().substring(0, searchResultAdapter.getData().get(position).getSymbol().indexOf("/"));
                                } else
                                    symbol = searchResultAdapter.getData().get(position).getSymbol();
                                String id = searchResultAdapter.getData().get(position).getId();
                                startActivity(new Intent(SearchActivity.this, ProjectDetailActivity.class).putExtra("logo", searchResultAdapter.getData().get(position).getLogo())
                                        .putExtra("symbol", symbol).putExtra("id", id));

                            }else if(type.equals(ShowType.exchange.getContent())){

                                Intent  intent = new Intent(getApplicationContext(), ExchangeDetailActivity.class);

                                intent.putExtra("id",searchResultAdapter.getData().get(position).getId());

                                intent.putExtra("exchange",searchResultAdapter.getData().get(position).getSymbol());

                                startActivity(intent);

                            }else if(type.equals(ShowType.quotes.getContent())){
                                fromhistory=true;
                                isFuzzy=false;
                                keywords = searchResultAdapter.getItem(position).getSymbol();
                                if(!TextUtils.isEmpty(keywords)){
                                    edit_content.setText(keywords);
                                }

                                twinklingRefreshLayout.startRefresh();

                            }
                        }



                        break;
                }

            }
        });



        cannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isFuzzy=false;

                keywords = edit_content.getText().toString().trim();

                // junpDetail(keywords);

                twinklingRefreshLayout.startRefresh();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showKeyboardshowKeyboard(false);
                finish();
            }
        });

        edit_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_content.setHint("");


            }
        });

        edit_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(edit_content.getText().length()==0){
                    showHistory();
                    hot_search.setVisibility(View.VISIBLE);
                    history_layout.setVisibility(View.VISIBLE);
                    searchresult_layout.setVisibility(View.GONE);
                    base_tablelayout.setVisibility(View.GONE);
                    re_noproject.setVisibility(View.GONE);
                    searchResultBeanList.clear();
                    searchProjectList.clear();
                    searchAccountList.clear();
                    searchExchangeList.clear();
                }else{
                    searchresult_layout.setVisibility(View.VISIBLE);
                    base_tablelayout.setVisibility(View.VISIBLE);
                    hot_search.setVisibility(View.GONE);
                    history_layout.setVisibility(View.GONE);
                    re_noproject.setVisibility(View.GONE);
                    if(!fromhistory){
                        isFuzzy=true;
                        keywords = edit_content.getText().toString();
//                        getFuzzyData();
                        twinklingRefreshLayout.startRefresh();
                    }
                }


            }
        });


        base_tablelayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                searchResultAdapter.getData().clear();
                searchResultAdapter.notifyDataSetChanged();
                re_noproject.setVisibility(View.GONE);
                if(tab.getPosition()==0){
                    startquates=0;
                    type=ShowType.quotes.getContent();
                    if(isFuzzy){
                        twinklingRefreshLayout.startRefresh();
                    }else{
                        if(searchResultBeanList.size()!=0){
                            if(searchResultBeanList.size()>15){
                                searchResultAdapter.addData(searchResultBeanList.subList(0,15));
                            }else
                                searchResultAdapter.addData(searchResultBeanList);
                        }else
                            twinklingRefreshLayout.startRefresh();
                    }



                }else if(tab.getPosition()==1){
                    startproject=0;
                    type=ShowType.project.getContent();
                    if(isFuzzy){
                        twinklingRefreshLayout.startRefresh();
                    }else{
                        if(searchProjectList.size()!=0){
                            if(searchProjectList.size()>15){
                                searchResultAdapter.addData(searchProjectList.subList(0,15));
                            }else
                                searchResultAdapter.addData(searchProjectList);
                        }else
                            twinklingRefreshLayout.startRefresh();
                    }




                }else if(tab.getPosition()==2){

                    type=ShowType.account.getContent();
                    if(isFuzzy){
                        twinklingRefreshLayout.startRefresh();
                    }else {
                        if (searchAccountList.size() != 0) {
                            searchResultAdapter.addData(searchAccountList);
                        } else
                            twinklingRefreshLayout.startRefresh();
                    }


                }else if(tab.getPosition()==3){
                    startexchange=0;
                    type=ShowType.exchange.getContent();
                    if(isFuzzy){
                        getData();
                    }else{
                        if(searchExchangeList.size()!=0){
                            if(searchExchangeList.size()>15){
                                searchResultAdapter.addData(searchExchangeList.subList(0,15));
                            }else
                                searchResultAdapter.addData(searchExchangeList);
                        }else
                            twinklingRefreshLayout.startRefresh();
                    }

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
    }

    private  String starttime;

    private String  endtime;


    //开始搜索内容
    private void searchData() {
        //隐藏软键盘
        if(!isFuzzy){
            showKeyboardshowKeyboard(false);
        }

        Date dt = new Date();
        endtime= MyUtils.getCurrectDate();
        starttime = MyUtils.getOldDate(dt,-180);
//        adapter.getData().clear();
        if(!TextUtils.isEmpty(keywords)){
            //检验搜索的字段
            junpDetail(keywords);
        }else{
            ToastUtils.showCenter(getString(R.string.search_notnull));
            twinklingRefreshLayout.finishRefreshing();
        }

    }



    //根据地址类型跳转不同的页面
    private void junpDetail(String keywords) {
        String params="";
        if(keywords.contains("(")){
            params=keywords.substring(0,keywords.indexOf("("));
        }else
            params=keywords;
        eos_address = params;
        //判断搜索的内容属性
        String [] param = MyUtils.getBaseLine(params);
        addHistory(keywords);
        if(param[0].equals(Commons.BTC)&&param[1].equals(Commons.ADDRESS)){
            Log.e("baseline","BTC地址");
            intent = new Intent(getApplicationContext(), BTCAddressDetailActivity.class);

            intent.putExtra("type",param[0]);

            intent.putExtra("paramstype",param[1]);

            intent.putExtra("address",params);

            startActivity(intent);
            twinklingRefreshLayout.finishRefreshing();
        }else if(param[0].equals(Commons.BTC)&&param[1].equals(Commons.HASH)){
            Log.e("baseline","BTC块hash");

            mPresenter.getBTCTradeDetail("BTC","hash",params);


        }else if(param[0].equals(Commons.ETH)&&param[1].equals(Commons.ADDRESS)){

            intent = new Intent(getApplicationContext(), ETHAddressDetailActivity.class);

            intent.putExtra("type",param[0]);

            intent.putExtra("paramstype",param[1]);

            intent.putExtra("address",params);

            startActivity(intent);

            twinklingRefreshLayout.finishRefreshing();
        }else if(param[0].equals(Commons.ETH)&&param[1].equals(Commons.HASH)){
            Log.e("baseline","ETH交易hash或ETH块hash");
            intent = new Intent(getApplicationContext(), TradeDetailActivity.class);

            intent.putExtra("type",param[0]);

            intent.putExtra("paramstype",param[1]);

            intent.putExtra("hash",params);

            startActivity(intent);
            twinklingRefreshLayout.finishRefreshing();
        }else if(param[0].equals(Commons.EOS)&&param[1].equals(Commons.ADDRESS)){
            Log.e("baseline","EOS地址");

            intent = new Intent(getApplicationContext(), AddressDetailActivity.class);

            intent.putExtra("type",param[0]);

            intent.putExtra("paramstype",param[1]);

            intent.putExtra("address",params);

            startActivity(intent);

            twinklingRefreshLayout.finishRefreshing();

        }else {

            //判断该地址是不是EOS地址。
           // mPresenter.getIsAddress(params,"EOS");
            // mPresenter.getSearch(eos_address);
            getData();

        }



        intent = null;

    }



    //添加历史搜索记录需去重
    private void addHistory(String keywords) {

        boolean isExist=false;

        if(PreferenceUtil.getStringList(Commons.history)==null){

            history_list = new ArrayList<>();

        }else{
            history_list=PreferenceUtil.getStringList(Commons.history);

            for(int i=0;i<history_list.size();i++){
                if(history_list.get(i).equals(keywords)){
                    isExist=true;
                    break;
                }
            }
            if(!isExist){
                if(history_list.size()>4){

                    history_list.remove(0);

                }
            }

        }
        if(!isExist){
            history_list.add(keywords);

            PreferenceUtil.setStringList(Commons.history,history_list);
        }


    }


    private void showHistory() {

        //历史搜索
        if(PreferenceUtil.getStringList(Commons.history)!=null&&PreferenceUtil.getStringList(Commons.history).size()!=0){

            history_layout.setVisibility(View.VISIBLE);

            List<String> historylist = PreferenceUtil.getStringList(Commons.history);

            List<LeadItemBean> leadItemBeans=new ArrayList<>();


            for(int i=PreferenceUtil.getStringList(Commons.history).size()-1;i>=0;i--){


                // RankingLabelBean rankingLabelBean = new RankingLabelBean();

                LeadItemBean leadItemBean = new LeadItemBean();

                leadItemBean.setItemName(historylist.get(i));

                // rankingLabelBean.setLable(leadItemBean);

                // rankingLabelBean.setType("history");

                leadItemBeans.add(leadItemBean);
            }

            historyAdapter.setNewData(leadItemBeans);
        }else
            history_layout.setVisibility(View.GONE);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 400){
            showHistory();
        }

    }

    @Override
    public void onSearchView(SearchResp items) {
        //精确搜索结果
        twinklingRefreshLayout.finishRefreshing();
        fromhistory=false;

            if (TextUtils.isEmpty(items.getErrInfo())) {

                searchResultAdapter.getData().clear();
                searchResultAdapter.notifyDataSetChanged();

                if (items.getResult() == null || items.getResult().getData().size() <= 0){
                    searchResultAdapter.setNewData(items.getResult().getData());
                    String  no_msg = getResources().getString(R.string.mp_add_des1);
                    String  noproject = "";
                    if(type.equals(ShowType.account.getContent())){
                        noproject=  String.format(no_msg,getResources().getString(R.string.zh_zh_des));

                    }else if(type.equals(ShowType.quotes.getContent())){

                        noproject=  String.format(no_msg,getResources().getString(R.string.quotes));

                    }else if(type.equals(ShowType.project.getContent())){

                        noproject=  String.format(no_msg,getResources().getString(R.string.title_mineproject));

                    }else if(type.equals(ShowType.exchange.getContent())){

                        noproject=  String.format(no_msg,getResources().getString(R.string.exchange));

                    }

                    re_noproject.setVisibility(View.VISIBLE);
                    re_noproject.setText(noproject);

                }
                else {


                    if(type.equals(ShowType.quotes.getContent())){

                        searchResultBeanList = items.getResult().getData();

                    }else if(type.equals(ShowType.project.getContent())){

                        searchProjectList = items.getResult().getData();

                    }else if(type.equals(ShowType.exchange.getContent())){

                        searchExchangeList = items.getResult().getData();

                    }


                    if(items.getResult().getData().size()>15){

                        if(isFuzzy){
                            //模糊匹配
                            List<SearchResultBean> searchResultBeanList = items.getResult().getData();

                            for(SearchResultBean searchResultBean:searchResultBeanList){

                                if(searchResultBean.getShowType().equals(ShowType.quotes.getContent())){
                                    searchResultBean.setSymbol(searchResultBean.getTransaction_pair());
                                }
                                searchResultBean.setShowType(ShowType.fuzzysearch.getContent());
                            }

                            searchResultAdapter.addData(searchResultBeanList);

                        }else
                            searchResultAdapter.addData(items.getResult().getData().subList(0,15));
                    }else if(items.getResult().getData().size()==1){
                        if(items.getResult().getData().get(0).getShowType().equals(ShowType.account.getContent())){
                            if(!TextUtils.isEmpty(items.getResult().getData().get(0).getBalance())){

                                searchAccountList= items.getResult().getData();

                                items.getResult().getData().get(0).setSymbol(eos_address);

                                searchResultAdapter.setNewData(items.getResult().getData());
                            }else{
                                searchResultAdapter.getData().clear();
                                searchResultAdapter.notifyDataSetChanged();
                                String  no_msg = getResources().getString(R.string.mp_add_des1);
                                String  noproject = "";
                                noproject=  String.format(no_msg,getResources().getString(R.string.zh_zh_des));
                                re_noproject.setVisibility(View.VISIBLE);
                                re_noproject.setText(noproject);
                            }

                        }else{
                            if(isFuzzy){
                                //模糊匹配
                                List<SearchResultBean> searchResultBeanList = items.getResult().getData();

                                for(SearchResultBean searchResultBean:searchResultBeanList){

                                    if(searchResultBean.getShowType().equals(ShowType.quotes.getContent())){
                                        searchResultBean.setSymbol(searchResultBean.getTransaction_pair());
                                    }
                                    searchResultBean.setShowType(ShowType.fuzzysearch.getContent());
                                }

                                searchResultAdapter.addData(searchResultBeanList);

                            }else
                                searchResultAdapter.setNewData(items.getResult().getData());

                        }

                    }else{

                        if(isFuzzy){
                            //模糊匹配
                            List<SearchResultBean> searchResultBeanList = items.getResult().getData();

                            for(SearchResultBean searchResultBean:searchResultBeanList){

                                if(searchResultBean.getShowType().equals(ShowType.quotes.getContent())){
                                    searchResultBean.setSymbol(searchResultBean.getTransaction_pair());
                                }
                                searchResultBean.setShowType(ShowType.fuzzysearch.getContent());
                            }

                            searchResultAdapter.addData(searchResultBeanList);

                        }else
                            searchResultAdapter.setNewData(items.getResult().getData());


                    }





                }

            } else {
                ToastUtils.show("" + items.getErrInfo());
            }



    }

    @Override
    public void onFuzzySearch(SearchResp items) {
        //模糊匹配结果
        twinklingRefreshLayout.finishRefreshing();
        twinklingRefreshLayout.finishLoadmore();
        fromhistory=false;

        if(TextUtils.isEmpty(items.getErrInfo())){

            if(items.getResult()!=null){

                searchResultAdapter.setNewData(items.getResult().getData());

            }


        }
    }


    @Override
    public void onSearchAddressView(TokenTypeResp tokenTypeResp) {




    }

    @Override
    public void onLookAround(LookAroundResp lookAroundResp) {

    }

    @Override
    public void onHot(HotProjectResp searchBean) {

    }

    @Override
    public void onRankDetail(RankingDetailResp resp) {

    }

    @Override
    public void getRankDetail2(RankingDetailResp resp) {

    }

    @Override
    public void ontHomeBaseLine(BaseLineResp resp) {

    }

    @Override
    public void onBTCTradeDetail(BTCTradeDetailResp btcTradeDetailResp) {

        if(TextUtils.isEmpty(btcTradeDetailResp.getErrInfo())){

            if(btcTradeDetailResp.getResult()!=null){

                String params="";
                if(keywords.contains("(")){
                    params=keywords.substring(0,keywords.indexOf("("));
                }else
                    params=keywords;

                intent = new Intent(getApplicationContext(), BTCTradeDetailActivity.class);

                intent.putExtra("type","BTC");

                intent.putExtra("paramstype","hash");

                intent.putExtra("address",params);

                intent.putExtra("tradeDetail",btcTradeDetailResp.getResult());

                startActivity(intent);
            }


        }

        twinklingRefreshLayout.finishRefreshing();

    }

    @Override
    public void onEOSTradeDetail(IsAddressResp resp) {





    }

    public void getData(){
        mPresenter.getSearch(eos_address,type);
    }


    public void getFuzzyData(){
        mPresenter.getFuzzySearch(keywords,type,1);
    }



    @Override
    public void onError() {

        twinklingRefreshLayout.finishRefreshing();

    }


    protected void showKeyboardshowKeyboard(boolean isShow) {
        if(!isShow){
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }else{

            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus()
                            .getWindowToken(), InputMethodManager.SHOW_FORCED);
        }

    }



    private void setEditTextState() {
        edit_content.setFocusable(true);
        edit_content.setFocusableInTouchMode(true);
        edit_content.requestFocus();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) edit_content.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(edit_content, 0);
                           }

                       },
                500);
    }




}