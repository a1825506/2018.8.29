package com.gikee.app.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.activity.ETHAddressDetailActivity;
import com.gikee.app.activity.TradeDetailActivity;
import com.gikee.app.adapter.AddressDetailAdapter;
import com.gikee.app.adapter.PopAllprojectAdapter;
import com.gikee.app.beans.SpecialAccountBean;
import com.gikee.app.beans.ZhanghuPopBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.presenter.baseline.AddressDetailPresenter;
import com.gikee.app.presenter.project.MineProjectPresenter;
import com.gikee.app.presenter.project.MineProjectView;
import com.gikee.app.resp.AddressAddedResp;
import com.gikee.app.resp.AddressCountResp;
import com.gikee.app.resp.AssetResp;
import com.gikee.app.resp.HashTradeResp;
import com.gikee.app.resp.TokensAddedResp;
import com.gikee.app.views.MyRefreshBottom;
import com.gikee.app.views.MyRefreshHeader;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import cn.qqtheme.framework.picker.DatePicker;

public class AccountTradeListFragment extends BaseFragment<MineProjectPresenter> implements MineProjectView {

    @Bind(R.id.address_recycle_layout)
    TwinklingRefreshLayout twinklingRefreshLayout;
    @Bind(R.id.address_recycle)
    RecyclerView recyclerView;
    @Bind(R.id.all_time)
    LinearLayout all_time;
    @Bind(R.id.start_time)
    TextView start_time;
    @Bind(R.id.end_time)
    TextView end_time;
    @Bind(R.id.time_choose_layout)
    LinearLayout time_choose_layout;
    @Bind(R.id.time_recycle)
    RecyclerView time_recycle;
    @Bind(R.id.date_choode)
    TextView date_choode;
    @Bind(R.id.all_account)
    LinearLayout all_account;
    @Bind(R.id.account_choose_layout)
    LinearLayout account_choose_layout;
    @Bind(R.id.choose_all_group)
    RadioGroup choose_all_group;


    @Bind(R.id.choose_background)
    View choose_background;
    @Bind(R.id.pop_cancle)
    View pop_cancle;

    @Bind(R.id.pop_rechose)
    TextView btn_reset;

    @Bind(R.id.pop_sure)
    TextView btn_sure;

    @Bind(R.id.choose_all)
    RadioButton choose_all_radio;

    @Bind(R.id.choose_asset_transfer)
    RadioButton choose_radio;

    @Bind(R.id.choose_asset_transfer_out)
    RadioButton choose_out_radio;



    private String type="all";



    private List<ZhanghuPopBean> list_pop = new ArrayList<>();
    private int timeType=0;
    private boolean isshow=false;
    private String  date_chose_start = "2009-06-17", date_chose_end = "2009-07-17";

    private  boolean isloadMore=false;


    private AddressDetailAdapter tradeadapter;

    public  static  final int REQUESCODE = 400;

    private String params;

    private View footerView;

    private TextView foottext;

    private String sort="1";

    private Date dateToday;

    List<SpecialAccountBean> mSpecialAccountBean;

    public static AccountTradeListFragment getInstance(List<SpecialAccountBean> specialAccountBean){

        AccountTradeListFragment accountStatisticsFragment = new AccountTradeListFragment();

        accountStatisticsFragment.setParam(specialAccountBean);

        return accountStatisticsFragment;

    }


    private void setParam(List<SpecialAccountBean> specialAccountBean){

        mSpecialAccountBean = specialAccountBean;

    }


    @Override
    protected void setupViews(LayoutInflater inflater) {



        setContentView(inflater, R.layout.fragment_accounttradelist);



    }

    private void initOnClick() {


        choose_all_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.choose_all:
                        choose_radio.setBackground(getResources().getDrawable(R.color.gray_86));

                        choose_all_radio.setBackground(getResources().getDrawable(R.color.title_color));

                        choose_out_radio.setBackground(getResources().getDrawable(R.color.gray_86));

                        choose_radio.setTextColor(getResources().getColor(R.color.black));

                        choose_all_radio.setTextColor(getResources().getColor(R.color.white));

                        choose_out_radio.setTextColor(getResources().getColor(R.color.black));

                        type="all";
                        break;
                    case R.id.choose_asset_transfer:

                        choose_radio.setBackground(getResources().getDrawable(R.color.title_color));

                        choose_all_radio.setBackground(getResources().getDrawable(R.color.gray_86));

                        choose_out_radio.setBackground(getResources().getDrawable(R.color.gray_86));

                        choose_radio.setTextColor(getResources().getColor(R.color.white));

                        choose_all_radio.setTextColor(getResources().getColor(R.color.black));

                        choose_out_radio.setTextColor(getResources().getColor(R.color.black));

                        type="in";
                        break;
                    case R.id.choose_asset_transfer_out:

                        choose_radio.setBackground(getResources().getDrawable(R.color.gray_86));

                        choose_all_radio.setBackground(getResources().getDrawable(R.color.gray_86));

                        choose_out_radio.setBackground(getResources().getDrawable(R.color.title_color));

                        choose_radio.setTextColor(getResources().getColor(R.color.black));

                        choose_all_radio.setTextColor(getResources().getColor(R.color.black));

                        choose_out_radio.setTextColor(getResources().getColor(R.color.white));
                        type="out";
                        break;
                }
                account_choose_layout.setVisibility(View.GONE);
                twinklingRefreshLayout.startRefresh();
            }
        });


        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDateChoose();
            }
        });


        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date_chose_start = start_time.getText().toString();
                date_chose_end = end_time.getText().toString();

                twinklingRefreshLayout.startRefresh();
                endAnimOut();
            }
        });


        all_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isshow){
                    isshow=false;
                    endAnimOut();

                }else{
                    isshow=true;
                    starAnimEnter();
                }


            }
        });

        choose_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                account_choose_layout.setVisibility(View.GONE);
            }
        });


        pop_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isshow){
                    isshow=false;
                    endAnimOut();

                }
            }
        });



        all_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(account_choose_layout.getVisibility()==View.GONE){

                    account_choose_layout.setVisibility(View.VISIBLE);

                }else
                    account_choose_layout.setVisibility(View.GONE);

            }
        });

        tradeadapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {


                Intent   intent = new Intent(getContext(),TradeDetailActivity.class);

                intent.putExtra("type","ETH");

                intent.putExtra("hash",tradeadapter.getData().get(position).getTradehash());

                intent.putExtra("jumptype",1);

                intent.putExtra("trade_type", "ETH");

                startActivityForResult(intent,REQUESCODE);
            }
        });

        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                isloadMore=false;
                sort="1";
                getData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                isloadMore=true;
                getData();

            }
        });

        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Drawable drawableRight = getResources().getDrawable(
                            R.mipmap.icon_up);
                    start_time.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, drawableRight, null);
                    start_time.setCompoundDrawablePadding(MyUtils.dip2px(15));
                    DatePicker picker = new DatePicker(getActivity());
                    picker.setSubmitTextColor(Color.parseColor("#39384c"));
                    picker.setCancelTextColor(Color.parseColor("#39384c"));
                    picker.setCancelText(R.string.mp_add_cancle);
                    picker.setSubmitText(R.string.dg_uploadcomplete_sure);
                    picker.setLabel("", "", "");
                    picker.setHeight(MyUtils.dip2px(250));
                    picker.setTopLineColor(Color.parseColor("#f7f7f8"));
                    picker.setTopLineHeight(2);
                    picker.setPressedTextColor(Color.parseColor("#39384c"));
                    picker.setDividerColor(Color.parseColor("#39384c"));
                    picker.setTextColor(Color.parseColor("#39384c"));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Time time = new Time();
                    time.setToNow();
                    Date date = null;
                    date = sdf.parse(start_time.getText().toString());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int monthDay = calendar.get(Calendar.DATE);
                    //设置时间区间
                    picker.setRangeStart(2009, 1, 1);
                    picker.setRangeEnd(time.year, time.month + 1, time.monthDay);
                    //设置默认显示时间
                    picker.setSelectedItem(year, month + 1, monthDay);
//                //加入动画
//                picker.setAnimationStyle(R.style.Animation_CustomPopup);
                    //回传数据
                    picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                        @Override
                        public void onDatePicked(String year, String month, String day) {
                            String str = year + "-" + month + "-" + day;
                            date_chose_start = str;
                            start_time.setText(str);
                            Drawable drawableRight = getResources().getDrawable(
                                    R.mipmap.icon_bottom);
                            start_time.setCompoundDrawablesWithIntrinsicBounds(null,
                                    null, drawableRight, null);
                            start_time.setCompoundDrawablePadding(MyUtils.dip2px(15));
                            // getEndTime();
                        }
                    });
                    picker.show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                MyUtils.chooseDate(tx_end,getActivity());
                try {
                    Drawable drawableRight = getResources().getDrawable(
                            R.mipmap.icon_up);
                    end_time.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, drawableRight, null);
                    end_time.setCompoundDrawablePadding(MyUtils.dip2px(15));

                    DatePicker picker = new DatePicker(getActivity());
                    picker.setSubmitTextColor(Color.parseColor("#39384c"));
                    picker.setCancelTextColor(Color.parseColor("#39384c"));
                    picker.setCancelText(R.string.mp_add_cancle);
                    picker.setSubmitText(R.string.dg_uploadcomplete_sure);
                    picker.setLabel("", "", "");
                    picker.setHeight(MyUtils.dip2px(250));
                    picker.setTopLineColor(Color.parseColor("#f7f7f8"));
                    picker.setTopLineHeight(2);
                    picker.setPressedTextColor(Color.parseColor("#39384c"));
                    picker.setDividerColor(Color.parseColor("#39384c"));
                    picker.setTextColor(Color.parseColor("#39384c"));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Time time = new Time();
                    time.setToNow();
                    Date date = null;
                    date = sdf.parse(end_time.getText().toString());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int monthDay = calendar.get(Calendar.DATE);
                    //设置时间区间
                    picker.setRangeStart(2009, 1, 1);
                    picker.setRangeEnd(time.year, time.month + 1, time.monthDay);
                    //设置默认显示时间
                    picker.setSelectedItem(year, month + 1, monthDay);
//                //加入动画
//                picker.setAnimationStyle(R.style.Animation_CustomPopup);
                    //回传数据
                    picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                        @Override
                        public void onDatePicked(String year, String month, String day) {
                            String str = year + "-" + month + "-" + day;
                            date_chose_end = str;
                            end_time.setText(str);
                            Drawable drawableRight = getResources().getDrawable(
                                    R.mipmap.icon_bottom);
                            end_time.setCompoundDrawablesWithIntrinsicBounds(null,
                                    null, drawableRight, null);
                            end_time.setCompoundDrawablePadding(MyUtils.dip2px(15));
                        }
                    });
                    picker.show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void initDateChoose() {
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
        time_recycle.setLayoutManager(layoutManager);
        time_recycle.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ((List<ZhanghuPopBean>) adapter.getData()).get(timeType).setChose(false);

                ((List<ZhanghuPopBean>) adapter.getData()).get(position).setChose(true);
                timeType = position;
                adapter.notifyDataSetChanged();

                if(position==0){
                    date_chose_start = MyUtils.getOldDateNoTime(dateToday,-30) + "";
                }else if(position==1){
                    date_chose_start = MyUtils.getOldDateNoTime(dateToday,-90) + "";
                }else if(position==2){
                    date_chose_start = MyUtils.getOldDateNoTime(dateToday,-180) + "";
                }else if(position==3){
                    date_chose_start = MyUtils.getOldDateNoTime(dateToday,-365) + "";
                }else if(position==4){
                    date_chose_start = MyUtils.getOldDateNoTime(dateToday,-(365*3)) + "";
                }else if(position==5){

                }

                date_choode.setText(((List<ZhanghuPopBean>) adapter.getData()).get(position).getName());

                endAnimOut();

                twinklingRefreshLayout.startRefresh();

            }
        });
    }



    private void starAnimEnter() {

        time_choose_layout.setVisibility(View.VISIBLE);
        for (int i = 0; i < time_choose_layout.getChildCount(); i++) {
            time_choose_layout.getChildAt(i).setVisibility(View.VISIBLE);
        }
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(300);
        animation.setFillAfter(true);
        if (time_choose_layout != null)
            time_choose_layout.startAnimation(animation);
    }


    private void endAnimOut() {
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(300);
        animation.setFillAfter(true);
        if (time_choose_layout != null)
            time_choose_layout.startAnimation(animation);
        time_choose_layout.setVisibility(View.GONE);
        for (int i = 0; i < time_choose_layout.getChildCount(); i++) {
            time_choose_layout.getChildAt(i).setVisibility(View.GONE);
        }
    }


    private void getData() {

        if(mPresenter!=null){

            Map map = new HashMap();

            List<String> list = new ArrayList<String>();

            List<String> list_time = new ArrayList<String>();

            List<String> list_sort = new ArrayList<String>();


            list_time.add(date_chose_start);

            list_time.add(date_chose_end);

            list_sort.add(sort);


            for(SpecialAccountBean specialAccountBean:mSpecialAccountBean){

                list.add(specialAccountBean.getAddress().get(0));
            }


            map.put("address",list);

            map.put("time",list_time);

            map.put("sort",list_sort);

            map.put("type",type);



            mPresenter.getAccountTrade(map);
        }


    }

    @Override
    protected void initView() {
        mPresenter = new MineProjectPresenter(this);
        dateToday = new Date();
        date_chose_end = MyUtils.getCurrectDateNoTime() + "";
        date_chose_start = MyUtils.getOldDateNoTime(dateToday,-7) + "";
        start_time.setText(date_chose_start);
        end_time.setText(date_chose_end);

        MyRefreshHeader headerView = new MyRefreshHeader(getContext());
        MyRefreshBottom bottomView = new MyRefreshBottom(getContext());

        twinklingRefreshLayout.setBottomView(bottomView);
        twinklingRefreshLayout.setAutoLoadMore(true);
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setEnableLoadmore(true);

        tradeadapter = new AddressDetailAdapter(params);


        footerView = LayoutInflater.from(getContext()).inflate(R.layout.view_myproject_footer, null);

        foottext=footerView.findViewById(R.id.myproject_footer_add);

        foottext.setText("");

        tradeadapter.addFooterView(footerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(tradeadapter);

        initOnClick();

        initDateChoose();


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

    @Override
    public void onMineProject(TokensAddedResp allProjectCollBean) {

    }

    @Override
    public void onMineAddress(AddressAddedResp resp) {

    }

    @Override
    public void onMineCount(AddressCountResp resp) {

    }

    @Override
    public void onAccountTrade(HashTradeResp resp) {

        twinklingRefreshLayout.finishRefreshing();

        twinklingRefreshLayout.finishLoadmore();

        if(TextUtils.isEmpty(resp.getErrInfo())){

            if(resp.getResult().getHashDetail()!=null){

                if(resp.getResult().getHashDetail().size()!=0){

                    if(isloadMore){
                        tradeadapter.addData(resp.getResult().getHashDetail());
                    }else
                        tradeadapter.setNewData(resp.getResult().getHashDetail());




                    Commons.BASELINE="ETH";

                    sort=resp.getResult().getHashDetail().get(resp.getResult().getHashDetail().size()-1).getSort();

                }


            }

        }

    }

    @Override
    public void onAssetData(AssetResp resp) {

    }

    @Override
    public void onError() {

    }
}
