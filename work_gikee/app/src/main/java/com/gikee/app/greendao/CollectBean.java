package com.gikee.app.greendao;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.util.List;

@Entity
public class CollectBean {


    @Id(autoincrement = true)
    private Long id;

    private String name;

    private String tag;

    private String logo;

    private String type;//标记地址还是项目(address、project)

    private Integer trandnum;

    private String addressid;//保存地址时：具体地址    保存项目时：项目ID

    private String address_list;//保存多个地址时：


    private boolean iscollect;

    private String balance;

    private String address_type;//标记地址类型（btc、eth、eos）

    private String detail;//合并监控标记

    private String count;//合并监控数量

    private String collect_time;//开始监控的时间

    private int trade_count;//监控到事件的数量






    @Generated(hash = 456024174)
    public CollectBean(Long id, String name, String tag, String logo, String type,
            Integer trandnum, String addressid, String address_list,
            boolean iscollect, String balance, String address_type, String detail,
            String count, String collect_time, int trade_count) {
        this.id = id;
        this.name = name;
        this.tag = tag;
        this.logo = logo;
        this.type = type;
        this.trandnum = trandnum;
        this.addressid = addressid;
        this.address_list = address_list;
        this.iscollect = iscollect;
        this.balance = balance;
        this.address_type = address_type;
        this.detail = detail;
        this.count = count;
        this.collect_time = collect_time;
        this.trade_count = trade_count;
    }

    @Generated(hash = 420494524)
    public CollectBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddressid() {
        return this.addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }

    public Integer getTrandnum() {
        return this.trandnum;
    }

    public void setTrandnum(Integer trandnum) {
        this.trandnum = trandnum;
    }

    public boolean getIscollect() {
        return this.iscollect;
    }

    public void setIscollect(boolean iscollect) {
        this.iscollect = iscollect;
    }

    public String getBalance() {
        return this.balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getAddress_type() {
        return this.address_type;
    }

    public void setAddress_type(String address_type) {
        this.address_type = address_type;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCount() {
        return this.count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCollect_time() {
        return this.collect_time;
    }

    public void setCollect_time(String collect_time) {
        this.collect_time = collect_time;
    }

    public int getTrade_count() {
        return this.trade_count;
    }

    public void setTrade_count(int trade_count) {
        this.trade_count = trade_count;
    }

    public String getAddress_list() {
        return this.address_list;
    }

    public void setAddress_list(String address_list) {
        this.address_list = address_list;
    }

   
}
