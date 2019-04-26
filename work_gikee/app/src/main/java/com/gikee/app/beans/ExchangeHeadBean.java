package com.gikee.app.beans;

public class ExchangeHeadBean {

    private  String id;

    private  String name;

    private  String alias;

    private  String logo;

    private  String tags;

    private  String tags_cn;

    private  int rank;

    private  String volume_24H;

    private  String volume_24H_cny;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTags_cn() {
        return tags_cn;
    }

    public void setTags_cn(String tags_cn) {
        this.tags_cn = tags_cn;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getVolume_24H() {
        return volume_24H;
    }

    public void setVolume_24H(String volume_24H) {
        this.volume_24H = volume_24H;
    }

    public String getVolume_24H_cny() {
        return volume_24H_cny;
    }

    public void setVolume_24H_cny(String volume_24H_cny) {
        this.volume_24H_cny = volume_24H_cny;
    }
}
