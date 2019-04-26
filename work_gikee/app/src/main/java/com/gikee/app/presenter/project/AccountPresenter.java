package com.gikee.app.presenter.project;

import com.gikee.app.Http.ApiMethods;
import com.gikee.app.api.MyObserver;
import com.gikee.app.api.ObserverOnNextListener;
import com.gikee.app.presenter.base.BasePresenter;
import com.gikee.app.resp.IntroInfoResp;
import com.gikee.app.resp.OwnerDistributeResp;
import com.gikee.app.resp.Top100Resp;
import com.gikee.app.resp.TopFreqAddrResp;
import com.gikee.app.resp.TradeCountDisResp;
import com.gikee.app.resp.TradeVolDisResp;
import com.gikee.app.resp.ValueResp;

/**
 * @author tgh
 * @date 18-8-6
 * @time 下午5:29
 * @describe TODO
 * @email a18255064049@163.com
 */
public class AccountPresenter extends BasePresenter<AccountView>{

    public AccountPresenter (AccountView view){

        attachView(view);
    }

    public void getValue(String method,String id, String date_from, String date_to, String unit){


        ObserverOnNextListener<ValueResp> listener = new ObserverOnNextListener<ValueResp>() {
            @Override
            public void onNext(ValueResp valueResp) {
                if(getView()!=null){
                    getView().onValue(valueResp);
                }
            }

            @Override
            public void onError() {
                if(getView()!=null) {
                    getView().onError();
                }
            }
        };

        ApiMethods.getValue(new MyObserver<ValueResp>(listener),method,id,date_from,date_to,unit);

    }



//    public void getAvgTrdVol(String symbol, String from, String to, String unit){
//
//
//        ObserverOnNextListener<AvgTrdVolResp> listener = new ObserverOnNextListener<AvgTrdVolResp>() {
//            @Override
//            public void onNext(AvgTrdVolResp newAndInactivityResp) {
//                if(getView()!=null){
//                    getView().onAvgTrdVol(newAndInactivityResp);
//                }
//            }
//
//            @Override
//            public void onError() {
//                if(getView()!=null) {
//                    getView().onError();
//                }
//            }
//        };
//
//        ApiMethods.getAvgTrdVol(new MyObserver<AvgTrdVolResp>(listener),symbol,from,to,unit);
//
//    }

//    public void getAllGas( String from, String to, String unit){
//
//
//        ObserverOnNextListener<AllGasResp> listener = new ObserverOnNextListener<AllGasResp>() {
//            @Override
//            public void onNext(AllGasResp newAndInactivityResp) {
//                if(getView()!=null){
//                    getView().onAllGas(newAndInactivityResp);
//                }
//            }
//
//            @Override
//            public void onError() {
//                if(getView()!=null) {
//                    getView().onError();
//                }
//            }
//        };
//
//        ApiMethods.getAllGas(new MyObserver<AllGasResp>(listener),from,to,unit);
//
//    }

//    public void getAvgGas( String from, String to, String unit){
//
//
//        ObserverOnNextListener<AvgGasResp> listener = new ObserverOnNextListener<AvgGasResp>() {
//            @Override
//            public void onNext(AvgGasResp newAndInactivityResp) {
//                if(getView()!=null){
//                    getView().onAvgGas(newAndInactivityResp);
//                }
//            }
//
//            @Override
//            public void onError() {
//                if(getView()!=null) {
//                    getView().onError();
//                }
//            }
//        };
//
//        ApiMethods.getAvgGas(new MyObserver<AvgGasResp>(listener),from,to,unit);
//
//    }

//    public void getMarketValue(String symbol, String from, String to){
//
//
//        ObserverOnNextListener<MarketValueResp> listener = new ObserverOnNextListener<MarketValueResp>() {
//            @Override
//            public void onNext(MarketValueResp newAndInactivityResp) {
//                if(getView()!=null){
//                    getView().onMarketValue(newAndInactivityResp);
//                }
//            }
//
//            @Override
//            public void onError() {
//                if(getView()!=null) {
//                    getView().onError();
//                }
//            }
//        };
//
//        ApiMethods.getMarketValue(new MyObserver<MarketValueResp>(listener),symbol,from,to);
//
//    }









    public void getOwnerDistribute(String symbol, String from, String to, String unit){


        ObserverOnNextListener<OwnerDistributeResp> listener = new ObserverOnNextListener<OwnerDistributeResp>() {
            @Override
            public void onNext(OwnerDistributeResp newAndInactivityResp) {
                if(getView()!=null){
                    getView().onOwnerDistribute(newAndInactivityResp);
                }
            }

            @Override
            public void onError() {
                if(getView()!=null) {
                    getView().onError();
                }
            }
        };

        ApiMethods.getOwnerDistribute(new MyObserver<OwnerDistributeResp>(listener),symbol,from,to,unit);

    }


    public void getTradeVolDis(String symbol, String from, String to, String unit){


        ObserverOnNextListener<TradeVolDisResp> listener = new ObserverOnNextListener<TradeVolDisResp>() {
            @Override
            public void onNext(TradeVolDisResp newAndInactivityResp) {
                if(getView()!=null){
                    getView().onTradeVolDis(newAndInactivityResp);
                }
            }

            @Override
            public void onError() {

                if(getView()!=null) {
                    getView().onError();
                }

            }
        };

        ApiMethods.getTradeVolDis(new MyObserver<TradeVolDisResp>(listener),symbol,from,to,unit);

    }

    public void getTradeCountDis(String symbol, String from, String to, String unit){


        ObserverOnNextListener<TradeCountDisResp> listener = new ObserverOnNextListener<TradeCountDisResp>() {
            @Override
            public void onNext(TradeCountDisResp newAndInactivityResp) {
                if(getView()!=null){
                    getView().onTradeCountDis(newAndInactivityResp);
                }
            }

            @Override
            public void onError() {
                if(getView()!=null) {
                    getView().onError();
                }
            }
        };

        ApiMethods.getTradeCountDis(new MyObserver<TradeCountDisResp>(listener),symbol,from,to,unit);

    }


//    public void getDiffPower(String from, String to, String unit){
//
//
//        ObserverOnNextListener<DiffPowerResp> listener = new ObserverOnNextListener<DiffPowerResp>() {
//            @Override
//            public void onNext(DiffPowerResp newAndInactivityResp) {
//                if(getView()!=null){
//                    getView().onDiffPower(newAndInactivityResp);
//                }
//            }
//
//            @Override
//            public void onError() {
//                if(getView()!=null) {
//                    getView().onError();
//                }
//            }
//        };
//
//        ApiMethods.getDiffPower(new MyObserver<DiffPowerResp>(listener),from,to,unit);
//
//    }











//    //新增与休眠账户
//    public void getNewAndInactivity(String symbol, String from, String to, String unit){
//
//
//        ObserverOnNextListener<NewAndInactivityResp> listener = new ObserverOnNextListener<NewAndInactivityResp>() {
//            @Override
//            public void onNext(NewAndInactivityResp newAndInactivityResp) {
//                if(getView()!=null){
//                    getView().onNewAndInactivity(newAndInactivityResp);
//                }
//            }
//
//            @Override
//            public void onError() {
//                if(getView()!=null) {
//                    getView().onError();
//                }
//            }
//        };
//
//        ApiMethods.getNewAndInactivity(new MyObserver<NewAndInactivityResp>(listener),symbol,from,to,unit);
//
//    }

    //Top100
    public void getTop100(String symbol, String date){


        ObserverOnNextListener<Top100Resp> listener = new ObserverOnNextListener<Top100Resp>() {
            @Override
            public void onNext(Top100Resp newAndInactivityResp) {
                if(getView()!=null){
                    getView().ontop(newAndInactivityResp);
                }
            }

            @Override
            public void onError() {
                if(getView()!=null) {
                    getView().onError();
                }
            }
        };

        ApiMethods.getTop100(new MyObserver<Top100Resp>(listener),symbol,date);

    }


    public void Top100Trade(String symbol,String date,String unit){


        ObserverOnNextListener<TopFreqAddrResp> listener = new ObserverOnNextListener<TopFreqAddrResp>() {
            @Override
            public void onNext(TopFreqAddrResp newAndInactivityResp) {
                if(getView()!=null){
                    getView().TopTrade(newAndInactivityResp);
                }
            }

            @Override
            public void onError() {
                if(getView()!=null) {
                    getView().onError();
                }
            }
        };

        ApiMethods.Top100Trade(new MyObserver<TopFreqAddrResp>(listener),symbol,date,unit);

    }


    public void TopPlayer(String id){


        ObserverOnNextListener<TopFreqAddrResp> listener = new ObserverOnNextListener<TopFreqAddrResp>() {
            @Override
            public void onNext(TopFreqAddrResp newAndInactivityResp) {
                if(getView()!=null){
                    getView().TopPlayer(newAndInactivityResp);
                }
            }

            @Override
            public void onError() {
                if(getView()!=null) {
                    getView().onError();
                }
            }
        };

        ApiMethods.TopPlayer(new MyObserver<TopFreqAddrResp>(listener),id);

    }

     //大额转账趋势
//    public void getBigTradeCountDis(String symbol, String from, String to, String unit){
//
//        ObserverOnNextListener<BigTradeCountDisResp> listener = new ObserverOnNextListener<BigTradeCountDisResp>() {
//            @Override
//            public void onNext(BigTradeCountDisResp newAndInactivityResp) {
//                if(getView()!=null){
//                    getView().onBigTradeCountDis(newAndInactivityResp);
//                }
//            }
//
//            @Override
//            public void onError() {
//                if(getView()!=null) {
//                    getView().onError();
//                }
//            }
//        };
//
//        ApiMethods.getBigTradeCountDis(new MyObserver<BigTradeCountDisResp>(listener),symbol,from,to,unit);
//
//    }

//    //交易热度
//    public void getTrade(String symbol, String from, String to, String unit){
//
//        ObserverOnNextListener<TradeResp> listener = new ObserverOnNextListener<TradeResp>() {
//            @Override
//            public void onNext(TradeResp newAndInactivityResp) {
//                if(getView()!=null){
//                    getView().onTrade(newAndInactivityResp);
//                }
//            }
//
//            @Override
//            public void onError() {
//                if(getView()!=null) {
//                    getView().onError();
//                }
//            }
//        };
//
//        ApiMethods.getTrade(new MyObserver<TradeResp>(listener),symbol,from,to,unit);
//
//    }




//    public void getGllAddCount(String symbol, String from, String to, String unit){
//
//        ObserverOnNextListener<AllAddCountResp> listener = new ObserverOnNextListener<AllAddCountResp>() {
//            @Override
//            public void onNext(AllAddCountResp newAndInactivityResp) {
//                if(getView()!=null){
//                    getView().onGllAddCount(newAndInactivityResp);
//                }
//            }
//
//            @Override
//            public void onError() {
//                if(getView()!=null) {
//                    getView().onError();
//                }
//            }
//        };
//
//        ApiMethods.getGllAddCount(new MyObserver<AllAddCountResp>(listener),symbol,from,to,unit);
//
//    }

//
//    public void getPrice(String symbol, String from, String to, String unit){
//
//        ObserverOnNextListener<AvgTrdVolResp> listener = new ObserverOnNextListener<AvgTrdVolResp>() {
//            @Override
//            public void onNext(AvgTrdVolResp newAndInactivityResp) {
//                if(getView()!=null){
//                    getView().onPrice(newAndInactivityResp);
//                }
//            }
//
//            @Override
//            public void onError() {
//                if(getView()!=null) {
//                    getView().onError();
//                }
//            }
//        };
//
//        ApiMethods.getPrice(new MyObserver<AvgTrdVolResp>(listener),symbol,from,to,unit);
//
//    }




    public void getIntroInfo(String name,String  language){

        ObserverOnNextListener<IntroInfoResp> listener = new ObserverOnNextListener<IntroInfoResp>() {
            @Override
            public void onNext(IntroInfoResp newAndInactivityResp) {
                if(getView()!=null){
                    getView().onntroInfo(newAndInactivityResp);
                }
            }

            @Override
            public void onError() {
                if(getView()!=null) {
                    getView().onError();
                }
            }
        };

        ApiMethods.getIntroInfo(new MyObserver<IntroInfoResp>(listener),name,language);

    }









}
