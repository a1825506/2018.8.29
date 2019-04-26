package com.gikee.app.presenter.version;

import com.gikee.app.resp.RateBeanResp;
import com.gikee.app.resp.RateResp;
import com.gikee.app.resp.VersionResp;

public interface VersionView {

    void onVersionInfo(VersionResp versionResp);

    void onExchangeRate(RateResp resp);

    void onBTCRate(RateBeanResp resp);
}
