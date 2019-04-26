package com.gikee.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.R;
import com.gikee.app.adapter.MyAddressAdapter;
import com.gikee.app.base.BaseActivity;
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
import com.gikee.app.views.MyRefreshHeader;
import com.gikee.app.views.PopMenuMore;
import com.gikee.app.views.PopMenuMoreItem;
import com.gikee.app.views.dialogs.AddAccountDialog;
import com.gikee.app.views.dialogs.ChoseUnitDialog;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

public class ExchangeMonitorActivity extends BaseActivity<MineProjectPresenter> implements MineProjectView {

    @Bind(R.id.mineproject_refreshLayout)
    TwinklingRefreshLayout twinklingRefreshLayout;
    @Bind(R.id.mineproject_recyclerview)
    RecyclerView recyclerView;


    private Intent intent;

    private MyAddressAdapter adapter;

    private PopMenuMore mMenu;

    private static final int MY_ACCOUNT = 0;
    private static final int SPECIAL_ACCOUNT = 1;
    private int choosePosition=-1;
    private CollectBean mpCollBean;
    private String symbol;
    private String coin;
    private static List<CollectBean> collectBeanList;



    private int deteleCount=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchangemonitor);
    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @Override
    protected void setUpView() {
        mPresenter = new MineProjectPresenter(this);
        setTitleColor(R.color.black);
        hideRightCollect(View.GONE);
        symbol = getIntent().getStringExtra("symbol");
        coin = getIntent().getStringExtra("coin");
        setTitle(getString(R.string.exchange_account));
        MyRefreshHeader headerView = new MyRefreshHeader(this);
        twinklingRefreshLayout.setAutoLoadMore(false);
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setEnableLoadmore(false);
        adapter = new MyAddressAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        initMenu();
        twinklingRefreshLayout.startRefresh();

    }


    public static List<CollectBean> getCollectBeanList(){

        return collectBeanList;
    }

    @Override
    protected void initOnclick() {
        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                getData();
            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter ad, View view, int position) {

                switch (view.getId()) {
                    case R.id.item_allproject_content:

                        Intent intent = new Intent(ExchangeMonitorActivity.this,AccountDetailActivity.class);

                        startActivity(intent);

                        break;
                    case R.id.more_opera:
                        choosePosition = position;
                        (new ChoseUnitDialog((Activity) ExchangeMonitorActivity.this, ChoseUnitDialog.type_address_exchange)).show();
                        break;
                }



            }
        });

    }



    public void getData() {
        //模拟收藏数据

        collectBeanList = SQLiteUtils.getInstance().selectExchangeAddress("address","exchange"+symbol+coin);

        Map<String,List<String>> map = new HashMap<String,List<String>>();

        List<String> eth_list  = new ArrayList<>();
        List<String> eos_list  = new ArrayList<>();
        List<String> btc_list  = new ArrayList<>();

        for(int i=0;i<collectBeanList.size();i++){

           CollectBean collectBean =  collectBeanList.get(i);

           if("eth".equals(collectBean.getAddress_type())){
               eth_list.add(collectBean.getAddressid());
           }else if("btc".equals(collectBean.getAddress_type())){
               btc_list.add(collectBean.getAddressid());
           }else if("eos".equals(collectBean.getAddress_type())){
               eos_list.add(collectBean.getAddressid());
           }
        }

        if(eth_list.size()>0){

            map.put("eth",eth_list);

        }

        if(btc_list.size()>0){

            map.put("btc",btc_list);

        }

        if(eos_list.size()>0){

            map.put("eos",eos_list);
        }


        mPresenter.getMineAddress(map);


    }


    //根据地址类型跳转不同的页面
    private void junpDetail(String keywords,String type) {

        if("btc".equals(type)){

            intent = new Intent(ExchangeMonitorActivity.this, BTCAddressDetailActivity.class);

            intent.putExtra("type","BTC");

            intent.putExtra("paramstype","address");

            intent.putExtra("address",keywords);

            startActivity(intent);
        }else if("eth".equals(type)){

            intent = new Intent(ExchangeMonitorActivity.this, ETHAddressDetailActivity.class);

            intent.putExtra("type","ETH");

            intent.putExtra("paramstype","address");

            intent.putExtra("address",keywords);

            startActivity(intent);

            Log.e("baseline","ETH地址");

        }else if("eos".equals(type)){

            intent = new Intent(ExchangeMonitorActivity.this, AddressDetailActivity.class);

            intent.putExtra("type","EOS");

            intent.putExtra("paramstype","address");

            intent.putExtra("address",keywords);

            startActivity(intent);
        }

        intent = null;

    }


    private void initMenu() {

        mMenu = new PopMenuMore(ExchangeMonitorActivity.this);
//         mMenu.setCorner(R.mipmap.my_coll);
        mMenu.setBackgroundColor(getResources().getColor(R.color.gray_33));

        ArrayList<PopMenuMoreItem> items = new ArrayList<>();
        items.add(new PopMenuMoreItem(MY_ACCOUNT,getString(R.string.title_address)));
        items.add(new PopMenuMoreItem(SPECIAL_ACCOUNT,getString(R.string.specialtrade)));
        mMenu.addItems(items);
        mMenu.setOnItemSelectedListener(new PopMenuMore.OnItemSelectedListener() {
            @Override
            public void selected(View view, PopMenuMoreItem item, int position) {
                switch (item.id) {
                    case MY_ACCOUNT:
                        new AddAccountDialog(ExchangeMonitorActivity.this,R.style.dialog,null).getInstance().show();
                        break;

                    case SPECIAL_ACCOUNT:

                        Intent intent = new Intent(ExchangeMonitorActivity.this,SpecialAccountSearchActivity.class);

                        startActivity(intent);

                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy(); }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeLanguageEvent(OnChangeLanguageEvent event){

        if(event.languageType== LanguageType.ADDRESS_TOP_EXCHANGE){

            String address=adapter.getData().get(choosePosition).getAddressid();
            List<CollectBean> collectBeanList=SQLiteUtils.getInstance().getAddressID(address);
            CollectBean collectBean = new CollectBean();
            collectBean.setName(adapter.getData().get(choosePosition).getName());
            collectBean.setType("address");
            collectBean.setId(collectBeanList.get(0).getId());
            SQLiteUtils.getInstance().deleteContacts(collectBean);

            //删除 合并监控 里面的记录


            List<CollectBean> collectlist=SQLiteUtils.getInstance().getAddressID("exchange"+symbol+coin);
            CollectBean collect_Bean = collectlist.get(0);
            if(Integer.parseInt(collect_Bean.getCount())==1){
                SQLiteUtils.getInstance().deleteContacts(collect_Bean);
            }else{
                collect_Bean.setCount(""+(Integer.parseInt(collect_Bean.getCount())-1));
                SQLiteUtils.getInstance().updateContacts(collect_Bean);
            }

            adapter.remove(choosePosition);

            adapter.notifyDataSetChanged();

        }else if(event.languageType==LanguageType.ADDRESS_TOP_EXCHANGE){

            if(choosePosition!=0){

                mpCollBean = adapter.getData().get(choosePosition);

                List<CollectBean> collectBeanList=SQLiteUtils.getInstance().getAddressID(mpCollBean.getAddressid());

                List<CollectBean> collectBeanList1=SQLiteUtils.getInstance().getAddressID(adapter.getData().get(0).getAddressid());

                CollectBean collectBean = new CollectBean();
                collectBean.setId(collectBeanList.get(0).getId());
                collectBean.setTrandnum(0);
                collectBean.setType("address");
                collectBean.setAddressid(mpCollBean.getAddressid());
                collectBean.setName(mpCollBean.getName());
                collectBean.setBalance(mpCollBean.getBalance());
                collectBean.setAddress_type(mpCollBean.getAddress_type());
                collectBean.setTag(mpCollBean.getTag());
                collectBean.setLogo(mpCollBean.getLogo());


                CollectBean collectBean1 = new CollectBean();
                collectBean1.setId(collectBeanList1.get(0).getId());
                collectBean1.setTrandnum(choosePosition);
                collectBean1.setType("address");
                collectBean1.setAddressid(adapter.getData().get(0).getAddressid());
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

        }else if(event.languageType==LanguageType.ADDRESS_EDIT_EXCHANGE){

            mpCollBean = adapter.getData().get(choosePosition);

            new AddAccountDialog(ExchangeMonitorActivity.this,R.style.dialog,mpCollBean).getInstance().show();

        }

    }

    @Override
    public void onMineProject(TokensAddedResp allProjectCollBean) {

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

                                collectBean.setBalance(String.valueOf(addressAddedBean.getBalance()));

                            }

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

    @Override
    public void onAssetData(AssetResp resp) {

    }

    @Override
    public void onError() {

    }
}
