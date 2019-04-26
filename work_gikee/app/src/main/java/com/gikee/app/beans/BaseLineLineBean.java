package com.gikee.app.beans;

import com.gikee.app.resp.ResponseInfo;

import java.util.List;

public class BaseLineLineBean extends ResponseInfo{

    private String currentValue;

    private String currentName;

    private String gain;

    private List<LineBean> line;


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

    public String getGain() {
        return gain;
    }

    public void setGain(String gain) {
        this.gain = gain;
    }

    public List<LineBean> getLine() {
        return line;
    }

    public void setLine(List<LineBean> line) {
        this.line = line;
    }

}
