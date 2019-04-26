package com.gikee.app.beans;

import com.gikee.app.resp.ResponseInfo;

public class CollectTrandBean extends ResponseInfo{

    private String trandname;

    private String tranditems;

    private String addressid;

    private String logo;

    private boolean isCollect=false;

    public String getTrandname() {
        return trandname;
    }

    public void setTrandname(String trandname) {
        this.trandname = trandname;
    }


    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    public String getTranditems() {
        return tranditems;
    }

    public void setTranditems(String tranditems) {
        this.tranditems = tranditems;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }


}
