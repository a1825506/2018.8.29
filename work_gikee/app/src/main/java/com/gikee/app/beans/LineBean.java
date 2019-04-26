package com.gikee.app.beans;

import com.gikee.app.resp.DotBean;

import java.util.List;

public class LineBean {

    private List<DotBean> dot;

    private String currentValue;

    private String currentName;


    public List<DotBean> getDot() {
        return dot;
    }

    public void setDot(List<DotBean> dot) {
        this.dot = dot;
    }


    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }
}
