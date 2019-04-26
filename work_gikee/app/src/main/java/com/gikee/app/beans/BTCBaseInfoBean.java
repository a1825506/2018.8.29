package com.gikee.app.beans;

import com.gikee.app.resp.ResponseInfo;

public class BTCBaseInfoBean extends ResponseInfo{


    /**
     * blockHeight :
     * confirmNum :
     * blockTime :
     * blockSize :
     * virtualSize :
     * weight :
     * input :
     * output :
     * Sigops :
     * minerFee :
     * minerRate :
     */

    private String blockHeight;
    private String confirmNum;
    private String blockTime;
    private String blockSize;
    private String virtualSize;
    private String weight;
    private String input;
    private String output;
    private String Sigops;
    private String minerFee;
    private String minerRate;

    public String getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(String blockHeight) {
        this.blockHeight = blockHeight;
    }

    public String getConfirmNum() {
        return confirmNum;
    }

    public void setConfirmNum(String confirmNum) {
        this.confirmNum = confirmNum;
    }

    public String getBlockTime() {
        return blockTime;
    }

    public void setBlockTime(String blockTime) {
        this.blockTime = blockTime;
    }

    public String getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(String blockSize) {
        this.blockSize = blockSize;
    }

    public String getVirtualSize() {
        return virtualSize;
    }

    public void setVirtualSize(String virtualSize) {
        this.virtualSize = virtualSize;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getSigops() {
        return Sigops;
    }

    public void setSigops(String Sigops) {
        this.Sigops = Sigops;
    }

    public String getMinerFee() {
        return minerFee;
    }

    public void setMinerFee(String minerFee) {
        this.minerFee = minerFee;
    }

    public String getMinerRate() {
        return minerRate;
    }

    public void setMinerRate(String minerRate) {
        this.minerRate = minerRate;
    }
}
