package com.gikee.app.presenter.search;

import com.gikee.app.resp.AddressAddedResp;
import com.gikee.app.resp.MonitorTradeResp;
import com.gikee.app.resp.SpecialAccountResp;

public interface SpecialSearchView {

    void onSpecialSearchView(SpecialAccountResp resp);

    void onAllAccount(SpecialAccountResp resp);

//    void onSpecialAccountList(SpecialAccountResp resp);

    void onMineAddress(AddressAddedResp resp);


    void onMonitorTrade(MonitorTradeResp resp);

    void onError();
}
