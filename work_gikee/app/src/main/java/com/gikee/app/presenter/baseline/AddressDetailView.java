package com.gikee.app.presenter.baseline;

import com.gikee.app.resp.AddressDetailResp;
import com.gikee.app.resp.BTCAddressReap;
import com.gikee.app.resp.BTCTradeDetailResp;
import com.gikee.app.resp.BTCTradeListResp;
import com.gikee.app.resp.EOSTradeDetailResp;
import com.gikee.app.resp.ERC20ListResp;
import com.gikee.app.resp.HashDetailResp;
import com.gikee.app.resp.HashTradeResp;

public interface AddressDetailView {

    void onAddressDetail(AddressDetailResp addressDetailResp);

    void onTradeList(HashTradeResp specialAddressResp);

    void onAddressTrans(ERC20ListResp resp);

    void onBTCTradeList(BTCTradeListResp resp);

    void onBTCAddressDetail(BTCAddressReap btcAddressReap);

    void HashDetail(HashDetailResp hashDetailResp);

    void onEOSAddress(AddressDetailResp eosAddressResp);

    void onBTCTradeDetail(BTCTradeDetailResp btcTradeDetailResp );

    void onEOSTradeDetail(EOSTradeDetailResp eosTradeDetailResp);

    void onError();
}
