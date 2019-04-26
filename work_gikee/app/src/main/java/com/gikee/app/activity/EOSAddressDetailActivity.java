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

import com.gikee.app.R;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.beans.AddressDetailBean;
import com.gikee.app.beans.HashTradeBean;
import com.gikee.app.beans.TokendBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.fragment.BaseFragment;
import com.gikee.app.fragment.ETHAddressDetailFragment;
import com.gikee.app.fragment.ETHTokenDetailFragment;
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

public class EOSAddressDetailActivity extends BaseActivity<AddressDetailPresenter> implements AddressDetailView {


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


    private List<BaseFragment> fragments = new ArrayList<>();

    private ClipData myClip;

    List<HashTradeBean> hash_detail;

    private List<TokendBean> tokendBeanList = new ArrayList<>();



    private String type="EOS";

    private String params="gq3tqnjzgage";

    private String paramstype = "address";

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addressdetail);
        mPresenter = new AddressDetailPresenter(this);
    }

    @Override
    protected void onChangeEvent(int type) {

    }


    @Override
    protected void setUpView() {

        mPresenter = new AddressDetailPresenter(this);
        showTitleBar();
        setTitle("EOS "+getString(R.string.address_datail));
        setBackground(R.color.title_color);
        params = getIntent().getStringExtra("address");

        address_id.setText(params);

        initViewPage();
    }


    private void initViewPage() {

        fragments.add(ETHTokenDetailFragment.getInstance());

        fragments.add(ETHAddressDetailFragment.getInstance());


       // ETHAddressDetailFragment.getInstance().setParams(params);

      //  ETHTokenDetailFragment.getInstance().setParams(params);


        final String[] mTitle =new String[2];//getResources().getStringArray(R.array.specialtrade_title);

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
                  //  setRightCollect(R.color.FFAF2C);
                    setRightCollect(R.mipmap.collected);
                }else
                    setRightCollect(R.mipmap.collection);
                    //setRightCollect(R.color.white);

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



    }

    private void getData() {

        mPresenter.getEOSAddressDetail(type,paramstype,params);

    }


    @Override
    public void onEOSAddress(AddressDetailResp eosAddressResp) {


        copy.setVisibility(View.VISIBLE);
        if (eosAddressResp.getResult() != null) {

            AddressDetailBean addressDetailBean = eosAddressResp.getResult();

            hash_detail = addressDetailBean.getHash_detail();


            tokendBeanList.clear();

            TokendBean tokendBean = new TokendBean();

            tokendBean.setTitle("ETH");

            if(addressDetailBean.getBalance().contains("Ether")){
                tokendBean.setValue(addressDetailBean.getBalance().replace("Ether","ETH"));
            }else
                tokendBean.setValue(addressDetailBean.getBalance());


            tokendBeanList.add(tokendBean);

            if(addressDetailBean.getToken_detail()!=null){
                for(int i=0;i<addressDetailBean.getToken_detail().size();i++){
                    tokendBeanList.add(addressDetailBean.getToken_detail().get(i));
                }
            }

          //  ETHAddressDetailFragment.getInstance().setAdapter(hash_detail);

            ETHTokenDetailFragment.getInstance().setAdapter(tokendBeanList);

            if(addressDetailBean.isIs_contract()){
                contract.setVisibility(View.VISIBLE);
            }else
                contract.setVisibility(View.GONE);

            if(!TextUtils.isEmpty(addressDetailBean.getBalance_usd())||!TextUtils.isEmpty(addressDetailBean.getToken_balance())){
                eth_title.setVisibility(View.VISIBLE);
                if(!TextUtils.isEmpty(addressDetailBean.getBalance_usd())){
                    total_account_us.setText("≈"+addressDetailBean.getBalance_usd());
                }
            }

        }



    }

    @Override
    public void onBTCTradeDetail(BTCTradeDetailResp btcTradeDetailResp) {

    }

    @Override
    public void onEOSTradeDetail(EOSTradeDetailResp eosTradeDetailResp) {

    }

    @Override
    public void onAddressDetail(AddressDetailResp addressDetailResp) {

    }

    @Override
    public void onTradeList(HashTradeResp specialAddressResp) {

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
    public void onError() {

    }
}
