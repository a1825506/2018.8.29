package com.gikee.app.beans;

import com.gikee.app.resp.ResponseInfo;

import java.util.List;

/**
 * Created by THINKPAD on 2018/7/25.
 */

public class AllProjectCollBean extends ResponseInfo {
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

        private List<Items> items;
        private String monetarySymbol;
        private String sortBy;

        public void setItems(List<Items> items) {
            this.items = items;
        }

        public List<Items> getItems() {
            return items;
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

    }

    public class Items {

        private String active;
        private String blockHeight;
        private String logo;
        private String name;
        private String address;
        private String blockInitTime;
        private String price;
        private String rateOfBigTrade;
        private String rateOfRise;
        private String symbol;
        private String accountsTotal;
        private String newAccount;
        private String activeAccount;
        private String tradeAmount;
        private String tradeVolume;

        public void setActive(String active) {
            this.active = active;
        }

        public String getActive() {
            return active;
        }

        public void setBlockHeight(String blockHeight) {
            this.blockHeight = blockHeight;
        }

        public String getBlockHeight() {
            return blockHeight;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getLogo() {
            return logo;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setBlockInitTime(String blockInitTime) {
            this.blockInitTime = blockInitTime;
        }

        public String getBlockInitTime() {
            return blockInitTime;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPrice() {
            return price;
        }

        public void setRateOfBigTrade(String rateOfBigTrade) {
            this.rateOfBigTrade = rateOfBigTrade;
        }

        public String getRateOfBigTrade() {
            return rateOfBigTrade;
        }

        public void setRateOfRise(String rateOfRise) {
            this.rateOfRise = rateOfRise;
        }

        public String getRateOfRise() {
            return rateOfRise;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setAccountsTotal(String accountsTotal) {
            this.accountsTotal = accountsTotal;
        }

        public String getAccountsTotal() {
            return accountsTotal;
        }

        public void setNewAccount(String newAccount) {
            this.newAccount = newAccount;
        }

        public String getNewAccount() {
            return newAccount;
        }

        public void setActiveAccount(String activeAccount) {
            this.activeAccount = activeAccount;
        }

        public String getActiveAccount() {
            return activeAccount;
        }

        public void setTradeAmount(String tradeAmount) {
            this.tradeAmount = tradeAmount;
        }

        public String getTradeAmount() {
            return tradeAmount;
        }

        public void setTradeVolume(String tradeVolume) {
            this.tradeVolume = tradeVolume;
        }

        public String getTradeVolume() {
            return tradeVolume;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

}
