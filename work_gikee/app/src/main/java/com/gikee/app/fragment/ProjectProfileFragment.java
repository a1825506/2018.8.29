package com.gikee.app.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;

import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.adapter.TokenBalanceAdapter;
import com.gikee.app.beans.BaseProjectInfo;
import com.gikee.app.beans.HeadInfoBean;
import com.gikee.app.beans.ProjectInfoBean;
import com.gikee.app.beans.PublicInfoBean;
import com.gikee.app.beans.RelatedLinksBean;
import com.gikee.app.beans.TokendBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.language.MultiLanguageUtil;
import com.gikee.app.presenter.project.ShuJuFenXiPresentetr;
import com.gikee.app.presenter.project.ShuJuFenXiView;
import com.gikee.app.resp.ProjectCompaResp;
import com.gikee.app.resp.ProjectInfoResp;
import com.gikee.app.resp.SummaryResp;
import com.gikee.app.views.MyRefreshHeader;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;

public class ProjectProfileFragment extends BaseFragment<ShuJuFenXiPresentetr> implements ShuJuFenXiView  {


    @Bind(R.id.fm_all_shuju_refreshLayout)
    TwinklingRefreshLayout twinklingRefreshLayout;
    @Bind(R.id.public_recycle)
    RecyclerView public_recycle;


    private OnUpdateHeadInfo onUpdateHeadInfo;

    private TokenBalanceAdapter baseInfoAdapter;

    private TokendBean tokendBean;//中间类，用来转换数据源需要的数据类型

    private List<TokendBean> tokendBeanList = new ArrayList<>();

    private String coinSymbol;

    private String mcontext;

    private boolean isShow=false;

//    private static volatile ProjectProfileFragment projectProfileFragment;


    private HeadInfoBean headInfo;
    private BaseProjectInfo baseInfo;
    private PublicInfoBean publicInfo;
    private RelatedLinksBean relatedLink;
    private String language="ch_zn";
    private String id;


   // private static volatile ProjectProfileFragment projectProfileFragment;

    public static ProjectProfileFragment getInstance(String coinSymbol,String id){

        ProjectProfileFragment projectProfileFragment = new ProjectProfileFragment();

        projectProfileFragment.setParams(coinSymbol,id);

        return projectProfileFragment;


    }

    public void setParams(String coinSymbol,String id){

        this.coinSymbol = coinSymbol;
        this.id  =id;

    }

    public void setData(ProjectInfoBean projectInfoBean){


        this.baseInfo =projectInfoBean.getBaseInfo();
        this.publicInfo = projectInfoBean.getPublicInfo();
        this.relatedLink = projectInfoBean.getRelatedLinks();

        if(tokendBeanList.size()!=0){
            tokendBeanList.clear();
        }

        if(baseInfo!=null){
            if(!TextUtils.isEmpty(baseInfo.getIntroduction())){

                if(!baseInfo.getIntroduction().equals("null")){

                tokendBean = new TokendBean();
                tokendBean.setTitle(baseInfo.getIntroduction());
                tokendBean.setType(TokenBalanceAdapter.item_type);
                tokendBean.setValue("");
                tokendBeanList.add(tokendBean);
                tokendBean = null;

                }
                
             }


            if(!TextUtils.isEmpty(baseInfo.getCnName())){

                if(!baseInfo.getCnName().equals("null")) {
                    tokendBean = new TokendBean();
                    tokendBean.setTitle(getContext().getResources().getString(R.string.ch_title));
                    tokendBean.setType(TokenBalanceAdapter.item_type);
                    tokendBean.setValue(baseInfo.getCnName());
                    tokendBeanList.add(tokendBean);
                    tokendBean = null;
                }
            }
            if(!TextUtils.isEmpty(baseInfo.getEnName())){
                if(!baseInfo.getEnName().equals("null")) {
                    tokendBean = new TokendBean();
                    tokendBean.setTitle(getContext().getResources().getString(R.string.en_title));
                    tokendBean.setType(TokenBalanceAdapter.item_type);
                    tokendBean.setValue(baseInfo.getEnName());
                    tokendBeanList.add(tokendBean);
                    tokendBean = null;
                }
            }
            if(!TextUtils.isEmpty(baseInfo.getPublishTime())){
                if(!baseInfo.getPublishTime().equals("null")) {
                    tokendBean = new TokendBean();
                    tokendBean.setTitle(getContext().getResources().getString(R.string.publish_time));
                    tokendBean.setType(TokenBalanceAdapter.item_type);
                    tokendBean.setValue(baseInfo.getPublishTime());
                    tokendBeanList.add(tokendBean);
                    tokendBean = null;
                }
            }
            if(!TextUtils.isEmpty(baseInfo.getTotalSupply())){
                if(!baseInfo.getTotalSupply().equals("null")) {
                    tokendBean = new TokendBean();
                    tokendBean.setTitle(getContext().getResources().getString(R.string.circulation));
                    tokendBean.setType(TokenBalanceAdapter.item_type);
                    tokendBean.setValue(MyUtils.fmtMicrometer(baseInfo.getTotalSupply()));
                    tokendBeanList.add(tokendBean);
                    tokendBean = null;
                }
            }

            if(!TextUtils.isEmpty(baseInfo.getMarketvalueRatio())){
                if(!baseInfo.getMarketvalueRatio().equals("null")) {
                    tokendBean = new TokendBean();
                    tokendBean.setTitle(getContext().getResources().getString(R.string.marketvalue_ratio));
                    tokendBean.setType(TokenBalanceAdapter.item_type);
                    tokendBean.setValue(baseInfo.getMarketvalueRatio());
                    tokendBeanList.add(tokendBean);
                    tokendBean = null;
                }
            }

            if(!TextUtils.isEmpty(baseInfo.getCirculation())){
                if(!baseInfo.getCirculation().equals("null")) {
                    tokendBean = new TokendBean();
                    tokendBean.setTitle(getResources().getString(R.string.circulation_));
                    tokendBean.setType(TokenBalanceAdapter.item_type);
                    tokendBean.setValue(MyUtils.fmtMicrometer(baseInfo.getCirculation()));
                    tokendBeanList.add(tokendBean);
                    tokendBean = null;
                }
            }

            if(!TextUtils.isEmpty(baseInfo.getCirculationRatio())){
                if(!baseInfo.getCirculationRatio().equals("null")) {
                    tokendBean = new TokendBean();
                    tokendBean.setTitle(getResources().getString(R.string.circulationrate));
                    tokendBean.setType(TokenBalanceAdapter.item_type);
                    tokendBean.setValue((Float.parseFloat(baseInfo.getCirculationRatio())*100)+"%");
                    tokendBeanList.add(tokendBean);
                    tokendBean = null;
                }
            }
            if(!TextUtils.isEmpty(baseInfo.getTurnover())){
                if(!baseInfo.getTurnover().equals("null")) {
                    tokendBean = new TokendBean();
                    tokendBean.setTitle(getResources().getString(R.string.turnover));
                    tokendBean.setType(TokenBalanceAdapter.item_type);
                    String value = MyUtils.getUnit()?baseInfo.getTurnover():String.valueOf(Float.parseFloat(baseInfo.getTurnover())* Commons.rate);
                    tokendBean.setValue(MyUtils.getUnitSymbol()+""+MyUtils.fmtMicrometer(value));
                    tokendBeanList.add(tokendBean);
                    tokendBean = null;
                }
            }


        }

        if(publicInfo!=null){


            if(!TextUtils.isEmpty(publicInfo.getStatus())){
                if(!publicInfo.getStatus().equals("null")) {
                    tokendBean = new TokendBean();
                    tokendBean.setTitle(getContext().getResources().getString(R.string.public_info));
                    tokendBean.setType(TokenBalanceAdapter.title_type);
                    tokendBeanList.add(tokendBean);
                    tokendBean = null;

                    tokendBean = new TokendBean();
                    tokendBean.setTitle(getContext().getResources().getString(R.string.status));
                    tokendBean.setType(TokenBalanceAdapter.item_type);
                    tokendBean.setValue(publicInfo.getStatus());
                    tokendBeanList.add(tokendBean);
                    tokendBean = null;
                }
            }

            if(!TextUtils.isEmpty(publicInfo.getAvg_price())){
                if(!publicInfo.getAvg_price().equals("null")) {
                    tokendBean = new TokendBean();
                    tokendBean.setTitle(getContext().getResources().getString(R.string.avg_price));
                    tokendBean.setType(TokenBalanceAdapter.item_type);
                    tokendBean.setValue(publicInfo.getAvg_price());
                    tokendBeanList.add(tokendBean);
                    tokendBean = null;
                }
            }

            if(!TextUtils.isEmpty(publicInfo.getStart_time())){
                if(!publicInfo.getStart_time().equals("null")) {
                    tokendBean = new TokendBean();
                    tokendBean.setTitle(getContext().getResources().getString(R.string.start_time));
                    tokendBean.setType(TokenBalanceAdapter.item_type);
                    tokendBean.setValue(publicInfo.getStart_time());
                    tokendBeanList.add(tokendBean);
                    tokendBean = null;
                }
            }

            if(!TextUtils.isEmpty(publicInfo.getEnd_time())){
                if(!publicInfo.getEnd_time().equals("null")) {
                    tokendBean = new TokendBean();
                    tokendBean.setTitle(getContext().getResources().getString(R.string.end_time));
                    tokendBean.setType(TokenBalanceAdapter.item_type);
                    tokendBean.setValue(publicInfo.getEnd_time());
                    tokendBeanList.add(tokendBean);
                    tokendBean = null;
                }
            }

            if(!TextUtils.isEmpty(publicInfo.getStart_price())){
                if(!publicInfo.getStart_price().equals("null")) {
                    tokendBean = new TokendBean();
                    tokendBean.setTitle(getContext().getResources().getString(R.string.start_price));
                    tokendBean.setType(TokenBalanceAdapter.item_type);
                    tokendBean.setValue(publicInfo.getStart_price());
                    tokendBeanList.add(tokendBean);
                    tokendBean = null;
                }
            }
            if(!TextUtils.isEmpty(publicInfo.getDistribution())){
                if(!publicInfo.getDistribution().equals("null")) {
                    tokendBean = new TokendBean();
                    tokendBean.setTitle(getContext().getResources().getString(R.string.ico_allocation));
                    tokendBean.setType(TokenBalanceAdapter.item_type);
                    tokendBean.setValue(publicInfo.getDistribution());
                    tokendBeanList.add(tokendBean);
                    tokendBean = null;
                }
            }


            if(!TextUtils.isEmpty(publicInfo.getInvestor_per())){
                if(!publicInfo.getInvestor_per().equals("null")) {
                    tokendBean = new TokendBean();
                    tokendBean.setTitle(getContext().getResources().getString(R.string.investor_share));
                    tokendBean.setType(TokenBalanceAdapter.item_type);
                    tokendBean.setValue(publicInfo.getInvestor_per()+"%");
                    tokendBeanList.add(tokendBean);
                    tokendBean = null;
                }
            }

            if(!TextUtils.isEmpty(publicInfo.getPlotform())){
                if(!publicInfo.getPlotform().equals("null")) {
                    tokendBean = new TokendBean();
                    tokendBean.setTitle(getContext().getResources().getString(R.string.token_platform));
                    tokendBean.setType(TokenBalanceAdapter.item_type);
                    tokendBean.setValue(publicInfo.getPlotform());
                    tokendBeanList.add(tokendBean);
                    tokendBean = null;
                }
            }
            if(!TextUtils.isEmpty(publicInfo.getCount())){
                if(!publicInfo.getCount().equals("null")) {
                    tokendBean = new TokendBean();
                    tokendBean.setTitle(getContext().getResources().getString(R.string.count));
                    tokendBean.setType(TokenBalanceAdapter.item_type);
                    tokendBean.setValue(publicInfo.getCount());
                    tokendBeanList.add(tokendBean);
                    tokendBean = null;
                }
            }

            if(!TextUtils.isEmpty(publicInfo.getSuccess_money())){
                if(!publicInfo.getSuccess_money().equals("null")) {
                    tokendBean = new TokendBean();
                    tokendBean.setTitle(getContext().getResources().getString(R.string.success_money));
                    tokendBean.setType(TokenBalanceAdapter.item_type);
                    tokendBean.setValue(publicInfo.getSuccess_money());
                    tokendBeanList.add(tokendBean);
                    tokendBean = null;
                }
            }

        }
        if(relatedLink!=null){
            tokendBean = new TokendBean();
            tokendBean.setTitle(getContext().getResources().getString(R.string.related_info));
            tokendBean.setType(TokenBalanceAdapter.title_type);
            tokendBeanList.add(tokendBean);
            tokendBean = null;

//            if(relatedLink.getBrowser().size()!=0){
//                tokendBean = new TokendBean();
//                tokendBean.setTitle(getContext().getResources().getString(R.string.browser));
//                tokendBean.setType(TokenBalanceAdapter.mulitem_type);
//                tokendBean.setValue_list(relatedLink.getBrowser());
//                tokendBeanList.add(tokendBean);
//                tokendBean = null;
//            }

            if(relatedLink.getWebsite()!=null&&relatedLink.getWebsite().size()!=0){
                if(!relatedLink.getWebsite().equals("null")) {
                    tokendBean = new TokendBean();
                    tokendBean.setTitle(getContext().getResources().getString(R.string.website));
                    tokendBean.setType(TokenBalanceAdapter.mulitem_type);
                    tokendBean.setValue_list(relatedLink.getWebsite());
                    tokendBeanList.add(tokendBean);
                    tokendBean = null;
                }
            }

            if(!TextUtils.isEmpty(relatedLink.getWhitePaper())){
                if(!relatedLink.getWhitePaper().equals("null")) {
                    tokendBean = new TokendBean();
                    tokendBean.setTitle(getContext().getResources().getString(R.string.whitePaper));
                    tokendBean.setType(TokenBalanceAdapter.mulitem_type);
                    List<String> value_list = new ArrayList<>();
                    value_list.add(relatedLink.getWhitePaper());
                    // tokendBean.setValue(relatedLink.getWhitePaper());
                    tokendBean.setValue_list(value_list);
                    tokendBeanList.add(tokendBean);
                    tokendBean = null;
                }
            }

        }

        baseInfoAdapter.setNewData(tokendBeanList);

   

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    protected void setupViews(LayoutInflater inflater) {

        setContentView(inflater, R.layout.fragment_projectprofile);



    }

    @Override
    protected void initView() {

        mPresenter = new ShuJuFenXiPresentetr(this);

        MyRefreshHeader headerView = new MyRefreshHeader(getContext());
        
        twinklingRefreshLayout.setEnableLoadmore(false);
        
        twinklingRefreshLayout.setEnableOverScroll(false);
        
        twinklingRefreshLayout.setHeaderView(headerView);

        baseInfoAdapter = new TokenBalanceAdapter();

        public_recycle.setLayoutManager(new LinearLayoutManager(getContext()));

        public_recycle.setNestedScrollingEnabled(false);

        public_recycle.setAdapter(baseInfoAdapter);



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


        Locale savedLanguageType = MultiLanguageUtil.getInstance().getLanguageLocale();
        if (savedLanguageType.equals(Locale.ENGLISH)) {
            language = "en";
        } else if(savedLanguageType.equals(Locale.SIMPLIFIED_CHINESE)) {
            language = "zh_CN";
        } else {
            language = "en";
        }

        mPresenter.getProjectInfo(id,language);

    }


    @Override
    protected void lazyLoad() {
        if(isViewCreated&&isUIVisible){

           // if(!isShow){
                twinklingRefreshLayout.startRefresh();
            //}
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
            isShow=false;

        }

    }

    @Override
    protected void onChangeEvent(int type) {

    }


    @Override
    public void onShuJuFenXi(SummaryResp shuJuFenXiBean) {

    }

    @Override
    public void onIndexContrast(SummaryResp shuJuFenXiBean) {

    }

    @Override
    public void onProjectInfo(ProjectInfoResp projectInfoResp) {

        twinklingRefreshLayout.finishRefreshing();

        if(projectInfoResp.getResult()!=null){

            if(projectInfoResp.getResult().getData()!=null){

                setData(projectInfoResp.getResult().getData());

                isShow=true;

               // onUpdateHeadInfo.updateHeanInfo(projectInfoResp.getResult().getData());
            }


        }else{
            isShow=false;
            twinklingRefreshLayout.startRefresh();
        }



    }

    @Override
    public void onProjectCompar(ProjectCompaResp projectCompaResp) {

    }

    @Override
    public void onError() {

    }


    public void setOnUpdateHeadInfo(OnUpdateHeadInfo mListener) {
        this.onUpdateHeadInfo = mListener;
    }


    public interface OnUpdateHeadInfo {
        void updateHeanInfo(ProjectInfoBean data);
    }


}
