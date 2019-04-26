package com.gikee.app.presenter.search;

import com.gikee.app.Http.ApiMethods;
import com.gikee.app.api.MyObserver;
import com.gikee.app.api.ObserverOnNextListener;
import com.gikee.app.beans.SearchResp;
import com.gikee.app.presenter.base.BasePresenter;
import com.gikee.app.resp.AddressAddedResp;
import com.gikee.app.resp.HashTradeResp;
import com.gikee.app.resp.MonitorTradeResp;
import com.gikee.app.resp.SpecialAccountResp;

import java.util.List;
import java.util.Map;

public class SpecialSearchPresenter extends BasePresenter<SpecialSearchView> {

    public SpecialSearchPresenter(SpecialSearchView view){

        attachView(view);
    }



    public void getSpecialSearch(String name,int page){

        ObserverOnNextListener<SpecialAccountResp> listener = new ObserverOnNextListener<SpecialAccountResp>() {
            @Override
            public void onNext(SpecialAccountResp searchBean) {

                if(getView()!=null){

                    getView().onSpecialSearchView(searchBean);

                }


            }

            @Override
            public void onError() {

                getView().onError();

            }
        };

        ApiMethods.getSpecialSearch(new MyObserver<SpecialAccountResp>(listener),name,page);


    }


//    public void getSpecialAccountList(String name,String symbol,String coin){
//
//        ObserverOnNextListener<SpecialAccountResp> listener = new ObserverOnNextListener<SpecialAccountResp>() {
//            @Override
//            public void onNext(SpecialAccountResp searchBean) {
//
//                if(getView()!=null){
//
//                    getView().onSpecialAccountList(searchBean);
//
//                }
//
//
//            }
//
//            @Override
//            public void onError() {
//
//                getView().onError();
//
//            }
//        };
//
//        ApiMethods.getSpecialAccountList(new MyObserver<SpecialAccountResp>(listener),name,symbol,coin);
//
//
//    }


    public void getMineAddress(Map<String,List<String>> map){


        ObserverOnNextListener<AddressAddedResp> listener = new ObserverOnNextListener<AddressAddedResp>() {
            @Override
            public void onNext(AddressAddedResp allProjectCollBean) {

                if(getView()!=null){

                    getView().onMineAddress(allProjectCollBean);

                }

            }

            @Override
            public void onError() {

                if(getView()!=null){

                    getView().onError();

                }

            }
        };

        ApiMethods.getMineAddress(new MyObserver<AddressAddedResp>(listener),map);

    }

    /**
     * 获取所有特殊地址列表
     */
    public void getAllSpecialAccount(int page){


        ObserverOnNextListener<SpecialAccountResp> listener = new ObserverOnNextListener<SpecialAccountResp>() {
            @Override
            public void onNext(SpecialAccountResp allProjectCollBean) {

                if(getView()!=null){

                    getView().onAllAccount(allProjectCollBean);

                }

            }

            @Override
            public void onError() {

                if(getView()!=null){

                    getView().onError();

                }

            }
        };

        ApiMethods.getAllSpecialAccount(new MyObserver<SpecialAccountResp>(listener),page);

    }



    /**
     * 获取监控地址在一段时间内的交易数；
     */
    public void getMonitorTrade(Map map){


        ObserverOnNextListener<MonitorTradeResp> listener = new ObserverOnNextListener<MonitorTradeResp>() {
            @Override
            public void onNext(MonitorTradeResp allProjectCollBean) {

                if(getView()!=null){

                    getView().onMonitorTrade(allProjectCollBean);

                }

            }

            @Override
            public void onError() {

                if(getView()!=null){

                    getView().onError();

                }

            }
        };

        ApiMethods.getMonitorTrade(new MyObserver<MonitorTradeResp>(listener),map);

    }


}
