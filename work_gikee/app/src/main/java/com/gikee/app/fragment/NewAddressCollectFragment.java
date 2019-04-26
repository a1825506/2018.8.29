package com.gikee.app.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.Observer.CollectObserverService;
import com.gikee.app.Observer.base_observe.ConstObserver;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.activity.ExchangeMonitorActivity;
import com.gikee.app.activity.MineAddressActivity;
import com.gikee.app.activity.SpecialAccountSearchActivity;
import com.gikee.app.adapter.SpecialAccountAdapter;
import com.gikee.app.beans.SpecialAccountBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.greendao.CollectBean;
import com.gikee.app.greendao.SQLiteUtils;
import com.gikee.app.language.LanguageType;
import com.gikee.app.language.OnChangeLanguageEvent;
import com.gikee.app.presenter.project.MineProjectPresenter;
import com.gikee.app.presenter.search.SpecialSearchPresenter;
import com.gikee.app.presenter.search.SpecialSearchView;
import com.gikee.app.resp.AddressAddedResp;
import com.gikee.app.resp.MonitorTradeResp;
import com.gikee.app.resp.SpecialAccountResp;

import com.gikee.app.type.ShowType;
import com.gikee.app.views.EditTextWithDel;
import com.gikee.app.views.MyRefreshBottom;
import com.gikee.app.views.MyRefreshHeader;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;

@SuppressLint("ValidFragment")
public class NewAddressCollectFragment extends BaseFragment<SpecialSearchPresenter> implements SpecialSearchView {

    EditTextWithDel search_searchtx;

    RecyclerView all_account_recycle;

    TwinklingRefreshLayout refreshLayout;

    TextView collect_count,monitor_account;

    RelativeLayout my_monitor_layout,search_context_layout;

    private SpecialAccountAdapter specialAccountAdapter;

    private Context mcontext;

    private View view;

    private int page=1;

    private boolean isLoadmore=false;

    private int count=0;

    @SuppressLint("ValidFragment")
    public NewAddressCollectFragment(Context context) {
        this.mcontext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPresenter = new SpecialSearchPresenter(this);
        EventBus.getDefault().register(this);
        view = LayoutInflater.from(mcontext).inflate(R.layout.fragment_newaddress_collect, container,false);
        search_searchtx = view.findViewById(R.id.search_searchtx);
        all_account_recycle = view.findViewById(R.id.all_account_recycle);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        collect_count = view.findViewById(R.id.collect_count);
        my_monitor_layout = view.findViewById(R.id.my_monitor_layout);
        search_context_layout = view.findViewById(R.id.search_context_layout);
        monitor_account=view.findViewById(R.id.monitor_account);

        MyRefreshHeader headerView = new MyRefreshHeader(getContext());
        MyRefreshBottom bottomView = new MyRefreshBottom(getContext());

        refreshLayout.setAutoLoadMore(true);
        refreshLayout.setHeaderView(headerView);
        refreshLayout.setEnableLoadmore(true);
        refreshLayout.setBottomView(bottomView);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) search_searchtx.getLayoutParams();

        params.width = (int)(MyUtils.getWidth()*(0.72));

        search_searchtx.setLayoutParams(params);

        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);

        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.line_gray));

        specialAccountAdapter = new SpecialAccountAdapter();

        all_account_recycle.addItemDecoration(divider);

        all_account_recycle.setLayoutManager(new LinearLayoutManager(getContext()));

        all_account_recycle.setAdapter(specialAccountAdapter);



        refreshLayout.startRefresh();

        onClick();

        collect_count.setVisibility(View.GONE);

        String count = String.valueOf(SQLiteUtils.getInstance().selectContacts("address","exchange").size());

        String value =String.format(mcontext.getString(R.string.monitor_address),count);

        monitor_account.setText(value);

        return view;
    }

    private void onClick() {

        search_searchtx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(),SpecialAccountSearchActivity.class);

                startActivity(intent);
            }
        });

        my_monitor_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                count=0;

                collect_count.setVisibility(View.GONE);

                Intent intent = new Intent(getContext(),MineAddressActivity.class);

                startActivity(intent);

            }
        });

        //刷新控件
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page=1;
                isLoadmore=false;
                getData();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
//                super.onLoadMore(refreshLayout);
                page++;
                isLoadmore=true;
                getData();

            }
        });


        specialAccountAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                String type = specialAccountAdapter.getData().get(position).getType();

                String address_type = specialAccountAdapter.getData().get(position).getCoin();

                String name =specialAccountAdapter.getData().get(position).getName();

                List<String> address_list = specialAccountAdapter.getData().get(position).getAddress();

                StringBuffer sb  = new StringBuffer();

                for(String address: address_list){

                    sb.append(address+",");

                }

                String address = null;

                if(type.equals(ShowType.exchange.getContent())){
                    address =  ShowType.exchange.getContent()+ name + address_type;
                }else
                    address = specialAccountAdapter.getData().get(position).getAddress().get(0);


                String name_Cn = specialAccountAdapter.getData().get(position).getName_cn();

                int count_num = specialAccountAdapter.getData().get(position).getCount();

                if (!SQLiteUtils.getInstance().isAddressCollect(address)) {
                    CollectBean collectBean = new CollectBean();
                    if(!TextUtils.isEmpty(name_Cn)){
                        collectBean.setName(name+"/"+name_Cn);
                    }else
                        collectBean.setName(name);

                    collectBean.setAddressid(address);
                    collectBean.setAddress_list(sb.toString());
                    collectBean.setType("address");
                    collectBean.setLogo(specialAccountAdapter.getData().get(position).getLogo());
                    collectBean.setAddress_type(address_type);
                    collectBean.setCount(String.valueOf(count_num));
                    collectBean.setDetail(address);
                    collectBean.setIscollect(true);
                    collectBean.setTrade_count(0);
                    collectBean.setCollect_time(MyUtils.getRemindTime());
                    SQLiteUtils.getInstance().addContacts(collectBean);
                    specialAccountAdapter.getData().get(position).setChoose(true);
                    specialAccountAdapter.notifyItemChanged(position);

                    Commons.collect_address = address;

                    EventBus.getDefault().post(new OnChangeLanguageEvent(LanguageType.GET_ADDRESS_BALANCE));

                }
            }
        });
    }



    private void getData() {

        mPresenter.getAllSpecialAccount(page);
    }


    @Override
    protected void setupViews(LayoutInflater inflater) {



    }

    @Override
    protected void initView() {

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onChangeEvent(int type) {

    }


    @Override
    public void onSpecialSearchView(SpecialAccountResp resp) {

    }

    @Override
    public void onAllAccount(SpecialAccountResp resp) {

        refreshLayout.finishRefreshing();

        refreshLayout.finishLoadmore();

        if(TextUtils.isEmpty(resp.getErrInfo())){
            if(resp.getResult().getData()!=null){
                if(resp.getResult().getData().size()!=0){

                    List<SpecialAccountBean> data=resp.getResult().getData();

                    for(int i=0;i<data.size();i++){

                        SpecialAccountBean specialAccountBean = data.get(i);

                        String type = specialAccountBean.getType();

                        String address= null;

                        String name = specialAccountBean.getName();

                        String coin = specialAccountBean.getCoin();

                        if(type.equals(ShowType.exchange.getContent())){
                            address =  ShowType.exchange.getContent()+ name + coin;
                        }else
                            address = specialAccountBean.getAddress().get(0);



                        if (SQLiteUtils.getInstance().isAddressCollect(address)) {

                            specialAccountBean.setChoose(true);

                        }
                    }


                    if(isLoadmore){
                        specialAccountAdapter.addData(data);
                    }else
                        specialAccountAdapter.setNewData(data);

                }
            }
        }


    }


    @Override
    public void onMineAddress(AddressAddedResp resp) {

    }

    @Override
    public void onMonitorTrade(MonitorTradeResp resp) {

    }

    @Override
    public void onError() {

    }

    Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==LanguageType.ADDRESS_DELETE){
                String count = String.valueOf(SQLiteUtils.getInstance().selectContacts("address","exchange").size());

                String value =String.format(mcontext.getString(R.string.monitor_address),count);

                monitor_account.setText(value);
            }
        }
    };


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeLanguageEvent(OnChangeLanguageEvent event){

        if(event.languageType== LanguageType.GET_ADDRESS_BALANCE){

            count++;
            collect_count.setVisibility(View.VISIBLE);
            collect_count.setText(String.valueOf(count));

            String count = String.valueOf(SQLiteUtils.getInstance().selectContacts("address","exchange").size());

            String value =String.format(mcontext.getString(R.string.monitor_address),count);

            monitor_account.setText(value);

        }else if(event.languageType== LanguageType.ADDRESS_DELETE){

            refreshLayout.startRefresh();

            mhandler.sendEmptyMessageDelayed(LanguageType.ADDRESS_DELETE,5000);

        }

    }
}
