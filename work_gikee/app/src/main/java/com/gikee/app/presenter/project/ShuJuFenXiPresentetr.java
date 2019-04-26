package com.gikee.app.presenter.project;

import com.gikee.app.Http.ApiMethods;
import com.gikee.app.api.MyObserver;
import com.gikee.app.api.ObserverOnNextListener;
import com.gikee.app.presenter.base.BasePresenter;
import com.gikee.app.resp.ProjectCompaResp;
import com.gikee.app.resp.ProjectInfoResp;
import com.gikee.app.resp.SummaryResp;

/**
 * @author tgh
 * @date 18-8-6
 * @time 下午6:37
 * @describe TODO
 * @email a18255064049@163.com
 */
public class ShuJuFenXiPresentetr extends BasePresenter<ShuJuFenXiView>{

    public ShuJuFenXiPresentetr(ShuJuFenXiView view){

        attachView(view);
    }

    public void getShujuFenXi(String items,String coinSymbol){


        ObserverOnNextListener<SummaryResp> listener = new ObserverOnNextListener<SummaryResp>() {
            @Override
            public void onNext(SummaryResp shuJuFenXiBean) {

                if(getView()!=null){

                    getView().onShuJuFenXi(shuJuFenXiBean);


                }
            }

            @Override
            public void onError() {
                if(getView()!=null){
                    getView().onError();
                }
            }
        };

        ApiMethods.getShujuFenXi(new MyObserver<SummaryResp>(listener),items,coinSymbol);


    }

    public void getRadeIndex(String items,String coinSymbol){


        ObserverOnNextListener<SummaryResp> listener = new ObserverOnNextListener<SummaryResp>() {
            @Override
            public void onNext(SummaryResp shuJuFenXiBean) {

                if(getView()!=null){

                    getView().onShuJuFenXi(shuJuFenXiBean);


                }
            }

            @Override
            public void onError() {
                if(getView()!=null){
                    getView().onError();
                }
            }
        };

        ApiMethods.getTradeIndex(new MyObserver<SummaryResp>(listener),items,coinSymbol);


    }

    public void getAddrIndex(String items,String coinSymbol){


        ObserverOnNextListener<SummaryResp> listener = new ObserverOnNextListener<SummaryResp>() {
            @Override
            public void onNext(SummaryResp shuJuFenXiBean) {

                if(getView()!=null){

                    getView().onShuJuFenXi(shuJuFenXiBean);


                }
            }

            @Override
            public void onError() {
                if(getView()!=null){
                    getView().onError();
                }
            }
        };

        ApiMethods.getAddrIndex(new MyObserver<SummaryResp>(listener),items,coinSymbol);


    }


    public void getAllIndex(String coinSymbol){


        ObserverOnNextListener<SummaryResp> listener = new ObserverOnNextListener<SummaryResp>() {
            @Override
            public void onNext(SummaryResp shuJuFenXiBean) {

                if(getView()!=null){

                    getView().onShuJuFenXi(shuJuFenXiBean);


                }
            }

            @Override
            public void onError() {
                if(getView()!=null){
                    getView().onError();
                }

            }
        };

        ApiMethods.getAllIndex(new MyObserver<SummaryResp>(listener),coinSymbol);


    }


    public void getIndexContrast(String coinSymbol,String date){


        ObserverOnNextListener<SummaryResp> listener = new ObserverOnNextListener<SummaryResp>() {
            @Override
            public void onNext(SummaryResp shuJuFenXiBean) {

                if(getView()!=null){

                    getView().onIndexContrast(shuJuFenXiBean);


                }
            }

            @Override
            public void onError() {
                if(getView()!=null){
                    getView().onError();
                }

            }
        };

        ApiMethods.getIndexContrast(new MyObserver<SummaryResp>(listener),coinSymbol,date);


    }


    public void getProjectInfo(String id,String language){

        ObserverOnNextListener<ProjectInfoResp> listener = new ObserverOnNextListener<ProjectInfoResp>() {
            @Override
            public void onNext(ProjectInfoResp shuJuFenXiBean) {

                if(getView()!=null){

                    getView().onProjectInfo(shuJuFenXiBean);

                }
            }

            @Override
            public void onError() {
                if(getView()!=null){
                    getView().onError();
                }

            }
        };

        ApiMethods.getProjectInfo(new MyObserver<ProjectInfoResp>(listener),id,language);


    }


    public void getProjectCompar(String id, String itemName,String date){

        ObserverOnNextListener<ProjectCompaResp> listener = new ObserverOnNextListener<ProjectCompaResp>() {
            @Override
            public void onNext(ProjectCompaResp shuJuFenXiBean) {

                if(getView()!=null){

                    getView().onProjectCompar(shuJuFenXiBean);

                }
            }

            @Override
            public void onError() {
                if(getView()!=null){
                    getView().onError();
                }

            }
        };

        ApiMethods.getprojectCompar(new MyObserver<ProjectCompaResp>(listener),id,itemName,date);


    }











}
