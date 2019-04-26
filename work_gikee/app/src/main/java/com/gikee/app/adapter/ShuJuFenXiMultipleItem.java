package com.gikee.app.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.gikee.app.beans.ShuJuFenXiBean;

/**
 * Created by THINKPAD on 2018/6/19.
 */

public class ShuJuFenXiMultipleItem implements MultiItemEntity {



    private int itemType;
    private String title;
    private ShuJuFenXiBean.Content items;


    public ShuJuFenXiMultipleItem(int itemType, String title, ShuJuFenXiBean.Content items) {
        this.itemType = itemType;
        this.title = title;
        this.items = items;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public ShuJuFenXiBean.Content getItems() {
        return items;
    }

    public void setItems(ShuJuFenXiBean.Content items) {
        this.items = items;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
