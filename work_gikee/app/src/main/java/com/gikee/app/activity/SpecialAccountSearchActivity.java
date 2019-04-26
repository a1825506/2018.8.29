/**
 * 特殊地址搜索
 *
 */
package com.gikee.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
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
import com.gikee.app.adapter.SpecialAccountAdapter;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.beans.LeadItemBean;
import com.gikee.app.beans.SearchResultBean;
import com.gikee.app.beans.SpecialAccountBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.greendao.CollectBean;
import com.gikee.app.greendao.SQLiteUtils;
import com.gikee.app.language.LanguageType;
import com.gikee.app.language.OnChangeLanguageEvent;
import com.gikee.app.preference_config.PreferenceUtil;
import com.gikee.app.presenter.search.SpecialSearchPresenter;
import com.gikee.app.presenter.search.SpecialSearchView;
import com.gikee.app.resp.AddressAddedResp;
import com.gikee.app.resp.MonitorTradeResp;
import com.gikee.app.resp.SpecialAccountResp;
import com.gikee.app.type.ShowType;
import com.gikee.app.views.EditTextWithDel;
import com.gikee.app.views.IconView;
import com.gikee.app.views.MyRefreshBottom;
import com.gikee.app.views.MyRefreshHeader;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

public class SpecialAccountSearchActivity extends BaseActivity<SpecialSearchPresenter> implements SpecialSearchView {

    @Bind(R.id.hot_search)
    LinearLayout hot_search;

//    @Bind(R.id.hot_recycle)
//    RecyclerView hot_recycle;

    @Bind(R.id.search_searchtx)
    EditTextWithDel edit_content;

    @Bind(R.id.history_recycle)
    RecyclerView history_recycle;

    @Bind(R.id.mineproject_refreshLayout)
    TwinklingRefreshLayout twinklingRefreshLayout;

    @Bind(R.id.search_recycleview)
    RecyclerView search_recycleview;

    @Bind(R.id.history_layout)
    RelativeLayout history_layout;

    @Bind(R.id.search)
    TextView search;

    @Bind(R.id.back)
    IconView back;

    @Bind(R.id.hisdelete_img)
    IconView history_img;

    @Bind(R.id.nodata)
    TextView nodata;

    @Bind(R.id.exchange)
    TextView exchange;
    @Bind(R.id.celebrity)
    TextView celebrity;
    @Bind(R.id.hack)
    TextView hack;
    @Bind(R.id.foundation)
    TextView foundation;
    @Bind(R.id.events)
    TextView events;
    @Bind(R.id.mining)
    TextView mining;


    private boolean isLoadmore=false;
    private LeaderItemAdapter hotAdapter,historyAdapter;

    private SpecialAccountAdapter specialAccountAdapter;

    private String keywords=null;

    private List<SpecialAccountBean> list = new ArrayList<>();

    private static int mresultcode=400;

    private int page=1;

    String[] hot={"火币","币安","K网","中本聪","比萨事件","DEA事件","黑客","ETH基金会"};

    private List<String> history_list;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialaccount);
    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        setEditTextState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==mresultcode){
            finish();
        }
    }

    @Override
    protected void setUpView() {
        hideTitleBar();
        mPresenter = new SpecialSearchPresenter(this);
        //下拉刷新控件添加头部
        MyRefreshHeader headerView = new MyRefreshHeader(SpecialAccountSearchActivity.this);
        MyRefreshBottom bottomView = new MyRefreshBottom(SpecialAccountSearchActivity.this);
        twinklingRefreshLayout.setAutoLoadMore(false);
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setEnableLoadmore(false );
        twinklingRefreshLayout.setBottomView(bottomView);


        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) edit_content.getLayoutParams();

        params.width = (int)(MyUtils.getWidth()*(0.72));

        edit_content.setLayoutParams(params);

//        hotAdapter = new LeaderItemAdapter();

        historyAdapter = new LeaderItemAdapter();

//        List<LeadItemBean> leadItemBeans=new ArrayList<>();
//
//        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
//
        LinearLayoutManager manager3 = new LinearLayoutManager(getApplicationContext());

        manager3.setAutoMeasureEnabled(true);
//
//        hot_recycle.setLayoutManager(manager);
//
//        hot_recycle.setAdapter(hotAdapter);


        history_recycle.setLayoutManager(manager3);

        history_recycle.setAdapter(historyAdapter);



        specialAccountAdapter = new SpecialAccountAdapter();

        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.line_gray));

        search_recycleview.addItemDecoration(divider);
        search_recycleview.setLayoutManager(new LinearLayoutManager(this));

        search_recycleview.setAdapter(specialAccountAdapter);

        //搜索历史记录展示
        showHistory();

    }

    @Override
    protected void initOnclick() {

        //点击搜索推荐项目
        exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keywords = exchange.getText().toString();
                if(!TextUtils.isEmpty(keywords)){
                    edit_content.setText(keywords);
                }

                twinklingRefreshLayout.startRefresh();
            }
        });

        celebrity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keywords = celebrity.getText().toString();
                if(!TextUtils.isEmpty(keywords)){
                    edit_content.setText(keywords);
                }

                twinklingRefreshLayout.startRefresh();
            }
        });

        hack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keywords = hack.getText().toString();
                if(!TextUtils.isEmpty(keywords)){
                    edit_content.setText(keywords);
                }

                twinklingRefreshLayout.startRefresh();
            }
        });

        foundation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keywords = foundation.getText().toString();
                if(!TextUtils.isEmpty(keywords)){
                    edit_content.setText(keywords);
                }

                twinklingRefreshLayout.startRefresh();
            }
        });


        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keywords = events.getText().toString();
                if(!TextUtils.isEmpty(keywords)){
                    edit_content.setText(keywords);
                }

                twinklingRefreshLayout.startRefresh();
            }
        });

        mining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keywords = mining.getText().toString();
                if(!TextUtils.isEmpty(keywords)){
                    edit_content.setText(keywords);
                }

                twinklingRefreshLayout.startRefresh();
            }
        });




        //点击搜索历史记录
        historyAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                keywords =historyAdapter.getItem(position).getItemName();
                twinklingRefreshLayout.startRefresh();
                if(!TextUtils.isEmpty(keywords)){
                    edit_content.setText(keywords);
                }


            }
        });

        //清除历史记录
        history_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                PreferenceUtil.removeKey(Commons.history_special);

                history_layout.setVisibility(View.GONE);


            }
        });

        //点击软键盘的搜索按钮
        edit_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    keywords = edit_content.getText().toString().trim();
                    twinklingRefreshLayout.startRefresh();
                    return true;
                }
                return false;
            }
        });


        //刷新控件
        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                history_layout.setVisibility(View.GONE);
                page=1;
                isLoadmore=false;

                searchData();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {

//                page++;
                isLoadmore=true;
                searchData();



            }
        });

        //点击搜索
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keywords = edit_content.getText().toString().trim();
                searchData();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showKeyboardshowKeyboard(false);
                finish();
            }
        });

        //点击列表
        specialAccountAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                String type = specialAccountAdapter.getData().get(position).getType();

                String address_type = specialAccountAdapter.getData().get(position).getCoin();

                String name =specialAccountAdapter.getData().get(position).getName();

                List<String> address_list = specialAccountAdapter.getData().get(position).getAddress();

                StringBuffer sb  = new StringBuffer();

                for(String address: address_list){

                    sb.append(address+",");

                }
                String address = null;

                if(type.equals(ShowType.exchange.getContent())){
                    address =  ShowType.exchange.getContent()+ name + address_type;
                }else
                    address = specialAccountAdapter.getData().get(position).getAddress().get(0);


                String name_Cn = specialAccountAdapter.getData().get(position).getName_cn();

                int count_num = specialAccountAdapter.getData().get(position).getCount();



                if (!SQLiteUtils.getInstance().isAddressCollect(address)) {
                    CollectBean collectBean = new CollectBean();
                    if(!TextUtils.isEmpty(name_Cn)){
                        collectBean.setName(name+"/"+name_Cn);
                    }else
                        collectBean.setName(name);

                    collectBean.setAddressid(address);
                    collectBean.setAddress_list(sb.toString());
                    collectBean.setType("address");
                    collectBean.setLogo(specialAccountAdapter.getData().get(position).getLogo());
                    collectBean.setAddress_type(address_type);
                    collectBean.setTag(address);
                    collectBean.setCount(String.valueOf(count_num));
                    collectBean.setDetail(address);
                    collectBean.setIscollect(true);
                    SQLiteUtils.getInstance().addContacts(collectBean);
                    specialAccountAdapter.getData().get(position).setChoose(true);
                    specialAccountAdapter.notifyItemChanged(position);
                    Commons.collect_address = address;
                    EventBus.getDefault().post(new OnChangeLanguageEvent(LanguageType.GET_ADDRESS_BALANCE));
                }
            }
        });

        //点击输入框
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
                    search_recycleview.setVisibility(View.GONE);
                    nodata.setVisibility(View.GONE);
                }

            }
        });

    }

    private void searchData() {

        mPresenter.getSpecialSearch(keywords,page);

        addHistory(keywords);
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

    @Override
    public void onSpecialSearchView(SpecialAccountResp resp) {

        twinklingRefreshLayout.finishRefreshing();

        twinklingRefreshLayout.finishLoadmore();

        MyUtils.hideKeyBoard(SpecialAccountSearchActivity.this);

        if(TextUtils.isEmpty(resp.getErrInfo())){

            if(resp.getResult()!=null){

                if(resp.getResult().getData().size()!=0){

                    hot_search.setVisibility(View.GONE);

                    nodata.setVisibility(View.GONE);

                    history_layout.setVisibility(View.GONE);

                    twinklingRefreshLayout.setVisibility(View.VISIBLE);

                    search_recycleview.setVisibility(View.VISIBLE);

                    List<SpecialAccountBean> data=resp.getResult().getData();

                    for(int i=0;i<data.size();i++){

                        SpecialAccountBean specialAccountBean = data.get(i);

                        String type = specialAccountBean.getType();

                        String address= null;

                        String name = specialAccountBean.getName();

                        String coin = specialAccountBean.getCoin();

                        if(type.equals(ShowType.exchange.getContent())){
                            address =  ShowType.exchange.getContent()+ name + coin;
                        }else
                            address = specialAccountBean.getAddress().get(0);



                        if (SQLiteUtils.getInstance().isAddressCollect(address)) {

                            specialAccountBean.setChoose(true);

                        }
                    }

//                    if(isLoadmore){
//                        specialAccountAdapter.addData(data);
//                    }else
                        specialAccountAdapter.setNewData(data);


                }else
                    showNoData();

            }else
                showNoData();

        }else
            showNoData();

    }

    @Override
    public void onAllAccount(SpecialAccountResp resp) {

    }

    private void showNoData() {

        hot_search.setVisibility(View.VISIBLE);

        history_layout.setVisibility(View.GONE);

        search_recycleview.setVisibility(View.GONE);

        twinklingRefreshLayout.setVisibility(View.GONE);

        nodata.setVisibility(View.VISIBLE);
    }


    @Override
    public void onMineAddress(AddressAddedResp resp) {

    }

    @Override
    public void onMonitorTrade(MonitorTradeResp resp) {

    }

    @Override
    public void onError() {

    }


    protected void showKeyboardshowKeyboard(boolean isShow) {
        if(!isShow){
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(SpecialAccountSearchActivity.this.getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }else{

            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(SpecialAccountSearchActivity.this.getCurrentFocus()
                            .getWindowToken(), InputMethodManager.SHOW_FORCED);
        }

    }

    private void showHistory() {

        //历史搜索
        if(PreferenceUtil.getStringList(Commons.history_special)!=null&&PreferenceUtil.getStringList(Commons.history_special).size()!=0){

            history_layout.setVisibility(View.VISIBLE);

            List<String> historylist = PreferenceUtil.getStringList(Commons.history_special);

            List<LeadItemBean> leadItemBeans=new ArrayList<>();


            for(int i=PreferenceUtil.getStringList(Commons.history_special).size()-1;i>=0;i--){

                LeadItemBean leadItemBean = new LeadItemBean();

                leadItemBean.setItemName(historylist.get(i));

                leadItemBeans.add(leadItemBean);
            }

            historyAdapter.setNewData(leadItemBeans);
        }else
            history_layout.setVisibility(View.GONE);


    }


    //添加历史搜索记录需去重
    private void addHistory(String keywords) {

        boolean isExist=false;

        if(PreferenceUtil.getStringList(Commons.history_special)==null){

            history_list = new ArrayList<>();

        }else{
            history_list=PreferenceUtil.getStringList(Commons.history_special);

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

            PreferenceUtil.setStringList(Commons.history_special,history_list);
        }


    }
}
