package com.gikee.app.type;

/***
 * 消息类型
 *
 */

public enum RemindType {

    Quotes("Quotes"), BigChange("TxChangeInfo");

    RemindType(String content) {
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
