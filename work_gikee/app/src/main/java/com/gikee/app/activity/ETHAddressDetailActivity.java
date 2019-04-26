package com.gikee.app.activity;

import android.content.ClipData;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
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
import com.gikee.app.fragment.ContractTradeFragment;
import com.gikee.app.fragment.ERC20AddressDetailFragment;
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
import com.gikee.app.views.dialogs.TokenBalanceDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class ETHAddressDetailActivity extends BaseActivity<AddressDetailPresenter> implements AddressDetailView {


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
    @Bind(R.id.total_account_us)
    TextView total_account_us;
    @Bind(R.id.total_account)
    TextView total_account;
    @Bind(R.id.token_more)
    ImageView token_more;
    @Bind(R.id.token_balance)
    TextView token_balance;


    private List<BaseFragment> fragments = new ArrayList<>();

    private String type="ETH";

    private String params="";

    private String paramstype = "address";

    private ClipData myClip;

    private String[] mTitle;

    private List<HashTradeBean> hash_detail;

    private List<TokendBean> tokendBeanList = new ArrayList<>();

    private List<String> title_list = new ArrayList<>();

    private static String totalTrade;

    private String strtoken_balance;

    private String balance;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ethaddressdetail);
    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @Override
    protected void setUpView() {

        mPresenter = new AddressDetailPresenter(this);
        params = getIntent().getStringExtra("address");
        address_id.setText(params);
        Commons.BASELINE = type;
        setBackground(R.color.title_color);
        showRightCollect();
        showTitleBar();
        setTitle(type+" "+getString(R.string.address_datail));
        initViewPage();
    }

    private void initViewPage() {

        fragments.add(ETHAddressDetailFragment.getInstance());

        fragments.add(ERC20AddressDetailFragment.getInstance());

        fragments.add(ContractTradeFragment.getInstance());

        ETHAddressDetailFragment.getInstance().setParams(type,params);

        ERC20AddressDetailFragment.getInstance().setParams(type,params);

        ContractTradeFragment.getInstance().setParams(type,params);

        mTitle =new String[3];

        mTitle[0]=getString(R.string.ethtraderecord);

        mTitle[1]=getString(R.string.erc20traderecord);

        mTitle[2]=getString(R.string.inside_the_contract);


        title_list.add(mTitle[0]);
        title_list.add(mTitle[1]);
        title_list.add(mTitle[2]);


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

        tabslayout.setTitles(baseFragmentPagerAdapter.getTitles());

        tabslayout.setViewPager(viewpager,0);

        ETHAddressDetailFragment.getInstance().setOnUpdateAddress(new ETHAddressDetailFragment.OnUpdateAddress() {
            @Override
            public void updateAddress(String address) {

                reInitViewPage(address);



            }
        });


        ContractTradeFragment.getInstance().setOnUpdateAddress(new ContractTradeFragment.OnUpdateAddress() {
            @Override
            public void updateAddress(String address) {

                reInitViewPage(address);



            }
        });


        ERC20AddressDetailFragment.getInstance().setOnUpdateAddress(new ERC20AddressDetailFragment.OnUpdateAddress() {
            @Override
            public void updateAddress(String address) {

                reInitViewPage(address);



            }
        });



        TradeDetailActivity.setOnUpdateAddress(new TradeDetailActivity.OnUpdateAddress() {
            @Override
            public void updateAddress(String address) {

                reInitViewPage(address);



            }
        });



        if (SQLiteUtils.getInstance().isAddressCollect(params)) {
            //已收藏
            setRightCollect(R.mipmap.collected);
        }

        getData();
    }

    private void reInitViewPage(String address) {


        address_id.setText(address);

        total_account_us.setText("");

        params = address;


        Commons.ETH_ADDRESS.add(address);

        //判断该地址是否收藏
        if (SQLiteUtils.getInstance().isAddressCollect(params)) {
            setRightCollect(R.mipmap.collected);
        }else
            setRightCollect(R.mipmap.collection);



        fragments.clear();

        fragments.add(ETHAddressDetailFragment.getInstance());

        fragments.add(ERC20AddressDetailFragment.getInstance());

        fragments.add(ContractTradeFragment.getInstance());

        ETHAddressDetailFragment.getInstance().setParams(type,params);

        ERC20AddressDetailFragment.getInstance().setParams(type,params);

        ContractTradeFragment.getInstance().setParams(type,params);

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

        getData();

    }


    private void getData() {

        mPresenter.getAddressDetail(type,paramstype,params);

    }

//    public static

    @Override
    protected void initOnclick() {

        token_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TokenBalanceDialog.instance(tokendBeanList.size(),tokendBeanList).show(getSupportFragmentManager(),"sharedialog");
            }
        });


        token_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TokenBalanceDialog.instance(tokendBeanList.size(),tokendBeanList).show(getSupportFragmentManager(),"sharedialog");
            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                TokenBalanceDialog.instance(strtoken_balance,tokendBeanList).show(getSupportFragmentManager(),"sharedialog");

                String text = address_id.getText().toString();
                myClip = ClipData.newPlainText("text", text);
                GikeeApplication.getMyApplicationContext().getMyClipboard().setPrimaryClip(myClip);
                ToastUtils.show(getString(R.string.copied));

            }
        });


        //收藏、取消收藏逻辑
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
//                if (!SQLiteUtils.getInstance().isAddressCollect(params)) {
//                    List<CollectBean> collectBeanList=SQLiteUtils.getInstance().selectAllContacts("address");
//                    CollectBean collectBean = new CollectBean();
//                    collectBean.setName(getString(R.string.my_address)+collectBeanList.size());
//                    collectBean.setAddressid(params);
//                    collectBean.setType("address");
//                    collectBean.setAddress_type("eth");
//                    collectBean.setDetail(params);
//                    collectBean.setBalance(balance);
//                    collectBean.setTag(params);
//                    collectBean.setIscollect(true);
//                    SQLiteUtils.getInstance().addContacts(collectBean);
//                    CollectObserverService.getInstance().notifyDataChanged(ConstObserver.COLLECT_ADDRESS_CHANGE);
//                    ToastUtils.show(getString(R.string.collect_success));
//                    setRightCollect(R.mipmap.collected);
//                } else {
//                    List<CollectBean> collectBeanList=SQLiteUtils.getInstance().getAddressID(params);
//                    CollectBean collectBean = new CollectBean();
//                    collectBean.setAddressid(params);
//                    collectBean.setId(collectBeanList.get(0).getId());
//                    collectBean.setType("address");
//                    collectBean.setTag(params);
//                    collectBean.setIscollect(false);
//                    SQLiteUtils.getInstance().deleteContacts(collectBean);
//                    CollectObserverService.getInstance().notifyDataChanged(ConstObserver.COLLECT_ADDRESS_CHANGE);
//                    setRightCollect(R.mipmap.collection);
//                    ToastUtils.show(getString(R.string.collect_cannel));
//                }
//            }
//        });

    }

    @Override
    public void onAddressDetail(AddressDetailResp addressDetailResp) {

        copy.setVisibility(View.VISIBLE);

        if (addressDetailResp.getResult() != null) {

            AddressDetailBean addressDetailBean = addressDetailResp.getResult();

            hash_detail = addressDetailBean.getHash_detail();

            tokendBeanList.clear();

            reInitView(addressDetailBean);

            if(addressDetailBean.getToken_detail()!=null){

                for(int i=0;i<addressDetailBean.getToken_detail().size();i++){

                    tokendBeanList.add(addressDetailBean.getToken_detail().get(i));
                }
            }

        }

    }

    /**
     * 交易记录总条数获取。分页信息需用到
     */
    public static int getTotalTrade(){

        return Integer.parseInt(totalTrade);

    }


    /**
     *头部信息
     */
    private void reInitView(AddressDetailBean addressDetailBean) {


        // 交易记录总条数
        if(!TextUtils.isEmpty(addressDetailBean.getTotal_trade())){

            if(MyUtils.isNumeric(addressDetailBean.getTotal_trade().replace("txns","").replace(",","").replace(" ",""))){

                totalTrade = addressDetailBean.getTotal_trade().replace("txns","").replace(",","").replace(" ","");

            }else {
                totalTrade =addressDetailBean.getTotal_trade();

            }


        }

        //代币余额处理
        if(!TextUtils.isEmpty(addressDetailBean.getToken_balance())){
            String value = addressDetailBean.getToken_balance();
            if(value.contains("$")){
                value = value.replace("$","");
            }
            if(value.contains(",")){
                value = value.replace(",","");
            }

            Double balance = MyUtils.getUnit()?Double.parseDouble(value):Double.parseDouble(value)*Commons.rate;

            String language = MyUtils.getLocalLanguage();

            String str_value =null;

            if(language=="zh_CN") {
                if (balance >= 100000000) {
                    str_value =  MyUtils.fmtMicrometer(balance / 100000000 + "") + "亿";
                } else if (balance >= 10000) {
                    str_value =  MyUtils.fmtMicrometer(balance / 10000 + "") + "万";
                }else
                    str_value = MyUtils.fmtMicrometer(balance+"");
            }else{
                if(balance>100000){
                    str_value =  MyUtils.fmtMicrometer(balance/100000+"")+"M";
                }else if(balance>=1000){
                    str_value = MyUtils.fmtMicrometer(balance/1000+"")+"K";
                }else
                    str_value = MyUtils.fmtMicrometer(balance+"");
            }

            token_balance.setText(MyUtils.getUnitSymbol()+""+str_value);

            strtoken_balance=addressDetailBean.getToken_balance();
        }



        //资产余额处理(USD或CNY显示)
        if(!TextUtils.isEmpty(addressDetailBean.getBalance_usd())){

            String value = addressDetailBean.getBalance_usd();
            if(value.contains("$")){
                value = value.replace("$","");
            }
            if(value.contains(",")){
                value = value.replace(",","");
            }

            Double balance = MyUtils.getUnit()?Double.parseDouble(value):Double.parseDouble(value)*Commons.rate;

            String language = MyUtils.getLocalLanguage();

            String str_value =null;

            if(language=="zh_CN") {
                if (balance >= 100000000) {
                    str_value =  MyUtils.fmtMicrometer(balance / 100000000 + "") + "亿";
                } else if (balance >= 10000) {
                    str_value =  MyUtils.fmtMicrometer(balance / 10000 + "") + "万";
                }else
                    str_value = MyUtils.fmtMicrometer(balance+"");
            }else{
                if(balance>100000){
                    str_value =  MyUtils.fmtMicrometer(balance/100000+"")+"M";
                }else if(balance>=1000){
                    str_value = MyUtils.fmtMicrometer(balance/1000+"")+"K";
                }else
                    str_value = MyUtils.fmtMicrometer(balance+"");
            }

            total_account.setText(MyUtils.getUnitSymbol()+""+str_value);
        }


        //只能合约图标显示
        if (addressDetailBean.isIs_contract()) {
            contract.setVisibility(View.VISIBLE);
        } else
            contract.setVisibility(View.GONE);


        //资产余额显示
        if (!TextUtils.isEmpty(addressDetailBean.getBalance())) {
            //美元价
            // price.append("≈"+MyUtils.fmtMicrometer(addressDetailBean.getBalance_usd()));
            if (addressDetailBean.getBalance().contains(".")) {
                balance =addressDetailBean.getBalance().substring(0, addressDetailBean.getBalance().indexOf(".") + 3) + "ETH";
                total_account_us.setText(addressDetailBean.getBalance().substring(0, addressDetailBean.getBalance().indexOf(".") + 3) + "ETH");
            } else{
                balance=addressDetailBean.getBalance();
                total_account_us.setText(addressDetailBean.getBalance());
            }

            //
        }


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

    }
}
