package com.gikee.app.presenter.project;

import com.gikee.app.Http.ApiMethods;
import com.gikee.app.api.MyObserver;
import com.gikee.app.api.ObserverOnNextListener;
import com.gikee.app.presenter.base.BasePresenter;
import com.gikee.app.resp.Top100Bean;

/**
 * @author tgh
 * @date 18-8-6
 * @time 下午6:06
 * @describe TODO
 * @email a18255064049@163.com
 */
public class ZhanghuTopPresenter extends BasePresenter<ZhanghuTopView> {

    public ZhanghuTopPresenter(ZhanghuTopView view){

        attachView(view);
    }


    public void getZhanghuTop(String top100ownerPerCoin, String coinSymbol, String date_from, String date_to, String s, String choseType){


        ObserverOnNextListener<Top100Bean> listener = new ObserverOnNextListener<Top100Bean>() {
            @Override
            public void onNext(Top100Bean todayTop100Bean) {
                if(getView()!=null){
                    getView().onZhanghuTop(todayTop100Bean);
                }
            }

            @Override
            public void onError() {

            }
        };

        ApiMethods.getZhanghuTop(new MyObserver<Top100Bean>(listener),top100ownerPerCoin,coinSymbol,date_from,date_to,s,choseType);
    }
}
