package com.gikee.app.presenter.project;

import com.gikee.app.resp.AddressAddedResp;
import com.gikee.app.resp.AddressCountResp;
import com.gikee.app.resp.AssetResp;
import com.gikee.app.resp.HashTradeResp;
import com.gikee.app.resp.TokensAddedResp;

/**
 * @author tgh
 * @date 18-8-6
 * @time 下午2:44
 * @describe TODO
 * @email a18255064049@163.com
 */
public interface MineProjectView {

    void onMineProject(TokensAddedResp allProjectCollBean);

    void onMineAddress(AddressAddedResp resp);

    void onMineCount(AddressCountResp resp);

    void onAccountTrade(HashTradeResp resp);

    void onAssetData(AssetResp resp);

    void onError();
}
