package com.gikee.app.resp;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class SummaryBean implements MultiItemEntity{


    /**
     * itemName :
     * titile :
     * showType :
     * pieData : {"percent":"","name":""}
     * tableData : [{"from":"","fromName":"","to":"","toName":"","value":1,"unit":""}]
     */

    private String itemName;
    private String titile;
    private String showType;
    private List<PieDataBean> pieData;
    private List<TableDataBean> tableData;
    private List<LineDataBean> lineData;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public List<PieDataBean> getPieData() {
        return pieData;
    }

    public void setPieData(List<PieDataBean> pieData) {
        this.pieData = pieData;
    }

    public List<TableDataBean> getTableData() {
        return tableData;
    }

    public void setTableData(List<TableDataBean> tableData) {
        this.tableData = tableData;
    }


    @Override
    public int getItemType() {
        return 0;
    }

    public List<LineDataBean> getLineData() {
        return lineData;
    }

    public void setLineData(List<LineDataBean> lineData) {
        this.lineData = lineData;
    }
}
