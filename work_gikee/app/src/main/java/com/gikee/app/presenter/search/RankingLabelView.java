package com.gikee.app.presenter.search;

import com.gikee.app.resp.RankingDetailResp;
import com.gikee.app.resp.RankingLabelResp;

public interface RankingLabelView {

    void onRanklabel(RankingLabelResp resp);

    void onRankDetail(RankingDetailResp resp);

    void onError();
}
