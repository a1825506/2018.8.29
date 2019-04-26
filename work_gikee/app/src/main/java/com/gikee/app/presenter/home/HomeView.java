package com.gikee.app.presenter.home;

import com.gikee.app.resp.MarketRateResp;
import com.gikee.app.resp.PowerResp;
import com.gikee.app.resp.SummaryBean;
import com.gikee.app.resp.SummaryResp;
import com.gikee.app.resp.TopFreqAddrResp;
import com.gikee.app.resp.ValueResp;

public interface HomeView {

    void onMarketRate(MarketRateResp marketRateResp);

    void onMarketTrend(ValueResp valueResp);

    void onPower(PowerResp powerResp);

    void onChain(SummaryResp summaryResp);

    void onTopPlayer(TopFreqAddrResp topFreqAddrResp);

    void onError();
}
