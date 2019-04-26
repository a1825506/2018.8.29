package com.gikee.app.beans;

public class GasdetailBean {
    /**
     * gaslimit :
     * gas_used_by_txn :
     * gas_price :
     * actual_tx_cost :
     */

    private String gaslimit;
    private String gas_used_by_txn;
    private String gas_price;
    private String actual_tx_cost;

    public String getGaslimit() {
        return gaslimit;
    }

    public void setGaslimit(String gaslimit) {
        this.gaslimit = gaslimit;
    }

    public String getGas_used_by_txn() {
        return gas_used_by_txn;
    }

    public void setGas_used_by_txn(String gas_used_by_txn) {
        this.gas_used_by_txn = gas_used_by_txn;
    }

    public String getGas_price() {
        return gas_price;
    }

    public void setGas_price(String gas_price) {
        this.gas_price = gas_price;
    }

    public String getActual_tx_cost() {
        return actual_tx_cost;
    }

    public void setActual_tx_cost(String actual_tx_cost) {
        this.actual_tx_cost = actual_tx_cost;
    }
}
