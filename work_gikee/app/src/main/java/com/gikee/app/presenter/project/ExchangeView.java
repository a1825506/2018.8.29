package com.gikee.app.presenter.project;

import com.gikee.app.resp.ExchangeResp;

public interface ExchangeView {

    void onExchange(ExchangeResp resp);

    void onError();
}
