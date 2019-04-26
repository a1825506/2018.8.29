package com.gikee.app.presenter.mineaddress;

import com.gikee.app.beans.FrequenTradeResp;
import com.gikee.app.resp.SpecialAddressResp;

public interface SpecialAddressView {

    void onSpecialAddress(SpecialAddressResp specialAddressBean);

    void onSpecialList(SpecialAddressResp specialAddressBean);

    void onBigData(SpecialAddressResp specialAddressBean);

    void onFrequentlyTrade(FrequenTradeResp specialAddressBean);
}
