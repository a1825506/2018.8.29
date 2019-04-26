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
import com.gikee.app.beans.AddressDetailBean;
import com.gikee.app.beans.HashTradeBean;
import com.gikee.app.beans.TokendBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.fragment.BaseFragment;
import com.gikee.app.fragment.ETHAddressDetailFragment;
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


/**
 * 地址详情页面 爬虫浏览器
 */
public class AddressDetailActivity extends BaseActivity<AddressDetailPresenter> implements AddressDetailView {


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

    List<HashTradeBean> hash_detail;

    private List<TokendBean> tokendBeanList = new ArrayList<>();

    private List<String> title_list = new ArrayList<>();

    private String[] mTitle;

    private static int totalTrade=0;

    private String balance;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addressdetail);

    }

    @Override
    protected void onChangeEvent(int type) {

    }


    @Override
    protected void setUpView() {
        mPresenter = new AddressDetailPresenter(this);
        setBackground(R.color.title_color);
        showTitleBar();
        showRightCollect();
        type = getIntent().getStringExtra("type");

        Commons.BASELINE = type;
        setTitle(type+" "+getString(R.string.address_datail));

        if(!TextUtils.isEmpty(type)){
            if(type.equals("ETH")){
                layout.setBackground(getResources().getDrawable(R.mipmap.eth_details));
            }else if(type.equals("EOS")){
                layout.setBackground(getResources().getDrawable(R.mipmap.eos_details));
            }
        }else
            layout.setBackground(getResources().getDrawable(R.mipmap.eos_details));


        Commons.BASELINE=type;

        params = getIntent().getStringExtra("address");

        address_id.setText(params);

        initViewPage();

    }

    private void initViewPage() {

        fragments.add(ETHTokenDetailFragment.getInstance());

        fragments.add(ETHAddressDetailFragment.getInstance());


        ETHAddressDetailFragment.getInstance().setParams(type,params);

        ETHTokenDetailFragment.getInstance().setParams(type,params);

        mTitle =new String[2];//getResources().getStringArray(R.array.specialtrade_title);

        mTitle[1]=getString(R.string.trade_record);

        mTitle[0]=getString(R.string.account_balance);

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

        tabslayout.setTitles(baseFragmentPagerAdapter.getTitles());

        tabslayout.setViewPager(viewpager,0);

        ETHAddressDetailFragment.getInstance().setOnUpdateAddress(new ETHAddressDetailFragment.OnUpdateAddress() {
            @Override
            public void updateAddress(String address) {

                address_id.setText(address);

                total_account_us.setText("");

                params = address;
                Commons.ETH_ADDRESS.clear();
                Commons.ETH_ADDRESS.add(address);

                if (SQLiteUtils.getInstance().isAddressCollect(params)) {
                    //已收藏
                    setRightCollect(R.mipmap.collected);
                }else
                    setRightCollect(R.mipmap.collection);

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
//                    CollectBean collectBean = new CollectBean();
//                    collectBean.setName(getString(R.string.my_address)+collectBeanList.size());
//                    collectBean.setAddressid(params);
//                    collectBean.setType("address");
//                    collectBean.setAddress_type("eos");
//                    collectBean.setDetail(params);
//                    collectBean.setBalance(balance);
//                    collectBean.setTag(params);
//                    collectBean.setIscollect(true);
//                    SQLiteUtils.getInstance().addContacts(collectBean);
//                    CollectObserverService.getInstance().notifyDataChanged(ConstObserver.COLLECT_ADDRESS_CHANGE);
//                    ToastUtils.show(getString(R.string.collect_success));
//
//                    setRightCollect(R.mipmap.collected);
//                } else {
//
//                    List<CollectBean> collectBeanList=SQLiteUtils.getInstance().getAddressID(params);
//
//                    CollectBean collectBean = new CollectBean();
//
//                    collectBean.setAddressid(params);
//                    collectBean.setId(collectBeanList.get(0).getId());
//                    collectBean.setType("address");
//                    collectBean.setTag(params);
//                    collectBean.setIscollect(false);
//                    SQLiteUtils.getInstance().deleteContacts(collectBean);
//                    CollectObserverService.getInstance().notifyDataChanged(ConstObserver.COLLECT_ADDRESS_CHANGE);
//                   // setRightCollect(R.color.white);
//                    setRightCollect(R.mipmap.collection);
//                    ToastUtils.show(getString(R.string.collect_cannel));
//                }
//
//
//            }
//        });

    }


    private void getData() {

        mPresenter.getAddressDetail(type,paramstype,params);

    }

    @Override
    public void onAddressDetail(AddressDetailResp addressDetailResp) {
        // mPresenter.getTradeList(type,params,1);
      //  if (TextUtils.isEmpty(addressDetailResp.getErrInfo())) {

        copy.setVisibility(View.VISIBLE);
            if (addressDetailResp.getResult() != null) {

                AddressDetailBean addressDetailBean = addressDetailResp.getResult();

                hash_detail = addressDetailBean.getHash_detail();


                tokendBeanList.clear();

                reInitView(addressDetailBean);

                    TokendBean tokendBean = new TokendBean();

                    if(type.equals(Commons.ETH)){
                        tokendBean.setTitle("ETH");

                        if(!TextUtils.isEmpty(addressDetailBean.getBalance())){
                            if(addressDetailBean.getBalance().contains("Ether")){
                                tokendBean.setValue(addressDetailBean.getBalance().replace("Ether","ETH"));
                            }else
                                tokendBean.setValue(addressDetailBean.getBalance());
                        }



                    }else{

                        tokendBean.setTitle("EOS");

                        if(!TextUtils.isEmpty(addressDetailBean.getBalance())){

                            if(addressDetailBean.getBalance().contains("Ether")){
                                tokendBean.setValue(addressDetailBean.getBalance().replace("Ether","ETH"));
                            }else
                                tokendBean.setValue(addressDetailBean.getBalance());
                        }


                    }

                    tokendBeanList.add(tokendBean);

                    if(addressDetailBean.getToken_detail()!=null){
                        for(int i=0;i<addressDetailBean.getToken_detail().size();i++){
                            tokendBeanList.add(addressDetailBean.getToken_detail().get(i));
                        }
                    }



              //  ETHAddressDetailFragment.getInstance().setAdapter(hash_detail);

                ETHTokenDetailFragment.getInstance().setAdapter(tokendBeanList);


            }

    }

    public static int getTotalTrade(){

        return totalTrade;

    }

    private void reInitView(AddressDetailBean addressDetailBean) {

        if(!TextUtils.isEmpty(addressDetailBean.getTotal_trade())){

            title_list.clear();

            title_list.add(mTitle[0]);

            title_list.add(mTitle[1]+"("+addressDetailBean.getTotal_trade().replace("txns","")+")");

            tabslayout.setTitles(title_list);

            if(MyUtils.isNumeric(addressDetailBean.getTotal_trade().replace("txns","").replace(",","").replace(" ",""))){

                totalTrade = Integer.parseInt(addressDetailBean.getTotal_trade().replace("txns","").replace(",","").replace(" ",""));

            }
        }

        if(addressDetailBean.isIs_contract()){
            contract.setVisibility(View.VISIBLE);
        }else
            contract.setVisibility(View.GONE);

        if(!TextUtils.isEmpty(addressDetailBean.getBalance_usd())||!TextUtils.isEmpty(addressDetailBean.getToken_balance())||!TextUtils.isEmpty(addressDetailBean.getBalance())){
            eth_title.setVisibility(View.VISIBLE);

            if(!TextUtils.isEmpty(addressDetailBean.getBalance_usd())){
                //美元价
                // price.append("≈"+MyUtils.fmtMicrometer(addressDetailBean.getBalance_usd()));
                total_account_us.setText("≈"+addressDetailBean.getBalance_usd());
                //
            }
            if(type.equals("EOS")){
                if(!TextUtils.isEmpty(addressDetailBean.getBalance())){
                    //

                    balance =addressDetailBean.getBalance()+type;

                    total_account_us.setText(MyUtils.fmtMicrometer1(addressDetailBean.getBalance())+type);


                    //   price.append("("+ MyUtils.fmtMicrometer(addressDetailBean.getBalance())+")");
                }
            }


        }
    }


    @Override
    public void onTradeList(HashTradeResp specialAddressBean) {


    }

    @Override
    public void onAddressTrans(ERC20ListResp resp) {

    }

    @Override
    public void onBTCTradeList(BTCTradeListResp resp) {

    }

    @Override
    public void onBTCAddressDetail(BTCAddressReap btcAddressReap) {

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

    @Override
    public void onError() {

       // twinklingRefreshLayout.finishRefreshing();

    }




}
