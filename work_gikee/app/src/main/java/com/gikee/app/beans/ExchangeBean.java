package com.gikee.app.beans;

public class ExchangeBean {

    private ExchangeHeadBean exchange_data;

    private PairBean pair_data;

    private ExchangeIntroBean intro_data;


    public ExchangeHeadBean getExchange_data() {
        return exchange_data;
    }

    public void setExchange_data(ExchangeHeadBean exchange_data) {
        this.exchange_data = exchange_data;
    }

    public PairBean getPair_data() {
        return pair_data;
    }

    public void setPair_data(PairBean pair_data) {
        this.pair_data = pair_data;
    }

    public ExchangeIntroBean getIntro_data() {
        return intro_data;
    }

    public void setIntro_data(ExchangeIntroBean intro_data) {
        this.intro_data = intro_data;
    }
}
