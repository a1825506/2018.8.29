package com.gikee.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
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
import com.gikee.app.Observer.base_observe.ConstObserver;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.adapter.MarkViewAdapter;
import com.gikee.app.adapter.ProjectChooseAcapter;
import com.gikee.app.adapter.SearchAdapter;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.beans.IndexChooseBean;
import com.gikee.app.beans.SearchBean;
import com.gikee.app.beans.SearchResp;
import com.gikee.app.beans.SearchResultBean;
import com.gikee.app.greendao.CollectBean;
import com.gikee.app.greendao.SQLiteUtils;
import com.gikee.app.presenter.search.InterfaceSearchView;
import com.gikee.app.presenter.search.SearchPresenter;
import com.gikee.app.resp.BTCTradeDetailResp;
import com.gikee.app.resp.BaseLineResp;
import com.gikee.app.resp.HotProjectResp;
import com.gikee.app.resp.IsAddressResp;
import com.gikee.app.resp.LookAroundResp;
import com.gikee.app.resp.RankingDetailResp;
import com.gikee.app.resp.TokenTypeResp;
import com.gikee.app.views.EditTextWithDel;
import com.gikee.app.views.IconView;
import com.gikee.app.views.MyBoldTextView;
import com.gikee.app.views.MyRefreshHeader;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;

/**
 * 项目详情-项目pk-添加pk项目
 */
public class AddProjectActivity extends BaseActivity<SearchPresenter> implements InterfaceSearchView {


    @Bind(R.id.search)
    TextView search;
    @Bind(R.id.search_searchtx)
    EditTextWithDel edit_content;
    @Bind(R.id.add_title)
    MyBoldTextView add_title;

    @Bind(R.id.my_collect)
    LinearLayout my_collect;
    @Bind(R.id.collect_recycle)
    RecyclerView collect_recycle;

    @Bind(R.id.back)
    IconView back;

    @Bind(R.id.mineproject_refreshLayout)
    TwinklingRefreshLayout twinklingRefreshLayout;

    @Bind(R.id.search_rela_noproject)
     TextView re_noproject;

    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;


    private SearchAdapter adapter;

    private String keywords = "";

    private ProjectChooseAcapter projectChooseAcapter;

    private List<IndexChooseBean> chooseItemBeanList = new ArrayList<>();

    private IndexChooseBean indexChooseBean;

    private List<IndexChooseBean> choose_title = new ArrayList<>();

    private boolean isFuzzy=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @Override
    protected void setUpView() {

        mPresenter = new SearchPresenter(this);


        hideTitleBar();

        choose_title  = (ArrayList<IndexChooseBean>) getIntent().getSerializableExtra("choosetitle");

        MyRefreshHeader headerView = new MyRefreshHeader(AddProjectActivity.this);

        twinklingRefreshLayout.setAutoLoadMore(false);
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setEnableLoadmore(false );

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) edit_content.getLayoutParams();

        params.width = (int)(MyUtils.getWidth()*(0.72));

        edit_content.setLayoutParams(params);

        adapter = new SearchAdapter();
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.line_gray));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.addItemDecoration(divider);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        projectChooseAcapter = new ProjectChooseAcapter();
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        collect_recycle.setLayoutManager(manager);
        collect_recycle.setAdapter(projectChooseAcapter);
        List<CollectBean> tags = SQLiteUtils.getInstance().selectAllContacts("project");
        for(CollectBean collectBean:tags){

            indexChooseBean = new IndexChooseBean();

            indexChooseBean.setId(collectBean.getAddressid());

            boolean isSelect=false;


            for(IndexChooseBean mindexChooseBean:choose_title){

                if(collectBean.getAddressid().equals(mindexChooseBean.getId())){
                    isSelect = true;
                }

            }


            indexChooseBean.setCheck(isSelect);

            indexChooseBean.setValue("+ "+collectBean.getName());

            indexChooseBean.setEnable(isSelect);

            indexChooseBean.setType("add");

            chooseItemBeanList.add(indexChooseBean);
        }
        projectChooseAcapter.setNewData(chooseItemBeanList);
        if(tags.size()==0){
            add_title.setVisibility(View.GONE);
        }




    }

    @Override
    protected void initOnclick() {

        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                twinklingRefreshLayout.setVisibility(View.VISIBLE);
                searchData();
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
                    adapter.getData().clear();
                    adapter.notifyDataSetChanged();
                    my_collect.setVisibility(View.VISIBLE);
                    re_noproject.setVisibility(View.GONE);
                    // showKeyboardshowKeyboard(false);
                }else{
                    isFuzzy=true;
                    keywords = edit_content.getText().toString().trim();
                    twinklingRefreshLayout.startRefresh();
                }
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isFuzzy=false;

                keywords = edit_content.getText().toString().trim();

                twinklingRefreshLayout.startRefresh();

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("choosetitle",(Serializable) choose_title);
                setResult(ConstObserver.REQUESCODE, intent);

                finish();
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


        projectChooseAcapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {


                if(projectChooseAcapter.getData().get(position).isCheck()){

                    projectChooseAcapter.getData().get(position).setCheck(false);

                    projectChooseAcapter.getData().get(position).setEnable(false);

                    projectChooseAcapter.notifyItemChanged(position);

                    for(IndexChooseBean mindexChooseBean:choose_title){
                        if(projectChooseAcapter.getData().get(position).getValue().equals(mindexChooseBean.getValue())){

                            choose_title.remove(mindexChooseBean);

                            break;
                        }
                    }


                    ToastUtils.show(getString(R.string.cannel_pk));

                }else{
                    if(choose_title.size()==6){

                        ToastUtils.show(getString(R.string.max_pk));

                        return;

                    }else{
                        projectChooseAcapter.getData().get(position).setCheck(true);

                        projectChooseAcapter.getData().get(position).setEnable(true);

                        choose_title.add( projectChooseAcapter.getData().get(position));

                        projectChooseAcapter.notifyItemChanged(position);

                        ToastUtils.show(getString(R.string.add_pk));
                    }


                }




            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter1, View view, int position) {

                showKeyboardshowKeyboard(false);

                if(choose_title.size()==6){

                    ToastUtils.show(getString(R.string.max_pk));

                    return;

                }else {

                    boolean isSelect=false;

                    for(IndexChooseBean mindexChooseBean:choose_title){

                        if(adapter.getData().get(position).getId().equals(mindexChooseBean.getId())){
                            isSelect = true;
                        }

                    }

                    if(isSelect){
                        ToastUtils.show(getString(R.string.add_pk));
                    }else{
                        IndexChooseBean indexChooseBean = new IndexChooseBean();

                        indexChooseBean.setId(adapter.getData().get(position).getId());

                        indexChooseBean.setValue(adapter.getData().get(position).getCoinname());

                        choose_title.add(indexChooseBean);

                        ToastUtils.show(getString(R.string.add_pk));
                        Intent intent = new Intent();
                        intent.putExtra("choosetitle", (Serializable) choose_title);
                        setResult(ConstObserver.REQUESCODE, intent);
                        finish();

                    }



                }

            }
        });



    }



    //开始搜索内容
    private void searchData() {
        //隐藏软键盘
        if(!isFuzzy){
            showKeyboardshowKeyboard(false);
        }


        if(!TextUtils.isEmpty(keywords)){
            //检验搜索的字段
            mPresenter.getSearch(keywords,"project");
        }else{
            ToastUtils.showCenter(getString(R.string.search_notnull));
            twinklingRefreshLayout.finishRefreshing();
        }

    }

    protected void showKeyboardshowKeyboard(boolean isShow) {
        if(!isShow){
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(AddProjectActivity.this.getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }else{

            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(AddProjectActivity.this.getCurrentFocus()
                            .getWindowToken(), InputMethodManager.SHOW_FORCED);
        }

    }

    SearchBean msearchBean;

    List<SearchBean> data;

    @Override
    public void onSearchView(SearchResp items) {

        twinklingRefreshLayout.finishRefreshing();
        if (TextUtils.isEmpty(items.getErrInfo())) {
            if (items.getResult() == null || items.getResult().getData().size() <= 0){

                re_noproject.setVisibility(View.VISIBLE);
                my_collect.setVisibility(View.VISIBLE);

            }else {
                my_collect.setVisibility(View.GONE);
                re_noproject.setVisibility(View.GONE);
                data = new ArrayList<>();
                for(SearchResultBean searchBean:items.getResult().getData()){
                    msearchBean = new SearchBean();

                    msearchBean.setType("add");

                    msearchBean.setCoinname(searchBean.getSymbol());

                    msearchBean.setId(searchBean.getId());

                    msearchBean.setLogo(searchBean.getLogo());

                    data.add(msearchBean);

                }
                adapter.setNewData(data);


            }



        } else {

            ToastUtils.show("" + items.getErrInfo());
        }

    }

    @Override
    public void onFuzzySearch(SearchResp searchBean) {

    }


    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("choosetitle",(Serializable) choose_title);
        setResult(ConstObserver.REQUESCODE, intent);

        finish();
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

    }

    @Override
    public void onEOSTradeDetail(IsAddressResp resp) {

    }

    @Override
    public void onError() {

    }
}
