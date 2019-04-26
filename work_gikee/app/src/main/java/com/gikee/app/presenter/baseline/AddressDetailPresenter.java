package com.gikee.app.presenter.baseline;

import com.gikee.app.Http.ApiMethods;
import com.gikee.app.api.MyObserver;
import com.gikee.app.api.ObserverOnNextListener;
import com.gikee.app.presenter.base.BasePresenter;
import com.gikee.app.resp.AddressDetailResp;
import com.gikee.app.resp.BTCAddressReap;
import com.gikee.app.resp.BTCTradeDetailResp;
import com.gikee.app.resp.BTCTradeListResp;
import com.gikee.app.resp.EOSTradeDetailResp;
import com.gikee.app.resp.ERC20ListResp;
import com.gikee.app.resp.HashDetailResp;
import com.gikee.app.resp.HashTradeResp;

public class AddressDetailPresenter extends BasePresenter<AddressDetailView>{


    public AddressDetailPresenter (AddressDetailView view){

        attachView(view);
    }


    public void getAddressDetail(String type,String paramstype,String params){


        ObserverOnNextListener<AddressDetailResp> listener = new ObserverOnNextListener<AddressDetailResp>() {
            @Override
            public void onNext(AddressDetailResp addressDetailResp) {
                if(getView()!=null){
                    getView().onAddressDetail(addressDetailResp);
                }
            }

            @Override
            public void onError() {

                if(getView()!=null){
                    getView().onError();
                }
            }
        };

        ApiMethods.getAddressDetail(new MyObserver<AddressDetailResp>(listener),type,paramstype,params);


    }

    public void getTradeList(String type,String address,int page){

        ObserverOnNextListener<HashTradeResp> listener = new ObserverOnNextListener<HashTradeResp>() {
            @Override
            public void onNext(HashTradeResp addressDetailResp) {
                if(getView()!=null){
                    getView().onTradeList(addressDetailResp);
                }
            }

            @Override
            public void onError() {

            }
        };

        ApiMethods.getTradeList(new MyObserver<HashTradeResp>(listener),type,address,page);

    }


    public void getAddressTrans(String address,String type,int page){

        ObserverOnNextListener<ERC20ListResp> listener = new ObserverOnNextListener<ERC20ListResp>() {
            @Override
            public void onNext(ERC20ListResp addressDetailResp) {
                if(getView()!=null){
                    getView().onAddressTrans(addressDetailResp);
                }
            }

            @Override
            public void onError() {

            }
        };

        ApiMethods.getAddressTrans(new MyObserver<ERC20ListResp>(listener),address,type,page);

    }



    public void getBTCTradeList(String address,int page){

        ObserverOnNextListener<BTCTradeListResp> listener = new ObserverOnNextListener<BTCTradeListResp>() {
            @Override
            public void onNext(BTCTradeListResp addressDetailResp) {
                if(getView()!=null){
                    getView().onBTCTradeList(addressDetailResp);
                }
            }

            @Override
            public void onError() {

            }
        };

        ApiMethods.getBTCTradeList(new MyObserver<BTCTradeListResp>(listener),address,page);

    }


    public void getBTCAddressDetail(String type,String address,String params){

        ObserverOnNextListener<BTCAddressReap> listener = new ObserverOnNextListener<BTCAddressReap>() {
            @Override
            public void onNext(BTCAddressReap addressDetailResp) {
                if(getView()!=null){
                    getView().onBTCAddressDetail(addressDetailResp);
                }
            }

            @Override
            public void onError() {

            }
        };

        ApiMethods.getBTCAddressDetail(new MyObserver<BTCAddressReap>(listener),type,address,params);

    }


    public void getHashDetail(String type,String address,String params){

        ObserverOnNextListener<HashDetailResp> listener = new ObserverOnNextListener<HashDetailResp>() {
            @Override
            public void onNext(HashDetailResp addressDetailResp) {
                if(getView()!=null){
                    getView().HashDetail(addressDetailResp);
                }
            }

            @Override
            public void onError() {

            }
        };

        ApiMethods.getHashDetail(new MyObserver<HashDetailResp>(listener),type,address,params);

    }


    public void getEOSAddressDetail(String type,String address,String params){

        ObserverOnNextListener<AddressDetailResp> listener = new ObserverOnNextListener<AddressDetailResp>() {
            @Override
            public void onNext(AddressDetailResp addressDetailResp) {
                if(getView()!=null){
                    getView().onEOSAddress(addressDetailResp);
                }
            }

            @Override
            public void onError() {

            }
        };

        ApiMethods.getEOSAddressDetail(new MyObserver<AddressDetailResp>(listener),type,address,params);

    }


    public void getBTCTradeDetail(String type,String address,String params){

        ObserverOnNextListener<BTCTradeDetailResp> listener = new ObserverOnNextListener<BTCTradeDetailResp>() {
            @Override
            public void onNext(BTCTradeDetailResp addressDetailResp) {
                if(getView()!=null){
                    getView().onBTCTradeDetail(addressDetailResp);
                }
            }

            @Override
            public void onError() {

            }
        };

        ApiMethods.getBTCTradeDetail(new MyObserver<BTCTradeDetailResp>(listener),type,address,params);

    }


    public void getEOSTradeDetail(String type,String address,String params){

        ObserverOnNextListener<EOSTradeDetailResp> listener = new ObserverOnNextListener<EOSTradeDetailResp>() {
            @Override
            public void onNext(EOSTradeDetailResp addressDetailResp) {
                if(getView()!=null){
                    getView().onEOSTradeDetail(addressDetailResp);
                }
            }

            @Override
            public void onError() {

            }
        };

        ApiMethods.getEOSradeDetail(new MyObserver<EOSTradeDetailResp>(listener),type,address,params);

    }



}
