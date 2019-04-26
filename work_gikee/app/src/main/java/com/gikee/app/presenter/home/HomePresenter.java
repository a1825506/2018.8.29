package com.gikee.app.presenter.home;

import com.gikee.app.Http.ApiMethods;
import com.gikee.app.api.MyObserver;
import com.gikee.app.api.ObserverOnNextListener;
import com.gikee.app.presenter.base.BasePresenter;
import com.gikee.app.resp.MarketRateResp;
import com.gikee.app.resp.PowerResp;
import com.gikee.app.resp.SummaryResp;
import com.gikee.app.resp.TopFreqAddrResp;
import com.gikee.app.resp.ValueResp;

public class HomePresenter extends BasePresenter<HomeView> {

    public HomePresenter (HomeView view){

        attachView(view);
    }


    public void getMarketRate(){

        ObserverOnNextListener<MarketRateResp> listener = new ObserverOnNextListener<MarketRateResp>() {
            @Override
            public void onNext(MarketRateResp searchBean) {

                if(getView()!=null){

                    getView().onMarketRate(searchBean);

                }


            }

            @Override
            public void onError() {

                getView().onError();

            }
        };

        ApiMethods.getMarketRate(new MyObserver<MarketRateResp>(listener));


    }

    public void getMarketTrend(String date){

        ObserverOnNextListener<ValueResp> listener = new ObserverOnNextListener<ValueResp>() {
            @Override
            public void onNext(ValueResp searchBean) {

                if(getView()!=null){

                    getView().onMarketTrend(searchBean);

                }


            }

            @Override
            public void onError() {

                getView().onError();

            }
        };

        ApiMethods.getMarketTrend(new MyObserver<ValueResp>(listener),date);


    }


    public void getPower(String date){

        ObserverOnNextListener<PowerResp> listener = new ObserverOnNextListener<PowerResp>() {
            @Override
            public void onNext(PowerResp searchBean) {

                if(getView()!=null){

                    getView().onPower(searchBean);

                }


            }

            @Override
            public void onError() {

                getView().onError();

            }
        };

        ApiMethods.getPower(new MyObserver<PowerResp>(listener),date);


    }


    public void getchain(String id,String date){

        ObserverOnNextListener<SummaryResp> listener = new ObserverOnNextListener<SummaryResp>() {
            @Override
            public void onNext(SummaryResp searchBean) {

                if(getView()!=null){

                    getView().onChain(searchBean);

                }


            }

            @Override
            public void onError() {

                getView().onError();

            }
        };

        ApiMethods.getchain(new MyObserver<SummaryResp>(listener),id,date);


    }


    public void TopPlayer(String id){


        ObserverOnNextListener<TopFreqAddrResp> listener = new ObserverOnNextListener<TopFreqAddrResp>() {
            @Override
            public void onNext(TopFreqAddrResp topFreqAddrResp) {
                if(getView()!=null){
                    getView().onTopPlayer(topFreqAddrResp);
                }
            }

            @Override
            public void onError() {
                if(getView()!=null) {
                    getView().onError();
                }
            }
        };

        ApiMethods.TopPlayer(new MyObserver<TopFreqAddrResp>(listener),id);

    }


}
