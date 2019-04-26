package com.gikee.app.presenter.project;

import com.gikee.app.Http.ApiMethods;
import com.gikee.app.api.MyObserver;
import com.gikee.app.api.ObserverOnNextListener;
import com.gikee.app.presenter.base.BasePresenter;
import com.gikee.app.resp.AddressAddedResp;
import com.gikee.app.resp.AddressCountResp;
import com.gikee.app.resp.AssetResp;
import com.gikee.app.resp.HashTradeResp;
import com.gikee.app.resp.TokensAddedResp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tgh
 * @date 18-8-6
 * @time 下午2:44
 * @describe TODO
 * @email a18255064049@163.com
 */
public class MineProjectPresenter extends BasePresenter<MineProjectView>{




    public MineProjectPresenter(MineProjectView view) {
        attachView(view);
    }




    /**
     * 获取项目相关的指标数据
     */
    public void getMineProject(String coinSymbols){


        ObserverOnNextListener<TokensAddedResp> listener = new ObserverOnNextListener<TokensAddedResp>() {
            @Override
            public void onNext(TokensAddedResp allProjectCollBean) {

                if(getView()!=null){

                    getView().onMineProject(allProjectCollBean);

                }

            }

            @Override
            public void onError() {

                if(getView()!=null){

                    getView().onError();

                }

            }
        };

        ApiMethods.getMineProject(new MyObserver<TokensAddedResp>(listener),coinSymbols);

    }


    /**
     * 获取地址对应的资产余额
     */
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
     * 获取地址在一段时间内的交易笔数
     */
    public void getTradeCount(Map<String,Long> map){


        ObserverOnNextListener<AddressCountResp> listener = new ObserverOnNextListener<AddressCountResp>() {
            @Override
            public void onNext(AddressCountResp allProjectCollBean) {

                if(getView()!=null){

                    getView().onMineCount(allProjectCollBean);

                }

            }

            @Override
            public void onError() {

                if(getView()!=null){

                    getView().onError();

                }

            }
        };

        ApiMethods.getTradeCount(new MyObserver<AddressCountResp>(listener),map);

    }

    /**
     * 获取若干地址在一段时间内的资产数据
     */
    public void getAssetData(Map map){


        ObserverOnNextListener<AssetResp> listener = new ObserverOnNextListener<AssetResp>() {
            @Override
            public void onNext(AssetResp allProjectCollBean) {

                if(getView()!=null){

                    getView().onAssetData(allProjectCollBean);

                }

            }

            @Override
            public void onError() {

                if(getView()!=null){

                    getView().onError();

                }

            }
        };

        ApiMethods.getAssetData(new MyObserver<AssetResp>(listener),map);

    }


    /**
     * 获取若干地址在一段时间内的交易记录
     */
    public void getAccountTrade(Map map){


        ObserverOnNextListener<HashTradeResp> listener = new ObserverOnNextListener<HashTradeResp>() {
            @Override
            public void onNext(HashTradeResp allProjectCollBean) {

                if(getView()!=null){

                    getView().onAccountTrade(allProjectCollBean);

                }

            }

            @Override
            public void onError() {

                if(getView()!=null){

                    getView().onError();

                }

            }
        };

        ApiMethods.getAccountTrade(new MyObserver<HashTradeResp>(listener),map);

    }










}
