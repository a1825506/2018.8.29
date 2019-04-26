package com.gikee.app.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.Observer.CollectObserverService;
import com.gikee.app.Observer.base_observe.ConstObserver;
import com.gikee.app.Observer.base_observe.IObserver;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.adapter.MyAddressAdapter;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.base.GikeeApplication;
import com.gikee.app.beans.AddressAddedBean;
import com.gikee.app.greendao.CollectBean;
import com.gikee.app.greendao.SQLiteUtils;
import com.gikee.app.language.LanguageType;
import com.gikee.app.language.OnChangeLanguageEvent;
import com.gikee.app.presenter.project.MineProjectPresenter;
import com.gikee.app.presenter.project.MineProjectView;
import com.gikee.app.resp.AddressAddedResp;
import com.gikee.app.resp.AddressCountResp;
import com.gikee.app.resp.AssetResp;
import com.gikee.app.resp.HashTradeResp;
import com.gikee.app.resp.TokensAddedResp;
import com.gikee.app.views.IconView;
import com.gikee.app.views.MyRefreshHeader;
import com.gikee.app.views.dialogs.AddAccountDialog;
import com.gikee.app.views.dialogs.ChoseUnitDialog;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MineAddressActivity extends BaseActivity<MineProjectPresenter> implements MineProjectView,IObserver {

    TwinklingRefreshLayout twinklingRefreshLayout;
    RecyclerView recyclerView;

    LinearLayout address_add ,my_account,special_account;

    RelativeLayout popView,layout;

    FrameLayout framelayout_layout;

    IconView back;


    private Context mcontext;
    private MyAddressAdapter adapter;
    private  View footerView;
    public static int REQUESCODE2 = 402;
    private int choosePosition=-1;
    private CollectBean mpCollBean;
    private List<CollectBean> collectBeanList;
    private static List<CollectBean> exchangecollectBeanList;
    private   Map<String,List<String>> map;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mineaddress);
    }

    @Override
    protected void onChangeEvent(int type) {

    }


    @Override
    protected void setUpView() {

        mcontext = MineAddressActivity.this;
        mPresenter = new MineProjectPresenter(this);
        hideTitleBar();


        CollectObserverService.getInstance().registerObserver(this);

        MyRefreshHeader headerView = new MyRefreshHeader(mcontext);
        twinklingRefreshLayout = findViewById(R.id.mineproject_refreshLayout);
        twinklingRefreshLayout.setAutoLoadMore(false);
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setEnableLoadmore(false);
        adapter = new MyAddressAdapter();
        recyclerView = findViewById(R.id.mineproject_recyclerview);
        address_add = findViewById(R.id.mineproject_search);
        popView  = findViewById(R.id.popView);
        layout = findViewById(R.id.layout);
        my_account  = findViewById(R.id.my_account);
        special_account  = findViewById(R.id.special_account);
        back = findViewById(R.id.back);
        framelayout_layout = findViewById(R.id.framelayout_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(mcontext));
        recyclerView.setAdapter(adapter);

        initMenu();
        footerView = LayoutInflater.from(mcontext).inflate(R.layout.view_nocontent, null);
        ((footerView.findViewById(R.id.nocontent_repeat))).setVisibility(View.GONE);
        adapter.addFooterView(footerView);


        initOnclick();
        twinklingRefreshLayout.startRefresh();

    }




    public static List<CollectBean> getCollectBeanList(){

        return exchangecollectBeanList;
    }


    public void initOnclick() {


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });



        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                getData();
            }
        });


        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter ad, View view, int position) {
                popView.setVisibility(View.GONE);
                switch (view.getId()) {
                    case R.id.item_allproject_content:

                        if(adapter.getData().get(position).getAddressid().contains("exchange")){
                            //交易所账户监听。跳转具体账户监听页面

                            exchangecollectBeanList = SQLiteUtils.getInstance().getAddressID(adapter.getData().get(position).getAddressid());

                            Intent intent = new Intent(mcontext,AccountDetailActivity.class);

                            intent.putExtra("type","exchange");

                            intent.putExtra("symbol",adapter.getData().get(position).getAddress_type());

                            intent.putExtra("balance",adapter.getData().get(position).getBalance());

                            startActivity(intent);
                        }else{

                            exchangecollectBeanList=SQLiteUtils.getInstance().getAddressID(adapter.getData().get(position).getAddressid());

                            Intent intent = new Intent(mcontext,AccountDetailActivity.class);

                            intent.putExtra("type","account");//标记是交易所还是单独账户

                            intent.putExtra("balance",adapter.getData().get(position).getBalance());

                            intent.putExtra("symbol",adapter.getData().get(position).getAddress_type());


                            startActivity(intent);

                        }

                        CollectBean collectBean =adapter.getData().get(position);

                        collectBean.setCollect_time(MyUtils.getRemindTime());

                        collectBean.setTrade_count(0);

                        SQLiteUtils.getInstance().updateContacts(collectBean);

                        adapter.notifyItemChanged(position);

                        break;
                    case R.id.more_opera:
                        choosePosition = position;
                        (new ChoseUnitDialog((Activity) mcontext, ChoseUnitDialog.type_address)).show();
                        break;
                    case R.id.copy:
                        String text =  adapter.getData().get(position).getAddressid();
                        ClipData  myClip = ClipData.newPlainText("text", text);
                        GikeeApplication.getMyApplicationContext().getMyClipboard().setPrimaryClip(myClip);
                        ToastUtils.show(getString(R.string.copied));
                        break;
                }



            }
        });

        address_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(popView.getVisibility()==View.GONE){
                    popView.setVisibility(View.VISIBLE);
                }else
                    popView.setVisibility(View.GONE);


            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popView.setVisibility(View.GONE);
            }
        });

        framelayout_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popView.setVisibility(View.GONE);
            }
        });

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popView.setVisibility(View.GONE);
            }
        });

        twinklingRefreshLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popView.setVisibility(View.GONE);
            }
        });

    }


    private void initMenu() {

        my_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddAccountDialog(mcontext,R.style.dialog,null).getInstance().show();
                popView.setVisibility(View.GONE);
            }
        });

        special_account.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext,SpecialAccountSearchActivity.class);

                startActivity(intent);

                popView.setVisibility(View.GONE);
            }
        });

    }


    public void getData() {
        //模拟收藏数据
        twinklingRefreshLayout.finishRefreshing();
        collectBeanList = SQLiteUtils.getInstance().selectContacts("address","exchange");
        Collections.reverse(collectBeanList);
        adapter.setNewData(collectBeanList);
        if(collectBeanList.size()==0){
            (footerView.findViewById(R.id.nocontent_img)).setVisibility(View.VISIBLE);
            (footerView.findViewById(R.id.nocontent_tx)).setVisibility(View.VISIBLE);
            (footerView.findViewById(R.id.nocontenttip_tx)).setVisibility(View.VISIBLE);
            ((TextView)(footerView.findViewById(R.id.nocontent_tx))).setText(getString(R.string.add_acount));

        }else{
            (footerView.findViewById(R.id.nocontent_img)).setVisibility(View.GONE);
            (footerView.findViewById(R.id.nocontent_tx)).setVisibility(View.GONE);
            (footerView.findViewById(R.id.nocontenttip_tx)).setVisibility(View.GONE);


        }

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUESCODE2){
            twinklingRefreshLayout.startRefresh();
        }
    }


    @Override
    public void onMineProject(TokensAddedResp items) {

    }

    @Override
    public void onMineAddress(AddressAddedResp resp) {

        twinklingRefreshLayout.finishRefreshing();

        if(TextUtils.isEmpty(resp.getErrInfo())){

            if(resp.getData()!=null){

                if(resp.getData().size()!=0){

                    List<AddressAddedBean> addressAddedBeanList = resp.getData();

                    for(CollectBean collectBean:collectBeanList){

                        for(AddressAddedBean addressAddedBean:addressAddedBeanList){

                            if(collectBean.getAddressid().equals(addressAddedBean.getAddress())){

                                if("eth".equals(collectBean.getAddress_type())){

                                    if(collectBean.getAddressid().contains("0x")){

                                        collectBean.setBalance(String.valueOf(addressAddedBean.getBalance()));

                                    }

                                }

                                if("btc".equals(collectBean.getAddress_type())){

                                    if(collectBean.getAddressid().length()==34){

                                        collectBean.setBalance(String.valueOf(addressAddedBean.getBalance()));

                                    }

                                }



                            }

                        }
                        if(TextUtils.isEmpty(collectBean.getBalance())){

                            collectBean.setBalance(getString(R.string.check_address_));
                        }

                    }



                }

            }

        }

        adapter.setNewData(collectBeanList);

    }

    @Override
    public void onMineCount(AddressCountResp resp) {

    }

    @Override
    public void onAccountTrade(HashTradeResp resp) {

    }

    /**
     *获取资产数据
     */
    @Override
    public void onAssetData(AssetResp resp) {



    }

    @Override
    public void onError() {

    }

    @Override
    public void onChange(Object o, int actionCode, int requestCode) {

        if(requestCode== ConstObserver.COLLECT_ADDRESS_CHANGE){
            //收藏成功或者取消，刷新数据源
            getData();
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeLanguageEvent(OnChangeLanguageEvent event){

        if(event.languageType== LanguageType.ADDRESS_DELETE){

            String address=adapter.getData().get(choosePosition).getAddressid();

            String address_type=adapter.getData().get(choosePosition).getAddress_type();

            List<CollectBean> collectBeanList=SQLiteUtils.getInstance().getAddressID(address);
            for(CollectBean collectBean:collectBeanList){
                if(collectBean.getAddress_type().equals(address_type)){
                    SQLiteUtils.getInstance().deleteContacts(collectBean);
                }

            }

            if(address.contains("exchange")){

                String tag = adapter.getData().get(choosePosition).getDetail();

                List<CollectBean> list1 = SQLiteUtils.getInstance().selectExchangeAddress("address",tag);

                for(CollectBean collectBean:list1){
                    SQLiteUtils.getInstance().deleteContacts(collectBean);
                }


            }



            adapter.remove(choosePosition);

            adapter.notifyDataSetChanged();

            if(adapter.getData().size()==0){

                twinklingRefreshLayout.startRefresh();

            }

        }else if(event.languageType==LanguageType.ADDRESS_TOP){

            if(choosePosition!=0){

                mpCollBean = adapter.getData().get(choosePosition);

                List<CollectBean> collectBeanList=SQLiteUtils.getInstance().getAddressID(mpCollBean.getAddressid());

                List<CollectBean> collectBeanList1=SQLiteUtils.getInstance().getAddressID(adapter.getData().get(0).getAddressid());

                CollectBean collectBean = new CollectBean();
                collectBean.setId(collectBeanList1.get(0).getId());
                collectBean.setTrandnum(0);
                collectBean.setType("address");
                collectBean.setAddressid(mpCollBean.getAddressid());
                collectBean.setName(mpCollBean.getName());
                collectBean.setCount(mpCollBean.getCount());
                collectBean.setDetail(mpCollBean.getDetail());
                collectBean.setBalance(mpCollBean.getBalance());
                collectBean.setAddress_type(mpCollBean.getAddress_type());
                collectBean.setTag(mpCollBean.getTag());
                collectBean.setLogo(mpCollBean.getLogo());


                CollectBean collectBean1 = new CollectBean();
                collectBean1.setId(collectBeanList.get(0).getId());
                collectBean1.setTrandnum(choosePosition);
                collectBean1.setType("address");
                collectBean1.setDetail(adapter.getData().get(0).getDetail());
                collectBean1.setAddressid(adapter.getData().get(0).getAddressid());
                collectBean1.setCount(adapter.getData().get(0).getCount());
                collectBean1.setTag(adapter.getData().get(0).getTag());
                collectBean1.setName(adapter.getData().get(0).getName());
                collectBean1.setLogo(adapter.getData().get(0).getLogo());
                collectBean1.setBalance(adapter.getData().get(0).getBalance());
                collectBean1.setAddress_type(adapter.getData().get(0).getAddress_type());


                adapter.getData().remove(mpCollBean);
                adapter.getData().add(0, mpCollBean);
                adapter.notifyDataSetChanged();

                SQLiteUtils.getInstance().updateContacts(collectBean);

                SQLiteUtils.getInstance().updateContacts(collectBean1);

                mpCollBean = null;
            }

        }else if(event.languageType==LanguageType.ADDRESS_EDIT){

            mpCollBean = adapter.getData().get(choosePosition);

            new AddAccountDialog(mcontext,R.style.dialog,mpCollBean).getInstance().show();

        }else if(event.languageType==LanguageType.UNIT_CNY||event.languageType==LanguageType.UNIT_USD){
        }else if(event.languageType==LanguageType.GET_MONITOR_TRADE){
            getData();

        }

    }


}
