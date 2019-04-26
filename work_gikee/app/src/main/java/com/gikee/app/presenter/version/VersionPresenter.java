package com.gikee.app.presenter.version;

import com.gikee.app.Http.ApiMethods;
import com.gikee.app.api.MyObserver;
import com.gikee.app.api.ObserverOnNextListener;
import com.gikee.app.presenter.base.BasePresenter;
import com.gikee.app.resp.BaseResp;
import com.gikee.app.resp.RateBeanResp;
import com.gikee.app.resp.RateResp;
import com.gikee.app.resp.VersionResp;

public class VersionPresenter extends BasePresenter<VersionView>{

    public VersionPresenter(VersionView view){

        attachView(view);
    }



    public void checkAppUpdate(String versionName,String language){


        ObserverOnNextListener<VersionResp> listener = new ObserverOnNextListener<VersionResp>() {
            @Override
            public void onNext(VersionResp versionResp) {

                if(getView()!=null){

                    getView().onVersionInfo(versionResp);

                }


            }

            @Override
            public void onError() {

            }
        };

        ApiMethods.checkAppUpdate(new MyObserver<VersionResp>(listener),versionName,language);


    }



    public void sendToken(String token,String model){


        ObserverOnNextListener<BaseResp> listener = new ObserverOnNextListener<BaseResp>() {
            @Override
            public void onNext(BaseResp versionResp) {

                if(getView()!=null){

                    //getView().onToken(versionResp);

                }


            }

            @Override
            public void onError() {

            }
        };

        ApiMethods.sendToken(new MyObserver<BaseResp>(listener),token,model);

    }


    public void getExchangeRate(){


        ObserverOnNextListener<RateResp> listener = new ObserverOnNextListener<RateResp>() {
            @Override
            public void onNext(RateResp versionResp) {

                if(getView()!=null){

                    getView().onExchangeRate(versionResp);

                }


            }

            @Override
            public void onError() {

            }
        };

        ApiMethods.getExchangeRate(new MyObserver<RateResp>(listener));

    }

    public void RateBeanResp(){


        ObserverOnNextListener<RateBeanResp> listener = new ObserverOnNextListener<RateBeanResp>() {
            @Override
            public void onNext(RateBeanResp versionResp) {

                if(getView()!=null){

                    getView().onBTCRate(versionResp);

                }


            }

            @Override
            public void onError() {

            }
        };

        ApiMethods.RateBeanResp(new MyObserver<RateBeanResp>(listener));

    }



}
