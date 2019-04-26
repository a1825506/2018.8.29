package com.gikee.app.type;

public enum TimeType {

    minute("minute"),hour("hour"),day("day"),week("week"),month("month");

    TimeType(String content) {
        this.content = content;
    }

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
