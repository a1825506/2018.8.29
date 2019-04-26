package com.gikee.app.beans;

import com.gikee.app.resp.ResponseInfo;

public class IndexChooseBean extends ResponseInfo{

    private boolean isCheck;

    private boolean isEnable;

    private String title;

    private String id;

    private String value;

    private String quate;

    private String type;

    private String logo;

    private int color;



    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getQuate() {
        return quate;
    }

    public void setQuate(String quate) {
        this.quate = quate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
