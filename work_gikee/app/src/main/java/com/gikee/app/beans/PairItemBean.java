package com.gikee.app.beans;

public class PairItemBean {

    private String pair_one;

    private String pair_two;

    private String price_change;

    private String price_one;

    private String price_pair;

    private String volume_24H;

    public String getPair_one() {
        return pair_one;
    }

    public void setPair_one(String pair_one) {
        this.pair_one = pair_one;
    }

    public String getPair_two() {
        return pair_two;
    }

    public void setPair_two(String pair_two) {
        this.pair_two = pair_two;
    }

    public String getPrice_change() {
        return price_change;
    }

    public void setPrice_change(String price_change) {
        this.price_change = price_change;
    }

    public String getPrice_one() {
        return price_one;
    }

    public void setPrice_one(String price_one) {
        this.price_one = price_one;
    }

    public String getPrice_pair() {
        return price_pair;
    }

    public void setPrice_pair(String price_pair) {
        this.price_pair = price_pair;
    }

    public String getVolume_24H() {
        return volume_24H;
    }

    public void setVolume_24H(String volume_24H) {
        this.volume_24H = volume_24H;
    }
}
