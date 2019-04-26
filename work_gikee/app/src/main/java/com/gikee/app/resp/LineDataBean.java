package com.gikee.app.resp;

import java.util.List;

public class LineDataBean {


    /**
     * line : {"dot":[],"currentValue":1,"currentName":""}
     */

    private List<String> currentValue;
    private List<String> currentName;
    private String quoteChange;
    private List<DotBean> dot;

    public List<String> getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(List<String> currentValue) {
        this.currentValue = currentValue;
    }

    public List<String> getCurrentName() {
        return currentName;
    }

    public void setCurrentName(List<String> currentName) {
        this.currentName = currentName;
    }

    public List<DotBean> getDot() {
        return dot;
    }

    public void setDot(List<DotBean> dot) {
        this.dot = dot;
    }


    public String getQuoteChange() {
        return quoteChange;
    }

    public void setQuoteChange(String quoteChange) {
        this.quoteChange = quoteChange;
    }
}
