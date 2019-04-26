package com.gikee.app.beans;

import java.util.List;

/**
 * Created by THINKPAD on 2018/6/19.
 */

public class ShuJuFenXiBean {

    private String errInfo;
    private Result result;

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public String getErrInfo() {
        return errInfo;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public class Result {

        private String coinSymbol;
        private boolean isCollect;
        private String totalAccounts;
        private List<ListBean> list;

        public void setCoinSymbol(String coinSymbol) {
            this.coinSymbol = coinSymbol;
        }

        public String getCoinSymbol() {
            return coinSymbol;
        }

        public void setIsCollect(boolean isCollect) {
            this.isCollect = isCollect;
        }

        public boolean getIsCollect() {
            return isCollect;
        }

        public String getTotalAccounts() {
            return totalAccounts;
        }

        public void setTotalAccounts(String totalAccounts) {
            this.totalAccounts = totalAccounts;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<ListBean> getList() {
            return list;
        }

    }

    public class ListBean {

        private String name;
        private List<Content> content;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setContent(List<Content> content) {
            this.content = content;
        }

        public List<Content> getContent() {
            return content;
        }

    }

    public class Content {

        private String current;
        private String yesterday;
        private List<Long> data_items;
        private List<DataPies> data_pies;
        private String rise;
        private int show_type;
        private String title;

        public void setCurrent(String current) {
            this.current = current;
        }

        public String getCurrent() {
            return current;
        }

        public String getYesterday() {
            return yesterday;
        }

        public void setYesterday(String yesterday) {
            this.yesterday = yesterday;
        }

        public List<DataPies> getData_pies() {
            return data_pies;
        }

        public void setData_pies(List<DataPies> data_pies) {
            this.data_pies = data_pies;
        }

        public void setData_items(List<Long> data_items) {
            this.data_items = data_items;
        }

        public List<Long> getData_items() {
            return data_items;
        }

        public void setRise(String rise) {
            this.rise = rise;
        }

        public String getRise() {
            return rise;
        }

        public void setShow_type(int show_type) {
            this.show_type = show_type;
        }

        public int getShow_type() {
            return show_type;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

    }

    public class DataPies {
        private String name;
        private double percentage;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPercentage() {
            return percentage;
        }

        public void setPercentage(double percentage) {
            this.percentage = percentage;
        }
    }
}
