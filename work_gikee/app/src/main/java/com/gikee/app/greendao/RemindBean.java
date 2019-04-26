package com.gikee.app.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class RemindBean {

    @Id(autoincrement = true)
    private Long id;
    private String title;
    private String title_en;
    private String time;
    private String from;
    private String context;
    private String context_en;
    private boolean isHot;
    
    @Generated(hash = 1431291097)
    public RemindBean(Long id, String title, String title_en, String time,
            String from, String context, String context_en, boolean isHot) {
        this.id = id;
        this.title = title;
        this.title_en = title_en;
        this.time = time;
        this.from = from;
        this.context = context;
        this.context_en = context_en;
        this.isHot = isHot;
    }
    @Generated(hash = 1914622572)
    public RemindBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getFrom() {
        return this.from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getContext() {
        return this.context;
    }
    public void setContext(String context) {
        this.context = context;
    }
//    public boolean getIshot() {
//        return this.ishot;
//    }
//    public void setIshot(boolean ishot) {
//        this.ishot = ishot;
//    }
    public boolean getIsHot() {
        return this.isHot;
    }
    public void setIsHot(boolean isHot) {
        this.isHot = isHot;
    }
    public String getTitle_en() {
        return this.title_en;
    }
    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }
    public String getContext_en() {
        return this.context_en;
    }
    public void setContext_en(String context_en) {
        this.context_en = context_en;
    }


   
}
