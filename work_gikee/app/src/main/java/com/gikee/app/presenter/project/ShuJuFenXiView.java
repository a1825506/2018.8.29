package com.gikee.app.presenter.project;

import com.gikee.app.resp.ProjectCompaResp;
import com.gikee.app.resp.ProjectInfoResp;
import com.gikee.app.resp.SummaryResp;

/**
 * @author tgh
 * @date 18-8-6
 * @time 下午6:37
 * @describe TODO
 * @email a18255064049@163.com
 */
public interface ShuJuFenXiView {

    void onShuJuFenXi(SummaryResp shuJuFenXiBean);

    void onIndexContrast(SummaryResp shuJuFenXiBean);

    void onProjectInfo(ProjectInfoResp projectInfoResp);

    void onProjectCompar(ProjectCompaResp projectCompaResp);

    void onError();
}
