package com.gikee.app.presenter.search;

import com.gikee.app.beans.SearchBean;
import com.gikee.app.beans.SearchResp;
import com.gikee.app.resp.AddressDetailResp;
import com.gikee.app.resp.BTCTradeDetailResp;
import com.gikee.app.resp.BaseLineResp;
import com.gikee.app.resp.HotProiectBean;
import com.gikee.app.resp.HotProjectResp;
import com.gikee.app.resp.IsAddressResp;
import com.gikee.app.resp.LookAroundResp;
import com.gikee.app.resp.RankingDetailResp;
import com.gikee.app.resp.TokenTypeResp;
import com.gikee.app.resp.VersionResp;

/**
 * @author tgh
 * @date 18-8-6
 * @time 下午4:36
 * @describe TODO
 * @email a18255064049@163.com
 */
public interface InterfaceSearchView {

    void onSearchView(SearchResp searchBean);

    void onFuzzySearch(SearchResp searchBean);

    void onSearchAddressView(TokenTypeResp tokenTypeResp);

    void onLookAround(LookAroundResp lookAroundResp);

    void onHot(HotProjectResp searchBean);

    void onRankDetail(RankingDetailResp resp);

    void getRankDetail2(RankingDetailResp resp);

    void ontHomeBaseLine(BaseLineResp resp);

    void onBTCTradeDetail(BTCTradeDetailResp btcTradeDetailResp);

    void onEOSTradeDetail(IsAddressResp resp);

    void onError();
}
