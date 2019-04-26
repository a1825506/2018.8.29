package com.gikee.app.beans;

import java.util.List;

/**
 * Created by THINKPAD on 2018/6/15.
 */

public class AllProjectBean {
    private String errInfo;
    private Result result;
    private String monetarySymbol;
    private String sortBy;

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

    public void setMonetarySymbol(String monetarySymbol) {
        this.monetarySymbol = monetarySymbol;
    }

    public String getMonetarySymbol() {
        return monetarySymbol;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortBy() {
        return sortBy;
    }

    public class Result {

        private List<Items> items;

        public void setItems(List<Items> items) {
            this.items = items;
        }

        public List<Items> getItems() {
            return items;
        }

    }

    public class Items {

        private String symbol;
        private String name;
        private long blockInitTime;
        private String logo;
        private String price;
        private String rateOfRise;
        private int blockHeight;
        private int accountsTotal;
        private int newAccount;
        private int activeAccount;
        private int tradeAmount;
        private int tradeVolume;

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setBlockInitTime(long blockInitTime) {
            this.blockInitTime = blockInitTime;
        }

        public long getBlockInitTime() {
            return blockInitTime;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getLogo() {
            return logo;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPrice() {
            return price;
        }

        public void setRateOfRise(String rateOfRise) {
            this.rateOfRise = rateOfRise;
        }

        public String getRateOfRise() {
            return rateOfRise;
        }

        public void setBlockHeight(int blockHeight) {
            this.blockHeight = blockHeight;
        }

        public int getBlockHeight() {
            return blockHeight;
        }

        public void setAccountsTotal(int accountsTotal) {
            this.accountsTotal = accountsTotal;
        }

        public int getAccountsTotal() {
            return accountsTotal;
        }

        public void setNewAccount(int newAccount) {
            this.newAccount = newAccount;
        }

        public int getNewAccount() {
            return newAccount;
        }

        public void setActiveAccount(int activeAccount) {
            this.activeAccount = activeAccount;
        }

        public int getActiveAccount() {
            return activeAccount;
        }

        public void setTradeAmount(int tradeAmount) {
            this.tradeAmount = tradeAmount;
        }

        public int getTradeAmount() {
            return tradeAmount;
        }

        public void setTradeVolume(int tradeVolume) {
            this.tradeVolume = tradeVolume;
        }

        public int getTradeVolume() {
            return tradeVolume;
        }

    }
}
