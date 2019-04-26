package com.gikee.app.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gikee.app.Observer.CollectObserverService;
import com.gikee.app.Observer.base_observe.ConstObserver;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.beans.CollectTrandBean;
import com.gikee.app.beans.HeadInfoBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.fragment.AllProjectDetailFragment;
import com.gikee.app.fragment.BaseFragment;
import com.gikee.app.fragment.IndexCompFragment;
import com.gikee.app.fragment.ProjectCompFragment;
import com.gikee.app.fragment.ProjectProfileFragment;
import com.gikee.app.fragment.SelfProjectDetailFragment;
import com.gikee.app.fragment.TopPlayerFragment;
import com.gikee.app.fragment.TransPairFragment;
import com.gikee.app.greendao.CollectBean;
import com.gikee.app.greendao.SQLiteUtils;
import com.gikee.app.greendao.TrandBean;
import com.gikee.app.language.LanguageType;
import com.gikee.app.language.MultiLanguageUtil;
import com.gikee.app.preference_config.PreferenceUtil;
import com.gikee.app.presenter.project.ShuJuFenXiPresentetr;
import com.gikee.app.presenter.project.ShuJuFenXiView;
import com.gikee.app.resp.ProjectCompaResp;
import com.gikee.app.resp.ProjectInfoResp;
import com.gikee.app.resp.SummaryResp;
import com.gikee.app.views.AutoHeightViewPager;
import com.gikee.app.views.BaseFragmentPagerAdapter;
import com.gikee.app.views.CustomTablayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;


public class  ProjectDetailActivity extends BaseActivity<ShuJuFenXiPresentetr> implements ShuJuFenXiView {


    @Bind(R.id.tabslayout)
    TabLayout tabslayout;
    @Bind(R.id.viewpager)
    AutoHeightViewPager viewpager;
    @Bind(R.id.project_img)
    ImageView project_img;
    @Bind(R.id.currect_price)
    TextView currect_price;//当前价格
    @Bind(R.id.marketvalue)
    TextView marketvalue;//流通市值
    @Bind(R.id.changehands)
    TextView changehands;//换手率
    @Bind(R.id.markerranke)
    TextView markerranke;//市值排名
    @Bind(R.id.project_info)
    RelativeLayout project_info;


    @Bind(R.id.currect_priceusd)
    TextView currect_priceusd;//当前美元价格

    @Bind(R.id.priceQuotechange)
    TextView priceQuotechange;//价格涨跌幅

    @Bind(R.id.hour_hight_value)
    TextView hour_hight_value;//24小时最高价

    @Bind(R.id.hour_low_value)
    TextView hour_low_value;//24小时最低价





    private List<BaseFragment> fragments = new ArrayList<>();
    private static int currectPosition=0;
    private String coinSymbol = "0xBTC";
    private String id;
    private String logo;
    private boolean isCollect=false;//是否收藏
    private List<TrandBean> trandlist = new ArrayList<>();//查看自选里是否有数据
    private  boolean isSelfChoose=false;
    private List<String> items=new ArrayList<>();//自选时的标签合集
    private  CollectBean collectBean;
    private HeadInfoBean headInfo;
    private int index=-1;
    private String IsFristRun="FristProjectDetailActivity";
    private String type;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ShuJuFenXiPresentetr(this);
        setContentView(R.layout.activity_project_detail);
    }

    @Override
    protected void onChangeEvent(int type) {

    }


    @Override
    protected void setUpView() {

        setTitleColor(R.color.black);
        coinSymbol = getIntent().getStringExtra("symbol");
        setTitleColor(R.color.black);
        setTitle(coinSymbol);
        id = getIntent().getStringExtra("id");
        Commons.smybl = coinSymbol;
        Commons.id = id;
        logo =getIntent().getStringExtra("logo");
        index = getIntent().getIntExtra("index",-1);

        type = getIntent().getStringExtra("type");

        if(!TextUtils.isEmpty(logo)){
            Glide.with(this).load(logo).into((CircleImageView) project_img);
        }




        final String[] mTitle =new String[6];

        mTitle[0]=getString(R.string.alltrends);

        mTitle[1]=getString(R.string.index_comparison);

        mTitle[2]=getString(R.string.pair);

        mTitle[3]=getString(R.string.project_pk);

        mTitle[4]=getString(R.string.topPlayer);



        mTitle[5]=getString(R.string.info);



        fragments.add(AllProjectDetailFragment.getInstance(coinSymbol,id));

        fragments.add(IndexCompFragment.getInstance(coinSymbol,id));

        fragments.add(TransPairFragment.getInstance(coinSymbol));

        fragments.add(ProjectCompFragment.getInstance(coinSymbol,id));

        fragments.add(TopPlayerFragment.getInstance(coinSymbol,id));

        fragments.add(ProjectProfileFragment.getInstance(coinSymbol,id));




        BaseFragmentPagerAdapter baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            protected Fragment getItemFragment(int position) {
                return fragments.get(position);
            }

            @Override
            protected String[] getMTitles() {
                return  mTitle;
            }

            @Override
            protected List<String> getAutoMTitles() {
                return null;
            }
        };
        viewpager.setScanScroll(false);

        viewpager.setAdapter(baseFragmentPagerAdapter);

        viewpager.setOffscreenPageLimit(5);

        if(index!=-1){
            viewpager.setCurrentItem(5);
        }else
            viewpager.setCurrentItem(0);

        if(!TextUtils.isEmpty(type)){
            viewpager.setCurrentItem(2);
        }else
            viewpager.setCurrentItem(0);

        tabslayout.setupWithViewPager(viewpager);

        getData();

//        if (!PreferenceUtil.getBoolean(IsFristRun,false)) {
//            // 如果是第一次进入页面则显示tips
//            tip.setVisibility(View.VISIBLE);
//            all_layout.setBackground(getResources().getDrawable(R.color.alpha_bg));
//        }

    }

    private void getData() {

        //判断当前显示哪个tab
      //  mPresenter.getAllIndex(id);

        String language = MyUtils.getLocalLanguage();
        mPresenter.getProjectInfo(id,language);

    }

    @Override
    protected void initOnclick() {

        //检查本地收藏列表，查看该项目是否收藏
        final List<CollectBean> tags = SQLiteUtils.getInstance().selectAllContacts("project");

        for(int i=0;i<tags.size();i++){

            if(tags.get(i).getName().equals(coinSymbol)){

                setRightCollect(R.mipmap.collection_gray);

                isCollect=true;

                break;
            }

        }





        //头部公共标签事件控制
        showRightImg(new IOnclik() {
            @Override
            public void OnClickSave() {

            }

            @Override
            public void OnImgClick(View view) {

              //  mMenu.showAsDropDown(view);

            }

            @Override
            public void OnImgCollect() {


                if(isCollect){
                    // 取消收藏
                    List<CollectBean> collectBeanList=SQLiteUtils.getInstance().getEntityID(coinSymbol);
                    if(collectBeanList.size()!=0){
                        collectBean = new CollectBean();
                        collectBean.setName(coinSymbol);
                        collectBean.setLogo(logo);
                        collectBean.setAddressid(id);
                        collectBean.setType("project");
                        collectBean.setIscollect(false);
                        collectBean.setId(collectBeanList.get(0).getId());
                        collectBean.setTag(coinSymbol);
                        SQLiteUtils.getInstance().deleteContacts(collectBean);
                       // setRightCollect(R.color.home_navite);
                        setRightCollect(R.mipmap.collection);
                        CollectObserverService.getInstance().notifyDataChanged(ConstObserver.COLLECT_PROJECT_CHANGE);
                        ToastUtils.show(getString(R.string.collect_cannel));
                        isCollect=false;
                        collectBean=null;
                    }

                }else{
                    //添加收藏
                    collectBean = new CollectBean();
                    collectBean.setName(coinSymbol);
                    collectBean.setLogo(logo);
                    collectBean.setType("project");
                    collectBean.setTrandnum(tags.size());
                    collectBean.setAddressid(id);
                    collectBean.setTag(coinSymbol);
                    collectBean.setIscollect(true);
                    SQLiteUtils.getInstance().addContacts(collectBean);
                    CollectObserverService.getInstance().notifyDataChanged(ConstObserver.COLLECT_PROJECT_CHANGE);
                   // setRightCollect(R.color.FFAF2C);
                    setRightCollect(R.mipmap.collection_gray);
                    ToastUtils.show(getString(R.string.collect_success));
                    CollectTrandBean collectTrandBean = new CollectTrandBean();
                    collectTrandBean.setCollect(true);
                    collectTrandBean.setTrandname(coinSymbol);
                    isCollect=true;
                    collectBean=null;
                }


            }
        });


        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewpager.requestLayout();
                currectPosition = position;
                //tabslayout_top.setClicked(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        tabslayout.setTabSelectedListener(new CustomTablayout.TabSelectedListener() {
//            @Override
//            public void tabClicked(int position) {
//                currectPosition = position;
//            }
//        });

//        tabslayout_top.setTabSelectedListener(new CustomTablayout.TabSelectedListener() {
//            @Override
//            public void tabClicked(int position) {
//                currectPosition = position;
//                tabslayout.setViewPager(viewpager,position);
//
//            }
//        });



    }



    public static int  getPosition(){

        return currectPosition;

    }


    @Override
    public void onShuJuFenXi(SummaryResp shuJuFenXiBean) {

//        if(TextUtils.isEmpty(shuJuFenXiBean.getErrInfo())){
//
//            if(shuJuFenXiBean.getResult()!=null){
//
//                if(shuJuFenXiBean.getResult().getData().size()==0){
//
//                    currectPosition=2;
//
//                    tabslayout.setViewPager(viewpager,currectPosition);
//                }
//
//            }else{
//                currectPosition=2;
//
//                tabslayout.setViewPager(viewpager,currectPosition);
//            }
//
//        }else{
//            currectPosition=2;
//
//            tabslayout.setViewPager(viewpager,currectPosition);
//        }

    }

    @Override
    public void onIndexContrast(SummaryResp shuJuFenXiBean) {

    }

    @Override
    public void onProjectInfo(ProjectInfoResp projectInfoResp) {

        if(projectInfoResp.getResult()!=null){

            if(projectInfoResp.getResult().getData()!=null){
                headInfo = projectInfoResp.getResult().getData().getHeadInfo();



                initView(headInfo,projectInfoResp.getResult().getData().getBaseInfo().getLogo());

            }



        }




    }

    @Override
    public void onProjectCompar(ProjectCompaResp projectCompaResp) {

    }

    private void initView(HeadInfoBean headInfo,String logo) {

        if(headInfo!=null){


            String main_price = "";

            String price = "";


            if(MyUtils.getLocalLanguage().equals("en")){

                price ="≈"+headInfo.getPriceBtc()+"BTC";

                main_price ="$"+MyUtils.fmtMicrometer1(headInfo.getPrice());

            }else{
                boolean ismain=false;

                if( MyUtils.getUnit()){
                    main_price ="$"+MyUtils.fmtMicrometer1(headInfo.getPrice());
                    ismain=true;
                }else{
                    ismain=false;
                    main_price ="¥"+MyUtils.fmtMicrometer1(headInfo.getPrcieCn());
                }

                if(ismain){
                    price="¥"+MyUtils.fmtMicrometer1(headInfo.getPrcieCn());
                }else
                    price="$"+MyUtils.fmtMicrometer1(headInfo.getPrice());


            }


            if(!TextUtils.isEmpty(main_price)){
                currect_price.setText(main_price);
            }

            if(!TextUtils.isEmpty(price)){
                currect_priceusd.setText(price);
            }

//            if(MyUtils.getUnit()){
//
//                if(!TextUtils.isEmpty(headInfo.getPrice())){
//                    if(Float.parseFloat(headInfo.getPrice())==0){
//                        currect_price.setText("$-");
//                    }else{
//                        currect_price.setText("$"+MyUtils.fmtMicrometer1(headInfo.getPrice()));
//                    }
//
//                }
//
//                if(!TextUtils.isEmpty(headInfo.getPrcieCn())){
//                    if(Float.parseFloat(headInfo.getPrcieCn())==0){
//                        currect_priceusd.setText("¥-");
//                    }else{
//                        currect_priceusd.setText("¥"+MyUtils.fmtMicrometer1(headInfo.getPrcieCn()));
//                    }
//
//                }
//
//            }else{
//
//                if(!TextUtils.isEmpty(headInfo.getPrice())){
//                    if(Float.parseFloat(headInfo.getPrice())==0){
//                        currect_price.setText("¥-");
//                    }else{
//                        currect_price.setText("¥"+MyUtils.fmtMicrometer1(headInfo.getPrcieCn()));
//                    }
//
//                }
//
//                if(!TextUtils.isEmpty(headInfo.getPrcieCn())){
//                    if(Float.parseFloat(headInfo.getPrcieCn())==0){
//                        currect_priceusd.setText("$-");
//                    }else{
//                        currect_priceusd.setText("$"+MyUtils.fmtMicrometer1(headInfo.getPrice()));
//                    }
//
//                }
//
//            }




            if(!TextUtils.isEmpty(headInfo.getPriceQuotechange())){
                if(headInfo.getPriceQuotechange().contains("-")){
                    priceQuotechange.setText("↓"+MyUtils.fmtMicrometer(headInfo.getPriceQuotechange().replace("-",""))+"%");

                    if(MyUtils.getQuateSymbol()){
                        priceQuotechange.setTextColor(getResources().getColor(R.color.faa3c));
                    }else
                        priceQuotechange.setTextColor(getResources().getColor(R.color.red));



                }else{
                    if(MyUtils.getQuateSymbol()){
                        priceQuotechange.setTextColor(getResources().getColor(R.color.red));
                    }else
                        priceQuotechange.setTextColor(getResources().getColor(R.color.faa3c));

                    priceQuotechange.setText("↑"+MyUtils.fmtMicrometer(headInfo.getPriceQuotechange())+"%");
                }

            }

            if(!TextUtils.isEmpty(headInfo.getHeightestPrice())){
               String  HeightestPrice=MyUtils.getUnit()?headInfo.getHeightestPrice():headInfo.getHeightestPrice_cny();
                if(Float.parseFloat(headInfo.getPrice())==0){
                    hour_hight_value.setText(MyUtils.getUnitSymbol()+"-");
                }else
                    hour_hight_value.setText(MyUtils.getUnitSymbol()+""+MyUtils.fmtMicrometer1(HeightestPrice));
            }

            if(!TextUtils.isEmpty(headInfo.getLowestPrice())){
                String LowestPrice = MyUtils.getUnit()?headInfo.getLowestPrice():headInfo.getLowestPrice_cny();

                if(Float.parseFloat(headInfo.getPrice())==0){
                    hour_low_value.setText(MyUtils.getUnitSymbol()+"-");
                }else
                    hour_low_value.setText(MyUtils.getUnitSymbol()+""+MyUtils.fmtMicrometer1(LowestPrice));
            }


            if(!TextUtils.isEmpty(headInfo.getMarketValue())){
                if(Float.parseFloat(headInfo.getMarketValue())==0){
                    marketvalue.setText(MyUtils.getUnitSymbol()+"-");
                }else{
                    double value;
                    if(MyUtils.getUnit()){

                        value = Double.parseDouble(headInfo.getMarketValue());
                    }else
                        value = Double.parseDouble(headInfo.getMarketValueCn());

                    marketvalue.setText(MyUtils.getUnitSymbol()+MyUtils.getBigNumber(value));
                }

            }

            if(!TextUtils.isEmpty(headInfo.getTurnoverRatio())){
                if(Float.parseFloat(headInfo.getTurnoverRatio().replace("%",""))==0){
                    changehands.setText("?%");
                }else
                    changehands.setText(headInfo.getTurnoverRatio());
            }

            if(!TextUtils.isEmpty(headInfo.getMarketvalueRanking())){
                String value;

                if("None".equals(headInfo.getMarketvalueRanking())){
                    value = String.format(getString(R.string.rank_com),"?");

                }else{

                    value = String.format(getString(R.string.rank_com),headInfo.getMarketvalueRanking());
                }

                markerranke.setText(value);
            }
        }

        if(!TextUtils.isEmpty(logo)){
            Glide.with(this).load(logo).into((CircleImageView) project_img);
        }



    }

    @Override
    public void onError() {

    }
}
