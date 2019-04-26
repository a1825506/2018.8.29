package com.gikee.app.presenter.project;

import com.gikee.app.Http.ApiMethods;
import com.gikee.app.api.MyObserver;
import com.gikee.app.api.ObserverOnNextListener;
import com.gikee.app.presenter.base.BasePresenter;
import com.gikee.app.resp.ExchangeResp;
import com.gikee.app.resp.SummaryResp;

public class ExchangePresenter extends BasePresenter<ExchangeView> {

    public ExchangePresenter(ExchangeView view){

        attachView(view);
    }



    public void getExchange(String id,String type,String p){


        ObserverOnNextListener<ExchangeResp> listener = new ObserverOnNextListener<ExchangeResp>() {
            @Override
            public void onNext(ExchangeResp shuJuFenXiBean) {

                if(getView()!=null){

                    getView().onExchange(shuJuFenXiBean);


                }
            }

            @Override
            public void onError() {
                if(getView()!=null){
                    getView().onError();
                }

            }
        };

        ApiMethods.getExchange(new MyObserver<ExchangeResp>(listener),id,type,p);


    }




}
