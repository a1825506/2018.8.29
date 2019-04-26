package com.gikee.app.beans;

public class AssetBean {

    /**
     * asset : 1221
     * asset_usd : 21121121212
     * count : 300
     * type : in
     */

    private double asset;
    private double asset_usd;
    private int count;
    private String type;



    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAsset_usd() {
        return asset_usd;
    }

    public void setAsset_usd(double asset_usd) {
        this.asset_usd = asset_usd;
    }

    public double getAsset() {
        return asset;
    }

    public void setAsset(double asset) {
        this.asset = asset;
    }
}
