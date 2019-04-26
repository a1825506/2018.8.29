package com.gikee.app.presenter.project;

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
 * @time 下午5:30
 * @describe TODO
 * @email a18255064049@163.com
 */
public interface AccountView {



    void onValue(ValueResp valueResp);

    void onTradeVolDis(TradeVolDisResp resp);

    void onTradeCountDis(TradeCountDisResp resp);

    void onOwnerDistribute(OwnerDistributeResp resp);

    void ontop(Top100Resp resp);

    void TopTrade(TopFreqAddrResp resp);

    void TopPlayer(TopFreqAddrResp resp);


    void onntroInfo(IntroInfoResp resp);

    void onError();
}
