package com.gikee.app.Http;

import com.gikee.app.beans.AllProjectBean;
import com.gikee.app.beans.AllProjectCollBean;
import com.gikee.app.beans.FrequenTradeResp;
import com.gikee.app.beans.SearchBean;
import com.gikee.app.beans.SearchResp;
import com.gikee.app.beans.ShuJuFenXiBean;
import com.gikee.app.beans.TodayAddBean;
import com.gikee.app.beans.TodayTop100Bean;
import com.gikee.app.beans.TransationFenBuBean;
import com.gikee.app.beans.ZhangHuBean;
import com.gikee.app.beans.ZhangHuFenBuBean;
import com.gikee.app.resp.ActiveAccountResp;
import com.gikee.app.resp.AddressAddedResp;
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
import com.gikee.app.resp.EOSAddressResp;
import com.gikee.app.resp.EOSTradeDetailResp;
import com.gikee.app.resp.ERC20ListResp;
import com.gikee.app.resp.ExchangeResp;
import com.gikee.app.resp.HashDetailResp;
import com.gikee.app.resp.HashTradeResp;
import com.gikee.app.resp.HotProiectBean;
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
import com.gikee.app.resp.TradeVolDisResp;
import com.gikee.app.resp.ValueResp;
import com.gikee.app.resp.VersionResp;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by DeMon on 2017/9/6.
 */

public interface ApiService {

    @GET("api/v2/newSearch")
    Observable<SearchResp> getSearch(@Query("content") String content,@Query("type") String type);

    @GET("api/v2/newFuzzySearch")
    Observable<SearchResp> getFuzzySearch(@Query("content") String content,@Query("type") String type,@Query("page") int page);


    @GET("api/v2/specialSearch")
    Observable<SpecialAccountResp> getSpecialSearch(@Query("content") String content,@Query("p") int page);

    @GET("api/v2/allSpecialAccount")
    Observable<SpecialAccountResp> getAllSpecialAccount(@Query("p") int page);


    @GET("api/v2/specialAccountList")
    Observable<SpecialAccountResp> getSpecialAccountList(@Query("content") String context,@Query("symbol") String symbol,@Query("coin") String coin);



    @GET("api/v2/marketValueRatio")
    Observable<MarketRateResp> getMarketRate();


    @GET("api/v2/newhashIndex")
    Observable<PowerResp> getPower(@Query("date") String date);


    @GET("api/v2/newchainIndex")
    Observable<SummaryResp> getChain(@Query("id") String id,@Query("date") String date);




    @GET("api/v2/newtotalMarketCap")
    Observable<ValueResp> getMarketTrend(@Query("date") String date);


    @GET("api/v2/ranking_label")
    Observable<RankingLabelResp> getRanklabel();

    @GET("api/v2/ranking")
    Observable<RankingDetailResp> getRankDetail(@Query("lable") String lableId,@Query("page") int page,@Query("rankedBy") String choseType,@Query("reverse") String reverse);


    @GET("api/v2/{method}")
    Observable<ValueResp> getValue(@Path("method") String method, @Query("id") String id, @Query("from") String from, @Query("to") String to, @Query("unit") String unit);



    @GET("api/v2/token/top100")
    Observable<Top100Resp> getTop100(@Query("symbol") String symbol, @Query("date")String date);


    @GET("api/v2/tradeCountRanking")
    Observable<TopFreqAddrResp> Top100Trade(@Query("id") String symbol, @Query("date")String date,@Query("unit")String unit);


    @GET("api/v2/topPlayer")
    Observable<TopFreqAddrResp> TopPlayer(@Query("id") String id);


    @GET("api/v2/explain")
    Observable<IntroInfoResp> getIntroInfo(@Query("name") String name,@Query("language") String language);


    @GET("api/v2/price")
    Observable<AvgTrdVolResp> getPrice(@Query("symbol") String symbol, @Query("from")String from, @Query("to")String to, @Query("unit")String unit);


    @GET("api/v2/token/ownerDistribute")
    Observable<OwnerDistributeResp> getOwnerDistribute(@Query("symbol") String symbol, @Query("from")String from, @Query("to")String to, @Query("unit")String unit);


    @GET("api/v2/token/tradeVolDis")
    Observable<TradeVolDisResp> getTradeVolDis(@Query("symbol") String symbol, @Query("from")String from, @Query("to")String to, @Query("unit")String unit);


    @GET("api/v2/token/tradeCountDis")
    Observable<TradeCountDisResp> getTradeCountDis(@Query("symbol") String symbol, @Query("from")String from, @Query("to")String to, @Query("unit")String unit);


    @GET("/api/v2/tokensAdded")
    Observable<TokensAddedResp> getMineProject(@Query("id") String coinSymbols);


    @POST("/api/v2/accountMonitoring")
    Observable<AddressAddedResp> getMineAddress(@Body Map<String,List<String>> paramsMap);



    @POST("/api/v2/getMonitorTrade")
    Observable<MonitorTradeResp> getMonitorTrade(@Body Map paramsMap);




    @POST("/api/v2/getTradeCount")
    Observable<AddressAddedResp> getTradeCount(@Body Map<String,Long> paramsMap);


    @POST("/api/v2/ethAssetdetail")
    Observable<AssetResp> getAssetData(@Body  Map paramsMap);


    @POST("/api/v2/getAccountTrade")
    Observable<HashTradeResp> getAccountTrade(@Body  Map paramsMap);



    @GET("/api/v2/historyMessageList")
    Observable<RemindInfoResp> getRemindInfo(@Query("p") String page);



    @GET("api/v2/token/summary")
    Observable<SummaryResp> getShujuFenXi(@Query("items") String items, @Query("symbol") String coinSymbol);

    @GET("api/v2/token/tradeIndex")
    Observable<SummaryResp> getTradeIndex(@Query("items") String items, @Query("symbol") String coinSymbol);

    @GET("api/v2/token/addrIndex")
    Observable<SummaryResp> getAddrIndex(@Query("items") String items, @Query("id") String id);


    @GET("api/v2/allIndex")
    Observable<SummaryResp> getAllIndex( @Query("id") String id);


    @GET("api/v2/newindexContrast")
    Observable<SummaryResp> getIndexConftrast( @Query("id") String id,@Query("date") String date);

    @GET("api/v2/newprojectComparison")
    Observable<ProjectCompaResp> getprojectCompar(@Query("id") String id,@Query("itemName") String itemName,@Query("date") String date);


    @GET("api/v2/exchange")
    Observable<ExchangeResp> getExchange(@Query("id") String id, @Query("type") String type,@Query("p") String p);



    @GET("api/v2/coinInfo")
    Observable<ProjectInfoResp> getProjectInfo(@Query("id") String id, @Query("language") String language);

    @GET("projects/{method}")
    Observable<Top100Bean> getZhanghuTop(@Path("method") String method, @Query("coinSymbol") String coinSymbol, @Query("from") String from, @Query("to") String to
            , @Query("maxItem") String maxItem, @Query("unit") String unit);


    @GET("addressMonitor/tokens")
    Observable<TokenTypeResp> getSearchAddressTkoen(@Query("address") String address);


    @GET("api/v2/lookAround")
    Observable<LookAroundResp> getLookAround(@Query("start") String start, @Query("limit") String limit);


    @GET("api/v2/HotProjects")
    Observable<HotProjectResp> getHot(@Query("start") String start, @Query("limit") String limit);


    @GET("/api/v2/token/specialAddress")
    Observable<SpecialAddressResp> getSpecialAddress(@Query("symbol") String symbol, @Query("start") String start, @Query("limit") String limit, @Query("startDate") String starDate, @Query("endDate") String endDate);


     @GET("/api/v2/checkAddress")
     Observable<IsAddressResp> getIsAddress(@Query("params") String params, @Query("type") String type);


    @GET("/api/v2/addressDetail")
    Observable<AddressDetailResp> getAddressDetail(@Query("type") String type, @Query("paramstype") String paramstype, @Query("params") String params);


    @GET("/api/v2/ethAddressTx")
    Observable<HashTradeResp> getTradeList(@Query("type") String type,@Query("address") String address, @Query("page") int page);


    @GET("/api/v2/ethAddressTrans")
    Observable<ERC20ListResp> getAddressTrans(@Query("a") String address, @Query("type") String type, @Query("p") int page);


    @GET("/api/v2/btcAddressTx")
    Observable<BTCTradeListResp> getBTCTradeList(@Query("address") String address, @Query("page") int page);

    @GET("/api/v2/btcAddressDetail")
    Observable<BTCAddressReap> getBTCAddressDetail(@Query("type") String type, @Query("paramstype") String paramstype, @Query("params") String params);

    @GET("/api/v2/hashDetail")
    Observable<HashDetailResp> getHashDetail(@Query("type") String type, @Query("paramstype") String paramstype, @Query("params") String params);


    @GET("/api/v2/eosDetail")
    Observable<AddressDetailResp> getEOSAddressDetail(@Query("type") String type, @Query("paramstype") String paramstype, @Query("params") String params);

    @GET("/api/v2/btcHashDetail")
    Observable<BTCTradeDetailResp> getBTCTradeDetail(@Query("type") String type, @Query("paramstype") String paramstype, @Query("params") String params);

    @GET("/api/v2/eosHashDetail")
    Observable<EOSTradeDetailResp> getEOSradeDetail(@Query("type") String type, @Query("paramstype") String paramstype, @Query("params") String params);


    @GET("/api/v2/token/specialList")
    Observable<SpecialAddressResp> getSpecialList(@Query("type") String type,@Query("address") String address, @Query("symbol") String symbol, @Query("start") String start, @Query("limit") String limit, @Query("startDate") String starDate, @Query("endDate") String endDate);


    @GET("/api/v2/token/bigTrade")
    Observable<SpecialAddressResp> getBigData(@Query("symbol") String symbol, @Query("start") String start, @Query("limit") String limit, @Query("startDate") String starDate, @Query("endDate") String endDate);


    @GET("/api/v2/token/frequentTrade")
    Observable<FrequenTradeResp> getFrequentlyTrade(@Query("symbol") String symbol, @Query("start") String start, @Query("limit") String limit, @Query("startDate") String starDate, @Query("endDate") String endDate);



    @GET("api/v2/version")
    Observable<VersionResp> checkAppUpdate(@Query("current") String versionName,@Query("language") String language);

    @GET("api/v2/setDevicePushId")
    Observable<BaseResp> sendToken(@Query("pushid") String token,@Query("model") String model);

    @GET("api/v2/exchangeRate")
    Observable<RateResp> getExchangeRate();


    @GET("api/v2/baseChain")
    Observable<BaseLineResp> getHomeBaseLine();

    @GET("api/v2/realTimeRate")
    Observable<RateBeanResp> getrealTimeRate();





}
