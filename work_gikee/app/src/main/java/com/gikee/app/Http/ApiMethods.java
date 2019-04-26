package com.gikee.app.Http;

import com.gikee.app.beans.AllProjectBean;
import com.gikee.app.beans.FrequenTradeResp;
import com.gikee.app.beans.SearchResp;
import com.gikee.app.beans.TodayAddBean;
import com.gikee.app.beans.TransationFenBuBean;
import com.gikee.app.beans.ZhangHuFenBuBean;
import com.gikee.app.resp.ActiveAccountResp;
import com.gikee.app.resp.AddressAddedResp;
import com.gikee.app.resp.AddressCountResp;
import com.gikee.app.resp.AddressDetailResp;
import com.gikee.app.resp.AllAddCountResp;
import com.gikee.app.resp.AllGasResp;
import com.gikee.app.resp.AssetResp;
import com.gikee.app.resp.AvgGasResp;
import com.gikee.app.resp.AvgTrdVolResp;
import com.gikee.app.resp.BTCAddressReap;
import com.gikee.app.resp.BTCTradeDetailResp;
import com.gikee.app.resp.BTCTradeListResp;
import com.gikee.app.resp.BaseLineAllResp;
import com.gikee.app.resp.BaseLineResp;
import com.gikee.app.resp.BaseResp;
import com.gikee.app.resp.BigTradeCountDisResp;
import com.gikee.app.resp.DiffPowerResp;
import com.gikee.app.resp.EOSTradeDetailResp;
import com.gikee.app.resp.ERC20ListResp;
import com.gikee.app.resp.ExchangeResp;
import com.gikee.app.resp.HashDetailResp;
import com.gikee.app.resp.HashTradeResp;
import com.gikee.app.resp.HotProjectResp;
import com.gikee.app.resp.IntroInfoResp;
import com.gikee.app.resp.IsAddressResp;
import com.gikee.app.resp.LookAroundResp;
import com.gikee.app.resp.MarketRateResp;
import com.gikee.app.resp.MarketValueResp;
import com.gikee.app.resp.MonitorTradeResp;
import com.gikee.app.resp.NewAndInactivityResp;
import com.gikee.app.resp.OwnerDistributeResp;
import com.gikee.app.resp.PowerResp;
import com.gikee.app.resp.ProjectCompaResp;
import com.gikee.app.resp.ProjectInfoResp;
import com.gikee.app.resp.RankingDetailResp;
import com.gikee.app.resp.RankingLabelResp;
import com.gikee.app.resp.RateBeanResp;
import com.gikee.app.resp.RateResp;
import com.gikee.app.resp.RemindInfoResp;
import com.gikee.app.resp.SpecialAccountResp;
import com.gikee.app.resp.SpecialAddressResp;
import com.gikee.app.resp.SummaryResp;
import com.gikee.app.resp.TokenTypeResp;
import com.gikee.app.resp.TokensAddedResp;
import com.gikee.app.resp.Top100Bean;
import com.gikee.app.resp.Top100Resp;
import com.gikee.app.resp.TopFreqAddrResp;
import com.gikee.app.resp.TradeCountDisResp;
import com.gikee.app.resp.TradeResp;
import com.gikee.app.resp.TradeVolDisResp;
import com.gikee.app.resp.ValueResp;
import com.gikee.app.resp.VersionResp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Query;

/**
 * Created by DeMon on 2017/9/6.
 */

public class ApiMethods {

    /**
     * 封装线程管理和订阅的过程
     */
    public static void ApiSubscribe(Observable observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }




    public static void getSearch(Observer<SearchResp> observer, String content,String type) {
        ApiSubscribe(ApiStrategy.getApiService().getSearch(content,type), observer);
    }


    public static void getFuzzySearch(Observer<SearchResp> observer, String content,String type,int page) {
        ApiSubscribe(ApiStrategy.getApiService().getFuzzySearch(content,type,page), observer);
    }


    public static void getSpecialSearch(Observer<SpecialAccountResp> observer, String content,int page) {
        ApiSubscribe(ApiStrategy.getApiService().getSpecialSearch(content,page), observer);
    }


    public static void getAllSpecialAccount(Observer<SpecialAccountResp> observer,int page) {
        ApiSubscribe(ApiStrategy.getApiService().getAllSpecialAccount(page), observer);
    }



    public static void getSpecialAccountList(Observer<SpecialAccountResp> observer, String content,String symbol,String coin) {
        ApiSubscribe(ApiStrategy.getApiService().getSpecialAccountList(content,symbol,coin), observer);
    }

    public static void getMarketRate(Observer<MarketRateResp> observer) {
        ApiSubscribe(ApiStrategy.getApiService().getMarketRate(), observer);
    }


    public static void getPower(Observer<PowerResp> observer,String date) {
        ApiSubscribe(ApiStrategy.getApiService().getPower(date), observer);
    }


    public static void getchain(Observer<SummaryResp> observer,String id,String date) {
        ApiSubscribe(ApiStrategy.getApiService().getChain(id,date), observer);
    }





    public static void getMarketTrend(Observer<ValueResp> observer, String date) {
        ApiSubscribe(ApiStrategy.getApiService().getMarketTrend(date), observer);
    }





    public static void getRanklabel(Observer<RankingLabelResp> observer) {
        ApiSubscribe(ApiStrategy.getApiService().getRanklabel(), observer);
    }


    public static void getRankDetail(Observer<RankingDetailResp> observer,String lableId,int page,String rankedBy,String reverse) {
        ApiSubscribe(ApiStrategy.getApiService().getRankDetail(lableId,page,rankedBy,reverse), observer);
    }


//    public static void getRankDetail2(Observer<RankingDetailResp> observer,String lableId,int start,int limit,String choseType) {
//        ApiSubscribe(ApiStrategy.getApiService().getRankDetail(lableId,start,limit,choseType), observer);
//    }


    public static void getValue(Observer<ValueResp> observer,String method,String id, String date_from, String date_to, String unit) {
        ApiSubscribe(ApiStrategy.getApiService().getValue(method,id,date_from,date_to,unit), observer);
    }




    public static void getTop100(Observer<Top100Resp> observer, String symbol, String date) {
        ApiSubscribe(ApiStrategy.getApiService().getTop100(symbol,date), observer);
    }

    public static void Top100Trade(Observer<TopFreqAddrResp> observer, String symbol,String date,String util) {
        ApiSubscribe(ApiStrategy.getApiService().Top100Trade(symbol,date,util), observer);
    }

    public static void TopPlayer(Observer<TopFreqAddrResp> observer, String id) {
        ApiSubscribe(ApiStrategy.getApiService().TopPlayer(id), observer);
    }


    public static void getOwnerDistribute(Observer<OwnerDistributeResp> observer, String symbol, String from, String to, String unit) {
        ApiSubscribe(ApiStrategy.getApiService().getOwnerDistribute(symbol,from,to,unit), observer);
    }


    public static void getPrice(Observer<AvgTrdVolResp> observer, String symbol, String from, String to, String unit) {
        ApiSubscribe(ApiStrategy.getApiService().getPrice(symbol,from,to,unit), observer);
    }


    public static void getIntroInfo(Observer<IntroInfoResp> observer, String name,String language) {
        ApiSubscribe(ApiStrategy.getApiService().getIntroInfo(name,language), observer);
    }




    public static void  getTradeVolDis(Observer<TradeVolDisResp> observer, String symbol, String from, String to, String unit) {
        ApiSubscribe(ApiStrategy.getApiService().getTradeVolDis(symbol,from,to,unit), observer);
    }


    public static void  getTradeCountDis(Observer<TradeCountDisResp> observer, String symbol, String from, String to, String unit) {
        ApiSubscribe(ApiStrategy.getApiService().getTradeCountDis(symbol,from,to,unit), observer);
    }

    public static void getMineProject(Observer<TokensAddedResp> observer,String coinSymbols) {
        ApiSubscribe(ApiStrategy.getApiService().getMineProject(coinSymbols),observer);
    }

    public static void getMineAddress(Observer<AddressAddedResp> observer, Map<String,List<String>> map) {
        ApiSubscribe(ApiStrategy.getApiService().getMineAddress(map),observer);
    }



    public static void getMonitorTrade(Observer<MonitorTradeResp> observer, Map map) {
        ApiSubscribe(ApiStrategy.getApiService().getMonitorTrade(map),observer);
    }



    public static void getTradeCount(Observer<AddressCountResp> observer, Map<String,Long> map) {
        ApiSubscribe(ApiStrategy.getApiService().getTradeCount(map),observer);
    }


    public static void getAssetData(Observer<AssetResp> observer, Map map) {
        ApiSubscribe(ApiStrategy.getApiService().getAssetData(map),observer);
    }

    public static void getAccountTrade(Observer<HashTradeResp> observer, Map map) {
        ApiSubscribe(ApiStrategy.getApiService().getAccountTrade(map),observer);
    }





    public static void getRemindInfo(Observer<RemindInfoResp> observer,String page) {
        ApiSubscribe(ApiStrategy.getApiService().getRemindInfo(page),observer);
    }


    public static void getShujuFenXi(Observer<SummaryResp> observer,String item, String coinSymbol) {
        ApiSubscribe(ApiStrategy.getApiService().getShujuFenXi(item,coinSymbol),observer);
    }

    public static void getTradeIndex(Observer<SummaryResp> observer,String item, String coinSymbol) {
        ApiSubscribe(ApiStrategy.getApiService().getTradeIndex(item,coinSymbol),observer);
    }

    public static void getAddrIndex(Observer<SummaryResp> observer,String item, String coinSymbol) {
        ApiSubscribe(ApiStrategy.getApiService().getAddrIndex(item,coinSymbol),observer);
    }


    public static void getAllIndex(Observer<SummaryResp> observer, String coinSymbol) {
        ApiSubscribe(ApiStrategy.getApiService().getAllIndex(coinSymbol),observer);
    }

    public static void getIndexContrast(Observer<SummaryResp> observer, String coinSymbol,String date) {
        ApiSubscribe(ApiStrategy.getApiService().getIndexConftrast(coinSymbol,date),observer);
    }

    public static void getprojectCompar(Observer<ProjectCompaResp> observer, String id, String itemName,String date) {
        ApiSubscribe(ApiStrategy.getApiService().getprojectCompar(id,itemName,date),observer);
    }


    public static void getExchange(Observer<ExchangeResp> observer, String id,String type,String p) {
        ApiSubscribe(ApiStrategy.getApiService().getExchange(id,type,p),observer);
    }







    public static void getProjectInfo(Observer<ProjectInfoResp> observer, String id,String language) {
        ApiSubscribe(ApiStrategy.getApiService().getProjectInfo(id,language),observer);
    }


    public static void getZhanghuTop(Observer<Top100Bean> observer, String top100ownerPerCoin, String coinSymbol, String date_from, String date_to, String s, String choseType) {
        ApiSubscribe(ApiStrategy.getApiService().getZhanghuTop(top100ownerPerCoin,coinSymbol,date_from,date_to,s,choseType),observer);
    }



    public static void getLookAround(Observer<LookAroundResp> observer, String start, String limit) {
        ApiSubscribe(ApiStrategy.getApiService().getLookAround(start,limit),observer);
    }

    public static void getHot(Observer<HotProjectResp> observer, String start, String limit) {
        ApiSubscribe(ApiStrategy.getApiService().getHot(start,limit),observer);
    }




    public static void getSearchAddressTkoen(Observer<TokenTypeResp> observer, String address) {
        ApiSubscribe(Api.getApiService().getSearchAddressTkoen(address),observer);
    }





    public static void getSpecialAddress(Observer<SpecialAddressResp> observer, String symbol, String start, String limit, String starDate, String endDate) {
        ApiSubscribe(Api.getApiService().getSpecialAddress(symbol,start,limit,starDate,endDate),observer);
    }


    public static void getIsAddress(Observer<IsAddressResp> observer, String params, String type) {
        ApiSubscribe(ApiStrategy.getApiService().getIsAddress(params,type),observer);
    }

    //获取地址详情
    public static void getAddressDetail(Observer<AddressDetailResp> observer,String type,String paramstype,String params) {
        ApiSubscribe(ApiStrategy.getApiService().getAddressDetail(type,paramstype,params),observer);
    }

    //获取地址详情页的交易列表

    public static void getTradeList(Observer<HashTradeResp> observer,String type, String address, int page) {
        ApiSubscribe(ApiStrategy.getApiService().getTradeList(type,address,page),observer);
    }


    //ERC20内部交易和合约内部交易
    public static void getAddressTrans(Observer<ERC20ListResp> observer, String address, String type, int page) {
        ApiSubscribe(ApiStrategy.getApiService().getAddressTrans(address,type,page),observer);
    }


    public static void getBTCTradeList(Observer<BTCTradeListResp> observer, String address, int page) {
        ApiSubscribe(ApiStrategy.getApiService().getBTCTradeList(address,page),observer);
    }




    public static void getBTCAddressDetail(Observer<BTCAddressReap> observer, String type, String paramstype, String params) {
        ApiSubscribe(ApiStrategy.getApiService().getBTCAddressDetail(type,paramstype,params),observer);
    }

    public static void getHashDetail(Observer<HashDetailResp> observer, String type, String paramstype, String params) {
        ApiSubscribe(ApiStrategy.getApiService().getHashDetail(type,paramstype,params),observer);
    }


    public static void getEOSAddressDetail(Observer<AddressDetailResp> observer, String type, String paramstype, String params) {
        ApiSubscribe(ApiStrategy.getApiService().getEOSAddressDetail(type,paramstype,params),observer);
    }

    public static void getBTCTradeDetail(Observer<BTCTradeDetailResp> observer, String type, String paramstype, String params) {
        ApiSubscribe(ApiStrategy.getApiService().getBTCTradeDetail(type,paramstype,params),observer);
    }



    public static void getEOSradeDetail(Observer<EOSTradeDetailResp> observer, String type, String paramstype, String params) {
        ApiSubscribe(ApiStrategy.getApiService().getEOSradeDetail(type,paramstype,params),observer);
    }


    public static void getSpeciallist(Observer<SpecialAddressResp> observer,String type,String address, String symbol, String start, String limit, String starDate, String endDate) {
        ApiSubscribe(Api.getApiService().getSpecialList(type,address,symbol,start,limit,starDate,endDate),observer);
    }

    public static void getBigData(Observer<SpecialAddressResp> observer, String symbol, String start, String limit, String starDate, String endDate) {
        ApiSubscribe(Api.getApiService().getBigData(symbol,start,limit,starDate,endDate),observer);
    }

    public static void getFrequentlyTrade(Observer<FrequenTradeResp> observer, String symbol, String start, String limit, String starDate, String endDate) {
        ApiSubscribe(Api.getApiService().getFrequentlyTrade(symbol,start,limit,starDate,endDate),observer);
    }


    public static void checkAppUpdate(Observer<VersionResp> observer, String versionname,String language) {
        ApiSubscribe(ApiStrategy.getApiService().checkAppUpdate(versionname,language),observer);
    }

    public static void sendToken(Observer<BaseResp> observer, String token,String model) {
        ApiSubscribe(ApiStrategy.getApiService().sendToken(token,model),observer);
    }

    public static void getExchangeRate(Observer<RateResp> observer) {
        ApiSubscribe(ApiStrategy.getApiService().getExchangeRate(),observer);
    }


    //获取首页基础链信息


    public static void getHomeBaseLine(Observer<BaseLineResp> observer) {
        ApiSubscribe(ApiStrategy.getApiService().getHomeBaseLine(),observer);
    }


    public static void RateBeanResp(Observer<RateBeanResp> observer) {
        ApiSubscribe(ApiStrategy.getApiService().getrealTimeRate(),observer);
    }


}
