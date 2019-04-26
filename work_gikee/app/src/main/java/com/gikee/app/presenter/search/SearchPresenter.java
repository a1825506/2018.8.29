package com.gikee.app.presenter.search;



import com.gikee.app.Http.ApiMethods;
import com.gikee.app.api.MyObserver;
import com.gikee.app.api.ObserverOnNextListener;
import com.gikee.app.beans.SearchResp;
import com.gikee.app.presenter.base.BasePresenter;
import com.gikee.app.resp.BTCTradeDetailResp;
import com.gikee.app.resp.BaseLineResp;
import com.gikee.app.resp.HotProjectResp;
import com.gikee.app.resp.IsAddressResp;
import com.gikee.app.resp.LookAroundResp;
import com.gikee.app.resp.RankingDetailResp;
import com.gikee.app.resp.TokenTypeResp;


/**
 * @author tgh
 * @date 18-8-6
 * @time 下午4:36
 * @describe TODO
 * @email a18255064049@163.com
 */
public class SearchPresenter extends BasePresenter<InterfaceSearchView>{

    String TAG="SearchPresenter";

    public SearchPresenter(InterfaceSearchView view){

        attachView(view);
    }


    public void getSearch(String name,String type){

        ObserverOnNextListener<SearchResp> listener = new ObserverOnNextListener<SearchResp>() {
            @Override
            public void onNext(SearchResp searchBean) {

                if(getView()!=null){

                    getView().onSearchView(searchBean);

                }


            }

            @Override
            public void onError() {

                getView().onError();

            }
        };

        ApiMethods.getSearch(new MyObserver<SearchResp>(listener),name,type);


    }



    public void getFuzzySearch(String name,String type,int page){

        ObserverOnNextListener<SearchResp> listener = new ObserverOnNextListener<SearchResp>() {
            @Override
            public void onNext(SearchResp searchBean) {

                if(getView()!=null){

                    getView().onFuzzySearch(searchBean);

                }


            }

            @Override
            public void onError() {

                getView().onError();

            }
        };

        ApiMethods.getFuzzySearch(new MyObserver<SearchResp>(listener),name,type,page);


    }



    public void getSearchAddress(String address){

        ObserverOnNextListener<TokenTypeResp> listener = new ObserverOnNextListener<TokenTypeResp>() {
            @Override
            public void onNext(TokenTypeResp searchBean) {

                if(getView()!=null){

                    getView().onSearchAddressView(searchBean);

                }


            }

            @Override
            public void onError() {

                getView().onError();

            }
        };
//
        ApiMethods.getSearchAddressTkoen(new MyObserver<TokenTypeResp>(listener),address);

    }




    public void getLookAround(int start,int limit){

        ObserverOnNextListener<LookAroundResp> listener = new ObserverOnNextListener<LookAroundResp>() {
            @Override
            public void onNext(LookAroundResp lookAroundResp) {

                if(getView()!=null){

                    getView().onLookAround(lookAroundResp);

                }


            }

            @Override
            public void onError() {



            }
        };
//
        ApiMethods.getLookAround(new MyObserver<LookAroundResp>(listener),start+"",limit+"");

    }


    public void getHot(int start,int limit){

        ObserverOnNextListener<HotProjectResp> listener = new ObserverOnNextListener<HotProjectResp>() {
            @Override
            public void onNext(HotProjectResp lookAroundResp) {

                if(getView()!=null){

                    getView().onHot(lookAroundResp);

                }


            }

            @Override
            public void onError() {

                getView().onError();

            }
        };
//
        ApiMethods.getHot(new MyObserver<HotProjectResp>(listener),start+"",limit+"");

    }

    public void getRankDetail(String lableId,int start,int limit,String choseType){

        ObserverOnNextListener<RankingDetailResp> listener = new ObserverOnNextListener<RankingDetailResp>() {
            @Override
            public void onNext(RankingDetailResp rankingDetailResp) {

                if(getView()!=null){

                    getView().onRankDetail(rankingDetailResp);

                }


            }

            @Override
            public void onError() {

                getView().onError();

            }
        };

//        ApiMethods.getRankDetail(new MyObserver<RankingDetailResp>(listener),lableId,start,limit,choseType);
    }




    public void getHomeBaseLine(){

        ObserverOnNextListener<BaseLineResp> listener = new ObserverOnNextListener<BaseLineResp>() {
            @Override
            public void onNext(BaseLineResp rankingDetailResp) {

                if(getView()!=null){

                    getView().ontHomeBaseLine(rankingDetailResp);

                }
            }

            @Override
            public void onError() {

                getView().onError();

            }
        };

        ApiMethods.getHomeBaseLine(new MyObserver<BaseLineResp>(listener));
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

                getView().onError();

            }
        };

        ApiMethods.getBTCTradeDetail(new MyObserver<BTCTradeDetailResp>(listener),type,address,params);

    }

    /**判断该地址是不是EOS地址*/
    public void getIsAddress(String params,String type){

        ObserverOnNextListener<IsAddressResp> listener = new ObserverOnNextListener<IsAddressResp>() {
            @Override
            public void onNext(IsAddressResp addressDetailResp) {
                if(getView()!=null){
                    getView().onEOSTradeDetail(addressDetailResp);
                }
            }

            @Override
            public void onError() {

                if(getView()!=null){
                    getView().onError();
                }
            }
        };

        ApiMethods.getIsAddress(new MyObserver<IsAddressResp>(listener),params,type);


       // ApiMethods.getEOSTradeDetail(new MyObserver<AddressDetailResp>(listener),type,paramstype,params);

    }





}
