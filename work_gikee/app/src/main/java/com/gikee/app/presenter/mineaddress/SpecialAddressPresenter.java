package com.gikee.app.presenter.mineaddress;

import com.gikee.app.Http.ApiMethods;
import com.gikee.app.api.MyObserver;
import com.gikee.app.api.ObserverOnNextListener;
import com.gikee.app.beans.FrequenTradeResp;
import com.gikee.app.presenter.base.BasePresenter;
import com.gikee.app.resp.SpecialAddressResp;

public class SpecialAddressPresenter extends BasePresenter<SpecialAddressView>{


    public SpecialAddressPresenter (SpecialAddressView view){

        attachView(view);
    }


    public void getSpecialAddress(String symbol,int start,int limit,String starDate,String endDate){


        ObserverOnNextListener<SpecialAddressResp> listener = new ObserverOnNextListener<SpecialAddressResp>() {
            @Override
            public void onNext(SpecialAddressResp specialAddressBean) {
                if(getView()!=null){
                    getView().onSpecialAddress(specialAddressBean);
                }
            }

            @Override
            public void onError() {

            }
        };

        ApiMethods.getSpecialAddress(new MyObserver<SpecialAddressResp>(listener),symbol,start+"",limit+"",starDate,endDate);


    }

    public void getSpeciallist(String type,String address,String symbol,int start,int limit,String starDate,String endDate){


        ObserverOnNextListener<SpecialAddressResp> listener = new ObserverOnNextListener<SpecialAddressResp>() {
            @Override
            public void onNext(SpecialAddressResp specialAddressBean) {
                if(getView()!=null){
                    getView().onSpecialList(specialAddressBean);
                }
            }

            @Override
            public void onError() {

            }
        };

        ApiMethods.getSpeciallist(new MyObserver<SpecialAddressResp>(listener),type,address,symbol,start+"",limit+"",starDate,endDate);


    }





    public void getBigData(String symbol,int start,int limit,String starDate,String endDate){


        ObserverOnNextListener<SpecialAddressResp> listener = new ObserverOnNextListener<SpecialAddressResp>() {
            @Override
            public void onNext(SpecialAddressResp specialAddressBean) {
                if(getView()!=null){
                    getView().onBigData(specialAddressBean);
                }
            }

            @Override
            public void onError() {

            }
        };

        ApiMethods.getBigData(new MyObserver<SpecialAddressResp>(listener),symbol,start+"",limit+"",starDate,endDate);


    }


    public void getFrequentlyTrade(String symbol,int start,int limit,String starDate,String endDate){


        ObserverOnNextListener<FrequenTradeResp> listener = new ObserverOnNextListener<FrequenTradeResp>() {
            @Override
            public void onNext(FrequenTradeResp specialAddressBean) {
                if(getView()!=null){
                    getView().onFrequentlyTrade(specialAddressBean);
                }
            }

            @Override
            public void onError() {

            }
        };

        ApiMethods.getFrequentlyTrade(new MyObserver<FrequenTradeResp>(listener),symbol,start+"",limit+"",starDate,endDate);


    }






    /**
     * 1.地址收藏
     * 2.页面接口调整，新增页面。
     *
     */


}
