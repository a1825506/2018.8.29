package com.gikee.app.presenter.project;

import com.gikee.app.resp.RemindInfoResp;

public interface RemindInfoView {

    public void onRemindInfo(RemindInfoResp remindInfoResp);

    public void onError();
}
