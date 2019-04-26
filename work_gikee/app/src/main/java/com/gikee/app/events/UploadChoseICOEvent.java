package com.gikee.app.events;

/**
 * Created by THINKPAD on 2018/3/29.
 */

public class UploadChoseICOEvent {

    private String fname;
    private String content;

    public UploadChoseICOEvent(String fname) {
        this.fname = fname;
    }

    public UploadChoseICOEvent(String fname, String content) {
        this.fname = fname;
        this.content = content;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
