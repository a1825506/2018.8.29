package com.gikee.app.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.Observer.CollectObserverService;
import com.gikee.app.Observer.base_observe.ConstObserver;
import com.gikee.app.Observer.base_observe.IObserver;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.activity.RemindDetailActivty;
import com.gikee.app.adapter.RemindAdapter;
import com.gikee.app.beans.RemindInfoBean;
import com.gikee.app.beans.RemindVlaueBean;
import com.gikee.app.beans.TotalMarketResp;
import com.gikee.app.datas.Commons;
import com.gikee.app.greendao.RemindBean;
import com.gikee.app.greendao.SQLiteUtils;
import com.gikee.app.preference_config.PreferenceUtil;
import com.gikee.app.presenter.project.RemindInfoPresenter;
import com.gikee.app.presenter.project.RemindInfoView;
import com.gikee.app.resp.RemindInfoResp;
import com.gikee.app.views.MyRefreshHeader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * {"title":"VEN链上发生特大交易单，交易量4800万，约合37亿人民币。","isHot":true,"title_en":"VEN链上发生特大交易单，交易量4800万，约合37亿人民币。","from":"VEN","context_en":"............","context":"madeforgoods与唯链认为，区块链技术具备不可篡改、去中心化的特性，它允许多方参与记录数据，并且数据不会被篡改，因此，区块链技术在防伪溯源应用上有天然的优势。将一物一码与区块链技术结合，建立基于区块链平台的防伪溯源系统，可以帮助企业解决信息不对称的难题，建立起强大的商业信任体系。"}
 *
 */
@SuppressLint("ValidFragment")
public class RemindFragment extends BaseFragment<RemindInfoPresenter> implements IObserver,RemindInfoView {

    private View view;
    private Context mcontext;
    private RecyclerView recyclerview;
    private RemindAdapter remindAdapter;
    private MessageReceiver mMessageReceiver;
    private TwinklingRefreshLayout twinklingRefreshLayout;
    public   String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";

    private static List<RemindInfoBean> remindInfoBeanList = new ArrayList<>();
    private RemindInfoBean remindInfoBean;
    private Gson gs = new Gson();

    private  TotalMarketResp totalMarketResp;

    public RemindFragment(Context context) {
        this.mcontext = context;
    }

    private int page=1;
    private boolean isLoadMore=false;


    public static List<RemindInfoBean> getRemindList(){

        return remindInfoBeanList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mPresenter = new RemindInfoPresenter(this);

        CollectObserverService.getInstance().registerObserver(this);

        view = LayoutInflater.from(mcontext).inflate(R.layout.fm_mineremind, container,false);

        recyclerview = view.findViewById(R.id.recyclerview);

        twinklingRefreshLayout = view.findViewById(R.id.mineproject_refreshLayout);
        MyRefreshHeader headerView = new MyRefreshHeader(getContext());
        twinklingRefreshLayout.setAutoLoadMore(true);
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setEnableLoadmore(true);

        remindAdapter = new RemindAdapter();

        recyclerview.setLayoutManager(new LinearLayoutManager(mcontext));

        recyclerview.setAdapter(remindAdapter);

        registerMessageReceiver();

        initonClick();

        twinklingRefreshLayout.startRefresh();

        return view;
    }

    private void initonClick() {

        remindAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent(mcontext, RemindDetailActivty.class);

                intent.putExtra("title",remindAdapter.getData().get(position).getTitle());

                intent.putExtra("title_en",remindAdapter.getData().get(position).getTitle_en());

                intent.putExtra("from",remindAdapter.getData().get(position).getSymbol());

                intent.putExtra("context",remindAdapter.getData().get(position).getContext());

                intent.putExtra("context_en",remindAdapter.getData().get(position).getContext_en());

                intent.putExtra("ishot",remindAdapter.getData().get(position).isHot());

                intent.putExtra("type",remindAdapter.getData().get(position).getType());

                intent.putExtra("time",remindAdapter.getData().get(position).getTime());

                startActivity(intent);

            }
        });
        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page=1;
                isLoadMore=false;
                getDate();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                isLoadMore=true;
                getDate();
            }
        });
    }

    public void getDate() {

        mPresenter.getRemindInfo(String.valueOf(page));

//        remindAdapter.setNewData(remindBeanList);
    }


    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(mcontext).registerReceiver(mMessageReceiver, filter);
    }

    @Override
    public void onChange(Object o, int actionCode, int requestCode) {
        if(requestCode== ConstObserver.REMINDINFO){

            getDate();

        }
    }

    private String title,title_en,content,content_en;


    @Override
    public void onRemindInfo(RemindInfoResp remindInfoResp) {

        twinklingRefreshLayout.finishRefreshing();

        twinklingRefreshLayout.finishLoadmore();

        if(!isLoadMore){
            remindInfoBeanList.clear();
        }




//        initRemindInfo(remindInfoResp);

        if(TextUtils.isEmpty(remindInfoResp.getErrInfo())){



            if(remindInfoResp.getResult()!=null){

                int szie = remindInfoResp.getResult().getData().size();

                PreferenceUtil.commitInt(Commons.totalMessageSize,szie);

                try{

                    for(RemindVlaueBean remindInfoBean1:remindInfoResp.getResult().getData()){

                        if(Commons.info_totalmarket.equals(remindInfoBean1.getType())||Commons.info_price.equals(remindInfoBean1.getType())){

                            totalMarketResp = gs.fromJson(remindInfoBean1.getValue(), TotalMarketResp.class);//把JSON字符串转为对象

                            if(Commons.info_totalmarket.equals(remindInfoBean1.getType())){

                                title = getString(R.string.info_markettitle)+" "+remindInfoBean1.getTime();

                                title_en = getString(R.string.info_markettitleen)+" "+remindInfoBean1.getTime();

                                for(RemindInfoBean remindInfoBean:totalMarketResp.getMessage()){

                                    String string=getResources().getString(R.string.info_marketcontent);

                                    String string_en=getResources().getString(R.string.info_marketcontenten);

                                    String up_down,up_down_en;

                                    if(remindInfoBean.getQuatechange().contains("-")){
                                        up_down = "下降";
                                        up_down_en = "down";
                                    }else{
                                        up_down = "上升";
                                        up_down_en = "up";
                                    }


                                    content = String.format(string,MyUtils.fmtMicrometer(remindInfoBean.getTodayValue()),up_down,MyUtils.fmtMicrometer(remindInfoBean.getQuatechange().replace("-",""))+"%");

                                    content_en=String.format(string_en,MyUtils.fmtMicrometer(remindInfoBean.getTodayValue()),up_down_en,MyUtils.fmtMicrometer(remindInfoBean.getQuatechange().replace("-",""))+"%");
                                }



                            }

                            if(Commons.info_price.equals(remindInfoBean1.getType())){

                                content="";

                                content_en="";

                                title = getString(R.string.info_pricetitle)+" "+remindInfoBean1.getTime();

                                title_en = getString(R.string.info_pricetitleen)+" "+remindInfoBean1.getTime();

                                for(RemindInfoBean remindInfoBean:totalMarketResp.getMessage()){

                                    String string=getResources().getString(R.string.info_pricecontent);

                                    String string_en=getResources().getString(R.string.info_pricecontenten);

                                    String up_down,up_down_en;

                                    if(remindInfoBean.getQuatechange().contains("-")){
                                        up_down = "下降";
                                        up_down_en = "down";
                                    }else{
                                        up_down = "上升";
                                        up_down_en = "up";
                                    }

                                    content =content+"\n"+String.format(string,remindInfoBean.getSymbol(),MyUtils.fmtMicrometer(remindInfoBean.getTodayValue()), MyUtils.fmtMicrometer(remindInfoBean.getYesterdayValue()),up_down,MyUtils.fmtMicrometer(remindInfoBean.getQuatechange().replace("-",""))+"%;");

                                    content_en=content_en+"\n"+String.format(string_en,remindInfoBean.getSymbol(),MyUtils.fmtMicrometer(remindInfoBean.getTodayValue()),up_down_en,MyUtils.fmtMicrometer(remindInfoBean.getQuatechange().replace("-",""))+"%",MyUtils.fmtMicrometer(remindInfoBean.getYesterdayValue()));



                                }

                            }

                        }else if(Commons.info_marketing.equals(remindInfoBean1.getType())){

                            totalMarketResp = gs.fromJson(remindInfoBean1.getValue(), TotalMarketResp.class);//把JSON字符串转为对象

                            for(RemindInfoBean remindInfoBean:totalMarketResp.getMessage()){

                                title = remindInfoBean.getTitle();

                                title_en = remindInfoBean.getTitle_en();

                                content = remindInfoBean.getContext();

                                content_en = remindInfoBean.getContext_en();
                            }

                        }else if(Commons.info_bigchange.equals(remindInfoBean1.getType())){


                            totalMarketResp = gs.fromJson(remindInfoBean1.getValue(), TotalMarketResp.class);//把JSON字符串转为对象

                            for(RemindInfoBean remindInfoBean:totalMarketResp.getMessage()){

                                title = remindInfoBean.getTitle();

                                title_en = remindInfoBean.getTitle_en();

                                content = remindInfoBean.getContext();

                                content_en = remindInfoBean.getContext_en();
                            }

                        }
                        else if(Commons.info_pricechange.equals(remindInfoBean1.getType())){

                            totalMarketResp = gs.fromJson(remindInfoBean1.getValue(), TotalMarketResp.class);//把JSON字符串转为对象


                                title = getString(R.string.info_pricechangetitle)+" "+remindInfoBean1.getTime();

                                title_en = getString(R.string.info_pricechangetitleen)+" "+remindInfoBean1.getTime();

                                for(RemindInfoBean remindInfoBean:totalMarketResp.getMessage()){

                                    String string=getResources().getString(R.string.info_pricechangecontent);

                                    String string_en=getResources().getString(R.string.info_pricechangecontenten);

                                    String up_down,up_down_en;

                                    if(remindInfoBean.getQuatechange().contains("-")){
                                        up_down = "下降";
                                        up_down_en = "down";
                                    }else{
                                        up_down = "上升";
                                        up_down_en = "up";
                                    }


                                    content = String.format(string,remindInfoBean.getSymbol(),MyUtils.fmtMicrometer(remindInfoBean.getTodayValue()),up_down,MyUtils.fmtMicrometer(remindInfoBean.getQuatechange().replace("-",""))+"%");

                                    content_en=String.format(string_en,remindInfoBean.getSymbol(),MyUtils.fmtMicrometer(remindInfoBean.getTodayValue()),up_down_en,MyUtils.fmtMicrometer(remindInfoBean.getQuatechange().replace("-",""))+"%");
                                }


                        }

                        if(!TextUtils.isEmpty(title)){
                            remindInfoBean = new RemindInfoBean();

                            remindInfoBean.setTitle(title);

                            remindInfoBean.setTitle_en(title_en);

                            remindInfoBean.setContext(content);

                            remindInfoBean.setContext_en(content_en);

                            remindInfoBean.setId(remindInfoBean1.getId());

                            remindInfoBean.setType(remindInfoBean1.getType());

                            remindInfoBean.setTime(remindInfoBean1.getTime());

                            remindInfoBean.setSymbol("Gikee");

                            remindInfoBeanList.add(remindInfoBean);
                        }
                    }


                    List<RemindBean> remindBeanList = SQLiteUtils.getInstance().getRemind();

                    for(RemindBean remindBean:remindBeanList){

                        remindInfoBean = new RemindInfoBean();

                        remindInfoBean.setTitle(remindBean.getTitle());

                        remindInfoBean.setTitle_en(remindBean.getTitle_en());

                        remindInfoBean.setContext(remindBean.getContext());

                        remindInfoBean.setContext_en(remindBean.getContext_en());

                        remindInfoBean.setTime(remindBean.getTime());

                        remindInfoBean.setHot(remindBean.getIsHot());

                        remindInfoBean.setSymbol(remindBean.getFrom());

                        remindInfoBeanList.add(remindInfoBean);

                    }


                        remindAdapter.setNewData(remindInfoBeanList);



                }catch (Exception e){

                }

            }

        }

    }


    @Override
    public void onError() {

    }



    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    getDate();
                }
            } catch (Exception e){

            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(mcontext).unregisterReceiver(mMessageReceiver);
    }

    @Override
    protected void setupViews(LayoutInflater inflater) {



    }

    @Override
    protected void initView() {



    }

    @Override
    protected void lazyLoad() {

        Log.e("RemindFragment","lazyLoad");


    }

    @Override
    protected void onChangeEvent(int type) {

    }
}
