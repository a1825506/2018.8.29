package com.gikee.app.beans;

public class NetStatusBean {

    private CPUBean RAM;

    private CPUBean CPU;

    private CPUBean Net;



    public CPUBean getCPU() {
        return CPU;
    }

    public void setCPU(CPUBean CPU) {
        this.CPU = CPU;
    }


    public CPUBean getRAM() {
        return RAM;
    }

    public void setRAM(CPUBean RAM) {
        this.RAM = RAM;
    }

    public CPUBean getNet() {
        return Net;
    }

    public void setNet(CPUBean net) {
        Net = net;
    }
}
