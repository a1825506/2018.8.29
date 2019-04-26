package com.gikee.app.events;

/**
 * Created by THINKPAD on 2018/6/29.
 */

public class MineChoseUnitEvent {

    private String name;
    private String text;

    public MineChoseUnitEvent(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
