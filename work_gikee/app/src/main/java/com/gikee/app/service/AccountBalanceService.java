package com.gikee.app.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.gikee.app.Utils.MyUtils;
import com.gikee.app.beans.AddressAddedBean;
import com.gikee.app.beans.SpecialAccountBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.greendao.CollectBean;
import com.gikee.app.greendao.SQLiteUtils;

import com.gikee.app.language.LanguageType;
import com.gikee.app.language.OnChangeLanguageEvent;
import com.gikee.app.presenter.search.SpecialSearchPresenter;
import com.gikee.app.presenter.search.SpecialSearchView;
import com.gikee.app.resp.AddressAddedResp;
import com.gikee.app.resp.MonitorTradeResp;
import com.gikee.app.resp.SpecialAccountResp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用来在后台获取收藏地址的资产余额，资产变动等信息
 */
public class AccountBalanceService extends Service implements SpecialSearchView {


    private List<CollectBean> collectBeanList;

    private SpecialSearchPresenter mPresenter;

    private  List<SpecialAccountBean> specialAccountBeanList;

    private double total_balanc=0;

    private boolean isrun=true;

    private Handler mhandler;

    private  Map map_monitor = new HashMap();

    @Override
    public void onCreate() {
        super.onCreate();
        mPresenter = new SpecialSearchPresenter(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent!=null){

            mhandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if(msg.what==1){

                        mPresenter.getMonitorTrade(map_monitor);


                    }else if(msg.what==2){

                        initGetBalance();
                    }

                }
            };


            new Thread(new Runnable() {
                @Override
                public void run() {

                    while (isrun){

                        try {
                            Thread.sleep(20000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        List<CollectBean> collectBeanList = SQLiteUtils.getInstance().selectContacts("address","exchange");

                        for(CollectBean collectBean:collectBeanList){

                            String name = collectBean.getAddressid();

                            Commons.collect_address = name;

                            Log.e("+++++",name);

                            String tag = collectBean.getDetail();

                            List<String> list  = new ArrayList<>();


                            map_monitor.clear();

                            if(collectBean.getAddressid().contains("exchange")){

                                if(collectBean.getAddress_list()!=null){

                                    String[] list_address  = collectBean.getAddress_list().split(",");

                                    for(String address:list_address){

                                        list.add(address);

                                    }

                                }



                            }else
                                list.add(collectBean.getAddressid());


                            map_monitor.put("address",list);

                            map_monitor.put("time",collectBean.getCollect_time());

                            map_monitor.put("name",name);

                            mhandler.sendEmptyMessage(1);

                            if(TextUtils.isEmpty(collectBean.getBalance())){

                                mhandler.sendEmptyMessage(2);
                            }



                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }




                        }


                    }

                }
            }).start();


        }

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        isrun=false;

    }

    private void initGetBalance() {

        total_balanc = 0;
        //获取所有收藏的地址。有单个具体地址、有交易所、
        collectBeanList = SQLiteUtils.getInstance().getAddressID(Commons.collect_address);

        CollectBean collectBean = collectBeanList.get(0);

//        for(CollectBean collectBean:collectBeanList){

        if(TextUtils.isEmpty(collectBean.getBalance())){

            Map<String,List<String>> map = new HashMap<String,List<String>>();

            String type = collectBeanList.get(0).getAddress_type();

            if(collectBean.getAddressid().contains("exchange")){
                //收藏的是交易所，将交易所所有地址筛选出来
                String[] list_address  = collectBeanList.get(0).getAddress_list().split(",");

                List<String> list  = new ArrayList<>();

                for(String address:list_address){

                    list.add(address);

                }

                map.put(type,list);


            }else{
                //收藏的是单个的账户

                List<String> list  = new ArrayList<>();

                list.add(collectBeanList.get(0).getAddressid());

                map.put(type,list);


            }

            mPresenter.getMineAddress(map);
        }

//        }
    }

    @Override
    public void onSpecialSearchView(SpecialAccountResp resp) {

    }

    @Override
    public void onAllAccount(SpecialAccountResp resp) {

    }




    @Override
    public void onMineAddress(AddressAddedResp resp) {


        if(TextUtils.isEmpty(resp.getErrInfo())){

            if(resp.getData()!=null){

                if(resp.getData().size()!=0){

                    List<AddressAddedBean> addressAddedBeanList = resp.getData();

                        for(AddressAddedBean addressAddedBean:addressAddedBeanList){

                            total_balanc = total_balanc+addressAddedBean.getBalance();

                        }
                }

            }

        }

        collectBeanList = SQLiteUtils.getInstance().getAddressID(Commons.collect_address);

        if(collectBeanList!=null){
            CollectBean collectBean = collectBeanList.get(0);

            if(total_balanc<0.1){
                collectBean.setBalance(MyUtils.getNumber(total_balanc+""));
            }else
                collectBean.setBalance(total_balanc+"");


            SQLiteUtils.getInstance().updateContacts(collectBean);
        }
        }




    @Override
    public void onMonitorTrade(MonitorTradeResp resp) {

        if(TextUtils.isEmpty(resp.getErrInfo())){

            if(resp.getTradeMonitor()!=null){

                if(resp.getTradeMonitor().getName()!=null){

                    List<CollectBean>  collectBeanList =  SQLiteUtils.getInstance().getAddressID(resp.getTradeMonitor().getName());

                    for(CollectBean collectBean:collectBeanList){

                        if(collectBean.getAddressid().equals(resp.getTradeMonitor().getName())){

                            Log.e("+++++",resp.getTradeMonitor().getName()+"==交易量："+resp.getTradeMonitor().getCount());
                            collectBean.setTrade_count(resp.getTradeMonitor().getCount());

                            SQLiteUtils.getInstance().updateContacts(collectBean);

                            EventBus.getDefault().post(new OnChangeLanguageEvent(LanguageType.GET_MONITOR_TRADE));

                        }
                    }

                }

            }

        }

    }





    @Override
    public void onError() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeLanguageEvent(OnChangeLanguageEvent event){

        if(event.languageType== LanguageType.GET_ADDRESS_BALANCE){

            initGetBalance();

        }


    }


//    public class ServiceBinder extends Binder {
//        public AccountBalanceService getService() {
//            return AccountBalanceService.this;
//        }
//    }
}
