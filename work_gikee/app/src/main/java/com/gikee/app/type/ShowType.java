package com.gikee.app.type;

public enum ShowType {

    pieLeft("pieLeft"), pieRight("pieRight"),table("table"),lineWave("lineWave"),frequentTrade("frequentTrade"),bigTrade("bigTrade"),specialAddress("specialAddress")
    ,topFreqAddr("topFreqAddr"),topPlayer("topPlayer"),quotes("quotes"),project("project"),account("account"),exchange("exchange"),fuzzysearch("fuzzysearch")
    ,mining("mining"),Foundation("Foundation"),events("events"),phish("phish"),hack("hack"),celebrity("celebrity"),eth("eth"),btc("btc"),eos("eos");

    ShowType(String content) {
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
