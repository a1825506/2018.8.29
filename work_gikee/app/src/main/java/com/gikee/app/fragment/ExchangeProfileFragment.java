package com.gikee.app.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;

import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.adapter.TokenBalanceAdapter;
import com.gikee.app.beans.ExchangeIntroBean;
import com.gikee.app.beans.TokendBean;
import com.gikee.app.presenter.project.ExchangePresenter;
import com.gikee.app.presenter.project.ExchangeView;
import com.gikee.app.presenter.project.ShuJuFenXiPresentetr;
import com.gikee.app.resp.ExchangeResp;
import com.gikee.app.views.MyRefreshHeader;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class ExchangeProfileFragment extends BaseFragment<ExchangePresenter> implements ExchangeView {

    @Bind(R.id.fm_all_shuju_refreshLayout)
    TwinklingRefreshLayout twinklingRefreshLayout;
    @Bind(R.id.public_recycle)
    RecyclerView public_recycle;

    private String id;

    private TokenBalanceAdapter baseInfoAdapter;

    private TokendBean tokendBean;//中间类，用来转换数据源需要的数据类型

    private List<TokendBean> tokendBeanList = new ArrayList<>();

    public static ExchangeProfileFragment getInstance(String id){

        ExchangeProfileFragment projectProfileFragment = new ExchangeProfileFragment();

        projectProfileFragment.setParams(id);

        return projectProfileFragment;

    }

    public void setParams(String id){

        this.id  =id;

    }

    @Override
    protected void setupViews(LayoutInflater inflater) {

        setContentView(inflater, R.layout.fragment_projectprofile);
    }

    @Override
    protected void initView() {
        mPresenter = new ExchangePresenter(this);

        MyRefreshHeader headerView = new MyRefreshHeader(getContext());

        twinklingRefreshLayout.setEnableLoadmore(false);

        twinklingRefreshLayout.setAutoLoadMore(false);

        twinklingRefreshLayout.setHeaderView(headerView);

        baseInfoAdapter = new TokenBalanceAdapter();

        public_recycle.setLayoutManager(new LinearLayoutManager(getContext()));

        public_recycle.setNestedScrollingEnabled(false);

        public_recycle.setAdapter(baseInfoAdapter);

        twinklingRefreshLayout.startRefresh();


        initOnclick();
    }

    private void initOnclick() {

        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {

                getData();
            }
        });
    }

    private void getData() {

        mPresenter.getExchange(id,"intro","1");
    }


    @Override
    protected void lazyLoad() {

//        if(isViewCreated&&isUIVisible){
//
//            // if(!isShow){
//            twinklingRefreshLayout.startRefresh();
//            //}
//            //数据加载完毕,恢复标记,防止重复加载
//            isViewCreated = false;
//            isUIVisible = false;
//
//        }

    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @Override
    public void onExchange(ExchangeResp resp) {

        twinklingRefreshLayout.finishRefreshing();

        if(TextUtils.isEmpty(resp.getErrInfo())){

            setData(resp.getResult().getIntro_data());

        }

    }

    private void setData(ExchangeIntroBean intro_data) {

        String language = MyUtils.getLocalLanguage();

        tokendBeanList.clear();

        if(intro_data!=null){

            if(!TextUtils.isEmpty(intro_data.getIntro_cn())){

                tokendBean = new TokendBean();
                if(language.equals("en")){
                    if(TextUtils.isEmpty(intro_data.getIntro())){
                        tokendBean.setTitle(intro_data.getIntro_cn().replace("<p>","").replace("</p>",""));
                    }else
                        tokendBean.setTitle(intro_data.getIntro().replace("<p>","").replace("</p>",""));
                }else
                    tokendBean.setTitle(intro_data.getIntro_cn().replace("<p>","").replace("</p>",""));
                tokendBean.setType(TokenBalanceAdapter.item_type);
                tokendBean.setValue("");
                tokendBeanList.add(tokendBean);
                tokendBean = null;

            }

            if(!TextUtils.isEmpty(intro_data.getCountry_cn())){

                tokendBean = new TokendBean();
                if(language.equals("en")){
                    tokendBean.setValue(intro_data.getCountry());
                }else
                    tokendBean.setValue(intro_data.getCountry_cn());
                tokendBean.setType(TokenBalanceAdapter.item_type);
                tokendBean.setTitle(getResources().getString(R.string.country));
                tokendBeanList.add(tokendBean);
                tokendBean = null;

            }

            if(!TextUtils.isEmpty(intro_data.getWebsite())){

                    tokendBean = new TokendBean();
                    tokendBean.setTitle(getContext().getResources().getString(R.string.website));
                    tokendBean.setType(TokenBalanceAdapter.mulitem_type);
                    List<String> value_list = new ArrayList<>();
                    value_list.add(intro_data.getWebsite());
                    // tokendBean.setValue(relatedLink.getWhitePaper());
                    tokendBean.setValue_list(value_list);
                    tokendBeanList.add(tokendBean);
                    tokendBean = null;

            }



            baseInfoAdapter.setNewData(tokendBeanList);

        }


    }

    @Override
    public void onError() {

    }
}
