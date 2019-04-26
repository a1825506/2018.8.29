package com.gikee.app.presenter.project;

import com.gikee.app.Http.ApiMethods;
import com.gikee.app.api.MyObserver;
import com.gikee.app.api.ObserverOnNextListener;
import com.gikee.app.presenter.base.BasePresenter;
import com.gikee.app.resp.RemindInfoResp;

public class RemindInfoPresenter extends BasePresenter<RemindInfoView> {

    public RemindInfoPresenter(RemindInfoView view) {
        attachView(view);
    }


    public void getRemindInfo(String page){


        ObserverOnNextListener<RemindInfoResp> listener = new ObserverOnNextListener<RemindInfoResp>() {
            @Override
            public void onNext(RemindInfoResp remindInfoResp) {

                if(getView()!=null){

                    getView().onRemindInfo(remindInfoResp);

                }

            }

            @Override
            public void onError() {

                if(getView()!=null){

                    getView().onError();

                }

            }
        };

        ApiMethods.getRemindInfo(new MyObserver<RemindInfoResp>(listener),page);

    }



}
