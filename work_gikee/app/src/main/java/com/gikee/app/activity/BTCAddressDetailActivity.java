package com.gikee.app.activity;

import android.content.ClipData;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gikee.app.Observer.CollectObserverService;
import com.gikee.app.Observer.base_observe.ConstObserver;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.base.GikeeApplication;
import com.gikee.app.beans.BTCAddressBean;
import com.gikee.app.beans.BTCAddressTradeBean;
import com.gikee.app.beans.TokendBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.fragment.BTCAddressDetailFragment;
import com.gikee.app.fragment.BaseFragment;
import com.gikee.app.fragment.ETHTokenDetailFragment;
import com.gikee.app.greendao.CollectBean;
import com.gikee.app.greendao.SQLiteUtils;
import com.gikee.app.presenter.baseline.AddressDetailPresenter;
import com.gikee.app.presenter.baseline.AddressDetailView;
import com.gikee.app.resp.AddressDetailResp;
import com.gikee.app.resp.BTCAddressReap;
import com.gikee.app.resp.BTCTradeDetailResp;
import com.gikee.app.resp.BTCTradeListResp;
import com.gikee.app.resp.EOSTradeDetailResp;
import com.gikee.app.resp.ERC20ListResp;
import com.gikee.app.resp.HashDetailResp;
import com.gikee.app.resp.HashTradeResp;
import com.gikee.app.views.BaseFragmentPagerAdapter;
import com.gikee.app.views.CustomTablayout;
import com.gikee.app.views.IconView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class BTCAddressDetailActivity extends BaseActivity<AddressDetailPresenter> implements AddressDetailView {


    @Bind(R.id.tabslayout)
    CustomTablayout tabslayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.address_id)
    TextView address_id;
    @Bind(R.id.contract)
    IconView contract;
    @Bind(R.id.copy)
    IconView copy;
    @Bind(R.id.eth_title)
    RelativeLayout eth_title;
    @Bind(R.id.total_account_us)
    TextView total_account_us;
    @Bind(R.id.total_account)
    TextView total_account;
    @Bind(R.id.layout)
    RelativeLayout layout;


    private List<BaseFragment> fragments = new ArrayList<>();

    private String type="ETH";

    private String params="";

    private String paramstype = "address";

    private ClipData myClip;

    private int currectposition=0;

    private String balance;


    private static int totalTrade=0;

    List<BTCAddressTradeBean> btcAddressTradeBeanList = new ArrayList<>();

    private List<TokendBean> tokendBeanList = new ArrayList<>();

    private List<String> title_list = new ArrayList<>();

    private String[] mTitle;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btcaddressdetail);
        mPresenter = new AddressDetailPresenter(this);

    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @Override
    protected void setUpView() {
        layout.setBackgroundResource(R.mipmap.btc_details);
        mPresenter = new AddressDetailPresenter(this);
        showTitleBar();
        showRightCollect();
        setBackground(R.color.title_color);


        type = getIntent().getStringExtra("type");

        setTitle(type+" "+getString(R.string.address_datail));

        Commons.BASELINE=type;

        params = getIntent().getStringExtra("address");

        address_id.setText(params);

        initViewPage();
    }

    private void initViewPage() {

        fragments.add(ETHTokenDetailFragment.getInstance());

        fragments.add(BTCAddressDetailFragment.getInstance());


        BTCAddressDetailFragment.getInstance().setParams(type,params);

        ETHTokenDetailFragment.getInstance().setParams(type,params);


        mTitle =new String[2];//getResources().getStringArray(R.array.specialtrade_title);

        mTitle[1]=getString(R.string.trade_record);

        mTitle[0]=getString(R.string.account_balance);


        title_list.add(mTitle[0]);

        title_list.add(mTitle[1]);

        tabslayout.setTitles(title_list);

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
        viewpager.setAdapter(baseFragmentPagerAdapter);

        viewpager.setOffscreenPageLimit(2);

        //tabslayout.setTitles(baseFragmentPagerAdapter.getTitles());

        tabslayout.setViewPager(viewpager,0);

        BTCAddressDetailFragment.getInstance().setOnUpdateAddress(new BTCAddressDetailFragment.OnUpdateAddress() {
            @Override
            public void updateAddress(String address) {

                address_id.setText(address);

                total_account_us.setText("");

                params = address;
                Commons.ETH_ADDRESS.clear();
                Commons.ETH_ADDRESS.add(address);

                if (SQLiteUtils.getInstance().isAddressCollect(params)) {
                    //已收藏
                   // setRightCollect(R.color.FFAF2C);
                    setRightCollect(R.mipmap.collected);
                }else
                    setRightCollect(R.mipmap.collection);
                   // setRightCollect(R.color.white);


                getData();

            }
        });


        ETHTokenDetailFragment.getInstance().setOnUpdateToken(new ETHTokenDetailFragment.OnUpdateTken() {
            @Override
            public void updateTokes() {

                getData();

            }
        });




        if (SQLiteUtils.getInstance().isAddressCollect(params)) {
            //已收藏
           // setRightCollect(R.color.FFAF2C);
            setRightCollect(R.mipmap.collected);
        }

        getData();
    }




    @Override
    protected void initOnclick() {


        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = address_id.getText().toString();
                myClip = ClipData.newPlainText("text", text);
                GikeeApplication.getMyApplicationContext().getMyClipboard().setPrimaryClip(myClip);
                ToastUtils.show(getString(R.string.copied));

            }
        });
        tabslayout.setTabSelectedListener(new CustomTablayout.TabSelectedListener() {
            @Override
            public void tabClicked(int position) {

                currectposition = position;

            }
        });

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currectposition = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        showRightImg(new IOnclik() {
//            @Override
//            public void OnClickSave() {
//
//            }
//
//            @Override
//            public void OnImgClick(View view) {
//
//                //  mMenu.showAsDropDown(view);
//
//            }
//
//            @Override
//            public void OnImgCollect() {
//
//
//                if (!SQLiteUtils.getInstance().isAddressCollect(params)) {
//                    List<CollectBean> collectBeanList=SQLiteUtils.getInstance().selectAllContacts("address");
//
//                    CollectBean collectBean = new CollectBean();
//                    collectBean.setName("我的地址"+collectBeanList.size());
//                    collectBean.setAddressid(params);
//                    collectBean.setType("address");
//                    collectBean.setAddress_type("btc");
//                    collectBean.setDetail(params);
//                    collectBean.setBalance(balance);
//                    collectBean.setTag(params);
//                    collectBean.setIscollect(true);
//                    SQLiteUtils.getInstance().addContacts(collectBean);
//                    CollectObserverService.getInstance().notifyDataChanged(ConstObserver.COLLECT_ADDRESS_CHANGE);
//                    ToastUtils.show(getString(R.string.collect_success));
//                   // setRightCollect(R.color.FFAF2C);
//                    setRightCollect(R.mipmap.collected);
//                } else {
//
//                    List<CollectBean> collectBeanList=SQLiteUtils.getInstance().getAddressID(params);
//
//                    CollectBean collectBean = new CollectBean();
//                    collectBean.setIscollect(false);
//                    collectBean.setAddressid(params);
//                    collectBean.setId(collectBeanList.get(0).getId());
//                    collectBean.setType("address");
//                    collectBean.setAddress_type("btc");
//                    collectBean.setTag(params);
//                    SQLiteUtils.getInstance().deleteContacts(collectBean);
//                    CollectObserverService.getInstance().notifyDataChanged(ConstObserver.COLLECT_ADDRESS_CHANGE);
//                 //   setRightCollect(R.color.white);
//                    setRightCollect(R.mipmap.collection);
//                    ToastUtils.show(getString(R.string.collect_cannel));
//                }
//
//
//            }
//        });

    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode== ETHAddressDetailFragment.REQUESCODE){
//
//            if(data!=null){
//
//                params = data.getStringExtra("address");
//
//                address_id.setText(params);
//
//                twinklingRefreshLayout.startRefresh();
//
//            }
//        }
//    }

    private void getData() {

        mPresenter.getBTCAddressDetail(type,paramstype,params);
    }

    @Override
    public void onBTCAddressDetail(BTCAddressReap btcAddressReap) {

        copy.setVisibility(View.VISIBLE);

        btcAddressTradeBeanList.clear();

        if (TextUtils.isEmpty(btcAddressReap.getErrInfo())) {

            if (btcAddressReap.getResult() != null) {

                copy.setVisibility(View.VISIBLE);

                BTCAddressBean btcAddressBean = btcAddressReap.getResult();

                btcAddressTradeBeanList = btcAddressBean.getAddress_trade();

                reInitView(btcAddressBean);

                tokendBeanList.clear();

                TokendBean tokendBean = new TokendBean();

                tokendBean.setTitle("BTC");

                tokendBean.setValue(btcAddressBean.getBalance());

                tokendBeanList.add(tokendBean);

                ETHTokenDetailFragment.getInstance().setAdapter(tokendBeanList);

                BTCAddressDetailFragment.getInstance().setAdapter(btcAddressTradeBeanList);

            }else{
                ToastUtils.show(getString(R.string.load_fail));
                BTCAddressDetailFragment.getInstance().setAdapter(btcAddressTradeBeanList);
            }

        } else {
            BTCAddressDetailFragment.getInstance().setAdapter(btcAddressTradeBeanList);
            ToastUtils.show(getString(R.string.nodata));

        }

    }

    @Override
    public void HashDetail(HashDetailResp hashDetailResp) {

    }

    @Override
    public void onEOSAddress(AddressDetailResp eosAddressResp) {

    }


    @Override
    public void onBTCTradeDetail(BTCTradeDetailResp btcTradeDetailResp) {

    }

    @Override
    public void onEOSTradeDetail(EOSTradeDetailResp eosTradeDetailResp) {

    }

    private void reInitView(BTCAddressBean btcAddressBean) {

        if(!TextUtils.isEmpty(btcAddressBean.getBalance())){
            balance = btcAddressBean.getBalance();
            eth_title.setVisibility(View.VISIBLE);
            total_account_us.setText("≈"+balance);


        }

        if(!TextUtils.isEmpty(btcAddressBean.getTrade_count())){

//            title_list.clear();

//            title_list.add(mTitle[0]);
//
//            title_list.add(mTitle[1]+"("+btcAddressBean.getTrade_count()+")");
//
//            tabslayout.setTitles(title_list);

            if(MyUtils.isNumeric(btcAddressBean.getTrade_count().replace(",","").replace(" ",""))){

                totalTrade = Integer.parseInt(btcAddressBean.getTrade_count().replace(",","").replace(" ",""));

            }

        }
    }

    public static int getTotalTrade(){

        return totalTrade;

    }


    @Override
    public void onTradeList(HashTradeResp specialAddressResp) {

    }

    @Override
    public void onAddressTrans(ERC20ListResp resp) {

    }

    @Override
    public void onBTCTradeList(BTCTradeListResp btcTradeListResp) {



    }

    @Override
    public void onError() {

    }


    @Override
    public void onAddressDetail(AddressDetailResp addressDetailResp) {

    }
}
