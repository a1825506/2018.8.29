package com.gikee.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.adapter.Top100TradeAdapter;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.presenter.project.AccountPresenter;
import com.gikee.app.presenter.project.AccountView;
import com.gikee.app.resp.IntroInfoResp;
import com.gikee.app.resp.OwnerDistributeResp;
import com.gikee.app.resp.Top100Resp;
import com.gikee.app.resp.TopFreqAddrResp;
import com.gikee.app.resp.TradeCountDisResp;
import com.gikee.app.resp.TradeVolDisResp;
import com.gikee.app.resp.ValueResp;
import com.gikee.app.views.IconView;
import com.gikee.app.views.MyRefreshHeader;
import com.gikee.app.views.dialogs.InfoDialog;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import java.util.Date;

import butterknife.Bind;

public class Top100TradeActivity extends BaseActivity<AccountPresenter> implements AccountView {


    @Bind(R.id.collect_rg)
    RadioGroup radioGroup;
    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    @Bind(R.id.yestoday)
    RadioButton yestoday_btn;
    @Bind(R.id.lastweek)
    RadioButton lastweek_btn;
    @Bind(R.id.lastmonth)
    RadioButton lastmonth_btn;
    @Bind(R.id.back)
    IconView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.nodata)
    TextView nodata;



    private AccountPresenter mPresenter;


    private String id,symbol,date_chose_big = "2009-07-17";;


    private String choseType="day";

    private Top100TradeAdapter top100TradeAdapter;



    private RecyclerView re_timeinterval;

    private  Date datetoday;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top100);
    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @Override
    protected void setUpView() {

        hideTitleBar();

        mPresenter = new AccountPresenter(this);

        id = getIntent().getStringExtra("id");

        symbol = getIntent().getStringExtra("symbol");

        date_chose_big = MyUtils.getCurrectDate() + "";

        datetoday = new Date();

        date_chose_big ="2018-11-01 00:00:00";//MyUtils.getOldDate(datetoday,-1) + "";

        title.setText(R.string.topFreqAdd_title);

        findViewById(R.id.all_shuju_zhanghu_pop_timedes).setVisibility(View.GONE);

        findViewById(R.id.all_shuju_zhanghu_pop_enddes).setVisibility(View.GONE);
        re_timeinterval = findViewById(R.id.all_shuju_zhanghu_pop_recycler);
        re_timeinterval.setVisibility(View.GONE);



        MyRefreshHeader headerView = new MyRefreshHeader(Top100TradeActivity.this);
        refreshLayout.setAutoLoadMore(false);
        refreshLayout.setHeaderView(headerView);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.startRefresh();
        top100TradeAdapter = new Top100TradeAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(Top100TradeActivity.this));

        recyclerView.setAdapter(top100TradeAdapter);

        lastweek_btn.setTextColor(getResources().getColor(R.color.mineproject));
        lastmonth_btn.setTextColor(getResources().getColor(R.color.mineproject));

        //getData();

    }

    private void getData() {

        mPresenter.Top100Trade(id,date_chose_big,choseType);
    }

    @Override
    protected void initOnclick() {


        top100TradeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent(Top100TradeActivity.this,TradeListActivty.class);

                intent.putExtra("address",top100TradeAdapter.getData().get(position).getAddress());

                intent.putExtra("symbol",symbol);

                intent.putExtra("type",1);

                startActivity(intent);

            }
        });

        top100TradeAdapter.setOnAddressClick(new Top100TradeAdapter.OnAddressClick() {
            @Override
            public void onAddress(String address) {


                Intent intent = new Intent(getApplicationContext(), ETHAddressDetailActivity.class);

                intent.putExtra("address",address);

                intent.putExtra("type","ETH");

                startActivity(intent);

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkid) {

                switch(checkid){
                    case R.id.yestoday:
                        yestoday_btn.setBackgroundResource(R.drawable.sharp_btn_project);
                        yestoday_btn.setTextColor(getResources().getColor(R.color.white));
                        lastweek_btn.setBackgroundResource(R.drawable.shape_btn_nom);
                        lastweek_btn.setTextColor(getResources().getColor(R.color.gray_33));
                        lastmonth_btn.setBackgroundResource(R.drawable.sharp_btn_addressnormal);
                        lastmonth_btn.setTextColor(getResources().getColor(R.color.gray_33));
                      //  date_chose_big =MyUtils.getOldDate(datetoday,-1) + "";
                        choseType="day";
                        refreshLayout.startRefresh();

                        break;
                    case R.id.lastweek:
                        //ToastUtils.show(getString(R.string.noweekdata));
                        yestoday_btn.setBackgroundResource(R.drawable.shape_btn_leftline);
                        yestoday_btn.setTextColor(getResources().getColor(R.color.gray_33));
                        lastweek_btn.setBackgroundResource(R.drawable.shape_btn_press);
                        lastweek_btn.setTextColor(getResources().getColor(R.color.white));
                        lastmonth_btn.setBackgroundResource(R.drawable.sharp_btn_rightline);
                        lastmonth_btn.setTextColor(getResources().getColor(R.color.gray_33));
                        //date_chose_big =MyUtils.getOldDate(datetoday,-1) + "";
                        choseType="week";
                        refreshLayout.startRefresh();
//
                        break;
                    case R.id.lastmonth:
                        // ToastUtils.show(getString(R.string.nomonthdata));
                        yestoday_btn.setBackgroundResource(R.drawable.sharp_btn_project_normal);
                        yestoday_btn.setTextColor(getResources().getColor(R.color.gray_33));
                        lastweek_btn.setBackgroundResource(R.drawable.shape_btn_nom);
                        lastweek_btn.setTextColor(getResources().getColor(R.color.gray_33));
                        lastmonth_btn.setBackgroundResource(R.drawable.sharp_btn_address);
                        lastmonth_btn.setTextColor(getResources().getColor(R.color.white));
                        //date_chose_big =MyUtils.getOldDate(datetoday,-1) + "";
                        choseType="month";
                        refreshLayout.startRefresh();
                        break;


                }
            }
        });

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {

                getData();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                // super.onLoadMore(refreshLayout);
                getData();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadmore();
                    }
                },2000);
            }
        });

    }


    @Override
    public void onValue(ValueResp valueResp) {

    }

    @Override
    public void onTradeVolDis(TradeVolDisResp resp) {

    }

    @Override
    public void onTradeCountDis(TradeCountDisResp resp) {

    }

    @Override
    public void onOwnerDistribute(OwnerDistributeResp resp) {

    }

    @Override
    public void ontop(Top100Resp resp) {

    }

    @Override
    public void TopTrade(TopFreqAddrResp resp) {
        refreshLayout.finishRefreshing();
        top100TradeAdapter.getData().clear();
        if(TextUtils.isEmpty(resp.getErrInfo())){

            if(resp.getResult().getData().size()>0){

                nodata.setVisibility(View.GONE);

                top100TradeAdapter.getData().addAll(resp.getResult().getData());

                top100TradeAdapter.notifyDataSetChanged();
            }else
                nodata.setVisibility(View.VISIBLE);



        }else{
            nodata.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void TopPlayer(TopFreqAddrResp resp) {

    }



    @Override
    public void onntroInfo(IntroInfoResp resp) {

        if(!TextUtils.isEmpty(resp.getResult())){

            new InfoDialog(Top100TradeActivity.this,R.style.dialog,resp.getResult()).setTitle().show();

        }

    }

    @Override
    public void onError() {
        refreshLayout.finishRefreshing();
    }
}
