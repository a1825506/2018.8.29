package com.gikee.app.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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
import android.widget.TableLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.adapter.IndexAdapter;
import com.gikee.app.adapter.MarkViewAdapter;
import com.gikee.app.adapter.PopAllprojectAdapter;
import com.gikee.app.beans.IndexChooseBean;
import com.gikee.app.beans.MarkerViewBean;
import com.gikee.app.beans.ZhanghuPopBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.presenter.project.ShuJuFenXiPresentetr;
import com.gikee.app.presenter.project.ShuJuFenXiView;
import com.gikee.app.resp.DotBean;
import com.gikee.app.resp.ProjectCompaResp;
import com.gikee.app.resp.ProjectInfoResp;
import com.gikee.app.resp.SummaryBean;
import com.gikee.app.resp.SummaryResp;
import com.gikee.app.views.AutoHeightViewPager;
import com.gikee.app.views.BaseFragmentPagerAdapter;
import com.gikee.app.views.LineChartEntity;
import com.gikee.app.views.MyBoldTextView;
import com.gikee.app.views.MyLineChart;
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
import com.github.mikephil.charting.listener.OnDrawLineChartTouchListener;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

public class IndexCompFragment extends BaseFragment<ShuJuFenXiPresentetr> implements ShuJuFenXiView,OnChartValueSelectedListener {

    @Bind(R.id.layout_time)
    LinearLayout layout_time;
    @Bind(R.id.linechart)
    MyLineChart myLineChart;
    @Bind(R.id.tabslayout)
    TabLayout tabslayout;
    @Bind(R.id.viewpager)
    AutoHeightViewPager viewPager;
    @Bind(R.id.layout_choosedate)
    LinearLayout lin_poplayout;
    @Bind(R.id.recycle_data)
    RecyclerView re_timeinterval;
    @Bind(R.id.text_time)
    TextView text_time;
    @Bind(R.id.all_layout)
    RelativeLayout all_layout;
    @Bind(R.id.txt_price)
    TextView txt_price;
    @Bind(R.id.txt_title)
    TextView txt_title;
    @Bind(R.id.img_title)
    ImageView img_title;
    @Bind(R.id.markerview_layout)
    LinearLayout markerview_layout;
    @Bind(R.id.recycle_markerview)
    RecyclerView recycle_markerview;
    @Bind(R.id.fm_all_shuju_refreshLayout)
    TwinklingRefreshLayout twinklingRefreshLayout;
    @Bind(R.id.text_baseLine)
    MyBoldTextView text_baseLine;
    @Bind(R.id.title_layout)
    ConstraintLayout title_layout;
    @Bind(R.id.text_info)
    TextView text_info;

    private MarkViewAdapter markViewAdapter;

    private List<ZhanghuPopBean> list_pop = new ArrayList<>();
    private int timeType=0;
    private int choosesize=30;
    boolean isshow=false;
    private List<BaseFragment> fragments = new ArrayList<>();
    private String coinSymbol;
    private String id;
    private List<String> choose_title=new ArrayList();

    private String date="30day";

//    private String[] newaccount = {Commons.activeDis,Commons.newAccount,Commons.participateAddress,Commons.wakeCount};
//
//    private String[] allaccount = {Commons.allAddr,Commons.ownAddr,Commons.inactiveCount};

    private int[] color = {R.color.piechat1,R.color.pie5,R.color.pie6,R.color.C6BC0,R.color.piechat4,R.color.A69A,R.color.piechat3,
                           R.color.pie14, R.color.piechat2,R.color.FF7043,R.color.C78909,R.color.pie18,R.color.BDBDBD,R.color.D3B37D};

    private Map<String,Integer> color_map = new HashMap<>();

    private Map<String,String> map_yValue;

    private Map<String,List<Entry>> point_list = new HashMap<>();


    private  List<SummaryBean> all_data = new ArrayList<>();
    private  List<IndexChooseBean> table_data = new ArrayList<>();
    private IndexChooseBean indexChooseBean;

    private int maxSize=0;

    private XYMarkerView xyMarkerView;

    public static IndexCompFragment getInstance(String symbol,String id){

        IndexCompFragment indexCompFragment = new IndexCompFragment();

        indexCompFragment.setParams(symbol,id);

        return indexCompFragment;

    }

    public void setParams(String symbol,String id){
        this.coinSymbol = symbol;
        this.id = id;

    }


    @Override
    protected void setupViews(LayoutInflater inflater) {
        setContentView(inflater, R.layout.fragment_indexcomp);
    }

    @Override
    protected void initView() {

        mPresenter = new ShuJuFenXiPresentetr(this);

        MyRefreshHeader headerView = new MyRefreshHeader(getContext());

        twinklingRefreshLayout.setEnableLoadmore(false);
        twinklingRefreshLayout.setEnableOverScroll(false);
        twinklingRefreshLayout.setHeaderView(headerView);


        myLineChart.setNoDataText(getString(R.string.getdata));

        xyMarkerView = new XYMarkerView(getContext());

        xyMarkerView.setChartView(myLineChart);

        myLineChart.setMarker(xyMarkerView);

        RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) myLineChart.getLayoutParams();

        params.height=(int)(MyUtils.getHieght()*(0.25));

        myLineChart.setLayoutParams(params);

        myLineChart.setOnChartValueSelectedListener(this);




        final String[] mTitle =new String[1];

        mTitle[0]=getString(R.string.index_choose);

       // mTitle[1]=getString(R.string.event_information);

        fragments.add(IndexChooseFragment.getInstance());

        IndexChooseFragment.getInstance().setId(coinSymbol,id);

      //  fragments.add(new IndexChooseFragment());

        BaseFragmentPagerAdapter baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getChildFragmentManager()) {
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
        viewPager.setScanScroll(false);

        viewPager.setAdapter(baseFragmentPagerAdapter);

        viewPager.setOffscreenPageLimit(1);

        viewPager.setCurrentItem(0);

        tabslayout.setupWithViewPager(viewPager);

        tabslayout.setVisibility(View.GONE);

        initChoosePop();

        onClick();

        new Thread(new MyThread()).start();





    }

    private void onClick() {






        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                layout_time.setVisibility(View.GONE);
                text_baseLine.setVisibility(View.GONE);
                title_layout.setVisibility(View.GONE);
                getData();
            }

        });


//        all_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myLineChart.highlightValue(null);
//            }
//        });

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

        IndexChooseFragment.getInstance().setOnUpdateChoose(new IndexChooseFragment.OnUpdateChoose() {
            @Override
            public void updateChoose(String choose) {

                if (choose_title.contains(choose)) {
                    choose_title.remove(choose);
                } else
                    choose_title.add(choose);

                IndexChooseFragment.getInstance().setChooseTitle(choose_title);


                setmLineChartData();

            }
        });
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
        // .setVisibility(View.GONE);
    }

    @Override
    protected void lazyLoad() {

        if(isViewCreated&&isUIVisible){
            twinklingRefreshLayout.startRefresh();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;


        }

    }

    @Override
    protected void onChangeEvent(int type) {

    }

    private void getData() {

        mPresenter.getIndexContrast(id,date);

    }

    @Override
    public void onShuJuFenXi(SummaryResp shuJuFenXiBean) {



    }

    @Override
    public void onIndexContrast(SummaryResp shuJuFenXiBean) {

        twinklingRefreshLayout.finishRefreshing();

        if(TextUtils.isEmpty(shuJuFenXiBean.getErrInfo())){


            if(shuJuFenXiBean.getResult()!=null){

                layout_time.setVisibility(View.VISIBLE);
                text_baseLine.setVisibility(View.VISIBLE);
                title_layout.setVisibility(View.VISIBLE);
                text_info.setVisibility(View.VISIBLE);
                table_data.clear();

                all_data.clear();

                List<SummaryBean> data =shuJuFenXiBean.getResult().getData();

                int count=0;
                for(SummaryBean summaryBean:data){

                    if(summaryBean.getShowType().equals("lineWave")){

                        //提取所有折线图
                        all_data.add(summaryBean);

                        indexChooseBean = new IndexChooseBean();

                        if(summaryBean.getItemName().equals(Commons.price)||summaryBean.getItemName().equals(Commons.allAddr)){
                            choose_title.add(summaryBean.getItemName());
                            indexChooseBean.setCheck(true);
                        }else
                            indexChooseBean.setCheck(false);



                        indexChooseBean.setEnable(true);

                        indexChooseBean.setTitle(summaryBean.getTitile());

                        indexChooseBean.setValue(summaryBean.getLineData().get(0).getCurrentValue().get(0));

                        indexChooseBean.setId(summaryBean.getItemName());

                        indexChooseBean.setColor(color[count]);

                        indexChooseBean.setQuate(summaryBean.getLineData().get(0).getQuoteChange());


                        table_data.add(indexChooseBean);

                        color_map.put(summaryBean.getItemName(),color[count]);

                        count++;
                    }

                }
                //绘图
                setmLineChartData();
                //填充table数据
                IndexChooseFragment.getInstance().setData(table_data);

                markViewAdapter = new MarkViewAdapter(color_map);

                GridLayoutManager  gridLayoutManager1 = new GridLayoutManager(getContext(), 2);

                recycle_markerview.setLayoutManager(gridLayoutManager1);

                recycle_markerview.setAdapter(markViewAdapter);


            }

        }

    }


    float y = 0;

    LineDataSet lineDataSet;

    private LineData lineData;

    private  LineChartEntity lineChartEntity=null;

    private Entry entry;



    List<String>  xValue = new ArrayList<>();





    private void setmLineChartData() {

        List<ILineDataSet>   dataSets = new ArrayList<>();

        point_list.clear();

        boolean isLeftY=false;
        boolean isRightY=false;
        boolean showRight=false;


        //List<List<Entry>> point_list = new ArrayList<List<Entry>>();

        map_yValue = new HashMap<>() ;

        maxSize=0;

        //maxSize=0;

        for(int i=0;i<all_data.size();i++){

            for(int j=0;j<choose_title.size();j++){

                if(all_data.get(i).getItemName().equals(choose_title.get(j))){

                    xValue.clear();

                    List<Entry> pointPrice = new ArrayList<>();

                    List<DotBean> dot = all_data.get(i).getLineData().get(0).getDot();


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

                                pointPrice.add(entry);

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

                                pointPrice.add(entry);

                            }

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

                            pointPrice.add(entry);

                        }
                    }

                    point_list.put(choose_title.get(j),pointPrice);

                    if(pointPrice.size()>maxSize){

                        maxSize = pointPrice.size();
                    }

                }

            }

        }





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

                if(choose_title.size()==1){
                    lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                    map_yValue.put(entry.getKey(),"left");
                    showRight=false;
                }else {
                    for (String choose : choose_title) {
                                if (choose.equals(entry.getKey())) {
                                    if (isLeftY) {
                                        lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
                                        isRightY = true;
                                    }else{
                                        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                                        isLeftY = true;
                                    }

                                    if (isLeftY) {
                                        map_yValue.put(entry.getKey(), "left");
                                        showRight = false;
                                    }
                                    if (isRightY) {
                                        map_yValue.put(entry.getKey(), "right");
                                        showRight = true;
                                    }
                                }

                    }
                }

                lineDataSet.setFillAlpha(40);
                lineDataSet.setDrawCircles(false);
                lineDataSet.setDrawFilled(false);
                lineDataSet.enableDashedHighlightLine(5f, 5f, 0);
                int color=0;
                for(Map.Entry<String,Integer> color_entry :color_map.entrySet()){

                    if(color_entry.getKey().equals(entry.getKey())){
                        color = color_entry.getValue();
                    }

                }
                lineDataSet.setHighLightColor(getResources().getColor(color));
                lineDataSet.setColor(getResources().getColor(color));
                lineDataSet.setDrawValues(false);
                dataSets.add(lineDataSet);


            }

        }

        if(dataSets.size()!=0){
            lineData = new LineData(dataSets);

            if(lineChartEntity!=null){

                myLineChart.setData(lineData);

                lineChartEntity.initXValue(xValue);

                lineChartEntity.showRight(showRight);

                myLineChart.invalidate();

                myLineChart.animateX(1000);

            }else {
                lineChartEntity = new LineChartEntity(getContext(), myLineChart, lineData, xValue, "day", Commons.currectLine);

                lineChartEntity.setLegendEnabled(false);

                lineChartEntity.showRightY(showRight, Commons.currectLine);
            }

            updateYValue(map_yValue);

            xyMarkerView.setLineData(point_list);
            xyMarkerView.setColormap(color_map);
            xyMarkerView.setChoose_title(null);
            xyMarkerView.setIAxisValueFormatter(xValue);
        }else{


         //   myLineChart.invalidate();
        }



    }

    //更新y轴title
    private void updateYValue(Map<String, String> map_yValue) {
        txt_price.setVisibility(View.GONE);
        txt_title.setVisibility(View.GONE);

        for(Map.Entry<String,String> y_title :map_yValue.entrySet()){

            if(y_title.getValue().equals("left")){

                txt_price.setVisibility(View.VISIBLE);

                if(y_title.getKey().equals(Commons.price)){
                    txt_price.setText(getString(R.string.price_us));
                }else if(y_title.getKey().equals(Commons.tradeValue)){
                    txt_price.setText(getString(R.string.tradeValue)+"("+getString(R.string.dg_chose_unit_america_des)+")");
                }else if(y_title.getKey().equals(Commons.avgTrdValue)){
                    txt_price.setText(getString(R.string.avgTrdValue)+"("+getString(R.string.dg_chose_unit_america_des)+")");
                }else if(y_title.getKey().equals(Commons.marketValue)){
                    txt_price.setText(getString(R.string.market_title));
                }else if(y_title.getKey().equals(Commons.tradeCount)){
                    txt_price.setText(getString(R.string.ap_transationnum));
                }else if(y_title.getKey().equals(Commons.tradevolume)){
                    txt_price.setText(getString(R.string.trade));
                }else if(y_title.getKey().equals(Commons.avgTrdVol)){
                    txt_price.setText(getString(R.string.avgTrdVol_title));
                }else if(y_title.getKey().equals(Commons.allAddr)){
                    txt_price.setText(getString(R.string.allAddr));
                }else if(y_title.getKey().equals(Commons.ownAddr)){
                    txt_price.setText(getString(R.string.onweraddress));
                }else if(y_title.getKey().equals(Commons.inactiveCount)){
                    txt_price.setText(getString(R.string.inactiveCount));
                }else if(y_title.getKey().equals(Commons.newAccount)){
                    txt_price.setText(getString(R.string.newaddress));
                }else if(y_title.getKey().equals(Commons.activeDis)){
                    txt_price.setText(getString(R.string.activeDis));
                }else if(y_title.getKey().equals(Commons.wakeCount)){
                    txt_price.setText(getString(R.string.wakeCount));
                }else if(y_title.getKey().equals(Commons.participateAddress)){
                    txt_price.setText(getString(R.string.participateAddress));
                }
            }


                if(y_title.getValue().equals("right")){
                    txt_title.setVisibility(View.VISIBLE);

                 if(y_title.getKey().equals(Commons.price)){
                    txt_title.setText(getString(R.string.price_us));
                }else if(y_title.getKey().equals(Commons.tradeValue)){
                    txt_title.setText(getString(R.string.tradeValue)+"("+getString(R.string.dg_chose_unit_america_des)+")");
                }else if(y_title.getKey().equals(Commons.avgTrdValue)){
                    txt_title.setText(getString(R.string.avgTrdValue)+"("+getString(R.string.dg_chose_unit_america_des)+")");
                }else if(y_title.getKey().equals(Commons.marketValue)){
                    txt_title.setText(getString(R.string.market_title));
                }else if(y_title.getKey().equals(Commons.tradeCount)){
                    txt_title.setText(getString(R.string.ap_transationnum));
                }else if(y_title.getKey().equals(Commons.tradevolume)){
                    txt_title.setText(getString(R.string.trade));
                }else if(y_title.getKey().equals(Commons.avgTrdVol)){
                    txt_title.setText(getString(R.string.avgTrdVol_title));
                }else if(y_title.getKey().equals(Commons.allAddr)){
                    txt_title.setText(getString(R.string.allAddr));
                }else if(y_title.getKey().equals(Commons.ownAddr)){
                    txt_title.setText(getString(R.string.onweraddress));
                }else if(y_title.getKey().equals(Commons.inactiveCount)){
                    txt_title.setText(getString(R.string.inactiveCount));
                }else if(y_title.getKey().equals(Commons.newAccount)){
                    txt_title.setText(getString(R.string.newaddress));
                }else if(y_title.getKey().equals(Commons.activeDis)){
                    txt_title.setText(getString(R.string.activeDis));
                }else if(y_title.getKey().equals(Commons.wakeCount)){
                    txt_title.setText(getString(R.string.wakeCount));
                }else if(y_title.getKey().equals(Commons.participateAddress)){
                    txt_title.setText(getString(R.string.participateAddress));
                }

            }
        }


    }


    @Override
    public void onProjectInfo(ProjectInfoResp projectInfoResp) {

    }

    @Override
    public void onProjectCompar(ProjectCompaResp projectCompaResp) {

    }

    @Override
    public void onError() {

    }


    @Override
    public void onResume() {
        super.onResume();
        if(myLineChart!=null){
            myLineChart.highlightValue(null);
        }

    }


    private List<MarkerViewBean>  markerViewBeanList = new ArrayList<>();
    long currentTimeMillis;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if(System.currentTimeMillis()-currentTimeMillis>1000){
                   // markerview_layout.setVisibility(View.GONE);
                    if(myLineChart!=null){
                        myLineChart.highlightValue(null);
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
                    Thread.sleep(200);//每隔1s执行一次
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

//        markerview_layout.setVisibility(View.VISIBLE);
//        markerViewBeanList.clear();
//
//        MarkerViewBean markerViewBean;
//
//        for(int i=0;i<xValue.size();i++){
//
//            if(i==e.getX()){
//
//                markerViewBean=new MarkerViewBean();
//                markerViewBean.setId("");
//                markerViewBean.setValue(xValue.get(i));
//                markerViewBeanList.add(markerViewBean);
//
//            }
//
//        }
//
//        for(Map.Entry<String,List<Entry>> entry :point_list.entrySet()){
//
//            for(int i=0;i<entry.getValue().size();i++){
//
//                if(entry.getValue().get(i).getX()==e.getX()){
//
//                    markerViewBean=new MarkerViewBean();
//                    String title="";
//                    if(entry.getKey().equals(Commons.price)){
//                        title = getString(R.string.price_title);
//                    }else if(entry.getKey().equals(Commons.marketValue)){
//                        title = getString(R.string.marketValue_title);
//                    }else if(entry.getKey().equals(Commons.allAddr)){
//                        title = getString(R.string.allAddr_title);
//                    }else if(entry.getKey().equals(Commons.ownAddr)){
//                        title = getString(R.string.ownAddr_title);
//                    }else if(entry.getKey().equals(Commons.newAccount)){
//                        title = getString(R.string.newCount);
//                    }else if(entry.getKey().equals(Commons.activeDis)){
//                        title = getString(R.string.activeDis_title);
//                    }else if(entry.getKey().equals(Commons.participateAddress)){
//                        title = getString(R.string.participateAddress);
//                    }else if(entry.getKey().equals(Commons.wakeCount)){
//                        title = getString(R.string.wakeCount);
//                    }else if(entry.getKey().equals(Commons.inactiveCount)){
//                        title = getString(R.string.inactiveCount);
//                    }else if(entry.getKey().equals(Commons.tradeCount)){
//                        title = getString(R.string.tradecount);
//                    }else if(entry.getKey().equals(Commons.tradevolume)){
//                        title = getString(R.string.tradenum);
//                    }else if(entry.getKey().equals(Commons.tradeValue)){
//                        title = getString(R.string.tradeValue);
//                    }else if(entry.getKey().equals(Commons.avgTrdVol)){
//                        title = getString(R.string.avgTrdVol_title);
//                    }else if(entry.getKey().equals(Commons.avgTrdValue)){
//                        title = getString(R.string.avgTrdValue);
//                    }
//
//                    markerViewBean.setId(entry.getKey());
//                    markerViewBean.setTitle(title);
//                    BigDecimal bd = new BigDecimal(entry.getValue().get(i).getY()+"");
//                    markerViewBean.setValue(MyUtils.fmtMicrometer(bd.toPlainString()));
//                    markerViewBeanList.add(markerViewBean);
//
//                }
//
//            }
//
//        }
//        markViewAdapter.setNewData(markerViewBeanList);

    }

    @Override
    public void onNothingSelected() {



    }
}
