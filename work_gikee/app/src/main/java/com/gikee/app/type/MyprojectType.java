package com.gikee.app.type;

public enum MyprojectType {

    top100("top100持币地址资产分布"), ownerDis("持币地址分布 "),specialTrade("特殊交易监控 "),bigTrade("大额转账趋势 "),newAndWake("新增和唤醒地址趋势")
    ,activeDis("活跃地址趋势 "),acountDis("账户地址趋势"),tradevolume("交易量"),tradenum("交易笔数"),tradehot("交易热度"),circulation("流通市值"),difficulty("挖矿难度与算力"),frequenttrade("平均每笔交易量")
    ,addressTrade("地址交易频次排名"),transVolume("交易量分布"),transAccount("交易笔数分布"),totalcount("总地址及其持币地址数"),Handfee("手续费总消耗"),Handfeetotl("手续费成本");

    MyprojectType(String content) {
        this.content = content;
    }

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
