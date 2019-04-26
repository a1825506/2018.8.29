package com.gikee.app.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TrandBean {

    @Id(autoincrement = true)
    private Long id;

    private String symbol;

    private String trandname;

    private String trandname_en;

    private String trandid;

    private Integer trandnum;

    private boolean iscollect;

    @Generated(hash = 537539749)
    public TrandBean(Long id, String symbol, String trandname, String trandname_en,
            String trandid, Integer trandnum, boolean iscollect) {
        this.id = id;
        this.symbol = symbol;
        this.trandname = trandname;
        this.trandname_en = trandname_en;
        this.trandid = trandid;
        this.trandnum = trandnum;
        this.iscollect = iscollect;
    }

    @Generated(hash = 701839347)
    public TrandBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTrandname() {
        return this.trandname;
    }

    public void setTrandname(String trandname) {
        this.trandname = trandname;
    }

    public String getTrandid() {
        return this.trandid;
    }

    public void setTrandid(String trandid) {
        this.trandid = trandid;
    }

    public boolean getIscollect() {
        return this.iscollect;
    }

    public void setIscollect(boolean iscollect) {
        this.iscollect = iscollect;
    }

    public Integer getTrandnum() {
        return this.trandnum;
    }

    public void setTrandnum(Integer trandnum) {
        this.trandnum = trandnum;
    }

    public String getTrandname_en() {
        return this.trandname_en;
    }

    public void setTrandname_en(String trandname_en) {
        this.trandname_en = trandname_en;
    }

}
