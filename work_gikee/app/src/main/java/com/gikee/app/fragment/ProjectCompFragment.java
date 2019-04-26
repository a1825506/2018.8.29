package com.gikee.app.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.activity.AddProjectActivity;
import com.gikee.app.activity.ProjectDetailActivity;
import com.gikee.app.activity.SearchActivity;
import com.gikee.app.activity.ZhanghuNumActivity;
import com.gikee.app.adapter.CompareAdapter;
import com.gikee.app.adapter.IndexAdapter;
import com.gikee.app.adapter.MarkViewAdapter;
import com.gikee.app.adapter.PopAllprojectAdapter;
import com.gikee.app.adapter.ProjectChooseAcapter;
import com.gikee.app.beans.ChooseItemBean;
import com.gikee.app.beans.IndexChooseBean;
import com.gikee.app.beans.MarkerViewBean;
import com.gikee.app.beans.ProjectCompaBean;
import com.gikee.app.beans.ZhanghuPopBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.language.MultiLanguageUtil;
import com.gikee.app.presenter.project.ShuJuFenXiPresentetr;
import com.gikee.app.presenter.project.ShuJuFenXiView;
import com.gikee.app.resp.DotBean;
import com.gikee.app.resp.ProjectCompaResp;
import com.gikee.app.resp.ProjectInfoResp;
import com.gikee.app.resp.SummaryBean;
import com.gikee.app.resp.SummaryResp;
import com.gikee.app.views.DividerItemDecoration;
import com.gikee.app.views.LineChartEntity;
import com.gikee.app.views.MyLineChart;
import com.gikee.app.views.MyRefreshBottom;
import com.gikee.app.views.MyRefreshHeader;
import com.gikee.app.views.XYMarkerView;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
//http://www.gikee.com/app/static/android/gikee_debug.apk
public class ProjectCompFragment extends BaseFragment<ShuJuFenXiPresentetr> implements ShuJuFenXiView,OnChartValueSelectedListener {


    @Bind(R.id.project_recycle)
    RecyclerView project_recycle;
    @Bind(R.id.tabslayout)
    TabLayout tabslayout;
    @Bind(R.id.linechart)
    MyLineChart linechart;
    @Bind(R.id.index_recycle)
    RecyclerView index_recycle;
    @Bind(R.id.index_title)
    TextView index_title;
    @Bind(R.id.layout_time)
    LinearLayout layout_time;
    @Bind(R.id.text_time)
    TextView text_time;
    @Bind(R.id.layout_choosedate)
    LinearLayout lin_poplayout;
    @Bind(R.id.recycle_data)
    RecyclerView re_timeinterval;

    @Bind(R.id.title_layout)
    ConstraintLayout title_layout;
    @Bind(R.id.markerview_layout)
    LinearLayout markerview_layout;
//    @Bind(R.id.recycle_markerview)
//    RecyclerView recycle_markerview;
    @Bind(R.id.fm_all_shuju_refreshLayout)
    TwinklingRefreshLayout twinklingRefreshLayout;


    private String coinSymbol;
    private String id;

    private StringBuffer id_sb = new StringBuffer();

    private ProjectChooseAcapter projectChooseAcapter;

    private CompareAdapter indexAdapter;

    private String itemName=Commons.marketValue;

    private String date="30day";

    List<IndexChooseBean> indexAdapterList = new ArrayList<>();

    List<IndexChooseBean> chooseItemBeanList = new ArrayList<>();

    List<IndexChooseBean> choosetitle = new ArrayList<>();

    List<IndexChooseBean> data;

    private IndexChooseBean indexChooseBean,indexChooseBean2;

    private List<String> titles_base=new ArrayList();

    private String choose_title=Commons.price;

    private int choosesize=30;

    private static int REQUESCODE=1000;

    private boolean isfristrun=false;

    private String unit="";

    private int[] color = {R.color.piechat1,R.color.piechat2,R.color.piechat3,R.color.piechat4,R.color.piechat6};

    private Map<String,Integer> color_map = new HashMap<>();
    boolean isshow=false;

    private List<ZhanghuPopBean> list_pop = new ArrayList<>();
    private int timeType=0;
    private String language = "";
    private XYMarkerView xyMarkerView;

    private boolean isShow=false;


    public static ProjectCompFragment getInstance(String symbol,String id){

        ProjectCompFragment projectCompFragment = new ProjectCompFragment();

        projectCompFragment.setParams(symbol,id);



        return projectCompFragment;

    }


    public void setParams(String symbol,String id){
        this.coinSymbol = symbol;
        this.id = id;
        id_sb.append(id);



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(data!=null){
                choosetitle  = (ArrayList<IndexChooseBean>) data.getSerializableExtra("choosetitle");
                initPKProject(choosetitle);
                twinklingRefreshLayout.startRefresh();
            }
        }
    }

    private void initPKProject( List<IndexChooseBean> choose_title) {

        chooseItemBeanList.clear();

        id_sb.delete(0,id_sb.length());

            for(int i=0;i<choose_title.size();i++){

                if(!TextUtils.isEmpty(choose_title.get(i).getId())){

                    String symbol = choose_title.get(i).getValue();

                    if(choose_title.get(i).getValue().contains("+")){
                        symbol = choose_title.get(i).getValue().replace("+ ","");
                    }

                    if(choose_title.get(i).getValue().contains("/")){
                        symbol = MyUtils.filterChinese(symbol.replace("/",""));
                    }

                   // String symbol = choose_title.get(i).getValue().replace("+ ","").replace("/","");

                    indexChooseBean = new IndexChooseBean();

                    for(Map.Entry<String,Integer> color_entry :color_map.entrySet()){

                        if(MyUtils.filterChinese(symbol).equals(color_entry.getKey())){
                            indexChooseBean.setColor(color_entry.getValue());
                        }

                    }

                    indexChooseBean.setId(choose_title.get(i).getId());

                    indexChooseBean.setValue(symbol);

                    indexChooseBean.setEnable(true);

                    chooseItemBeanList.add(indexChooseBean);

                    if(i==choose_title.size()-1){
                        id_sb.append(choose_title.get(i).getId());
                    }else
                        id_sb.append(choose_title.get(i).getId()+",");


                }
            }



       // }

        if(choose_title.size()<6){

            indexChooseBean = new IndexChooseBean();

            indexChooseBean.setId(null);

            indexChooseBean.setValue(getString(R.string.addproject));

            indexChooseBean.setEnable(false);

            chooseItemBeanList.add(indexChooseBean);
        }


        projectChooseAcapter.setNewData(chooseItemBeanList);

    }

    @Override
    protected void setupViews(LayoutInflater inflater) {

        setContentView(inflater, R.layout.fragment_projectcomp);

    }

    @Override
    protected void initView() {

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

        unit = getString(R.string.marketValue_title);


        MyRefreshHeader headerView = new MyRefreshHeader(getContext());

        twinklingRefreshLayout.setEnableLoadmore(false);
        twinklingRefreshLayout.setEnableOverScroll(false);
        twinklingRefreshLayout.setHeaderView(headerView);


        mPresenter = new ShuJuFenXiPresentetr(this);

        linechart.setNoDataText(getString(R.string.getdata));

        xyMarkerView = new XYMarkerView(getContext());

        xyMarkerView.setChartView(linechart);

        linechart.setMarker(xyMarkerView);




        RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) linechart.getLayoutParams();

        params.height=(int)(MyUtils.getHieght()*(0.25));

        linechart.setLayoutParams(params);

        linechart.setOnChartValueSelectedListener(this);

        projectChooseAcapter = new ProjectChooseAcapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        project_recycle.setLayoutManager(linearLayoutManager);

        project_recycle.setAdapter(projectChooseAcapter);

        indexChooseBean = new IndexChooseBean();

        indexChooseBean.setId(id);

        indexChooseBean.setValue(coinSymbol);

        indexChooseBean.setEnable(true);

        indexChooseBean.setColor(color[0]);

        chooseItemBeanList.add(indexChooseBean);

        indexChooseBean = new IndexChooseBean();

        indexChooseBean.setId(null);

        indexChooseBean.setValue(getString(R.string.addproject));

        indexChooseBean.setEnable(false);

        chooseItemBeanList.add(indexChooseBean);

        projectChooseAcapter.setNewData(chooseItemBeanList);

        indexAdapter = new CompareAdapter();

        index_recycle.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));

        index_recycle.setLayoutManager(new LinearLayoutManager(getContext()));

        index_recycle.setAdapter(indexAdapter);

        initChoosePop();

        onClick();

        initTablayout();

        new Thread(new MyThread()).start();



    }


    private void initChoosePop() {

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


                twinklingRefreshLayout.startRefresh();

                endAnimOut();

            }
        });
    }


    private void getData() {

        mPresenter.getProjectCompar(id_sb.toString(),itemName,date);


    }




    private void onClick() {


        indexAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent(getContext(),ZhanghuNumActivity.class);

                intent.putExtra("id", indexAdapter.getData().get(position).getId());

                intent.putExtra("symbol", indexAdapter.getData().get(position).getTitle());

                intent.putExtra("title", unit);

                intent.putExtra("type", choose_title);

                startActivity(intent);

            }
        });


        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                project_recycle.setVisibility(View.GONE);
                tabslayout.setVisibility(View.GONE);
                index_title.setVisibility(View.GONE);
                title_layout.setVisibility(View.GONE);
                linechart.setVisibility(View.GONE);
                index_recycle.setVisibility(View.GONE);
                getData();
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

        tabslayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                String tab_title =tab.getText().toString();

                int position = tab.getPosition();

                if(position==0){
                    unit=tab_title;
                    tab_title = Commons.marketValue;
                    itemName= Commons.marketValue;
                }else if(position==1){
                    unit=tab_title;
                    tab_title = Commons.price;
                    itemName= Commons.price;
                }else if(position==2){
                    unit = tab_title;
                    tab_title = Commons.allAddr;
                    itemName= Commons.allAddr;
                }else if(position==3){
                    unit = tab_title;
                    tab_title = Commons.ownAddr;
                    itemName= Commons.ownAddr;
                }else if(position==4){
                    unit = tab_title;
                    tab_title = Commons.activeDis;
                    itemName= Commons.activeDis;
                }else if(position==5){
                    unit = tab_title;
                    tab_title = Commons.participateAddress;
                    itemName= Commons.participateAddress;
                }else if(position==6){
                    unit = tab_title;
                    tab_title = Commons.newAccount;
                    itemName= Commons.newAccount;
                }else if(position==7){
                    unit = tab_title;
                    tab_title = Commons.wakeCount;
                    itemName= Commons.wakeCount;
                }else if(position==8){
                    unit = tab_title;
                    tab_title = Commons.inactiveCount;
                    itemName= Commons.inactiveCount;
                }else if(position==9){
                    unit = tab_title;
                    tab_title = Commons.tradeCount;
                    itemName= Commons.tradeCount;
                }else if(position==10){
                    unit = tab_title;
                    tab_title = Commons.tradevolume;
                    itemName= Commons.tradevolume;
                }else if(position==11){
                    unit=tab_title+getString(R.string.price_us);
                    tab_title = Commons.tradeValue;
                    itemName= Commons.tradeValue;
                }else if(position==12){
                    unit=tab_title+getString(R.string.price_us);
                    tab_title = Commons.avgTrdValue;
                    itemName= Commons.avgTrdValue;
                }else if(position==13){
                    unit = tab_title;
                    tab_title = Commons.avgTrdVol;
                    itemName= Commons.avgTrdVol;
                }

                choose_title = tab_title;
                if(isShow){
                    getData();
                }

//                twinklingRefreshLayout.startRefresh();


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        projectChooseAcapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                switch (view.getId()){

                    case R.id.delete_image_view:
                        //点击删除对比
                        if(!TextUtils.isEmpty(projectChooseAcapter.getData().get(position).getId())){
                            String delete_id=null;
                            for(IndexChooseBean mindexChooseBean:chooseItemBeanList){
                                if(projectChooseAcapter.getData().get(position).getValue().equals(mindexChooseBean.getValue())){

                                    chooseItemBeanList.remove(mindexChooseBean);
                                    delete_id = mindexChooseBean.getId();
                                    break;
                                }
                            }



                            //下一次网络请求参数更新
                            id_sb.delete(0,id_sb.length());

                            for(IndexChooseBean mindexChooseBean:chooseItemBeanList){

                                if(!TextUtils.isEmpty(mindexChooseBean.getId())){

                                    id_sb.append(mindexChooseBean.getId()+",");
                                }

                            }
                            //删除划线数据源，不重复请求，重新画图
                            for(ProjectCompaBean projectCompaBean:all_data){

                                if(delete_id.equals(projectCompaBean.getId())){

                                    all_data.remove(projectCompaBean);

                                    break;

                                }

                            }

                            setmLineChartData();


                            //添加项目按钮是否显示判断
                            if(chooseItemBeanList.size()==4){

                                boolean addNull=false;

                                for(IndexChooseBean mindexChooseBean:chooseItemBeanList){

                                    if(TextUtils.isEmpty(mindexChooseBean.getId())){

                                        addNull=true;

                                        break;

                                    }
                                }

                                if(!addNull){
                                    indexChooseBean = new IndexChooseBean();

                                    indexChooseBean.setId(null);

                                    indexChooseBean.setValue(getString(R.string.addproject));

                                    indexChooseBean.setEnable(false);

                                    chooseItemBeanList.add(indexChooseBean);
                                }



                            }


                            //更新对比recycleview 数据源
                            projectChooseAcapter.setNewData(chooseItemBeanList);



                        }



                        break;
                    case R.id.selected_text_view:
                        if(TextUtils.isEmpty(projectChooseAcapter.getData().get(position).getId())){
                            //点击添加项目

                            Intent intent = new Intent(getContext(), AddProjectActivity.class);
                            intent.putExtra("isadd",0);
                            intent.putExtra("choosetitle",(Serializable)chooseItemBeanList);
                            startActivityForResult(intent,REQUESCODE);
                            getContext().startActivity(intent);

                        }
                        break;

                }


            }
        });

    }


    private void updataChoose(Map<String, Integer> color_map) {


        for(IndexChooseBean mindexChooseBean:chooseItemBeanList){


            for(Map.Entry<String,Integer> color_entry :color_map.entrySet()){

                if(color_entry.getKey().equals(mindexChooseBean.getValue())){
                    mindexChooseBean.setColor(color_entry.getValue());

                }

            }




        }

        projectChooseAcapter.setNewData(chooseItemBeanList);
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
        // .setVisibility(View.GONE);
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


    @Override
    protected void lazyLoad() {

        if(isViewCreated&&isUIVisible){
            twinklingRefreshLayout.startRefresh();
            isShow=true;
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;


        }

    }

    @Override
    protected void onChangeEvent(int type) {

    }

    private  List<ProjectCompaBean> all_data = new ArrayList<>();

    @Override
    public void onProjectCompar(ProjectCompaResp projectCompaResp) {

        twinklingRefreshLayout.finishRefreshing();
        if(TextUtils.isEmpty(projectCompaResp.getErrInfo())){
            project_recycle.setVisibility(View.VISIBLE);
            tabslayout.setVisibility(View.VISIBLE);
            index_title.setVisibility(View.VISIBLE);
            title_layout.setVisibility(View.VISIBLE);
            linechart.setVisibility(View.VISIBLE);
            index_recycle.setVisibility(View.VISIBLE);
            int maxProject=0;

            String maxProject_id = null;

            all_data =   projectCompaResp.getResult().getData();
            indexAdapterList.clear();
            color_map.clear();
            int count=0;
            for(ProjectCompaBean projectCompaBean:all_data){

                color_map.put(projectCompaBean.getSymbol(),color[count]);
                count++;

//                if(maxProject<projectCompaBean.getCoinInfo().size()){
//                    //找出项目最长的指标
//                    maxProject = projectCompaBean.getCoinInfo().size();
//
//                    maxProject_id = projectCompaBean.getId();
//
//                }

            }
//
//
//
//            for(ProjectCompaBean projectCompaBean:all_data){
//
//                titles_base.clear();
//
//                if(projectCompaBean.getId().equals(maxProject_id)){
//                    //最长的项目指标作为tablayout赋值
//                    List<SummaryBean> coinInfo = projectCompaBean.getCoinInfo();
//
//                    for(SummaryBean summaryBean:coinInfo){
//                        if(language.equals("en")){
//                            titles_base.add(summaryBean.getItemName());
//                        }else
//                            titles_base.add(summaryBean.getTitile());
//
//
//                    }
//
//                }
//
//            }
            setmLineChartData();

            updataChoose(color_map);

           // initMarketView();


        }

    }


    private void initTablayout() {

        titles_base.add(getString(R.string.marketValue));

        titles_base.add(getString(R.string.price_title));

        titles_base.add(getString(R.string.allAddr_title));

        titles_base.add(getString(R.string.ownAddr_title));

        titles_base.add(getString(R.string.activeDis_title));

        titles_base.add(getString(R.string.participateAddress));

        titles_base.add(getString(R.string.newCount));

        titles_base.add(getString(R.string.awakeaddress));

        titles_base.add(getString(R.string.inactiveCount));

        titles_base.add(getString(R.string.tradecount));

        titles_base.add(getString(R.string.tradenum));

        titles_base.add(getString(R.string.tradeValue));

        titles_base.add(getString(R.string.avgTrdVol_title));

        titles_base.add(getString(R.string.avgTrdValue));



        for(String tab:titles_base){

            tabslayout.addTab(tabslayout.newTab().setText(tab));

        }
    }


    int maxSize=0;

    float y = 0;

    private LineDataSet lineDataSet;

    private LineData lineData;

    private LineChartEntity lineChartEntity=null;

    private Entry entry;

    private Map<String,List<Entry>> point_list = new HashMap<>();

    List<String>  xValue = new ArrayList<>();

    private void setmLineChartData() {

        List<ILineDataSet>   dataSets = new ArrayList<>();

        xValue.clear();
        maxSize=0;
        point_list.clear();
        indexAdapterList.clear();

        for(ProjectCompaBean projectCompaBean:all_data){
            List<SummaryBean> coinInfo = projectCompaBean.getCoinInfo();
            indexChooseBean2 = new IndexChooseBean();

            indexChooseBean2.setLogo(projectCompaBean.getLogo());

            indexChooseBean2.setType(choose_title);

            indexChooseBean2.setId(projectCompaBean.getId());

            indexChooseBean2.setTitle(projectCompaBean.getSymbol());

            for(Map.Entry<String,Integer> color_entry :color_map.entrySet()){

                if(projectCompaBean.getSymbol().contains(color_entry.getKey())){
                    indexChooseBean2.setColor(color_entry.getValue());
                }

            }


            for(SummaryBean summaryBean:coinInfo){
                if(summaryBean.getItemName().equals(choose_title)){
                    indexChooseBean2.setValue(summaryBean.getLineData().get(0).getCurrentValue().get(0));
                    indexChooseBean2.setQuate(summaryBean.getLineData().get(0).getQuoteChange());
                    indexAdapterList.add(indexChooseBean2);
                    List<DotBean> dot = summaryBean.getLineData().get(0).getDot();
                    List<Entry> point = new ArrayList<>();
                    if(choosesize!=-1){
                        if(dot.size()>=choosesize){

                            int count=0;

                            for(int n=dot.size()-choosesize;n<dot.size();n++){

                                if(dot.size()>maxSize){
                                    xValue.add(dot.get(n).getTime());
                                }

                                if(!TextUtils.isEmpty(dot.get(n).getValue())&&!"None".equals(dot.get(n).getValue())){

                                    y = Float.parseFloat(dot.get(n).getValue());
                                }
                                entry =  new Entry((float) count, y);

                                point.add(entry);

                                count++;

                            }
                        }else{

                            for(int n=0;n<dot.size();n++){

                                y=0;

                                if(dot.size()>maxSize){
                                    xValue.add(dot.get(n).getTime());
                                }

                                if(!TextUtils.isEmpty(dot.get(n).getValue())&&!"None".equals(dot.get(n).getValue())){

                                    y = Float.parseFloat(dot.get(n).getValue());
                                }

                                entry =  new Entry((float) n, y);

                                point.add(entry);

                            }

                        }
                    }else{

                        for(int n=0;n<dot.size();n++){

                            y=0;
                            if(dot.size()>maxSize){
                                xValue.add(dot.get(n).getTime());
                            }

                            if(!TextUtils.isEmpty(dot.get(n).getValue())){

                                y = Float.parseFloat(dot.get(n).getValue());
                            }

                            entry =  new Entry((float) n, y);

                            point.add(entry);

                        }
                    }

                    point_list.put(projectCompaBean.getSymbol(),point);
                    if(point.size()>maxSize){

                        maxSize = point.size();
                    }
                }

            }

        }

        indexAdapter.setNewData(indexAdapterList);


        //数据源右对齐
        Iterator< Map.Entry<String,List<Entry>>> iterator = point_list.entrySet().iterator();
        Map<String,List<Entry>> new_point = new HashMap<>();

        while(iterator.hasNext()){
            Map.Entry<String,List<Entry>> mpointPrice = iterator.next();
            if (mpointPrice.getValue().size() < maxSize) {
                List<Entry> pointPrice = new ArrayList<>();
                int count=0;
                for (int i = 0; i < maxSize; i++) {
                    if (i < maxSize - mpointPrice.getValue().size()) {
                        y = 0;
                        entry = new Entry((float) i, y);
                    }else{
                        y = mpointPrice.getValue().get(count).getY();
                        entry = new Entry((float) i, y);
                        count++;
                    }
                    pointPrice.add(entry);
                }

                iterator.remove();
                new_point.put(mpointPrice.getKey(),pointPrice);
            }

        }



        for(Map.Entry<String,List<Entry>> entry :new_point.entrySet()){

            point_list.put(entry.getKey(),entry.getValue());

        }


        //数据源
        for(Map.Entry<String,List<Entry>> entry :point_list.entrySet()){

            if(entry.getValue().size()!=0){
                lineDataSet = new LineDataSet(entry.getValue(), "");
                lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
                lineDataSet.setLineWidth(0.8f);
                lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                lineDataSet.setFillAlpha(40);
                lineDataSet.setDrawCircles(false);
                lineDataSet.setDrawFilled(false);
                lineDataSet.enableDashedHighlightLine(5f, 5f, 0);
                int color=0;
                for(Map.Entry<String,Integer> color_entry :color_map.entrySet()){

                    if(entry.getKey().contains(color_entry.getKey())){
                        color = color_entry.getValue();
                    }

                }
                lineDataSet.setHighLightColor(getContext().getResources().getColor(color));
                lineDataSet.setColor(getContext().getResources().getColor(color));
                lineDataSet.setDrawValues(false);
                dataSets.add(lineDataSet);
            }
        }

        if(dataSets.size()!=0){
            lineData = new LineData(dataSets);

            if(lineChartEntity!=null){

                linechart.setData(lineData);

                lineChartEntity.initXValue(xValue);

                lineChartEntity.showRight(false);

                linechart.invalidate();

                linechart.animateX(1000);

            }else {
                lineChartEntity = new LineChartEntity(getContext(), linechart, lineData, xValue, "day", Commons.price);

                lineChartEntity.setLegendEnabled(false);

                lineChartEntity.showRightY(false, "");


            }

            xyMarkerView.setLineData(point_list);
            xyMarkerView.setColormap(color_map);
            xyMarkerView.setChoose_title(choose_title);
            xyMarkerView.setIAxisValueFormatter(xValue);
        }else
            ToastUtils.show(getString(R.string.nodata));

    }


    @Override
    public void onShuJuFenXi(SummaryResp shuJuFenXiBean) {

    }

    @Override
    public void onIndexContrast(SummaryResp shuJuFenXiBean) {

    }

    @Override
    public void onProjectInfo(ProjectInfoResp projectInfoResp) {

    }


    @Override
    public void onError() {

    }


    long currentTimeMillis;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if(System.currentTimeMillis()-currentTimeMillis>1000){
                   // markerview_layout.setVisibility(View.GONE);
                    if(linechart!=null){
                        linechart.highlightValue(null);
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

    @Override
    public void onValueSelected(Entry e, Highlight h) {


        currentTimeMillis =  System.currentTimeMillis();



    }

    @Override
    public void onNothingSelected() {

    }
}
