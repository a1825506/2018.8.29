package com.gikee.app.presenter.search;

import com.gikee.app.Http.ApiMethods;
import com.gikee.app.api.MyObserver;
import com.gikee.app.api.ObserverOnNextListener;
import com.gikee.app.beans.SearchResp;
import com.gikee.app.presenter.base.BasePresenter;
import com.gikee.app.resp.RankingDetailResp;
import com.gikee.app.resp.RankingLabelResp;

public class RankingLabelPresenter extends BasePresenter<RankingLabelView>{

    public RankingLabelPresenter(RankingLabelView view){

        attachView(view);
    }


    public void getRanklabel(){

        ObserverOnNextListener<RankingLabelResp> listener = new ObserverOnNextListener<RankingLabelResp>() {
            @Override
            public void onNext(RankingLabelResp searchBean) {

                if(getView()!=null){

                    getView().onRanklabel(searchBean);

                }


            }

            @Override
            public void onError() {

                getView().onError();

            }
        };

        ApiMethods.getRanklabel(new MyObserver<RankingLabelResp>(listener));
    }



    public void getRankDetail(String lableId,int page,String rankedBy,int reverse){

        ObserverOnNextListener<RankingDetailResp> listener = new ObserverOnNextListener<RankingDetailResp>() {
            @Override
            public void onNext(RankingDetailResp rankingDetailResp) {

                if(getView()!=null){

                    getView().onRankDetail(rankingDetailResp);

                }


            }

            @Override
            public void onError() {

            }
        };

        ApiMethods.getRankDetail(new MyObserver<RankingDetailResp>(listener),lableId,page,rankedBy,reverse+"");
    }


}
